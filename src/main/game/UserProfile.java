package main.game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class UserProfile implements Serializable {
    private static final long serialVersionUID = 1L;

    private String username;
    private String preferredPlatform;
    private List<AbstractGame> ownedGames;

    public UserProfile(String username, String preferredPlatform) {
        this.username = username;
        this.preferredPlatform = preferredPlatform;
        this.ownedGames = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public String getPreferredPlatform() {
        return preferredPlatform;
    }

    public List<AbstractGame> getOwnedGames() {
        return ownedGames;
    }

    public void addGame(AbstractGame game) {
        ownedGames.add(game);
    }

    public void rateGame(AbstractGame game, int rating) {
        // Implementation for rating games
        System.out.println("Game: " + game.getTitle() + " rated " + rating + "/5");
    }
}