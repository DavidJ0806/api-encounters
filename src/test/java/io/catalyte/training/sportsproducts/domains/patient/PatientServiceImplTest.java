package io.catalyte.training.sportsproducts.domains.patient;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

import io.catalyte.training.sportsproducts.domains.enctouner.Encounter;
import io.catalyte.training.sportsproducts.domains.enctouner.EncounterRepository;
import io.catalyte.training.sportsproducts.exceptions.ResourceNotFound;
import io.catalyte.training.sportsproducts.exceptions.ServerError;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.DataAccessException;
import org.springframework.web.server.ResponseStatusException;


@RunWith(MockitoJUnitRunner.class)
public class PatientServiceImplTest {

  @Mock
  private PatientRepository patientRepository;

  @Mock
  private EncounterRepository encounterRepository;

  @InjectMocks
  PatientServiceImpl patientServiceImpl;

  @Test
  public void getAllPatientsThrowsDataAccessException() {
    when(patientRepository.findAll()).thenThrow(new DataAccessException("Server-error") {
    });
    assertThrows(ServerError.class, () -> patientServiceImpl.getAllPatients());
  }

  @Test
  public void getPatientByIdThrowsDataAccessException() {
    when(patientRepository.findById(anyLong())).thenThrow(new DataAccessException("Server-error") {
    });
    assertThrows(ServerError.class, () -> patientServiceImpl.getPatientById(1L));
  }

  @Test
  public void createPatientThrowsDataAccessException() {
    Patient testPatient = new Patient("David", "Johnson", "123-22-2314", "d@j.com", "street", "city", "CA", "92345", 25, 162, 200, "None", "Male");
    when(patientRepository.findByEmail(anyString())).thenThrow(new DataAccessException("Server-error") {
    });
    assertThrows(ServerError.class, () -> patientServiceImpl.createPatient(testPatient));
  }

  @Test
  public void createPatientThrowsResponseStatusExceptionIfEmailIsTaken() {
    Patient testPatient = new Patient("David", "Johnson", "123-22-2314", "hulksnewemailaddress@wwf.com", "street", "city", "CA", "92345", 25, 162, 200, "None", "Male");
    when(patientRepository.findByEmail(anyString())).thenReturn(testPatient);
    assertThrows(ResponseStatusException.class, () -> patientServiceImpl.createPatient(testPatient));
  }

  @Test
  public void createPatientThrowsDataAccessExceptionWhenSaving() {
    Patient testPatient = new Patient("David", "Johnson", "123-22-2314", "hulksnewemailaddress@wwf.com", "street", "city", "CA", "92345", 25, 162, 200, "None", "Male");
    when(patientRepository.findByEmail(anyString())).thenReturn(null);
    when(patientRepository.save(any(Patient.class))).thenThrow(new DataAccessException("Server-error") {
    });
    assertThrows(ServerError.class, () -> patientServiceImpl.createPatient(testPatient));
  }

  @Test
  public void updatePatientThrowsDataAccessExceptionWhenFindingById() {
    Patient testPatient = new Patient("David", "Johnson", "123-22-2314", "hulksnewemailaddress@wwf.com", "street", "city", "CA", "92345", 25, 162, 200, "None", "Male");
    when(patientRepository.findById(anyLong())).thenThrow(new DataAccessException("Server-error") {
    });
    assertThrows(ServerError.class, () -> patientServiceImpl.updatePatient(testPatient, 1L));
  }

  @Test
  public void updatePatientThrowsResourceNotFoundWhenPatientDoesNotExist() {
    Patient testPatient = new Patient("David", "Johnson", "123-22-2314", "hulksnewemailaddress@wwf.com", "street", "city", "CA", "92345", 25, 162, 200, "None", "Male");
    assertThrows(ResourceNotFound.class, () -> patientServiceImpl.updatePatient(testPatient, 20L));
  }

  @Test
  public void updatePatientThrowsResponseStatusExceptionIfIdsDoNotMatch() {
    Patient updatedPatient = new Patient(50L, "David", "Johnson", "123-22-2314", "hulksnewemailaddress@wwf.com", "street", "city", "CA", "92345", 25, 162, 200, "None", "Male");
    Patient existingPatient = new Patient(100L, "David", "Johnson", "123-22-2314", "hulksnewemailaddress@wwf.com", "street", "city", "CA", "92345", 25, 162, 200, "None", "Male");
    when(patientRepository.findById(anyLong())).thenReturn(java.util.Optional.of(existingPatient));
    assertThrows(ResponseStatusException.class, () -> patientServiceImpl.updatePatient(updatedPatient, 50L));
  }

  @Test
  public void updatePatientThrowsResponseStatusExceptionWhenUpdatingToExistingEmail() {
    Patient updatedPatient = new Patient(4L, "David", "Johnson", "123-22-2314", "hulksnewemailaddress@wwf.com", "street", "city", "CA", "92345", 25, 162, 200, "None", "Male");
    Patient existingPatient = new Patient(4L, "David", "Johnson", "123-22-2314", "hulksnewemailadd2ress@wwf.com", "street", "city", "CA", "92345", 25, 162, 200, "None", "Male");
    when(patientRepository.findById(anyLong())).thenReturn(java.util.Optional.of(existingPatient));
    when(patientRepository.findByEmail(anyString())).thenReturn(existingPatient);
    assertThrows(ResponseStatusException.class, () -> patientServiceImpl.updatePatient(updatedPatient, 4L));
  }

  @Test
  public void updatePatientThrowsDataAccessExceptionWhenFindByEmail() {
    Patient updatedPatient = new Patient(4L, "David", "Johnson", "123-22-2314", "hulksnewemailaddress@wwf.com", "street", "city", "CA", "92345", 25, 162, 200, "None", "Male");
    Patient existingPatient = new Patient(4L, "David", "Johnson", "123-22-2314", "hulksnewemailaddr2ess@wwf.com", "street", "city", "CA", "92345", 25, 162, 200, "None", "Male");
    when(patientRepository.findById(anyLong())).thenReturn(java.util.Optional.of(existingPatient));
    when(patientRepository.findByEmail(anyString())).thenThrow(new DataAccessException("Server-error") {
    });
    assertThrows(ServerError.class, () -> patientServiceImpl.updatePatient(updatedPatient, 4L));
  }

  @Test
  public void updatePatientThrowsDataAccessExceptionWhenSaving() {
    Patient updatedPatient = new Patient(4L, "David", "Johnson", "123-22-2314", "hulksnewemailaddress@wwf.com", "street", "city", "CA", "92345", 25, 162, 200, "None", "Male");
    Patient existingPatient = new Patient(4L, "David", "Johnson", "123-22-2314", "hulksnewemailaddress@wwf.com", "street", "city", "CA", "92345", 25, 162, 200, "None", "Male");
    when(patientRepository.findById(anyLong())).thenReturn(java.util.Optional.of(existingPatient));
    when(patientRepository.save(any(Patient.class))).thenThrow(new DataAccessException("Server-error"){
    });
    assertThrows(ServerError.class, () -> patientServiceImpl.updatePatient(updatedPatient, 4L));
  }

  @Test
  public void deletePatientThrowsDataAccessExceptionWhenFindById() {
    Patient deletedPatient = new Patient(4L, "David", "Johnson", "123-22-2314", "hulksnewemailaddress@wwf.com", "street", "city", "CA", "92345", 25, 162, 200, "None", "Male");
    when(encounterRepository.findAllByPatientId(anyLong())).thenThrow(new DataAccessException("Server-error"){
    });
    assertThrows(ServerError.class, () -> patientServiceImpl.deletePatient(deletedPatient));
  }

  @Test
  public void deletePatientThrowsResponseStatusExceptionWhenDeletingWithEncounters() {
    Patient deletedPatient = new Patient(4L, "David", "Johnson", "123-22-2314", "hulksnewemailaddress@wwf.com", "street", "city", "CA", "92345", 25, 162, 200, "None", "Male");
    LocalDate localDate = LocalDate.of(2020, 8, 4);
    BigDecimal cost = BigDecimal.valueOf(10.00).setScale(2, RoundingMode.HALF_UP);
    BigDecimal copay = BigDecimal.valueOf(10.10).setScale(2, RoundingMode.HALF_UP);
    List<Encounter> encounterList = new ArrayList<>();
    Encounter existingEncounter = new Encounter(1L, "new encounter", "N3W 3C3", "New Hospital", "123.456.789-00", "Z99", cost, copay, "new complaint", 0, 0, 0, localDate);
    encounterList.add(existingEncounter);
    when(encounterRepository.findAllByPatientId(anyLong())).thenReturn(encounterList);
    assertThrows(ResponseStatusException.class, () -> patientServiceImpl.deletePatient(deletedPatient));
  }

  @Test
  public void deletePatientThrowsDataAccessExceptionWhenDeleting() {
    Patient patient = new Patient(4L, "David", "Johnson", "123-22-2314", "hulksnewemailaddress@wwf.com", "street", "city", "CA", "92345", 25, 162, 200, "None", "Male");
    List<Encounter> newList = new ArrayList<>();
    when(encounterRepository.findAllByPatientId(anyLong())).thenReturn(newList);
    doThrow(new DataAccessException("Server-error") {
    }).when(patientRepository).delete(patient);
    assertThrows(ServerError.class, () -> patientServiceImpl.deletePatient(patient));
  }
}