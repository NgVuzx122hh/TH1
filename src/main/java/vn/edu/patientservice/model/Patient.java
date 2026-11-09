package vn.edu.patientservice.model;

import java.time.LocalDate;

public record Patient(
        Long id,
        String fullName,
        LocalDate dateOfBirth,
        String gender,
        String phone,
        String address) {
}
