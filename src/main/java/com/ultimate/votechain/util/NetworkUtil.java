package com.ultimate.votechain.util;

import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class NetworkUtil
{
    public static String getLocalIP()
    {
        String result = "";

        try
        {
            InetAddress inetAddress = InetAddress.getLocalHost();
            result = inetAddress.getHostAddress();
        }
        catch (UnknownHostException e)
        {
            e.printStackTrace();
        }

        return result;
    }

    public static String getSocketIP(Socket socket)
    {
        String result = "";

        try
        {
            InetAddress inetAddress = socket.getInetAddress();
            result = inetAddress.getHostAddress();
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

        return result;
    }
}
