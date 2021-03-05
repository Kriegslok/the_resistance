
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.JSONPObject;
import org.asynchttpclient.*;
import org.drinkless.tdlib.TdApi;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.DataInput;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class RClient {

    private final String methodGetUpdates = "getUpdates";
    private final String methodSendMessage = "sendMessage";
    private final String methodEditMessageText = "editMessageText";
    private final String chatId = "chat_id=917985571";
    private String text = new String();
    private String responseStr;
    private int lastUpdate_id = 0;
    ArrayList<Message> messagesList;
    AsyncHttpClient asyncHttpClient;
    RClientConfig config;

    public RClient(AsyncHttpClient asyncHttpClient, RClientConfig config) {
        this.asyncHttpClient = asyncHttpClient;
        this.config = config;
    }

    public void getUpdates(){
            StringBuilder requestBuilder = new StringBuilder();
            requestBuilder.append(config.getTelegramBotUrl());
            requestBuilder.append(config.getToken());
            requestBuilder.append(methodGetUpdates);
            if(lastUpdate_id != 0){
                requestBuilder.append("?offset=");
                requestBuilder.append(lastUpdate_id);
            }
            String request = requestBuilder.toString();
            System.out.println(request);
            ListenableFuture<Response> futureResponse = asyncHttpClient.prepareGet(request).execute();
            Response response = null;
            try {
                response = futureResponse.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            assert response != null;
            responseStr = response.getResponseBody();
            //updateJsonParser(responseStr);
            updateJsonParserNew(responseStr);

        }

        public void updateJsonParserNew(String responseStr){
            ObjectMapper mapper = new ObjectMapper();
            JsonNode jsonNode = null;
            try {
                jsonNode = mapper.readValue(responseStr, JsonNode.class);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            System.out.println(jsonNode.toString());
            List<String> result = jsonNode.get("result").asText();
            System.out.println(update);
        }

        public void updateJsonParser(String responseStr) {
        ArrayList<Message> messagesList = new ArrayList<>();
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = null;
            try {
                jsonObject = (JSONObject) jsonParser.parse(responseStr);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            assert jsonObject != null;
            JSONArray results = (JSONArray) jsonObject.get("result");
            Iterator i = results.iterator();
            while (i.hasNext()) {
                JSONObject message = (JSONObject) i.next();
                lastUpdate_id = Integer.parseInt(message.get("update_id").toString());
                JSONObject messageContents = (JSONObject) message.get("message");
                JSONObject chat = (JSONObject) messageContents.get("chat");
                int chat_id = Integer.parseInt(chat.get("id").toString());
                int message_id = Integer.parseInt(messageContents.get("message_id").toString());
                String text = messageContents.get("text").toString();
                Message receivedMessage = new Message(chat_id, message_id, text);
                messagesList.add(receivedMessage);
            }
            this.messagesList = messagesList;
        }

        public void printMessages(){
        for (Message message: messagesList) {
            System.out.println(message.toString());
        }
        }

}


