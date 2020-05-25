public class Reply {

    private String post_id;  //String to store POST ID
    private double value; //Double to store Reply value
    private String responder_id; //String to store Responder Id

    public Reply() {
    }

    //Constructor to initialize values of attributes
    public Reply(String post_id, double value, String responder_id) {
        this.post_id = post_id;
        this.value = value;
        this.responder_id = responder_id;

    }

    //Getters and Setters
    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public String getResponder_id() {
        return responder_id;
    }

    public void setResponder_id(String responder_id) {
        this.responder_id = responder_id;
    }

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }
}
