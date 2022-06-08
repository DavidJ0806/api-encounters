package io.catalyte.training.sportsproducts.domains.enctouner;

import java.util.List;

/**
 * This interface provides an abstraction layer for the Encounter Service
 */
public interface EncounterService {

  Encounter createEncounter(Encounter encounter, Long id);

  Encounter updateEncounter(Encounter encounter, Long patientId, Long encounterId);

  Encounter getEncounterById(Long patientId, Long encounterId);

  List<Encounter> getAllEncountersByPatientId(Long id);
}
