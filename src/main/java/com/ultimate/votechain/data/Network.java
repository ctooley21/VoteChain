package com.ultimate.votechain.data;

import com.ultimate.votechain.util.NetworkUtil;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Network
{

    private boolean isCandidate;
    private boolean isLeader;

    private static List<String> nodes;

    public Network()
    {
        isCandidate = false;

        nodes = new ArrayList<>();
        nodes.add("10.48.209.42");
        nodes.add("10.47.161.22");
        nodes.add("10.48.238.98");
        nodes.add("10.47.29.179");

        isLeader = networkHasLeader();
        System.out.println(isLeader);
    }

    private static boolean networkHasLeader()
    {
        for(String node : nodes)
        {
            if(node.equalsIgnoreCase(NetworkUtil.getLocalIP()))
            {
                continue;
            }
            try
            {
                new Socket().connect(new InetSocketAddress(node, 9001), 1000);
                System.out.println(node + " is on the network.");
                return false;
            }
            catch (Exception e)
            {
                System.out.println(node + " is not on the network.");
            }
        }
        return true;
    }

    public static void startElection()
    {
        boolean isCandidate = false;
        int ms = (int) (Math.random() * (320 - 160)) + 160;

        Timer timer = new Timer(ms, new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent arg)
            {

            }
        });
        timer.setRepeats(false);
        timer.start();
    }
}
