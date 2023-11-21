package views.games;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Scanner;
import java.util.stream.Collectors;

public class CommandlineWordle {
    private static final String ANSI_RESET = "\u001B[0m";
    private static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
    private static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
    private static final String ANSI_GRAY_BACKGROUND = "\u001B[100m";
    private static final String ALPHABET = "q w e r t y u i o p\n a s d f g h j k l\n  z x c v b n m";//"abcdefghijklmnopqrstuvwxyz";


    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String[] guesses = new String[6];
        List<String> vocabulary = new ArrayList<>();
        char[] answer;

        String filePath = System.getProperty("user.dir") + "\\src\\main\\resources\\game\\winning-words.txt";
        try (Scanner cin = new Scanner(new FileReader(filePath))) {
            while (cin.hasNextLine()) {
                vocabulary.add(cin.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }


        // Set blank guesses
        for (int i = 0; i < 6; i++) {
            guesses[i] = "-----";
        }

        gameLoop: while (true) {
            System.out.println("Welcome to Wordle!");
            System.out.println("[1] Play game\n" +
                                "[2] Tutorial\n" +
                                "[3] Exit");
            Scanner cin = new Scanner(System.in);
            int t;
            t = cin.nextInt();

            if (t == 2) {
                System.out.println("Wordle is a game where you try to guess a 5-letter word.\n" +
                                    "You have 6 tries to guess the word.\n" +
                                    "After each guess, you will be given hints as to which letters are correct.\n" +
                                    "A green letter means that the letter is in the correct position.\n" +
                                    "A yellow letter means that the letter is in the word, but not in the correct position.\n" +
                                    "A gray letter means that the letter is not in the word.\n" +
                                    "In game, you can type\"stop\" or \"exit\" to exit the game.\n" +
                                    "You can type \"next\", \"new\", or \"skip\" to skip the current word.\n" +
                                    "Good luck!");
                continue;
            } else if (t == 3) {
                break;
            }

            boolean correct = true;
            int guessIndex = 0;
            char current;
            ArrayList<Character> green = new ArrayList<>();
            ArrayList<Character> gray = new ArrayList<>();
            ArrayList<Character> yellow = new ArrayList<>();


            answer = vocabulary.get((int) (Math.random() * 2308)).toCharArray();
            System.out.println("Begin!");

            while (guessIndex < 6) {
                StringBuilder alphabetInfo = new StringBuilder();

                for (int i = 0; i < ALPHABET.length(); i++) {
                    if (green.contains(ALPHABET.charAt(i))) {
                        alphabetInfo.append(ANSI_GREEN_BACKGROUND);
                    } else if (yellow.contains(ALPHABET.charAt(i))) {
                        alphabetInfo.append(ANSI_YELLOW_BACKGROUND);
                    } else if (gray.contains(ALPHABET.charAt(i))) {
                        alphabetInfo.append(ANSI_GRAY_BACKGROUND);
                    } else {
                        alphabetInfo.append(ALPHABET.charAt(i));
                        continue;
                    }
                    alphabetInfo.append(ALPHABET.charAt(i)).append(ANSI_RESET);
                }
                System.out.println("\n" + String.join("\n", guesses));
                System.out.println(alphabetInfo + "\n");
                System.out.print("Guess: ");
                String guess = input.nextLine().toLowerCase();
                String[] guessColors = new String[5];
                /*
                 * As green/yellow/gray status of each letter is calculated,
                 * keep track of which hints have been given out as to keep
                 * them accurate (e.g. prevent two yellows for one letter)
                 */
                char[] answerInfo = answer.clone();

                correct = true;

                // Exit the program
                if (guess.equals("stop") || guess.equals("exit")) {
                    break gameLoop;
                }

                // Give up
                if (guess.equals("next") || guess.equals("new") || guess.equals("skip")) {
                    correct = false;
                    break;
                }

                if (guess.length() != 5) {
                    System.out.println("Must be 5 letters!");
                    continue;
                }

                if (!vocabulary.contains(guess)) {
                    System.out.println("Not in the vocabulary!");
                    continue;
                }

                /*
                 * First, look for correct positions. Then, consider yellow/gray.
                 * Order matters for double-letter situations
                 */
                for (int i = 0; i < 5; i++) {
                    current = guess.charAt(i);

                    if (current == answerInfo[i]) {
                        guessColors[i] = ANSI_GREEN_BACKGROUND + current + ANSI_RESET;
                        answerInfo[new String(answerInfo).indexOf(current)] = '0';
                        if (!green.contains(current)) green.add(current);
                    }
                }

                for (int i = 0; i < 5; i++) {
                    current = guess.charAt(i);

                    if (guessColors[i] == null) {
                        correct = false;

                        if (new String(answerInfo).contains(String.valueOf(current))) {
                            guessColors[i] = ANSI_YELLOW_BACKGROUND + current + ANSI_RESET;
                            answerInfo[new String(answerInfo).indexOf(current)] = '0';
                            if (!yellow.contains(current)) yellow.add(current);
                        } else {
                            guessColors[i] = String.valueOf(current);
                            if (!gray.contains(current)) gray.add(current);
                        }
                    }
                }


                guesses[guessIndex] = String.join("", guessColors);

                guessIndex++;

                if (correct) break;
            }

            if (correct) {
                System.out.println("Nice! That took " + guessIndex + " tries!");
            } else {
                System.out.println("The answer was: " + new String(answer));
            }

            // Reset guesses
            for (int i = 0; i < guessIndex; i++) {
                guesses[i] = "-----";
            }
        }
    }
}
