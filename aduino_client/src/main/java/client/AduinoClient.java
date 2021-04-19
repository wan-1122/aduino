package client;

import java.awt.Robot;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;

import org.apache.commons.io.IOUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

import net.sf.json.JSONObject;

public class AduinoClient {
	private int port;
	
	public boolean send(String msg) throws Exception {
		if (this.port <= 0) {
			throw new Exception("port is empty");
		}
		try (Socket socket = new Socket("localhost", this.port);
				OutputStream output = socket.getOutputStream();
				InputStream input = socket.getInputStream();
				PrintWriter writer = new PrintWriter(output, true);
				BufferedReader reader = new BufferedReader(new InputStreamReader(input));) {
			
			writer.println(msg);
			String line = reader.readLine();
			System.out.println("receive msg : " + line);
		} catch (UnknownHostException ex) {
			System.out.println("Server not found: " + ex.getMessage());
		} catch (IOException ex) {
			System.out.println("I/O error: " + ex.getMessage());
		}
		return true;
	}
	
	public void doAction() throws Exception {
		Robot bot = new Robot();
		InputStream is = this.getClass().getClassLoader().getResourceAsStream("aduino_config.xml");
		
		Document doc = Jsoup.parse(IOUtils.toString(is), "", Parser.xmlParser());

		this.port = Integer.parseInt(doc.select("port").text().trim());

		Elements commands = doc.select("command");

		boolean isFirst = true;
		String[] items = new String[commands.size()];
		for (int i = 0; i < commands.size(); i++) {
			items[i] = commands.get(i).text().trim();
			if (items[i] == null) {
				continue;
			}
			if (items[i].startsWith("keypress") && isFirst) {
				items[i] = String.format(items[i], "test");
				isFirst = false;
			}
		}
		JSONObject jsonObject = new JSONObject();
		jsonObject.put("commands", items);
		
		String[] positions = items[0].split(" ");
		if (positions[0].startsWith("click")) {
			bot.mouseMove(Integer.parseInt(positions[1]), Integer.parseInt(positions[2]));	
		}
		
		if (send(jsonObject.toString())) {
		} else {
		}
	}

	public static void main(String[] args) {
		try {
			new AduinoClient().doAction();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
