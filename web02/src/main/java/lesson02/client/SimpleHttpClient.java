package lesson02.client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class SimpleHttpClient {
	
	public static void main(String[] args) throws Exception {
		// 1. 소켓 및 입출력 스트림 준비
		Socket socket = new Socket("www.daum.net", 80);
		BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		PrintStream out = new PrintStream(socket.getOutputStream());
		
		
		// 2. 요청라인 출력
		out.println("GET / HTTP/1.1");
		
		// 3. 헤더정보 출력
		out.println("Host: www.daum.net");
		out.println("User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_0)"
				+ " AppleWebKit/537.36 (KHTML, like Gecko)"
				+ " Chrome/30.0.1599.101 Safari/537.36");
		
		// 4. 공백라인 출력
		out.println();
		
		// 5. 응답내용 출력
		String line = null;
		while ((line = in.readLine()) != null) {
			System.out.println(line);
		}
		
		in.close();
		out.close();
		socket.close();
	}
}
