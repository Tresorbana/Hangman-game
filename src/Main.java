import java.io.*;
import java.util.*;

public class Main {
    public static final int MAX_TRIES = 6;
    public static final String WORDS_FILE = "C:\\Users\\treso\\IdeaProjects\\Hangman\\src\\words.txt";
    public static final String SCORES_FILE = "C:\\Users\\treso\\IdeaProjects\\Hangman\\src\\scores.txt";

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        List<String> words = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(WORDS_FILE))) {
            String line;
            while ((line = br.readLine()) != null) {
                words.add(line.trim().toLowerCase());
            }

            if (words.isEmpty()) {
                System.out.println("No words found");
                return;
            }

            String word = words.get(new Random().nextInt(words.size()));
            char[] guessArr = new char[word.length()];
            Arrays.fill(guessArr, '_');

            int tries = 0;
            while (tries < MAX_TRIES && !String.valueOf(guessArr).equals(word)) {
                System.out.println("Word = " + String.valueOf(guessArr) + " | Tries left: " + (MAX_TRIES - tries));
                System.out.print("Enter a letter: ");
                char input = scanner.nextLine().toLowerCase().charAt(0);

                boolean ok = false;
                for (int i = 0; i < word.length(); i++) {
                    if (word.charAt(i) == input && guessArr[i] == '_') {
                        guessArr[i] = input;
                        ok = true;
                    }
                }

                if (!ok) {
                    tries++; // increment only once if the guess is wrong
                    System.out.println("Wrong guess!");
                }
            }

            String result = String.valueOf(guessArr).equals(word) ? "WIN" : "LOSE";
            System.out.println(result + "! Word was: " + word);

            // Save score
            try (BufferedWriter bw = new BufferedWriter(new FileWriter(SCORES_FILE, true))) {
                bw.write(result + " | " + word + " | tries: " + tries);
                bw.newLine();
            }

            // Show history
            System.out.println("\nHistory:");
            try (BufferedReader reader = new BufferedReader(new FileReader(SCORES_FILE))) {
                String len;
                while ((len = reader.readLine()) != null) {
                    System.out.println(len);
                }
            }

        } catch (FileNotFoundException e) {
            System.out.println("No file was found");
        } catch (IOException e) {
            System.out.println("Something went wrong");
        }

        scanner.close();
    }
}
