package Package1;

import java.util.*;
import java.io.*;

public class Main {

    public static void main(String[] args) {
        ArrayList<Runner> runners = new ArrayList<>();
        int lineNumber = 0;

        
        File errorFile = new File("errors.dat");
        PrintWriter errorWriter = null;

        try {
            errorWriter = new PrintWriter(errorFile);
        } catch (FileNotFoundException e) {
            System.out.println("Error opening the error output file.");
            System.exit(0);
        }

        int errorCount = 0;

        try  {
        	Scanner kb = new Scanner(new File("runners.dat"));
            while (kb.hasNext()) {
                lineNumber++;  
                String name = kb.nextLine();

                lineNumber++; 
                String country = kb.nextLine();

                Time startTime = null;
                Time endTime = null;

                try {
                    lineNumber++;  
                    startTime = new Time(kb.nextInt(), kb.nextInt(), kb.nextDouble());

                    lineNumber++;  
                    endTime = new Time(kb.nextInt(), kb.nextInt(), kb.nextDouble());

                    runners.add(new Runner(name, country, startTime, endTime));
                } catch (InputMismatchException e) {
                    errorWriter.println("line " + lineNumber + ": mismatch error for " + name + ", skipping to next runner");
                    errorCount++;

                    if (startTime == null) { 
                        kb.nextLine();
                    }
                }

                if (kb.hasNext()) {
                    kb.nextLine();
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("File not found.");
        }

        errorWriter.println("Total errors: " + errorCount);
        errorWriter.close();

        
        printRunners(runners);

       
        printMedalists(runners);
    }

    public static void printRunners(ArrayList<Runner> runners) {
        try (PrintWriter writer = new PrintWriter(new File("results.dat"))) {
            writer.println("All Runners:");
            for (Runner runner : runners) {
                writer.println(runner);
                writer.println();
            }

            writer.println("\nMedal Winners:");
        } catch (FileNotFoundException e) {
            System.out.println("Error opening results.dat for writing.");
        }
    }

    public static void printMedalists(ArrayList<Runner> runners) {
        Runner gold = null, silver = null, bronze = null;

        for (Runner runner : runners) {
            if (gold == null || runner.compareTo(gold) < 0) {
                bronze = silver;
                silver = gold;
                gold = runner;
            } else if (silver == null || runner.compareTo(silver) < 0) {
                bronze = silver;
                silver = runner;
            } else if (bronze == null || runner.compareTo(bronze) < 0) {
                bronze = runner;
            }
        }

        try (PrintWriter writer = new PrintWriter(new FileOutputStream(new File("results.dat"), true))) {
            writer.println("Gold: " + gold);
            writer.println();
            writer.println("Silver: " + silver);
            writer.println();
            writer.println("Bronze: " + bronze);
        } catch (FileNotFoundException e) {
            System.out.println("Error opening results.dat for writing.");
        }
    }
}

