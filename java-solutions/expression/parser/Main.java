package expression.parser;

import expression.TripleExpression;

public class Main {
    public static void main(String[] args) {
        ExpressionParser parser = new ExpressionParser();

        try {
            TripleExpression expression1 = parser.parse("low(0)\n\n\n");

            System.out.println(expression1.evaluate(-1233625964, -568565418, 930173053));
        } catch (StringIndexOutOfBoundsException e) {
            e.printStackTrace();
        }
    }
}
