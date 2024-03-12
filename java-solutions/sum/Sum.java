package sum;

public final class Sum {
    public static void main(String[] args) {
        int sum = 0;
        for (String arg : args) {
            int previous = -1;
            for (int i = 0; i < arg.length(); ++i) {
                if (!Character.isWhitespace(arg.charAt(i))) {
                    continue;
                }
                if (i - previous > 1) {
                    sum += Integer.parseInt(arg.substring(previous + 1, i));
                }
                previous = i;
            }
            if (previous != arg.length() - 1)
                sum += Integer.parseInt(arg.substring(previous + 1));
        }
        System.out.println(sum);
    }
}