package main.game.library;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import main.game.AbstractGame;
import main.game.SinglePlayerGame;
import main.game.MultiplayerGame;
import main.game.OnlineGame;

public class GameLibrary {
    private List<AbstractGame> games;
    private static final String SAVE_FILE = "game_library.dat";

    public GameLibrary() {
        games = new ArrayList<>();
        loadGamesFromFile();
    }

    public void addGame(AbstractGame game) {
        games.add(game);
        saveGamesToFile();
    }

    public List<AbstractGame> searchGame(String title) {
        List<AbstractGame> result = new ArrayList<>();
        for (AbstractGame game : games) {
            if (game.getTitle().toLowerCase().contains(title.toLowerCase())) {
                result.add(game);
            }
        }
        return result;
    }

    public List<AbstractGame> getAllGames() {
        return games;
    }

    // Data persistence methods
    public void saveGamesToFile() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SAVE_FILE))) {
            oos.writeObject(games);
            System.out.println("Games saved to file: " + SAVE_FILE);
        } catch (IOException e) {
            System.err.println("Error saving games to file: " + e.getMessage());
        }
    }

    @SuppressWarnings("unchecked")
    public void loadGamesFromFile() {
        File file = new File(SAVE_FILE);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
                games = (List<AbstractGame>) ois.readObject();
                System.out.println("Games loaded from file: " + SAVE_FILE);
            } catch (IOException | ClassNotFoundException e) {
                System.err.println("Error loading games from file: " + e.getMessage());
                // If loading fails, we keep the empty games list created in the constructor
            }
        } else {
            System.out.println("No save file found. Starting with empty library.");
        }
    }
}