package com.mv.fileWatchDog;
/*
TEST CODE TO MONITOR INDIVIDUAL FILES FOR CHANGES
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
	
	public long getTimeStamp() {
		return timeStamp;
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

	FileWatcher(String filePath) throws FileNotFoundException
	{
		this.file = new File(filePath);
		this.originalContent = fileReader(filePath);
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
	
	boolean isFileUpdated(String filePath) throws FileNotFoundException
	{
		this.file = new File(filePath); 
		long modifiedTimeStamp = file.lastModified();
		if(this.timeStamp != modifiedTimeStamp)
		{
			this.timeStamp = modifiedTimeStamp;
			this.modifiedContent = fileReader(filePath);
			if(!(this.originalContent.equals(this.modifiedContent)))
			{
				this.originalContent = this.modifiedContent;
				return true;
			}
		}
		return false;
	}

	/*public static void main(String[] args) throws FileNotFoundException
	{
		String filePath = "C:\\Users\\Vishy\\Desktop\\javaSample.txt";
		FileWatcher watchDog = new FileWatcher(filePath);
		while(true)
		{
			if(watchDog.isFileUpdated(filePath))
			{
				System.out.println("File updated at::: " + new java.text.SimpleDateFormat("dd/MM/yyyy, HH:mm:ss").format(new Date(watchDog.timeStamp)));
			}
		}
	}*/
}

