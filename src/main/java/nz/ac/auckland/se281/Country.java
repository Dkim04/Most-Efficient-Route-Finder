package nz.ac.auckland.se281;

public class Country {

  private String countryName;
  private String continent;
  private String taxFee;

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
    for (Character c : countryName.toCharArray()) {
      sum += c * countryName.indexOf(c);
    }

    return sum;
  }

  @Override
  public boolean equals(Object obj) {
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
