package vn.edu.patientservice.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class PatientControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void exposesHelloAndInfoEndpoints() throws Exception {
        mockMvc.perform(get("/api/patients/hello"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Welcome to Patient Service"));
        mockMvc.perform(get("/api/patients/info"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("UP"));
    }

    @Test
    void createsFindsSearchesUpdatesAndDeletesPatient() throws Exception {
        String patient = """
                {
                  "fullName": "Nguyen Van An",
                  "dateOfBirth": "2003-05-12",
                  "gender": "Male",
                  "phone": "0901234567",
                  "address": "Ha Noi"
                }
                """;

        mockMvc.perform(post("/api/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(patient))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.fullName").value("Nguyen Van An"));

        mockMvc.perform(get("/api/patients/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.phone").value("0901234567"));
        mockMvc.perform(get("/api/patients/search").param("name", "an"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].fullName").value("Nguyen Van An"));

        String updatedPatient = patient.replace("Nguyen Van An", "Tran Thi Binh");
        mockMvc.perform(put("/api/patients/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(updatedPatient))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fullName").value("Tran Thi Binh"));

        mockMvc.perform(delete("/api/patients/1"))
                .andExpect(status().isOk());
        mockMvc.perform(get("/api/patients/1"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.status").value(404))
                .andExpect(jsonPath("$.message").value("Patient not found"));
    }

    @Test
    void rejectsInvalidInputAndReturnsNotFoundForMissingPatient() throws Exception {
        mockMvc.perform(post("/api/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {"fullName":" ","phone":""}
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.status").value(400))
                .andExpect(jsonPath("$.message").exists());

        mockMvc.perform(post("/api/patients")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value(containsString("request body")));

        mockMvc.perform(delete("/api/patients/999"))
                .andExpect(status().isNotFound());
    }
}
