package Utility;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class SingletonClass {
	private PrintWriter writer;
	private SingletonClass(){
			try {	
			writer= new PrintWriter(new FileWriter("loggerFILE.txt",true));
			}catch(IOException e) {e.printStackTrace();}
	}
	public static SingletonClass getInstance(){
		return Holder.INSTANCE;
	}
	private static final class Holder{
		private static SingletonClass INSTANCE= new SingletonClass();
	}
	
	public void logging(String message) {
		String timeStamp=LocalDateTime.now()+"  " +message;
		System.out.println("[LOG] "+ timeStamp);
		writer.println(timeStamp);										// scrive su file
		writer.flush();													//svuota il buffer per scrivere subito su file
	}
}
