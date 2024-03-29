import java.util.ArrayList;
import java.util.Random;

import javafx.util.Pair;

class Bot {
    enum Difficulty {
        EASY, MEDIUM, IMPOSSIBLE, PLAYER
    }

    Random r = new Random();
    Difficulty difficulty;
    Player[][] board;

    public Bot(Difficulty difficulty) {
        this.difficulty = difficulty;
    }

    public Pair<Integer, Integer> move(Player[][] board) {
        Pair<Integer, Integer> choice = new Pair<>(0, 0);
        this.board = board;
        if (this.difficulty == Difficulty.EASY) {
            return randomMove();
        } else if (this.difficulty == Difficulty.MEDIUM) {
            choice = makeThree(Player.PLAYER2);
            if (choice.getKey() != -1) {
                return choice;
            }
            choice = makeThree(Player.PLAYER1);

            if (choice.getKey() != -1) {
                return choice;
            }
            return randomMove();
        }

        return choice;
    }

    private Pair<Integer, Integer> makeThree(Player p) {
        int blankPos = -1;
        for (int i = 0; i < 3; ++i) {
            Player[] line = getRow(i);
            blankPos = getBlankInThree(line, p);
            if (blankPos != -1) {
                return new Pair<>(i, blankPos);
            }

            line = getColumn(i);
            blankPos = getBlankInThree(line, p);
            if (blankPos != -1) {
                return new Pair<>(blankPos, i);
            }
        }

        Pair<Player[], Player[]> diags = getDiags();
        blankPos = getBlankInThree(diags.getKey(), p);
        if (blankPos != -1) {
            return new Pair<>(blankPos, blankPos);
        }

        blankPos = getBlankInThree(diags.getValue(), p);
        if (blankPos != -1) {
            return new Pair<>(2 - blankPos, blankPos);
        }

        return new Pair<>(-1, -1);
    }

    private Pair<Integer, Integer> randomMove() {
        ArrayList<Pair<Integer, Integer>> choices = new ArrayList<>();
        for (int x = 0; x < 3; ++x) {
            for (int y = 0; y < 3; ++y) {
                if (board[x][y] == Player.NONE) {
                    choices.add(new Pair<>(x, y));
                }
            }
        }

        int random = (r.nextInt(choices.size()));
        return choices.get(random);
    }

    private int getBlankInThree(Player[] line, Player p) {
        int player1 = 0;
        int player2 = 0;
        int blankPos = -1;
        for (int i = 0; i < 3; ++i) {
            if (line[i] == Player.PLAYER1) {
                ++player1;
            } else if (line[i] == Player.PLAYER2) {
                ++player2;
            } else {
                if (blankPos == -1) {
                    blankPos = i;
                } else {
                    blankPos = -1;
                    break;
                }
            }
        }

        if (p == Player.PLAYER1 && player1 == 2 || p == Player.PLAYER2 && player2 == 2) {
            return blankPos;
        }

        return -1;
    }

    private Player[] getRow(int rowNum) {
        return board[rowNum];
    }

    private Player[] getColumn(int colNum) {
        Player[] column = new Player[3];
        for (int i = 0; i < 3; ++i) {
            column[i] = board[i][colNum];
        }
        return column;
    }

    private Pair<Player[], Player[]> getDiags() {
        Player[] topLeft = new Player[3];
        Player[] bottomLeft = new Player[3];

        for (int i = 0; i < 3; ++i) {
            topLeft[i] = board[i][i];
            bottomLeft[i] = board[2 - i][i];
        }

        return new Pair<>(topLeft, bottomLeft);
    }
}