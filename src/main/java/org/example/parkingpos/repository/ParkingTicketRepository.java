package org.example.parkingpos.repository;

import org.example.parkingpos.model.entity.ParkingTicket;
import org.example.parkingpos.model.enums.TicketStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ParkingTicketRepository extends JpaRepository<ParkingTicket, String> {

    Optional<ParkingTicket> findByVehiclePlateNumberAndStatus(String vehiclePlateNumber, TicketStatus ticketStatus);
    boolean existsByVehiclePlateNumberAndStatus(String vehiclePlateNumber, TicketStatus status);
}
