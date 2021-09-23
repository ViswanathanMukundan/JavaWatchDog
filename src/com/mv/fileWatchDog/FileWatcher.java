package com.mv.fileWatchDog;
/*
THIS CLASS IS USED TO MONITOR INDIVIDUAL FILES FOR CHANGES.
THE APPROACH USED AS OF NOW IS SIMPLISTIC, THE FILE CONTENTS ARE READ INITIALLY AND SET TO A VARIABLE. WHEN THE METHOD IS INVOKED, THE FILE CONTENTS ARE READ AGAIN AND BOTH VALUES ARE COMPARED.
IF THERE IS ANY DISCREPANCY, IT IMPLIES THAT THE FILE HAS BEEN UPDATED.
*/

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.Scanner;
import org.apache.commons.io.input.ReversedLinesFileReader;
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
		return this.file;
	}

	public String getOriginalContent() {
		return this.originalContent;
	}

	public String getModifiedContent() {
		return this.modifiedContent;
	}
	
	public void setTimeStamp(long newTimeStamp)
	{
		this.timeStamp = newTimeStamp;
	}
	

	//CONSTRUCTOR TO INITIALIZE THE FILEWATCHER BY SETTING THE TARGET FILE'S PATH.
	FileWatcher(String filePath) throws IOException
	{
		this.file = new File(filePath);
		this.originalContent = fileReader(filePath);
		this.lineCount = numberOfLines(filePath);
	}
	
	//GET THE CONTENTS OF THE FILE AS A STRING
	private String fileReader(String filePath) throws FileNotFoundException
	{
		String contents = "";
		Scanner reader = new Scanner(this.file);
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
	 * GETTING THE NUMBER OF LINES IN THE FILE
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
	
	/*
	METHOD TO RETURN THE CONTENTS OF THE VERY LAST LINE IN THE FILE.
	SINCE LOG FILES HAVE THEIR ENTRIES ADDED IN NEW LINES, AND THE LAST LINE CONTAINS
	THE MOST RECENT ENTRY, THIS METHOD WILL BE USED TO GET ONLY THAT LINE AND CHECK IF A 
	MAIL EVENT IS REQUIRED TO BE TRIGGERED. 
	MAKES USE OF org.apache.commons.io.input.ReversedLinesFileReader TO READ THE FILE FROM THE LAST AND RETURN
	THE FIRST VALUE.
	*/
	@SuppressWarnings({ "deprecation", "resource" })
	String getLastLine(String filePath) throws IOException
	{
		return new ReversedLinesFileReader(new File(filePath)).readLine();
	}
}

