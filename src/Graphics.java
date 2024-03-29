import javafx.animation.PauseTransition;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Graphics extends Application {

	static final String CSS_LOCATION = "css/start.css";
	static final String PLAYER_OPTION = "player-option";
	static final int HEIGHT = 540;
	static final int WIDTH = 640;

	static final String TITLE = "Tic-Tac-ToeFX";
	static final String PLAYER1_TEXT = "Player 1: ";
	static final String PLAYER2_TEXT = " Player 2: ";
	static final String COMPUTER_TEXT = " Computer: ";

	static final Label TITLE_LABEL = new Label(TITLE);

	static Button[][] buttons = new Button[3][3];
	static Label scoreboard = new Label();
	private static Game game;

	public static void main(String[] args) {
		launch(args);
	}

	public static void updateScoreboard(int player1Score, int player2Score) {
		if (game.computerOpponent()) {
			scoreboard.setText(PLAYER1_TEXT + player1Score + COMPUTER_TEXT + player2Score);
		} else {
			scoreboard.setText(PLAYER1_TEXT + player1Score + PLAYER2_TEXT + player2Score);
		}
	}

	public static void setButtonText(int row, int col, Player mover) {
		Platform.runLater(() -> {
			buttons[row][col].setMouseTransparent(true);
			if (mover == Player.PLAYER1) {
				buttons[row][col].setText("X");
			} else {
				buttons[row][col].setText("O");
			}
		});

	}

	@Override
	public void start(Stage window) {
		window.setTitle(TITLE);
		window.setResizable(false);

		TITLE_LABEL.setStyle("-fx-font: 48 Arial; -fx-text-fill: RED;");

		BorderPane startLayout = new BorderPane();
		HBox top = new HBox();
		HBox bottom = new HBox();

		Button singlePlayer = new Button("One Player");
		singlePlayer.getStyleClass().add(PLAYER_OPTION);
		singlePlayer.setOnAction(e -> difficultyLevel(window));

		Button twoPlayer = new Button("Two Player");
		twoPlayer.getStyleClass().add(PLAYER_OPTION);
		twoPlayer.setOnAction(e -> displayBoard(window));

		top.getChildren().add(TITLE_LABEL);

		bottom.getChildren().addAll(singlePlayer, twoPlayer);

		startLayout.setTop(top);
		startLayout.setBottom(bottom);

		Scene startScene = new Scene(startLayout, WIDTH, HEIGHT);
		startScene.getStylesheets().add(CSS_LOCATION);

		window.setScene(startScene);
		window.show();
	}

	private static void difficultyLevel(Stage window) {
		BorderPane startLayout = new BorderPane();
		HBox top = new HBox();
		HBox bottom = new HBox();

		Button easy = new Button("Easy");
		easy.getStyleClass().add(PLAYER_OPTION);
		easy.setOnAction(e -> {
			Graphics.game = new Game(Bot.Difficulty.EASY);
			displayBoard(window);
		});

		Button medium = new Button("Medium");
		medium.getStyleClass().add(PLAYER_OPTION);
		medium.setOnAction(e -> {
			Graphics.game = new Game(Bot.Difficulty.MEDIUM);
			displayBoard(window);
		});

		Button impossible = new Button("Impossible");
		impossible.getStyleClass().add(PLAYER_OPTION);
		impossible.setOnAction(e -> {
			Graphics.game = new Game(Bot.Difficulty.IMPOSSIBLE);
			displayBoard(window);
		});

		top.getChildren().add(TITLE_LABEL);

		bottom.getChildren().addAll(easy, medium, impossible);

		startLayout.setTop(top);
		startLayout.setBottom(bottom);

		Scene startScene = new Scene(startLayout, WIDTH, HEIGHT);
		startScene.getStylesheets().add(CSS_LOCATION);
		window.setScene(startScene);
	}

	private static void displayBoard(Stage window) {
		if (game == null) {
			game = new Game(Bot.Difficulty.PLAYER);
		}
		scoreboard.setStyle("-fx-background-color: WHITE");

		for (int i = 0; i < 3; ++i) {
			Button[] row = new Button[3];
			for (int j = 0; j < 3; ++j) {
				Button b = new Button();
				buttonInit(b, i * 3 + j);
				row[j] = b;
			}
			buttons[i] = row;
		}

		BorderPane pane = new BorderPane();
		HBox top = new HBox();
		HBox bottom = new HBox();
		GridPane gamePane = new GridPane();

		pane.setTop(top);
		pane.setCenter(gamePane);
		pane.setBottom(bottom);
		gamePane.setAlignment(Pos.CENTER);

		Scene boardPane = new Scene(pane, WIDTH, HEIGHT);

		gamePane.setPadding(new Insets(10, 10, 10, 10));

		GridPane.setConstraints(scoreboard, 0, 4, 3, 1); // Node, column, Row, columnSpan, RowSpan
		GridPane.setHalignment(scoreboard, HPos.CENTER);

		for (Button[] row : buttons) {
			gamePane.getChildren().addAll(row);
		}

		top.getChildren().add(TITLE_LABEL);

		scoreboard.setStyle("-fx-font: 24 Arial;");
		pane.setStyle("-fx-background-color: lightgray");
		bottom.getChildren().add(scoreboard);
		bottom.setAlignment(Pos.CENTER);
		top.setAlignment(Pos.CENTER);

		gamePane.getStylesheets().add(CSS_LOCATION);

		window.setScene(boardPane);
	}

	private static void buttonInit(Button b, int buttonNumber) {
		b.setPrefSize(160, 160);
		b.setStyle("-fx-font: 75 arial; -fx-focus-color: transparent; -fx-faint-focus-color: transparent;");

		int col = buttonNumber % 3;
		int row = buttonNumber / 3;
		b.setOnAction(e -> game.move(row, col));
		GridPane.setConstraints(b, col, row);
	}

	public static void resetButtons() {
		for (Button[] row : buttons) {
			for (Button b : row) {
				reset(b);
			}
		}

	}

	public static void lockBoard() {
		for (Button[] row : buttons) {
			for (Button b : row) {
				b.setDisable(true);
			}
		}
		PauseTransition transition = new PauseTransition(Duration.seconds(2));
		transition.setOnFinished(e -> resetButtons());
		transition.play();
	}

	private static void reset(Button b) {
		b.setText("");
		b.setDisable(false);
		b.setMouseTransparent(false);
	}
}