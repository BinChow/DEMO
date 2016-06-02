package main.java.com;

/**
 * Created by ZhouBin on 5/30/16.
 */
public class Customer {
    private String name;        // customer name, randomly generated
    private Good good;          // the good customer chooses

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Good getGood() {
        return this.good;
    }

    public void setGood(Good good) {
        this.good = good;
    }

    public Customer () {
    }

    public Customer(String name, Good good) {
        this.name = name;
        setGood(good);
    }
}
