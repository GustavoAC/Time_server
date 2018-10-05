package server;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface ICalendar extends Remote {
	String getFormattedDate(String format) throws RemoteException;
	String getFormattedDateAt(String format, String timezone) throws RemoteException;
}
