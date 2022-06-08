package io.catalyte.training.sportsproducts.domains.patient;

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

@Entity
@Table(name = "Patients")
public class Patient {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  Long id;

  @Pattern(regexp = "^(?:[A-z]|[-|'](?=[A-z]))+$", message = "First name will accept letters and an optional hyphen or apostrophe.")
  @NotBlank(message = "First name is required.")
  String firstName;

  @Pattern(regexp = "^(?:[A-z]|[-|'](?=[A-z]))+$", message = "Last name will accept letters and an optional hyphen or apostrophe.")
  @NotBlank(message = "Last name is required.")
  String lastName;

  @Pattern(regexp = "^\\d{3}-\\d{2}-\\d{4}$", message = "Must follow format of 123-12-1234.")
  @NotBlank(message = "SSN is required.")
  String ssn;

  @Pattern(regexp = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z]+\\.[a-zA-Z]+$", message = "Email is not valid.")
  @NotBlank(message = "Email is required.")
  String email;

  @NotBlank(message = "Street is required.")
  String street;

  @NotBlank(message = "City is required.")
  String city;

  @Pattern(regexp = "^(A[KLRZ]|C[AOT]|D[CE]|FL|GA|HI|I[ADLN]|K[SY]|LA|M[ADEINOST]|N[CDEHJMVY]|O[HKR]|P[AR]|RI|S[CD]|T[NX]|UT|V[AIT]|W[AIVY]|\\s*)$", message = "Must be a valid two letter state abbreviate. Ex. MD.")
  @NotBlank(message = "State is required.")
  String state;

  @Pattern(regexp = "^\\d{5}(?:[-]\\d{4})?$", message = "Must follow the format 12345 or 12345-1234.")
  @NotBlank(message = "Postal code is required.")
  String postal;

  @NotNull(message = "Age is required.")
  @Min(value = 1, message = "Age must be greater than 0.")
  Integer age;

  @NotNull(message = "Height is required.")
  @Min(value = 1, message = "Height must be greater than 0.")
  Integer height;

  @NotNull(message = "Weight is required.")
  @Min(value = 1, message = "Weight must be greater than 0.")
  Integer weight;

  @NotBlank(message = "Insurance is required.")
  String insurance;

  @Pattern(regexp = "^(Male|Female|Other)$", message = "Gender must be 'Male', 'Female', or 'Other'.")
  @NotBlank(message = "Gender is required.")
  String gender;

  public Patient() {
  }

  public Patient(Long id, String firstName, String lastName, String ssn, String email,
      String street, String city, String state, String postal, Integer age, Integer height,
      Integer weight, String insurance, String gender) {
    this.id = id;
    this.firstName = firstName;
    this.lastName = lastName;
    this.ssn = ssn;
    this.email = email;
    this.street = street;
    this.city = city;
    this.state = state;
    this.postal = postal;
    this.age = age;
    this.height = height;
    this.weight = weight;
    this.insurance = insurance;
    this.gender = gender;
  }

  public Patient(String firstName, String lastName, String ssn, String email, String street,
      String city, String state, String postal, Integer age, Integer height, Integer weight,
      String insurance, String gender) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.ssn = ssn;
    this.email = email;
    this.street = street;
    this.city = city;
    this.state = state;
    this.postal = postal;
    this.age = age;
    this.height = height;
    this.weight = weight;
    this.insurance = insurance;
    this.gender = gender;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getSsn() {
    return ssn;
  }

  public void setSsn(String ssn) {
    this.ssn = ssn;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getStreet() {
    return street;
  }

  public void setStreet(String street) {
    this.street = street;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getState() {
    return state;
  }

  public void setState(String state) {
    this.state = state;
  }

  public String getPostal() {
    return postal;
  }

  public void setPostal(String postal) {
    this.postal = postal;
  }

  public Integer getAge() {
    return age;
  }

  public void setAge(Integer age) {
    this.age = age;
  }

  public Integer getHeight() {
    return height;
  }

  public void setHeight(Integer height) {
    this.height = height;
  }

  public Integer getWeight() {
    return weight;
  }

  public void setWeight(Integer weight) {
    this.weight = weight;
  }

  public String getInsurance() {
    return insurance;
  }

  public void setInsurance(String insurance) {
    this.insurance = insurance;
  }

  public String getGender() {
    return gender;
  }

  public void setGender(String gender) {
    this.gender = gender;
  }

  @Override
  public String toString() {
    return "Patient{" +
        "id=" + id +
        ", firstName='" + firstName + '\'' +
        ", lastName='" + lastName + '\'' +
        ", ssn='" + ssn + '\'' +
        ", email='" + email + '\'' +
        ", street='" + street + '\'' +
        ", city='" + city + '\'' +
        ", state='" + state + '\'' +
        ", postal='" + postal + '\'' +
        ", age=" + age +
        ", height=" + height +
        ", weight=" + weight +
        ", insurance='" + insurance + '\'' +
        ", gender='" + gender + '\'' +
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
    Patient patient = (Patient) o;
    return Objects.equals(id, patient.id) && Objects.equals(firstName,
        patient.firstName) && Objects.equals(lastName, patient.lastName)
        && Objects.equals(ssn, patient.ssn) && Objects.equals(email,
        patient.email) && Objects.equals(street, patient.street)
        && Objects.equals(city, patient.city) && Objects.equals(state,
        patient.state) && Objects.equals(postal, patient.postal)
        && Objects.equals(age, patient.age) && Objects.equals(height,
        patient.height) && Objects.equals(weight, patient.weight)
        && Objects.equals(insurance, patient.insurance) && Objects.equals(gender,
        patient.gender);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, firstName, lastName, ssn, email, street, city, state, postal, age,
        height,
        weight, insurance, gender);
  }
}
