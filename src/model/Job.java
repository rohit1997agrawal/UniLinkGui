package model;

public class Job extends Post {
    private double proposed_price; //Attribute of type Double to store the Proposed Price
    private double lowest_offer; //Attribute of type Double to store the Lowest Offer

    public Job() {
    }

    //Parametrized constructor to initialize attributes and create an "Job Post"
    public Job(String id, String title, String description, double proposed_price, String creator_id,String image_name) {
        //Calling Constructor of Super Class "Post" to initialize attributes of Post
        super(id, title, description, creator_id ,image_name);
        this.proposed_price = proposed_price;

    }

    //Getters and Setters
    public double getProposed_price() {
        return proposed_price;
    }

    public void setProposed_price(double proposed_price) {
        this.proposed_price = proposed_price;
    }

    public double getLowest_offer() {
        return lowest_offer;
    }

    public void setLowest_offer(double lowest_offer) {
        this.lowest_offer = lowest_offer;
    }

    //Override the getPostDetails() method of Super Class
    @Override
    public String getPostDetails() {
        String lowest_offer_value;
        String post_details = super.getPostDetails(); //To call the method from Super Class "Post"
        //If Lowest offer is empty -> "No Offer" to be displayed
        if (this.getLowest_offer() == 0.0) {
            lowest_offer_value = "No OFFER";
        } else {
            lowest_offer_value = String.valueOf(this.getLowest_offer());
            lowest_offer_value = "$" + lowest_offer_value;
        }
        String job_details = post_details + "\nProposed Price:\t$" + this.getProposed_price() + "\nLowest Offer:\t" + lowest_offer_value;
        return job_details; //Contains All Details i.e Post Details + Job Details
    }

    //Called to add corresponding reply object to ReplyList
    public boolean handleReply(Reply reply)
    {
        this.getReplyList().add(reply);   //Adding current "Reply object" to ArrayList "ReplyList"
        this.setLowest_offer(reply.getValue()); //Update the "Lowest Offer" to current offer
        return true;
    }

    //ToString used to export data to text file
    @Override
    public String toString() {
        return  "id='" + getId() + '\'' +
                "~ title='" + getTitle() + '\'' +
                "~ description='" + getDescription() + '\'' +
                "~ creator_id='" + getCreator_id() + '\'' +
                "~ status='" + getStatus() + '\'' +
                "~ image_name='" + getImage_name() + '\'' +
                "~ proposed_price=" + proposed_price +
                "~ lowest_offer=" + lowest_offer +
                "~ replyList=" + getReplyList() ;

    }

    //Implementation of Abstract method "getReplyDetails" to display "Offer History" of "Job"
    public String getReplyDetails() {
        String reply_details = "";
        if (this.getReplyList().size() == 0) {
            reply_details = "EMPTY";
        } else {
            //To Display offer history in Ascending order , Print the Array List "replyList" in Reverse order
            for (int i = getReplyList().size() - 1; i >= 0; i--) {
                reply_details = reply_details + "\n" + getReplyList().get(i).getResponder_id() + ":\t$" + getReplyList().get(i).getValue();
            }


        }
        return "\n-- Offer History -- " + reply_details;  //Return the Offer history

    }


}
