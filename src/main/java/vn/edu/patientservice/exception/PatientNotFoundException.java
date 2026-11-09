package vn.edu.patientservice.exception;

public class PatientNotFoundException extends RuntimeException {

    public PatientNotFoundException(long id) {
        super("Patient not found");
    }
}
