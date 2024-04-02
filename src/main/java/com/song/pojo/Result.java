package com.song.pojo;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @Description:
 * @Author song
 * @Data 2024/1/18 16:28
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Result {

    private Integer code;
    private String msg;
    private Object data;

    public static Result ok(Object data, String msg) {
        return new Result(200, msg, data);
    }

    public static Result ok(Object data) {
        return new Result(200, "", data);
    }

    public static Result ok(String msg) {
        return new Result(200, msg, null);
    }

    public static Result fail(String msg) {
        return new Result(500, msg, null);
    }

}
