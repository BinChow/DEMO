package main.java.com;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by ZhouBin on 5/31/16.
 */
public class Warehouse {
    private Map<Integer, Integer> storage;

    public Warehouse() {
        this.storage = new HashMap<Integer, Integer>();
    }

    public Boolean isEmpty() {
        if (this.storage == null) return false;

        for (Map.Entry<Integer, Integer> entry : this.storage.entrySet()) {
            Integer productCount = entry.getValue();
            if (productCount != 0) return false;
        }
        return true;
    }

    public Integer getProductCount(Integer type) {
        if (this.storage == null) return EnumErrorCode.InternalError.getValue();
        return storage.get(type);
    }

    public Integer setProductCount(Integer type, Integer count) {
        if (this.storage == null) return EnumErrorCode.InternalError.getValue();
        storage.put(type, count);

        return EnumErrorCode.NoError.getValue();
    }

    public Integer updateProductCount(Integer type) {
        if (this.storage == null || !storage.containsKey(type) || storage.get(type) <= 0) {
            return EnumErrorCode.InternalError.getValue();
        }

        Integer originalCount = storage.get(type);
        storage.put(type, originalCount - 1);

        return EnumErrorCode.NoError.getValue();
    }
}
