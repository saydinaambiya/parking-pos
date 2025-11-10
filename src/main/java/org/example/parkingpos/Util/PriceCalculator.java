package org.example.parkingpos.Util;

import lombok.extern.slf4j.Slf4j;
import org.example.parkingpos.model.entity.ParkingTicket;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.Duration;
import java.time.LocalDateTime;

@Slf4j
public class PriceCalculator {
    public static BigDecimal calculateParkingPrice(LocalDateTime checkInTime, LocalDateTime checkOutTime, BigDecimal ratePerHour) {
        if (checkInTime == null || checkOutTime == null || ratePerHour == null) {
            log.error("Parameters is required for price calculation");
            throw new IllegalArgumentException("Parameters must be not null");
        }

        if (checkOutTime.isBefore(checkInTime)) {
            log.error("Check out time is before check in time");
            throw new IllegalArgumentException("Check out time must be after check in time");
        }

        long durationMinutes = Duration.between(checkInTime, checkOutTime).toMinutes();

        // in case same time in and out
        if (durationMinutes == 0) {
            durationMinutes = 1;
        }

        long hours = (durationMinutes + 59) / 60;

        log.debug("Calculating price for {} hour {} minutes", hours, durationMinutes);

        BigDecimal totalPrice = ratePerHour.multiply(BigDecimal.valueOf(hours));
        log.debug("Total price is {}", totalPrice);

        return totalPrice;
    }
}
