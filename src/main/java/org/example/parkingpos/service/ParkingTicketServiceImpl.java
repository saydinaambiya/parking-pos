package org.example.parkingpos.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.parkingpos.Util.PriceCalculator;
import org.example.parkingpos.exception.DuplicateException;
import org.example.parkingpos.exception.NotFoundException;
import org.example.parkingpos.model.dto.request.CheckInRequest;
import org.example.parkingpos.model.dto.request.CheckOutRequest;
import org.example.parkingpos.model.dto.response.CheckInResponse;
import org.example.parkingpos.model.dto.response.TicketDetailResponse;
import org.example.parkingpos.model.dto.response.CommonSuccessResponse;
import org.example.parkingpos.model.entity.ParkingTicket;
import org.example.parkingpos.model.enums.TicketStatus;
import org.example.parkingpos.repository.ParkingTicketRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class ParkingTicketServiceImpl implements ParkingTicketService {
    private final ParkingTicketRepository parkingTicketRepository;
    @Value("${app.parking.rate-per-hour}")
    private BigDecimal ratePerHour;

    @Override
    @Transactional
    public CommonSuccessResponse<CheckInResponse> checkIn(CheckInRequest request) {
        boolean isAlreadyCheckedIn = parkingTicketRepository.existsByVehiclePlateNumberAndStatus(request.getVehiclePlateNumber(), TicketStatus.CHECKED_IN);
        if (isAlreadyCheckedIn) {
            log.warn("Vehicle with plate number {} already checked in", request.getVehiclePlateNumber());
            throw new DuplicateException(request.getVehiclePlateNumber());
        }

        ParkingTicket parkingTicket = ParkingTicket.builder()
                .vehiclePlateNumber(request.getVehiclePlateNumber())
                .vehicleType(request.getVehicleType())
                .checkInTime(LocalDateTime.now())
                .status(TicketStatus.CHECKED_IN)
                .build();

        ParkingTicket savedParkingTicket = parkingTicketRepository.save(parkingTicket);
        log.info("Vehicle with plate number {} checked in successfully with ID : {}", savedParkingTicket.getVehiclePlateNumber(), savedParkingTicket.getUuid());
        CheckInResponse dataResponse = CheckInResponse.builder()
                .ticketId(savedParkingTicket.getUuid())
                .vehiclePlateNumber(savedParkingTicket.getVehiclePlateNumber())
                .checkInTime(savedParkingTicket.getCheckInTime())
                .ratePerHour(ratePerHour)
                .build();
        return CommonSuccessResponse.success(
                "CHECKED_IN",
                "Vehicle checked in successfully",
                dataResponse
        );

    }

    @Override
    public CommonSuccessResponse<TicketDetailResponse> getActiveTicket(String vehiclePlatNumber) {
        Optional<ParkingTicket> parkingTicket = parkingTicketRepository.findByVehiclePlateNumberAndStatus(vehiclePlatNumber, TicketStatus.CHECKED_IN);
        if (parkingTicket.isEmpty()){
            log.warn("No active ticket for plate number :{}", vehiclePlatNumber);
            throw new NotFoundException(vehiclePlatNumber);
        }
        LocalDateTime now = LocalDateTime.now();
        ParkingTicket ticket = parkingTicket.get();
        Long durationMinutes = ticket.calculateDurationMinutes(now);
        BigDecimal estimatedPrice = PriceCalculator.calculateParkingPrice(ticket.getCheckInTime(), now, ratePerHour);

        TicketDetailResponse dataResponse = TicketDetailResponse.builder()
                .ticketId(ticket.getUuid())
                .vehiclePlateNumber(ticket.getVehiclePlateNumber())
                .vehicleType(ticket.getVehicleType())
                .checkInTime(ticket.getCheckInTime())
                .checkOutTime(now)
                .durationMinutes(durationMinutes)
                .totalPrice(estimatedPrice)
                .build();
        return CommonSuccessResponse.success(
                "ACTIVE_TICKET",
                "Ticket is active",
                dataResponse
        );
    }

    @Override
    @Transactional
    public CommonSuccessResponse<TicketDetailResponse> checkOut(CheckOutRequest request) {
        Optional<ParkingTicket> parkingTicket = parkingTicketRepository.findByVehiclePlateNumberAndStatus(request.getVehiclePlateNumber(), TicketStatus.CHECKED_IN);
        if (parkingTicket.isEmpty()){
            log.warn("No checked in ticket for plate number: {}", request.getVehiclePlateNumber());
            throw new NotFoundException(request.getVehiclePlateNumber());
        }
        ParkingTicket ticket = parkingTicket.get();
        LocalDateTime checkOutTime = LocalDateTime.now();
        Long durationMinutes = ticket.calculateDurationMinutes(checkOutTime);

        BigDecimal totalPrice = PriceCalculator.calculateParkingPrice(ticket.getCheckInTime(), checkOutTime, ratePerHour);

        ticket.setPaymentMethod(request.getPaymentMethod());
        ticket.setCheckOutTime(checkOutTime);
        ticket.setDurationMinutes(durationMinutes);
        ticket.setTotalPrice(totalPrice);
        ticket.setStatus(TicketStatus.CHECKED_OUT);

        ParkingTicket updatedParkingTicket = parkingTicketRepository.save(ticket);
        log.info("Vehicle with plate number {} cheked out successfully. Total price {}", updatedParkingTicket.getVehiclePlateNumber(), ticket.getTotalPrice());
        TicketDetailResponse dataResponse = TicketDetailResponse.builder()
                .ticketId(updatedParkingTicket.getUuid())
                .vehiclePlateNumber(updatedParkingTicket.getVehiclePlateNumber())
                .vehicleType(updatedParkingTicket.getVehicleType())
                .checkInTime(updatedParkingTicket.getCheckInTime())
                .checkOutTime(updatedParkingTicket.getCheckOutTime())
                .durationMinutes(updatedParkingTicket.getDurationMinutes())
                .totalPrice(updatedParkingTicket.getTotalPrice())
                .build();

        return CommonSuccessResponse.success(
                "CHECKED_OUT",
                "Vehicle checked out successfully",
                dataResponse
        );
    }
}
