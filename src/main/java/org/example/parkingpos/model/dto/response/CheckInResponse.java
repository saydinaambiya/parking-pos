package org.example.parkingpos.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CheckInResponse {
    private Long ticketId;
    private String vehiclePlateNumber;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime checkInTime;
    private String status;
    private String message;

    public static CheckInResponse success(Long ticketId, String vehiclePlateNumber, LocalDateTime checkInTime) {
        return CheckInResponse.builder()
                .ticketId(ticketId)
                .vehiclePlateNumber(vehiclePlateNumber)
                .checkInTime(checkInTime)
                .status("CHECKED_IN")
                .message("Vehicle checked in successfully")
                .build();
    }
}
