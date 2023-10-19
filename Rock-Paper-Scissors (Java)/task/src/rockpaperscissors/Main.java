package rockpaperscissors;

import java.io.*;
import java.nio.file.Files;
import java.util.*;

public class Main {

    private static final Scanner scanner = new Scanner(System.in);
    private static List<String> arr = List.of("rock", "paper", "scissors");

    public static void main(String[] args) {
        // write your code here
        System.out.println("Enter your name:");
        String username = scanner.nextLine();
        System.out.println("Hello, " + username);
        String options = scanner.nextLine();
        if (!options.trim().equalsIgnoreCase("")) {
            arr = Arrays.stream(options.split(",")).toList();
        }
        Map<String, Integer> map = new HashMap<>();
        for (int i = 0; i < arr.size(); i++) {
            map.put(arr.get(i), arr.size() - i);
        }
        int valueAvg = arr.size() / 2;
        System.out.println("Okay, let's start");
        while (true) {
            String userChoose = scanner.nextLine();
            if ("!exit".equalsIgnoreCase(userChoose)) {
                break;
            } else if ("!rating".equalsIgnoreCase(userChoose)) {
                System.out.println("Your rating: " + readAndWriteScore(username, false, 0));
                continue;
            } else if (arr.stream().noneMatch(e -> userChoose.equals(e))) {
                System.out.println("Invalid input");
                continue;
            }
            int n = new Random().nextInt(arr.size());
            String computerChoose = arr.get(n);
            int valueUser = map.get(userChoose);
            int valueComputer = map.get(computerChoose);

            if (valueUser == valueComputer) {
                System.out.println("There is a draw " + userChoose);
                readAndWriteScore(username, true, 50);
            } else if ((valueUser + valueAvg + 1 > valueComputer && valueUser < valueComputer) || (valueUser + valueAvg > arr.size() && valueUser + valueAvg + 1 - arr.size() > valueComputer)) {
                System.out.printf("Well done. The computer chose %s and failed\n", computerChoose);
                readAndWriteScore(username, true, 100);
            } else {
                System.out.println("Sorry, but the computer chose " + computerChoose);
                readAndWriteScore(username, true, 0);
            }
        }
        System.out.println("Bye!");
    }

    public static int readAndWriteScore(String username, boolean isWrite, int point) {
        Integer result = null;
        try (BufferedReader br = new BufferedReader(new FileReader("rating.txt"))) {
            String line = br.readLine();
            StringBuilder stringBuilder = new StringBuilder();
            while (line != null) {
                String[] strSplit = line.split(" ");
                if (username.equalsIgnoreCase(strSplit[0])) {
                    result = Integer.parseInt(strSplit[1]);
                    result += point;
                    stringBuilder.append(username).append(" ").append(result).append("\n");
                } else {
                    stringBuilder.append(line).append("\n");
                }
                line = br.readLine();
            }
            if (isWrite) {
                BufferedWriter writer = new BufferedWriter(new FileWriter("rating.txt"));
                if (result == null) {
                    result = point;
                    stringBuilder.append(username).append(" ").append(result);
                }

                writer.write(stringBuilder.toString());

                writer.close();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return result;
    }
}
