package com.portfolio.postproject.common.param;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.FieldError;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ResponseError {

    //private String field;
    private String message;

    //에러를 JoinResponseError 빌드 형식으로 커스텀하여 전달
    public static ResponseError of(FieldError e) {
        return ResponseError.builder()
                //.field(e.getField())
                .message(e.getDefaultMessage())
                .build();
    }
}
