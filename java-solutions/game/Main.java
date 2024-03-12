package game;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        System.out.print("Select a mode [OneGame/Tournament]: ");
        String mode = sc.next();

        if (!mode.equals("OneGame") && !mode.equals("Tournament")) {
            System.out.println("Incorrect mode");
            return;
        }

        System.out.print("Select a board [Rectangle/Circle]: ");
        String board = sc.next();

        int m;
        int n = 0;
        int k;

        switch (board) {
            case "Rectangle":
                System.out.print("Enter m, n and k: ");
                m = sc.nextInt();
                n = sc.nextInt();
                k = sc.nextInt();
                break;
            case "Circle":
                System.out.print("Enter diameter and k: ");
                m = sc.nextInt();
                k = sc.nextInt();
                break;
            default:
                System.out.println("Incorrect board");
                return;
        }

        if (mode.equals("OneGame")) {
            final Game game = new Game(false, new HumanPlayer(), new HumanPlayer());

            int result;
            do {
                result = switch (board) {
                    case "Rectangle" -> game.play(new MNKBoard(m, n, k));
                    case "Circle" -> game.play(new CircleBoard(m, k));
                    default -> -1;
                };
                System.out.println("Game result: " + result);
            } while (result == 0);
        } else {
            System.out.print("Enter number of participants: ");
            int playersNumber = sc.nextInt();

            Player[] players = new Player[playersNumber];
            for (int i = 0; i < playersNumber; ++i) {
                System.out.print("Enter player " + (i + 1) + " type [Human/Random/Sequential]: ");
                String playerType = sc.next();
                players[i] = switch (playerType) {
                    case "Human" -> new HumanPlayer();
                    case "Random" -> new RandomPlayer();
                    case "Sequential" -> new SequentialPlayer();
                    default -> null;
                };
                if (players[i] == null) {
                    System.out.println("Incorrect player type");
                    return;
                }
            }

            final Tournament tournament = new Tournament(players, board, m, n, k);

            int[] places = tournament.play();
            System.out.println("Tournament places:");
            for (int place : places) {
                System.out.print(place + " ");
            }
            System.out.println();
        }

        sc.close();
    }
}
