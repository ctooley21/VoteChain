package com.ultimate.votechain.data;

import com.ultimate.votechain.VoteChain;
import com.ultimate.votechain.util.StringUtil;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Block {

    public String hash;
    public String previousHash;
    private String merkleRoot;
    private List<Transaction> transactions = new ArrayList<>();
    private long timeStamp;
    private int nonce;

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

    public String getMerkleRoot()
    {
        return merkleRoot;
    }

    public long getTimeStamp()
    {
        return timeStamp;
    }

    public List<Transaction> getTransactions()
    {
        return transactions;
    }

    public String calculateHash()
    {
        return StringUtil.applySha256(previousHash + Long.toString(timeStamp) + Integer.toString(nonce) + merkleRoot);
    }

    public void mineBlock() {
        merkleRoot = StringUtil.getMerkleRoot(transactions);
        String target = StringUtil.getDificultyString(VoteChain.getDifficulty());

        while(!hash.substring(0, VoteChain.getDifficulty()).equals(target))
        {
            nonce ++;
            hash = calculateHash();
        }
    }

    public void addTransaction(Transaction transaction)
    {
        if(transaction == null)
        {
            return;
        }

        if((!previousHash.equalsIgnoreCase("0")))
        {
            if((!transaction.processTransaction()))
            {
                return;
            }
        }

        transactions.add(transaction);

        if(transactions.size() >= 5)
        {
            mineBlock();
        }
    }
}