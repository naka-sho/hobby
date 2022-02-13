package org.my.hobby.symbol;


import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

import java.security.cert.Extension;
import java.util.Set;

import org.eclipse.microprofile.rest.client.inject.RegisterRestClient;

@RegisterRestClient
public interface SymbolRequest {
    @POST
    @Path("/send")
    @Produces(MediaType.APPLICATION_JSON)
    String send(PayloadSendRequest payloadSendRequest);
}
