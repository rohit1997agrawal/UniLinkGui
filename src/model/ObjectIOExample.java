package model;

import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class ObjectIOExample {

    private static final String filepath="D:\\Rohini-Assignment\\Assignment - 2\\Posts.txt";

    public static void main(String args[]) {

        ObjectIOExample objectIO = new ObjectIOExample();
        objectIO.ReadObjectFromFile(filepath);

//        //Read object from file
//        Post st = (Post) objectIO.ReadObjectFromFile(filepath);
//        System.out.println(st);
    }

    public void ReadObjectFromFile(String filepath) {

        try {

            File file =
                    new File(filepath);
            Scanner sc = new Scanner(file);
          //  sc.useDelimiter("~");

            while (sc.hasNextLine()) {
                String line = sc.nextLine();
            //    System.out.println(line);
                String[] currentLine=line.split("~");

                for(String current : currentLine) {
                    if (current.split("=")[0].trim().equals("replyList")) {
                        String[] abc;
                        //System.out.println(current);
                          current = current.substring(12, current.length() - 1);
                        //   System.out.println(current);
                        abc = current.split(",");

                        for (String i : abc) {

                            String[] def = i.split("\\|");
                            //     System.out.println(i);
                            for (String j : def) {
                                 System.out.println(j.split("=")[0]);
                                 System.out.println(j.split("=")[1]);
                            }
                        }
                    } else {
                         System.out.println(current.split("=")[0]);
                        System.out.println(current.split("=")[1]);
//                System.out.println(sc.next());
//                System.out.println(sc.next());
//                System.out.println(sc.next());
                    }
                }
            }

        } catch (Exception ex) {
            ex.printStackTrace();

        }
    }
}
