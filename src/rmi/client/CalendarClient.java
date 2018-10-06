package rmi.client;

import java.net.MalformedURLException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.util.Scanner;

import exceptions.DateServerException;
import rmi.server.ICalendar;

public class CalendarClient {
	private ICalendar calendar;
	
	public CalendarClient() throws DateServerException {
		restartConnection();
	}
	
	public String getFormattedDate(String format) throws DateServerException {
		try {
			return calendar.getFormattedDate(format);
		} catch (RemoteException e) {
			restartConnection();
		}
		
		try {
			return calendar.getFormattedDate(format);
		} catch (RemoteException e) {
			throw new DateServerException("Houve algum erro na conexão com o servidor, tente mais tarde");
		}
	}
	
	public String getFormattedDateAt(String format, String timezone) throws DateServerException {
		try {
			return calendar.getFormattedDateAt(format, timezone);
		} catch (RemoteException e) {
			restartConnection();
		}
		
		try {
			return calendar.getFormattedDateAt(format, timezone);
		} catch (RemoteException e) {
			throw new DateServerException("Houve algum erro na conexão com o servidor, tente mais tarde");
		}
	}
	
	private void restartConnection() throws DateServerException {
		try {
			calendar = (ICalendar) Naming.lookup("rmi://localhost/Calendar");
		} catch (MalformedURLException e) {
			throw new DateServerException("Erro na formação da URL de acesso");
		} catch (RemoteException e) {
			throw new DateServerException("Houve um erro na comunicação com o servidor remoto");
		} catch (NotBoundException e) {
			throw new DateServerException("O serviço RMI de horário não foi iniciado");
		}
	}
	
	public static void main(String[] args) {
		CalendarClient calendarClient;
		
		try {
			calendarClient = new CalendarClient();
		} catch (DateServerException e1) {
			System.out.println(e1.getMessage());
			System.out.println("Tente novamente mais tarde");
			return;
		}
		
		Scanner scanner = new Scanner(System.in);
		
		String format = "";
		while (true) { 
			System.out.println("Enter data format: ");
			format = scanner.nextLine();
			
			if (format.equals("") || format.toLowerCase().equals("exit")) break;
			
			try {
				String date = calendarClient.getFormattedDate(format);
				System.out.println("Data atual: " + date);
			} catch (DateServerException e) {
				System.out.println(e.getMessage());
				System.out.println("Tente novamente mais tarde");
			}
			
			
		}
		
		scanner.close();
		System.out.println("Exiting...");
	}
}
