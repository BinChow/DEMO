package main.java.com.zhoubin;

/**
 * Created by ZhouBin on 5/30/16.
 */
public class SuperMarket {
    private String name;
    private String address;
    private String contactInfo;

    public static void main(String[] args) {
        runSuperMarketBusiness();
    }

    public static void runSuperMarketBusiness() {
        final Business business= new Business();
        business.initBusiness();

        Thread threadCashier = new Thread(new Runnable() {
            @Override
            public void run() {
                business.subRoutineCashier();
            }
        });

        Thread threadCustomer = new Thread(new Runnable() {
            @Override
            public void run() {
                business.subRoutineCustomer();
            }
        });

        threadCashier.start();
        threadCustomer.start();
    }
}
