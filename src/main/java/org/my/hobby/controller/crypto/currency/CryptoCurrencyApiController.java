package org.my.hobby.controller.crypto.currency;

import javax.inject.Inject;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.my.hobby.core.Queue;
import org.my.hobby.core.Rule;
import org.my.hobby.core.Symbol;
import org.my.hobby.service.CryptoService;
import org.my.hobby.service.RuleService;
import org.my.hobby.service.UserService;

record SendRequest(
        @Min(0L) Long price,
        String message
) {
}

record AddQueueRequest(
        @NotBlank(message = "address not found") String address,
        @NotBlank(message = "transaction not found") String transaction,
        @NotBlank(message = "transaction not found") Long price,
        @NotBlank(message = "url not found") String url
) {
}

record ErrorAddressDeleteRequest(
        @NotBlank(message = "address not found") String address
) {
}

@Path("api")
public class CryptoCurrencyApiController {

    @Inject
    CryptoService cryptoService;

    @Inject
    RuleService ruleService;

    @Inject
    UserService userService;

    /**
     * 送金
     *
     * @return
     */
    @POST
    @Path("send")
    @Produces(MediaType.APPLICATION_JSON)
    public String send(SendRequest sendRequest) {

        String address = cryptoService.address();
        if("".equals(address)){
            return "all transaction complete";
        }

        Symbol symbol = new Symbol(address,
                sendRequest.price(),
                sendRequest.message()
        );

        Rule rule = ruleService.rule();
        cryptoService.send(rule, symbol);
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
                addQueueRequest.price(),
                addQueueRequest.url()
        );
        cryptoService.add(queue);
        cryptoService.createLog(queue);
        return "OK";
    }

    /**
     * 送金ログ登録
     *
     * @return
     */
    @POST
    @Path("error/address/delete")
    @Produces(MediaType.APPLICATION_JSON)
    public String errorAddressDelete(ErrorAddressDeleteRequest errorAddressDeleteRequest) {
        userService.delete(errorAddressDeleteRequest.address());
        return "OK";
    }
}
