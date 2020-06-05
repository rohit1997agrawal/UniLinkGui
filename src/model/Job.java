package model;

public class Job extends Post {
    private double proposed_price;
    private double lowest_offer;

    public Job() {
    }

    //Parametrized constructor to initialize attributes and create an "Job Post"
    public Job(String id, String title, String description, double proposed_price, String creator_id, String image_name) {
        //Calling Constructor of Super Class "Post" to initialize attributes of Post
        super(id, title, description, creator_id, image_name);
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


    //Called to add corresponding reply object to ReplyList
    public boolean handleReply(Reply reply) {
        this.getReplyList().add(reply);   //Adding current "Reply object" to ArrayList "ReplyList"
        this.setLowest_offer(reply.getValue()); //Update the "Lowest Offer" to current offer
        return true;
    }

    //ToString used to export data to text file
    @Override
    public String toString() {
        return "id='" + getId() + '\'' +
                "~ title='" + getTitle() + '\'' +
                "~ description='" + getDescription() + '\'' +
                "~ creator_id='" + getCreator_id() + '\'' +
                "~ status='" + getStatus() + '\'' +
                "~ image_name='" + getImage_name() + '\'' +
                "~ proposed_price=" + proposed_price +
                "~ lowest_offer=" + lowest_offer +
                "~ replyList=" + getReplyList();

    }


}
