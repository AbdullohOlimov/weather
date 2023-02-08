package com.example.wheather.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ResponseDto {
    private String status;
    private String message;
    private Object data;

    public static ResponseDto getError(String i, String success) {
        return new ResponseDto(i, success, null);
    }

    public static ResponseDto getSuccess(Object object) {
        return new ResponseDto("ok", "success", object);
    }

}
