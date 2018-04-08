package com.ultimate.votechain;

import com.ultimate.votechain.data.Block;
import com.ultimate.votechain.data.GUI_Login;
import com.ultimate.votechain.data.Transaction;
import com.ultimate.votechain.util.CSVUtil;
import org.bouncycastle.jce.provider.BouncyCastleProvider;


import java.security.Security;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class VoteChain
{

    private static List<Block> chain = new ArrayList<>();
    private static int difficulty = 5;

    private static HashMap<String, List<String>> electionData = new HashMap<>();

    public static void main(String[] args)
    {
    	guiStart();
        electionData = CSVUtil.readElectionData("C:\\Users\\drkpr\\Dropbox\\Projects\\VoteChain\\src\\main\\resources\\election_data.csv");

        Security.addProvider(new BouncyCastleProvider());
        Block genesis = new Block();
        addBlock(genesis);

        for(int i = 0; i < 10; i++)
        {
            addBlock(new Block());
        }

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

            if(!currentBlock.getHash().equals(currentBlock.calculateHash()))
            {
                return false;
            }

            if(!previousBlock.getHash().equals(currentBlock.getPreviousHash()))
            {
                return false;
            }

            if(!currentBlock.getHash().substring(0, difficulty).equals(hashTarget))
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