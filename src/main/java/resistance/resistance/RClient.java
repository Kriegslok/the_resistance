package resistance.resistance;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.asynchttpclient.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import resistance.resistance.telegramResponse.Message;
import resistance.resistance.telegramResponse.TelegramResponse;
import resistance.resistance.telegramResponse.TelegramUpdate;
import resistance.resistance.telegramResponse.Update;

import java.util.List;
import java.util.concurrent.ExecutionException;

@Component
public class RClient {

    private final String methodGetUpdates = "getUpdates";
    private final String methodSendMessage = "sendMessage?";
    private final String methodEditMessageText = "editMessageText?";
    //private int lastUpdate_id;
    private AsyncHttpClient asyncHttpClient;
    private RClientConfig config;

    @Autowired
    public RClient(RClientConfig config){
        this.config = config;
        asyncHttpClient = config.getAsyncHttpClient();
    }

    public List<Update> getUpdates(int lastUpdateId) throws JsonProcessingException, ExecutionException, InterruptedException {
        List<Update> listOfUpdates;
        StringBuilder requestBuilder = new StringBuilder();
        requestBuilder.append(config.getTelegramBotUrl());
        requestBuilder.append(config.getToken());
        requestBuilder.append(methodGetUpdates);
        if(lastUpdateId != 0){
            requestBuilder.append("?offset=");
            requestBuilder.append(lastUpdateId);
        }
        String request = requestBuilder.toString();

        String responseStr = executeRequest(request);
        listOfUpdates = updateJsonParser(responseStr);
        return listOfUpdates;
    }

    private String executeRequest(String request) throws ExecutionException, InterruptedException {
        ListenableFuture<Response> futureResponse = asyncHttpClient.prepareGet(request).execute();
        Response response = null;

            response = futureResponse.get();
        return response.getResponseBody();
    }

    public List<Update> updateJsonParser(String responseStr) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        TelegramUpdate telegramUpdate = mapper.readValue(responseStr, TelegramUpdate.class);
        List<Update> listOfUpdates = telegramUpdate.getResult();
        return listOfUpdates;
    }

    public void updateToJson(Update update) throws JsonProcessingException {
        ObjectMapper stringMapper = new ObjectMapper();
        String jsonString = null;

            jsonString = stringMapper.writeValueAsString(update);

        System.out.println(jsonString);
    }

    public Message responseJsonParser(String responseStr) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        TelegramResponse telegramResponse = mapper.readValue(responseStr, TelegramResponse.class);
        return telegramResponse.getResult();
    }

    public Message respondOnUpdate(Update update, String text) throws JsonProcessingException, ExecutionException, InterruptedException {
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


    public Message sendMessage(int chatId, String text) throws JsonProcessingException, ExecutionException, InterruptedException {
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


