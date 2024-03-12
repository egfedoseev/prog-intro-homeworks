package expression;

public class Main {
    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.print("Expected 1 argument, found " + args.length + "\nUsage: NUMBER");
            return;
        }

        int x;
        try {
            x = Integer.parseInt(args[0]);
        } catch (NumberFormatException e) {
            System.out.println("Argument is not a number, NumberFormatException: " + e.getMessage());
            return;
        }

        System.out.println(new Add(
                new Subtract(
                        new Multiply(
                                new Variable("x"),
                                new Variable("x")
                        ),
                        new Multiply(
                                new Const(2),
                                new Variable("x")
                        )
                ),
                new Const(1)).evaluate(x)
        );
    }
}
