package utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 生产时间
 */
public class DateWorker {

    public String createDate(){
        Date day = new Date();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return simpleDateFormat.format(day);
    }
}
