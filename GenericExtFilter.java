package com.psd2.utils;

import java.io.File;
import java.io.FilenameFilter;

public class GenericExtFilter implements FilenameFilter {

	private String ext;

	public GenericExtFilter(String ext) {
		this.ext = ext;
	}
	
	@Override
	public boolean accept(File loc, String name) {
		if(name.lastIndexOf('.')>0)
        {
           // get last index for '.' 
           int lastIndex = name.lastIndexOf('.');
           
           // get extension
           String str = name.substring(lastIndex);
           
           // matching extension 
           if(str.equalsIgnoreCase(ext))
           {
              return true;
           }
        }
        return false;
		
	}
}
