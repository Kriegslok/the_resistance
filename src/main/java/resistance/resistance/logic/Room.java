package resistance.resistance.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collector;
import java.util.stream.Collectors;

@Component
public class Room {
    @Value("${Room.roomId}")
    private String roomId;
    private Map<Peer, Date> roomVisitorsMap;

//    public Room(String roomId, Map<Peer, Date> roomVisitorsMap) {
//        this.roomId = roomId;
//        this.roomVisitorsMap = roomVisitorsMap;
//    }
@Autowired
//    public Room(String roomId) {
//        this.roomId = roomId;
//        this.roomVisitorsMap = new HashMap<>();
//    }

    public Room() {
        this.roomVisitorsMap = new HashMap<>();
    }

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public Map<Peer, Date> getRoomVisitorsMap() {
        return roomVisitorsMap;
    }

    public void setRoomVisitorsMap(Map<Peer, Date> roomVisitorsMap) {
        this.roomVisitorsMap = roomVisitorsMap;
    }

    public int getActiveCount(){
        Date date = new Date();
        date.setTime(date.getTime() - 5000);
        return (int) roomVisitorsMap.entrySet().stream().filter(entry -> entry.getValue().after(date)).count();
    }

    public Set<Peer> getActiveVisitors(){
        Date date = new Date();
        date.setTime(date.getTime() - 5000);
        Set<Peer> activePeersSet = new HashSet<>();
         (roomVisitorsMap.entrySet().stream().filter(entry -> entry.getValue().after(date)).collect(Collectors.toSet())).forEach(entry ->{ activePeersSet.add(entry.getKey());});
        return activePeersSet;
    }

}


