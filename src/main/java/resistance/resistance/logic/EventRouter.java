package resistance.resistance.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import resistance.resistance.telegramResponse.Update;

import java.util.Date;

@Component
public class EventRouter {

    RoomManager roomManager;
    OnlineService onlineService;
    @Value("${EventRouter.laterThen}")
    int laterThen;
@Autowired
    public EventRouter(RoomManager roomManager, OnlineService onlineService) {
        this.roomManager = roomManager;
        this.onlineService = onlineService;
    }

    public void scenerySelector(Update update){
        Date date = new Date();
        Peer peer = OnlineService.getPeer(update.getMessage().getFrom().getId(), update.getMessage().getFrom().getFirst_name(), update.getMessage().getChat().getId());
        if(peer.getRoomId() == null && (peer.getLastMsgTs() == null || peer.getLastMsgTs().getTime() < date.getTime() - laterThen)){
            sceneryOne(peer);
        }
        else if(peer.getRoomId() == null && (peer.getLastMsgTs().getTime() > date.getTime() - laterThen)){
            sceneryTwo(peer);
        }
        else if(peer.getRoomId() != null && (peer.getLastMsgTs().getTime() < date.getTime() - laterThen)){
            sceneryThree(peer);
        }
        else if(peer.getRoomId() != null && (peer.getLastMsgTs().getTime() > date.getTime() - laterThen)){
            sceneryFour(peer);
        }
        else sceneryFive(peer);
    }

    private void sceneryOne(Peer peer){
        //sceneryOne
        System.out.println("SceneryOne");
        peer.setLastMsgTs(new Date());
        OnlineService.updatePeer(peer);
    }

    private void sceneryTwo(Peer peer){
        //sceneryTwo
        System.out.println("SceneryTwo");
        peer.setLastMsgTs(new Date());
        OnlineService.updatePeer(peer);
    }

    private void sceneryThree(Peer peer){
        //sceneryThree
        System.out.println("SceneryThree");
        peer.setLastMsgTs(new Date());
        OnlineService.updatePeer(peer);
    }

    private void sceneryFour(Peer peer){
        //sceneryFour
        System.out.println("SceneryFour");
        peer.setLastMsgTs(new Date());
        OnlineService.updatePeer(peer);
    }

    private void sceneryFive(Peer peer){
        //sceneryFive
        System.out.println("SceneryFive");
        peer.setLastMsgTs(new Date());
        OnlineService.updatePeer(peer);
    }

}
