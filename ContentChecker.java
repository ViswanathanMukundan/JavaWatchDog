package com.mv.fileWatchDog;

import java.io.IOException;
import java.util.Date;

public class ContentChecker 
{

	private String MAIL_SUBJECT = "Reg. log readings";
	private FileWatcher watchDog;
	private String filePath;

	public String getMAIL_SUBJECT() {
		return MAIL_SUBJECT;
	}

	public void setMAIL_SUBJECT(String mAIL_SUBJECT) {
		MAIL_SUBJECT = mAIL_SUBJECT;
	}

	public FileWatcher getWatchDog() {
		return watchDog;
	}

	public void setWatchDog(FileWatcher watchDog) {
		this.watchDog = watchDog;
	}

	public String getFilePath() {
		return filePath;
	}

	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}

	ContentChecker(String filePath) throws IOException 
	{
		this.filePath = filePath;
		this.watchDog = new FileWatcher(filePath);
	}

	public void checkFileContent() throws Exception 
	{
		if (watchDog.isFileUpdated(this.filePath)) 
		{
			if (watchDog.getLastLine(this.filePath).toLowerCase().contains("critical"))
				new Mailer().sendEmail(this.MAIL_SUBJECT,
						"Critical event recorded at::: "
								+ new java.text.SimpleDateFormat("dd/MM/yyyy, HH:mm:ss")
										.format(new Date(watchDog.getTimeStamp()))
								+ "\nEntry: " + watchDog.getLastLine(this.filePath) + "\n\n Kindly take action");
		}
	}

}
