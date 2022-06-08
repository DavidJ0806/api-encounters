package io.catalyte.training.sportsproducts.domains.patient;

import static io.catalyte.training.sportsproducts.constants.Paths.PATIENTS_PATH;

import io.catalyte.training.sportsproducts.domains.enctouner.EncounterController;
import java.util.List;
import javax.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest controller for patient entity
 */
@RestController
@RequestMapping(value = PATIENTS_PATH)
public class PatientController {

  Logger logger = LogManager.getLogger(EncounterController.class);

  private final PatientService patientService;

  @Autowired
  public PatientController(
      PatientService patientService) {
    this.patientService = patientService;
  }

  /**
   * Exposes the endpoint for all patients
   *
   * @return List of Patient, HttpStatus
   */
  @GetMapping
  public ResponseEntity<List<Patient>> findAll() {
    logger.info("Request received for getPatients");
    return new ResponseEntity<>(patientService.getAllPatients(), HttpStatus.OK);
  }

  /**
   * Exposes the endpoint for a specific patient
   *
   * @param id Long
   * @return Patient, HttpStatus
   */
  @GetMapping(path = "/{id}")
  public ResponseEntity<Patient> findById(@PathVariable Long id) {
    logger.info("Request received for getPatientById");
    return new ResponseEntity<>(patientService.getPatientById(id), HttpStatus.OK);
  }

  /**
   * Exposes the Post mapping for the patient endpoint
   *
   * @param patient Patient
   * @return Patient, HttpStatus
   */
  @PostMapping
  public ResponseEntity<Patient> createEntity(@Valid @RequestBody Patient patient) {
    logger.info("Request received for createPatient");
    return new ResponseEntity<>(patientService.createPatient(patient), HttpStatus.CREATED);
  }

  /**
   * Exposes the mapping to update a patient
   *
   * @param id      Long
   * @param patient Patient
   * @return Patient, HttpStatus
   */
  @PutMapping(path = "/{id}")
  public ResponseEntity<Patient> updateEntity(
      @PathVariable Long id,
      @Valid @RequestBody Patient patient
  ) {
    logger.info("Request received for updatePatient");
    return new ResponseEntity<>(patientService.updatePatient(patient, id), HttpStatus.OK);
  }

  /**
   * Exposes endpoint to delete patients by id
   *
   * @param id Long
   * @return HttpStatus
   */
  @DeleteMapping("/{id}")
  public ResponseEntity<Long> deleteById(@PathVariable Long id) {
    logger.info("Request received for deletePatientById: " + id);
    patientService.deletePatient(patientService.getPatientById(id));
    return new ResponseEntity<>(id, HttpStatus.NO_CONTENT);
  }
}
