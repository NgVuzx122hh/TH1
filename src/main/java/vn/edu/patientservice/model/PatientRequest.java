package vn.edu.patientservice.model;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record PatientRequest(
        @NotBlank(message = "fullName must not be blank")
        String fullName,
        LocalDate dateOfBirth,
        String gender,
        @NotBlank(message = "phone must not be blank")
        String phone,
        String address) {
}
