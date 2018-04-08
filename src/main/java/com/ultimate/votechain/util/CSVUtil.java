package com.ultimate.votechain.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class CSVUtil
{
    public static HashMap<String, Integer> readVoterData(String path)
    {
        HashMap<String, Integer> map = new HashMap<>();
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(path)))
        {
            while ((line = br.readLine()) != null)
            {
                String[] data = line.replace("\"", "").split(",");
                String party = data[1];

                if(!map.containsKey(party))
                {
                    map.put(party, 1);
                }
                else
                {
                    map.replace(party, map.get(party) + 1);
                }
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return map;
    }

    public static HashMap<String, List<String>> readElectionData(String path)
    {
        HashMap<String, List<String>> map = new HashMap<>();
        String line;
        try (BufferedReader br = new BufferedReader(new FileReader(path)))
        {
            while ((line = br.readLine()) != null)
            {
                String[] data = line.replace("\"", "").split(",");
                String position = data[0];
                String candidate = data[1];
                List<String> candidates = Arrays.asList(candidate.split("|"));

                map.put(position, candidates);
            }
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        return map;
    }
}
