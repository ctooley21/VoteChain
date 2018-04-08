package com.ultimate.votechain.data;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class InitializeNetwork{
	//Bryan 10.48.238.98 9001
	//Bryan2 10.47.29.179
	
	static ServerSocket HOSTSOCK = null;	
    static Socket clientSock = null;
    static ServerThread threadInstance = null;
    	//Can only be one instance of a port per thread
    
    String line;
    Scanner is;
    PrintStream os;
    
    private InitializeNetwork() {
    	Initalize();
    }
    
    private static void Initalize() {

	    try {
	    	HOSTSOCK = new ServerSocket(9001);
	    }catch(IOException e) {
	    	System.out.println("Server Error");
	    	e.printStackTrace();
	    }
	    System.out.println("Server Listening on " + HOSTSOCK +"....");
	    
	    while(true) {
	    	try {
	    		clientSock = HOSTSOCK.accept();
	    		System.out.println("Connection Established with "+clientSock+"!");
	    		threadInstance = new ServerThread();
	    		threadInstance.start();
	    		threadInstance.setClientThread(clientSock);
	    	}catch(Exception e) {
	    		
	    	}
	    }
	}
    public static class ServerThread extends Thread{
    	String inpt = null;
    	Scanner inSocket = null;
    	Scanner outSocket = null;
    	Socket tClientSocket = null;
    	
		private ServerThread() {
			
		}
		private void setClientThread(Socket clientSock) {
			tClientSocket = clientSock;
		}
		public void run() {
			//Must be public as is apart of thread
			
			try {
				inSocket = new Scanner(new InputStreamReader(tClientSocket.getInputStream()));
				outSocket = new Scanner((Readable) tClientSocket.getOutputStream());
					//Kind of janky might need to change to PrintWriter
			}catch(IOException e){
				inpt=inSocket.nextLine();
					//Might need to change line to just next
				System.out.println("Error on client port");
			}	
		}
    }
}

