package client;

import com.sun.deploy.net.HttpResponse;
import org.omg.CORBA.NameValuePair;
import sun.net.www.http.HttpClient;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CalendarRestClient {
	public static void main(String[] args) {
		URL formatUrl = null;
		URL formatTimezoneUrl = null;
		try {
			formatUrl = new URL("http://localhost:8080/rest_glassfish_war_exploded/time/getFormattedDate");
			formatTimezoneUrl = new URL("http://localhost:8080/rest_glassfish_war_exploded/time/getFormattedDateAt");
		} catch (MalformedURLException e) {
			return;
		}

		String input;
		Scanner scanner = new Scanner(System.in);

		while (true) {
			System.out.println("1 para consulta por formato, 2 para consulta por formato e local, outro para sair");
			input = scanner.nextLine();
			if (input.equals("1")) {
				System.out.println("Insira o formato: ");
				String format;
				format = scanner.nextLine();
				System.out.println(makeRequest(formatUrl, "{ \"format\": \""+ format + "\" }"));

			} else if (input.equals("2")) {
				System.out.println("Insira o formato: ");
				String format;
				format = scanner.nextLine();

				System.out.println("Insira a zona: ");
				String timezone;
				timezone = scanner.nextLine();

				System.out.println(makeRequest(formatTimezoneUrl,
						"{ \"format\": \""+ format + "\", \"timezone\": \""+ timezone + "\"}"));
			} else {
				break;
			}
		}

		System.out.println("Até mais");
	}

	public static String makeRequest(URL url, String body) {
		try {

			URLConnection con = url.openConnection();
			HttpURLConnection http = (HttpURLConnection) con;
			con.setRequestProperty("Content-Type", "application/json");
			http.setRequestMethod("POST"); // PUT is another valid option

			con.setDoOutput(true);
			OutputStream os = http.getOutputStream();
			OutputStreamWriter osw = new OutputStreamWriter(os, "UTF-8");
			osw.write(body);
			osw.flush();
			osw.close();

			http.connect();

			BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
			return br.readLine();

		} catch (Exception e) {
			return "Ocorreu um erro na tentativa de conexão";
		}
	}
}
