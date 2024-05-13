/*
 * Hi there, if you're here to learn from my code or see how good it is: don't. It sucks
 * This code is made to create simple CheaterListings without readability in mind
 * It isn't even in a proper project folder
 */

import java.awt.datatransfer.StringSelection;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;

import java.io.File;
import java.util.Arrays;
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
                listingNumber = Integer.parseInt(data.next().replace(".", ""));
                continue;
            }
            String line = data.nextLine().replace(":", "");

            if (line.startsWith("IGN")) {
                line = line.replace("IGN", "");
                while (line.charAt(0) == ' ') {
                    line = line.substring(1);
                }
                IGN = line;
            }
            else if (line.startsWith("Discord")) {
                try {
                    line = line.replace("Discord", "");
                while (line.charAt(0) == ' ') {
                    line = line.substring(1);
                }
                if (line.equalsIgnoreCase("unk") || line.equalsIgnoreCase("idk")) {
                    discord = "unknown";
                    ID = "";
                    continue;
                }
                discord = line.replaceAll(" ", "");
                }
                catch (Exception e) {
                    discord = "unknown";
                    ID = "";
                }
            }
            else if (line.startsWith("ID") && ID == null) {
                if (discord.equalsIgnoreCase("unknown")) {
                    continue;
                }
                line = line.replaceAll("[^0-9]", "");
                
                ID = "[" + line + "]";
            }
            else if (line.startsWith("Cheats Used")) {
                line = line.replace("Cheats Used", "");
                while (line.charAt(0) == ' ') {
                    line = line.substring(1);
                }
                cheats = line;
            }
            else if (line.startsWith("Date")) {
                line = line.replace("DD/MM/YY", "");
                line = line.replace("-", "/");
                line = line.replace(".", "/");
                line = line.replace("\\", "/");
                line = line.replaceAll("[^(0-9)|/|r]", "");
                
                while (line.length() > 0 && (! Character.isDigit(line.charAt(0)))) {
                    line = line.substring(1);
                }
                
                String[] dateArray = line.split("/", 3);
                
                if (dateArray.length < 3) {
                    date = "not provided";
                    continue;
                }
                

                if (dateArray[0].charAt(0) == '0')
                    dateArray[0] = dateArray[0].substring(1);
                
                if (dateArray[1].charAt(0) == '0')
                    dateArray[1] = dateArray[1].substring(1);

                if (dateArray[2].length() == 4)
                    dateArray[2] = dateArray[2].substring(2);


                date = dateArray[0] + "/" + dateArray[1] + "/" + dateArray[2];

                if (dateArray[2].endsWith("r")) {
                    dateArray[2] = dateArray[2].replace("r", "");
                    if (dateArray[2].length() == 4)
                        dateArray[2] = dateArray[2].substring(2);
                    date = dateArray[1] + "/" + dateArray[0] + "/" + dateArray[2];
                }
            }
            else if (line.startsWith("Comment")) {
                line = line.replace("Comment", "");
                if (line.length() <= 1) {
                    comment = "none provided";
                    continue;
                }
                while (line.charAt(0) == ' ') {
                    line = line.substring(1);
                }
                comment = line;
            }
            else if (line.startsWith("Caught By")) {
                line = line.replace("Caught By", "");
                while (line.charAt(0) == ' ') {
                    line = line.substring(1);
                    
                    if (line.length() < 1) {
                        line = "unknown";
                        break;
                    }
                     
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

        String discordOut;
        if (discord == null || discord.equalsIgnoreCase("unknown"))
            discordOut = "unknown";
        else
            discordOut = discord + " " + ID;


        String output = listingNumber + "." +
                "\nIGN: " + IGN +
                "\nDiscord: " + discordOut +
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
