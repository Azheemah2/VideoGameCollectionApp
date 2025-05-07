package main;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.game.*;
import javafx.geometry.Insets;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class GameLibraryApp extends Application {
    private List<UserProfile> users = new ArrayList<>();
    private UserProfile currentUser;
    private ListView<String> gameList;
    private ComboBox<String> userDropdown;
    private ComboBox<String> gameDropdown;
    private Label messageLabel;
    private Label progressLabel;
    private TextArea displayArea;
    private TextField progressField;
    private Button updateProgressButton;
    private TextField searchField;
    private Button searchButton;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Game Library Manager");

        // Load users from files
        users = GamePersistenceManager.loadAllUsers();
        currentUser = users.get(0);

        BorderPane root = new BorderPane();
        VBox formContainer = new VBox(15);
        formContainer.setPadding(new Insets(15));
        formContainer.setStyle("-fx-background-color: #1e272e; -fx-padding: 20px; -fx-border-radius: 10px; -fx-border-color: #485460;");

        Label formTitle = new Label("Add New Game");
        formTitle.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: white;");

        GridPane formPane = new GridPane();
        formPane.setPadding(new Insets(15));
        formPane.setHgap(10);
        formPane.setVgap(10);

        userDropdown = new ComboBox<>();
        for (UserProfile user : users) {
            userDropdown.getItems().add(user.getUsername());
        }
        userDropdown.setValue(currentUser.getUsername());
        userDropdown.setOnAction(e -> switchUser(userDropdown.getValue()));

        // Create menu bar with save/load options
        MenuBar menuBar = new MenuBar();
        Menu fileMenu = new Menu("File");
        MenuItem saveItem = new MenuItem("Save Library");
        MenuItem loadItem = new MenuItem("Load Library");
        MenuItem addUserItem = new MenuItem("Add New User");
        MenuItem exitItem = new MenuItem("Exit");

        saveItem.setOnAction(e -> saveGameLibrary());
        loadItem.setOnAction(e -> loadGameLibrary());
        addUserItem.setOnAction(e -> showAddUserDialog());
        exitItem.setOnAction(e -> {
            saveGameLibrary();
            Platform.exit();
        });

        fileMenu.getItems().addAll(saveItem, loadItem, addUserItem, new SeparatorMenuItem(), exitItem);
        menuBar.getMenus().add(fileMenu);

        Label titleLabel = new Label("Title:");
        titleLabel.setStyle("-fx-text-fill: white;");
        Label genreLabel = new Label("Genre:");
        genreLabel.setStyle("-fx-text-fill: white;");
        Label platformLabel = new Label("Platform:");
        platformLabel.setStyle("-fx-text-fill: white;");
        Label releaseYearLabel = new Label("Release Year:");
        releaseYearLabel.setStyle("-fx-text-fill: white;");
        Label developerLabel = new Label("Developer:");
        developerLabel.setStyle("-fx-text-fill: white;");
        Label gameTypeLabel = new Label("Game Type:");
        gameTypeLabel.setStyle("-fx-text-fill: white;");
        Label selectGameLabel = new Label("Select Game:");
        selectGameLabel.setStyle("-fx-text-fill: white;");
        Label updateProgressLabel = new Label("Update Progress:");
        updateProgressLabel.setStyle("-fx-text-fill: white;");
        Label searchGameLabel = new Label("Search Game:");
        searchGameLabel.setStyle("-fx-text-fill: white;");

        TextField titleField = new TextField();
        titleField.setPromptText("Game Title");
        TextField genreField = new TextField();
        genreField.setPromptText("Genre");
        TextField platformField = new TextField();
        platformField.setPromptText("Platform");
        TextField releaseYearField = new TextField();
        releaseYearField.setPromptText("Release Year");
        TextField developerField = new TextField();
        developerField.setPromptText("Developer");
        ComboBox<String> gameTypeDropdown = new ComboBox<>();
        gameTypeDropdown.getItems().addAll("SinglePlayer", "Multiplayer", "OnlineGame");
        gameTypeDropdown.setPromptText("Game Type");

        Button addButton = new Button("Add Game");
        addButton.setStyle("-fx-background-color: #0be881; -fx-text-fill: black; -fx-font-weight: bold; -fx-padding: 10px; -fx-border-radius: 5px;");

        messageLabel = new Label();
        messageLabel.setStyle("-fx-text-fill: white;");
        progressLabel = new Label("Game Progress: N/A");
        progressLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");

        displayArea = new TextArea();
        displayArea.setEditable(false);
        displayArea.setPrefHeight(100);

        gameDropdown = new ComboBox<>();
        updateGameDropdown();

        progressField = new TextField();
        progressField.setPromptText("Enter Progress");
        updateProgressButton = new Button("Update Progress");
        updateProgressButton.setOnAction(e -> updateGameProgress(progressField.getText()));

        searchField = new TextField();
        searchField.setPromptText("Search Game");
        searchButton = new Button("Search");
        searchButton.setOnAction(e -> searchGame(searchField.getText()));

        Button removeGameButton = new Button("Remove Game");
        removeGameButton.setStyle("-fx-background-color: #ff5e57; -fx-text-fill: white; -fx-font-weight: bold;");
        removeGameButton.setOnAction(e -> removeSelectedGame());

        addButton.setOnAction(e -> addGame(titleField.getText(), genreField.getText(), platformField.getText(), releaseYearField.getText(), developerField.getText(), gameTypeDropdown.getValue()));

        formPane.addRow(0, new Label("User:"), userDropdown);
        formPane.addRow(1, titleLabel, titleField);
        formPane.addRow(2, genreLabel, genreField);
        formPane.addRow(3, platformLabel, platformField);
        formPane.addRow(4, releaseYearLabel, releaseYearField);
        formPane.addRow(5, developerLabel, developerField);
        formPane.addRow(6, gameTypeLabel, gameTypeDropdown);
        formPane.addRow(7, addButton);
        formPane.addRow(8, selectGameLabel, gameDropdown);
        formPane.addRow(9, updateProgressLabel, progressField, updateProgressButton);
        formPane.addRow(10, searchGameLabel, searchField, searchButton);

        formContainer.getChildren().addAll(menuBar, formTitle, formPane, messageLabel, displayArea);

        gameList = new ListView<>();
        gameList.setPrefHeight(300);
        gameList.setStyle("-fx-background-color: #d2dae2; -fx-border-color: #1e272e; -fx-border-radius: 5px;");
        gameList.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                progressLabel.setText("Game Progress: " + getGameProgress(extractGameTitle(newVal)));
                gameDropdown.setValue(extractGameTitle(newVal));
            }
        });

        VBox gameContainer = new VBox(10, gameList, progressLabel, removeGameButton);
        gameContainer.setPadding(new Insets(15));
        gameContainer.setStyle("-fx-background-color: #485460; -fx-padding: 15px; -fx-border-radius: 10px;");

        root.setTop(formContainer);
        root.setCenter(gameContainer);

        Scene scene = new Scene(root, 600, 700);
        primaryStage.setScene(scene);
        primaryStage.show();

        // Set up auto-save on application close
        primaryStage.setOnCloseRequest(e -> {
            saveGameLibrary();
        });

        displayUserGames();
    }

    private void showAddUserDialog() {
        Dialog<UserProfile> dialog = new Dialog<>();
        dialog.setTitle("Add New User");
        dialog.setHeaderText("Enter user details:");

        ButtonType addButtonType = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButtonType, ButtonType.CANCEL);

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        TextField usernameField = new TextField();
        usernameField.setPromptText("Username");
        TextField platformField = new TextField();
        platformField.setPromptText("Preferred Platform");

        grid.add(new Label("Username:"), 0, 0);
        grid.add(usernameField, 1, 0);
        grid.add(new Label("Preferred Platform:"), 0, 1);
        grid.add(platformField, 1, 1);

        dialog.getDialogPane().setContent(grid);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButtonType) {
                return new UserProfile(usernameField.getText(), platformField.getText());
            }
            return null;
        });

        Optional<UserProfile> result = dialog.showAndWait();
        result.ifPresent(user -> {
            // Check for duplicate usernames
            for (UserProfile existingUser : users) {
                if (existingUser.getUsername().equals(user.getUsername())) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("Username already exists");
                    alert.setContentText("Please choose a different username.");
                    alert.showAndWait();
                    return;
                }
            }

            users.add(user);
            userDropdown.getItems().add(user.getUsername());
            userDropdown.setValue(user.getUsername());
            switchUser(user.getUsername());
            saveGameLibrary();
        });
    }

    private String extractGameTitle(String listItem) {
        if (listItem == null) return null;
        int dashIndex = listItem.indexOf(" - ");
        return dashIndex > 0 ? listItem.substring(0, dashIndex) : listItem;
    }

    private void removeSelectedGame() {
        String selectedItem = gameList.getSelectionModel().getSelectedItem();
        if (selectedItem != null) {
            String gameTitle = extractGameTitle(selectedItem);
            currentUser.removeGame(gameTitle);
            updateGameDropdown();
            displayUserGames();
            messageLabel.setText("Game removed successfully!");
            messageLabel.setStyle("-fx-text-fill: green;");
        } else {
            messageLabel.setText("No game selected!");
            messageLabel.setStyle("-fx-text-fill: red;");
        }
    }

    private void saveGameLibrary() {
        boolean success = GamePersistenceManager.saveAllUsers(users);
        if (success) {
            messageLabel.setText("Game library saved successfully!");
            messageLabel.setStyle("-fx-text-fill: green;");
        } else {
            messageLabel.setText("Error saving game library!");
            messageLabel.setStyle("-fx-text-fill: red;");
        }
    }

    private void loadGameLibrary() {
        users = GamePersistenceManager.loadAllUsers();
        if (!users.isEmpty()) {
            userDropdown.getItems().clear();
            for (UserProfile user : users) {
                userDropdown.getItems().add(user.getUsername());
            }

            if (!userDropdown.getItems().contains(currentUser.getUsername())) {
                currentUser = users.get(0);
                userDropdown.setValue(currentUser.getUsername());
            } else {
                userDropdown.setValue(currentUser.getUsername());
            }

            updateGameDropdown();
            displayUserGames();
            messageLabel.setText("Game library loaded successfully!");
            messageLabel.setStyle("-fx-text-fill: green;");
        } else {
            messageLabel.setText("No saved data found or error loading data!");
            messageLabel.setStyle("-fx-text-fill: red;");
        }
    }

    private void updateGameDropdown() {
        gameDropdown.getItems().clear();
        for (AbstractGame game : currentUser.getOwnedGames()) {
            gameDropdown.getItems().add(game.getTitle());
        }
    }

    private void switchUser(String username) {
        for (UserProfile user : users) {
            if (user.getUsername().equals(username)) {
                currentUser = user;
                updateGameDropdown();
                displayUserGames();
                messageLabel.setText("Switched to user: " + username);
                messageLabel.setStyle("-fx-text-fill: green;");
                break;
            }
        }
    }

    private String getGameProgress(String gameTitle) {
        for (AbstractGame game : currentUser.getOwnedGames()) {
            if (game.getTitle().equals(gameTitle)) {
                if (game instanceof MultiplayerGame) {
                    return ((MultiplayerGame) game).getWinLossRatio();
                } else if (game instanceof SinglePlayerGame) {
                    return ((SinglePlayerGame) game).getLevelsCompletedString();
                } else if (game instanceof OnlineGame) {
                    return ((OnlineGame) game).getOnlineRankString();
                } else {
                    return game.getProgress() + "%";
                }
            }
        }
        return "N/A";
    }

    private void displayUserGames() {
        displayArea.clear();
        gameList.getItems().clear();
        for (AbstractGame game : currentUser.getOwnedGames()) {
            String gameInfo;
            if (game instanceof MultiplayerGame) {
                MultiplayerGame mpGame = (MultiplayerGame) game;
                gameInfo = game.getTitle() + " - " + mpGame.getWinLossRatio();
            } else if (game instanceof SinglePlayerGame) {
                SinglePlayerGame spGame = (SinglePlayerGame) game;
                gameInfo = game.getTitle() + " - " + spGame.getLevelsCompletedString();
            } else if (game instanceof OnlineGame) {
                OnlineGame onlineGame = (OnlineGame) game;
                gameInfo = game.getTitle() + " - " + onlineGame.getOnlineRankString();
            } else {
                gameInfo = game.getTitle() + " - " + game.getProgress() + "%";
            }
            displayArea.appendText(gameInfo + "\n");
            gameList.getItems().add(gameInfo);
        }
    }

    private void updateGameProgress(String progress) {
        if (gameDropdown.getValue() != null && !progress.isEmpty()) {
            try {
                int progressValue = Integer.parseInt(progress);

                for (AbstractGame game : currentUser.getOwnedGames()) {
                    if (game.getTitle().equals(gameDropdown.getValue())) {
                        if (game instanceof MultiplayerGame) {
                            // For multiplayer games, update wins/losses
                            game.updateProgress(progressValue);
                            displayUserGames();
                            MultiplayerGame mpGame = (MultiplayerGame) game;
                            progressLabel.setText(mpGame.getWinLossRatio());
                            messageLabel.setText("Win/Loss record updated successfully!");
                            messageLabel.setStyle("-fx-text-fill: green;");
                        } else if (game instanceof SinglePlayerGame) {
                            // For single player games, update levels completed
                            if (progressValue < 0) {
                                messageLabel.setText("Levels cannot be negative!");
                                messageLabel.setStyle("-fx-text-fill: red;");
                                return;
                            }

                            game.updateProgress(progressValue);
                            displayUserGames();
                            SinglePlayerGame spGame = (SinglePlayerGame) game;
                            progressLabel.setText(spGame.getLevelsCompletedString());
                            messageLabel.setText("Levels updated successfully!");
                            messageLabel.setStyle("-fx-text-fill: green;");
                        } else if (game instanceof OnlineGame) {
                            // For online games, update rank
                            if (progressValue < 0) {
                                messageLabel.setText("Rank cannot be negative!");
                                messageLabel.setStyle("-fx-text-fill: red;");
                                return;
                            }

                            game.updateProgress(progressValue);
                            displayUserGames();
                            OnlineGame onlineGame = (OnlineGame) game;
                            progressLabel.setText(onlineGame.getOnlineRankString());
                            messageLabel.setText("Online rank updated successfully!");
                            messageLabel.setStyle("-fx-text-fill: green;");
                        } else {
                            // For other game types, validate 0-100%
                            if (progressValue < 0 || progressValue > 100) {
                                messageLabel.setText("Progress must be between 0 and 100!");
                                messageLabel.setStyle("-fx-text-fill: red;");
                                return;
                            }

                            game.updateProgress(progressValue);
                            displayUserGames();
                            progressLabel.setText("Game Progress: " + progressValue + "%");
                            messageLabel.setText("Progress updated successfully!");
                            messageLabel.setStyle("-fx-text-fill: green;");
                        }
                        break;
                    }
                }
            } catch (NumberFormatException e) {
                messageLabel.setText("Please enter a valid number for progress!");
                messageLabel.setStyle("-fx-text-fill: red;");
            }
        } else {
            messageLabel.setText("Please select a game and enter progress!");
            messageLabel.setStyle("-fx-text-fill: red;");
        }
    }

    private void searchGame(String query) {
        if (query == null || query.trim().isEmpty()) {
            displayUserGames();
            return;
        }

        displayArea.clear();
        gameList.getItems().clear();

        for (AbstractGame game : currentUser.getOwnedGames()) {
            if (game.getTitle().toLowerCase().contains(query.toLowerCase())) {
                String gameInfo;
                if (game instanceof MultiplayerGame) {
                    MultiplayerGame mpGame = (MultiplayerGame) game;
                    gameInfo = game.getTitle() + " - " + mpGame.getWinLossRatio();
                } else if (game instanceof SinglePlayerGame) {
                    SinglePlayerGame spGame = (SinglePlayerGame) game;
                    gameInfo = game.getTitle() + " - " + spGame.getLevelsCompletedString();
                } else if (game instanceof OnlineGame) {
                    OnlineGame onlineGame = (OnlineGame) game;
                    gameInfo = game.getTitle() + " - " + onlineGame.getOnlineRankString();
                } else {
                    gameInfo = game.getTitle() + " - " + game.getProgress() + "%";
                }
                displayArea.appendText(gameInfo + "\n");
                gameList.getItems().add(gameInfo);
            }
        }

        if (gameList.getItems().isEmpty()) {
            messageLabel.setText("No games found matching: " + query);
            messageLabel.setStyle("-fx-text-fill: orange;");
        } else {
            messageLabel.setText("Found " + gameList.getItems().size() + " games matching: " + query);
            messageLabel.setStyle("-fx-text-fill: white;");
        }
    }

    private void addGame(String title, String genre, String platform, String releaseYear, String developer, String gameType) {
        if (title.isEmpty() || genre.isEmpty() || platform.isEmpty() || releaseYear.isEmpty() || developer.isEmpty() || gameType == null) {
            messageLabel.setText("Please fill in all fields.");
            messageLabel.setStyle("-fx-text-fill: red;");
            return;
        }

        try {
            int year = Integer.parseInt(releaseYear);
            AbstractGame game;

            switch (gameType) {
                case "SinglePlayer":
                    game = new SinglePlayerGame(title, genre, platform, year, developer);
                    break;
                case "Multiplayer":
                    game = new MultiplayerGame(title, genre, platform, year, developer);
                    break;
                case "OnlineGame":
                    game = new OnlineGame(title, genre, platform, year, developer);
                    break;
                default:
                    messageLabel.setText("Invalid game type selected!");
                    messageLabel.setStyle("-fx-text-fill: red;");
                    return;
            }

            // Check for duplicate game titles
            for (AbstractGame existingGame : currentUser.getOwnedGames()) {
                if (existingGame.getTitle().equals(title)) {
                    messageLabel.setText("Game with this title already exists!");
                    messageLabel.setStyle("-fx-text-fill: red;");
                    return;
                }
            }

            currentUser.addGame(game);
            updateGameDropdown();
            displayUserGames();
            messageLabel.setText("Game added successfully!");
            messageLabel.setStyle("-fx-text-fill: green;");

            // Auto-save after adding a game
            GamePersistenceManager.saveUser(currentUser);
        } catch (NumberFormatException e) {
            messageLabel.setText("Please enter a valid release year!");
            messageLabel.setStyle("-fx-text-fill: red;");
        }
    }
}