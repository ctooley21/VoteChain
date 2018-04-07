package com.ultimate.votechain.data;

import com.ultimate.votechain.util.StringUtil;

import java.security.PrivateKey;
import java.security.PublicKey;

public class Transaction
{
    public String transactionId;
    public PublicKey sender;
    public byte[] signature;
    public Vote vote;

    private static int sequence = 0;

    public Transaction(PublicKey from, Vote vote) {
        this.sender = from;
        this.vote = vote;
    }

    private String calulateHash()
    {
        sequence++;
        return StringUtil.applySha256(
                StringUtil.getStringFromKey(sender) + sequence
        );
    }

    public void generateSignature(PrivateKey privateKey) {
        String data = StringUtil.getStringFromKey(sender);
        signature = StringUtil.applyECDSASig(privateKey,data);
    }

    public boolean verifiySignature() {
        String data = StringUtil.getStringFromKey(sender);
        return StringUtil.verifyECDSASig(sender, data, signature);
    }

    public boolean processTransaction()
    {
        if(!verifiySignature())
        {
            System.out.println("#Transaction Signature failed to verify");
            return false;
        }

        transactionId = calulateHash();
        return true;
    }
}
