public class Chat {
    private String id;
    private String first_name;
    private String type;

    public Chat(String id, String first_name, String type) {
        this.id = id;
        this.first_name = first_name;
        this.type = type;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
