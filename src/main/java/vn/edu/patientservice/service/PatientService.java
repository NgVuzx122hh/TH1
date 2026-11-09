package vn.edu.patientservice.service;

import vn.edu.patientservice.exception.PatientNotFoundException;
import vn.edu.patientservice.model.Patient;
import vn.edu.patientservice.model.PatientRequest;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class PatientService {

    private final ConcurrentMap<Long, Patient> patients = new ConcurrentHashMap<>();
    private final AtomicLong nextId = new AtomicLong(1);

    public List<Patient> findAll() {
        return patients.values().stream()
                .sorted(Comparator.comparing(Patient::id))
                .toList();
    }

    public Patient findById(long id) {
        Patient patient = patients.get(id);
        if (patient == null) {
            throw new PatientNotFoundException(id);
        }
        return patient;
    }

    public Patient create(PatientRequest request) {
        long id = nextId.getAndIncrement();
        Patient patient = toPatient(id, request);
        patients.put(id, patient);
        return patient;
    }

    public Patient update(long id, PatientRequest request) {
        if (!patients.containsKey(id)) {
            throw new PatientNotFoundException(id);
        }
        Patient patient = toPatient(id, request);
        patients.put(id, patient);
        return patient;
    }

    public void delete(long id) {
        if (patients.remove(id) == null) {
            throw new PatientNotFoundException(id);
        }
    }

    public List<Patient> searchByName(String name) {
        String query = name.toLowerCase();
        return findAll().stream()
                .filter(patient -> patient.fullName().toLowerCase().contains(query))
                .toList();
    }

    private Patient toPatient(long id, PatientRequest request) {
        return new Patient(
                id,
                request.fullName().trim(),
                request.dateOfBirth(),
                request.gender(),
                request.phone().trim(),
                request.address());
    }
}
