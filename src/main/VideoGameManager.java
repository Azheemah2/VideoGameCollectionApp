package main;

import main.game.AbstractGame;
import main.game.MultiplayerGame;
import main.game.OnlineGame;
import main.game.SinglePlayerGame;
import main.game.library.GameLibrary;

import java.util.List;

public class VideoGameManager {
    public static void main(String[] args) {
        GameLibrary library = new GameLibrary();

        // Adding games to the library
        library.addGame(new SinglePlayerGame("The Witcher 3", "RPG", "PC", 2015, "CD Projekt Red"));
        library.addGame(new MultiplayerGame("Overwatch", "FPS", "PC", 2016, "Blizzard"));
        library.addGame(new OnlineGame("World of Warcraft", "MMORPG", "PC", 2004, "Blizzard"));

        // Display all games
        System.out.println("All Games in Library:");
        for (AbstractGame game : library.getAllGames()) {
            game.displayInfo();
        }

        // Search for a game
        String searchTitle = "Overwatch";
        System.out.println("\nSearching for game: " + searchTitle);
        List<AbstractGame> foundGames = library.searchGame(searchTitle);
        if (foundGames.isEmpty()) {
            System.out.println("No game found.");
        } else {
            for (AbstractGame game : foundGames) {
                game.displayInfo();
            }
        }
    }
}
