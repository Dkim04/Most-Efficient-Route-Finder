package nz.ac.auckland.se281;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/** This class is the main entry point. */
public class MapEngine {

  private Set<Country> countrySet = new HashSet<>();
  private Graph<Country> riskMap = new Graph<>();
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

    for (int i = 0; i < 42; i++) {
      String[] parts = countries.get(i).split(",");
      Country country = new Country(parts[0], parts[1], parts[2]);
      countrySet.add(country);
    }

    for (int i = 0; i < 42; i++) {
      String[] parts = adjacencies.get(i).split(",");
      Country country = Utils.getCountryByName(parts[0], countrySet);
      for (int j = 1; j < parts.length; j++) {
        riskMap.addEdge(country, Utils.getCountryByName(parts[j], countrySet));
      }
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

    // use the same code used in the showInfoCountry method to get the correct input of the start
    // and end
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

    // use BFS method in the Graph class to find the shortest path between the start and end country
    // and get the correct amount of tax needed to pay as well as all the continents being visited
    if (start.equals(end)) {
      MessageCli.NO_CROSSBORDER_TRAVEL.printMessage();
    } else {
      Integer taxSum = 0;
      List<String> continentsVisited = new ArrayList<>();
      List<String> route = new ArrayList<>();
      List<Country> countryRoute =
          riskMap.findShortestPath(
              Utils.getCountryByName(start, countrySet), Utils.getCountryByName(end, countrySet));
      for (Country country : countryRoute) {
        taxSum += Integer.parseInt(country.getTaxFee());
        route.add(country.getCountryName());
        if (!continentsVisited.contains(country.getContinent())) {
          continentsVisited.add(country.getContinent());
        }
      }
      taxSum -= Integer.parseInt(countryRoute.get(0).getTaxFee());
      String routeString = Utils.convertListToString(route);
      String continentsVisitedString = Utils.convertListToString(continentsVisited);

      MessageCli.ROUTE_INFO.printMessage(routeString);
      MessageCli.CONTINENT_INFO.printMessage(continentsVisitedString);
      MessageCli.TAX_INFO.printMessage(Integer.toString(taxSum));
    }
  }
}
