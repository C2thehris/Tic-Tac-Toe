package a;

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

	static int bnum = 0;
	static boolean x = true;
	static int player1 = 0;
	static int player2 = 0;
	static Button b = new Button("");
	static Button b1 = new Button("");
	static Button b2 = new Button("");
	static Button b3 = new Button("");
	static Button b4 = new Button("");
	static Button b5 = new Button("");
	static Button b6 = new Button("");
	static Button b7 = new Button("");
	static Button b8 = new Button("");
	static Label label = new Label("Score : Player 1: " + player1 + " Player 2: " + player2);
	static GridPane pane = new GridPane();
	static Scene scene = new Scene(pane, 324, 432, Color.BLUE);

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		launch(args);
	}

	@Override
	public void start(Stage stage) {
		// TODO Auto-generated method stub

		label.setStyle("-fx-background-color: #76D7C4;");
		stage.setTitle("Tic Tac Toe");
		stage.setResizable(false);

		standardize(b);
		standardize(b1);
		standardize(b2);
		standardize(b3);
		standardize(b4);
		standardize(b5);
		standardize(b6);
		standardize(b7);
		standardize(b8);

		pane.setStyle("-fx-background-color: rgba(0, 0, 0, 0);");
		pane.setPadding(new Insets(10, 10, 10, 10));
		pane.setVgap(8);
		pane.setHgap(8);

		for (int i = 0; i < 3; i++) {
			pane.getColumnConstraints().add(new ColumnConstraints(100));
			pane.getRowConstraints().add(new RowConstraints(100));
		}

		GridPane.setConstraints(label, 0, 4, 3, 1);
		GridPane.setHalignment(label, HPos.CENTER);
		pane.getChildren().addAll(b, b1, b2, b3, b4, b5, b6, b7, b8, label);

		stage.setScene(scene);
		stage.show();

	}

	public static int win(boolean a) {
		int c = 0;
		if (!b.getText().isEmpty() && b.getText() != null && b.getText().equals(b1.getText())
				&& b.getText().equals(b2.getText())) {
			if (a) {
				c = 1;
			} else {
				c = 2;
			}
		} else if (!b3.getText().isEmpty() && b3.getText().equals(b4.getText()) && b4.getText().equals(b5.getText())) {
			if (a) {
				c = 1;
			} else {
				c = 2;
			}
		} else if (!b6.getText().isEmpty() && b6.getText().equals(b7.getText()) && b7.getText().equals(b8.getText())) {
			if (a) {
				c = 1;
			} else {
				c = 2;
			}
		} else if (!b1.getText().isEmpty() && b1.getText().equals(b4.getText()) && b4.getText().equals(b7.getText())) {
			if (a) {
				c = 1;
			} else {
				c = 2;
			}
		} else if (!b2.getText().isEmpty() && b2.getText().equals(b5.getText()) && b5.getText().equals(b8.getText())) {
			if (a) {
				c = 1;
			} else {
				c = 2;
			}
		} else if (!b.getText().isEmpty() && b.getText().equals(b3.getText()) && b6.getText().equals(b.getText())) {
			if (a) {
				c = 1;
			} else {
				c = 2;
			}
		} else if (!b.getText().isEmpty() && b.getText().equals(b4.getText()) && b.getText().equals(b8.getText())) {
			if (a) {
				c = 1;
			} else {
				c = 2;
			}
		} else if (!b2.getText().isEmpty() && b2.getText().equals(b4.getText()) && b4.getText().equals(b6.getText())) {
			if (a) {
				c = 1;
			} else {
				c = 2;
			}
		} else if (!b.getText().isEmpty() && !b1.getText().isEmpty() && !b2.getText().isEmpty()
				&& !b3.getText().isEmpty() && !b4.getText().isEmpty() && !b5.getText().isEmpty()
				&& !b6.getText().isEmpty() && !b7.getText().isEmpty() && !b8.getText().isEmpty()) {
			c = 3;
		}
		return c;
	}

	public static void score() {
		int temp = win(x);
		if (temp == 1) {
			x = false;
			label.setText("Score : Player 1: " + player1 + " Player 2: " + ++player2);
			reset(b);
			reset(b1);
			reset(b2);
			reset(b3);
			reset(b4);
			reset(b5);
			reset(b6);
			reset(b7);
			reset(b8);
		} else if (temp == 2) {
			x = true;
			label.setText("Score : Player 1: " + ++player1 + " Player 2: " + player2);
			reset(b);
			reset(b1);
			reset(b2);
			reset(b3);
			reset(b4);
			reset(b5);
			reset(b6);
			reset(b7);
			reset(b8);
		} else if (temp == 3) {
			reset(b);
			reset(b1);
			reset(b2);
			reset(b3);
			reset(b4);
			reset(b5);
			reset(b6);
			reset(b7);
			reset(b8);
		}
	}

	public static void standardize(Button b) {

		b.setPrefSize(75, 75);
		b.setStyle("-fx-font: 37 arial;");
		b.setOnAction(e -> {
			if (x) {
				b.setText("X");
				x = false;
			} else {
				b.setText("O");
				x = true;
			}
			b.setMouseTransparent(true);
			score();
		});
		if (bnum % 3 == 0) {
			GridPane.setConstraints(b, 0, (bnum / 3));
			bnum++;
		} else if (bnum % 3 == 1) {
			GridPane.setConstraints(b, 1, (bnum / 3));
			bnum++;
		} else {
			GridPane.setConstraints(b, 2, (bnum / 3));
			bnum++;
		}
	}

	public static void reset(Button b) {
		b.setText("");
		b.setDisable(false);
		b.setMouseTransparent(false);
	}
}
