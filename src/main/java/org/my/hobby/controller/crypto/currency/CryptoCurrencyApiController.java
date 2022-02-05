package org.my.hobby.controller.crypto.currency;

import javax.inject.Inject;
import javax.validation.Validator;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.my.hobby.service.CryptoService;

@Path("api")
public class CryptoCurrencyApiController {

    @Inject
    Validator validator;

    @Inject
    CryptoService cryptoService;

    /**
     * 送金
     *
     * @return
     */
    @GET
    @Path("send")
    @Produces(MediaType.TEXT_HTML)
    public String index() {
        cryptoService.send();
        return "aaaa";
    }
}
