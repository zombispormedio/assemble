package com.zombispormedio.assemble.utils;

import com.amulyakhare.textdrawable.util.ColorGenerator;
import com.annimon.stream.IntStream;
import com.annimon.stream.Stream;

import java.util.LinkedList;


/**
 * Created by Xavier Serrano on 31/07/2016.
 */
public class Utils {

    public static boolean presenceOf(Object obj) {
        return obj != null;
    }

    public static boolean presenceOf(String obj) {
        boolean it_is = true;

        if (obj == null) {
            it_is = false;
        } else {
            if (obj.isEmpty()) {
                it_is = false;
            }
        }
        return it_is;
    }

    public static boolean safeEquals(Object o1, Object o2) {
        boolean equals = o1 != null;
        if (equals) {
            equals = o1.equals(o2);
        }
        return equals;
    }



    public static int getColorByString(String str) {
        ColorGenerator generator = ColorGenerator.MATERIAL;
        return generator.getColor(str.hashCode());

    }

    public static int[] toArray(LinkedList<Integer> in) {
        return Stream.of(in)
                .mapToInt(i->i)
                .toArray();
    }


    public static Integer[] toInteger(int[] in) {
        return IntStream.of(in)
                .boxed()
                .toArray(Integer[]::new);
    }

    public static class Pair<X, Y> {

        public final X first;

        public final Y second;

        public Pair(X first, Y second) {
            this.first = first;
            this.second = second;
        }
    }




}
