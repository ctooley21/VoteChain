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
    
    static LeaderMode leader = null;
    static int numOfPeers;
    
    public InitializeNetwork() {
    	//Constructor
    	leader = new LeaderMode();
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
            //System.out.println("test1");
	    	try
            {	
	    		//Attempting to connect to Socket
	    			// System.out.println("test2");
	    		clientSock = HOSTSOCK.accept();
	    		System.out.println("Connection Established with " + clientSock + "!");
	    		threadInstance = new ServerThread();
	    		threadInstance.start();
	    		threadInstance.setClientThread(clientSock);

	    		Scanner scanner = new Scanner(clientSock.getInputStream());

	    		while(scanner.hasNext())
	    			//Waiting for serial input from peers
                {
                    String input = scanner.next();
                    if(input.equalsIgnoreCase("?"))
                    	//Asking if this node is leader
                    {
                        System.out.println("test");
                        String response = Network.isLeader() ? "Y" : "N";
                        sendMessage(NetworkUtil.getSocketIP(clientSock), 9001, response);
                        System.out.println("Leader Request Received");
                    }else if(scanner.next().equalsIgnoreCase("v")) {
                    	//Leader vote requested
                    	
                    	//Call Vote Class
                    	System.out.println("Vote Query Recieved");
                    }
                    else if(input.equalsIgnoreCase("Y")){
                    	//Yes vote recieved (In response to voting request)
                    	
                        System.out.println("Yes Vote Recieved");
                        Network.leader = NetworkUtil.getSocketIP(clientSock);
                    }else if(input.equalsIgnoreCase("D")) {
                    	
                    }else {
                    	//Heartbeat Recieved
                    	if (Network.isLeader() == true) {
	                    	System.out.println("Heartbeat Recieved " + Integer.parseInt(scanner.next()));
	                    	numOfPeers = Integer.parseInt(scanner.next());
	                    	leader.Heartbeat(numOfPeers, NetworkUtil.getSocketIP(clientSock));
                    	}else {
                    		//Reset timer for leadership
                    	}
                    }
                }
	    	}
	    	catch(Exception e)
            {
	    		e.printStackTrace();
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
			//Constructor
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
				System.out.println("Run message");
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

