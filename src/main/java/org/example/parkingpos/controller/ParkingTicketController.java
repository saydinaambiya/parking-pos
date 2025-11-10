package org.example.parkingpos.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.parkingpos.model.dto.request.CheckInRequest;
import org.example.parkingpos.model.dto.request.CheckOutRequest;
import org.example.parkingpos.model.dto.response.CheckInResponse;
import org.example.parkingpos.model.dto.response.TicketDetailResponse;
import org.example.parkingpos.model.dto.response.CommonSuccessResponse;
import org.example.parkingpos.service.ParkingTicketService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/parking")
@RequiredArgsConstructor
public class ParkingTicketController {
    private final ParkingTicketService parkingTicketService;

    @PostMapping(
            value = "/check-in",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonSuccessResponse<CheckInResponse>> checkIn(@Valid @RequestBody CheckInRequest checkInRequest) {
        log.info("CheckIn Request: {}", checkInRequest);

        CommonSuccessResponse<CheckInResponse> response = parkingTicketService.checkIn(checkInRequest);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping(
            value = "/ticket/{vehiclePlateNumber}",
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonSuccessResponse<TicketDetailResponse>> getActiveTicket(
            @PathVariable
            @NotBlank(message = "Vehicle plate number cannot be empty")
            String vehiclePlateNumber
    ) {

        log.info("getActiveTicket Request: {}", vehiclePlateNumber);

        CommonSuccessResponse<TicketDetailResponse> response = parkingTicketService.getActiveTicket(vehiclePlateNumber);

        return ResponseEntity.ok(response);
    }

    @PostMapping(
            value = "/check-out",
            consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE
    )
    public ResponseEntity<CommonSuccessResponse<TicketDetailResponse>> checkOut(@Valid @RequestBody CheckOutRequest checkOutRequest) {
        log.info("CheckOut Request: {}", checkOutRequest);

        CommonSuccessResponse<TicketDetailResponse> response = parkingTicketService.checkOut(checkOutRequest);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/health")
    public ResponseEntity<String> health() {
        return ResponseEntity.ok("Parking POS API is running");
    }
}
