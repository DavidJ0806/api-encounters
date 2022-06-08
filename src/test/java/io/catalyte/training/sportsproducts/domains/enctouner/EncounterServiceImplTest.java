package io.catalyte.training.sportsproducts.domains.enctouner;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import io.catalyte.training.sportsproducts.domains.patient.Patient;
import io.catalyte.training.sportsproducts.domains.patient.PatientRepository;
import io.catalyte.training.sportsproducts.domains.patient.PatientServiceImpl;
import io.catalyte.training.sportsproducts.exceptions.ServerError;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.DataAccessException;
import org.springframework.web.server.ResponseStatusException;

@RunWith(MockitoJUnitRunner.class)
public class EncounterServiceImplTest {

  @Mock
  private PatientRepository patientRepository;

  @Mock
  private EncounterRepository encounterRepository;

  @InjectMocks
  EncounterServiceImpl encounterServiceImpl;

  public Encounter createEncounter() {
    LocalDate localDate = LocalDate.of(2020, 8, 4);
    BigDecimal cost = BigDecimal.valueOf(10.00).setScale(2, RoundingMode.HALF_UP);
    BigDecimal copay = BigDecimal.valueOf(10.10).setScale(2, RoundingMode.HALF_UP);
    return new Encounter(1L, "new encounter", "N3W 3C3", "New Hospital", "123.456.789-00", "Z99", cost, copay, "new complaint", localDate);
  }

  @Test
  public void createEncounterThrowsResponseStatusExceptionWhenIdsDoNotMatch() {
    Encounter encounter = createEncounter();
    assertThrows(ResponseStatusException.class, () -> encounterServiceImpl.createEncounter(encounter, 5L));
  }

  @Test
  public void createEncounterThrowsDataAccessExceptionWhenFindById() {
    when(patientRepository.findById(anyLong())).thenThrow(new DataAccessException("Server-error") {
    });
    assertThrows(ServerError.class, () -> encounterServiceImpl.createEncounter(createEncounter(), 1L));
  }

  @Test
  public void createEncounterThrowsDataAccessExceptionWhenSave() {
    when(patientRepository.findById(any())).thenReturn(java.util.Optional.of(
        new Patient("David", "Johnson", "123-22-2314", "d23@j.com", "street", "city", "CA", "92345",
            25, 162, 200, "None", "Male")));
    when(encounterRepository.save(any(Encounter.class))).thenThrow(new DataAccessException("Server-error") {
    });
    assertThrows(ServerError.class, () -> encounterServiceImpl.createEncounter(createEncounter(), 1L));
  }

  @Test
  public void updateEncounterThrowsDataAccessExceptionWhenFindById() {
    when(encounterRepository.findById(anyLong())).thenThrow(new DataAccessException("Server-error") {
    });
    assertThrows(ServerError.class, () -> encounterServiceImpl.updateEncounter(createEncounter(), 1L, 1L));
  }

  @Test
  public void updateEncounterThrowsDataAccessExceptionWhenPatientFindById() {
    Encounter encounter = createEncounter();
    when(encounterRepository.findById(anyLong())).thenReturn(
        java.util.Optional.ofNullable(encounter));
    when(patientRepository.findById(anyLong())).thenThrow(new DataAccessException("Server-error"){
    });
    assertThrows(ServerError.class, () -> encounterServiceImpl.updateEncounter(encounter, 50L, 1L));
  }

  @Test
  public void updateEncounterThrowsDataAccessExceptionWhenSave() {
    Encounter encounter = createEncounter();
    encounter.setPatientId(2L);
    when(encounterRepository.findById(anyLong())).thenReturn(
        java.util.Optional.of(encounter));
    when(patientRepository.findById(anyLong())).thenReturn(java.util.Optional.of(
        new Patient("David", "Johnson", "123-22-2314", "d23@j.com", "street", "city", "CA", "92345",
            25, 162, 200, "None", "Male")));
    when(encounterRepository.save(any(Encounter.class))).thenThrow(new DataAccessException("Server-error") {
    });
    assertThrows(ServerError.class, () -> encounterServiceImpl.updateEncounter(encounter, 1L, 1L));
  }

  @Test
  public void getAllEncountersByPatientIdThrowsDAEWhenFindingAll() {
    when(encounterRepository.findAllByPatientId(anyLong())).thenThrow(new DataAccessException("server-error") {
    });
    assertThrows(ServerError.class, () -> encounterServiceImpl.getAllEncountersByPatientId(1L));
  }

  @Test
  public void getEncounterByIdThrowsDAEWhenFindingById() {
    when(encounterRepository.findById(anyLong())).thenThrow(new DataAccessException("server-error") {
    });
    assertThrows(ServerError.class, () -> encounterServiceImpl.getEncounterById(1L, 1L));
  }

}