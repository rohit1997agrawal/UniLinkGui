package model;

public class Event extends Post {
    private String venue;
    private String date;
    private int capacity;
    private int attendee_count;

    public Event() {
    }

    //Parametrized constructor to initialize attributes and create an "Event"
    public Event(String id, String title, String description, String venue, String date, int capacity, String creator_id, String image_name) {
        //Calling Constructor of Super Class "Post" to initialize attributes of Post
        super(id, title, description, creator_id, image_name);
        this.venue = venue;
        this.date = date;
        this.capacity = capacity;
    }

    //Getters and Setters
    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    //ToString used to export data to text file
    @Override
    public String toString() {
        return "" +
                "id='" + getId() + '\'' +
                "~ title='" + getTitle() + '\'' +
                "~ description='" + getDescription() + '\'' +
                "~ creator_id='" + getCreator_id() + '\'' +
                "~ status='" + getStatus() + '\'' +
                "~ image_name='" + getImage_name() + '\'' +
                "~ venue='" + venue + '\'' +
                "~ date='" + date + '\'' +
                "~ capacity=" + capacity +
                "~ attendee_count=" + attendee_count +
                "~ replyList=" + getReplyList();

    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public int getAttendee_count() {
        return attendee_count;
    }

    public void setAttendee_count(int attendee_count) {
        this.attendee_count = attendee_count;
    }


    //Called to add corresponding reply object to ReplyList
    public boolean handleReply(Reply reply) {
        this.getReplyList().add(reply);
        this.attendee_count++;
        //Condition to Close the event if Attendees count match the capacity
        if (this.attendee_count == this.capacity) {
            this.setStatus("CLOSED");
        }
        return true;
    }


}
