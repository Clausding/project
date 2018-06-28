package com.dingya.smartframework.util;

import java.util.Iterator;
import java.util.Set;

/**
 * @Author: dingya
 * @Description:打印Set
 * @Date: Created in 16:23 2018/6/27
 */
public class PrintUtil {
    public static void printSet(Set<?> set) {
        Iterator<?> iterator = set.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
        System.out.println();
    }
}
