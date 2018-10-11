package client;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.TimezoneDTO;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class CalendarRestClient {
	public static void main(String[] args) {
		URL formatUrl;
		URL formatTimezoneUrl;
		URL getTimezones;
		try {
			formatUrl = new URL("http://localhost:8080/rest_glassfish_war_exploded/time/formatted_date");
			formatTimezoneUrl = new URL("http://localhost:8080/rest_glassfish_war_exploded/time/timezone_formatted_date");
			getTimezones = new URL("http://localhost:8080/rest_glassfish_war_exploded/time/timezones");
		} catch (MalformedURLException e) {
			return;
		}

		String input;
		Scanner scanner = new Scanner(System.in);
		ObjectMapper mapper = new ObjectMapper();

		while (true) {
			System.out.println("1 para consulta por formato, 2 para consulta por formato e local, 3 para zonas, outro para sair");
			input = scanner.nextLine();
			if (input.equals("1")) {
				System.out.println("Insira o formato: ");
				String format;
				format = scanner.nextLine();
				System.out.println(makeRequest(formatUrl, "{ \"format\": \""+ format + "\" }", "POST"));

			} else if (input.equals("2")) {
				System.out.println("Insira o formato: ");
				String format;
				format = scanner.nextLine();

				System.out.println("Insira a zona: ");
				String timezone;
				timezone = scanner.nextLine();

				System.out.println(makeRequest(formatTimezoneUrl,
						"{ \"format\": \""+ format + "\", \"timezone\": \""+ timezone + "\"}",
						"POST"));
			} else if (input.equals("3")) {
				String zonesJson = makeRequest(getTimezones, null,"GET");
				TimezoneDTO timezoneDTO;
				try {
					timezoneDTO = mapper.readValue(zonesJson, TimezoneDTO.class);
				} catch (IOException e) {
					System.out.println("Erro inesperado na conversão da resposta.");
					continue;
				}

				System.out.println("Timezones disponíveis:");
				for (String str: timezoneDTO.getTimezones()) {
					System.out.println(str);
				}
			} else {
				break;
			}
		}

		System.out.println("Até mais");
	}

	private static String makeRequest(URL url, String body, String method) {
		try {

			URLConnection con = url.openConnection();
			HttpURLConnection http = (HttpURLConnection) con;
			con.setRequestProperty("Content-Type", "application/json");
			http.setRequestMethod(method); // PUT is another valid option

			if (body != null) {
				con.setDoOutput(true);
				OutputStream os = http.getOutputStream();
				OutputStreamWriter osw = new OutputStreamWriter(os, StandardCharsets.UTF_8);
				osw.write(body);
				osw.flush();
				osw.close();
			}

			http.connect();

			BufferedReader br;

			if (http.getResponseCode() == 200) {
				br = new BufferedReader(new InputStreamReader(con.getInputStream(), StandardCharsets.UTF_8));
			} else {{
				br = new BufferedReader(new InputStreamReader(http.getErrorStream(), StandardCharsets.UTF_8));
			}}

			String temp, res = "";
			while ((temp = br.readLine()) != null) {
				res += temp + "\n";
			}
			return res;


		} catch (Exception e) {
			e.printStackTrace();
			return "Ocorreu um erro na tentativa de conexão";
		}
	}
}
