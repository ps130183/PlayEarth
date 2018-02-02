package com.km.rmbank.dto;


/**
 * Created by Sunflower on 2016/1/11.
 */
public class Response<T> {

//    public String code;
//    public String message;
//    public T object;
    public String status;
    public String message;
    public T result;


    public boolean isSuccess() {
        return status.equals(RetCode.SUCCESS.getStatus());
    }

    @Override
    public String toString() {
        return "Response{" +
                "status='" + status + '\'' +
                ", message='" + message + '\'' +
                ", result=" + result +
                '}';
    }
}
