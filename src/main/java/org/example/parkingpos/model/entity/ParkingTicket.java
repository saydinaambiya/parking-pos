package org.example.parkingpos.model.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.parkingpos.model.enums.PaymentMethod;
import org.example.parkingpos.model.enums.TicketStatus;
import org.example.parkingpos.model.enums.VehicleType;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.LocalDateTime;

@Data
@Entity
@Table(
        name = "parking_tickets",
        indexes = {
                @Index(name = "idx_vehicle_plate_status", columnList = "vehicle_plate_number, status")
        }
)
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParkingTicket {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String uuid;

    @Column(name = "vehicle_plate_number", nullable = false, length = 10)
    private String vehiclePlateNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "vehicle_type", nullable = false)
    private VehicleType vehicleType;

    @Enumerated(EnumType.STRING)
    @Column(name = "payment_method")
    private PaymentMethod paymentMethod;

    @Column(name = "check_in_time", nullable = false)
    private LocalDateTime checkInTime;

    @Column(name = "check_out_time")
    private LocalDateTime checkOutTime;

    @Column(name = "duration_minutes")
    private Long durationMinutes;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TicketStatus status;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate(){
        if (this.checkInTime == null) {
            this.checkInTime = LocalDateTime.now();
        }

        if (this.status == null){
            this.status = TicketStatus.CHECKED_IN;
        }
    }

    public boolean isCheckIn(){
        return this.status == TicketStatus.CHECKED_IN;
    }

    public boolean isCheckOut(){
        return this.status == TicketStatus.CHECKED_OUT;
    }

    public Long calculateDurationMinutes(LocalDateTime checkOutTime) {
        if (this.checkInTime == null || checkOutTime == null) {
            return null;
        }

        return Duration.between(this.checkInTime, checkOutTime).toMinutes();
    }
}
