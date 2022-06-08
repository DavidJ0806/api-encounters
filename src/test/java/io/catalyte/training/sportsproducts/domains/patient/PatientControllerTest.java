package io.catalyte.training.sportsproducts.domains.patient;
import static io.catalyte.training.sportsproducts.constants.Paths.PATIENTS_PATH;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

import com.fasterxml.jackson.databind.ObjectMapper;
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
public class PatientControllerTest {

  @Autowired
  private WebApplicationContext wac;

  private MockMvc mockMvc;

  @Before
  public void setUp() {
    mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
  }

  @Test
  public void getAllPatientsReturns200() throws Exception {
    mockMvc.perform(get(PATIENTS_PATH)).andExpect(status().isOk());
  }

  @Test
  public void getPatientByIdReturns200() throws Exception {
    mockMvc.perform(get(PATIENTS_PATH + "/1")).andExpect(status().isOk());
  }

  @Test
  public void createPatientReturns201() throws Exception {
    Patient testPatient = new Patient("David", "Johnson", "123-22-2314", "d@j.com", "street", "city", "CA", "92345", 25, 162, 200, "None", "Male");
    ObjectMapper mapper = new ObjectMapper();
    String asJson = mapper.writeValueAsString(testPatient);
    mockMvc.perform(post(PATIENTS_PATH).contentType(MediaType.APPLICATION_JSON).content(asJson))
        .andExpect(status().isCreated());
  }

  @Test
  public void updatePatientReturns200() throws Exception {
    Patient testPatient = new Patient("David", "Johnson", "123-22-2314", "da@j.com", "street", "city", "CA", "92345", 25, 162, 200, "None", "Male");
    ObjectMapper mapper = new ObjectMapper();
    String asJson = mapper.writeValueAsString(testPatient);
    mockMvc.perform(put(PATIENTS_PATH + "/1").contentType(MediaType.APPLICATION_JSON).content(asJson))
        .andExpect(status().isOk());
  }

  @Test
  public void deletePatientReturnsNoContentAndDeletesPatient() throws Exception {
    mockMvc.perform(delete(PATIENTS_PATH + "/2").contentType(MediaType.ALL)
        .content("1L")).andExpect(status().isNoContent());
  }

}