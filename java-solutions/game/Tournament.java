package game;

import java.util.*;

public class Tournament {
    Player[] players;

    String board;

    private final int m;

    private final int n;

    private final int k;

    public Tournament(Player[] players, String board, int m, int n, int k) {
        this.players = players;
        this.board = board;
        this.m = m;
        this.n = n;
        this.k = k;
    }

    public int[] play() {
        ArrayList<Integer> playerIndexes = new ArrayList<>();
        for (int i = 0; i < players.length; ++i) {
            playerIndexes.add(i);
        }

        int[] places = new int[players.length];
        int curPlace = players.length - players.length / 2 + 1;
        while (playerIndexes.size() > 1) {
            ArrayList<Integer> newPlayerIndexes = new ArrayList<>();
            for (int i = 0; i < playerIndexes.size(); i += 2) {
                if (i == playerIndexes.size() - 1) {
                    newPlayerIndexes.add(playerIndexes.get(i));
                    continue;
                }
                Random rnd = new Random();
                int playerIdx1;
                int playerIdx2;
                if (rnd.nextBoolean()) {
                    playerIdx1 = playerIndexes.get(i);
                    playerIdx2 = playerIndexes.get(i + 1);
                } else {
                    playerIdx2 = playerIndexes.get(i);
                    playerIdx1 = playerIndexes.get(i + 1);
                }

                System.out.println("Playing " + playerIdx1 + " and " + playerIdx2);

                final Game game = new Game(false, players[playerIdx1], players[playerIdx2]);
                int result;
                do {
                    result = switch (board) {
                        case "Rectangle" -> game.play(new MNKBoard(m, n, k));
                        case "Circle" -> game.play(new CircleBoard(m, k));
                        default -> -1;
                    };
                    System.out.println("Game result: " + result);
                } while (result == 0);
                if (result == 1) {
                    newPlayerIndexes.add(playerIdx1);
                    places[playerIdx2] = curPlace;
                } else {
                    newPlayerIndexes.add(playerIdx2);
                    places[playerIdx1] = curPlace;
                }
            }

            playerIndexes = newPlayerIndexes;
            curPlace = playerIndexes.size() - playerIndexes.size() / 2 + 1;
        }

        places[playerIndexes.get(0)] = 1;
        return places;
    }
}
