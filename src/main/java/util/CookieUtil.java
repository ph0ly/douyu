package util;

import org.apache.http.client.CookieStore;
import org.apache.http.cookie.Cookie;

/**
 * @author ph0ly
 * @time 2016-09-16
 */
public class CookieUtil {

    public static Cookie findCookie(CookieStore cookieStore, String key) {
        for (Cookie cookie : cookieStore.getCookies()) {
            if (cookie.getName().equalsIgnoreCase(key)) {
                return cookie;
            }
        }
        return null;
    }
}
