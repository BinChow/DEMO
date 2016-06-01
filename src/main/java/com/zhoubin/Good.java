package main.java.com.zhoubin;

/**
 * Created by ZhouBin on 5/30/16.
 */
public class Good {
    private String name;
    private Integer type;
    private Long purchaseTime;

    public Integer getType() {
        return this.type;
    }

    public void setGoodType(Integer typeChoice) {
        this.type = typeChoice;
    }

    public Long getPurchaseTime() {
        return this.purchaseTime;
    }

    public void setPurchaseTime(Long purchaseTime) {
        this.purchaseTime = purchaseTime;
    }

    public Good(String name, Integer type, Long purchaseTime) {
        this.name = name;
        this.type = type;
        this.purchaseTime = purchaseTime;
    }
}
