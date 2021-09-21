# JavaWatchDog
Simple project to monitor a log file and trigger an email message in case of any change.

The aim of this project is to constantly monitor a specific log file for any changes. If any critical events/warnings are recorded in the log, an email is triggered to a set of users immediately.

Some specific JAR files required:
  -> activation.jar (JavaBeans activation)
  -> javax.mail.jar (JavaMail API)
  -> smtp.jar 

It is understood that the log file is being populated by some other means. For test purposes, the file can be manually modified by entering random text.

Some improvements to be made:
  1. Optimize the solution
  2. Try to make the files run on individual threads.
  3. As of now, emails are triggered in case of any modification. Try to restrict the triggers to specific events.
  4. Mailer takes about 8 seconds to send a single mail. 
