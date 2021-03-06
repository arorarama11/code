package com.psd2.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.testng.IReporter;
import org.testng.IResultMap;
import org.testng.ISuite;
import org.testng.ISuiteResult;
import org.testng.ITestContext;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;
import org.testng.Reporter;
import org.testng.internal.Utils;
import org.testng.xml.XmlSuite;

/**
 * Class description - This class used to generate the new reporter
 * 
 * 
 *         How to use - Add child node <listeners> to node <suite> in the
 *         suite.xml, like this: <listeners><listener
 *         class-name="com.ibm.sce.core.utils.NewReporter" /></listeners>
 * 
 */
public class NewReporter implements IReporter {

	private String rootPath = null;

	private String folderName = null;

	private List<String> fileNameList = null;

	private int testsPass = 0;

	private int testsFail = 0;

	private int testsSkip = 0;

	private int confFail = 0;

	private int confSkip = 0;

	private Map<String, Map<String, List<Map<String, Object>>>> reportBody = null;

	private HashMap<String, String> testNameMap = null;

	private HashMap<String, String> classDetails = null;
	
	private String chartScript="";

	private long[] time = new long[2];
	
	private HashMap<String, HashMap<Integer, Integer>> epicSpecificInfoMap = new HashMap<String, HashMap<Integer,Integer>>();
	
	public NewReporter() {
		super();
		folderName = String.valueOf(new Date().getTime());
		fileNameList = new ArrayList<String>();
		reportBody = new HashMap<String, Map<String, List<Map<String, Object>>>>();
		testNameMap = new HashMap<String, String>();
		classDetails = new HashMap<String, String>();
	}

	private void createFolder(String outdir) {
		rootPath = outdir;
		while (new File(outdir + "/" + folderName).exists()) {
			folderName = String.valueOf(Long.valueOf(folderName) + 1);
		}
		new File(outdir + "/" + folderName).mkdirs();
	}

	public void generateReport(List<XmlSuite> xml, List<ISuite> suites,
			String outdir) {
		this.createFolder(outdir);
		for (ISuite suite : suites) {
			Map<String, ISuiteResult> r = suite.getResults();
			for (ISuiteResult r2 : r.values()) {
				ITestContext testContext = r2.getTestContext();
				long startTime = testContext.getStartDate().getTime();
				long endTime = testContext.getEndDate().getTime();				
				if (startTime < time[0] || time[0]==0)
					time[0] = startTime;
				if (endTime > time[1] || time[1]==0)
					time[1] = endTime;
				confFail = confFail
						+ testContext.getFailedConfigurations().size();
				resultDetail(testContext.getFailedConfigurations());
				testsFail = testsFail + testContext.getFailedTests().size();
				resultDetail(testContext.getFailedTests());
				confSkip = confSkip
						+ testContext.getSkippedConfigurations().size();
				resultDetail(testContext.getSkippedConfigurations());
				testsSkip = testsSkip + testContext.getSkippedTests().size();
				resultDetail(testContext.getSkippedTests());
				testsPass = testsPass + testContext.getPassedTests().size();
				resultDetail(testContext.getPassedTests());
			}
		}
		try {
			File file = new File(outdir + "/report.html");
			BufferedWriter output = new BufferedWriter(new FileWriter(file));
			String htmlContent = this.reportHeader() + this.reportTableHead()
					+ this.getTableBody() + this.reportBottom();
			output.write(htmlContent);
			output.close();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private void resultDetail(IResultMap tests) {
		for (ITestResult result : tests.getAllResults()) {
			ITestNGMethod method = result.getMethod();
			String [] testname = result.getTestClass().getName().split("\\.");
			String epicName = testname[testname.length-2];
			if(!epicSpecificInfoMap.containsKey(epicName)){
				HashMap<Integer, Integer> passFailMap = new HashMap<Integer, Integer>();
				passFailMap.put(result.getStatus(), 1);
				epicSpecificInfoMap.put(epicName, passFailMap);
			}
			else{
				Integer value = epicSpecificInfoMap.get(epicName).get(result.getStatus());
				if(value == null){
					epicSpecificInfoMap.get(epicName).put(result.getStatus(), 1);
				}
				else{
					epicSpecificInfoMap.get(epicName).put(result.getStatus(), value + 1);
				}
			}
			reportTableBodyInformation(result, method);
		}
	}

	private String getTime(long t) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Long time = new Long(t);
		return format.format(time);
	}

	private List<String> find(String reg, String str) {
		Matcher matcher = Pattern.compile(reg).matcher(str);
		List<String> list = new ArrayList<String>();
		while (matcher.find()) {
			list.add(matcher.group());
		}
		return list;
	}

	private String getRunTime(long[] time) {		
		long l = time[1] - time[0];		
		long day = l / (24 * 60 * 60 * 1000);
		long hour = (l / (60 * 60 * 1000) - day * 24);
		long min = ((l / (60 * 1000)) - day * 24 * 60 - hour * 60);
		long s = (l / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
		String str = day + " day " + hour + " hour " + min + " min " + s
				+ " sec ";
		List<String> list = this.find("[1-9]+.*", str);
		String spend = "";
		if(list.size()>0)
			spend = list.get(0).trim();
		else
			spend = "0 sec";
		return spend;
	}

	private String getTestCaseStatus(int i) {
		String status = null;
		switch (i) {
		case 1:
			status = "Pass";
			break;
		case 2:
			status = "Fail";
			break;
		case 3:
			status = "Skip";
			break;
		}
		return status;
	}

	private void reportTableBodyInformation(ITestResult ans,
			ITestNGMethod method) {
		String key = method.getTestClass().getXmlTest().getName()
				+ method.getTestClass().getName();
		String methodName = method.getMethodName();
		Map<String, Object> tempMap = new HashMap<String, Object>();
		tempMap.put("itr", ans);
		tempMap.put("itm", method);
		if (reportBody.containsKey(key)) {
			Map<String, List<Map<String, Object>>> tempMethodMap = reportBody
					.get(key);
			if (tempMethodMap.containsKey(methodName)) {
				List<Map<String, Object>> tempList = tempMethodMap
						.get(methodName);
				tempList.add(tempMap);
				tempMethodMap.put(methodName, tempList);
			} else {
				List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
				list.add(tempMap);
				tempMethodMap.put(methodName, list);
			}
			reportBody.put(key, tempMethodMap);
		} else {
			Map<String, List<Map<String, Object>>> tempMethodMap = new HashMap<String, List<Map<String, Object>>>();
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			list.add(tempMap);
			tempMethodMap.put(methodName, list);
			reportBody.put(key, tempMethodMap);
		}
		testNameMap.put(key, method.getTestClass().getXmlTest().getName());
	}

	@SuppressWarnings({ "unused", "unchecked" })
	private String reportTableBody(List<Map<String, Object>> list,
			List<Integer> methodCountList) throws Exception {
		StringBuffer append = new StringBuffer();
		//int tempCount = 0;
		for (int i = 0; i < list.size(); i++) {
			ITestResult ans = (ITestResult) list.get(i).get("itr");
			Object[] parameters = ans.getParameters();
			String description = "";
			boolean hasParameters = parameters != null && parameters.length > 0;
			/*if (hasParameters) {
				HashMap<String, String> paraMap = (HashMap<String, String>) parameters[0];
				if (paraMap.containsKey("description"))
					//description = paraMap.get("description");
				if (paraMap.containsKey("Description"))
					//description = paraMap.get("Description");
			}*/
			ITestNGMethod method = (ITestNGMethod) list.get(i).get("itm");

			String className = method.getTestClass().getName();
			String methodName = method.getMethodName();
			String testClassName = className.replace(".", "#");
			testClassName = testClassName.split("#")[testClassName.split("#").length-1];
			classDetails = ExcelDataProvider.getClassDetails(System.getProperty("user.dir") + "/RunConfiguration.xlsx", "classDetail", testClassName);

			String start = getTime(ans.getStartMillis());
			String end = getTime(ans.getEndMillis());
			String testName = method.getTestClass().getXmlTest().getName();
			String fileName = logFile(ans, method);

			// Get Class owner information
			//String absFilePath = new File("").getAbsolutePath();
			//PropertyUtils propertyUtil = new PropertyUtils();
			//File filePath = new File(absFilePath+ "/files/class_owners.properties");
			String owner = "";
			if (classDetails != null) {
				owner = classDetails.get(testClassName).split("##")[0];
				description = classDetails.get(testClassName).split("##")[1];
				if (owner == null) {
					owner = ""; 
				}
				if (description == null) {
					description = ""; 
				}
			} else { // // Get the owner name and description from local java file.
				owner = getOwnerName(className); 
				description = getDescription(className,methodName);
			}
			append.append("<tr>\n");
			if (i == 0) {
				append.append("<td rowspan=\"" + String.valueOf(list.size())
						+ "\">" + testName + "</td>\n");
				append.append("<td rowspan=\"" + String.valueOf(list.size())
						+ "\">" + className + "</td>\n");

			}
//			if (i == methodCountList.get(0)) {
//				tempCount = methodCountList.get(0);
//				methodCountList.remove(0);
//				append.append("<td rowspan=\""
//						+ String.valueOf(methodCountList.get(0) - tempCount)
//						+ "\">" + method.getMethodName() + "</td>\n");
//			}
			append.append("<td>" + method.getMethodName() + "</td>\n");
			if (i == 0) {
				append.append("<td rowspan=\"" + String.valueOf(list.size())
						+ "\">" + owner + "</td>\n");
			}
			append.append("<td>" + description + "</td>\n");
			append.append("<td class=\"" + getTestCaseStatus(ans.getStatus())
					+ "\">" + getTestCaseStatus(ans.getStatus()) + "</td>\n");
			append.append("<td>" + start + "</td>\n");
			append.append("<td>" + end + "</td>\n");

			if (fileName == null)
				append.append("<td>Details</td>\n");
			else
				append.append("<td><a href=\"./" + folderName + "/" + fileName
						+ ".html\" target=\"_blank\">Details</a></td>\n");
			append.append("</tr>\n");
		}
		return append.toString();
	}

	private ArrayList<String> sortHashMapByValue(HashMap<String, String> map) {
		ArrayList<Map.Entry<String, String>> list = new ArrayList<Map.Entry<String, String>>(
				map.entrySet());
		Collections.sort(list, new Comparator<Map.Entry<String, String>>() {
			public int compare(Entry<java.lang.String, String> arg0,
					Entry<java.lang.String, String> arg1) {
				if (arg0.getValue().compareTo(arg1.getValue()) == 0)
					return arg0.getKey().compareTo(arg1.getKey());
				else
					return arg0.getValue().compareTo(arg1.getValue());
			}
		});
		ArrayList<String> keyList = new ArrayList<String>();
		for (Map.Entry<String, String> li : list) {
			keyList.add(li.getKey());
		}
		return keyList;
	}

	private List<Map<String, Object>> sortListByValue(
			List<Map<String, Object>> list) {
		Collections.sort(list, new Comparator<Map<String, Object>>() {
			public int compare(Map<java.lang.String, Object> arg0,
					Map<java.lang.String, Object> arg1) {
				return String.valueOf(
						((ITestResult) arg0.get("itr")).getStartMillis())
						.compareTo(
								String.valueOf(((ITestResult) arg1.get("itr"))
										.getStartMillis()));
			}
		});

		return list;
	}

	private String getTableBody() throws Exception {
		StringBuffer append = new StringBuffer();
		append.append("<tbody>\n");
		ArrayList<String> testNameList = sortHashMapByValue(testNameMap);
		for (String body : testNameList) {
			Map<String, List<Map<String, Object>>> map = reportBody.get(body);
			List<String> l = new ArrayList<String>();
			for (String s : map.keySet()) {

				l.add(s);
			}
			// Collections.sort(l);
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			List<Integer> methodCountList = new ArrayList<Integer>();
			int tempCount = 0;
			methodCountList.add(0);
			for (String s : l) {
				List<Map<String, Object>> listM = map.get(s);
				tempCount = tempCount + listM.size();
				methodCountList.add(tempCount);
				for (Map<String, Object> m : listM) {
					list.add(m);
				}
			}
			sortListByValue(list);
			append.append(reportTableBody(list, methodCountList));
		}
		append.append("</tbody>\n");
		return append.toString();
	}

	private String logFileHeader(String title) {
		StringBuffer append = new StringBuffer();
		append.append("<html>\n");
		append.append("<head>\n");
		append.append("<title>" + title + "</title>\n");
		append.append("</head>\n");
		append.append("<style type=\"text/css\">\n");
		append
				.append(".text{font-size:36;TEXT-ALIGN: center;word-break: break-all; word-wrap:break-word;}\n");
		append.append(".text1{font-size:20;TEXT-ALIGN: center;}\n");
		append.append(".detail{TEXT-ALIGN: left;}\n");
		append
				.append("table{text-align:center;width:100%;border-collapse:collapse;table-layout: fixed;}\n");
		append
				.append(".para{text-align:left;word-break: break-all; word-wrap:break-word;}\n");
		append.append("</style>\n");
		append.append("<body>\n");
		append
				.append("<table cellpadding=\"10\" cellspacing=\"0\" border=\"1\">\n");
		return append.toString();
	}

	private String logFileBottom() {
		StringBuffer append = new StringBuffer();
		append.append("</table>\n");
		append.append("</body>\n");
		append.append("</html>\n");
		return append.toString();
	}

	private String logFile(ITestResult ans, ITestNGMethod method) {
		String fileName = null;
		StringBuffer append = new StringBuffer();
		Object[] parameters = ans.getParameters();
		boolean hasParameters = parameters != null && parameters.length > 0;
		List<String> msgs = Reporter.getOutput(ans);
		boolean hasReporterOutput = msgs.size() > 0;
		Throwable exception = ans.getThrowable();
		boolean hasThrowable = exception != null;
		append.append("<tr>\n");
		append.append("<td class=\"text\" colspan=\""
				+ String.valueOf(parameters.length) + "\">"
				+ method.getTestClass().getName() + ":"
				+ method.getMethodName() + "</td>\n");
		append.append("</tr>\n");
		if (hasParameters) {
			append.append("<tr class=\"text1\">\n");
			for (int i = 0; i < parameters.length; i++) {
				append.append("<td>Parameter #" + String.valueOf(i + 1)
						+ "</td>\n");
			}
			append.append("</tr>\n");
			append.append("<tr class=\"para\">\n");
			for (int i = 0; i < parameters.length; i++) {
				int width = 100 / parameters.length;
				append.append("<td width=" + width + "%>"
						+ parameters[i].toString() + "</td>\n");
			}
			append.append("</tr>\n");
		}
		if (hasReporterOutput) {
			append.append("<tr class=\"detail\">\n<td colspan=\""
					+ String.valueOf(parameters.length) + "\">\n");
			append.append("<h3>Test Messages</h3>\n");
			for (String out : msgs) {
				append.append(out + "<br/>\n");
			}
			append.append("</td>\n</tr>\n");
		}
		if (hasThrowable) {
			append.append("<tr class=\"detail\">\n<td colspan=\""
					+ String.valueOf(parameters.length) + "\">\n");
			append.append("<h3>Failure</h3>\n");
			append.append(exception.getMessage() + "<br/><br/>\n");
			StackTraceElement[] stack = exception.getStackTrace();
			Throwable t2 = exception.getCause();
			if (t2 == exception) {
				t2 = null;
			}
			for (StackTraceElement s : stack) {
				append.append(Utils.escapeHtml(s.toString()) + "<br/>\n");
			}
			append.append("</td>\n</tr>\n");
		}
		try {
			if (hasParameters || hasReporterOutput || hasThrowable) {
				fileName = String.valueOf(new Date().getTime());
				while (fileNameList.contains(fileName)) {
					fileName = String.valueOf(Long.valueOf(fileName) + 1);
				}
				fileNameList.add(fileName);
				File file = new File(rootPath + "/" + folderName + "/"
						+ fileName + ".html");
				BufferedWriter output = new BufferedWriter(new FileWriter(file));
				output.write(logFileHeader(method.getMethodName() + "_"
						+ fileName)
						+ append.toString() + logFileBottom());
				output.close();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return fileName;
	}

	private String reportHeader() {
		StringBuffer append = new StringBuffer();
		append.append("<html>\n");
		append.append("<head>\n");
		append.append("<title>Automation Test Report</title>\n");
		append.append(testSummaryChartScript());
		
		append.append("<style type=\"text/css\">\n");
		append.append(".text{font-size:36;text-align: center;}\n");
		append.append(".mytext{font-size:20;text-align: center;}\n");
		append.append(".projecttext{font-size:25;text-align: left;color: blue;}\n");

		append.append("table{text-align:center;width:100%;border-collapse:collapse;table-layout: fixed;word-wrap:break-word;}\n");
		append.append("table th{background: #ccc}\n");
		append.append(".Pass{background: green;}\n");
		append.append(".Fail{background: red;}\n");
		append.append(".Skip{background: yellow;}.lightgreen{background: #CCFF99;}.lightred{background: #FF6633;}\n");
		append.append(".sum{font-size:20;color: blue;text-align: left;}.p:first-letter {text-transform: uppercase;}." +
						"allcaps {font-variant: small-caps;text-transform: uppercase;}\n");
		append.append("</style>\n");
		append.append("</head>\n");
		append.append("<body>\n");
		append.append("<div>\n");
		append.append("<div class=\"text\" ><b>Automation Test Report</b></div>\n");
		append.append("<br/>\n");
		
		append.append("<div style=\"width: 600px; height: 150px;float:left;\">");
		append.append("<div class=\"projecttext\" ><b>Project : PSD 2</b></div>\n");
		append.append("<div class=\"projecttext\" ><b>CMA Version : "+PropertyUtils.getProperty("cma_ver")+"</b></div>\n");
		append.append("<div class=\"projecttext\" ><b>Client : "+PropertyUtils.getProperty("client_name")+"</b></div>\n");
		append.append("<br/>\n");

		append.append("<div class=\"sum\">\n");
		append.append("<div style=\"float:left;font-size: 19px;\">Tests Run: "
				+ String.valueOf(testsPass + testsFail + testsSkip)
				+ ", Passes: " + String.valueOf(testsPass) + ", Failures: "
				+ String.valueOf(testsFail) + ", Skips: "
				+ String.valueOf(testsSkip));
		if (confFail > 0 || confSkip > 0) {
			append.append(", Configuration Failures: "
					+ String.valueOf(confFail));
		}
		append.append("<br/> Execution Time: "+this.getRunTime(time)+"</div>\n");
		append.append("</div>\n");
		append.append("</div>\n");
		append.append("<div id=\"piechart_3d\" style=\"width: 600px; height: 370px;float:right;\"></div>");
		
		append.append("<div style=\"width:480px;float:left;\" ><div class=\"mytext\" ><br/><b>Test Summary</b></div>");

		append.append("<table align=\"center\" width=\"20%\" cellpadding=\"10\" cellspacing=\"0\" border=\"1\">");
		append.append("<thead>\n");
		append.append("<tr>\n");
		append.append("<th>Module</th>\n");
		append.append("<th>Pass</th>\n");
		append.append("<th>Fail</th>\n");
		append.append("<th>Skip</th>\n");
		append.append("</tr>\n");
		append.append("</thead>\n");
		
		
		for (Iterator<Entry<String, HashMap<Integer, Integer>>> iterator = epicSpecificInfoMap.entrySet().iterator(); iterator.hasNext();) {
			Entry<String, HashMap<Integer, Integer>> type = (Entry<String, HashMap<Integer, Integer>>) iterator.next();
			HashMap<Integer, Integer> entryValue = type.getValue();
			Integer pass = 0;
			Integer fail = 0;
			Integer skip = 0;
			for (Iterator<Entry<Integer, Integer>> iterator2 = entryValue.entrySet().iterator(); iterator2
					.hasNext();) {
				Entry<Integer, Integer> type2 = (Entry<Integer, Integer>) iterator2.next();
				
				if(type2.getKey().equals(1))
					pass = type2.getValue();
				if(type2.getKey().equals(2))
					fail = type2.getValue();
				if(type2.getKey().equals(3))
					skip = type2.getValue();
			}
			if(fail > 0 || skip > 0)
				append.append("<tr class=\"lightred\">\n");
			else
				append.append("<tr class=\"lightgreen\">\n");
			append.append("<td class=\"p\">" + type.getKey() + "</td>\n");
			append.append("<td>" + pass + "</td>\n");
			append.append("<td>" + fail + "</td>\n");
			append.append("<td>" + skip + "</td>\n");
			append.append("</tr>\n");
	
		}
		append.append("</table>\n");
		
		append.append("</div><table><tr><td><div class=\"mytext\" style=\"float:center;\"><br/><b>Overall Summary</b></div></td></tr></table>");
		
		append
				.append("<table cellpadding=\"10\" cellspacing=\"0\" border=\"1\">\n");
		return append.toString();
	}

	private String reportTableHead() {
		StringBuffer append = new StringBuffer();
		append.append("<thead>\n");
		append.append("<tr>\n");
		append.append("<th width=20%>Test</th>\n");
		append.append("<th width=13%>Class</th>\n");
		append.append("<th width=13%>Method</th>\n");
		append.append("<th width=7%>Owner</th>\n");
		append.append("<th width=20%>Description</th>\n");
		append.append("<th width=5%>Status</th>\n");
		append.append("<th width=8%>Begining Time</th>\n");
		append.append("<th width=8%>Finished Time</th>\n");
		append.append("<th width=6%>Details Link</th>\n");
		append.append("</tr>\n");
		append.append("</thead>\n");
		return append.toString();
	}

	private String reportBottom() {
		StringBuffer append = new StringBuffer();
		append.append("</table>\n");
		append.append("</div>\n");
		append.append("</body>\n");
		append.append("</html>\n");
		return append.toString();
	}
	
	/**
	 * Get the @author information from java file.
	 * @param classPath complete path of the java file
	 * @return String Author name
	 */
	@SuppressWarnings("unused")
	private String getOwnerName(String classPath) {
		final String AUTHOR_KEY= "@author";
		final String FILE_TYPE="java";
		FileInputStream fileIPStream = null;
		String strClassPathWithForwordSlash = null;
		String strLine;
		String files = "";
		String authorName = "";
		DataInputStream dataIPStream = null;
		BufferedReader bufferReader = null;
		
		try {
			
			strClassPathWithForwordSlash = classPath.replace(".", "/");
			String strJavaFilePath = new File("").getAbsolutePath().replace("\\", "/")+"/src/"+strClassPathWithForwordSlash+".java";
			File file = new File(strJavaFilePath);
			if(file.exists()) { // check if .java file is present or not.
				fileIPStream = new FileInputStream(strJavaFilePath);
				dataIPStream = new DataInputStream(fileIPStream);
				bufferReader = new BufferedReader(new InputStreamReader(dataIPStream));
				
				// Read Java file line by line till @author is not found
				while ((strLine = bufferReader.readLine()) != null) {
					if (strLine.toLowerCase().contains(AUTHOR_KEY)) {
						String [] javaFileName=files.split("."+FILE_TYPE);
						String className = javaFileName[0];
						String []authorLine=strLine.split(AUTHOR_KEY);
						authorName = authorLine[1];
						authorName = authorName.replaceFirst("-", "").trim();
						authorName = authorName.replaceFirst(":", "").trim();								
						break;	
					}
				}
			} else {
				System.out.println("File Not Found : " + strJavaFilePath);
				return ""; 
			}
			return authorName;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); 
			return "";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		} finally {
			try {
				// Dereference all objects in this finally block.
				if(dataIPStream != null) {
					dataIPStream.close();
				}
				fileIPStream = null;
				strClassPathWithForwordSlash = null;
				strLine = null;
				files = null;
				dataIPStream = null;
				bufferReader = null;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Get the description information from java file.
	 * @param classPath complete path of the java file
	 * @return String Class description
	 */
	/*private String getDescription(String classPath, String method) {
		FileInputStream fileIPStream = null;
		String strClassPathWithForwordSlash = null;
		String strLine;
		String description = "";
		DataInputStream dataIPStream = null;
		BufferedReader bufferReader = null;
		
		try {
			strClassPathWithForwordSlash = classPath.replace(".", "/");
			String strJavaFilePath = new File("").getAbsolutePath().replace("\\", "/")+"/src/"+strClassPathWithForwordSlash+".java";
			File file = new File(strJavaFilePath);
			if(file.exists()) { // check if .java file is present or not.
				fileIPStream = new FileInputStream(strJavaFilePath);
				dataIPStream = new DataInputStream(fileIPStream);
				bufferReader = new BufferedReader(new InputStreamReader(dataIPStream));
				// Read Java file line by line till @Test Description is not found
				int lineNumber = 0;
				while ((strLine = bufferReader.readLine()) != null) {
					lineNumber++;
					if (strLine.contains(method+"(")) {
						for(int i=0; i<=lineNumber+1;i++){
							if( i == lineNumber+1 ){
								String desc = bufferReader.readLine();
								description = desc.substring(desc.indexOf(":")+1);
								break;
							}
						}
					}
				}
			} else {
				System.out.println("File Not Found : " + strJavaFilePath);
				return ""; 
			}
			return description;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); 
			return "";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		} finally {
			try {
				// Dereference all objects in this finally block.
				if(dataIPStream != null) {
					dataIPStream.close();
				}
				fileIPStream = null;
				strClassPathWithForwordSlash = null;
				strLine = null;
				dataIPStream = null;
				bufferReader = null;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}*/
	
	/**
	 * Get the description information from java file.
	 * @param classPath complete path of the java file
	 * @return String Class description
	 */
	private String getDescription(String classPath, String method) {
		final String DESCRIPTION_KEY= "Class Description";
		final String FILE_TYPE="java";
		FileInputStream fileIPStream = null;
		String strClassPathWithForwordSlash = null;
		String strLine;
		String files = "";
		String description = "";
		DataInputStream dataIPStream = null;
		BufferedReader bufferReader = null;
		
		try {
			System.out.println(method);
			strClassPathWithForwordSlash = classPath.replace(".", "/");
			String strJavaFilePath = new File("").getAbsolutePath().replace("\\", "/")+"/src/"+strClassPathWithForwordSlash+".java";
			File file = new File(strJavaFilePath);
			if(file.exists()) { // check if .java file is present or not.
				fileIPStream = new FileInputStream(strJavaFilePath);
				dataIPStream = new DataInputStream(fileIPStream);
				bufferReader = new BufferedReader(new InputStreamReader(dataIPStream));
				
				// Read Java file line by line till @author is not found
				while ((strLine = bufferReader.readLine()) != null) {
					if (strLine.contains(DESCRIPTION_KEY)) {
						String [] javaFileName=files.split("."+FILE_TYPE);
						String className = javaFileName[0];
						System.out.println(className);
						String []authorLine=strLine.split(DESCRIPTION_KEY);
						description = authorLine[1];
						System.out.println(description);
						description = description.replaceFirst("-", "").trim();
						description = description.replaceFirst(":", "").trim();								
						break;	
					}
				}
			} else {
				System.out.println("File Not Found : " + strJavaFilePath);
				return ""; 
			}
			return description;
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace(); 
			return "";
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return "";
		} finally {
			try {
				// Dereference all objects in this finally block.
				if(dataIPStream != null) {
					dataIPStream.close();
				}
				fileIPStream = null;
				strClassPathWithForwordSlash = null;
				strLine = null;
				files = null;
				dataIPStream = null;
				bufferReader = null;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}}
	
	
	private String testSummaryChartScript(){
		chartScript="<script type=\"text/javascript\" src=\"https://www.gstatic.com/charts/loader.js\"></script>\r\n" + 
				"		<script src=\"https://ajax.googleapis.com/ajax/libs/jquery/3.4.1/jquery.min.js\"></script>"+
				"    <script type=\"text/javascript\">\r\n" + 
				"var totalNumberOfTestCases="+ testsFail+testsPass+testsSkip+";\r\n" + 
				"      google.charts.load(\"current\", {packages:[\"corechart\"]});\r\n" + 
				"      google.charts.setOnLoadCallback(drawChart);\r\n" + 
				"      function drawChart() {\r\n" + 
				"        var data = google.visualization.arrayToDataTable([\r\n" + 
				"          ['Task', 'totalNumberOfTestCases'],\r\n" + 
				"          ['Work',     0],\r\n" + 
				"          ['Fail',     "+testsFail+"],\r\n" + 
				"          ['Skip',  "+testsSkip+"],\r\n" + 
				"          ['Pass', "+testsPass+"],\r\n" + 
				"          ['Configuration Failures',    "+confFail+"]\r\n" + 
				"        ]);\r\n" + 
				"\r\n" + 
				"        var options = {\r\n" + 
				"          title: 'Test Execution Chart',\r\n" +
				"          is3D: true,\r\n" + 
				"        };\r\n" + 
				"\r\n" + 
				"        var chart = new google.visualization.PieChart(document.getElementById('piechart_3d'));\r\n" + 
				"        chart.draw(data, options);\r\n" + 
				"		 $('text[x=\"115\"]').attr(\"x\", \"150\");"+
				"		 $('text[x=\"150\"]').attr(\"font-size\", \"16\");"+
				"      }\r\n" + 
				"    </script>";
		return chartScript;
	}
}
