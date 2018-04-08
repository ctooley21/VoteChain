package com.ultimate.votechain.data;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Network
{

    private boolean isCandidate;
    private boolean isLeader;

    private List<String> nodes;

    public Network()
    {
        isCandidate = false;
        isLeader = networkHasLeader();
    }

    private static boolean networkHasLeader()
    {
        return false;
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
