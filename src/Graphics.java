import java.util.ArrayList;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Graphics extends Application {

	enum Player {
		PLAYER1, PLAYER2, DRAW, NONE
	}

	static final String PLAYER1_TEXT = "Player 1: ";
	static final String PLAYER2_TEXT = " Player 2: ";

	int player1Score = 0;
	int player2Score = 0;
	Player turn = Player.PLAYER1;

	ArrayList<ArrayList<Button>> buttons = new ArrayList<>();
	Label scoreboard = new Label(PLAYER1_TEXT + player1Score + PLAYER2_TEXT + player2Score);
	GridPane pane = new GridPane();
	Scene scene = new Scene(pane, 324, 432, Color.BLUE);

	public static void main(String[] args) {
		launch(args);
	}

	@Override
	public void start(Stage stage) {
		scoreboard.setStyle("-fx-background-color: WHITE;");
		stage.setTitle("Tic Tac Toe");
		stage.setResizable(false);

		for (int i = 0; i < 3; ++i) {
			ArrayList<Button> row = new ArrayList<>();
			for (int j = 0; j < 3; ++j) {
				Button b = new Button();
				buttonInit(b, i * 3 + j);
				row.add(b);
			}
			buttons.add(row);
		}

		pane.setStyle("-fx-background-color: rgba(0, 0, 0, 0);");
		pane.setPadding(new Insets(10, 10, 10, 10));
		pane.setVgap(8);
		pane.setHgap(8);

		for (int i = 0; i < 3; ++i) {
			pane.getColumnConstraints().add(new ColumnConstraints(100));
			pane.getRowConstraints().add(new RowConstraints(100));
		}
		GridPane.setConstraints(scoreboard, 0, 4, 3, 1);
		GridPane.setHalignment(scoreboard, HPos.CENTER);

		for (ArrayList<Button> row : buttons) {
			pane.getChildren().addAll(row);
		}
		pane.getChildren().add(scoreboard);

		stage.setScene(scene);
		stage.show();
	}

	boolean matching(ArrayList<Button> btns) {
		String first = btns.get(0).getText();
		for (Button button : btns) {
			if (!button.getText().equals(first))
				return false;
		}
		return true;
	}

	Player checkRow(int rowNum) {
		ArrayList<Button> row = buttons.get(rowNum);
		if (row.get(0).getText().isEmpty())
			return Player.NONE;

		if (matching(row)) {
			if (row.get(0).getText().equals("X"))
				return Player.PLAYER1;
			return Player.PLAYER2;
		}

		return Player.NONE;
	}

	Player checkColumn(int colNum) {
		ArrayList<Button> column = new ArrayList<>();

		for (ArrayList<Button> row : buttons) {
			column.add(row.get(colNum));
		}
		if (column.get(0).getText().isEmpty())
			return Player.NONE;

		if (matching(column)) {
			if (column.get(0).getText().equals("X"))
				return Player.PLAYER1;
			return Player.PLAYER2;
		}

		return Player.NONE;
	}

	Player checkDiags() {
		ArrayList<Button> topLeft = new ArrayList<>();
		ArrayList<Button> bottomLeft = new ArrayList<>();

		for (int i = 0; i < 3; ++i) {
			topLeft.add(buttons.get(i).get(i));
			bottomLeft.add(buttons.get(2 - i).get(i));
		}

		if (matching(topLeft) && !topLeft.get(0).getText().isEmpty()) {
			if (topLeft.get(0).getText().equals("X"))
				return Player.PLAYER1;
			return Player.PLAYER2;
		} else if (matching(bottomLeft) && !bottomLeft.get(0).getText().isEmpty()) {
			if (bottomLeft.get(0).getText().equals("X"))
				return Player.PLAYER1;
			return Player.PLAYER2;
		}

		return Player.NONE;
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

		for (ArrayList<Button> row : buttons) {
			for (Button b : row) {
				if (b.getText().isEmpty())
					return Player.NONE;
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

		for (ArrayList<Button> row : buttons) {
			for (Button b : row) {
				reset(b);
			}
		}
	}

	void buttonInit(Button b, int buttonNumber) {
		b.setPrefSize(75, 75);
		b.setStyle("-fx-font: 36 arial;");
		b.setOnAction(e -> {
			if (turn == Player.PLAYER1) {
				b.setText("X");
				turn = Player.PLAYER2;
			} else {
				b.setText("O");
				turn = Player.PLAYER1;
			}
			b.setMouseTransparent(true);

			Player winner = winner();
			if (winner != Player.NONE) {
				score(winner);
			}
		});

		int col = buttonNumber % 3;
		int row = buttonNumber / 3;
		GridPane.setConstraints(b, col, row);
	}

	void reset(Button b) {
		b.setText("");
		b.setDisable(false);
		b.setMouseTransparent(false);
	}
}