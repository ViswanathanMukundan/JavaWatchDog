package com.mv.fileWatchDog;
/*
TEST CODE TO MONITOR INDIVIDUAL FILES FOR CHANGES
*/

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;

public class FileWatcher 
{
	private long timeStamp;
	private File file;
	private String originalContent;
	private String modifiedContent;
	private int lineCount;
	
	logEntry writer = new logEntry();
	
	public long getTimeStamp()
	{
		return this.timeStamp;
	}
	
	public File getFile() {
		return file;
	}

	public String getOriginalContent() {
		return originalContent;
	}

	public String getModifiedContent() {
		return modifiedContent;
	}
	
	public void setTimeStamp(long newTimeStamp)
	{
		this.timeStamp = newTimeStamp;
	}
	
	FileWatcher(String filePath) throws IOException
	{
		this.file = new File(filePath);
		this.originalContent = fileReader(filePath);
		this.lineCount = numberOfLines(filePath);
	}
	
	private String fileReader(String filePath) throws FileNotFoundException
	{
		String contents = "";
		Scanner reader = new Scanner(this.file);
		//reader.useDelimiter("\\Z");
		while(reader.hasNext())
			contents += reader.next();
		reader.close();
		return contents;
	}
	
	/*
	 * NAIVELY CHECKING FILE UPDATE BY COMPARING LINE COUNTS AT DIFFERENT POINTS OF TIME.
	 * SINCE THE CURRENT USE CASE IS FOR LOG WATCHING, AND LOG ENTRIES ARE ADDED TO NEW LINES, THIS WORKS.
	 * HOWEVER, FOR CONFIG FILES, THE APPROACH USED WILL DIFFER.
	 * */
	boolean isFileUpdated(String filePath) throws IOException
	{
		this.modifiedContent = fileReader(filePath);
		/*if(!(getOriginalContent().equals(getModifiedContent())))
		{
			setTimeStamp(getFile().lastModified());
			System.out.println("Updated content::: " + StringUtils.difference(this.originalContent, this.modifiedContent));
			System.out.println("No of lines::: " + numberOfLines(filePath));
			this.originalContent = this.modifiedContent;
			return true;
		}*/
		
		int updatedLineCount = numberOfLines(filePath);
		if(this.lineCount != updatedLineCount)
		{
			this.lineCount = updatedLineCount;
			setTimeStamp(getFile().lastModified());
			this.originalContent = this.modifiedContent;
			return true;
		}
		
		return false;
		 
	}
	/*
	 * GETTING THE LINE COUNT OF THE FILE
	 * */
	int numberOfLines(String filePath) throws IOException
	{
		LineNumberReader lineReader = new LineNumberReader(new FileReader(filePath));
		while(lineReader.skip(Long.MAX_VALUE) > 0)
		{}
		int result = lineReader.getLineNumber() + 1;
		lineReader.close();
		return result;
		
	}

}

