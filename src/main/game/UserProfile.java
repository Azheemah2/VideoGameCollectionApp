package main.game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a user profile with owned games
 */
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

    public boolean removeGame(String title) {
        for (int i = 0; i < ownedGames.size(); i++) {
            if (ownedGames.get(i).getTitle().equals(title)) {
                ownedGames.remove(i);
                return true;
            }
        }
        return false;
    }
}