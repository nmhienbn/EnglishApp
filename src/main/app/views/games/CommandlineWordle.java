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
    private static final String NOCOLOR = "\u001B[0m";
    private static final String GREEN = "\u001B[42m";
    private static final String YELLOW = "\u001B[43m";
    private static final String GRAY = "\u001B[100m";
    private static final String KEYBOARD = "q w e r t y u i o p\n a s d f g h j k l\n  z x c v b n m";//"abcdefghijklmnopqrstuvwxyz";


    public static void main(String[] args) {
        Scanner input = new Scanner(System.in);
        String[] words = new String[6];
        List<String> wordList = new ArrayList<>();
        char[] answer;

        String filePath = System.getProperty("user.dir") + "\\src\\main\\resources\\game\\winning-words.txt";
        try (Scanner cin = new Scanner(new FileReader(filePath))) {
            while (cin.hasNextLine()) {
                wordList.add(cin.nextLine());
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return;
        }


        // Set blank words
        for (int i = 0; i < 6; i++) {
            words[i] = "-----";
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


            answer = wordList.get((int) (Math.random() * 2308)).toCharArray();
            System.out.println("Begin!");

            while (guessIndex < 6) {
                StringBuilder KEYBOARDInfo = new StringBuilder();

                for (int i = 0; i < KEYBOARD.length(); i++) {
                    if (green.contains(KEYBOARD.charAt(i))) {
                        KEYBOARDInfo.append(GREEN);
                    } else if (yellow.contains(KEYBOARD.charAt(i))) {
                        KEYBOARDInfo.append(YELLOW);
                    } else if (gray.contains(KEYBOARD.charAt(i))) {
                        KEYBOARDInfo.append(GRAY);
                    } else {
                        KEYBOARDInfo.append(KEYBOARD.charAt(i));
                        continue;
                    }
                    KEYBOARDInfo.append(KEYBOARD.charAt(i)).append(NOCOLOR);
                }
                System.out.println("\n" + String.join("\n", words));
                System.out.println(KEYBOARDInfo + "\n");
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

                if (guess.equals("next") || guess.equals("skip") || guess.equals("new")) {
                    correct = false;
                    break;
                }

                // Exit the program
                if (guess.equals("exit") || guess.equals("stop")) {
                    break gameLoop;
                }


                if (guess.length() != 5) {
                    System.out.println("Must be 5 letters!");
                    continue;
                }

                if (!wordList.contains(guess)) {
                    System.out.println("Not in the wordList!");
                    continue;
                }

                /*
                 * First, look for correct positions. Then, consider yellow/gray.
                 * Order matters for double-letter situations
                 */
                for (int i = 0; i < 5; i++) {
                    current = guess.charAt(i);

                    if (current == answerInfo[i]) {
                        guessColors[i] = GREEN + current + NOCOLOR;
                        answerInfo[new String(answerInfo).indexOf(current)] = '0';
                        if (!green.contains(current)) green.add(current);
                    }
                }

                for (int i = 0; i < 5; i++) {
                    current = guess.charAt(i);

                    if (guessColors[i] == null) {
                        correct = false;

                        if (new String(answerInfo).contains(String.valueOf(current))) {
                            guessColors[i] = YELLOW + current + NOCOLOR;
                            answerInfo[new String(answerInfo).indexOf(current)] = '0';
                            if (!yellow.contains(current)) yellow.add(current);
                        } else {
                            guessColors[i] = String.valueOf(current);
                            if (!gray.contains(current)) gray.add(current);
                        }
                    }
                }


                words[guessIndex] = String.join("", guessColors);

                guessIndex++;

                if (correct) break;
            }

            if (correct) {
                System.out.println("Nice! That took " + guessIndex + " tries!");
            } else {
                System.out.println("The answer was: " + new String(answer));
            }

            // Reset words
            for (int i = 0; i < guessIndex; i++) {
                words[i] = "-----";
            }
        }
    }
}
