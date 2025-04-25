package main.game;

import java.util.ArrayList;
import java.util.List;

public class UserProfile {
    private String username;
    private String preferredPlatform;
    private List<AbstractGame> ownedGames;

    public UserProfile(String username, String preferredPlatform) {
        this.username = username;
        this.preferredPlatform = preferredPlatform;
        this.ownedGames = new ArrayList<>();
    }

    public void addGame(AbstractGame game) {
        ownedGames.add(game);
    }

    public List<AbstractGame> getOwnedGames() {
        return ownedGames;
    }

    public String getUsername() { return username; }
    public String getPreferredPlatform() { return preferredPlatform; }
}
