package org.my.hobby;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import javax.validation.constraints.NotBlank;

import java.math.BigDecimal;

import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;


@Path("")
public class Resource {
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello() {
        return "OK";
    }

    @CheckedTemplate
    public static class Templates {
        public static native TemplateInstance item(Item item);
    }

    @GET
    @Path("template")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance get() {
        return Templates.item(new Item("test", new BigDecimal("1.234")));
    }
}
