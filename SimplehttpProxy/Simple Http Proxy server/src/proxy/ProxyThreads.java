package proxy;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.Date;


public class ProxyThreads implements Runnable 
{
	private static final int BUFFER_SIZE = 32768;
	Socket browserSocket;
	
	public ProxyThreads(Socket socket) {
		this.browserSocket = socket;		
	}
	public void run ()
	{
		try {
			DataOutputStream dout;
			BufferedReader din;
			dout = new DataOutputStream(browserSocket.getOutputStream());
			din =new BufferedReader(new InputStreamReader(browserSocket.getInputStream()));
		//while(!din.ready())
		//{
		
		/*try {
			listenForHttp();
		} catch (IOException e) {
			e.printStackTrace();
		}*/
	
	//public void listenForHttp() throws IOException

		String inputLine= din.readLine();
		String urlToCall="";
		System.out.println(inputLine);
		if(inputLine !=null)
		{
				String[] tokens = inputLine.split(" ");
				if(!tokens[0].equals("GET")) return;
				urlToCall=tokens[1];
				System.out.println("here:"+ urlToCall);
				int start=7;
				int end = urlToCall.indexOf("/", start);
				String file=urlToCall.substring(end,urlToCall.length());
				String host = urlToCall.substring(start, end);
				System.out.println("Request for : " + host);
				
				// socket to the Internet
				Socket iSocket;
				DataOutputStream iDout = null;
				BufferedReader iDin = null;
				
					iSocket = new Socket(host,80);
					iDout= new DataOutputStream(iSocket.getOutputStream());
					iDin =new BufferedReader(new InputStreamReader(iSocket.getInputStream()));
				
				System.out.println("file: "+ file);
					dout.writeUTF("GET"+ file + "HTTP/1.1\r\n");
				    dout.writeUTF("Host:" + host + "\r\n\r\n");
					iDout.flush();
				
				
					System.out.println("the response from the internet " + iDin.read());
				
				
				//String response = iDin.readLine();
			
				byte[] by = new byte[ BUFFER_SIZE ];
				//by = response.getBytes("UTF-8");
				Format formt = new SimpleDateFormat("MMM dd, yyyy HH:MM:SS");
				String date =  formt.format(new Date());
				
				int c = 0,s=0;
				while((c= iSocket.getInputStream().read(by))!=-1)
				{
					dout.write(by,0,c);
					s+=c;
				}
				dout.flush();
				String message = date + browserSocket.getInetAddress().toString() + urlToCall + s;
				System.out.println("message:" +message);
				ProxyServer.log(message); 
				
				
					iSocket.close();
					iDin.close();
					iDout.close();
			
		browserSocket.close();
		dout.close();
		din.close();
		}
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	
	}
	
	
}
