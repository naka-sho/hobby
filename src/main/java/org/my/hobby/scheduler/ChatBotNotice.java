package org.my.hobby.scheduler;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.quarkus.logging.Log;
import io.quarkus.scheduler.Scheduled;
import io.quarkus.scheduler.ScheduledExecution;
import io.vertx.ext.web.handler.BodyHandler;
import lombok.Data;
import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.my.hobby.core.Queue;
import org.my.hobby.core.TransactionStatus;
import org.my.hobby.core.TransactionStatusGroupType;
import org.my.hobby.service.CryptoService;

@ApplicationScoped
public class ChatBotNotice {

    @Inject
    CryptoService cryptoService;

    @Inject
    ObjectMapper objectMapper;

    private boolean process = false;

    @ConfigProperty(name = "chat.server.url.notice")
    String reqChatCompleteUrl;

    /**
     * 仮想通貨送信結果を取得し、その結果をチャットに通知する
     */
    @Scheduled(cron = "{cron.expr}")
    void cronJobWithExpressionInConfig() {
        if (this.process) {
            System.out.println("プロセスが起動中");
            return;
        }
        this.process = true;
        List<Queue> queues = cryptoService.allQueue();

        queues.stream().parallel().forEach(e -> {
            try {
                HttpClient cliHttp2 = HttpClient.newBuilder()
                        .version(HttpClient.Version.HTTP_2)
                        .build();
                HttpRequest reqTransactionStatus = HttpRequest.newBuilder()
                        .uri(URI.create(e.url()))
                        .build();

                HttpResponse<String> response = cliHttp2.send(reqTransactionStatus, HttpResponse.BodyHandlers.ofString());
                TransactionStatus transactionStatus = objectMapper.readValue(response.body(), TransactionStatus.class);
                System.out.println(transactionStatus);

                if (!TransactionStatusGroupType.confirmed(transactionStatus.getGroup())) {
                    this.process = false;
                    return;
                }

                HttpClient cliHttp = HttpClient.newBuilder()
                        .version(HttpClient.Version.HTTP_1_1)
                        .build();

                ReqChatComplete reqChatCompleteParameters = new ReqChatComplete(
                        "管理人",
                        "アドレス : " + transactionStatus.getHash() + "の送金ステータスが完了になりました"
                );

                Log.debug(reqChatCompleteParameters.toJson());

                HttpRequest reqChatComplete = HttpRequest.newBuilder()
                        .uri(URI.create(reqChatCompleteUrl))
                        .header("Content-Type", "application/json")
                        .POST(HttpRequest.BodyPublishers.ofString(
                                reqChatCompleteParameters.toJson()))
//                                "{ \"sendAddress\" : \"" + transactionStatus.getHash() + "\" }"))
                        .build();

                try {
                    HttpResponse<String> responseReqChatComplete = cliHttp.send(reqChatComplete, HttpResponse.BodyHandlers.ofString());
                    System.out.println(responseReqChatComplete);
                } catch (IOException ex) {
                    ex.printStackTrace();
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                this.process = false;
                return;
            } catch (IOException ex) {
                System.out.println(ex.getMessage());
                this.process = false;
                return;
            } catch (InterruptedException ex) {
                System.out.println(ex.getMessage());
                this.process = false;
                return;
            } finally {
                this.process = false;
                return;
            }
        });
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

    ;
}
