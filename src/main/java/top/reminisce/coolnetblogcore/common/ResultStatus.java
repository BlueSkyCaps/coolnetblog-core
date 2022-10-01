package top.reminisce.coolnetblogcore.common;

/**
 * @author BlueSkyCarry
 */

public enum ResultStatus {
    /**
     * 失败
     */
    FAIL(0),
    /**
     * 成功
     */
    SUCCESS(1),
    /**
     * 其余
     */
    OTHER(2);

    private final int value;

    ResultStatus(int value) {
        this.value = value;
    }

    public static ResultStatus valueOf(int status) {
        ResultStatus[] var1 = values();
        for (ResultStatus rs : var1) {
            if (rs.value == status) {
                return rs;
            }
        }
        return null;
    }

    public int value() {
        return this.value;
    }
}
