package top.reminisce.coolnetblogcore.pojo.vo;

import top.reminisce.coolnetblogcore.common.ResultStatus;

public class Result {
    private String message;

    public int getStatus() {
        return this.status;
    }
    public ResultStatus getResultStatus() {
        return ResultStatus.valueOf(this.status);
    }

    public void setStatus(ResultStatus resultStatus) {
        this.status = resultStatus.value();
    }
    private int status;
    private Object data;
    private StackTraceElement[] stackTrace;


    public Result(String message, ResultStatus resultStatus, Object data, StackTraceElement[] stackTrace) {
        this.message = message;
        this.status = resultStatus.value();
        this.data = data;
        this.stackTrace = stackTrace;
    }




    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public StackTraceElement[] getStackTrace() {
        return stackTrace;
    }

    public void setStackTrace(StackTraceElement[] stackTrace) {
        this.stackTrace = stackTrace;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
