package vn.edu.patientservice.controller;

import vn.edu.patientservice.model.Patient;
import vn.edu.patientservice.model.PatientRequest;
import vn.edu.patientservice.service.PatientService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    private final PatientService patientService;

    public PatientController(PatientService patientService) {
        this.patientService = patientService;
    }

    @GetMapping("/hello")
    public Map<String, String> hello() {
        return Map.of("message", "Welcome to Patient Service");
    }

    @GetMapping("/info")
    public Map<String, String> info() {
        return Map.of(
                "service", "Patient Service",
                "version", "1.0",
                "status", "UP");
    }

    @GetMapping
    public List<Patient> findAll() {
        return patientService.findAll();
    }

    @GetMapping("/search")
    public List<Patient> search(@RequestParam String name) {
        return patientService.searchByName(name);
    }

    @GetMapping("/{id}")
    public Patient findById(@PathVariable long id) {
        return patientService.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Patient create(@Valid @RequestBody PatientRequest request) {
        return patientService.create(request);
    }

    @PutMapping("/{id}")
    public Patient update(@PathVariable long id, @Valid @RequestBody PatientRequest request) {
        return patientService.update(id, request);
    }

    @DeleteMapping("/{id}")
    public Map<String, String> delete(@PathVariable long id) {
        patientService.delete(id);
        return Map.of("message", "Patient deleted");
    }
}
