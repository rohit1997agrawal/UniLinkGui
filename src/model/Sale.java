package model;


public class Sale extends Post {
    private double asking_price; //Attribute of Type Double to Store Asking Price
    private double highest_offer; //Attribute of Type Double to Store Highest offer
    private double minimum_raise; //Attribute of Type Double to Store Minimum raise


    public Sale() {
    }

    //Parametrized constructor to initialize attributes and create an "Sale Post"
    public Sale(String id, String title, String description, double asking_price, double minimum_raise, String creator_id , String image_name) {
        //Calling Constructor of Super Class "Post" to initialize attributes of Post
        super(id, title, description, creator_id,image_name);
        this.asking_price = asking_price;
        this.minimum_raise = minimum_raise;

    }

    //Getters and Setters
    public double getAsking_price() {
        return asking_price;
    }

    public void setAsking_price(double asking_price) {
        this.asking_price = asking_price;
    }

    public double getHighest_offer() {
        return highest_offer;
    }

    public void setHighest_offer(double highest_offer) {
        this.highest_offer = highest_offer;
    }

    public double getMinimum_raise() {
        return minimum_raise;
    }

    public void setMinimum_raise(double minimum_raise) {
        this.minimum_raise = minimum_raise;
    }

    //Override the getPostDetails() method of Super Class
    @Override
    public String getPostDetails() {
        String highest_offer_value;
        String post_details = super.getPostDetails(); //To call the method from Super Class "Post"
        //If highest offer is empty -> "No Offer" to be displayed
        if (this.getHighest_offer() == 0.0) {
            highest_offer_value = "No OFFER";
        } else {
            highest_offer_value = String.valueOf(this.getHighest_offer());
            highest_offer_value = "$" + highest_offer_value;
        }
        String sale_details = post_details + "\nMinimum Raise:\t$" + this.minimum_raise + "\nHighest Offer :\t" + highest_offer_value;
        return sale_details; //Contains All Details i.e Post Details + Sale Details
    }

    //Called to add corresponding reply object to ReplyList
    public boolean handleReply(Reply reply) {
        this.highest_offer = reply.getValue();
        this.getReplyList().add(reply);
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
                "~ asking_price=" + asking_price +
                "~ highest_offer=" + highest_offer +
                "~ minimum_raise=" + minimum_raise +
                "~ replyList=" + getReplyList() ;
    }


    //Implementation of Abstract method "getReplyDetails" to display "Offer History" and "Asking Price" of "Sale"
    public String getReplyDetails() {
        String reply_details = "";
        if (this.getReplyList().size() == 0) {
            reply_details = "EMPTY";
        } else {
            //To Display offer history in Descending order , Print in Reverse order
            for (int i = getReplyList().size() - 1; i >= 0; i--) {
                reply_details = reply_details + "\n" + getReplyList().get(i).getResponder_id() + ":\t$" + getReplyList().get(i).getValue();
            }
        }
        String askingprice = "\nAsking Price: \n $" + this.asking_price + "\n";
        return askingprice + "\n-- Offer History --  " + reply_details;  //Return the Offer history
    }
}
