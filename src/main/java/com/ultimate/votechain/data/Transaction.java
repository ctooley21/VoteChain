package com.ultimate.votechain.data;

import com.ultimate.votechain.VoteChain;
import com.ultimate.votechain.util.StringUtil;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;
import java.util.List;

public class Transaction
{
    public String transactionID;
    public String hash;
    public String previousHash;
    private PublicKey sender;
    private byte[] signature;
    private List<Vote> votes;
    private long timestamp;
    private static int sequence = 0;

    public Transaction(PublicKey from, List<Vote> votes)
    {
        if(VoteChain.getChain().isEmpty())
        {
            setPreviousHash("0");
        }
        else
        {
            setPreviousHash(VoteChain.getChain().get(VoteChain.getChain().size() - 1).getHash());
        }
        this.sender = from;
        this.votes = votes;
        this.timestamp = new Date().getTime();
        this.hash = calculateHash();
    }

    public void setPreviousHash(String previousHash)
    {
        this.previousHash = previousHash;
    }

    public String getPreviousHash()
    {
        return previousHash;
    }

    public String getHash()
    {
        return hash;
    }

    public String calculateHash()
    {
        sequence++;
        return StringUtil.applySha256(previousHash + Long.toString(timestamp));
    }

    public void generateSignature(PrivateKey privateKey)
    {
        String data = StringUtil.getStringFromKey(sender);
        signature = StringUtil.applyECDSASig(privateKey, data);
    }

    public boolean verifySignature()
    {
        String data = StringUtil.getStringFromKey(sender);
        return StringUtil.verifyECDSASig(sender, data, signature);
    }

    public boolean processTransaction()
    {
        if(!verifySignature())
        {
            return false;
        }

        transactionID = calculateHash();
        return true;
    }

    public List<Vote> getVotes()
    {
        return votes;
    }
}
