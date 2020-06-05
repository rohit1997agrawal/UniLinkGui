package model;


public class Sale extends Post {
    private double asking_price;
    private double highest_offer;
    private double minimum_raise;


    public Sale() {
    }

    //Parametrized constructor to initialize attributes and create an "Sale Post"
    public Sale(String id, String title, String description, double asking_price, double minimum_raise, String creator_id, String image_name) {
        //Calling Constructor of Super Class "Post" to initialize attributes of Post
        super(id, title, description, creator_id, image_name);
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


    //Called to add corresponding reply object to ReplyList
    public boolean handleReply(Reply reply) {
        this.highest_offer = reply.getValue();
        this.getReplyList().add(reply);
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
                "~ asking_price=" + asking_price +
                "~ highest_offer=" + highest_offer +
                "~ minimum_raise=" + minimum_raise +
                "~ replyList=" + getReplyList();
    }


}
