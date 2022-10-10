package top.reminisce.coolnetblogcore.util;

import org.springframework.util.ObjectUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * @author BlueSky
 * @date 2022/10/6
 */
public class TimeUtils {
    private static final String DATE_USUAL_PATTERN = "yyyy-MM-dd HH:mm:ss";
    private static final String DATE_USUAL_PATTERN_EXCLUDE = "yyyy-MM-dd 00:00:00";
    /**
     * 获取当前日期字符串表示形式，使用中文常用格式 "年-月-日 时:分:秒"
     * @return 当前日期 String
     */
    public static String currentDateTimeText(){
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_USUAL_PATTERN);
        return dateFormat.format(new Date());
    }

    /**
     * 将一个日期对象转换成日期文本形式
     * @return 日期文本形式
     */
    public static String dateConvertToDateText(Date date){
        if(date == null)
        {
            throw new RuntimeException("日期对象转换成日期文本形式：Date对象不得为空");
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_USUAL_PATTERN);
        return dateFormat.format(date);
    }

    /**
     * 将一个日期文本形式转换成日期对象
     * @return 日期对象
     */
    public static Date dateTextConvertToDate(String dateText) throws ParseException {
        if(ObjectUtils.isEmpty(dateText))
        {
            throw new RuntimeException("日期文本转换成日期对象：String不得为空");
        }
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_USUAL_PATTERN);
        return dateFormat.parse(dateText);
    }

    /**
     * 获取当前的日期
     * @return 当前日期对象 Date
     */
    public static Date currentDateTime() throws ParseException {
        return TimeUtils.dateTextConvertToDate(TimeUtils.currentDateTimeText());
    }

    /**
     * 将日期转化为不包含时间的日期：<br>
     * YYYY-mm-dd 00:00:00
     * @return 日期对象 Date
     */
    public static Date dateExcludeTime(Date date) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_USUAL_PATTERN_EXCLUDE);
        return dateTextConvertToDate(dateFormat.format(date));
    }

    /**
     * 将日期偏移指定的天数
     * @param amount 往后追加的天数，若是负数，则往前追
     * @return 计算后的日期对象，
     */
    public static Date dateTimeOffsetDay(Date date, int amount){
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, amount);
        return calendar.getTime();
    }

    /**
     * 当前日期时间加指定毫秒
     * @param time 毫秒
     * @return 当前时间＋指定毫秒数的时间
     */
    public static Date timeLongAdd(long time){
        Date date = new Date();
        long l = date.getTime() + time;
        return new Date(l);
    }
}
