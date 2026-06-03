package com.itheima.pojo;

import lombok.Data;

@Data
public class Result<T> {
    //状态码
    private Integer code;
    //返回信息
    private String message;
    //返回数据
    private T data;
    public static <T> Result<T> success(){
        Result<T> result = new Result<>();
        result.code=1;
        result.message="成功";
        return result;
    }
    public static <T> Result<T> success(T object){
        Result<T> result = new Result<>();
        result.code=1;
        result.message="成功";
        result.data=object;
        return result;
    }
    public static <T> Result<T> error(String message){
        Result<T> result = new Result<>();
        result.code=0;
        result.message="失败";
        return result;
    }
}
