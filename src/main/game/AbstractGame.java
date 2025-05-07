package main.game;

import java.io.Serializable;

/**
 * Abstract base class for all game types
 */
public abstract class AbstractGame implements Serializable {
    private static final long serialVersionUID = 1L;

    private String title;
    private String genre;
    private String platform;
    private int releaseYear;
    private String developer;
    private int progress;

    public AbstractGame(String title, String genre, String platform, int releaseYear, String developer) {
        this.title = title;
        this.genre = genre;
        this.platform = platform;
        this.releaseYear = releaseYear;
        this.developer = developer;
        this.progress = 0;
    }

    public String getTitle() {
        return title;
    }

    public String getGenre() {
        return genre;
    }

    public String getPlatform() {
        return platform;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public String getDeveloper() {
        return developer;
    }

    public int getProgress() {
        return progress;
    }

    /**
     * Get progress as a formatted string
     *
     * @return String representation of progress
     */
    public String getProgressString() {
        return progress + "%";
    }

    public void updateProgress(int progress) {
        // Ensure progress is between 0 and 100
        this.progress = Math.max(0, Math.min(100, progress));
    }

    @Override
    public String toString() {
        return title + " (" + releaseYear + ") - " + genre + " - Progress: " + progress + "%";
    }
}