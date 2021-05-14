package resistance.resistance.logic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import resistance.resistance.entities.ReplyMarkup;
import resistance.resistance.entities.TelegramInlineButton;
import resistance.resistance.entities.telegramResponse.Update;

import java.rmi.UnexpectedException;
import java.text.DecimalFormat;
import java.util.*;

@Component
public class EventRouter {

    final RoomManager roomManager;
    final OnlineService onlineService;
    @Value("${EventRouter.laterThen}")
    long laterThen;
    String replyMarkup = "&reply_markup=";
    final ObjectMapper mapper = new ObjectMapper();
    @Value("${EventRouter.enterRoomButtonText}")
    private String enterRoomButtonText;
    private Map<String, String> buttonsMap;

    @Autowired
    public EventRouter(RoomManager roomManager, OnlineService onlineService) {
        this.roomManager = roomManager;
        this.onlineService = onlineService;
    }

    private void init(){
        this.buttonsMap = new HashMap<>();
        this.buttonsMap.put("EnterButton", enterRoomButtonText);
        System.out.println(enterRoomButtonText);
    }

    public  List<String> scenerySelector(Update update) throws UnexpectedException {
        if(buttonsMap == null){
            init();
        }
        Date date = new Date();
        List<String> replyList = new ArrayList<>();
        String messageText = "";
        Peer peer;
        if(update.getMessage() != null) {
            peer = OnlineService.getPeer(update.getMessage().getFrom().getId(), update.getMessage().getFrom().getFirst_name(), update.getMessage().getChat().getId());
        }
        else if(update.getCallback_query() != null && update.getCallback_query().getMessage() != null){
            peer = OnlineService.getPeer(update.getCallback_query().getFrom().getId(), update.getCallback_query().getFrom().getFirst_name(), update.getCallback_query().getMessage().getChat().getId());
        }
        else{
            throw  new UnexpectedException("Unexpected response");
        }
        if(peer.getRoomId() == null && (peer.getLastMsgTs() == null || peer.getLastMsgTs().getTime() < date.getTime() - laterThen)){
            replyList = sceneryOne(peer);
        }
        else if(peer.getRoomId() == null && (peer.getLastMsgTs().getTime() > date.getTime() - laterThen) &&
                update.getMessage() != null){
            replyList = sceneryTwo(peer);
        }
        else if(peer.getRoomId() == null && (peer.getLastMsgTs().getTime() > date.getTime() - laterThen) &&
                update.getMessage() == null && update.getCallback_query() != null && buttonsMap.get("EnterButton").equals(update.getCallback_query().getData())){
            replyList = sceneryThree(peer);
        }
        else if(peer.getRoomId() != null && (peer.getLastMsgTs().getTime() > date.getTime() - laterThen) &&
                (((laterThen - (date.getTime() - roomManager.getRoom().getRoomVisitorsMap().get(peer).getTime()))/60000d)) >= 0){
            replyList = sceneryFour(peer);
        }
        else if(peer.getRoomId() != null && (peer.getLastMsgTs().getTime() < date.getTime() - laterThen) ||
                (((laterThen - (date.getTime() - roomManager.getRoom().getRoomVisitorsMap().get(peer).getTime()))/60000d)) <= 0){
            replyList = sceneryFive(peer);
        }
        else scenerySix(peer);
        return replyList;
    }



    private List<String> sceneryOne(Peer peer){
        //sceneryOne
        String greetingText = "Some greeting text";
        String buttonText = "Enter room";
        String callbackData = "room1";
        TelegramInlineButton button = new TelegramInlineButton(buttonText, callbackData);
        ReplyMarkup replyMarkup = new ReplyMarkup(button);
        try {
            buttonText = mapper.writeValueAsString(replyMarkup);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        List<String> replyList = new ArrayList<>();
        replyList.add(greetingText);
        replyList.add(buttonText);
        System.out.println("SceneryOne");
        peer.setLastMsgTs(new Date());
        OnlineService.updatePeer(peer);
        return replyList;
    }

    private List<String> sceneryTwo(Peer peer){
        //sceneryTwo
        String greetingText = "Some other text";
        String buttonText = "Enter room";
        String callbackData = "room1";
        TelegramInlineButton button = new TelegramInlineButton(buttonText, callbackData);
        ReplyMarkup replyMarkup = new ReplyMarkup(button);
        try {
            buttonText = mapper.writeValueAsString(replyMarkup);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        List<String> replyList = new ArrayList<>();
        replyList.add(greetingText);
        replyList.add(buttonText);
        System.out.println("SceneryTwo");
        peer.setLastMsgTs(new Date());
        OnlineService.updatePeer(peer);
        return replyList;
    }

    private  List<String> sceneryThree(Peer peer){
        //sceneryThree
        roomManager.connectToRoom(peer);
        List<String> replyList = new ArrayList<>();
        String messageText = "You and " + (roomManager.getRoom().getActiveCount() - 1) + " players more will stay here for " + (laterThen/60000d) + " minutes";
        replyList.add(messageText);
        System.out.println("SceneryThree");
        peer.setLastMsgTs(new Date());
        OnlineService.updatePeer(peer);
        return replyList;
    }

    private  List<String> sceneryFour(Peer peer){
        //sceneryFour
        Date date = new Date();
        DecimalFormat decimalFormat = new DecimalFormat("#.#");
        String remainingTime = decimalFormat.format(((laterThen - (date.getTime() - roomManager.getRoom().getRoomVisitorsMap().get(peer).getTime()))/60000d));
        List<String> replyList = new ArrayList<>();
        String messageText = "You will stay here " +  remainingTime + " minutes more.";
        replyList.add(messageText);
        System.out.println("SceneryFour");
        peer.setLastMsgTs(new Date());
        OnlineService.updatePeer(peer);
        return replyList;
    }

    private List<String> sceneryFive(Peer peer){
        //sceneryFive
        System.out.println("SceneryFive");
        peer.setRoomId(null);
        peer.setLastMsgTs(null);
        Date date = new Date();
        if(peer.getLastMsgTs() == null){
            return sceneryOne(peer);
        }
        else return sceneryTwo(peer);
    }

    private List<String> scenerySix(Peer peer) {
        //scenerySix
        List<String> replyList = new ArrayList<>();
        String messageText = "";
        replyList.add(messageText);
        System.out.println("ScenerySix");
        peer.setLastMsgTs(new Date());
        OnlineService.updatePeer(peer);
        return replyList;
    }

    private void notifyEverybody(OnlineService onlineService, Peer peer, Room room ){
        //TODO: notify all room visitors about new peer connection
    }

}
