package org.example.parkingpos.model.dto.request;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.parkingpos.model.enums.VehicleType;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CheckInRequest {
    @NotBlank(message = "Vehicle plate number is required")
    @Size(min = 3, max = 10, message = "Vehicle plate number must be between 2 and 10 characters")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Vehicle plate number can only contain letters and numbers")
    private String vehiclePlateNumber;

    @Enumerated(EnumType.STRING)
    private VehicleType vehicleType;
}
