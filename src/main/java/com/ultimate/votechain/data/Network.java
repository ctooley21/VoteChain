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
    private static boolean isLeader;

    private static List<String> nodes;
    public static String leader;

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
        List<String> aliveNodes = new ArrayList<>();
        for(String node : nodes)
        {
            /*if(node.equalsIgnoreCase(NetworkUtil.getLocalIP()))
            {
                continue;
            }
            */
            try
            {
                new Socket().connect(new InetSocketAddress(node, 9001), 250);
                System.out.println(node + " is on the network.");
                aliveNodes.add(node);
            }
            catch (Exception e)
            {
                System.out.println(node + " is not on the network.");
            }
        }

        findLeader(aliveNodes);
        return aliveNodes.isEmpty();
    }

    private static void findLeader(List<String> nodes)
    {
        for(String node : nodes)
        {
            System.out.println("testing!");
            InitializeNetwork.sendMessage(node, 9001, "?");
        }
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

    public static void startHeartBeat()
    {
        Timer timer = new Timer(333, new ActionListener()
        {
            @Override
            public void actionPerformed(ActionEvent arg)
            {

            }
        });
        timer.setRepeats(false);
        timer.start();
    }

    public static boolean isLeader()
    {
        return isLeader;
    }
}
