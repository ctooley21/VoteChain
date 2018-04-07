package com.ultimate.votechain.data;

public class Vote
{
    private String position;
    private String candidate;

    public Vote(String position, String candidate)
    {
        setPosition(position);
        setCandidate(candidate);
    }

    private void setPosition(String newPosition)
    {
        position = newPosition;
    }

    private void setCandidate(String newCandidate)
    {
        candidate = newCandidate;
    }

    public String getPosition()
    {
        return position;
    }

    public String getCandidate()
    {
        return candidate;
    }
}