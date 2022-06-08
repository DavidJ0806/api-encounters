package io.catalyte.training.sportsproducts.domains.enctouner;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Objects;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Table(name = "Encounters")
public class Encounter {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  @NotNull(message = "Patient Id is required.")
  Long patientId;

  String notes;

  @NotBlank(message = "Visit Code is required.")
  @Pattern(regexp = "^(\\w\\d\\w\\s\\d\\w\\d)$", message = "Visit Code must follow format example 'H7J 8W2'.")
  String visitCode;

  @NotBlank(message = "Provider is required.")
  String provider;

  @NotBlank(message = "Billing code is required.")
  @Pattern(regexp = "^(\\d){3}\\.(\\d){3}\\.(\\d){3}-(\\d){2}$", message = "Billing code must follow the example '123.456.789-12'.")
  String billingCode;

  @NotBlank(message = "ICD10 code is required.")
  @Pattern(regexp = "^(\\w)(\\d){2}$", message = "Must follow format example 'A22'.")
  String icd10;

  @Cost
  BigDecimal totalCost;

  @Cost
  BigDecimal copay;

  @NotBlank(message = "Chief complaint is required.")
  String chiefComplaint;

  Integer pulse;

  @Min(value = 1, message = "Must be greater than 1")
  Integer systolic;

  @Min(value = 1, message = "Must be greater than 1")
  Integer diastolic;

  // TODO: exception handling to clean up the parse exception response
  @DateTimeFormat(pattern = "YYYY-MM-DD")
  LocalDate date;

  public Encounter() {
  }

  public Encounter(Long id, Long patientId, String notes, String visitCode, String provider,
      String billingCode, String icd10, BigDecimal totalCost, BigDecimal copay,
      String chiefComplaint, Integer pulse, Integer systolic, Integer diastolic,
      LocalDate date) {
    this.id = id;
    this.patientId = patientId;
    this.notes = notes;
    this.visitCode = visitCode;
    this.provider = provider;
    this.billingCode = billingCode;
    this.icd10 = icd10;
    this.totalCost = totalCost;
    this.copay = copay;
    this.chiefComplaint = chiefComplaint;
    this.pulse = pulse;
    this.systolic = systolic;
    this.diastolic = diastolic;
    this.date = date;
  }

  public Encounter(Long patientId, String notes, String visitCode, String provider,
      String billingCode, String icd10, BigDecimal totalCost, BigDecimal copay,
      String chiefComplaint, LocalDate date) {
    this.patientId = patientId;
    this.notes = notes;
    this.visitCode = visitCode;
    this.provider = provider;
    this.billingCode = billingCode;
    this.icd10 = icd10;
    this.totalCost = totalCost;
    this.copay = copay;
    this.chiefComplaint = chiefComplaint;
    this.date = date;
  }

  public Encounter(Long patientId, String notes, String visitCode, String provider,
      String billingCode, String icd10, BigDecimal totalCost, BigDecimal copay,
      String chiefComplaint, Integer pulse, Integer systolic, Integer diastolic,
      LocalDate date) {
    this.patientId = patientId;
    this.notes = notes;
    this.visitCode = visitCode;
    this.provider = provider;
    this.billingCode = billingCode;
    this.icd10 = icd10;
    this.totalCost = totalCost;
    this.copay = copay;
    this.chiefComplaint = chiefComplaint;
    this.pulse = pulse;
    this.systolic = systolic;
    this.diastolic = diastolic;
    this.date = date;
  }

  public Encounter(long patientId, String new_encounter, String visitCode, String new_hospital,
      String billingCode, String z99, BigDecimal cost, BigDecimal copay, String new_complaint,
      int pulse, int systolic, int diastolic, String s) {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getPatientId() {
    return patientId;
  }

  public void setPatientId(Long patientId) {
    this.patientId = patientId;
  }

  public String getNotes() {
    return notes;
  }

  public void setNotes(String notes) {
    this.notes = notes;
  }

  public String getVisitCode() {
    return visitCode;
  }

  public void setVisitCode(String visitCode) {
    this.visitCode = visitCode;
  }

  public String getProvider() {
    return provider;
  }

  public void setProvider(String provider) {
    this.provider = provider;
  }

  public String getBillingCode() {
    return billingCode;
  }

  public void setBillingCode(String billingCode) {
    this.billingCode = billingCode;
  }

  public String getIcd10() {
    return icd10;
  }

  public void setIcd10(String icd10) {
    this.icd10 = icd10;
  }

  public BigDecimal getTotalCost() {
    return totalCost;
  }

  public void setTotalCost(BigDecimal totalCost) {
    this.totalCost = totalCost;
  }

  public BigDecimal getCopay() {
    return copay;
  }

  public void setCopay(BigDecimal copay) {
    this.copay = copay;
  }

  public String getChiefComplaint() {
    return chiefComplaint;
  }

  public void setChiefComplaint(String chiefComplaint) {
    this.chiefComplaint = chiefComplaint;
  }

  public Integer getPulse() {
    return pulse;
  }

  public void setPulse(Integer pulse) {
    this.pulse = pulse;
  }

  public Integer getSystolic() {
    return systolic;
  }

  public void setSystolic(Integer systolic) {
    this.systolic = systolic;
  }

  public Integer getDiastolic() {
    return diastolic;
  }

  public void setDiastolic(Integer diastolic) {
    this.diastolic = diastolic;
  }

  public LocalDate getDate() {
    return date;
  }

  public void setDate(LocalDate date) {
    this.date = date;
  }

  @Override
  public String toString() {
    return "Encounter{" +
        "id=" + id +
        ", patientId=" + patientId +
        ", notes='" + notes + '\'' +
        ", visitCode='" + visitCode + '\'' +
        ", provider='" + provider + '\'' +
        ", billingCode='" + billingCode + '\'' +
        ", icd10='" + icd10 + '\'' +
        ", totalCost=" + totalCost +
        ", copay=" + copay +
        ", chiefComplaint='" + chiefComplaint + '\'' +
        ", pulse=" + pulse +
        ", systolic=" + systolic +
        ", diastolic=" + diastolic +
        ", date=" + date +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Encounter encounter = (Encounter) o;
    return Objects.equals(id, encounter.id) && Objects.equals(patientId,
        encounter.patientId) && Objects.equals(notes, encounter.notes)
        && Objects.equals(visitCode, encounter.visitCode) && Objects.equals(
        provider, encounter.provider) && Objects.equals(billingCode, encounter.billingCode)
        && Objects.equals(icd10, encounter.icd10) && Objects.equals(totalCost,
        encounter.totalCost) && Objects.equals(copay, encounter.copay)
        && Objects.equals(chiefComplaint, encounter.chiefComplaint)
        && Objects.equals(pulse, encounter.pulse) && Objects.equals(systolic,
        encounter.systolic) && Objects.equals(diastolic, encounter.diastolic)
        && Objects.equals(date, encounter.date);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, patientId, notes, visitCode, provider, billingCode, icd10, totalCost,
        copay, chiefComplaint, pulse, systolic, diastolic, date);
  }
}
