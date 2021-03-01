public class Message {
    private String text;
    private int chat_id;
    private int message_id;

    public Message (int chat_id, int message_id, String text){
        this.chat_id = chat_id;
        this.message_id = message_id;
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getChat_id() {
        return chat_id;
    }

    public void setChat_id(int chat_id) {
        this.chat_id = chat_id;
    }

    public int getMessage_id() {
        return message_id;
    }

    public void setMessage_id(int message_id) {
        this.message_id = message_id;
    }

    @Override
    public String toString() {
        return (chat_id + " " + message_id + " " + text);
    }
}
