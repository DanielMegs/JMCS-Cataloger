package daniel;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;

public class JMCS {
    private File inputFile;
    private ArrayList<String> catalog;
    private Stack<Character> stack;

    public JMCS(File inputFile) {
        this.inputFile = inputFile;
        catalog = new ArrayList<>();
        stack = new Stack<>();
    }

    public void buildCatalog() {
        Scanner fileInputScan = null;
        try {
            fileInputScan = new Scanner(inputFile);
            search(fileInputScan);

        } catch (FileNotFoundException e) {
            System.out.println("Error - The file can't be found");
        } finally {
            if (fileInputScan != null) {
                fileInputScan.close();
            }
        }
    }

    public void search(Scanner fileInputScan) {
        while (fileInputScan.hasNextLine()) {
            String line = fileInputScan.nextLine().trim();
            if (isCode(line)) {
                processLine(line);
            }
        }
    }

    private void processLine(String line) {
        if (isControlStructure(line)) {
            catalog.add("Control Structure: " + line);
        } else {
            catalog.add("Method or Other: " + line);
        }

        for (char c : line.toCharArray()) {
            if (c == '{') {
                stack.push(c);
            } else if (c == '}' && !stack.isEmpty() && stack.peek() == '{') {
                stack.pop();
            }
        }
    }

    private boolean isControlStructure(String line) {
        return (line.startsWith("if") || line.startsWith("else") || line.startsWith("for")
                || line.startsWith("while") || line.startsWith("switch")
                || line.startsWith("try") || line.startsWith("catch") || line.startsWith("finally"));
    }

    public Boolean isCode(String str) {
        return !str.isEmpty() && !str.startsWith("//");
    }

    public String toString() {
        StringBuilder string = new StringBuilder();
        for (String line : catalog) {
            string.append(line).append("\n");
        }
        return string.toString();
    }
}