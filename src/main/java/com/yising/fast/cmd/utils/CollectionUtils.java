package com.yising.fast.cmd.utils;

import java.util.Collection;

public class CollectionUtils {
    /**
     * 判断集合是否为空
     *
     * @param collection 集合
     * */
    public static boolean isEmpty(Collection collection) {
        return collection == null || collection.isEmpty();
    }
}
