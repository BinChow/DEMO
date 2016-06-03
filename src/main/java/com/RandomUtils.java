package main.java.com;

/**
 * Created by ZhouBin on 5/31/16.
 */
public class RandomUtils {

    /**
     * Total 36 chars.
     */
    private static final String Chars = "0123456789abcdefghijklmnopqrstuvwxyz";

    /**
     * randomly generated string as customer's name
     *
     * @param length
     * @return
     */
    public static String getRandomString(int length) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            sb.append(Chars.charAt(randomCharactor()));
        }
        return sb.toString();
    }

    /**
     * randomly choose a character
     *
     * @return
     */
    private static Integer randomCharactor() {
        Integer Min = 0;
        Integer Max = Chars.length();
        return Min + (int)(Math.random() * (Max - Min));
    }

    /**
     * randomly choose from categories of product
     *
     * @return
     */
    public static Integer randomTypeChoice() {
        Integer Min = 0;
        Integer Max = 3;
        return Min + (int)(Math.random() * (Max - Min));
    }

    /**
     * take 10 milli seconds as unit to generate time in range 1000 to 3000 milli seconds
     * 10 milli seconds are also the interval of cashier rounds robin to serve
     *
     * @return
     */
    public static long randomCustomerGeneration() {
        long Min = 100L;
        long Max = 300L;
        return (Min + (new Double(Math.random() * (Max - Min))).longValue()) * 10L;
    }

    /**
     * take 10 milli seconds as unit to generate time in range 5000 to 10000 milli seconds
     * 10 milli seconds are also the interval of cashier rounds robin to serve
     *
     * @return
     */
    public static long randomCashierGeneration() {
        long Min = 500L;
        long Max = 1000L;
        return (Min + (new Double(Math.random() * (Max - Min))).longValue()) * 10L;
    }
}
