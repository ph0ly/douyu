package core;

import model.Room;
import model.User;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringEscapeUtils;
import org.apache.http.client.CookieStore;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.HttpClientConnectionManager;
import org.apache.http.conn.util.PublicSuffixMatcher;
import org.apache.http.conn.util.PublicSuffixMatcherLoader;
import org.apache.http.cookie.Cookie;
import org.apache.http.cookie.CookieSpecProvider;
import org.apache.http.impl.client.BasicCookieStore;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.impl.cookie.DefaultCookieSpecProvider;
import util.CookieUtil;
import util.JsonUtils;
import util.MD5Utils;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.Map;

/**
 * @author ph0ly
 * @time 2016-09-16
 */
public class DouyuTVClient implements TVClient {

    private static CloseableHttpClient httpClient = null;
    private volatile User user = new User();

    static {
        PublicSuffixMatcher publicSuffixMatcher = PublicSuffixMatcherLoader.getDefault();

        Registry<CookieSpecProvider> r = RegistryBuilder.<CookieSpecProvider>create()
                .register(CookieSpecs.DEFAULT,
                        new DefaultCookieSpecProvider(publicSuffixMatcher))
                .build();

        RequestConfig requestConfig = RequestConfig.custom()
                .setCookieSpec(CookieSpecs.DEFAULT)
                .setSocketTimeout(3000)
                .setConnectTimeout(4000)
                .build();

        PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager();
        poolingHttpClientConnectionManager.setMaxTotal(100);
        httpClient = HttpClients.custom()
                .setDefaultCookieSpecRegistry(r)
                .setDefaultRequestConfig(requestConfig)
                .setConnectionManager(poolingHttpClientConnectionManager)
                .build();
    }

    private CookieStore store = new BasicCookieStore();
    private HttpClientContext context = HttpClientContext.create();

    public DouyuTVClient() {
        context.setCookieStore(store);
    }

    @Override
    public boolean login(String userName, String password) {
        user.setAccountName(userName);
        user.setAccountPassword(password);
        String cryptoPassword = MD5Utils.MD5(password);
        System.out.println(userName + " : " + cryptoPassword);

        HttpGet httpGet = new HttpGet("http://passport.douyu.com/api/captcha?v=1473949960234");
        try {
            CloseableHttpResponse response = httpClient.execute(httpGet, context);

            BufferedImage captchaImage = ImageIO.read(response.getEntity().getContent());
            ImageIO.write(captchaImage, "jpg", new File("/Users/ph0ly/trash/douyu_captcha.jpg"));

            System.out.println("输入验证码继续: ");
            String captcha = new BufferedReader(new InputStreamReader(System.in)).readLine();
            System.out.println(captcha);

            //1026289a6cc32bb59a24f961a3db04fe
            String loginUrl = "http://passport.douyu.com/iframe/login?username=" + userName + "&password=" + cryptoPassword + "&" +
                    "captcha_word=" + captcha + "&login_type=nickname&redirect=/api/oauth2/authorize_inside?client_id=1&" +
                    "redirect_uri=http://yuba.douyu.com/authlogin&response_type=code&" +
                    "redirect_url=http://www.douyu.com/member/login?ref_url=/api/oauth2/authorize_inside?client_id=1&" +
                    "redirect_uri=http://yuba.douyu.com/authlogin&response_type=code&t=1473950253875&client_id=1&_=";

            httpGet = new HttpGet(loginUrl);
            response = httpClient.execute(httpGet, context);
            String resp = IOUtils.toString(response.getEntity().getContent());
            resp = resp.replace("(", "");
            resp = resp.replace(")", "");
            System.out.println(resp);

            Map<String, Object> map = JsonUtils.toBean(resp, Map.class);
            Integer code = Integer.parseInt(map.get("error").toString());
            if (code == 1) {
                System.out.println("验证码错误: " + StringEscapeUtils.unescapeJava(map.get("msg").toString()));
                return false;
            }
            Map<String, Object> respData = (Map)map.get("data");
            String authUrl = "http://www.douyu.com/api/passport/login?code=" + respData.get("code") + "&state=" + respData.get("state") + "&uid=" + respData.get("uid") + "&client_id=" + respData.get("client_id");
            httpGet = new HttpGet(authUrl);
            response = httpClient.execute(httpGet, context);
            resp = IOUtils.toString(response.getEntity().getContent());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        for (Cookie c : context.getCookieStore().getCookies()) {
            System.out.println("===>" + c.getName() + ": " + c.getValue());
        }
        System.out.println("=========================");

        Cookie cookie = CookieUtil.findCookie(context.getCookieStore(), "acf_nickname");
        if (cookie != null) {
            System.out.println("登录成功: " + cookie.getValue());
            return true;
        } else {
            System.out.println("登录失败");
            return false;
        }
    }

    @Override
    public boolean quitRoom(String roomId) {
        return false;
    }

    @Override
    public List<Room> getJoinedRooms() {
        return null;
    }

    @Override
    public boolean isJoined(String roomId) {
        return false;
    }

    @Override
    public boolean logout() {
        return false;
    }

    @Override
    public User getLoginUser() {
        return user;
    }

    @Override
    public boolean joinRoom(String roomId) {
        HttpGet httpGet = new HttpGet("https://www.douyu.com/xueqiujiang");
        try {
            httpGet.setHeader("Connection", "keep-alive");
            httpGet.setHeader("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_5) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/52.0.2743.116 Safari/537.36");
            CloseableHttpResponse response = httpClient.execute(httpGet, context);
            if (response.getStatusLine().getStatusCode() == 200) {
                System.out.println(user.getAccountName() + " 加入 " + roomId + " 成功");
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return true;
    }
}
