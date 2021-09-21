package com.mv.fileWatchDog;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.nio.file.*;

/*
 * CLASS TO ADD AN ENTRY INTO THE LOG FILE WHOSE SOURCE IS SPECIFIED 
 * */



public class logEntry {
	
	private static String LOG_SOURCE_FILE = "PATH TO THE LOG FILE";
	Path fileName = Path.of(LOG_SOURCE_FILE);
	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy::: HH:mm:ss");
	
	void addEntry(String entry)
	{
		byte bytes[] = ("\r\n" + formatter.format(new Date()) + ": " + entry.toString()).getBytes();
		try 
		{
			Files.write(fileName, bytes, StandardOpenOption.APPEND); //APPEND THE ENTRY INSTEAD OF OVERWRITING THE EXISTING ONES.
		}
		catch (IOException e) 
		{
			//e.printStackTrace();
		}
	}

}
