/*
 * Hi there, if you're here to learn from my code or see how good it is: don't. It sucks
 * This code is made to create simple CheaterListings without readability in mind
 * It isn't even in a proper project folder
 */

import java.awt.datatransfer.StringSelection;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;

import java.io.File;
import java.util.Scanner;

public class AutoList {

    public static void main(String[] args) {
        createListing("input.txt");
        System.out.println("\u001B[31mListing successfully created and copied to clipboard.\u001B[0m");
    }

    public static void createListing (String path) {
        Scanner data = loadFile(path);
        Integer listingNumber = null;
        String IGN = null;
        String discord = null;
        String ID = null;
        String cheats = null;
        String date = null;
        String caughtBy = null;
        String positivity = null;
        String comment = null;
        String evidence = null;

        while (data.hasNextLine()) {
            if (listingNumber == null) {
                listingNumber = data.nextInt();
                continue;
            }
            String line = data.nextLine().replace(":", "");

            if (line.startsWith("IGN")) {
                line = commonReplace(line);
                line = line.replace("IGN", "");
                IGN = line;
            }
            else if (line.startsWith("Discord")) {
                line = commonReplace(line);
                line = line.replace("Discord", "");
                discord = line;
            }
            else if (line.startsWith("ID")) {
                if (line.equalsIgnoreCase("unk")) {
                    ID = "";
                    continue;
                }

                line = commonReplace(line);
                line = line.replace("ID", "");
                ID = line;
            }
            else if (line.startsWith("Cheats Used")) {
                line = line.replace("Cheats Used", "");
                while (line.charAt(0) == ' ') {
                    line = line.substring(1);
                }
                cheats = line;
            }
            else if (line.startsWith("Date")) {
                line = commonReplace(line);
                line = line.replace("Date", "").replace("DD/MM/YY", "");
                line = line.replace("-", "/");
                line = line.replace(".", "/");
                line = line.replace("\\", "/");
                
                if (line.charAt(line.length() - 1) == 'r') {
                    line = line.replace("r", "");
                    String[] dateArray = line.split("/", 3);
                    if (dateArray[1].charAt(0) == '0')
                        dateArray[1] = dateArray[1].substring(1);
                    date = dateArray[1] + "/" + dateArray[0] + "/" + dateArray[2];
                    continue;
                }

                if (line.charAt(0) == '0')
                    line = line.substring(1);
                date = line;
            }
            else if (line.startsWith("Comment")) {
                line = line.replace("Comment", "");
                while (line.charAt(0) == ' ') {
                    line = line.substring(1);
                }
                comment = line;
            }
            else if (line.startsWith("Caught By")) {
                line = line.replace("Caught By", "");
                while (line.charAt(0) == ' ') {
                    line = line.substring(1);
                }
                caughtBy = line;
            }
            else if (line.startsWith("Positive")) {
                line = commonReplace(line);
                line = line.replace("Positive", "");
                positivity = line;
            }
            else if (line.startsWith("Evidence")) {
                evidence = "";
                while (data.hasNextLine()) {
                    evidence += data.nextLine() + "\n";
                }
            }
        }
        data.close();

        String output = listingNumber + "." +
                "\nIGN: " + IGN +
                "\nDiscord: " + discord + " " + ID +
                "\nCheats used: " + cheats +
                "\nDate: " + date +
                "\nCaught by: " + caughtBy +
                "\nPositive?: " + positivity +
                "\nComment: " + comment +
                "\nEvidence:" + 
                "\n\n\n[original evidence]\n" + evidence;
        try {
            // copy to clipboard code directly taken from https://stackoverflow.com/a/6713290
            StringSelection stringSelection = new StringSelection(output);
            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            clipboard.setContents(stringSelection, null);
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public static Scanner loadFile(String path) {
        File input = new File("input.txt");
        Scanner data = null;

        try {
            data = new Scanner(input);
        } catch (Exception e) {
            System.err.println(e);
        }
        return data;
    }
    public static String commonReplace(String input) {
        return input.replace(" ", "").replace(".", "").replace(",", "").replace("-", "").replace("?", "");
    }
}
