package io.catalyte.training.sportsproducts.domains.patient;

import io.catalyte.training.sportsproducts.domains.enctouner.Encounter;
import io.catalyte.training.sportsproducts.domains.enctouner.EncounterRepository;
import io.catalyte.training.sportsproducts.exceptions.ResourceNotFound;
import io.catalyte.training.sportsproducts.exceptions.ServerError;
import java.util.List;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

/**
 * Implements patient service interface
 */
@Service
public class PatientServiceImpl implements PatientService {

  private final Logger logger = LogManager.getLogger(PatientController.class);

  private final PatientRepository patientRepository;

  private final EncounterRepository encounterRepository;

  @Autowired
  public PatientServiceImpl(
      PatientRepository patientRepository,
      EncounterRepository encounterRepository) {
    this.patientRepository = patientRepository;
    this.encounterRepository = encounterRepository;
  }

  /**
   * Calls the patient repository to find all patients
   *
   * @return List of Patient
   */
  @Override
  public List<Patient> getAllPatients() {
    try {
      return patientRepository.findAll();
    } catch (DataAccessException dae) {
      logger.error(dae.getMessage());
      throw new ServerError(dae.getMessage());
    }
  }

  /**
   * Calls the repository to find a patient by id
   *
   * @param id Long
   * @return Patient
   */
  @Override
  public Patient getPatientById(Long id) {
    Patient patient;

    try {
      patient = patientRepository.findById(id)
          .orElseThrow(() -> new ResourceNotFound("Patient with id " + id + " doesn't exist"));
    } catch (DataAccessException e) {
      logger.error(e.getMessage());
      throw new ServerError(e.getMessage());
    }
    return patient;
  }

  /**
   * Posts a patient in the repo
   *
   * @param patient Patient
   * @return Patient
   */
  @Override
  public Patient createPatient(Patient patient) {
    // CHECK TO MAKE SURE USER EMAIL IS NOT TAKEN
    Patient existingPatient;

    try {
      existingPatient = patientRepository.findByEmail(patient.getEmail());
    } catch (DataAccessException dae) {
      logger.error(dae.getMessage());
      throw new ServerError(dae.getMessage());
    }

    if (existingPatient != null) {
      logger.error("Email is taken");
      throw new ResponseStatusException(HttpStatus.CONFLICT, "Email is taken");
    }

    try {
      logger.info("Saved patient");
      return patientRepository.save(patient);
    } catch (DataAccessException dae) {
      logger.error(dae.getMessage());
      throw new ServerError(dae.getMessage());
    }
  }

  /**
   * Updates a patient to the repo
   *
   * @param updatedPatient Patient
   * @param id             Long
   * @return Patient
   */
  @Override
  public Patient updatePatient(Patient updatedPatient, Long id) {
    Patient existingPatient;

    try {
      existingPatient = patientRepository.findById(id).orElse(null);
    } catch (DataAccessException dae) {
      logger.error(dae.getMessage());
      throw new ServerError(dae.getMessage());
    }

    if (existingPatient == null) {
      logger.error("Patient with id: " + id + " does not exist");
      throw new ResourceNotFound("Patient with id: " + id + " does not exist");
    }

    // GIVE THE PATIENT ID IF NOT SPECIFIED IN BODY TO AVOID DUPLICATE PATIENTS
    if (updatedPatient.getId() == null) {
      updatedPatient.setId(id);
    }

    if (!updatedPatient.getId().equals(existingPatient.id)) {
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Patient id does not match");
    }

    try {
      if (!updatedPatient.getEmail().equals(existingPatient.getEmail())) {
        Patient existingPatientWithEmail = patientRepository.findByEmail(updatedPatient.email);
        if (existingPatientWithEmail != null) {
          throw new ResponseStatusException(HttpStatus.CONFLICT, "Patient email already exists");
        }
      }
    } catch (DataAccessException dae) {
      logger.error(dae.getMessage());
      throw new ServerError(dae.getMessage());
    }

    try {
      logger.info("Updated Patient");
      return patientRepository.save(updatedPatient);
    } catch (DataAccessException dae) {
      logger.error(dae.getMessage());
      throw new ServerError(dae.getMessage());
    }
  }

  /**
   * Deletes a patient from the repo
   *
   * @param patient Patient
   */
  @Override
  public void deletePatient(Patient patient) {
    List<Encounter> existingEncounters;

    try {
      existingEncounters = encounterRepository.findAllByPatientId(patient.id);
    } catch (DataAccessException dae) {
      logger.info(dae.getMessage());
      throw new ServerError(dae.getMessage());
    }

    if (existingEncounters.size() != 0) {
      throw new ResponseStatusException(HttpStatus.CONFLICT,
          "Cannot delete a patient with encounters");
    }

    try {
      patientRepository.delete(patient);
    } catch (DataAccessException dae) {
      logger.info(dae.getMessage());
      throw new ServerError(dae.getMessage());
    }
  }

}
