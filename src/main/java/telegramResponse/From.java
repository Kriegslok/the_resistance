package telegramResponse;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class From {
    private int id;
    private boolean is_bot;
    private String first_name;
    private String language_code;
    private String username;

    public From() {
        this.id = 0;
        this.is_bot = false;
        this.first_name = null;
        this.language_code = null;
        this.username = null;
    }

    public From(int id, boolean is_bot, String first_name, String language_code, String username) {
        this.id = id;
        this.is_bot = is_bot;
        this.first_name = first_name;
        this.language_code = language_code;
        this.username = username;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isIs_bot() {
        return is_bot;
    }

    public void setIs_bot(boolean is_bot) {
        this.is_bot = is_bot;
    }

    public String getFirst_name() {
        return first_name;
    }

    public void setFirst_name(String first_name) {
        this.first_name = first_name;
    }

    public String getLanguage_code() {
        return language_code;
    }

    public void setLanguage_code(String language_code) {
        this.language_code = language_code;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
