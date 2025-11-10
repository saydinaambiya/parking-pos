package org.example.parkingpos.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CheckInRequest {
    @NotBlank(message = "Vehicle plate number is required")
    @Size(min = 3, max = 10, message = "Vehicle plate number must be between 2 and 10 characters")
    @Pattern(regexp = "^[a-zA-Z0-9]+$", message = "Vehicle plate number can only contain letters and numbers")
    private String vehiclePlateNumber;
}
