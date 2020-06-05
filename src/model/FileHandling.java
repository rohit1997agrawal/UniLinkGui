package model;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Scanner;

public class FileHandling {


    /*
    Function to export data to file Posts.txt in the selected directory
    Modified and Used toString() to write post/reply details to text file

    3 types of delimiters are used to extract data
    All details related to a Post separated by the delimiter "~"
    Each Reply in a reply list is separated by a delimiter "|"
    Data related to a Reply are separated by a delimiter ","
     */
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


    /*
   Function to import file Posts.txt from the selected path
   The text file is parsed line by line , to convert and store values to Corresponding objects
   And merged into the postCollection

   Each line contains Post and Reply Details for one particular post
   3 types of delimiters are used to extract data
   All details related to a Post separated by the delimiter "~"
   Each Reply in a reply list is separated by a delimiter "|"
   Data related to a Reply are separated by a delimiter ","

    */
    public Boolean importData(String filepath, UniLink unilink) {
        try {
            File file = new File(filepath);
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {
                ArrayList<String> finalPostString = new ArrayList<String>();
                ArrayList<Reply> replyList = new ArrayList<>();
                String line = sc.nextLine();
                line = line.replaceAll("\'", "");
                String[] currentLine = line.split("~");
                for (String current : currentLine) {
                    if (current.split("=")[0].trim().equals("replyList")) {
                        String[] replyListItems;
                        current = current.substring(12, current.length() - 1).trim();
                        replyListItems = current.split(",");
                        if (!current.equals(""))
                            for (String i : replyListItems) {
                                ArrayList<String> finalReplyString = new ArrayList<String>();
                                String[] def = i.split("\\|");
                                for (String j : def) {
                                    finalReplyString.add(j.split("=")[1]);
                                }
                                Reply reply = new Reply(finalReplyString.get(0), Double.parseDouble(finalReplyString.get(1)), finalReplyString.get(2));
                                replyList.add(reply);
                            }
                    } else {
                        finalPostString.add(current.split("=")[1]);
                    }
                }
                if (finalPostString.get(0).startsWith("EVE")) {
                    Event event = new Event(finalPostString.get(0), finalPostString.get(1), finalPostString.get(2), finalPostString.get(6), finalPostString.get(7), Integer.parseInt(finalPostString.get(8)), finalPostString.get(3), finalPostString.get(5));
                    event.setStatus(finalPostString.get(4));
                    event.setAttendee_count(Integer.parseInt(finalPostString.get(9)));
                    event.setReplyList(replyList);
                    unilink.getPostCollection().add(event);
                } else if (finalPostString.get(0).startsWith("SAL")) {
                    Sale sale = new Sale(finalPostString.get(0), finalPostString.get(1), finalPostString.get(2), Double.parseDouble(finalPostString.get(6)), Double.parseDouble(finalPostString.get(8)), finalPostString.get(3), finalPostString.get(5));
                    sale.setStatus(finalPostString.get(4));
                    sale.setHighest_offer(Double.parseDouble(finalPostString.get(7)));
                    sale.setReplyList(replyList);
                    unilink.getPostCollection().add(sale);
                } else if (finalPostString.get(0).startsWith("JOB")) {
                    Job job = new Job(finalPostString.get(0), finalPostString.get(1), finalPostString.get(2), Double.parseDouble(finalPostString.get(6)), finalPostString.get(3), finalPostString.get(5));
                    job.setStatus(finalPostString.get(4));
                    job.setLowest_offer(Double.parseDouble(finalPostString.get(7)));
                    job.setReplyList(replyList);
                    unilink.getPostCollection().add(job);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return false;
        }
        return true;
    }
}
