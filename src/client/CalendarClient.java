package client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

import server.ICalendar;

public class CalendarClient {
	public static void main(String[] args) throws MalformedURLException, RemoteException, NotBoundException {
		ICalendar calendar = (ICalendar) Naming.lookup("rmi://localhost/Calendar");
		Scanner scanner = new Scanner(System.in);
		
		String format = "";
		while (true) { 
			System.out.println("Enter data format: ");
			format = scanner.nextLine();
			
			if (format.equals("") || format.toLowerCase().equals("exit")) break;
			
			String date = calendar.getFormattedDate(format);
			System.out.println("Data atual: " + date);
		}
		
		scanner.close();
		System.out.println("Exiting...");
	}
}
