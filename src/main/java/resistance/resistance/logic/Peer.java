package resistance.resistance.logic;

import java.util.Date;
import java.util.Objects;

public class Peer {
    private int userId;
    private String name;
    private int chatId;
    private Date lastMsgTs;
    private String roomId;

    public Peer(int userId, String name, int chatId, Date lastMsgTs, String roomId) {
        this.userId = userId;
        this.name = name;
        this.chatId = chatId;
        this.lastMsgTs = lastMsgTs;
        this.roomId = roomId;
    }

    public Peer(int userId, String name, int chatId) {
        this.userId = userId;
        this.name = name;
        this.chatId = chatId;
    }

    public int getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }

    public Date getLastMsgTs() {
        return lastMsgTs;
    }

    public void setLastMsgTs(Date lastMsgTs) {
        this.lastMsgTs = lastMsgTs;
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public int getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Peer peer = (Peer) o;
        return userId == peer.userId && chatId == peer.chatId && Objects.equals(name, peer.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, name, chatId);
    }
}
