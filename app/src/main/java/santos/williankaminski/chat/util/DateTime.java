package santos.williankaminski.chat.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Willian Kaminski dos santos
 * @since 06-10-2019
 * @version 0.0.1
 */
public class DateTime {

    public static String getTodayDateTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("z yyyy-MM-dd 'at' HH:mm:ss");
        String currentDateandTime = sdf.format(new Date());
        return currentDateandTime;
    }
}
