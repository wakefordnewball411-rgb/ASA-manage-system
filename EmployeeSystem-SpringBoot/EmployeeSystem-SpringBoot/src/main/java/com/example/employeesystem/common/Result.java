package com.example.employeesystem.common;

//自动生成getter&&setter
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {
    private Integer  code;
    private String msg;
    private Object data;

    //响应成功
    public static Result success(Object data){
        return new Result(200,"success",data);
    }//带参
    public static Result success(){
        return new Result(200,"success",null);
    }//无参
    //响应失败
    public static Result fail(String msg){
        return new Result(500,"fail",null);
    }

    //可重载灵活调整返回值
}

