package com.urise.webapp;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class StreamDemo {
    public static void main(String[] args) {
        int [] values = {5,2,1,3,3,3};
        System.out.println(minValue(values));

        List<Integer> integers = Arrays.asList(1, 1, 1, 2, 2);
        System.out.println(oddOrEven(integers));
    }

        public static int minValue(int[] values) {
            return Arrays.stream(values)
                    .distinct()
                    .sorted()
                    .reduce(0,(a,b) -> a * 10 + b);
        }

        public static List<Integer> oddOrEven(List<Integer> integers) {
            return integers.stream()
                    .collect(Collectors.partitioningBy(i -> i % 2 != 0))
                    .get(integers.stream().filter(i -> i % 2 != 0).count() % 2 == 0);
        }
}