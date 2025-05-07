package main.game.library;

import java.util.ArrayList;
import java.util.List;
import main.game.AbstractGame;

public class GameLibrary {
    private List<AbstractGame> games;

    public GameLibrary() {
        games = new ArrayList<>();
    }

    public void addGame(AbstractGame game) {
        games.add(game);
    }

    public List<AbstractGame> searchGame(String title) {
        List<AbstractGame> result = new ArrayList<>();
        for (AbstractGame game : games) {
            if (game.getTitle().equalsIgnoreCase(title)) {
                result.add(game);
            }
        }
        return result;
    }

    public List<AbstractGame> getAllGames() {
        return games;
    }
}