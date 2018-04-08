package com.ultimate.votechain;

import com.ultimate.votechain.data.Block;
import com.ultimate.votechain.data.GUI_Login;
import com.ultimate.votechain.data.Transaction;
import org.bouncycastle.jce.provider.BouncyCastleProvider;


import java.security.Security;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.swing.*;
import java.awt.*;
//GUI import

public class VoteChain
{

    private static List<Block> chain = new ArrayList<>();
    private static int difficulty = 1;

    private static HashMap<String, List<String>> electionData = new HashMap<>();

    public static void main(String[] args)
    {
    	guiStart();
    	
        Security.addProvider(new BouncyCastleProvider());
        Block genesis = new Block("0");
        addBlock(genesis);

        isChainValid();
    }
    
    private static void guiStart() {
    	GUI_Login login = new GUI_Login();       
    }

    private static Boolean isChainValid()
    {
        Block currentBlock;
        Block previousBlock;
        String hashTarget = new String(new char[difficulty]).replace('\0', '0');

        for(int i = 1; i < chain.size(); i++)
        {
            currentBlock = chain.get(i);
            previousBlock = chain.get(i - 1);

            if(!currentBlock.hash.equals(currentBlock.calculateHash()))
            {
                return false;
            }

            if(!previousBlock.hash.equals(currentBlock.previousHash))
            {
                return false;
            }

            if(!currentBlock.hash.substring(0, difficulty).equals(hashTarget))
            {
                return false;
            }


            for(int j = 0; j <currentBlock.getTransactions().size(); j++)
            {
                Transaction currentTransaction = currentBlock.getTransactions().get(j);
                if(!currentTransaction.verifySignature())
                {
                    return false;
                }
            }
        }

        System.out.println("Blockchain is valid");
        return true;
    }

    private static void addBlock(Block newBlock)
    {
        newBlock.mineBlock();
        chain.add(newBlock);
    }

    public static List<Block> getChain()
    {
        return chain;
    }

    public static int getDifficulty()
    {
        return difficulty;
    }
}