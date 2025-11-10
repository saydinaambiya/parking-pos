package org.example.parkingpos.service;

import org.example.parkingpos.model.dto.request.CheckInRequest;
import org.example.parkingpos.model.dto.request.CheckOutRequest;
import org.example.parkingpos.model.dto.response.CheckInResponse;
import org.example.parkingpos.model.dto.response.TicketDetailResponse;
import org.example.parkingpos.model.dto.response.CommonSuccessResponse;

public interface ParkingTicketService {
    CommonSuccessResponse<CheckInResponse> checkIn(CheckInRequest request);
    CommonSuccessResponse<TicketDetailResponse> getActiveTicket(String vehiclePlatNumber);
    CommonSuccessResponse<TicketDetailResponse> checkOut(CheckOutRequest request);
}
