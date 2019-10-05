package santos.williankaminski.chat.util;

import android.util.Base64;

/**
 * @author Willian Kaminski dos santos
 * @since 05-10-2019
 * @version 0.0.1
 */
public class Base64Custom {

    public static String encodeBase64(String string){
        return Base64.encodeToString(string.getBytes(), Base64.DEFAULT).replaceAll("\\n|\\r", "");
    }

    public static String decodeBase64(String string){
        return new String(Base64.decode(string, Base64.DEFAULT));
    }
}
