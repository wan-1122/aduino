package server;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Date;

import gnu.io.CommPort;
import gnu.io.CommPortIdentifier;
import gnu.io.SerialPort;

public class AduinoServer {
	public AduinoServer() {
		super();
	}

	void connect(String portName) throws Exception {
		CommPortIdentifier portIdentifier = CommPortIdentifier.getPortIdentifier(portName);
		if (portIdentifier.isCurrentlyOwned()) {
			System.out.println("Error: Port is currently in use");
		} else {
			// 클래스 이름을 식별자로 사용하여 포트 오픈
			CommPort commPort = portIdentifier.open(this.getClass().getName(), 2000);

			if (commPort instanceof SerialPort) {
				// 포트 설정(통신속도 설정. 기본 9600으로 사용)
				SerialPort serialPort = (SerialPort) commPort;
				serialPort.setSerialPortParams(9600, SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
						SerialPort.PARITY_NONE);

				// Input,OutputStream 버퍼 생성 후 오픈
				InputStream serialIn = serialPort.getInputStream();
				OutputStream serialOut = serialPort.getOutputStream();

				int port = 39600;
				try (ServerSocket serverSocket = new ServerSocket(port)) {
					System.out.println("Server is listening on port " + port);
					while (true) {
						Socket socket = serverSocket.accept();
						
						System.out.println("[ " + socket.getInetAddress() + " ] client connected");
						OutputStream output = socket.getOutputStream();
						PrintWriter pw = new PrintWriter(output);
						
						InputStream input = socket.getInputStream();
						BufferedReader reader = new BufferedReader(new InputStreamReader(input));
						String msg = reader.readLine();						
						serialOut.write(msg.getBytes("utf-8"));

						long st1 = System.currentTimeMillis();
						System.out.println("------------start------------" + new Date());
						System.out.println("send msg: " + msg);

						try {
							while (serialIn.available() < 1) {
								Thread.sleep(10);
							}
							BufferedReader reader2 = new BufferedReader(new InputStreamReader(serialIn));
							String serialMsg = reader2.readLine();
							System.out.println("received serial msg : " + serialMsg);

							pw.println(serialMsg);
							pw.flush();
						} catch (IOException e) {
							e.printStackTrace();
						}
						long ed1 = System.currentTimeMillis();
						System.out.println("------------end------------" + (ed1 - st1) + " m/s");
					}
				} catch (IOException ex) {
					System.out.println("Server exception: " + ex.getMessage());
					ex.printStackTrace();
				}
			} else {
				System.out.println("Error: Only serial ports are handled by this example.");
			}
		}
	}

	/** */
	// 데이터 수신
	public static class SerialReader implements Runnable {
		InputStream in;

		public SerialReader(InputStream in) {
			this.in = in;
		}

		public void run() {
			byte[] buffer = new byte[1024];
			int len = -1;
			try {
				while ((len = this.in.read(buffer)) > -1) {
					System.out.print(new String(buffer, 0, len));
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	public static void main(String[] args) {
		try {
			new AduinoServer().connect("COM9"); // 입력한 포트로 연결
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
