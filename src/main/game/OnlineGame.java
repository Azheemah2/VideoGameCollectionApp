package main.game;

/**
 * Represents an online game
 */
public class OnlineGame extends AbstractGame {
    private static final long serialVersionUID = 1L;

    private boolean isOnline;
    private int onlineRank;

    public OnlineGame(String title, String genre, String platform, int releaseYear, String developer) {
        super(title, genre, platform, releaseYear, developer);
        this.isOnline = true;
        this.onlineRank = 0;
    }

    public boolean isOnline() {
        return isOnline;
    }

    public void setOnline(boolean online) {
        isOnline = online;
    }

    public int getOnlineRank() {
        return onlineRank;
    }

    public void setOnlineRank(int rank) {
        this.onlineRank = rank;
    }

    /**
     * Update the player's online rank
     *
     * @param progress The player's new rank
     */
    @Override
    public void updateProgress(int progress) {
        this.onlineRank = progress;

        // Calculate percentage based on a maximum rank of 100
        int percentage = Math.min(100, onlineRank);
        super.updateProgress(percentage);
    }

    /**
     * Get online rank as a string
     *
     * @return String representation of online rank
     */
    public String getOnlineRankString() {
        return "Online Rank: " + onlineRank;
    }

    @Override
    public String getProgressString() {
        return getOnlineRankString();
    }

    /**
     * Display game information in the console
     */
    public void displayInfo() {
        System.out.println("[OnlineGame] " + getTitle() + " - " + getGenre() + " on " + getPlatform() + " (Online)");
        System.out.println("Rank: " + onlineRank);
    }

    /**
     * Simulate connecting to the online game
     */
    public void play() {
        System.out.println("Connecting to online game: " + getTitle());
    }

    @Override
    public String toString() {
        return getTitle() + " (" + getReleaseYear() + ") - " + getGenre() + " - " + getOnlineRankString();
    }
}