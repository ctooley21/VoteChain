package com.ultimate.votechain.data;

import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.util.ArrayList;

public class Wallet
{
    public PrivateKey privateKey;
    public PublicKey publicKey;
    public ArrayList<Vote> votes = new ArrayList<>();

    public Wallet()
    {
        generateKeyPair();
    }

    private void generateKeyPair()
    {
        try
        {
            KeyPairGenerator keyGen = KeyPairGenerator.getInstance("ECDSA","BC");
            SecureRandom random = SecureRandom.getInstance("SHA1PRNG");
            ECGenParameterSpec ecSpec = new ECGenParameterSpec("prime192v1");

            keyGen.initialize(ecSpec, random);
            KeyPair keyPair = keyGen.generateKeyPair();

            privateKey = keyPair.getPrivate();
            publicKey = keyPair.getPublic();
        }
        catch(Exception e)
        {
            throw new RuntimeException(e);
        }
    }

    public Transaction sendVote(Vote vote)
    {
        Transaction newTransaction = new Transaction(publicKey, vote);
        newTransaction.generateSignature(privateKey);
        return newTransaction;
    }
}
