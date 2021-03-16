import java.util.*;

public class WordFreqInfo {
    private String word;
    private int occurCount;
    private ArrayList<Frequency> followList;

    public WordFreqInfo(String word, int count) {
        this.word = word;
        this.occurCount = count;
        this.followList = new ArrayList<Frequency>();
    }

    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Word :" + word+":");
        sb.append(" (" + occurCount + ") : ");
        for (Frequency f : followList) {
            sb.append(f.toString());
        }

        return sb.toString();
    }

    public void updateFollows(String follow) {
        this.occurCount++;
        boolean updated = false;
        for (Frequency f : followList) {
            if (follow.compareTo(f.follow) == 0) {
                f.followCount++;
                updated = true;
            }
        }
        if (!updated) {
            followList.add(new Frequency(follow, 1));
        }
    }

    public int getOccurCount() {
        return this.occurCount;
    }

    public String getFollowWord(int count) {
        ArrayList<String> followWordList = new ArrayList<>();
        for (int i = 0; i < followList.size(); i++) {
            Frequency frequency = followList.get(i);
            for (int j = 0; j < frequency.getFollowCount(); j++) {
                followWordList.add(frequency.getWord());
            }
        }
        return followWordList.get(count);
    }

    private class Frequency {
        String follow;
        int followCount;

        public Frequency(String follow, int ct) {
            this.follow = follow;
            this.followCount = ct;
        }

        @Override
        public String toString() {
            return follow + " [" + followCount + "] ";
        }

        @Override
        public boolean equals(Object f2) {
            return this.follow.equals(((Frequency)f2).follow);
        }

        public String getWord() {
            return this.follow;
        }

        public int getFollowCount() {
            return this.followCount;
        }
    }
}
