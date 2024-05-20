package nz.ac.auckland.se281;

public class Country {

  private String country;
  private String continent;
  private String taxFee;

  public Country(String country, String continent, String taxFee) {
    this.country = country;
    this.continent = continent;
    this.taxFee = taxFee;
  }

  @Override
  public String toString() {
    return MessageCli.COUNTRY_INFO.getMessage(country, continent, taxFee);
  }

  @Override
  public int hashCode() {
    int sum = 0;
    for (Character c : country.toCharArray()) {
      sum += c * country.indexOf(c);
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
    if (country == null) {
      if (other.country != null) {
        return false;
      }
    } else if (!country.equals(other.country)) {
      return false;
    }

    return true;
  }
}
