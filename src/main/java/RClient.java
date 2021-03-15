
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.asynchttpclient.*;
import telegramResponse.Message;
import telegramResponse.TelegramResponse;
import telegramResponse.TelegramUpdate;
import telegramResponse.Update;

import java.util.List;
import java.util.concurrent.ExecutionException;


public class RClient {

    private final String methodGetUpdates = "getUpdates";
    private final String methodSendMessage = "sendMessage?";
    private final String methodEditMessageText = "editMessageText?";
    private int lastUpdate_id;
    final AsyncHttpClient asyncHttpClient;
    final RClientConfig config;

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

        String responseStr = executeRequest(request);
        listOfUpdates = updateJsonParser(responseStr);
        return listOfUpdates;
    }

    private String executeRequest(String request) {
        ListenableFuture<Response> futureResponse = asyncHttpClient.prepareGet(request).execute();
        Response response = null;
        try {
            response = futureResponse.get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
        assert response != null;
        return response.getResponseBody();
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
        return telegramResponse.getResult();
    }

    public Message respondOnUpdate(Update update, String text) throws JsonProcessingException {
            Message responseMessage;
            int chatId = update.getMessage().getChat().getId();
            responseMessage = sendMessage(chatId, text);
            return responseMessage;
    }

    private String buildRequest(String text, int chatId) {
        String request = config.getTelegramBotUrl() +
                config.getToken() +
                methodSendMessage +
                "chat_id=" +
                chatId +
                "&text=" +
                text;
        System.out.println(request);
        return request;
    }


    public Message sendMessage(int chatId, String text) throws JsonProcessingException {
        Message responseMessage;
        String request = buildRequest(text, chatId);
        String responseStr = executeRequest(request);
        responseMessage = responseJsonParser(responseStr);
        return responseMessage;
    }



    public void editTextMessage(int chatId, int messageId, String text) {
        String request = config.getTelegramBotUrl() +
                config.getToken() +
                methodEditMessageText +
                "chat_id=" +
                chatId +
                "&message_id=" +
                messageId +
                "&text=" +
                text;
        System.out.println(request);
        asyncHttpClient.prepareGet(request).execute();
    }


}


