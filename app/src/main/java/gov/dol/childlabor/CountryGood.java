package gov.dol.childlabor;

import java.io.Serializable;

/**
 * Created by trjohnson on 10/21/2015.
 */
public class CountryGood implements Serializable {

    private String goodName, countryName,countryRegion,derivedCountry;
    private Boolean hasChildLabor, hasForcedLabor, hasForcedChildLabor,hasDerivedLaborExploitation;

    public CountryGood() {
        hasChildLabor = hasForcedLabor = hasForcedChildLabor = hasDerivedLaborExploitation = false;
    }

    public String getDerivedCountry() {
        return derivedCountry;
    }

    public void setDerivedCountry(String derivedCountry) {
        this.derivedCountry = derivedCountry;
    }

    public Boolean getHasDerivedLaborExploitation() {
        return hasDerivedLaborExploitation;
    }

    public void setHasDerivedLaborExploitation(Boolean hasDerivedLaborExploitation) {
        this.hasDerivedLaborExploitation = hasDerivedLaborExploitation;
    }

    public String getCountryName() {
        return countryName;
    }

    public void setCountryName(String name) {
        this.countryName = name;
    }

    public String getGoodName() {
        return goodName;
    }

    public void setGoodName(String name) {
        this.goodName = name;
    }

    public Boolean hasChildLabor() {
        return hasChildLabor;
    }

    public void setChildLabor(Boolean hasChildLabor) {
        this.hasChildLabor = hasChildLabor;
    }

    public Boolean hasForcedLabor() {
        return hasForcedLabor;
    }

    public void setForcedLabor(Boolean hasForcedLabor) {
        this.hasForcedLabor = hasForcedLabor;
    }

    public Boolean hasForcedChildLabor() {
        return hasForcedChildLabor;
    }

    public void setForcedChildLabor(Boolean hasForcedChildLabor) {
        this.hasForcedChildLabor = hasForcedChildLabor;
    }

    public String getCountryRegion() {
        return countryRegion;
    }

    public void setCountryRegion(String countryRegion) {
        this.countryRegion = countryRegion;
    }
}
