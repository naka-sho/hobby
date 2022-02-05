package org.my.hobby.controller.crypto.currency;

import javax.inject.Inject;
import javax.validation.Validator;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.math.BigDecimal;

import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;

@Path("/")
public class CryptoCurrencyController {

    @Inject
    Validator validator;

    @CheckedTemplate
    public static class Templates {
        public static native TemplateInstance setting(Item item);
        public static native TemplateInstance list(Item item);
        public static native TemplateInstance add(Item item);
        public static native TemplateInstance delete(Item item);
    }

    /**
     * 設定
     *
     * @return
     */
    @GET
    @Path("setting")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance setting() {
        return Templates.setting(new Item("test", new BigDecimal("1.234")));
    }

    /**
     * アドレス一覧
     *
     * @return
     */
    @GET
    @Path("")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance list() {
        return Templates.list(new Item("test", new BigDecimal("1.234")));
    }

    /**
     * アドレス登録
     *
     * @return
     */
    @GET
    @Path("add")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance add() {
        return Templates.add(new Item("test", new BigDecimal("1.234")));
    }

    /**
     * アドレス登録実行
     *
     * @return
     */
    @POST
    @Path("add/user")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance addUser() {
        return Templates.list(new Item("test", new BigDecimal("1.234")));
    }

    /**
     * アドレス削除
     *
     * @return
     */
    @GET
    @Path("delete")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance delete() {
        return Templates.delete(new Item("test", new BigDecimal("1.234")));
    }

    /**
     * アドレス削除実行
     *
     * @return
     */
    @GET
    @Path("delete/user")
    @Produces(MediaType.TEXT_HTML)
    public TemplateInstance deleteUser() {
        return Templates.list(new Item("test", new BigDecimal("1.234")));
    }
}
