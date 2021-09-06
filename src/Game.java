import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.util.Duration;
import javafx.util.Pair;

class Game {
    private int player1Score = 0;
    private int player2Score = 0;
    private Player turn = Player.PLAYER1;
    private Bot bot;
    private Player[][] board = new Player[3][3];

    public Game(Bot.Difficulty difficulty) {
        this.bot = new Bot(difficulty);
        for (int i = 0; i < 3; ++i) {
            for (int j = 0; j < 3; ++j) {
                board[i][j] = Player.NONE;
            }
        }
    }

    public boolean computerOpponent() {
        return (bot.difficulty == Bot.Difficulty.PLAYER);
    }

    public void move(int row, int col) {
        board[row][col] = this.turn;
        Graphics.setButtonText(row, col, this.turn);
        boolean win = scoreGame();

        if (this.turn == Player.PLAYER1) {
            this.turn = Player.PLAYER2;
        } else {
            this.turn = Player.PLAYER1;
        }

        if (this.bot.difficulty != Bot.Difficulty.PLAYER && !win) {
            Pair<Integer, Integer> botMove = bot.move(board);
            board[botMove.getKey()][botMove.getValue()] = Player.PLAYER2;
            Platform.runLater(() -> Graphics.setButtonText(botMove.getKey(), botMove.getValue(), Player.PLAYER2));
            scoreGame();
            this.turn = Player.PLAYER1;
        }
    }

    public boolean scoreGame() {
        Player winner = winner();

        if (winner != Player.NONE) {
            if (winner == Player.PLAYER1) {
                ++player1Score;
            } else if (winner == Player.PLAYER2) {
                ++player2Score;
            }
            clearBoard();
            final KeyFrame updateScore = new KeyFrame(Duration.seconds(0),
                    e -> Graphics.updateScoreboard(this.player1Score, this.player2Score));
            final KeyFrame lock = new KeyFrame(Duration.seconds(0), e -> Graphics.lockBoard());
            final KeyFrame clear = new KeyFrame(Duration.seconds(2), e -> {
                Graphics.resetButtons();
            });
            final KeyFrame startNext = new KeyFrame(Duration.seconds(2.02), e -> {
                if (this.turn == Player.PLAYER2) {
                    Pair<Integer, Integer> botMove = bot.move(board);
                    board[botMove.getKey()][botMove.getValue()] = Player.PLAYER2;
                    Platform.runLater(
                            () -> Graphics.setButtonText(botMove.getKey(), botMove.getValue(), Player.PLAYER2));
                    this.turn = Player.PLAYER1;
                }
            });

            Timeline restartGame = new Timeline(lock, updateScore, clear, startNext);
            Platform.runLater(restartGame::play);

            return true;
        }
        return false;
    }

    private Player winner() {
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

        if (searchForEmpty())
            return Player.NONE;

        return Player.DRAW;
    }

    private Player matching(Player[] line) {
        Player first = line[0];
        if (first == Player.NONE)
            return Player.NONE;

        if (line[1] == first && line[2] == first) {
            return first;
        }
        return Player.NONE;
    }

    private Player checkRow(int rowNum) {
        Player[] row = board[rowNum];
        return matching(row);
    }

    private Player checkColumn(int colNum) {
        Player[] column = new Player[3];
        for (int i = 0; i < 3; ++i) {
            column[i] = board[i][colNum];
        }
        return matching(column);
    }

    private Player checkDiags() {
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

    private boolean searchForEmpty() {
        for (Player[] row : board) {
            for (Player p : row) {
                if (p == Player.NONE) {
                    return true;
                }
            }
        }
        return false;
    }

    private void clearBoard() {
        for (Player[] row : board) {
            for (int i = 0; i < row.length; ++i) {
                row[i] = Player.NONE;
            }
        }
    }
}