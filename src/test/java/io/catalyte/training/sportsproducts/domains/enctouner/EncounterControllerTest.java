package io.catalyte.training.sportsproducts.domains.enctouner;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
public class EncounterControllerTest {

  @Autowired
  private WebApplicationContext wac;

  private MockMvc mockMvc;

  @Before
  public void setUp() {
    mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
  }

  @Test
  public void createEncounterReturns201() throws Exception {
    LocalDate localDate = LocalDate.of(2020, 8, 4);
    BigDecimal cost = BigDecimal.valueOf(10.00).setScale(2, RoundingMode.HALF_UP);
    BigDecimal copay = BigDecimal.valueOf(10.10).setScale(2, RoundingMode.HALF_UP);
    Encounter testEncounter = new Encounter(1L, "new encounter", "N3W 3C3", "New Hospital", "123.456.789-00", "Z99", cost, copay, "new complaint", localDate);
    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new JavaTimeModule());
    mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    String asJson = mapper.writeValueAsString(testEncounter);
    mockMvc.perform(post("/patients/1/encounters").contentType(MediaType.APPLICATION_JSON).content(asJson))
        .andExpect(status().isCreated());
  }
  @Test
  public void updateEncounterReturns200() throws Exception {
    LocalDate localDate = LocalDate.of(2020, 8, 4);
    BigDecimal cost = BigDecimal.valueOf(10.00).setScale(2, RoundingMode.HALF_UP);
    BigDecimal copay = BigDecimal.valueOf(10.10).setScale(2, RoundingMode.HALF_UP);
    Encounter testEncounter = new Encounter(1L, "new encounter", "N3W 3C3", "New Hospital", "123.456.789-00", "Z99", cost, copay, "new complaint", localDate);
    ObjectMapper mapper = new ObjectMapper();
    mapper.registerModule(new JavaTimeModule());
    mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    String asJson = mapper.writeValueAsString(testEncounter);
    mockMvc.perform(put("/patients/1/encounters/1").contentType(MediaType.APPLICATION_JSON).content(asJson))
        .andExpect(status().isOk());
  }

  @Test
  public void getAllEncountersReturns200() throws Exception {
    mockMvc.perform(get("/patients/1/encounters")).andExpect(status().isOk());
  }

  @Test
  public void getEncounterByIdReturns200() throws Exception {
    mockMvc.perform(get("/patients/1/encounters/1")).andExpect(status().isOk());
  }

}