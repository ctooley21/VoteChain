package com.ultimate.votechain.data;

import com.ultimate.votechain.util.StringUtil;

import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Date;
import java.util.List;

public class Transaction
{
    public String transactionId;
    public PublicKey sender;
    public byte[] signature;
    public List<Vote> votes;
    public long timestamp;
    private static int sequence = 0;

    public Transaction(PublicKey from, List<Vote> votes) {
        this.sender = from;
        this.votes = votes;
        this.timestamp = new Date().getTime();
    }

    private String calculateHash()
    {
        sequence++;
        return StringUtil.applySha256(StringUtil.getStringFromKey(sender) + sequence);
    }

    public void generateSignature(PrivateKey privateKey) {
        String data = StringUtil.getStringFromKey(sender);
        signature = StringUtil.applyECDSASig(privateKey, data);
    }

    public boolean verifySignature() {
        String data = StringUtil.getStringFromKey(sender);
        return StringUtil.verifyECDSASig(sender, data, signature);
    }

    public boolean processTransaction()
    {
        if(!verifySignature())
        {
            System.out.println("#Transaction Signature failed to verify");
            return false;
        }

        transactionId = calculateHash();
        return true;
    }

    public List<Vote> getVotes()
    {
        return votes;
    }
}
