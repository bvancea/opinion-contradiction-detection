package contradiction.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

/**
 * Created with IntelliJ IDEA.
 * User: bogdan
 * Date: 5/11/13
 * Time: 9:56 PM
 * To change this template use File | Settings | File Templates.
 */
public class ParserUtil {

    public static void parseWords(String fileName, List<String> words ){
        File file = new File(fileName);
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
            while (scanner.hasNext()) {
                words.add(scanner.nextLine());
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    public static void parseSentimentWords(String fileName, List<String> sentiments, List<Double> sentimentOrientations) {
        File file = new File(fileName);
        Scanner scanner = null;
        try {
            scanner = new Scanner(file);
            while (scanner.hasNext()) {
                String line = scanner.nextLine();
                String[] tokens = line.split("\t");

                String sentimentWord = tokens[0];
                Double sentimentOrientation = Double.parseDouble(tokens[1])/5;

                sentiments.add(sentimentWord);
                sentimentOrientations.add(sentimentOrientation);
            }
            scanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (InputMismatchException e) {
            e.printStackTrace();
        }
    }
}
