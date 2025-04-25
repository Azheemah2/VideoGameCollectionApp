package main.game;


public class  SinglePlayerGame extends AbstractGame {
    private int levelsCompleted;

    public SinglePlayerGame(String title, String genre, String platform, int releaseYear, String developer) {
        super(title, genre, platform, releaseYear, developer);
        this.levelsCompleted = 0;
    }

    public void updateProgress(int levels) {
        this.levelsCompleted = levels;
    }

    public String getProgress() {
        return "Levels Completed: " + levelsCompleted;
    }

    public void displayInfo() {
        System.out.println("[SinglePlayer] " + title + " - " + genre + " on " + platform);
    }
}
