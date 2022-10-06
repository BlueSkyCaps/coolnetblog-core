package top.reminisce.coolnetblogcore.util;

import org.springframework.util.ObjectUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author BlueSky
 * @date 2022/10/6
 */
public class TimeUtils {
    private static final String DATE_PATTERN_ZH_CN = "yyyy-MM-dd HH:mm:ss";
    /**
     * 获取当前日期字符串表示形式，使用中文标准格式 "年-月-日 时:分:秒"
     * @return 当前日期 String
     */
    public static String currentDateTimeText(){
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_PATTERN_ZH_CN);
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
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_PATTERN_ZH_CN);
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
        SimpleDateFormat dateFormat = new SimpleDateFormat(DATE_PATTERN_ZH_CN);
        return dateFormat.parse(dateText);
    }

    /**
     * 获取当前的日期，使用中文标准格式 "年-月-日 时:分:秒"
     * @return 当前日期对象 Date
     */
    public static Date currentDateTime() throws ParseException {
        return TimeUtils.dateTextConvertToDate(TimeUtils.currentDateTimeText());
    }
}
