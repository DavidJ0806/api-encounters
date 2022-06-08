package io.catalyte.training.sportsproducts.data;

import io.catalyte.training.sportsproducts.domains.enctouner.Encounter;
import io.catalyte.training.sportsproducts.domains.enctouner.EncounterRepository;
import io.catalyte.training.sportsproducts.domains.patient.Patient;
import io.catalyte.training.sportsproducts.domains.patient.PatientRepository;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * Because this class implements CommandLineRunner, the run method is executed as soon as the server
 * successfully starts and before it begins accepting requests from the outside. Here, we use this
 * as a place to run some code that generates and saves a list of random products into the
 * database.
 */
@Component
public class DemoData implements CommandLineRunner {

  private final Logger logger = LogManager.getLogger(DemoData.class);

  @Autowired
  private PatientRepository patientRepository;

  @Autowired
  private EncounterRepository encounterRepository;

  @Autowired
  private Environment env;


  public static final int DEFAULT_NUMBER_OF_PRODUCTS = 500;

  @Override
  public void run(String... strings) {
    boolean loadData;

    try {
      // Retrieve the value of custom property in application.yml
      loadData = Boolean.parseBoolean(env.getProperty("products.load"));
    } catch (NumberFormatException nfe) {
      logger.error("config variable loadData could not be parsed, falling back to default");
      loadData = true;
    }

    if (loadData) {
      seedDatabase();
    }
  }

  private void seedDatabase() {
    int numberOfProducts;

    try {
      // Retrieve the value of custom property in application.yml
      numberOfProducts = Integer.parseInt(env.getProperty("products.number"));
    } catch (NumberFormatException nfe) {
      logger.error("config variable numberOfProducts could not be parsed, falling back to default");
      // If it's not a string, set it to be a default value
      numberOfProducts = DEFAULT_NUMBER_OF_PRODUCTS;
    }

    Patient patient = new Patient("Hulk", "Hogan", "123-45-6789", "hulksnewemailaddress@wwf.com",
        "8430 W Sunset Blvd", "Los Angeles", "CA", "90049", 66, 79, 299, "Self-Insured", "Male");
    Patient patient2 = new Patient("Amir", "Hogan", "123-45-6789", "hulksnewemaddress@wwf.com",
        "8430 W Sunset Blvd", "Los Angeles", "CA", "90049", 13, 79, 299, "Self-Insured", "Male");
    patient2.setId(2L);
    Patient patient3 = new Patient("David", "Johnson", "123-45-6789", "d@wwf.com",
        "8430 W Sunset Blvd", "Los Angeles", "CA", "90049", 66, 79, 299, "Self-Insured", "Male");
    patientRepository.save(patient);
    patientRepository.save(patient2);
    patientRepository.save(patient3);


    LocalDate localDate = LocalDate.of(2020, 8, 4);
    BigDecimal cost = BigDecimal.valueOf(10.00).setScale(2, RoundingMode.HALF_UP);
    BigDecimal copay = BigDecimal.valueOf(10.10).setScale(2, RoundingMode.HALF_UP);
    Encounter testEncounter = new Encounter(1L, "new encounter", "N3W 3C3", "New Hospital", "123.456.789-00", "Z99", cost, copay, "new complaint", localDate);
    encounterRepository.save(testEncounter);

    LocalDate localDate2 = LocalDate.of(2020, 8, 6);
    BigDecimal cost2 = BigDecimal.valueOf(100.00).setScale(2, RoundingMode.HALF_UP);
    BigDecimal copay2 = BigDecimal.valueOf(103.10).setScale(2, RoundingMode.HALF_UP);
    Encounter testEncounter2 = new Encounter(1L, "new encounter", "N3W 3C3", "New Hospital", "123.456.789-00", "Z99", cost, copay, "new complaint", localDate);
    encounterRepository.save(testEncounter2);

    LocalDate localDate3 = LocalDate.of(2020, 6, 4);
    BigDecimal cost3 = BigDecimal.valueOf(90.00).setScale(2, RoundingMode.HALF_UP);
    BigDecimal copay3 = BigDecimal.valueOf(903.10).setScale(2, RoundingMode.HALF_UP);
    Encounter testEncounter3 = new Encounter(3L, "new encounter", "N3W 3C3", "New Hospital", "123.456.789-00", "Z99", cost, copay, "new complaint", localDate);
    encounterRepository.save(testEncounter3);
  }

}
