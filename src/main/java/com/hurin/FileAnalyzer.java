package com.hurin;

import java.io.*;
import java.util.*;

public class FileAnalyzer {
    private File book;

    FileAnalyzer(File book) throws InvalidObjectException {
        if (book.canRead())
            this.book = book;
        else
            throw new InvalidObjectException("Cannot read " + book);
    }

    public Map<String, Integer> getAnalyzedBookMap() throws FileNotFoundException {

        BufferedReader reader = new BufferedReader(new FileReader(book));

        Map<String, Integer> table = new HashMap<>();

        List<String> lines = reader.lines().toList();

        List<String> words = new ArrayList<>();

        lines.forEach(word -> {
            words.addAll(
                    Arrays.stream(word.replaceAll("[^a-zA-Z ]", "")
                                    .toLowerCase()
                                    .split("\\s+"))
                            .toList()
            );
        });

        words.forEach(word -> {
            if (table.containsKey(word))
                table.put(word, table.get(word) + 1);
            else table.put(word, 1);
        });

        return sortByValue(table);
    }

    public <K, V extends Comparable<? super V>> Map<K, V> sortByValue(Map<K, V> map) {
        List<Map.Entry<K, V>> list = new ArrayList<>(map.entrySet());
        list.sort(Map.Entry.comparingByValue());
        Collections.reverse(list);

        Map<K, V> result = new LinkedHashMap<>();
        for (Map.Entry<K, V> entry : list) {
            result.put(entry.getKey(), entry.getValue());
        }

        return result;
    }
}
