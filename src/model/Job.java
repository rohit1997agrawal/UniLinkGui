public class Job extends Post {
    private double proposed_price; //Attribute of type Double to store the Proposed Price
    private double lowest_offer; //Attribute of type Double to store the Lowest Offer

    public Job() {
    }

    //Parametrized constructor to initialize attributes and create an "Job Post"
    public Job(String id, String title, String description, double proposed_price, String creator_id) {
        //Calling Constructor of Super Class "Post" to initialize attributes of Post
        super(id, title, description, creator_id);
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

    //Implementation of Abstract method "handleReply" to handle Reply to an "Job"
    public boolean handleReply(Reply reply) {
        //To check if Job Post is open and offered Price is a positive number
        if (this.getStatus().equals("OPEN"))
            if (reply.getValue() > 0) {
                //To check if proposed price is less than current lowest offer / No offers present
                if (reply.getValue() < this.getLowest_offer() || this.getReplyList().size() == 0) {
                    this.getReplyList().add(reply);   //Adding current "Reply object" to ArrayList "ReplyList"
                    this.setLowest_offer(reply.getValue()); //Update the "Lowest Offer" to current offer
                    return true;
                } else {
                    System.out.println("Offer higher than current lowest offer! ");
                }
            } else {
                System.out.println("Offer Price can not be negative! ");
            }
        else {
            System.out.println("Job Offer Closed!");
        }
        return false; //One or more criteria not not passed


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
