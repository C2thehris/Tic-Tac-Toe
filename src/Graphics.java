import javafx.application.Application;
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

public class Graphics extends Application {

	enum Player {
		PLAYER1, PLAYER2, DRAW, NONE
	}

	enum Bot {
		IMPOSSIBLE, MEDIUM, EASY, PLAYER
	}

	static final int HEIGHT = 540;
	static final int WIDTH = 640;

	static final String PLAYER1_TEXT = "Player 1: ";
	static final String PLAYER2_TEXT = " Player 2: ";

	int player1Score = 0;
	int player2Score = 0;
	Player turn = Player.PLAYER1;
	Bot difficulty = Bot.PLAYER;

	Player[][] board = new Player[3][3];
	Label scoreboard = new Label(PLAYER1_TEXT + player1Score + PLAYER2_TEXT + player2Score);
	Button[][] buttons = new Button[3][3];

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage window) {
		window.setTitle("Tic Tac Toe");
		window.setResizable(false);

		BorderPane startLayout = new BorderPane();
		HBox top = new HBox(new Label("Tic-Tac-ToeFX"));
		HBox bottom = new HBox();

		Button singlePlayer = new Button("One Player");
		singlePlayer.getStyleClass().add("player-option");
		singlePlayer.setOnAction(e -> {
			difficultyLevel(window);
		});

		Button twoPlayer = new Button("Two Player");
		twoPlayer.getStyleClass().add("player-option");
		twoPlayer.setOnAction(e -> {
			displayBoard(window);
		});

		bottom.getChildren().addAll(singlePlayer, twoPlayer);

		startLayout.setTop(top);
		startLayout.setBottom(bottom);

		Scene startScene = new Scene(startLayout, WIDTH, HEIGHT);
		startScene.getStylesheets().add("css/start.css");

		window.setScene(startScene);
		window.show();
	}

	void difficultyLevel(Stage window) {

	}

	void displayBoard(Stage window) {
		scoreboard.setStyle("-fx-background-color: WHITE");
		for (int i = 0; i < 3; ++i) {
			Button[] row = new Button[3];
			Player[] boardRow = new Player[3];
			for (int j = 0; j < 3; ++j) {
				Button b = new Button();
				boardRow[j] = Player.NONE;
				buttonInit(b, i * 3 + j);
				row[j] = b;
			}
			board[i] = boardRow;
			buttons[i] = row;
		}

		BorderPane pane = new BorderPane();
		HBox bottom = new HBox();
		GridPane gamePane = new GridPane();

		pane.setCenter(gamePane);
		pane.setBottom(bottom);
		gamePane.setAlignment(Pos.CENTER);

		Scene boardPane = new Scene(pane, WIDTH, HEIGHT);

		gamePane.setStyle("-fx-background-color: rgba(0, 0, 0, 0);");
		gamePane.setPadding(new Insets(10, 10, 10, 10));

		GridPane.setConstraints(scoreboard, 0, 4, 3, 1); // Node, column, Row, columnSpan, RowSpan
		GridPane.setHalignment(scoreboard, HPos.CENTER);

		for (Button[] row : buttons) {
			gamePane.getChildren().addAll(row);
		}

		scoreboard.setStyle("-fx-font: 24 Arial");
		bottom.getChildren().add(scoreboard);
		bottom.setAlignment(Pos.CENTER);

		window.setScene(boardPane);
	}

	Player matching(Player[] line) {
		Player first = line[0];
		if (first == Player.NONE)
			return Player.NONE;

		if (line[1] == first && line[2] == first) {
			return first;
		}
		return Player.NONE;
	}

	Player checkRow(int rowNum) {
		Player[] row = board[rowNum];
		return matching(row);
	}

	Player checkColumn(int colNum) {
		Player[] column = new Player[3];

		for (int i = 0; i < 3; ++i) {
			column[i] = board[colNum][i];
		}

		return matching(column);
	}

	Player checkDiags() {
		Player[] topLeft = new Player[3];
		Player[] bottomLeft = new Player[3];

		for (int i = 0; i < 3; ++i) {
			topLeft[i] = board[i][i];
			bottomLeft[i] = board[2 - i][i];
		}

		Player top = matching(topLeft);
		if (top != Player.NONE) {
			return top;
		}

		return matching(bottomLeft);
	}

	Player winner() {
		Player winner;
		for (int i = 0; i < 3; ++i) {
			winner = checkRow(i);
			if (winner != Player.NONE)
				return winner;
			winner = checkColumn(i);
			if (winner != Player.NONE)
				return winner;
		}

		winner = checkDiags();
		if (winner != Player.NONE)
			return winner;

		for (Player[] row : board) {
			for (Player p : row) {
				if (p == Player.NONE) {
					return Player.NONE;
				}
			}
		}

		return Player.DRAW;
	}

	void score(Player gameWinner) {
		if (gameWinner == Player.PLAYER1) {
			++player1Score;
			scoreboard.setText(PLAYER1_TEXT + player1Score + PLAYER2_TEXT + player2Score);
		} else if (gameWinner == Player.PLAYER2) {
			++player2Score;
			scoreboard.setText(PLAYER1_TEXT + player1Score + PLAYER2_TEXT + player2Score);
		}

		for (Button[] row : buttons) {
			for (Button b : row) {
				reset(b);
			}
		}
	}

	void buttonInit(Button b, int buttonNumber) {
		b.setPrefSize(160, 160);
		b.setStyle("-fx-font: 75 arial; -fx-focus-color: transparent; -fx-faint-focus-color: transparent;");

		int col = buttonNumber % 3;
		int row = buttonNumber / 3;
		b.setOnAction(e -> {
			if (turn == Player.PLAYER1) {
				b.setText("X");
				board[col][row] = Player.PLAYER1;
				turn = Player.PLAYER2;
			} else {
				b.setText("O");
				board[col][row] = Player.PLAYER2;
				turn = Player.PLAYER1;
			}
			b.setMouseTransparent(true);

			Player winner = winner();
			if (winner != Player.NONE) {
				score(winner);
			}
		});
		GridPane.setConstraints(b, col, row);
	}

	void reset(Button b) {
		b.setText("");
		b.setDisable(false);
		b.setMouseTransparent(false);
	}
}