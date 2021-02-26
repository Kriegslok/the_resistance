import io.netty.util.concurrent.Future;
import org.asynchttpclient.*;
import org.asynchttpclient.request.body.multipart.Part;
import org.asynchttpclient.util.HttpConstants;

import java.util.concurrent.ExecutionException;

public class RClient {
    private final String token = "bot1535167887:AAHspHwJURI66rdMqxim_xYxjMGrKQWngKk";

    public Request getRequest(){
        Request getRequest = new RequestBuilder(HttpConstants.Methods.POST)
                .setUrl("https://api.telegram.org/bot1535167887:AAHspHwJURI66rdMqxim_xYxjMGrKQWngKk")
                //.setHeader("", "bot1535167887:AAHspHwJURI66rdMqxim_xYxjMGrKQWngKk" )
                //.setHeader(token, token)
                .setMethod("sendMessage")
                .addFormParam("chat_id", "917985571")
                .addFormParam("text", "qwertty")
                .build();
        System.out.println(getRequest.toString());
        return getRequest;
    }
    public void execute(Request request) throws ExecutionException, InterruptedException {
        AsyncHttpClient client = Dsl.asyncHttpClient();
        ListenableFuture<Response> responseFuture =  client.executeRequest(request);
        responseFuture.get();
        System.out.println(client.toString());
        System.out.println(responseFuture);
    }
}
