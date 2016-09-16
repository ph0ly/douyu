//import com.gargoylesoftware.htmlunit.BrowserVersion;
//import com.gargoylesoftware.htmlunit.WebClient;
//import com.gargoylesoftware.htmlunit.html.*;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import core.DouyuAPIs;
import core.DouyuTVClient;
import core.TVClient;
import model.Room;
import org.apache.commons.io.IOUtils;
import org.apache.http.client.CookieStore;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.params.ClientPNames;
import org.apache.http.client.params.CookiePolicy;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.util.PublicSuffixMatcher;
import org.apache.http.conn.util.PublicSuffixMatcherLoader;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieSpecProvider;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.impl.cookie.DefaultCookieSpecProvider;
import org.apache.http.message.BasicNameValuePair;

import javax.imageio.ImageIO;
import javax.ws.rs.core.MultivaluedMap;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.function.BiConsumer;

/**
 * @author ph0ly
 * @time 2016-09-15
 */
public class TestMain {

    public static void main(String[] args) throws Exception {

//        try (final WebClient webClient = new WebClient(BrowserVersion.CHROME)) {
//            final HtmlPage html = webClient.getPage("http://www.douyu.com/member/login");
//            HtmlTextInput inputUserName = (HtmlTextInput)html.getElementById("InputUserName");
//            HtmlPasswordInput inputPassword = (HtmlPasswordInput)html.getElementById("InputPassword");
//            HtmlTextInput inputCaptcha = (HtmlTextInput)html.getElementById("InputCaptcha");
//            HtmlButton submitButton = (HtmlButton)html.getFirstByXPath("//button[@class='btn']");
//
//            inputUserName.setValueAttribute("kongyoujiu438");
//            inputPassword.setValueAttribute("tujing9739");
//
//            HtmlPage newPage = submitButton.click();
//            System.out.println(newPage.asText());
//        }

//        Client client = Client.create();
//        WebResource webResource = client.resource("http://www.ph0ly.com");
//        ClientResponse response = webResource.get(ClientResponse.class);
//        String resp = response.getEntity(String.class);
//        System.out.println(resp);

//        Set<String> cookies = Sets.newHashSet();
//
//        Client client = Client.create();
//
//        WebResource webResource = client.resource("http://passport.douyu.com/api/captcha?v=1473949960234");
//        ClientResponse response = webResource.get(ClientResponse.class);
//        MultivaluedMap<String, String> headersMap = response.getHeaders();
//        headersMap.forEach(new BiConsumer<String, List<String>>() {
//            @Override
//            public void accept(String s, List<String> strings) {
//                System.out.println(s + ": " + Joiner.on(",").join(strings));
//            }
//        });
//        cookies.add(headersMap.getFirst("Set-Cookie"));
//        BufferedImage bufferedImage = ImageIO.read(response.getEntityInputStream());
//        ImageIO.write(bufferedImage, "jpg", new File("/Users/ph0ly/trash/douyu_captcha.jpg"));
//
//        System.out.println("输入验证码继续: ");
//        String captcha = new BufferedReader(new InputStreamReader(System.in)).readLine();
//        System.out.println(captcha);
//
//        String loginUrl = "http://passport.douyu.com/iframe/login?username=kongyoujiu438&password=1026289a6cc32bb59a24f961a3db04fe&" +
//                "captcha_word=" + captcha + "&login_type=nickname&redirect=/api/oauth2/authorize_inside?client_id=1&" +
//                "redirect_uri=http://yuba.douyu.com/authlogin&response_type=code&" +
//                "redirect_url=http://www.douyu.com/member/login?ref_url=/api/oauth2/authorize_inside?client_id=1&" +
//                "redirect_uri=http://yuba.douyu.com/authlogin&response_type=code&t=1473950253875&client_id=1&_=";
//
//        response = client.resource(loginUrl).header("Cookie", Joiner.on(";").join(cookies)).get(ClientResponse.class);
//        String resp = response.getEntity(String.class);
//        resp = resp.replace("(", "");
//        resp = resp.replace(")", "");
//
//        headersMap = response.getHeaders();
//        cookies.add(headersMap.getFirst("Set-Cookie"));
//        headersMap.forEach(new BiConsumer<String, List<String>>() {
//            @Override
//            public void accept(String s, List<String> strings) {
//                System.out.println(s + ": " + Joiner.on(",").join(strings));
//            }
//        });
//
//        Map<String, Object> map = util.JsonUtils.toBean(resp, Map.class);
//        Integer code = Integer.parseInt(map.get("error").toString());
//        if (code == 1) {
//            System.out.println("验证码错误...");
//            return;
//        }
//        Map<String, Object> respData = (Map)map.get("data");
//
//        String authUrl = "http://www.douyu.com/api/passport/login?code=" + respData.get("code") + "&state=" + respData.get("state") + "&uid=" + respData.get("uid") + "&client_id=" + respData.get("client_id");
//        response = client.resource(authUrl).header("Cookie", Joiner.on(";").join(cookies)).get(ClientResponse.class);
//        System.out.println(resp);
//
//        resp = response.getEntity(String.class);
//        headersMap = response.getHeaders();
//        headersMap.forEach(new BiConsumer<String, List<String>>() {
//            @Override
//            public void accept(String s, List<String> strings) {
//                System.out.println(s + ": " + Joiner.on(",").join(strings));
//            }
//        });

        String roomId = "1025638";
        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(1);
        executorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                Room room = DouyuAPIs.getRoom(roomId);
                System.out.println("房间: " + room.getId() + ", 人气: " + room.getOnline());
            }
        }, 0, 10, TimeUnit.SECONDS);


        List<BasicNameValuePair> users = Lists.newArrayList();
        users.add(new BasicNameValuePair("xiafu465", "tangbu0682"));
        users.add(new BasicNameValuePair("fengyexing711", "shadi5170"));
        users.add(new BasicNameValuePair("haowei531", "jijing8241"));
        users.add(new BasicNameValuePair("xiesishan926", "juyuan3367"));
        users.add(new BasicNameValuePair("weiji955", "zhuobu3335"));

        for (BasicNameValuePair pair : users) {
            TVClient client = new DouyuTVClient();
            System.out.println(pair.toString());
            while (!client.login(pair.getName(), pair.getValue())) {}
            client.joinRoom(roomId);
        }

    }



}
