package pl.coderslab;


import org.apache.commons.lang3.ArrayUtils;
import org.apache.commons.lang3.math.NumberUtils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class TaskManager<tab> {
    static final String FILE_NAME = "tasks.csv";
    static final String[] OPTIONS = new String[]{"add", "remove", "list", "exit"};
    static String[][] tasks;

    public static void main(String[] args) {
        tasks = saveDataInTheTable(FILE_NAME);
        displayOptions(OPTIONS);
        Scanner scan = new Scanner(System.in);
        while (scan.hasNextLine()) {
            String input = scan.nextLine();

            switch (input) {
                case "add":
                    addTask();
                    break;
                case "remove":
                    removeTask(tasks, getNumberOfLine());
                    break;
                case "list":
                    displayUpdatedTab(tasks);
                    break;
                case "exit":
                    saveTableToTheFile(FILE_NAME,tasks);
                    System.out.println(ConsoleColors.RED + "bye bye");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Please select a correct option.");
            }
            displayOptions(OPTIONS);
        }
    }

    public static String[][] saveDataInTheTable(String fileName) {
        Path path = Paths.get(fileName);
        try {
            List<String> lines = Files.readAllLines(path);
            tasks = new String[lines.size()][];
            for (int i = 0; i < lines.size(); i++) {
                String line = lines.get(i);
                String[] split = line.split("'");
                tasks[i] = split;
            }
        } catch (IOException e) {
            System.out.println("File cannot be found.");
        }
        return tasks;
    }


    public static void displayOptions(String[] tab) {
        System.out.print(ConsoleColors.BLUE);
        System.out.println("Please select an option: " + ConsoleColors.RESET);
        for (String element : tab) {
            System.out.println(element);
        }

    }

    public static void addTask() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Please add task description: ");
        String description = scan.nextLine();
        System.out.println("Please add task due date: ");
        String dueDate = scan.nextLine();
        System.out.println("Is your task important: true/false.");
        String importance = scan.nextLine();
        tasks = Arrays.copyOf(tasks, tasks.length + 1);
        tasks[tasks.length - 1] = new String[3];
        tasks[tasks.length - 1][0] = description;
        tasks[tasks.length - 1][1] = dueDate;
        tasks[tasks.length - 1][2] = importance;
    }

    public static void displayUpdatedTab(String[][] tasks) {
        for (int i = 0; i < tasks.length; i++) {
            System.out.print(i + " : ");
            for (int j = 0; j < tasks[i].length; j++) {
                System.out.print(tasks[i][j] + " ");
            }
            System.out.println();
        }
    }


    public static int getNumberOfLine() {
        Scanner scan = new Scanner(System.in);
        System.out.println("Please select number to remove.");

        String numberToRemove = scan.nextLine();
        while (!checkIfNumberIsEqualOrBiggerThanZero(numberToRemove)) {
            System.out.println("This is not a number. Please provide number greater or equal 0.");
            scan.nextLine();
        }
        return Integer.parseInt(numberToRemove);
    }


    public static boolean checkIfNumberIsEqualOrBiggerThanZero(String input) {
        if (NumberUtils.isParsable(input)) {
            return Integer.parseInt(input) >= 0;
        }
        return false;
    }


    public static void removeTask(String[][] tab, int index) {
        if (index < tasks.length) {
            tasks = ArrayUtils.remove(tab, index);
        }

    }

    public static void saveTableToTheFile(String fileName, String [][]tab){
        Path path = Paths.get(fileName);

        String[] lines = new String[tasks.length];
        for (int i = 0; i <tasks.length ; i++) {
            lines[i] = String.join(",",tab[i]);
        }
        try {
            Files.write(path, Arrays.asList(lines));
        }catch (IOException e){
            System.out.println("File cannot be found");
        }
    }
}




