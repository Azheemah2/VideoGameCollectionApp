package main.game;

public class AbstractGame {
    protected String title;
    protected String genre;
    protected String platform;
    protected int releaseYear;
    protected String developer;

    public AbstractGame(String title, String genre, String platform, int releaseYear, String developer) {
        this.title = title;
        this.genre = genre;
        this.platform = platform;
        this.releaseYear = releaseYear;
        this.developer = developer;
    }

    public String getTitle() { return title; }
    public String getGenre() { return genre; }
    public String getPlatform() { return platform; }
    public int getReleaseYear() { return releaseYear; }
    public String getDeveloper() { return developer; }

    public void displayInfo() {

    }

    public void updateProgress(int value) {

    }

    public String getProgress() {
        return null;
    }
}