package main.java.com.zhoubin;

/**
 * Created by ZhouBin on 5/31/16.
 */
public enum EnumProduct {
    EnumProdMacBook(0),
    EnumProdApple(1),
    EnumProdCookie(2);

    private int value;

    EnumProduct(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
