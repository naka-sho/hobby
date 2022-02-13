package org.my.hobby.scheduler;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.quarkus.logging.Log;
import io.quarkus.scheduler.Scheduled;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.my.hobby.core.Queue;
import org.my.hobby.core.TransactionStatus;
import org.my.hobby.core.TransactionStatusGroupType;
import org.my.hobby.service.CryptoService;

@ApplicationScoped
public class ChatBotNotice {

    private HttpResponse requestChat;

    @Inject
    CryptoService cryptoService;

    @Inject
    ObjectMapper objectMapper;

    @ConfigProperty(name = "chat.server.url.notice")
    String reqChatCompleteUrl;

    /**
     * 仮想通貨送信結果を取得し、その結果をチャットに通知する
     */
    @Scheduled(cron = "{cron.expr}")
    void cronJobWithExpressionInConfig() {
        List<Queue> queues = cryptoService.allQueue();

        queues.stream().parallel().forEach(e -> {
            HttpResponse response = this.requestUrl(e.url());
            TransactionStatus transactionStatus = this.modelMap((String) response.body());
            if (!TransactionStatusGroupType.confirmed(transactionStatus.getGroup())) {
                return;
            }

            ReqChatComplete reqChatCompleteParameters = new ReqChatComplete(
                    "管理人",
                    transactionStatus.getHash() + " に " + e.price() + " 送金完了"
            );

            this.requestChatComplete(reqChatCompleteUrl, reqChatCompleteParameters);
            cryptoService.deleteQueue(e.queueId());
            return;
        });
    }

    private TransactionStatus modelMap(String body){
        try {
            return objectMapper.readValue(body, TransactionStatus.class);
        } catch (JsonProcessingException ex) {
            Log.debug(ex.getMessage());
            throw new RuntimeException(ex.getMessage());
        }
    }

    private HttpResponse requestUrl(String url)
    {
        HttpClient cliHttp2 = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .build();
        HttpRequest reqTransactionStatus = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .build();

        try {
            return cliHttp2.send(reqTransactionStatus, HttpResponse.BodyHandlers.ofString());
        } catch (IOException ex) {
            Log.debug(ex.getMessage());
            throw new RuntimeException(ex.getMessage());
        } catch (InterruptedException ex) {
            throw new RuntimeException(ex.getMessage());
        }
    }

    private HttpResponse requestChatComplete(String url, ReqChatComplete reqChatCompleteParameters){
        HttpClient cliHttp = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .build();

        HttpRequest reqChatComplete = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(
                        reqChatCompleteParameters.toJson()))
//                                "{ \"sendAddress\" : \"" + transactionStatus.getHash() + "\" }"))
                .build();

        try {
            return cliHttp.send(reqChatComplete, HttpResponse.BodyHandlers.ofString());
        } catch (IOException ex) {
            Log.debug(ex.getMessage());
            throw new RuntimeException(ex.getMessage());
        } catch (InterruptedException ex) {
            Log.debug(ex.getMessage());
            throw new RuntimeException(ex.getMessage());
        }
    }

    private record ReqChatComplete(
            String name,
            String message
    ) {

        public String toJson() {
            return """
                    { "name" : "%s", "message" : "%s" }
                    """.formatted(name, message);
        }
    }
}
