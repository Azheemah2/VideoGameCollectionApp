package main.game;

/**
 * Represents a single player game with level tracking
 */
public class SinglePlayerGame extends AbstractGame {
    private static final long serialVersionUID = 1L;

    private int levelsCompleted;
    private boolean hasStoryMode;

    public SinglePlayerGame(String title, String genre, String platform, int releaseYear, String developer) {
        super(title, genre, platform, releaseYear, developer);
        this.levelsCompleted = 0;
        this.hasStoryMode = true;
    }

    public boolean hasStoryMode() {
        return hasStoryMode;
    }

    public void setHasStoryMode(boolean hasStoryMode) {
        this.hasStoryMode = hasStoryMode;
    }

    public int getLevelsCompleted() {
        return levelsCompleted;
    }

    /**
     * Update the number of levels completed
     *
     * @param levels Number of levels completed
     */
    @Override
    public void updateProgress(int levels) {
        this.levelsCompleted = levels;

        // Calculate percentage based on an assumed max of 100 levels
        // This maintains compatibility with the AbstractGame progress tracking
        int percentage = Math.min(100, levelsCompleted);
        super.updateProgress(percentage);
    }

    /**
     * Get progress as a string representation
     * This overrides the AbstractGame.getProgressString() method
     *
     * @return String representation of levels completed
     */
    @Override
    public String getProgressString() {
        return "Levels Completed: " + levelsCompleted;
    }

    /**
     * Get levels completed as a string
     *
     * @return String representation of levels completed
     */
    public String getLevelsCompletedString() {
        return "Levels Completed: " + levelsCompleted;
    }

    /**
     * Display game information in the console
     */
    public void displayInfo() {
        System.out.println("[SinglePlayer] " + getTitle() + " - " + getGenre() + " on " + getPlatform());
        System.out.println("Levels Completed: " + levelsCompleted);
    }

    @Override
    public String toString() {
        return getTitle() + " (" + getReleaseYear() + ") - " + getGenre() + " - " + getProgressString();
    }
}