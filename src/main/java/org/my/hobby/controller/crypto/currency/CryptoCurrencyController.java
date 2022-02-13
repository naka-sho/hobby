package org.my.hobby.controller.crypto.currency;

import javax.inject.Inject;
import javax.validation.Validator;
import javax.validation.constraints.Min;
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
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.quarkus.qute.CheckedTemplate;
import io.quarkus.qute.TemplateInstance;
import io.smallrye.common.constraint.NotNull;
import org.jboss.resteasy.spi.HttpRequest;
import org.my.hobby.core.CryptoType;
import org.my.hobby.core.NetworkType;
import org.my.hobby.core.Rule;
import org.my.hobby.core.Symbol;
import org.my.hobby.core.User;
import org.my.hobby.service.CryptoService;
import org.my.hobby.service.RuleService;
import org.my.hobby.service.UserService;

@Path("/")
public class CryptoCurrencyController {

    public static final Pattern PATTERN = Pattern.compile("\n");

    @Inject
    Validator validator;

    @Inject
    RuleService ruleService;

    @Inject
    UserService userService;

    @Inject
    CryptoService cryptoService;

    @CheckedTemplate
    public static class Templates {
        public static native TemplateInstance setting(List<CryptoType> cryptoTypes, List<NetworkType> networkTypeList, ViewSetting setting,
                                                      Long ruleId);

        public static native TemplateInstance list(List<User> userList);
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
                rule.node()
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
            @Context HttpRequest request) throws URISyntaxException {

        Rule rule = new Rule(ruleId
                , CryptoType.cryptoTypeMap().getOrDefault(crypto, CryptoType.SYMBOL)
                , hash
                , epochAdjustment
                , privateKey
                , NetworkType.networkTypeMap().getOrDefault(networkType, NetworkType.TEST)
                , mosaic
                , node);

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
        List<User> users = userService.userSendList();
        return Templates.list(users);
    }

    /**
     * アドレス登録実行
     *
     * @return
     */
    @POST
    @Path("add/user")
    @Produces(MediaType.TEXT_PLAIN)
    public Response addUser(@FormParam("addUser") @NotNull String addUser,
                            @Context HttpRequest request) throws URISyntaxException {
        List<String> userList = Arrays.stream(addUser.split("\r\n|\n"))
                .map(
                        e -> e.trim()
                )
                .filter(e -> !"".equals(e))
                .toList();

        if(!userList.isEmpty()){
            userService.addList(userList);
        }

        URI requestUrI = request.getUri().getRequestUri();
        URI redirectUrI = new URI("https", requestUrI.getHost(), "/", "");
        return Response.status(301)
                .location(redirectUrI)
                .build();
    }

    /**
     * アドレス削除実行
     *
     * @return
     */
    @POST
    @Path("delete/user")
    @Produces(MediaType.TEXT_PLAIN)
    public Response deleteUser(@FormParam("deleteUser") @NotNull String deleteUser,
                               @Context HttpRequest request) throws URISyntaxException {
        List<String> userList = Arrays.stream(deleteUser.split("\r\n|\n"))
                .map(
                        e -> e.trim()
                )
                .filter(e -> !"".equals(e))
                .toList();

        if(!userList.isEmpty()){
            userService.deleteList(userList);
        }

        URI requestUrI = request.getUri().getRequestUri();
        URI redirectUrI = new URI("https", requestUrI.getHost(), "/", "");
        return Response.status(301)
                .location(redirectUrI)
                .build();
    }

    /**
     * 一括送金
     *
     * @return
     */
    @POST
    @Path("send/user")
    @Produces(MediaType.TEXT_PLAIN)
    public Response sendUser(@FormParam("price") @Min(0L) @NotNull Long price,
                             @Context HttpRequest request) throws URISyntaxException {

        List<User> users = userService.userSendList();

        Rule rule = ruleService.rule();

        users.stream().parallel()
                .filter(e -> !e.send())
                .forEach(e -> {
                    Symbol symbol = new Symbol(e.address(),
                            price,
                            "一括送金"
                    );
                    cryptoService.send(rule, symbol);
                });

        URI requestUrI = request.getUri().getRequestUri();
        URI redirectUrI = new URI("https", requestUrI.getHost(), "/", "");
        return Response.status(301)
                .location(redirectUrI)
                .build();
    }
}
