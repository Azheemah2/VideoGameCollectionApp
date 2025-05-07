package main.game;

/**
 * Represents a multiplayer game with win/loss tracking
 */
public class MultiplayerGame extends AbstractGame {
    private static final long serialVersionUID = 1L;

    private int maxPlayers;
    private int wins;
    private int losses;

    public MultiplayerGame(String title, String genre, String platform, int releaseYear, String developer) {
        super(title, genre, platform, releaseYear, developer);
        this.maxPlayers = 4; // Default value
        this.wins = 0;
        this.losses = 0;
    }

    public int getMaxPlayers() {
        return maxPlayers;
    }

    public void setMaxPlayers(int maxPlayers) {
        this.maxPlayers = maxPlayers;
    }

    public int getWins() {
        return wins;
    }

    public int getLosses() {
        return losses;
    }

    /**
     * Update wins or losses based on input
     * Positive values increase wins, negative values increase losses
     *
     * @param winOrLoss positive for wins, negative for losses
     */
    @Override
    public void updateProgress(int winOrLoss) {
        if (winOrLoss > 0) {
            wins += winOrLoss;
        } else {
            losses -= winOrLoss;
        }

        // Calculate progress percentage based on win ratio
        int totalGames = wins + losses;
        int progressValue = (totalGames > 0) ? (int)((double)wins / totalGames * 100) : 0;

        // Call parent method to update the progress percentage
        super.updateProgress(progressValue);
    }

    /**
     * Get win/loss ratio as a string
     *
     * @return String representation of win/loss record
     */
    public String getWinLossRatio() {
        return "Win/Loss Ratio: " + wins + "/" + losses;
    }

    @Override
    public String getProgressString() {
        return getWinLossRatio();
    }

    /**
     * Display game information in the console
     */
    public void displayInfo() {
        System.out.println("[Multiplayer] " + getTitle() + " - " + getGenre() + " on " + getPlatform());
        System.out.println("Win/Loss: " + wins + "/" + losses);
    }

    @Override
    public String toString() {
        return getTitle() + " (" + getReleaseYear() + ") - " + getGenre() + " - " + getWinLossRatio();
    }
}