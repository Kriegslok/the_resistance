package resistance.resistance.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
@Component
public class RoomManager {
    @Autowired
    private Room room;

    public RoomManager(Room room) {
        this.room = room;
    }

    public void connectToRoom(Peer peer){
        peer.setRoomId(this.room.getRoomId());
        room.getRoomVisitorsMap().put(peer, new Date());
    }

    public Room getRoom(){
        return room;
    }

}
