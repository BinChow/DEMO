package main.java.com.zhoubin;

/**
 * Created by ZhouBin on 5/31/16.
 */
public class RandomUtils {

    public static Integer randomTypeChoice() {
        Integer Min = 0;
        Integer Max = 3;
        return Min + (int)(Math.random() * (Max - Min));
    }

    public static long randomCustomerGeneration() {
        long Min = 100L;
        long Max = 300L;
        System.out.println("random generated customer");
        return (Min + (new Double(Math.random() * (Max - Min))).longValue()) * 10L;
    }

    public static long randomCashierGeneration() {
        long Min = 500L;
        long Max = 1000L;
        System.out.println("random generated cashier");
        return (Min + (new Double(Math.random() * (Max - Min))).longValue()) * 10L;
    }
}
