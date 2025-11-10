package org.example.parkingpos.model.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CommonSuccessResponse<T> {
    private String status;
    private String message;
    private T data;

    public static CommonSuccessResponse success(String status, String message, Object data){
        return CommonSuccessResponse.builder()
                .status(status)
                .message(message)
                .data(data)
                .build();
    }
}
