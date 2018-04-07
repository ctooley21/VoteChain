package com.ultimate.votechain.data;

import com.ultimate.votechain.util.StringUtil;

import java.util.ArrayList;
import java.util.Date;

public class Block {

    public String hash;
    public String previousHash;
    public String merkleRoot;
    public ArrayList<Transaction> transactions = new ArrayList<>();
    public long timeStamp;
    public int nonce;

    public Block(String previousHash )
    {
        this.previousHash = previousHash;
        this.timeStamp = new Date().getTime();
        this.hash = calculateHash();
    }

    public String getHash()
    {
        return hash;
    }

    public String getPreviousHash()
    {
        return previousHash;
    }

    public long getTimeStamp()
    {
        return timeStamp;
    }

    public String calculateHash() {
        return StringUtil.applySha256(
                previousHash +
                        Long.toString(timeStamp) +
                        Integer.toString(nonce) +
                        merkleRoot
        );
    }

    public void mineBlock(int difficulty)
    {
        String target = new String(new char[difficulty]).replace('\0', '0');
        while(!hash.substring(0, difficulty).equals(target))
        {
            nonce++;
            hash = calculateHash();
        }

        System.out.println("Block Mined: " + hash);
    }

    public boolean addTransaction(Transaction transaction)
    {
        if(transaction == null)
        {
            return false;
        }

        if((!previousHash.equalsIgnoreCase("0")))
        {
            if((!transaction.processTransaction()))
            {
                System.out.println("Transaction failed to process. Discarded.");
                return false;
            }
        }

        transactions.add(transaction);
        System.out.println("Transaction Successfully added to Block");
        return true;
    }
}