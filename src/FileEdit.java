package src;

import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.List;
import java.util.ArrayList;
import java.io.File;
import java.util.Scanner;

public class FileEdit {
    public static void main(String[] args) {

        Scanner kb = new Scanner(System.in);
        System.out.print("Folder path: ");
        String path = kb.nextLine();

        File folder = new File(path);
        File[] listOfFiles = folder.listFiles();
        List<File> jpgList = getJpgList(listOfFiles);
        List<File> txtList = getTxtList(listOfFiles);

        for (File j : jpgList) {
            String jName = j.getName();
            String subJpg = jName.substring(0, 19);
            for (File t : txtList) {
                String tName = t.getName();
                String subTxt = tName.substring(0, 19);
                if (subJpg.equals(subTxt)) {
                    String title = getContent(path, tName);
                    String newName = path + "/(" + title + ") " + jName;
                    File newFile = new File(newName);

                    if (j.renameTo(newFile)) {
                        System.out.println(jName + ": worked");
                    } else {
                        System.out.println(jName + ": didn't work");
                    }
                }
            }
        }

        kb.close();
    }

    private static List<File> getJpgList(File[] listOFiles) {
        List<File> jpgList = new ArrayList<File>();
        for (File f : listOFiles) {
            String type = getFileExtension(f);
            if (type.equals("jpg")) {
                jpgList.add(f);
            }
        }
        return jpgList;
    }

    private static List<File> getTxtList(File[] listOFiles) {
        List<File> txtList = new ArrayList<File>();
        for (File f : listOFiles) {
            String type = getFileExtension(f);
            if (type.equals("txt")) {
                txtList.add(f);
            }
        }
        return txtList;
    }

    private static String getFileExtension(File file) {
        String fileName = file.getName();
        if (fileName.lastIndexOf(".") != -1 && fileName.lastIndexOf(".") != 0) {
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        } else {
            return "";
        }
    }

    private static String getContent(String path, String fileName) {
        String file = path.concat("/" + fileName);
        String line = "";
        try (BufferedReader br = new BufferedReader(new FileReader(file));) {
            line = br.readLine();
        } catch (IOException e) {
            System.err.printf("A fucky has ocorred uwu\n", e.getMessage());
        }
        return line;
    }
}