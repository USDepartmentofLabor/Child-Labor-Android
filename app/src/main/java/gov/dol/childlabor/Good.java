package gov.dol.childlabor;

import java.io.Serializable;

/**
 * Created by trjohnson on 10/21/2015.
 */
public class Good implements Serializable{
    private String name, sector;
    private CountryGood[] countries;

    public Good(String name) {
        this.name = name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setSector(String sector) {
        this.sector = sector;
    }

    public String getSector() {
        return sector;
    }

    public CountryGood[] getCountries() {
        return countries;
    }

    public void setCountries(CountryGood[] countries) {
        this.countries = countries;
    }

    public String getSectorHeader() {
        switch(getSector()) {
            case "Agriculture/Forestry/Fishing":
                break;
            case "Manufacturing":
                break;
            case "Mining/Quarrying":
                break;
            case "Other":
                break;
        }

        return getSector();
    }

}
