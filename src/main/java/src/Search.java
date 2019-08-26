package src;

import java.util.ArrayList;

public class Search
{
    private String match;
    private ArrayList<int[]> positions;

    public Search()
    {
    }

    private ArrayList<int[]> findMatch(String text)
    {
        // Split the text into paragraphs, create an array list to store all results (setting caret position is done by
        // paragraph number [0], and char offset [1])
        String[] paragraphs = text.split("\n");
        ArrayList<int[]> found = new ArrayList<>();

        int i = 0;
        while (i < paragraphs.length)
        {
            int substringPos = paragraphs[i].indexOf(match);
            // If a result is found create an array of paragraph number and start of substring
            if (substringPos > -1)
            {
                int[] paraOffset = new int[]{i, substringPos};
                found.add(paraOffset);
            }
            i++;
        }
        return found;
    }

    public int[] nextPosition(int[] pos)
    {
        int[] next = null;
        if (positions == null || positions.size() == 0) return null;

        // Find the next match after the current pos, can break as the positions list is ordered
        for (int[] p : positions)
        {
            System.out.println(pos[0] + " " + p[0]);
            if (p[0] == pos[0] && p[1] >= pos[1])
            {
                next = p;
                break;
            } else if (p[0] > pos[0])
            {
                next = p;
                break;
            }
        }

        // Wrap around to first result if there were no results after current pos
        if (next == null) next = positions.get(0);
        return new int[]{next[0], next[1], match.length()};
    }

    public void updateMatch(String match, String text)
    {
        // Update the match and find matches
        this.match = match;
        positions = findMatch(text);
    }

    public int getResultNum()
    {
        return positions.size();
    }
}
