public class Update {
    private String update_id;
    private Message message;

    public Update(String update_id, Message message) {
        this.update_id = update_id;
        this.message = message;
    }

    public String getUpdate_id() {
        return update_id;
    }

    public void setUpdate_id(String update_id) {
        this.update_id = update_id;
    }

    public Message getMessage() {
        return message;
    }

    public void setMessage(Message message) {
        this.message = message;
    }
}
