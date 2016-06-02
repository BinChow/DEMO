package main.java.com;

import java.util.*;

/**
 * Created by ZhouBin on 5/31/16.
 */

public class Business {
    final static Integer cashierSize = 3;                   // the size of cashiers
    final static Integer productSize = 15;                  // the count of good in each category

    private PriorityQueue<Cashier> cashierPriorityQueue;    // cashier queue with cash time as priority
    private Queue<Customer> customerQueue;                  // customer waits in queue to checkout
    private Warehouse warehouse;                            // the warehouse of the products
    private Statistic statistic;                            // the statistic to record and calculate

    public Statistic getStatistic() {
        return this.statistic;
    }

    /**
     *     cashier subroutine rounds robin in 10 milli seconds interval to check:
     *     if there are available cashiers to serve the customer
     *         if there are customers
     *             the cashiers serve customers one by one
     *         else
     *             the cashier waits for customer
     *     else
     *         continues to round robin
     */
    public void subRoutineCashier(){
        while (true) {
            try {
                while (checkAvailableCashier()) {
                    // serve customer
                    Integer errorCode = cashierServeCustomer();
                    if (errorCode == EnumErrorCode.NoWaitingCustomer.getValue()) {
                        // if there is no waiting customer and the warehouse is empty
                        // set the sale to be finished and do the statistic calculation and output
                        statistic.setSaleFinish();
                        statistic.outputResult();
                        return;
                    }
                }

                // round robin to serve custom is 10 milli seconds interval
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     *     customer thread generates customer randomly in 1000 to 3000 milli seconds interval:
     *     if the good is available for the customer to choose
     *         customer chooses the good and wait for cashier
     *     else
     *         customer leaves as nothing happened
     */
    public void subRoutineCustomer(){
        while (true) {
            try {
                // generate customer randomly in choice and purchase time
                Thread.sleep(RandomUtils.randomCustomerGeneration());

                Integer errorCode = customerChooseGood();
                if (errorCode == EnumErrorCode.EmptyStorage.getValue()) {
                    // if there is no available goods for all categories, stop the customer subroutine
                    return;
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * check if there are available cashier whose cash time is earlier than current time slot
     *
     * @return
     */
    private Boolean checkAvailableCashier() {
        synchronized (this) {
            if (cashierPriorityQueue == null) return false;

            Long now = System.currentTimeMillis();
            Cashier cashier = cashierPriorityQueue.peek();
            if (cashier != null && now >= cashier.getCashTime()) {
                // if the cash time of cashier in the priority queue top fits the time slot
                // the cashier is available to serve customer
                return true;
            } else {
                return false;
            }
        }
    }

    /**
     * the synchronized method access the priority queue of cashiers, queue of customers and warehouse
     * make sure the piece of code and resource are not shared in threads
     *
     * there is cashier available cashier, it is guaranteed for the method
     * cashier serves the customer, once served, he/she get ready for serving the next time immediately
     * customer and good info is recorded by statistic
     *
     * @return
     */
    private Integer cashierServeCustomer() {
        synchronized (this) {
            if (cashierPriorityQueue == null || customerQueue == null) return EnumErrorCode.WrongParameter.getValue();
            if (cashierPriorityQueue.isEmpty()) return EnumErrorCode.NoError.getValue();

            // there is no waiting customer
            if (customerQueue.isEmpty()) {
                if (warehouse.isEmpty()) {
                    // the warehouse is also empty, return error code to stop the subroutine
                    return EnumErrorCode.NoWaitingCustomer.getValue();
                } else {
                    // the warehouse is not empty, there will be customers, return and wait for customers
                    return EnumErrorCode.NoError.getValue();
                }
            }

            // it is guaranteed that there is available cashier
            // cashier serves customer, by removing the available cashier from the priority queue
            // and setting the cashier to be available in the future
            Long now = System.currentTimeMillis();
            Long nextCashTime = now + RandomUtils.randomCashierGeneration();

            Cashier cashier = cashierPriorityQueue.poll();
            Cashier newCashier = new Cashier(cashier.getName(), cashier.getEmployeeNumber(), nextCashTime);
            cashierPriorityQueue.add(newCashier);
            System.out.println("cashier " + cashier.getName() + " serves at " + now + ". next cash time at " + nextCashTime);

            // the customer is served, by removing the customer from the waiting queue
            // record the time and count in statistics
            Customer customer = customerQueue.remove();
            if (cashier != null && customer != null && customer.getGood() != null) {
                statistic.addCustomerCount();
                statistic.updateWaitDuration(customer);
                statistic.updateCashierServiceCount(cashier);
                statistic.updateProductSoldDuration(customer);
            }

            return EnumErrorCode.NoError.getValue();
        }
    }

    /**
     * the synchronized method access the queue of customers and warehouse
     * make sure the piece of code and resource are not shared in threads
     *
     * the method is waken up in random interval to generate customer choice
     * add the customer into waiting list if there is available good to choose
     *
     * @return
     */
    private Integer customerChooseGood() {
        synchronized (this) {
            if (customerQueue == null) return EnumErrorCode.InternalError.getValue();

            // randomly choose the type of product
            Integer type = RandomUtils.randomTypeChoice();
            Integer count = warehouse.getProductCount(type);

            if (count > 0) {
                // if there is available good for the type customer chooses
                // set the customer with selected good, add the customer in waiting list
                warehouse.updateProductCount(type);
                Long now = System.currentTimeMillis();
                Good good = new Good(EnumProduct.values()[type].name(), type, now);
                Customer customer = new Customer(RandomUtils.getRandomString(8), good);
                customerQueue.add(customer);
                System.out.println("customer " + customer.getName() + " selects " + good.getName() + " at " + now);
            } else if (warehouse.isEmpty()) {
                // if there is no available good for the type, actually the warehouse is empty
                // return error code to stop the subroutine
                return EnumErrorCode.EmptyStorage.getValue();
            }
            // if there is no available good for the type but there are goods of other types, just return
            return EnumErrorCode.NoError.getValue();
        }
    }

    /**
     * initialize the business
     */
    public Business() {
        // initialize the priority queue for cashiers
        // the cash time is the priority in the queue
        this.cashierPriorityQueue = new PriorityQueue<Cashier>(cashierSize, new Comparator<Cashier>() {
            public int compare(Cashier cashierA, Cashier cashierB) {
                return cashierA.getCashTime().equals(cashierB.getCashTime()) ? 0 : (cashierA.getCashTime() < cashierB.getCashTime() ? -1 : 1);
            }
        });
        // initialize three cashiers into the priority queue
        Long now = System.currentTimeMillis();
        this.cashierPriorityQueue.add(new Cashier("Mary", 0, now + RandomUtils.randomCashierGeneration()));
        this.cashierPriorityQueue.add(new Cashier("John", 1, now + RandomUtils.randomCashierGeneration()));
        this.cashierPriorityQueue.add(new Cashier("Sandy", 2, now + RandomUtils.randomCashierGeneration()));

        // initialize customer waiting list
        this.customerQueue = new LinkedList<Customer>();

        // initialize warehouse with three categories, fifteen products each category
        this.warehouse = new Warehouse();
        this.warehouse.setProductCount(EnumProduct.EnumProdMacBook.getValue(), productSize);
        this.warehouse.setProductCount(EnumProduct.EnumProdApple.getValue(), productSize);
        this.warehouse.setProductCount(EnumProduct.EnumProdCookie.getValue(), productSize);

        // initialize the statistic
        statistic = new Statistic(cashierSize);
        statistic.setSaleStart();
    }
}
