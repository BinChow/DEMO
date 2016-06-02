package main.java.com;

/**
 * Created by ZhouBin on 5/30/16.
 */
public class Good {
    private String name;        // the name of the good
    private Integer type;       // the type of the good
    private Long purchaseTime;  // the time customer chooses the good

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return this.type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Long getPurchaseTime() {
        return this.purchaseTime;
    }

    public void setPurchaseTime(Long purchaseTime) {
        this.purchaseTime = purchaseTime;
    }

    public Good(String name, Integer type, Long purchaseTime) {
        setName(name);
        setType(type);
        setPurchaseTime(purchaseTime);
    }
}
