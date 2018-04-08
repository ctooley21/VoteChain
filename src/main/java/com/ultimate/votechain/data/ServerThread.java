package com.ultimate.votechain.data;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

public class ServerThread extends Thread
{
	String inpt = null;
	Scanner inSocket = null;
	PrintStream outSocket = null;
	Socket tClientSocket = null;
	
	ServerThread()
    {
		//Constructor
	}

	void setClientThread(Socket clientSock)
    {
		tClientSocket = clientSock;
	}
	public void run()
    {
		//Must be public as is apart of thread

		try {
			inSocket = new Scanner(new InputStreamReader(tClientSocket.getInputStream()));
			outSocket = new PrintStream(tClientSocket.getOutputStream());
			//Kind of janky might need to change to PrintWriter
			System.out.println("Run message");
		}catch(IOException e){
			inpt=inSocket.nextLine();
			//Might need to change line to just next
			System.out.println("Error on client port");
		}	
    }
}