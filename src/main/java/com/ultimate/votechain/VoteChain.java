package com.ultimate.votechain;

import com.ultimate.votechain.data.GUI_Login;
import com.ultimate.votechain.data.Network;
import com.ultimate.votechain.data.Transaction;
import com.ultimate.votechain.data.Wallet;
import com.ultimate.votechain.util.CSVUtil;
import org.bouncycastle.jce.provider.BouncyCastleProvider;


import java.security.Security;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class VoteChain
{
    private static List<Transaction> chain = new ArrayList<>();
    private static HashMap<String, List<String>> electionData = new HashMap<>();

    public static void main(String[] args)
    {
    	guiStart();
        electionData = CSVUtil.readElectionData("C:\\Users\\drkpr\\Dropbox\\Projects\\VoteChain\\src\\main\\resources\\election_data.csv");

        Security.addProvider(new BouncyCastleProvider());

        Wallet wallet = new Wallet();

        Transaction genesis = new Transaction(wallet.getPublicKey(), new ArrayList<>());
        genesis.generateSignature(wallet.getPrivateKey());

        for(int i = 0; i < 10; i++)
        {
            Transaction transaction = new Transaction(wallet.getPublicKey(), new ArrayList<>());
            transaction.generateSignature(wallet.getPrivateKey());
            chain.add(transaction);
        }

        System.out.println(isChainValid());

        new Network();
    }
    
    private static void guiStart() {
    	GUI_Login login = new GUI_Login();       
    }

    private static Boolean isChainValid()
    {
        Transaction currentTransaction;
        Transaction previousTransaction;

        for(int i = 1; i < chain.size(); i++)
        {
            currentTransaction = chain.get(i);
            previousTransaction = chain.get(i - 1);

            if(!currentTransaction.getHash().equals(currentTransaction.calculateHash()))
            {
                return false;
            }

            if(!previousTransaction.getHash().equals(currentTransaction.getPreviousHash()))
            {
                return false;
            }

            if(!currentTransaction.verifySignature())
            {
                return false;
            }
        }
        System.out.println("Blockchain is valid");
        return true;
    }

    private static void addTransaction(Transaction newTransaction)
    {
        chain.add(newTransaction);
    }

    public static List<Transaction> getChain()
    {
        return chain;
    }
}