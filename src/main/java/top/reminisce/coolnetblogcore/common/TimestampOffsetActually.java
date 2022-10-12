package top.reminisce.coolnetblogcore.common;

/**
 * 表示时间单位的长整型毫秒数
 * @author BlueSky
 * @date 2022/10/12
 */
public enum TimestampOffsetActually {
    /**
     * 半分钟
     */
    HALF_MINUTES(35000),

    /**
     * 一分钟
     */
    MINUTES(60000),
    /**
     * 五分钟
     */

    FIVE_MINUTES(300000L),
    /**
     * 半小时
     */
    HALF_HOUR(1800000L),
    /**
     * 一小时
     */
    ONE_HOUR(3600000L),
    /**
     * 三小时
     */
    THREE_HOURS(10800000L),
    /**
     * 半天
     */
    HALF_DAY(43200000L),
    /**
     * 一天
     */
    ONE_DAY(86400000L),
    /**
     * 三天
     */
    THREE_DAYS(259200000L),
    /**
     * 一周
     */
    ONE_WEEK(604800000L),
    /**
     * 半月
     */
    HALF_MOON(1296000000L),
    /**
     * 一月
     */
    ONE_MONTH(2592000000L),
    /**
     * 三月
     */
    THREE_MONTHS(7776000000L),
    /**
     * 半年
     */
    HALF_YEAR(15768000000L),
    /**
     * 一年
     */
    ONE_YEAR(31536000000L);

    private final long value;

    TimestampOffsetActually(long value) {
        this.value = value;
    }


    public static TimestampOffsetActually valueOf(long value) {
        TimestampOffsetActually[] var1 = values();
        for (TimestampOffsetActually t : var1) {
            if (t.value == value) {
                return t;
            }
        }
        return null;
    }

    public long value() {
        return this.value;
    }
}