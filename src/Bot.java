import java.util.ArrayList;
import java.util.Random;

import javafx.util.Pair;

class Bot {
    enum Difficulty {
        EASY, MEDIUM, IMPOSSIBLE, PLAYER
    }

    Difficulty difficulty;

    public Bot(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public Pair<Integer, Integer> move(Player[][] board) {
        Pair<Integer, Integer> choice = new Pair<Integer, Integer>(0, 0);

        if (this.difficulty == Difficulty.EASY) {
            return randomMove(board);
        }

        return choice;
    }

    private Pair<Integer, Integer> randomMove(Player[][] board) {
        ArrayList<Pair<Integer, Integer>> choices = new ArrayList<>();
        for (int x = 0; x < 3; ++x) {
            for (int y = 0; y < 3; ++y) {
                if (board[x][y] == Player.NONE) {
                    choices.add(new Pair<Integer, Integer>(x, y));
                }
            }
        }

        Random r = new Random();
        int random = (r.nextInt(choices.size()));
        return choices.get(random);
    }

}