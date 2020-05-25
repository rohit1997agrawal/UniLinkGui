import java.util.ArrayList;

public abstract class Post {  //Abstract class , as it contains Abstract methods
    private String id;    //String to store Post ID
    private String title; //String to store Post Title
    private String description; //String to store Event description
    private String creator_id; //String to store Creator ID
    private String status; //String to store Status
    private ArrayList<Reply> replyList = new ArrayList<Reply>(); //ArrayList of type "Reply" class , to store Replies of Post


    public Post() {
    }

    // Parameterized Constructor to be used by sub Classes to to initialize the attributes
    public Post(String id, String title, String description, String creator_id) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.creator_id = creator_id;
        this.status = "OPEN";  //By Default Status is set to "OPEN"

    }

    // Getters and setters
    public String getCreator_id() {
        return creator_id;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public String getId() {
        return id;
    }

    public ArrayList<Reply> getReplyList() {
        return replyList;
    }

    public void setReplyList(ArrayList<Reply> replyList) {
        this.replyList = replyList;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    //Method to Return Post Details
    public String getPostDetails() {
        String post_details = "ID:\t\t\t\t" + this.getId() + "\nTitle:\t\t\t" + this.getTitle() + "\nDescription:\t" + this.getDescription() + "\nCreator Id:\t\t" + this.getCreator_id() + "\nStatus:\t\t\t" + this.getStatus();
        return post_details;
    }

    //Abstract methods which are implemented in the subclasses Event/Job/Sale
    public abstract boolean handleReply(Reply reply);

    public abstract String getReplyDetails();


}
