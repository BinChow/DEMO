package main.java.com.zhoubin;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ZhouBin on 6/1/16.
 */
public class Statistic {
    private Long saleStart;
    private Long saleFinish;
    private Long productSoldDuration;
    private Long waitDuration;
    private Integer customerCount;
    private List<Integer> cashierServiceCounts;
    private List<Cashier> cashiers;

    public Statistic (Integer cashierSize) {
        this.saleStart = 0L;
        this.saleFinish = 0L;
        this.productSoldDuration = 0L;
        this.customerCount = 0;
        this.waitDuration = 0L;
        cashierServiceCounts = new ArrayList<Integer>(cashierSize);
        for(int i = 0; i < cashierSize; i++) {
            cashierServiceCounts.add(0);
        }
        cashiers = new ArrayList<Cashier>();
        cashiers.add(new Cashier("Mary", 0, 0L));
        cashiers.add(new Cashier("John", 1, 0L));
        cashiers.add(new Cashier("Sandy", 2, 0L));
    }

    public Long getSaleStart() {
        return this.saleStart;
    }

    public void setSaleStart() {
        this.saleStart = System.currentTimeMillis();
    }

    public Long getSaleFinish() {
        return this.saleFinish;
    }

    public void setSaleFinish() {
        this.saleFinish = System.currentTimeMillis();
    }

    public List<Cashier> getCashierList() {
        return this.cashiers;
    }

    public Integer addCustomerCount() {
        this.customerCount++;
        return this.customerCount;
    }

    public void updateWaitDuration(Customer customer) {
        Long now = System.currentTimeMillis();
        Long purchaseTime = customer.getGood().getPurchaseTime();
        this.waitDuration += now - purchaseTime;
    }

    public void updateCashierServiceCount(Cashier cashier) {
        Integer employeeNumber = cashier.getEmployeeNumber();
        Integer count = cashierServiceCounts.get(employeeNumber) + 1;
        cashierServiceCounts.set(employeeNumber, count);
    }

    public void updateProductSoldDuration(Customer customer) {
        Long purchaseTime = customer.getGood().getPurchaseTime();
        this.productSoldDuration += purchaseTime - saleStart;
    }

    public void outputResult() {
        Double averageWaitDuration = new Double(waitDuration) / new Double(customerCount);
        Double averageSoldDuration = new Double(productSoldDuration) / new Double(customerCount);
        Long saleDuration = saleFinish - saleStart;

        System.out.println("the average waiting duration of customer is : " + String.format("%.2f", averageWaitDuration) + " milli seconds.");
        System.out.println("the average product sold duration is : " + String.format("%.2f", averageSoldDuration) + " milli seconds.");
        System.out.println("the duration of sale is : " + String.format("%d", saleDuration) + " milli seconds.");
        for (int i = 0; i < Business.cashierSize; i++) {
            String cashierName = cashiers.get(i).getName();
            Integer employeeNumber = cashiers.get(i).getEmployeeNumber();
            Integer cashierServiceCount = cashierServiceCounts.get(i);

            System.out.println("Cashier " + cashierName + ", employee number " + employeeNumber + ", servers " + cashierServiceCount + " customer(s).");
        }
    }
}
