
import org.asynchttpclient.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;


public class RClient {
    private final String telegramBotUrl = "https://api.telegram.org/";
    private final String token = "bot1535167887:AAHspHwJURI66rdMqxim_xYxjMGrKQWngKk/";
    private final String methodGetUpdates = "getUpdates";
    private final String methodSendMessage = "sendMessage";
    private final String methodEditMessageText = "editMessageText";
    private String chatId = "chat_id=917985571";
    private String text = new String();
    private String responseStr;
    private int lastUpdate_id = 0;
    ArrayList<Message> messagesList;

    public void getUpdates(){

        try(AsyncHttpClient asyncHttpClient = new DefaultAsyncHttpClient()) {
            StringBuilder requestBuilder = new StringBuilder();
            requestBuilder.append(telegramBotUrl);
            requestBuilder.append(token);
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
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }responseStr = response.getResponseBody();
            updateJsonParser(responseStr);

        } catch (IOException e) {
            e.printStackTrace();
        }

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


