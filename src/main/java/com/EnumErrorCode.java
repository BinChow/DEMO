package main.java.com;

/**
 * Created by ZhouBin on 5/31/16.
 */
public enum EnumErrorCode {
    NoError(0),
    WrongParameter(1),
    InternalError(2),
    EmptyStorage(3),
    NoWaitingCustomer(4);

    private int value;

    EnumErrorCode(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
