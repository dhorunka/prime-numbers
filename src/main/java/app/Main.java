package app;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) {
        List<Integer> primeNumbers = eratosthenesAlgorithm(123456789);
        System.out.println(findMaxElements(primeNumbers));
    }

    private static List<Integer> eratosthenesAlgorithm(int n) {
        List<Integer> numbers = new ArrayList<>();
        int[] primeNumbers = new int[n + 1];

        for (int i = 0; i <= n; i++) {
            primeNumbers[i] = 1;
        }

        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (primeNumbers[i] == 1) {
                for (int j = 2; i * j <= n; j++) {
                    primeNumbers[i * j] = 0;
                }
            }
        }

        for (int i = 2; i <= n; i++) {
            if (primeNumbers[i] == 1) {
                numbers.add(i);
            }
        }
        return numbers;
    }

    private static int findMaxElements(List<Integer> primeNumbersList) {
        String last = String.valueOf(primeNumbersList.get(primeNumbersList.size() - 1));
        List<Integer> numbers = primeNumbersList
                .stream()
                .filter(i -> String.valueOf(i).length() >= last.length() - 1)
                .toList();

        Map<String, Integer> sortNumbers = new HashMap<>();
        List<String> collect = numbers
                .stream()
                .map(i -> Arrays.stream(String.valueOf(i).split(""))
                        .sorted(Comparator.comparingInt(Integer::parseInt))
                        .collect(Collectors.joining())).toList();
        collect.forEach(s -> {
            if (sortNumbers.containsKey(s)) {
                sortNumbers.put(s, sortNumbers.get(s) + 1);
            } else {
                sortNumbers.put(s, 1);
            }});
        Optional<Integer> max = sortNumbers
                .values()
                .stream()
                .max(Integer::compareTo);
        return max.orElseThrow(() -> new RuntimeException("Can't get max list of numbers"));
    }
}
