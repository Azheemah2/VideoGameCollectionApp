package main;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import main.game.AbstractGame;
import main.game.MultiplayerGame;
import main.game.SinglePlayerGame;
import main.game.UserProfile;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.Button;

import java.util.ArrayList;
import java.util.List;
import javafx.geometry.Insets;

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
        users.add(new UserProfile("Player1", "PC"));
        users.add(new UserProfile("Player2", "Console"));
        currentUser = users.get(0);

        BorderPane root = new BorderPane();
        VBox formContainer = new VBox(15);
        formContainer.setPadding(new Insets(15));
        formContainer.setStyle(
                "-fx-background-color: #1e272e; -fx-padding: 20px; -fx-border-radius: 10px; -fx-border-color: #485460;");

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
        addButton.setStyle(
                "-fx-background-color: #0be881; -fx-text-fill: black; -fx-font-weight: bold; -fx-padding: 10px; -fx-border-radius: 5px;");

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

        addButton.setOnAction(e -> addGame(titleField.getText(), genreField.getText(), platformField.getText(),
                releaseYearField.getText(), developerField.getText(), gameTypeDropdown.getValue()));

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

        formContainer.getChildren().addAll(formTitle, formPane, messageLabel, displayArea);

        gameList = new ListView<>();
        gameList.setPrefHeight(300);
        gameList.setStyle("-fx-background-color: #d2dae2; -fx-border-color: #1e272e; -fx-border-radius: 5px;");
        gameList.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                progressLabel.setText("Game Progress: " + getGameProgress(newVal));
            }
        });

        VBox gameContainer = new VBox(10, gameList, progressLabel);
        gameContainer.setPadding(new Insets(15));
        gameContainer.setStyle("-fx-background-color: #485460; -fx-padding: 15px; -fx-border-radius: 10px;");

        root.setTop(formContainer);
        root.setCenter(gameContainer);

        Scene scene = new Scene(root, 600, 700);
        primaryStage.setScene(scene);
        primaryStage.show();

        displayUserGames();
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
                gameList.getItems().clear();
                for (AbstractGame game : user.getOwnedGames()) {
                    gameList.getItems().add(game.getTitle());
                }
                progressLabel.setText("Game Progress: N/A");
            }
        }
    }

    private String getGameProgress(String gameTitle) {
        for (AbstractGame game : currentUser.getOwnedGames()) {
            if (game.getTitle().equals(gameTitle)) {
                return game.getProgress() + "%";
            }
        }
        return "Unknown";
    }

    private void displayUserGames() {
        displayArea.clear();
        gameList.getItems().clear();
        for (AbstractGame game : currentUser.getOwnedGames()) {
            String gameInfo = game.getTitle() + " - " + game.getProgress() + "%";
            displayArea.appendText(gameInfo + "\n");
            gameList.getItems().add(gameInfo);
        }
    }

    private void updateGameProgress(String progress) {
        if (gameDropdown.getValue() != null && !progress.isEmpty()) {
            for (AbstractGame game : currentUser.getOwnedGames()) {
                if (game.getTitle().equals(gameDropdown.getValue())) {
                    game.updateProgress(Integer.parseInt(progress));
                    displayUserGames();
                    break;
                }
            }
        }
    }

    private void searchGame(String query) {
        displayArea.clear();
        for (AbstractGame game : currentUser.getOwnedGames()) {
            if (game.getTitle().toLowerCase().contains(query.toLowerCase())) {
                displayArea.appendText(game.getTitle() + " - " + game.getProgress() + "%\n");
            }
        }
    }

    private void addGame(String title, String genre, String platform, String releaseYear, String developer,
            String gameType) {
        if (title.isEmpty() || genre.isEmpty() || platform.isEmpty() || releaseYear.isEmpty() || developer.isEmpty()
                || gameType == null) {
            messageLabel.setText("Please fill in all fields.");
            messageLabel.setStyle("-fx-text-fill: red;");
            return;
        }
        AbstractGame game;
        if (gameType.equals("SinglePlayer")) {
            game = new SinglePlayerGame(title, genre, platform, Integer.parseInt(releaseYear), developer);
        } else {
            game = new MultiplayerGame(title, genre, platform, Integer.parseInt(releaseYear), developer);
        }
        currentUser.addGame(game);
        updateGameDropdown();
        displayUserGames();
        messageLabel.setText("Game added successfully!");
        messageLabel.setStyle("-fx-text-fill: green;");
    }
}