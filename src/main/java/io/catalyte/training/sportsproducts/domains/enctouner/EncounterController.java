package io.catalyte.training.sportsproducts.domains.enctouner;

import static io.catalyte.training.sportsproducts.constants.Paths.PATIENTS_PATH;

import java.util.List;
import javax.validation.Valid;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Rest controller for encounter entity
 */
@RestController
@RequestMapping(value = PATIENTS_PATH)
public class EncounterController {

  Logger logger = LogManager.getLogger(EncounterController.class);

  private final EncounterService encounterService;

  @Autowired
  public EncounterController(
      EncounterService encounterService) {
    this.encounterService = encounterService;
  }

  /**
   * Exposes endpoint to post to encounters
   *
   * @param encounter Encounter
   * @param id        Long
   * @return Encounter, HttpStatus
   */
  @PostMapping(path = "/{id}/encounters")
  public ResponseEntity<Encounter> createEntity(@Valid @RequestBody Encounter encounter,
      @PathVariable Long id) {
    logger.info("Request received for createEncounter");
    return new ResponseEntity<>(encounterService.createEncounter(encounter, id),
        HttpStatus.CREATED);
  }

  /**
   * Exposes endpoint to get all encounters by patient id
   * @param id Long
   * @return List Encounters
   */
  @GetMapping(path = "/{id}/encounters")
  public ResponseEntity<List<Encounter>> getAllById(@PathVariable Long id) {
    logger.info("Request received for getAllEncountersByPatientId");
    return new ResponseEntity<>(encounterService.getAllEncountersByPatientId(id), HttpStatus.OK);
  }

  /**
   * Exposes endpoint to get a specific encounter
   * @param id Long
   * @param encounterId Long
   * @return Encounter
   */
  @GetMapping(path = "/{id}/encounters/{encounterId}")
  public ResponseEntity<Encounter> getEncounterById(@PathVariable Long id, @PathVariable Long encounterId) {
    logger.info("Request received for getEncounterById");
    return new ResponseEntity<>(encounterService.getEncounterById(id, encounterId), HttpStatus.OK);
  }

  /**
   * Exposes endpoint to edit an encounter
   *
   * @param patientId   Long
   * @param encounterId Long
   * @param encounter   Encounter
   * @return Encounter, HttpStatus
   */
  @PutMapping(path = "/{patientId}/encounters/{encounterId}")
  public ResponseEntity<Encounter> updateEncounter(
      @PathVariable Long patientId,
      @PathVariable Long encounterId,
      @Valid @RequestBody Encounter encounter
  ) {
    logger.info("Request received for updateEncounter");
    return new ResponseEntity<>(encounterService.updateEncounter(encounter, patientId, encounterId),
        HttpStatus.OK);
  }

}
