package main.game;

public class OnlineGame extends AbstractGame {
    private int progress;
    private boolean isOnline;

    public OnlineGame(String title, String genre, String platform, int releaseYear, String developer) {
        super(title, genre, platform, releaseYear, developer);
        this.progress = 0;
        this.isOnline = true;
    }

    public void updateProgress(int progress) {
        this.progress = progress;
    }

    public String getProgress() {
        return "Levels Completed: " + progress;
    }

    public void displayInfo() {
        System.out.println("[OnlineGame] " + title + " - " + genre + " on " + platform + " (Online)");
    }

    public void play() {
        System.out.println("Connecting to online game: " + title);
    }
}