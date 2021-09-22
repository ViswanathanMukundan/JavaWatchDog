package com.mv.fileWatchDog;
/*
THIS CLASS IS USED TO MONITOR INDIVIDUAL FILES FOR CHANGES.
THE APPROACH USED AS OF NOW IS SIMPLISTIC, THE FILE CONTENTS ARE READ INITIALLY AND SET TO A VARIABLE. WHEN THE METHOD IS INVOKED, THE FILE CONTENTS ARE READ AGAIN AND BOTH VALUES ARE COMPARED.
IF THERE IS ANY DISCREPANCY, IT IMPLIES THAT THE FILE HAS BEEN UPDATED.
*/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class FileWatcher 
{
	private long timeStamp;
	private File file;
	private String originalContent;
	private String modifiedContent;
	
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
	FileWatcher(String filePath) throws FileNotFoundException
	{
		this.file = new File(filePath);
		this.originalContent = fileReader(filePath);
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
	
	// METHOD TO MONITOR THE SPECIFIED FILE AND ALERT IN CASE OF ANY UPDATE. (REGARDLESS OF WHETHER CONTENT IS ADDED, OR REMOVED)
	// A HEAVY ASSUMPTION IS MADE HERE, SINCE LOG FILE CONTENTS ARE TYPICALLY NOT DELETED.
	boolean isFileUpdated(String filePath) throws FileNotFoundException
	{
		this.modifiedContent = fileReader(filePath);
		if(!(getOriginalContent().equals(getModifiedContent())))
		{
			setTimeStamp(getFile().lastModified());   //UPDATE THE TIMESTAMP, FOR MAIL PURPOSES
			this.originalContent = this.modifiedContent;
			return true;
		}
		return false;
	}
}

