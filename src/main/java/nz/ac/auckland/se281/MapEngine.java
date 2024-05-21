package nz.ac.auckland.se281;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** This class is the main entry point. */
public class MapEngine {

  private Set<Country> countrySet = new HashSet<>();
  private boolean correctInput = false;
  private String start = null;
  private boolean correctStart = false;
  private String end = null;
  private boolean correctEnd = false;

  public MapEngine() {
    // add other code here if you want
    loadMap(); // keep this mehtod invocation
  }

  /** invoked one time only when constracting the MapEngine class. */
  private void loadMap() {
    List<String> countries = Utils.readCountries();
    List<String> adjacencies = Utils.readAdjacencies();
    // add code here to create your data structures

    for (String s : countries) {
      String[] parts = s.split(",");
      Country country = new Country(parts[0], parts[1], parts[2]);
      countrySet.add(country);
    }
  }

  /** this method is invoked when the user run the command info-country. */
  public void showInfoCountry() {
    // add code here
    String input = null;

    while (!correctInput) {
      try {
        MessageCli.INSERT_COUNTRY.printMessage();
        input = Utils.scanner.nextLine();
        input = Utils.capitalizeFirstLetterOfEachWord(input);
        Utils.doesCountryExist(input, countrySet);
        correctInput = true;
      } catch (CountryDoesNotExistException e) {
        MessageCli.INVALID_COUNTRY.printMessage(input);
      }
    }

    for (Country country : countrySet) {
      if (country.getCountryName().equals(input)) {
        MessageCli.COUNTRY_INFO.printMessage(input, country.getContinent(), country.getTaxFee());
        correctInput = false;
      }
    }
  }

  /** this method is invoked when the user run the command route. */
  public void showRoute() {

    while (!correctStart) {
      try {
        MessageCli.INSERT_SOURCE.printMessage();
        start = Utils.scanner.nextLine();
        start = Utils.capitalizeFirstLetterOfEachWord(start);
        Utils.doesCountryExist(start, countrySet);
        correctStart = true;
      } catch (CountryDoesNotExistException e) {
        MessageCli.INVALID_COUNTRY.printMessage(start);
      }
    }

    while (!correctEnd) {
      try {
        MessageCli.INSERT_DESTINATION.printMessage();
        end = Utils.scanner.nextLine();
        end = Utils.capitalizeFirstLetterOfEachWord(end);
        Utils.doesCountryExist(end, countrySet);
        correctEnd = true;
      } catch (CountryDoesNotExistException e) {
        MessageCli.INVALID_COUNTRY.printMessage(end);
      }
    }

    if (start.equals(end)) {
      MessageCli.NO_CROSSBORDER_TRAVEL.printMessage();
    }
  }
}
