package com.ultimate.votechain.data;

import com.ultimate.votechain.util.NetworkUtil;
import sun.nio.ch.Net;

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class InitializeNetwork{
	Socket s;
	ServerSocket ss;
    public InitializeNetwork() {
    	 try {
    		 ss = new ServerSocket(1234);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}    
    }
    public void clientRequest() {
    	 while (true) 
         {
             // Accept the incoming request
             s = ss.accept();
  
             System.out.println("New client request received : " + s);
              
             // obtain input and output streams
             DataInputStream dis = new DataInputStream(s.getInputStream());
             DataOutputStream dos = new DataOutputStream(s.getOutputStream());
              
             System.out.println("Creating a new handler for this client...");
  
             // Create a new handler object for handling this request.
             ClientHandler mtch = new ClientHandler(s,"client ", dis, dos);
  
             // Create a new Thread with this object.
             Thread t = new Thread(mtch);

             // start the thread.
             t.start();
  
         }
     }
    class ClientHandler implements Runnable {
        Scanner scn;
        private String name;
        final DataInputStream dis;
        final DataOutputStream dos;
        Socket s;
        boolean isloggedin;
        
    	public ClientHandler(Socket s, String name, DataInputStream dis, DataOutputStream dos) {
    		this.dis = dis;
    		this.dos = dos;
    		this.name = name;
    		this.s = s;
    		this.isloggedin=true;
    		this.scn = new Scanner(System.in);
    	}
    

	@Override
	public void run() {
        String received;
        ClientHandler mc;
        while (true) 
        {
            try
            {
                // receive the string
                received = dis.readUTF();
                 
                System.out.println(received);

                dos.writeUTF("v");
                        break;
                } catch (IOException e) {
                 
                e.printStackTrace();
            }
             
        }
        try
        {
            // closing resources
            this.dis.close();
            this.dos.close();
             
        }catch(IOException e){
            e.printStackTrace();
        }
      }	
	}
}