package main.java.com.zhoubin;

/**
 * Created by ZhouBin on 5/30/16.
 */
public class Cashier {
    private String name;
    private Integer employeeNumber;
    private Long cashTime;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setEmployeeNumber(Integer employeeNumber) {
        this.employeeNumber = employeeNumber;
    }

    public Integer getEmployeeNumber() {
        return employeeNumber;
    }

    public void setCashTime(Long cashTime) {
        this.cashTime = cashTime;
    }

    public Long getCashTime() {
        return cashTime;
    }

    public Cashier(String name, Integer employeeNumber, Long cashTime) {
        this.name = name;
        this.employeeNumber = employeeNumber;
        this.cashTime = cashTime;
    }

    public Cashier () {
    }
}
