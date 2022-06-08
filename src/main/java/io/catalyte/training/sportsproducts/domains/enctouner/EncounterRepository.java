package io.catalyte.training.sportsproducts.domains.enctouner;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Encounter Repo
 */
@Repository
public interface EncounterRepository extends JpaRepository<Encounter, Long> {

  List<Encounter> findAllByPatientId(Long patientId);
}
