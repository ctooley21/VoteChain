package com.ultimate.votechain.data;

import java.security.*;
import java.security.spec.ECGenParameterSpec;
import java.util.List;

public class Wallet
{
    private PrivateKey privateKey;
    private PublicKey publicKey;
    private String district;

    public Wallet(String district)
    {
        generateKeyPair();
        this.district = district;
    }

    public Wallet()
    {
        generateKeyPair();
        district = "CA-01";
    }

    public PrivateKey getPrivateKey()
    {
        return privateKey;
    }

    public PublicKey getPublicKey()
    {
        return publicKey;
    }

    public String getDistrict()
    {
        return district;
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

    public Transaction sendVote(List<Vote> votes)
    {
        Transaction newTransaction = new Transaction(publicKey, votes);
        newTransaction.generateSignature(privateKey);
        return newTransaction;
    }
}
