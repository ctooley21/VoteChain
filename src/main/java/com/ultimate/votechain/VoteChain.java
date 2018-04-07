package com.ultimate.votechain;

import com.ultimate.votechain.data.Block;
import com.ultimate.votechain.data.Transaction;
import com.ultimate.votechain.data.Wallet;
import org.bouncycastle.jce.provider.BouncyCastleProvider;

import java.security.Security;
import java.util.ArrayList;

public class VoteChain
{

    public static ArrayList<Block> chain = new ArrayList<>();

    public static int difficulty = 3;
    public static Wallet walletA;
    public static Wallet walletB;
    public static Transaction genesisTransaction;

    public static void main(String[] args)
    {
        Security.addProvider(new BouncyCastleProvider());

        walletA = new Wallet();
        walletB = new Wallet();
        Wallet coinbase = new Wallet();

        genesisTransaction = new Transaction(coinbase.publicKey, null);
        genesisTransaction.generateSignature(coinbase.privateKey);
        genesisTransaction.transactionId = "0";

        System.out.println("Creating and Mining Genesis block... ");
        Block genesis = new Block("0");
        genesis.addTransaction(genesisTransaction);
        addBlock(genesis);

        Block block1 = new Block(genesis.hash);
        addBlock(block1);

        Block block2 = new Block(block1.hash);
        addBlock(block2);

        Block block3 = new Block(block2.hash);
        addBlock(block3);

        isChainValid();
    }

    public static Boolean isChainValid()
    {
        Block currentBlock;
        Block previousBlock;
        String hashTarget = new String(new char[difficulty]).replace('\0', '0');

        for(int i=1; i < chain.size(); i++)
        {
            currentBlock = chain.get(i);
            previousBlock = chain.get(i-1);

            if(!currentBlock.hash.equals(currentBlock.calculateHash()))
            {
                System.out.println("#Current Hashes not equal");
                return false;
            }

            if(!previousBlock.hash.equals(currentBlock.previousHash))
            {
                System.out.println("#Previous Hashes not equal");
                return false;
            }

            if(!currentBlock.hash.substring(0, difficulty).equals(hashTarget))
            {
                System.out.println("#This block hasn't been mined");
                return false;
            }


            for(int j = 0; j <currentBlock.transactions.size(); j++)
            {
                Transaction currentTransaction = currentBlock.transactions.get(j);

                if(!currentTransaction.verifySignature())
                {
                    System.out.println("#Signature on Transaction(" + j + ") is Invalid");
                    return false;
                }
            }

        }
        System.out.println("Blockchain is valid");
        return true;
    }

    public static void addBlock(Block newBlock)
    {
        newBlock.mineBlock(difficulty);
        chain.add(newBlock);
    }

    public static ArrayList<Block> getChain()
    {
        return chain;
    }
}