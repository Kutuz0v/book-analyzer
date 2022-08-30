package com.hurin;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.*;
import java.util.Map;

@Component
public class ReconTool implements CommandLineRunner {
    @Autowired
    RestService restService;

    private void main(String[] args) throws IOException {
        File book = new File(args[0]);

        FileAnalyzer analyzer = new FileAnalyzer(book);
        Map<String, Integer> analyzedBookMap = analyzer.getAnalyzedBookMap();
        File wordsTable = new File(book.getName() + " analyzed.csv");
        if (!wordsTable.exists())
            wordsTable.createNewFile();

        BufferedWriter writer = new BufferedWriter(new FileWriter(wordsTable));
        int counter = 0;

        for (Map.Entry<String, Integer> word : analyzedBookMap.entrySet()) {
            writer.write(
                    String.format(
                            "%d,%s,=GOOGLETRANSLATE(B%d; \"en\"; \"uk\")\n",
                            word.getValue(), word.getKey(), ++counter));
        }
        System.out.println("Done!");
    }

    @Override
    public void run(String... args) throws Exception {
        main(args);
    }
}

