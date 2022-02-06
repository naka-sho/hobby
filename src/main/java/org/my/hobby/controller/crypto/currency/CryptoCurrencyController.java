package org.my.hobby.controller.crypto.currency;

import javax.inject.Inject;
import javax.validation.Validator;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.math.BigDecimal;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import io.smallrye.common.constraint.NotNull;
import org.jboss.resteasy.spi.HttpRequest;
import org.my.hobby.core.CryptoType;
import org.my.hobby.core.NetworkType;
import org.my.hobby.core.Rule;
import org.my.hobby.service.RuleService;

@Path("/")
public class CryptoCurrencyController {

    @Inject
    Validator validator;

    @Inject
    RuleService ruleService;

    @CheckedTemplate
    public static class Templates {
        public static native TemplateInstance setting(List<CryptoType> cryptoTypes, List<NetworkType> networkTypeList, ViewSetting setting,
                                                      Long ruleId);

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
        Rule rule = ruleService.rule();
        final ViewSetting setting = new ViewSetting(
                rule.crypto(),
                rule.hash(),
                rule.epochAdjustment(),
                rule.privateKey(),
                rule.networkType(),
                rule.mosaic(),
                rule.node(),
                rule.pointSum(),
                rule.pointAddCount()
        );
        return Templates.setting(CryptoType.cryptoTypes(), NetworkType.networkTypeList(), setting, rule.ruleId());
    }

    /**
     * 設定反映
     *
     * @return
     */
    @POST
    @Path("setting/update")
    @Produces(MediaType.TEXT_PLAIN)
    public Response settingUpdate(
            @FormParam("ruleId") Long ruleId,
            @FormParam("crypto") @NotNull String crypto,
            @FormParam("hash") @NotNull String hash,
            @FormParam("epochAdjustment") @NotNull String epochAdjustment,
            @FormParam("privateKey") @NotNull String privateKey,
            @FormParam("networkType") @NotNull String networkType,
            @FormParam("mosaic") @NotNull String mosaic,
            @FormParam("node") @NotNull String node,
            @FormParam("pointSum") @NotNull Integer pointSum,
            @FormParam("pointAddCount") @NotNull Integer pointAddCount,
            @Context HttpRequest request) throws URISyntaxException {

        Rule rule = new Rule(ruleId
                , CryptoType.cryptoTypeMap().getOrDefault(crypto, CryptoType.SYMBOL)
                , hash
                , epochAdjustment
                , privateKey
                , NetworkType.networkTypeMap().getOrDefault(networkType, NetworkType.TEST)
                , mosaic
                , node
                , pointSum
                , pointAddCount);

        ruleService.update(rule);

        URI requestUrI = request.getUri().getRequestUri();
        URI redirectUrI = new URI("https", requestUrI.getHost(), "/setting", "");
        return Response.status(301)
                .location(redirectUrI)
                .build();
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
