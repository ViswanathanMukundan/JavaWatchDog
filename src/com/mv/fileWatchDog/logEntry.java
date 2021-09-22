package com.mv.fileWatchDog;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.nio.file.*;

/*
 *THIS CLASS IS USED TO ADD ENTRIES INTO YET ANOTHER LOG FILE. AN ASSUMED USE-CASE CAN BE TAKEN AS- RECORDING ONLY THE CRITICAL WARNINGS AND ERRORS IN ORDER TO MEASURE THE 
 EFFICIENCY OF THE SYSTEM.
 * */



public class logEntry {
	
	private static String LOG_SOURCE_FILE = "PATH TO THE LOG FILE"; //THE FULLY-QUALIFIED PATH TO THE LOG FILE (OR MAYBE CONFIG FILE) IS SPECIFIED IN THIS VARIABLE.
	Path fileName = Path.of(LOG_SOURCE_FILE);
	SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy::: HH:mm:ss");
	
	void addEntry(String entry)
	{
		byte bytes[] = ("\r\n" + formatter.format(new Date()) + ": " + entry.toString()).getBytes();
		try 
		{
			Files.write(fileName, bytes, StandardOpenOption.APPEND);  //APPEND THE ENTRY INSTEAD OF OVERWRITING THE EXISTING ONES.
		}
		catch (IOException e) 
		{
			//e.printStackTrace();
		}
	}

}
