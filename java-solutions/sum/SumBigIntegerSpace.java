package sum;

import java.math.BigInteger;

public final class SumBigIntegerSpace {
    public static void main(String[] args) {
        BigInteger sum = BigInteger.ZERO;

        for (String arg : args) {
            int previous = -1;
            for (int i = 0; i < arg.length(); ++i) {
                boolean isSpaceSeparator = Character.getType(arg.charAt(i)) == Character.SPACE_SEPARATOR;
                if (!isSpaceSeparator && i < arg.length() - 1) {
                    continue;
                }
                int right = previous + 1;
                int left = i;
                if (!isSpaceSeparator)
                    ++left;
                if (right < left) {
                    sum = sum.add(new BigInteger(arg.substring(right, left)));
                }
                previous = i;
            }
        }
        System.out.println(sum);
    }
}
