package model;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class FileHandling {


    public Boolean exportData(String filesource, UniLink unilink) {
        try {
        FileWriter fw = new FileWriter(filesource + "/Posts.txt");
        BufferedWriter output = new BufferedWriter(fw);
        Iterator<Post> iterator = unilink.getPostCollection().iterator();  //Using an iterator to remove/delete the post

            while (iterator.hasNext()) {
                Post currentPost = iterator.next();
                if (currentPost instanceof Event) {
                    Event obj = (Event) currentPost;

                    output.write(obj.toString());


                    output.newLine();
                } else if (currentPost instanceof Sale) {
                    Sale obj = (Sale) currentPost;
                    output.write(obj.toString());
                    output.newLine();
                } else if (currentPost instanceof Job) {
                    Job obj = (Job) currentPost;
                    output.write(obj.toString());
                    output.newLine();
                }


            }

            output.close();

        } catch (Exception e) {
            return false;

        }
        return true;
    }


    public Boolean importData (String filepath,UniLink unilink) {

        try {

            File file =
                    new File(filepath);
            Scanner sc = new Scanner(file);
          //  sc.useDelimiter("~");

            while (sc.hasNextLine()) {

                ArrayList<String> finalPostString = new ArrayList<String>();
                ArrayList<Reply> replyList = new ArrayList<>();

                String line = sc.nextLine();
                line = line.replaceAll("\'","");
            //    System.out.println(line);
                String[] currentLine=line.split("~");

                for(String current : currentLine) {

                    if (current.split("=")[0].trim().equals("replyList")) {
                        String[] abc;
                        //System.out.println(current);
                          current = current.substring(12, current.length() - 1).trim();
                 //         System.out.println(current);
                        abc = current.split(",");
                        if(!current.equals(""))
                        for (String i : abc) {
                            ArrayList<String> finalReplyString = new ArrayList<String>();
                      //      System.out.println(abc.length);
                            String[] def = i.split("\\|");
                            //     System.out.println(i);
                            for (String j : def) {
                               //  System.out.println(j.split("=")[0]);
                                 //System.out.println(j.split("=")[1]);
                                 finalReplyString.add(j.split("=")[1]);
                            }
                         Reply reply = new Reply(finalReplyString.get(0),Double.parseDouble(finalReplyString.get(1)),finalReplyString.get(2));
                       //     System.out.println(finalReplyString.get(0) + finalReplyString.get(1) + finalReplyString.get(2));
                           replyList.add(reply);
                        }
                    } else {
                       //  System.out.println(current.split("=")[0]);
                        //System.out.println(current.split("=")[1]);
                        finalPostString.add(current.split("=")[1]);
//
                    }
                }

              //  System.out.println(finalPostString.get(0) + finalPostString.get(1) + finalPostString.get(2));
                if(finalPostString.get(0).startsWith("EVE"))
                {


                    Event event = new Event(finalPostString.get(0),finalPostString.get(1),finalPostString.get(2),finalPostString.get(6),finalPostString.get(7),Integer.parseInt(finalPostString.get(8)),finalPostString.get(3),finalPostString.get(5));
                    event.setStatus(finalPostString.get(4));
                    event.setAttendee_count(Integer.parseInt(finalPostString.get(9)));
                    event.setReplyList(replyList);

                    unilink.getPostCollection().add(event);
                    System.out.println(event.toString());


                }
                else if(finalPostString.get(0).startsWith("SAL"))
                {
                    Sale sale = new Sale(finalPostString.get(0),finalPostString.get(1),finalPostString.get(2),Double.parseDouble(finalPostString.get(6)),Double.parseDouble(finalPostString.get(8)),finalPostString.get(3),finalPostString.get(5));

                    //Event sale = new Event(finalPostString.get(0),finalPostString.get(1),finalPostString.get(2),finalPostString.get(6),finalPostString.get(7),Integer.parseInt(finalPostString.get(8)),finalPostString.get(3),finalPostString.get(5));
                    sale.setStatus(finalPostString.get(4));
                    sale.setHighest_offer(Double.parseDouble(finalPostString.get(7)));
                    //event.setAttendee_count(Integer.parseInt(finalPostString.get(9)));
                    sale.setReplyList(replyList);
                    unilink.getPostCollection().add(sale);
                    System.out.println(sale.toString());
                }
                else if(finalPostString.get(0).startsWith("JOB"))

                    {
                        Job job = new Job(finalPostString.get(0),finalPostString.get(1),finalPostString.get(2),Double.parseDouble(finalPostString.get(6)),finalPostString.get(3),finalPostString.get(5));

                        //Event sale = new Event(finalPostString.get(0),finalPostString.get(1),finalPostString.get(2),finalPostString.get(6),finalPostString.get(7),Integer.parseInt(finalPostString.get(8)),finalPostString.get(3),finalPostString.get(5));
                        job.setStatus(finalPostString.get(4));
                        job.setLowest_offer(Double.parseDouble(finalPostString.get(7)));
                       // sale.setHighest_offer(Double.parseDouble(finalPostString.get(7)));
                        //event.setAttendee_count(Integer.parseInt(finalPostString.get(9)));
                        job.setReplyList(replyList);
                        unilink.getPostCollection().add(job);
                        System.out.println(job.toString());
                    }
                }


        } catch (Exception ex) {
            ex.printStackTrace();
            return false;

        }
        return  true;
    }
}
