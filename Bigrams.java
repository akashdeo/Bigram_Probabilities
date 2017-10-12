//package Part1;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

/**
 *
 * @author akash
 */
public class Bigrams {

    /**
     * @param args the command line arguments
     * @throws java.io.FileNotFoundException
     */
    String first;
    String second;

    Bigrams(String a, String b) {
        first = a;
        second = b;
    }

    public static void main(String[] args) throws FileNotFoundException, IOException {
        // TODO code application logic here
        Scanner sc = new Scanner(System.in);
        FileReader fReader = new FileReader(args[0]);
        BufferedReader bReader = new BufferedReader(fReader);
        BufferedReader br = new BufferedReader(fReader);
        Map<String, Integer> wordCounter = new HashMap<>();
        String all = "";
        String line = "";
        while ((line = bReader.readLine()) != null) {
            all = all + line + " ";
            String[] words = line.split("\\s+");
            for (String word : words) {
                if (wordCounter.containsKey(word)) {
                    wordCounter.put(word, wordCounter.get(word) + 1);
                } else {
                    wordCounter.put(word, 1);
                }
            }
        }
        //System.out.println(wordCounter.get("is"));
        String[] words = all.split("\\s+");
        bReader.close();
        int wordCount = 0;
        wordCount = wordCounter.size();
        //System.out.println(wordCount);
        String[][] wordArray = new String[wordCount + 1][wordCount + 1];
        wordArray[0][0] = "";
        int i = 1;
        for (Map.Entry<String, Integer> entry : wordCounter.entrySet()) {
            String key = entry.getKey();
            wordArray[0][i] = key;
            wordArray[i][0] = key;
            i++;
        }
        Map<Bigrams, Integer> bigramsCounter = new HashMap<>();
        for (int len = 0; len < words.length - 1; len += 1) {
            String firstString = words[len];
            String secondString = words[len + 1];
            Bigrams bigram = new Bigrams(firstString, secondString);
            if (bigramsCounter.containsKey(bigram)) {
                bigramsCounter.put(bigram, bigramsCounter.get(bigram) + 1);
            } else {
                bigramsCounter.put(bigram, 1);
            }

        }
        line = "";
        String sentence = "";

        System.out.println("Enter the sentence");
        sentence = sc.nextLine();

        String[] sentenceWords = sentence.split("\\s+");
        System.out.println("enter the smoothing degree 1. No Smoothing 2. One Smoothing 3. Good Turing Smoothing");
        int smoothingDegree = sc.nextInt();
        double probability = 1.0;
        int count = 0;
        String[][] sentenceWordsbyWordsMATRIX = new String[sentenceWords.length + 1][sentenceWords.length + 1];
        sentenceWordsbyWordsMATRIX[0][0] = "";
        for (int row = 1; row < sentenceWords.length + 1; row++) {
            for (int col = 1; col < sentenceWords.length + 1; col++) {
                sentenceWordsbyWordsMATRIX[row][col] = "0";
            }
        }
        for (int row = 1; row < sentenceWords.length + 1; row++) {

            sentenceWordsbyWordsMATRIX[row][0] = sentenceWords[count];
            sentenceWordsbyWordsMATRIX[0][row] = sentenceWords[count];
            count++;

        }

        bigramsCounter.entrySet().forEach((entry) -> {
            Bigrams key = entry.getKey();
            for (int row = 1; row < sentenceWords.length + 1; row++) {
                for (int col = 1; col < sentenceWords.length + 1; col++) {
                    if (sentenceWordsbyWordsMATRIX[row][0].equals(key.first) && sentenceWordsbyWordsMATRIX[0][col].equals(key.second)) {
                        sentenceWordsbyWordsMATRIX[row][col] = String.valueOf(entry.getValue());
                    }
                }
            }
        });
        DecimalFormat df = new DecimalFormat("#.#####");
        switch (smoothingDegree) {
            case 1:
                System.out.println("Bigram counts for the given sentence");
                for (int row = 0; row < sentenceWords.length + 1; row++) {
                    for (int col = 0; col < sentenceWords.length + 1; col++) {
                        System.out.print(sentenceWordsbyWordsMATRIX[row][col] + "\t\t");
                    }
                    System.out.println("");
                }
                System.out.println("Bigram Probabilities for the given sentence");
                for (int row = 0; row < sentenceWords.length + 1; row++) {
                    for (int col = 0; col < sentenceWords.length + 1; col++) {
                        if (row > 0 && col > 0) {
                            double value = (Integer.parseInt(sentenceWordsbyWordsMATRIX[row][col])) / (wordCounter.get(sentenceWordsbyWordsMATRIX[row][0]) * 1.0);
                            System.out.print(df.format(value) + "\t\t");
                        } else {
                            System.out.print(sentenceWordsbyWordsMATRIX[row][col] + "\t\t");
                        }

                    }
                    System.out.println("");
                }

                for (int len = 0; len < sentenceWords.length - 1; len++) {
                    String firstPart = sentenceWords[len];
                    String secondPart = sentenceWords[len + 1];
                    g:
                    {
                        for (int row = 1; row < sentenceWords.length + 1; row++) {
                            for (int col = 1; col < sentenceWords.length + 1; col++) {
                                if (sentenceWordsbyWordsMATRIX[row][0].equals(firstPart) && sentenceWordsbyWordsMATRIX[0][col].equals(secondPart)) {
                                    double p = (double) (Integer.parseInt(sentenceWordsbyWordsMATRIX[row][col])) / (double) (wordCounter.get(firstPart));
                                    probability *= p;
                                    System.out.println("Probability of the " + (len + 1) + " biagram in the sentence: " + p);
                                    break g;
                                }
                            }
                        }
                    }
                }
                System.out.println("Probability for the given sentence with No Smoothing: " + probability);
                break;
            case 2:
                System.out.println("Bigram counts for the given sentence");
                for (int row = 0; row < sentenceWords.length + 1; row++) {
                    for (int col = 0; col < sentenceWords.length + 1; col++) {
                        if (row > 0 && col > 0) {
                            int value = Integer.parseInt(sentenceWordsbyWordsMATRIX[row][col]) + 1;
                            System.out.print(value + "\t\t");
                        } else {
                            System.out.print(sentenceWordsbyWordsMATRIX[row][col] + "\t\t");
                        }
                    }
                    System.out.println("");
                }
                System.out.println("Bigram Probabilities for the given sentence");
                for (int row = 0; row < sentenceWords.length + 1; row++) {
                    for (int col = 0; col < sentenceWords.length + 1; col++) {
                        if (row > 0 && col > 0) {
                            double value = (Integer.parseInt(sentenceWordsbyWordsMATRIX[row][col]) + 1) / ((wordCounter.get(sentenceWordsbyWordsMATRIX[row][0]) + wordCount) * 1.0);
                            System.out.print(df.format(value) + "\t\t");
                        } else {
                            System.out.print(sentenceWordsbyWordsMATRIX[row][col] + "\t\t");
                        }

                    }
                    System.out.println("");
                }
                for (int len = 0; len < sentenceWords.length - 1; len++) {
                    String firstPart = sentenceWords[len];
                    String secondPart = sentenceWords[len + 1];
                    g:
                    {
                        for (int row = 1; row < sentenceWords.length + 1; row++) {
                            for (int col = 1; col < sentenceWords.length + 1; col++) {
                                if (sentenceWordsbyWordsMATRIX[row][0].equals(firstPart) && sentenceWordsbyWordsMATRIX[0][col].equals(secondPart)) {
                                    double p = (double) (Integer.parseInt(sentenceWordsbyWordsMATRIX[row][col]) + 1) / (double) (wordCounter.get(firstPart) + wordCount);
                                    probability *= p;
                                    System.out.println("Probability of the " + (len + 1) + " biagram in the sentence: " + p);
                                    break g;
                                }
                            }
                        }
                    }
                }
                System.out.println("Probability with Add One Smoothing: " + probability);
                break;
            case 3:
                //System.out.println("Bigram counts for the given sentence");
                String[][] countMatrix = new String[sentenceWords.length + 1][sentenceWords.length + 1];
                count = 0;
                String[][] probabilityMatrix = new String[sentenceWords.length + 1][sentenceWords.length + 1];
                for (int row = 1; row < sentenceWords.length + 1; row++) {

                    countMatrix[row][0] = sentenceWords[count];
                    countMatrix[0][row] = sentenceWords[count];
                    count++;

                }
                count = 0;
                for (int row = 1; row < sentenceWords.length + 1; row++) {

                    probabilityMatrix[row][0] = sentenceWords[count];
                    probabilityMatrix[0][row] = sentenceWords[count];
                    count++;

                }
                countMatrix[0][0] = " ";
                probabilityMatrix[0][0] = " ";
                for (int row = 1; row < sentenceWords.length + 1; row++) {
                    for (int col = 1; col < sentenceWords.length + 1; col++) {

                        countMatrix[row][col] = "0";
                        probabilityMatrix[row][col] = "0";
                    }
                    //System.out.println("");
                }

                for (int len = 0; len < sentenceWords.length - 1; len++) {
                    int valueOfBigram = 0;
                    int sumOfCountsOfBigrams = 0;
                    int countOfCurrentBigram = 0;
                    int countOfNPlusOneBiagram = 0;
                    String firstPart = sentenceWords[len];
                    String secondPart = sentenceWords[len + 1];
                    g:
                    {

                        for (int row = 1; row < sentenceWords.length + 1; row++) {
                            for (int col = 1; col < sentenceWords.length + 1; col++) {
                                if (sentenceWordsbyWordsMATRIX[row][0].equals(firstPart) && sentenceWordsbyWordsMATRIX[0][col].equals(secondPart)) {
                                    valueOfBigram = Integer.parseInt(sentenceWordsbyWordsMATRIX[row][col]);

                                    break g;
                                }
                            }
                        }
                    }
                    if (valueOfBigram == 0) {
                        countOfCurrentBigram = 1;
                        for (Map.Entry<Bigrams, Integer> entry : bigramsCounter.entrySet()) {
                            int value = entry.getValue();
                            sumOfCountsOfBigrams += value;

                            if (value == valueOfBigram + 1) {
                                countOfNPlusOneBiagram++;
                            }
                        }
                    } else {

                        for (Map.Entry<Bigrams, Integer> entry : bigramsCounter.entrySet()) {
                            int value = entry.getValue();
                            sumOfCountsOfBigrams += value;
                            if (value == valueOfBigram) {
                                countOfCurrentBigram++;
                            }
                            if (value == valueOfBigram + 1) {
                                countOfNPlusOneBiagram++;
                            }
                        }
                    }

                    //sentenceWordsbyWordsMATRIX[r][c] = "" + countOfNPlusOneBiagram;
                    double numerator = (double) ((valueOfBigram + 1) * (countOfNPlusOneBiagram));
                    double denominator = (double) (countOfCurrentBigram * sumOfCountsOfBigrams);
                    double p = ((double) numerator) / ((double) denominator);
                    probability *= p;
                    System.out.println("Probability of " + (len + 1) + " biagram is: " + p);

                }
                String first = "",
                 second = "";

                for (int row = 1; row < sentenceWords.length + 1; row++) {
                    for (int col = 1; col < sentenceWords.length + 1; col++) {
                        first = countMatrix[row][0];
                        second = countMatrix[0][col];
                        Bigrams b = new Bigrams(first, second);
                        int counterOfNPlusOne = 0;
                        int counter = 0;
                        int value = 0;
                        int sumofbigramcounts = 0;
                        if (bigramsCounter.containsKey(b)) {
                            value = bigramsCounter.get(b);

                            for (Map.Entry<Bigrams, Integer> entry : bigramsCounter.entrySet()) {
                                sumofbigramcounts += entry.getValue();
                                if (entry.getValue() == value + 1) {
                                    counterOfNPlusOne++;
                                }
                                if (entry.getValue() == value) {
                                    counter++;
                                }
                            }

                        } else {

                            for (Map.Entry<Bigrams, Integer> entry : bigramsCounter.entrySet()) {
                                sumofbigramcounts += entry.getValue();
                                if (entry.getValue() == value + 1) {
                                    counterOfNPlusOne++;
                                }
                                counter = 1;
                            }
                        }
                        countMatrix[row][col] = counterOfNPlusOne + "";
                        probabilityMatrix[row][col] = ((value + 1) * counterOfNPlusOne) / (1.0 * counter * sumofbigramcounts) + "";
                    }
                }
                System.out.println("Bigram counts with (c+1) counts for the given sentence");
                for (int row = 0; row < sentenceWords.length + 1; row++) {
                    for (int col = 0; col < sentenceWords.length + 1; col++) {

                        System.out.print(countMatrix[row][col] + "\t\t");
                    }
                    System.out.println("");
                }
                System.out.println("Probabilty table for the given sentence");
                for (int row = 0; row < sentenceWords.length + 1; row++) {
                    for (int col = 0; col < sentenceWords.length + 1; col++) {
                        if (row == 0 || col == 0) {
                            System.out.print(probabilityMatrix[row][col] + "\t\t\t\t");
                        }
                        else{
                            System.out.print(probabilityMatrix[row][col] + "\t\t");
                        }
                    }
                    System.out.println("");
                }
                System.out.println("Probability with Good Turing Discounting: " + probability);
                break;
            default:
                break;
        }
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 37 * hash + Objects.hashCode(this.first);
        hash = 37 * hash + Objects.hashCode(this.second);
        return hash;
    }

    @Override
    public boolean equals(Object obj
    ) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Bigrams other = (Bigrams) obj;
        if (!Objects.equals(this.first, other.first)) {
            return false;
        }
        return Objects.equals(this.second, other.second);
    }

}
