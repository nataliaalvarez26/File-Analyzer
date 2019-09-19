package edu.sdsu.cs;

import java.io.*;

import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

/**
 * Natalia Alvarez
 */

public class App {
    static Path curPath;
    private static ArrayList<String> lines = new ArrayList<>();
    private static List<String> newStats = new ArrayList<>();

    public static void main(String[] args) {
        String defPath = "./";
        if (args.length != 0) {
            new App(args);
            File curFile = new File(defPath);
            getAllFiles(curFile);
            for (String files : lines) {
                fileToString(files);
                newStats.clear();
            }
        }
    }

    private App(String[] args) {
        curPath = Paths.get(args[0]);
    }

    private static ArrayList<String> getAllFiles(File root) {
        try {
            File[] files = root.listFiles();
            for (File file : files) {
                if (file.isFile()) {

                    String s = file.getCanonicalPath();

                    if (s.substring(s.length() - 5).equals(".java")) {
                        lines.add(s);
                    }
                    if (s.substring(s.length() - 4).equals(".txt")) {
                        lines.add(s);
                    }
                } else if (file.isDirectory()) {
                    getAllFiles(file);
                }
            }
        } catch (IOException E) {
            E.printStackTrace();
        }
        return lines;
    }

    private static void fileToString(String stringPath) {
        try {
            Path filePath = Paths.get(stringPath);
            List<String> lines = Files.readAllLines(filePath, Charset.defaultCharset());

            longestLine(lines, newStats);
            averageLength(lines, newStats);
            sensitiveUnique(lines, newStats);
            insensitiveUnique(lines, newStats);
            numberTokens(lines, newStats);
            theMostFrequentToken(lines, newStats);
            numFrequentToken(lines, newStats);
            mostFrequentTokens(lines, newStats);
            leastFrequentTokens(lines, newStats);

            stringPath = stringPath.concat(".stats");
            Path newFilePath = Paths.get(stringPath);


            writeToFile(newFilePath, newStats);


        } catch (IOException E) {

            E.printStackTrace();
        }
    }

    private static void longestLine(List<String> lines, List<String> newStats) {
        int max = lines.get(0).length();
        for (String line : lines) {
            if (line.length() > max) {
                max = line.length();
            }
        }
        String line = String.format("%20s:%03d", "Longest line length", max);
        newStats.add(line);
    }

    private static void averageLength(List<String> lines, List<String> newStats) {
        int counter = 0;
        int totalLength = 0;
        for (String line : lines) {
            totalLength += line.length();
            counter++;
        }
        int averageLength = totalLength / counter;

        String line = String.format("%20s:%03d", "Average line length", averageLength);
        newStats.add(line);
    }

    private static void sensitiveUnique(List<String> lines, List<String> newStats) {
        int counter = 1;
        ArrayList<String> tokens = new ArrayList<>();
        for (String line : lines) {
            String[] split = line.split(" ");
            for (String token : split) {
                tokens.add(token);
            }
        }

        int currIndex = 0;
        boolean unique = true;
        for (String uniqueToken : tokens) {
            if (currIndex > 0) {
                for (int i = currIndex - 1; i >= 0; i--) {

                    if (uniqueToken.equals(tokens.get(i))) {
                        unique = false;
                    }
                }
                if (unique) counter++;
            }
            currIndex++;
            unique = true;
        }

        String line = String.format("%20s:%03d", "Case sensitive unique tokens", counter);
        newStats.add(line);
    }

    private static void insensitiveUnique(List<String> lines, List<String> newStats) {
        int counter = 1;
        ArrayList<String> tokens = new ArrayList<>();
        for (String line : lines) {
            String[] split = line.split(" ");
            for (String token : split) {
                tokens.add(token);
            }
        }

        int currIndex = 0;
        boolean unique = true;
        for (String uniqueToken : tokens) {
            if (currIndex > 0) {
                for (int i = currIndex - 1; i >= 0; i--) {

                    if (uniqueToken.equalsIgnoreCase(tokens.get(i))) {
                        unique = false;
                    }
                }
                if (unique) counter++;
            }
            currIndex++;
            unique = true;
        }

        String line = String.format("%20s:%03d", "Case insensitive unique tokens", counter);
        newStats.add(line);

    }

    private static void numberTokens(List<String> lines, List<String> newStats) {
        int counter = 0;
        ArrayList<String> tokens = new ArrayList<>();
        for (String line : lines) {
            String[] split = line.split("\\s+");
            for (String token : split) {
                tokens.add(token);
            }
        }
        for (String token : tokens) {
            counter++;
        }

        String line = String.format("%20s:%03d", "Number of tokens", counter);
        newStats.add(line);
    }

    private static void theMostFrequentToken(List<String> lines, List<String> newStats) {
        ArrayList<String> tokens = new ArrayList<>();

        String fileContents = "";
        for (String line : lines) {
            fileContents += (line + " ");
        }

        String[] split = fileContents.split("\\s+");

        for (int i = 0; i < split.length; i++) {
            tokens.add(split[i]);
        }
        int max = 0;
        for (String uniqueToken : tokens) {
            if (Collections.frequency(tokens, uniqueToken) > max) {
                max = Collections.frequency(tokens, uniqueToken);
            }
        }
        List<String> maxTokens = new ArrayList<>();
        for (String uniqueToken : tokens) {
            if (Collections.frequency(tokens, uniqueToken) == max && !maxTokens.contains(uniqueToken)) {
                maxTokens.add(uniqueToken);
            }
        }

        String line = " ";
        for (String token : maxTokens) {
            line += token + " ";
        }

        String output = String.format("%20s:%20s", "Most frequently occurring token(s)", line);
        newStats.add(output);

    }

    private static void numFrequentToken(List<String> lines, List<String> newStats) {
        ArrayList<String> tokens = new ArrayList<>();

        String fileContents = "";
        for (String line : lines) {
            fileContents += (line + " ");
        }

        String[] split = fileContents.split("\\s+");

        for (int i = 0; i < split.length; i++) {
            tokens.add(split[i]);
        }

        int max = 0;

        for (String token : tokens) {
            if (Collections.frequency(tokens, token) > max) {
                max = Collections.frequency(tokens, token);

            }
        }

        String line = String.format("%20s:%03d", "Occurrences of most frequent token", max);
        newStats.add(line);

    }

    private static void mostFrequentTokens(List<String> lines, List<String> newStats) {
        ArrayList<String> tokens = new ArrayList<>();
        ArrayList<String> uniqueTokens = new ArrayList<>();

        String fileContents = "";
        for (String line : lines) {
            fileContents += (line + " ");
        }

        String[] split = fileContents.split("\\s+");

        for (int i = 0; i < split.length; i++) {
            tokens.add(split[i]);
        }

        uniqueTokens.add(tokens.get(0));
        int currIndex = 0;
        boolean unique = true;
        for (String uniqueToken : tokens) {
            if (currIndex > 0) {
                for (int i = currIndex - 1; i >= 0; i--) {

                    if (uniqueToken.equals(tokens.get(i))) {
                        unique = false;
                    }
                }
                if (unique) uniqueTokens.add(uniqueToken);
            }
            currIndex++;
            unique = true;
        }

        List<Integer> tokenFrequencies = new ArrayList<>();
        for (String uniqueToken : uniqueTokens) {
            tokenFrequencies.add(Collections.frequency(tokens, uniqueToken));
        }

        int n = tokenFrequencies.size();
        for (int curIndex = 0; curIndex < n - 1; curIndex++) {
            int minIndex = curIndex;
            for (int checkIndex = curIndex + 1; checkIndex < n; checkIndex++) {
                if (tokenFrequencies.get(checkIndex) < tokenFrequencies.get(minIndex))
                    minIndex = checkIndex;
            }
            int temp = tokenFrequencies.get(minIndex);
            tokenFrequencies.set(minIndex, tokenFrequencies.get(curIndex));
            tokenFrequencies.set(curIndex, temp);

            String tempTwo = uniqueTokens.get(minIndex);
            uniqueTokens.set(minIndex, uniqueTokens.get(curIndex));
            uniqueTokens.set(curIndex, tempTwo);
        }
        newStats.add("Top Ten Most Frequent Tokens");
        for (int i = tokenFrequencies.size() - 1; i > tokenFrequencies.size() - 11; i--) {
            String line = String.format("%20s:%03d", uniqueTokens.get(i), tokenFrequencies.get(i));
            newStats.add(line);
        }
    }

    private static void leastFrequentTokens(List<String> lines, List<String> newStats) {
        ArrayList<String> tokens = new ArrayList<>();
        ArrayList<String> uniqueTokens = new ArrayList<>();

        String fileContents = "";
        for (String line : lines) {
            fileContents += (line + " ");
        }

        String[] split = fileContents.split("\\s+");

        for (int i = 0; i < split.length; i++) {
            tokens.add(split[i]);
        }

        int currIndex = 0;
        boolean unique = true;
        for (String uniqueToken : tokens) {
            if (currIndex > 0) {
                for (int i = currIndex - 1; i >= 0; i--) {

                    if (uniqueToken.equals(tokens.get(i))) {
                        unique = false;
                    }
                }
                if (unique) uniqueTokens.add(uniqueToken);

            }
            currIndex++;
            unique = true;
        }

        List<Integer> tokenFrequencies = new ArrayList<Integer>();
        for (String uniqueToken : uniqueTokens) {

            tokenFrequencies.add(Collections.frequency(tokens, uniqueToken));
        }
        int n = tokenFrequencies.size();
        for (int curIndex = 0; curIndex < n - 1; curIndex++) {
            int minIndex = curIndex;
            for (int checkIndex = curIndex + 1; checkIndex < n; checkIndex++) {
                if (tokenFrequencies.get(checkIndex) < tokenFrequencies.get(minIndex))
                    minIndex = checkIndex;
            }
            int temp = tokenFrequencies.get(minIndex);
            tokenFrequencies.set(minIndex, tokenFrequencies.get(curIndex));
            tokenFrequencies.set(curIndex, temp);

            String tempTwo = uniqueTokens.get(minIndex);
            uniqueTokens.set(minIndex, uniqueTokens.get(curIndex));
            uniqueTokens.set(curIndex, tempTwo);
        }
        newStats.add("Top Ten Least Frequent Tokens");
        for (int i = 0; i < 10; i++) {

            String line = String.format("%20s:%03d", uniqueTokens.get(i), tokenFrequencies.get(i));
            newStats.add(line);
        }
    }

    private static void writeToFile(Path location, List<String> toWrite) {
        try {
            Files.write(location, toWrite, Charset.defaultCharset());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
