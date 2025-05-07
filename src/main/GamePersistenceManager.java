package main;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import main.game.AbstractGame;
import main.game.MultiplayerGame;
import main.game.SinglePlayerGame;
import main.game.UserProfile;

/**
 * Handles saving and loading game data to/from files
 */
public class GamePersistenceManager {
    private static final String DATA_DIRECTORY = "gamedata";
    private static final String FILE_EXTENSION = ".dat";

    /**
     * Saves all user profiles to their respective files
     * @param users List of user profiles to save
     * @return true if save was successful, false otherwise
     */
    public static boolean saveAllUsers(List<UserProfile> users) {
        createDataDirectoryIfNeeded();
        boolean allSaved = true;

        for (UserProfile user : users) {
            if (!saveUser(user)) {
                allSaved = false;
            }
        }

        return allSaved;
    }

    /**
     * Saves a single user profile to a file
     * @param user The user profile to save
     * @return true if save was successful, false otherwise
     */
    public static boolean saveUser(UserProfile user) {
        createDataDirectoryIfNeeded();
        String filename = DATA_DIRECTORY + File.separator + user.getUsername() + FILE_EXTENSION;

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(user);
            return true;
        } catch (IOException e) {
            System.err.println("Error saving user " + user.getUsername() + ": " + e.getMessage());
            return false;
        }
    }

    /**
     * Loads all user profiles from the data directory
     * @return List of loaded user profiles
     */
    public static List<UserProfile> loadAllUsers() {
        createDataDirectoryIfNeeded();
        List<UserProfile> users = new ArrayList<>();
        File dataDir = new File(DATA_DIRECTORY);

        if (dataDir.exists() && dataDir.isDirectory()) {
            File[] files = dataDir.listFiles((dir, name) -> name.endsWith(FILE_EXTENSION));

            if (files != null) {
                for (File file : files) {
                    String username = file.getName().replace(FILE_EXTENSION, "");
                    UserProfile user = loadUser(username);
                    if (user != null) {
                        users.add(user);
                    }
                }
            }
        }

        // If no users were loaded, create default ones
        if (users.isEmpty()) {
            users.add(new UserProfile("Player1", "PC"));
            users.add(new UserProfile("Player2", "Console"));
        }

        return users;
    }

    /**
     * Loads a specific user profile from file
     * @param username The username of the profile to load
     * @return The loaded UserProfile or null if it couldn't be loaded
     */
    public static UserProfile loadUser(String username) {
        String filename = DATA_DIRECTORY + File.separator + username + FILE_EXTENSION;
        File file = new File(filename);

        if (!file.exists()) {
            return null;
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            return (UserProfile) ois.readObject();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Error loading user " + username + ": " + e.getMessage());
            return null;
        }
    }

    /**
     * Creates the data directory if it doesn't exist
     */
    private static void createDataDirectoryIfNeeded() {
        File directory = new File(DATA_DIRECTORY);
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }
}