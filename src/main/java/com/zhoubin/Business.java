package main.java.com.zhoubin;

import java.util.*;

/**
 * Created by ZhouBin on 5/31/16.
 */

public class Business {
    final static Integer cashierSize = 3;
    final static Integer productSize = 15;

    private PriorityQueue<Cashier> cashierPriorityQueue;
    private Queue<Customer> customerQueue;
    private Warehouse warehouse;
    private Statistic statistic;

    public void subRoutineCashier(){
        while (true) {
            try {
                while (checkAvailableCashier()) {
                    Integer errorCode = cashierServeCustomer();
                    if (errorCode == EnumErrorCode.NoWaitingCustomer.getValue()) {
                        statistic.setSaleFinish();
                        statistic.outputResult();
                        return;
                    }
                }

                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void subRoutineCustomer(){
        while (true) {
            try {
                Integer errorCode = customerChooseGood();
                if (errorCode == EnumErrorCode.EmptyStorage.getValue()) {
                    return;
                }

                Thread.sleep(RandomUtils.randomCustomerGeneration());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    private Boolean checkAvailableCashier() {
        synchronized (this) {
            if (cashierPriorityQueue == null) return false;
            Long now = System.currentTimeMillis();
            Cashier cashier = cashierPriorityQueue.peek();
            if (cashier != null && now >= cashier.getCashTime()) {
                return true;
            } else {
                return false;
            }
        }
    }

    private Integer cashierServeCustomer() {
        synchronized (this) {
            if (cashierPriorityQueue == null || customerQueue == null) return EnumErrorCode.WrongParameter.getValue();
            if (cashierPriorityQueue.isEmpty()) return EnumErrorCode.NoError.getValue();

            if (customerQueue.isEmpty()) {
                if (warehouse.isEmpty()) {
                    return EnumErrorCode.NoWaitingCustomer.getValue();
                } else {
                    return EnumErrorCode.NoError.getValue();
                }
            }

            Customer customer = customerQueue.remove();
            Cashier cashier = cashierPriorityQueue.poll();
            Cashier newCashier = new Cashier();
            newCashier.setCashTime(System.currentTimeMillis() + RandomUtils.randomCashierGeneration());
            newCashier.setEmployeeNumber(cashier.getEmployeeNumber());
            newCashier.setName(cashier.getName());
            cashierPriorityQueue.add(newCashier);

            if (cashier != null && customer != null && customer.getGood() != null) {
                statistic.addCustomerCount();
                statistic.updateWaitDuration(customer);
                statistic.updateCashierServiceCount(cashier);
                statistic.updateProductSoldDuration(customer);
            }

            return EnumErrorCode.NoError.getValue();
        }
    }

    private Integer customerChooseGood() {
        synchronized (this) {
            if (customerQueue == null) return EnumErrorCode.InternalError.getValue();

            Integer typeChoice = RandomUtils.randomTypeChoice();
            Integer count = warehouse.getProductCount(typeChoice);

            if (count > 0) {
                warehouse.updateProductCount(typeChoice);
                Customer customer = new Customer();
                customer.setGood(new Good(EnumProduct.values()[typeChoice].name(), typeChoice, System.currentTimeMillis()));
                customerQueue.add(customer);
            } else if (warehouse.isEmpty()) {
                return EnumErrorCode.EmptyStorage.getValue();
            }
            return EnumErrorCode.NoError.getValue();
        }
    }

    public void initBusiness() {
        this.cashierPriorityQueue = new PriorityQueue<Cashier>(cashierSize, new Comparator<Cashier>() {
            public int compare(Cashier cashierA, Cashier cashierB) {
                return cashierA.getCashTime().equals(cashierB.getCashTime()) ? 0 : (cashierA.getCashTime() < cashierB.getCashTime() ? -1 : 1);
            }
        });
        Long now = System.currentTimeMillis();
        this.cashierPriorityQueue.add(new Cashier("Mary", 0, now + RandomUtils.randomCashierGeneration()));
        this.cashierPriorityQueue.add(new Cashier("John", 1, now + RandomUtils.randomCashierGeneration()));
        this.cashierPriorityQueue.add(new Cashier("Sandy", 2, now + RandomUtils.randomCashierGeneration()));

        this.customerQueue = new LinkedList<Customer>();

        this.warehouse = new Warehouse();
        this.warehouse.setProductCount(EnumProduct.EnumProdMacBook.getValue(), productSize);
        this.warehouse.setProductCount(EnumProduct.EnumProdApple.getValue(), productSize);
        this.warehouse.setProductCount(EnumProduct.EnumProdCookie.getValue(), productSize);

        statistic = new Statistic(cashierSize);
        statistic.setSaleStart();
    }
}
