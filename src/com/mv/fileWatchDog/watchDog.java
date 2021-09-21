/*
 * METHOD CLASS TO MONITOR A SPECIFIC FILE FOR ANY CHANGES, 
 * AND IMPLEMENT A SPECIFIC ACTION.
 * */
package com.mv.fileWatchDog;

import java.nio.file.*;

/*
 * java.nio.file CONTAINS THE FOLLOWING CLASSES:
 * 	-> StandardWatchEventKinds
 * 	-> WatchService
 * 	-> WatchKey
 * 	-> Path
 * 	-> Paths
 * -> WatchEvent
 * -> 
 * 
 * */

public class watchDog{
	
	logEntry writer = new logEntry();
	
	watchDog(String filePath)
	{
		boolean poll = true;
		try
		{
			WatchService watchService = FileSystems.getDefault().newWatchService();
			Path path = Paths.get(filePath);
			path.register(watchService, StandardWatchEventKinds.ENTRY_CREATE, 
					StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.ENTRY_DELETE, StandardWatchEventKinds.OVERFLOW);
			while(poll)
			{
				WatchKey key = watchService.take();
				for(WatchEvent<?> event : key.pollEvents())
				{
					System.out.println("Event kind: " + event.kind() + " File: " + event.context());
					
					if(event.kind() == StandardWatchEventKinds.ENTRY_MODIFY || event.kind() == StandardWatchEventKinds.OVERFLOW)
					{
						String fileName = filePath + "\\" + event.context();
						System.err.println(fileName);
					//	FileWatcher watcher = new FileWatcher(fileName);
						//if(watcher.isFileUpdated(fileName))
						{
							writer.addEntry("File " + event.context() + " has been Modified");
							//String entry = "File updated at::: " + new java.text.SimpleDateFormat("dd/MM/yyyy, HH:mm:ss").format(new Date(watcher.getTimeStamp()));
							//writer.addEntry(entry);
						}
					}
					if(event.kind() == StandardWatchEventKinds.ENTRY_CREATE)
						writer.addEntry("File " + event.context() + " has been Created");
					if(event.kind() == StandardWatchEventKinds.ENTRY_DELETE)
						writer.addEntry("File " + event.context() + " has been Deleted");
				}
				poll = key.reset();
			}
		}
		catch(Exception e)
		{
			e.getStackTrace();
		}
	}

}