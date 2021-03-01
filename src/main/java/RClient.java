import io.netty.handler.codec.json.JsonObjectDecoder;
import io.netty.util.concurrent.Future;
import org.asynchttpclient.*;
import org.asynchttpclient.request.body.multipart.Part;
import org.asynchttpclient.util.HttpConstants;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;

import static org.asynchttpclient.Dsl.asyncHttpClient;

public class RClient {
    private final String telegramBotUrl = "https://api.telegram.org/";
    private final String token = "bot1535167887:AAHspHwJURI66rdMqxim_xYxjMGrKQWngKk/";
    private final String methodGetUpdates = "getUpdates";
    private final String methodSendMessage = "getUpdates";
    private final String methodEditMessageText = "editMessageText";
    private String chatId = "chat_id=917985571";
    private String text = new String();
    private String responseStr;

    public void getUpdates() throws IOException, ExecutionException, InterruptedException, ParseException {

        AsyncHttpClient asyncHttpClient = new DefaultAsyncHttpClient();

            String request = new String(telegramBotUrl + token + methodGetUpdates);
            ListenableFuture<Response> futureResponse = asyncHttpClient.prepareGet(request).execute();
            Response response = futureResponse.get();
            responseStr = response.getResponseBody();
        ArrayList<Message> messagesList = updateJsonParser(responseStr);
        for (Message message: messagesList) {
            System.out.println(message.toString());
        }
        }

        public ArrayList<Message> updateJsonParser(String responseStr) throws ParseException {
        ArrayList<Message> messagesList = new ArrayList<>();
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(responseStr);
            JSONArray results = (JSONArray) jsonObject.get("result");
            Iterator i = results.iterator();
            while (i.hasNext()) {
                JSONObject message = (JSONObject) i.next();
                JSONObject messageContents = (JSONObject) message.get("message");
                JSONObject chat = (JSONObject) messageContents.get("chat");
                int chat_id = Integer.parseInt(chat.get("id").toString());
                int message_id = Integer.parseInt(messageContents.get("message_id").toString());
                String text = messageContents.get("text").toString();
                Message receivedMessage = new Message(chat_id, message_id, text);
                messagesList.add(receivedMessage);
            }

            return messagesList;
        }

//    public ArrayList responseList (String responseStr){
//        ArrayList<ArrayList<String>> responseList = new ArrayList<>();
//        //ArrayList<String> response = new ArrayList<>();
//        ArrayList<String> message = new ArrayList<>();
//        String[] response = responseStr.split("message");
//        System.out.println("---");
//        for (String str: response) {
//            System.out.println(str);
//        }
//        //System.out.println(response);
//        return responseList;
//    }
}


