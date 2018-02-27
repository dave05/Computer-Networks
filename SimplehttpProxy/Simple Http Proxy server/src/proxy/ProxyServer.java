package proxy;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
//import java.net.UnknownHostException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
//import java.util.logging.SimpleFormatter;


public class ProxyServer {
	  ServerSocket s;
	  boolean listening = true;
	  int port = 3333;	
		//ProxyThreads pt;
	public static void main(String[] args) {
		new ProxyServer();
	}
	public ProxyServer()
	{
		System.out.println("Started on: " + port);
		try {
			s = new ServerSocket(port);
			while(listening)
			{
				Socket con = s.accept();
				new Thread(new ProxyThreads(con)).start();
			}
		} catch (IOException e1) {
			
			e1.printStackTrace();
		}
		
	
	
	
try {
	s.close();
} catch (IOException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}	
}
	public static synchronized void log( String message) {
		FileWriter fw = null;
		BufferedWriter bw = null;
		PrintWriter out = null;
		try {
		    fw = new FileWriter("proxylog.txt", true);
		    bw = new BufferedWriter(fw);
		    out = new PrintWriter(bw);
		    out.println(message);
		    out.close();
		} catch (IOException e) {
		    //exception handling left as an exercise for the reader
		}
		finally {
		    if(out != null)
			    out.close();
		    try {
		        if(bw != null)
		            bw.close();
		    } catch (IOException e) {
		        //exception handling left as an exercise for the reader
		    }
		    try {
		        if(fw != null)
		            fw.close();
		    } catch (IOException e) {
		        //exception handling left as an exercise for the reader
		    }
		}
	}
}
