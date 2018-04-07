package com.ultimate.votechain.util;

import com.ultimate.votechain.VoteChain;
import com.ultimate.votechain.data.Block;
import com.ultimate.votechain.data.Transaction;
import com.ultimate.votechain.data.Vote;

import java.util.HashMap;
import java.util.List;

public class VoteUtil
{

    public HashMap<String, HashMap<String, Integer>> getFinalVote()
    {
        HashMap<String, HashMap<String, Integer>> finalVotes = new HashMap<>();

        for(Block block : VoteChain.getChain())
        {
            List<Transaction> transactions = block.getTransactions();

            for(Transaction transaction : transactions)
            {
                for(Vote vote : transaction.getVotes())
                {
                    String candidate = vote.getCandidate();
                    String position = vote.getPosition();

                    finalVotes.computeIfAbsent(position, pos -> finalVotes.put(pos, new HashMap<>()));

                    HashMap<String, Integer> candidates = finalVotes.get(position);

                    if(candidates.containsKey(candidate))
                    {
                        candidates.replace(candidate, candidates.get(candidate) + 1);
                    }
                    else
                    {
                        candidates.put(candidate, 1);
                    }
                }
            }
        }
        return finalVotes;
    }
}
