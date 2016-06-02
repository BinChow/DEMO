package main.java.com;

/**
 * Created by ZhouBin on 5/30/16.
 */
public class Customer {
    private String name;
    private Good good;

    public Customer () {
    }

    public Customer(String name, Integer goodType, Good good) {
        this.name = name;
        this.good = good;
    }

    public Good getGood() {
        return this.good;
    }

    public void setGood(Good good) {
        this.good = good;
    }
}
