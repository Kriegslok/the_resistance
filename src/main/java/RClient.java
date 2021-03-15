
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.asynchttpclient.*;
import org.asynchttpclient.Response;
import telegramResponse.Message;
import telegramResponse.TelegramResponse;
import telegramResponse.TelegramUpdate;
import telegramResponse.Update;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;


public class RClient {

    private final String methodGetUpdates = "getUpdates";
    private final String methodSendMessage = "sendMessage?";
    private final String methodEditMessageText = "editMessageText?";
    //private final String chatId = "chat_id=917985571";
    private String text = new String();
    //private String responseStr;
    private int lastUpdate_id = 0;
    ArrayList<Message> messagesList;
    AsyncHttpClient asyncHttpClient;
    RClientConfig config;

    public RClient(AsyncHttpClient asyncHttpClient, RClientConfig config, int lastUpdate_id) {
        this.asyncHttpClient = asyncHttpClient;
        this.config = config;
        this.lastUpdate_id = lastUpdate_id;
    }

    public List<Update> getUpdates() throws JsonProcessingException {
            List<Update> listOfUpdates;
            StringBuilder requestBuilder = new StringBuilder();
            requestBuilder.append(config.getTelegramBotUrl());
            requestBuilder.append(config.getToken());
            requestBuilder.append(methodGetUpdates);
            if(lastUpdate_id != 0){
                requestBuilder.append("?offset=");
                requestBuilder.append(lastUpdate_id);
            }
            String request = requestBuilder.toString();
            //System.out.println(request);
            ListenableFuture<Response> futureResponse = asyncHttpClient.prepareGet(request).execute();
            Response response = null;
            try {
                response = futureResponse.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
            assert response != null;
            String responseStr = response.getResponseBody();
            listOfUpdates = updateJsonParser(responseStr);
            return listOfUpdates;
        }

        public List<Update> updateJsonParser(String responseStr) throws JsonProcessingException {
            ObjectMapper mapper = new ObjectMapper();
            TelegramUpdate telegramUpdate = mapper.readValue(responseStr, TelegramUpdate.class);
            List<Update> listOfUpdates = telegramUpdate.getResult();
            for (Update update : listOfUpdates) {
                lastUpdate_id = update.getUpdate_id();
                //System.out.println(update.getMessage().toString());
            }
            return listOfUpdates;
        }

        public Message responseJsonParser(String responseStr) throws JsonProcessingException {
            ObjectMapper mapper = new ObjectMapper();
            TelegramResponse telegramResponse = mapper.readValue(responseStr, TelegramResponse.class);
            Message responseMessage = telegramResponse.getResult();
            return responseMessage;
        }

        public Message respondOnUpdate(Update update, String text) throws JsonProcessingException {
                Message responseMessage;
                int chatId = update.getMessage().getChat().getId();
                StringBuilder requestBuilder = new StringBuilder();
                requestBuilder.append(config.getTelegramBotUrl());
                requestBuilder.append(config.getToken());
                requestBuilder.append(methodSendMessage);
                requestBuilder.append("chat_id=");
                requestBuilder.append(chatId);
                requestBuilder.append("&text=");
                requestBuilder.append(text);
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
                String responseStr = response.getResponseBody();
                responseMessage = responseJsonParser(responseStr);

                return responseMessage;
            }


        public void sendMessage(int chatId, String text){
            StringBuilder requestBuilder = new StringBuilder();
            requestBuilder.append(config.getTelegramBotUrl());
            requestBuilder.append(config.getToken());
            requestBuilder.append(methodSendMessage);
            requestBuilder.append("chat_id=");
            requestBuilder.append(chatId);
            requestBuilder.append("&text=");
            requestBuilder.append(text);
            String request = requestBuilder.toString();
            System.out.println(request);
            asyncHttpClient.prepareGet(request).execute();
        }



        public void editTextMessage(int chatId, int messageId, String text) {
            StringBuilder requestBuilder = new StringBuilder();
            requestBuilder.append(config.getTelegramBotUrl());
            requestBuilder.append(config.getToken());
            requestBuilder.append(methodEditMessageText);
            requestBuilder.append("chat_id=");
            requestBuilder.append(chatId);
            requestBuilder.append("&message_id=");
            requestBuilder.append(messageId);
            requestBuilder.append("&text=");
            requestBuilder.append(text);
            String request = requestBuilder.toString();
            System.out.println(request);
            asyncHttpClient.prepareGet(request).execute();
        }


        public void printMessages(){
            for (Message message: messagesList) {
               System.out.println(message.toString());
            }
        }

}


