package com.ultimate.votechain.util;

import java.net.InetAddress;
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
}
