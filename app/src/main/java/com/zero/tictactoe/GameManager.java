package com.zero.tictactoe;


public class GameManager {
    // handles the state of the game
    private static boolean isGameOver = false;

    private static boolean hasWinner = false;
    // if is 1 cross turn to move and if is 0 circle turn to move
    private static int isCrossTurn = 0;

    private static int[] board = {
            -1, -1, -1,
            -1, -1, -1,
            -1, -1, -1,
    };

    // All possible winning game options
    private static int [][] winningPositions = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
            {0, 4, 8}, {2, 4, 6}
    };

    public interface Callback {
        void setData(String text);
    }

    public static boolean getIsGameOver() {
        return isGameOver;
    }
    public static void setIsGameOver(boolean state) { isGameOver = state; }

    public static int getIsCrossTurn() { return isCrossTurn; }
    public static void setIsCrossTurn(int crossTurn) {
        if (crossTurn == 0 || crossTurn == 1) { isCrossTurn = crossTurn; }
    }

    public static boolean getHasWinner() { return hasWinner; }

    public static String getPlayer() {
        if (isCrossTurn == 0) {
            return "Circle";
        } else {
            return "Cross";
        }
    }

    public static void resetGameManager() {
        isCrossTurn = 0;
        isGameOver = false;
        hasWinner = false;
        for (int i = 0; i < board.length; i++) { board[i] = -1; }
    };

    public static int getBoardTile(int index) {
        if (!isValidIndex(index)) { return -2; }

        return board[index];
    }

    public static void setBoardTile(int index) {
        if (isValidIndex(index)) { board[index] = isCrossTurn; }
    }

    public static boolean isValidIndex(int index) {
        return (index >= 0 && index < board.length);
    }

    public static void checkForWinner(Callback callback) {
        for (int[] winPos: winningPositions) {
            if (board[winPos[0]] == board[winPos[1]] &&
                    board[winPos[1]] == board[winPos[2]] &&
                    board[winPos[2]] != -1) {

                 String winner = "Circle";

                if (board[winPos[0]] == 1) {
                    winner = "Cross";
                }

                hasWinner = true;
                callback.setData(winner + " wins the game");

            }
        }
    };

    public static boolean scanForDraw() {
        boolean gameOver = true;
        for (int tile: board) {
            if (tile == -1) {
                gameOver = false;
            }
        }

        return gameOver;
    };
}
