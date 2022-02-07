package org.my.hobby.controller.crypto.currency;

import javax.inject.Inject;
import javax.validation.Validator;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.eclipse.microprofile.config.inject.ConfigProperty;
import org.my.hobby.core.Queue;
import org.my.hobby.core.Symbol;
import org.my.hobby.service.CryptoService;

record SendRequest(
        @NotBlank(message = "sendAddress not found") String sendAddress,
        @Min(0L) Long price,
        String message
) {
}

record AddQueueRequest(
        @NotBlank(message = "address not found") String address,
        @NotBlank(message = "transaction not found") String transaction,
        @NotBlank(message = "url not found") String url
) {
}

@Path("api")
public class CryptoCurrencyApiController {

    @Inject
    CryptoService cryptoService;

    /**
     * 送金
     *
     * @return
     */
    @POST
    @Path("send")
    @Produces(MediaType.APPLICATION_JSON)
    public String send(SendRequest sendRequest) {
        Symbol symbol = new Symbol(sendRequest.sendAddress(),
                sendRequest.price(),
                sendRequest.message()
        );
        cryptoService.send(symbol);
        return "OK";
    }

    /**
     * 送金ログ登録
     *
     * @return
     */
    @POST
    @Path("queue/add")
    @Produces(MediaType.APPLICATION_JSON)
    public String addQueue(AddQueueRequest addQueueRequest) {
        Queue queue = new Queue(addQueueRequest.address(),
                addQueueRequest.transaction(),
                addQueueRequest.url()
        );
        cryptoService.add(queue);
        return "OK";
    }
}
