package resistance.resistance.logic;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
@Component
public class OnlineService {
    @Autowired
    private static Set<Peer> activePeersSet = new HashSet<>();

    public static Peer getPeer(int userId, String name, int chatId){

       try {
           Optional<Peer> gotPeer = activePeersSet.stream().filter(peer -> (peer.getUserId() == userId &&
                   peer.getName().equals(name) && peer.getChatId() == chatId)).findFirst();
           if (gotPeer.isPresent()) {
               return gotPeer.get();
           }
       }catch (NullPointerException e){
           System.out.println("NPE!");
       }
        Peer peer = new Peer(userId, name, chatId );
        activePeersSet.add(peer);
        return peer;
    }

    public static void updatePeer(Peer peer){
        activePeersSet.add(peer);
    }

}
