package com.ultimate.votechain.data;

import com.ultimate.votechain.util.NetworkUtil;
import sun.nio.ch.Net;

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
    
    public InitializeNetwork() {
    	Initalize();
    }
    
    private static void Initalize() {

	    try
        {
	    	HOSTSOCK = new ServerSocket(9001);
	    }
	    catch(IOException e)
        {
	    	System.out.println("Server Error");
	    	e.printStackTrace();
	    }
	    System.out.println("Server Listening on " + HOSTSOCK + "....");
	    
	    while(true)
        {
            System.out.println("test1");
	    	try
            {
                System.out.println("test2");
	    		clientSock = HOSTSOCK.accept();
	    		System.out.println("Connection Established with " + clientSock + "!");
	    		threadInstance = new ServerThread();
	    		threadInstance.start();
	    		threadInstance.setClientThread(clientSock);

	    		Scanner scanner = new Scanner(clientSock.getInputStream());

	    		while(scanner.hasNext())
                {
                    String input = scanner.next();
                    if(input.equalsIgnoreCase("?"))
                    {
                        System.out.println("test");
                        String response = Network.isLeader() ? "Y" : "N";
                        sendMessage(NetworkUtil.getSocketIP(clientSock), 9001, response);
                    }
                    else if(input.equalsIgnoreCase("Y"))
                    {
                        System.out.println("test34");
                        Network.leader = NetworkUtil.getSocketIP(clientSock);
                    }
                }
	    	}
	    	catch(Exception e)
            {

	    	}
	    }
	}

    public static class ServerThread extends Thread
    {
    	String inpt = null;
    	Scanner inSocket = null;
    	PrintStream outSocket = null;
    	Socket tClientSocket = null;
    	
		private ServerThread()
        {
			
		}

		private void setClientThread(Socket clientSock)
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
			}catch(IOException e){
				inpt=inSocket.nextLine();
				//Might need to change line to just next
				System.out.println("Error on client port");
			}	
		}
    }

    public static void sendMessage(String node, int port, String message)
    {
        Socket socket;
        DataOutputStream os;

        try
        {
            socket = new Socket(node, port);
        }
        catch (Exception e)
        {
            System.out.println("Error connecting to: " + node + " - " + message);
            return;
        }

        try
        {
            os = new DataOutputStream(socket.getOutputStream());
            os.writeBytes(message);
        }
        catch (Exception e)
        {
            System.out.println("Error sending message: " + message);
        }

        System.out.println("Successfully sent \"" + message + "\" to " + node);
    }
}

