package nz.ac.auckland.se281;

/** This class represents a country with its name, continent and tax fee. */
public class Country {

  private String countryName;
  private String continent;
  private String taxFee;

  /** Constructor for the Country class. */
  public Country(String countryName, String continent, String taxFee) {
    this.countryName = countryName;
    this.continent = continent;
    this.taxFee = taxFee;
  }

  public String getCountryName() {
    return countryName;
  }

  public String getContinent() {
    return continent;
  }

  public String getTaxFee() {
    return taxFee;
  }

  @Override
  public String toString() {
    return MessageCli.COUNTRY_INFO.getMessage(countryName, continent, taxFee);
  }

  @Override
  public int hashCode() {
    int sum = 0;
    char[] countryNameChars = countryName.toCharArray();
    for (int i = 0; i < countryNameChars.length; i++) {
      sum += countryNameChars[i] * i;
    }

    return sum;
  }

  @Override
  public boolean equals(Object obj) {
    // compare the object and the classes as well as their country name since there are no different
    // countries with the same name

    if (this == obj) {
      return true;
    } else if (obj == null) {
      return false;
    } else if (getClass() != obj.getClass()) {
      return false;
    }

    Country other = (Country) obj;
    if (countryName == null) {
      if (other.countryName != null) {
        return false;
      }
    } else if (!countryName.equals(other.countryName)) {
      return false;
    }

    return true;
  }
}
