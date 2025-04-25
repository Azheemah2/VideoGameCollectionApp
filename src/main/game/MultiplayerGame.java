package main.game;

public class MultiplayerGame extends AbstractGame {
    private int wins;
    private int losses;

    public MultiplayerGame(String title, String genre, String platform, int releaseYear, String developer) {
        super(title, genre, platform, releaseYear, developer);
        this.wins = 0;
        this.losses = 0;
    }

    public void updateProgress(int winOrLoss) {
        if (winOrLoss > 0) {
            wins += winOrLoss;
        } else {
            losses -= winOrLoss;
        }
    }

    public String getProgress() {
        return "Win/Loss Ratio: " + wins + "/" + losses;
    }

    public void displayInfo() {
        System.out.println("[Multiplayer] " + title + " - " + genre + " on " + platform);
    }
}