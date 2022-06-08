package io.catalyte.training.sportsproducts.domains.patient;

import java.util.List;

/**
 * This interface provides an abstraction layer for the Patient Service
 */
public interface PatientService {

  List<Patient> getAllPatients();

  Patient getPatientById(Long id);

  Patient createPatient(Patient patient);

  Patient updatePatient(Patient patient, Long id);

  void deletePatient(Patient patient);
}
