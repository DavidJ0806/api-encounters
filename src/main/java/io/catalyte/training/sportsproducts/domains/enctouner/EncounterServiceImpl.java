package io.catalyte.training.sportsproducts.domains.enctouner;

import io.catalyte.training.sportsproducts.domains.patient.PatientController;
import io.catalyte.training.sportsproducts.domains.patient.PatientRepository;
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
 * Implements encounter service interface
 */
@Service
public class EncounterServiceImpl implements EncounterService {

  private final Logger logger = LogManager.getLogger(PatientController.class);

  private final EncounterRepository encounterRepository;
  private final PatientRepository patientRepository;

  @Autowired
  public EncounterServiceImpl(
      EncounterRepository encounterRepository,
      PatientRepository patientRepository) {
    this.encounterRepository = encounterRepository;
    this.patientRepository = patientRepository;
  }

  /**
   * Creates an encounter and saves it to the database
   *
   * @param encounter Encounter
   * @param id        Long
   * @return Encounter
   */
  @Override
  public Encounter createEncounter(Encounter encounter, Long id) {

    // MISMATCHING IDs
    if (!encounter.patientId.equals(id)) {
      logger.error("Provided patient id does not match with the path id");
      throw new ResponseStatusException(HttpStatus.BAD_REQUEST,
          "Provided patient id does not match with the path id");
    }

    // IF USER TRIES TO UPDATE A PATIENT, CHECK IF THE PATIENT EXISTS
    try {
      patientRepository.findById(encounter.patientId)
          .orElseThrow(() -> new ResourceNotFound("patient doesn't exist"));
    } catch (DataAccessException dae) {
      logger.error(dae.getMessage());
      throw new ServerError(dae.getMessage());
    }

    // SAVE ENCOUNTER
    try {
      logger.info("Saved encounter");
      return encounterRepository.save(encounter);
    } catch (DataAccessException dae) {
      logger.error(dae.getMessage());
      throw new ServerError(dae.getMessage());
    }
  }

  /**
   * Updates an existing encounter with the updated version
   *
   * @param updatedEncounter Encounter
   * @param patientId        Long
   * @param encounterId      Long
   * @return Updated Encounter
   */
  @Override
  public Encounter updateEncounter(Encounter updatedEncounter, Long patientId, Long encounterId) {

    // ENCOUNTER MUST EXIST
    try {
      encounterRepository.findById(encounterId)
          .orElseThrow(() -> new ResourceNotFound("Encounter " + encounterId + " doesn't exist"));
    } catch (DataAccessException dae) {
      logger.error(dae.getMessage());
      throw new ServerError(dae.getMessage());
    }

    // AVOID DUPLICATES
    if (updatedEncounter.id == null) {
      updatedEncounter.setId(encounterId);
    }

    // NEW PATIENT MUST EXIST
    if (!patientId.equals(updatedEncounter.patientId)) {
      try {
        patientRepository.findById(patientId)
            .orElseThrow(() -> new ResourceNotFound("Patient doesn't exist"));
      } catch (DataAccessException dae) {
        logger.error(dae.getMessage());
        throw new ServerError(dae.getMessage());
      }
    }

    // SAVE
    try {
      logger.info("Updated Encounter");
      return encounterRepository.save(updatedEncounter);
    } catch (DataAccessException dae) {
      logger.error(dae.getMessage());
      throw new ServerError(dae.getMessage());
    }
  }

  @Override
  public Encounter getEncounterById(Long patientId, Long encounterId) {
    Encounter encounter;
    try {
      encounter = encounterRepository.findById(encounterId).orElse(null);
      return encounter;
    } catch (DataAccessException dae) {
      logger.error(dae.getMessage());
      throw new ServerError(dae.getMessage());
    }
  }

  @Override
  public List<Encounter> getAllEncountersByPatientId(Long id) {
    List<Encounter> encountersList;

    try {
      encountersList = encounterRepository.findAllByPatientId(id);
      return encountersList;
    } catch (DataAccessException dae) {
      logger.error(dae.getMessage());
      throw new ServerError(dae.getMessage());
    }
  }
}
