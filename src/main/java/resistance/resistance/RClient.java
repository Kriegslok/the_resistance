package resistance.resistance;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.asynchttpclient.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import resistance.resistance.entities.telegramResponse.Message;
import resistance.resistance.entities.telegramResponse.TelegramResponse;
import resistance.resistance.entities.telegramResponse.TelegramUpdate;
import resistance.resistance.entities.telegramResponse.Update;

import java.rmi.UnexpectedException;
import java.util.List;
import java.util.concurrent.ExecutionException;

@Component
public class RClient {

    private final String methodGetUpdates = "getUpdates";
    private final String methodSendMessage = "sendMessage?";
    private final String methodEditMessageText = "editMessageText?";
    //private int lastUpdate_id;
    private AsyncHttpClient asyncHttpClient;
    private RClientConfig rConfig;



    @Autowired
    public RClient(RClientConfig rConfig, AsyncHttpClient httpClient){
        this.rConfig = rConfig;
        this.asyncHttpClient = httpClient;
    }


    public List<Update> getUpdates(int lastUpdateId) throws JsonProcessingException, ExecutionException, InterruptedException {
        List<Update> listOfUpdates;
        StringBuilder requestBuilder = new StringBuilder();
        requestBuilder.append(rConfig.getTelegramBotUrl());
        requestBuilder.append(rConfig.getToken());
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
        ListenableFuture<Response> futureResponse = this.asyncHttpClient.prepareGet(request).execute();
        //asyncHttpClient.prepareGet(request).addBodyPart(new Par);
        Response response = null;

            response = futureResponse.get();

//        System.out.println(response.getHeaders());
//        System.out.println(response.getResponseBody());
        return response.getResponseBody();
    }


    private String executeRequest(String request, String json) throws ExecutionException, InterruptedException {
        ListenableFuture<Response> futureResponse = asyncHttpClient.prepareGet(request)
                .addHeader("Content-Type", "application/json").setBody(json).execute();
        Response response = null;

        response = futureResponse.get();
        System.out.println(response.getHeaders());
        System.out.println(response.getResponseBody());
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

    public Message respondOnUpdate(Update update, String text) throws JsonProcessingException, ExecutionException, InterruptedException, UnexpectedException {
            Message responseMessage;
        int chatId = 0;
        if(update.getMessage() != null) {
            chatId = update.getMessage().getChat().getId();
        }
        else if(update.getCallback_query() != null && update.getCallback_query().getMessage() != null){
            chatId = update.getCallback_query().getMessage().getChat().getId();
        }
        else{
            throw  new UnexpectedException("Unexpected response");
        }
            responseMessage = sendMessage(chatId, text);
            return responseMessage;
    }

    public Message respondOnUpdate(Update update, String text, String json) throws JsonProcessingException, ExecutionException, InterruptedException, UnexpectedException {
        Message responseMessage;
        int chatId = 0;
        if(update.getMessage() != null) {
            chatId = update.getMessage().getChat().getId();
        }
        else if(update.getCallback_query() != null && update.getCallback_query().getMessage() != null){
            chatId = update.getCallback_query().getMessage().getChat().getId();
        }
        else{
            throw  new UnexpectedException("Unexpected response");
        }
        responseMessage = sendMessage(chatId, text, json);
        return responseMessage;
    }

    private String buildRequest(String text, int chatId) {
        String request = rConfig.getTelegramBotUrl() +
                rConfig.getToken() +
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

    public Message sendMessage(int chatId, String text, String json) throws JsonProcessingException, ExecutionException, InterruptedException {
        Message responseMessage;
        String request = buildRequest(text, chatId);
        String responseStr = executeRequest(request, json);
        responseMessage = responseJsonParser(responseStr);
        return responseMessage;
    }



    public void editTextMessage(int chatId, int messageId, String text) {
        String request = rConfig.getTelegramBotUrl() +
                rConfig.getToken() +
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


