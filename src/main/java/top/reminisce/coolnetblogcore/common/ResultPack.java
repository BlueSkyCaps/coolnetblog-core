package top.reminisce.coolnetblogcore.common;

import top.reminisce.coolnetblogcore.pojo.vo.Result;

public class ResultPack {
    public static Result fluent(){
        return new Result(null, ResultStatus.SUCCESS, null, null);
    }
    public static Result fluent(Object body){
        return new Result(null, ResultStatus.SUCCESS, body, null);
    }

    public static Result fluent(String message){
        return new Result(message, ResultStatus.SUCCESS, null, null);
    }

    public static Result fluent(String message, Object body){
        return new Result(message, ResultStatus.SUCCESS, body, null);
    }

    public static Result error(String errMessage){
        return new Result(errMessage, ResultStatus.FAIL, null, null);
    }
    public static Result error(String errMessage, StackTraceElement[] stackTrace){
        return new Result(errMessage, ResultStatus.FAIL, null, stackTrace);
    }

    public static Result error(String errMessage, Object errTipBody){
        return new Result(errMessage, ResultStatus.FAIL, errTipBody, null);
    }

    public static Result error(String errMessage, Object errTipBody, StackTraceElement[] stackTrace){
        return new Result(errMessage, ResultStatus.FAIL, errTipBody, stackTrace);
    }

    public static Result reject(String rejectMessage){
        return new Result(rejectMessage, ResultStatus.FAIL, null, null);
    }
    public static Result reject(String rejectMessage, StackTraceElement[] stackTrace){
        return new Result(rejectMessage, ResultStatus.FAIL, null, stackTrace);
    }

    public static Result reject(String rejectMessage, Object rejectTipBody){
        return new Result(rejectMessage, ResultStatus.FAIL, rejectTipBody, null);
    }

    public static Result reject(String rejectMessage, Object rejectTipBody, StackTraceElement[] stackTrace){
        return new Result(rejectMessage, ResultStatus.FAIL, rejectTipBody, stackTrace);
    }
}
