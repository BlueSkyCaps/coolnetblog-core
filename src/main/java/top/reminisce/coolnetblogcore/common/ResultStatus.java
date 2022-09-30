package top.reminisce.coolnetblogcore.common;

public enum ResultStatus {
    FAIL(0),
    SUCCESS(1),
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
