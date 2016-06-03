package main.java.com;

/**
 * Created by ZhouBin on 5/30/16.
 */
public class SuperMarket {
    private String name;
    private String address;
    private String contactInfo;

    public static void main(String[] args) {
        runBusiness();
    }

    /**
     * the business opens two threads:
     *
     * 1.  cashier subroutine checks in 10 milli seconds time slice:
     *     if there are available cashiers to serve the customer
     *         if there are customers
     *             the cashiers serve customers one by one
     *         else
     *             the cashier waits for customer
     *     else
     *         continues to check in next time slice
     *
     * 2.  customer thread generates customer randomly in 1000 to 3000 milli seconds interval:
     *     if the good is available for the customer to choose
     *         customer chooses the good and wait for cashier
     *     else
     *         customer leaves as nothing happened
     *
     * @return
     */
    public static Business runBusiness() {
        final Business business = new Business();

        // cashier and customer are two separate threads
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

        // wait until children threads end
        try {
            threadCashier.join();
            threadCustomer.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return business;
    }
}
