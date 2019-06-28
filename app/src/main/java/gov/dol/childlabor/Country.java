package gov.dol.childlabor;

import java.io.Serializable;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Hashtable;

/**
 * Created by trjohnson on 10/21/2015.
 */
public class Country implements Serializable {
    private String name, url, description, region, level;
    private CountryGood[] goods;
    private ArrayList<SuggestedAction> suggestedActions = new ArrayList<SuggestedAction>();

    public Statistics statistics;
    public Boolean hasMultipleTerritories = false;
    public String automaticdowngrade = "";
    public Conventions conventions;
    public Hashtable<String, Standard> standards = new Hashtable<String, Standard>();
    public Hashtable<String, TerritoryStandard> territoryStandards = new Hashtable<String, TerritoryStandard>();
    public Hashtable<String, Enforcement> enforcements = new Hashtable<String, Enforcement>();
    public Hashtable<String, TerritoryEnforcement> territoryEnforcements = new Hashtable<String, TerritoryEnforcement>();
    public String coordinationMechanism, policyMechanism, programMechanism;
    // public Hashtable<String, TerritoryStatistics> TerritoryStatistics = new
    // Hashtable<String, TerritoryStatistics>();

    public Country(String name) {
        this.name = name;
        this.conventions = new Conventions();
        this.statistics = new Statistics();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getURL() {
        return url;
    }

    public void setURL(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRegion() {
        if (region != null && region.equalsIgnoreCase("Asia & Pacific")) {
            return "Asia & the Pacific";
        }
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getLevel() {
        if (level == null || level.isEmpty()) {
            return "Not Covered in TDA Report";
        } else {
            return level;
        }
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public CountryGood[] getGoods() {
        return goods;
    }

    public void setGoods(CountryGood[] goods) {
        this.goods = goods;
    }

    public CountryGood[] getChildLaborGoods() {
        ArrayList<CountryGood> countryGoods = new ArrayList<CountryGood>();
        for (CountryGood good : goods) {
            if (good.hasChildLabor())
                countryGoods.add(good);
        }
        return countryGoods.toArray(new CountryGood[countryGoods.size()]);
    }

    public CountryGood[] getForcedLaborGoods() {
        ArrayList<CountryGood> countryGoods = new ArrayList<CountryGood>();
        for (CountryGood good : goods) {
            if (good.hasForcedLabor())
                countryGoods.add(good);
        }
        return countryGoods.toArray(new CountryGood[countryGoods.size()]);
    }

    public CountryGood[] getForcedChildLaborGoods() {
        ArrayList<CountryGood> countryGoods = new ArrayList<CountryGood>();
        for (CountryGood good : goods) {
            if (good.hasForcedChildLabor())
                countryGoods.add(good);
        }
        return countryGoods.toArray(new CountryGood[countryGoods.size()]);
    }

    public SectionHeader getLevelHeader() {
        SectionHeader header;
        if (getLevel().startsWith("Significant Advancement")) {
            header = new SectionHeader(0, "Significant Advancement");
        } else if (getLevel().startsWith("Moderate Advancement")) {
            header = new SectionHeader(1, "Moderate Advancement");
        } else if (getLevel().startsWith("Minimal Advancement")) {
            header = new SectionHeader(2, "Minimal Advancement");
        } else if (getLevel().startsWith("No Advancement")) {
            header = new SectionHeader(3, "No Advancement");
        } else if (getLevel().startsWith("No Assessment")) {
            header = new SectionHeader(4, "No Assessment");
        } else {
            header = new SectionHeader(5, "Not Covered in TDA Report");
        }

        return header;
    }

    public String getRegionHeader() {
        return getRegion();
    }

    public void addSuggestedAction(String section, String[] actions) {
        suggestedActions.add(new SuggestedAction(section, actions));
    }

    public void addEnforcement(String type, String value) {
        enforcements.put(type, new Enforcement(type, value));
    }

    public void addTerritoryEnforcement(String type, ArrayList<Hashtable<String, String>> territories) {
        ArrayList<TerritoryValue> values = new ArrayList<>(); // Testing
        for (Hashtable<String, String> territory : territories) {
            TerritoryValue value = new TerritoryValue(territory.get("Territory_Name"),
                    territory.get("Territory_Display_Name"), territory.get("Enforcement"));
            values.add(value);
        }
        TerritoryEnforcement enforcement = new TerritoryEnforcement(type, values);
        territoryEnforcements.put(type, enforcement);
    }

    /*
     * public void addTerritoryStatistics(String type, ArrayList<Hashtable<String,
     * String>> territories) { ArrayList<TerritoryValue> values = new ArrayList<>();
     * // Testing for (Hashtable<String, String> territory : territories) {
     * TerritoryValue value = new TerritoryValue(territory.get("Territory_Name"),
     * territory.get("Territory_Display_Name"), territory.get("Enforcement"));
     * values.add(value); } TerritoryStatistics statistics = new
     * TerritoryStatistics(type, values); TerritoryStatistics.put(type, statistics);
     * }
     */

    public void addStandard(String type, Hashtable<String, String> standardHash) {
        Standard standard = new Standard(type, standardHash.get("Standard"), standardHash.get("Age"),
                standardHash.get("Calculated_Age"), standardHash.get("Conforms_To_Intl_Standard"));
        standards.put(type, standard);
    }

    public void addTerritoryStandard(String type, ArrayList<Hashtable<String, String>> territories) {
        ArrayList<TerritoryValue> values = new ArrayList<>();
        for (Hashtable<String, String> territory : territories) {
            TerritoryValue value = new TerritoryValue(territory.get("Territory_Name"),
                    territory.get("Territory_Display_Name"), territory.get("Standard"), territory.get("Age"),
                    territory.get("Calculated_Age"), territory.get("Conforms_To_Intl_Standard"));
            values.add(value);
        }
        TerritoryStandard standard = new TerritoryStandard(type, values);
        territoryStandards.put(type, standard);
    }

    public SuggestedAction[] getSuggestedActions() {
        return suggestedActions.toArray(new SuggestedAction[suggestedActions.size()]);
    }

    class SectionHeader implements Serializable {
        public int order;
        public String header;

        public SectionHeader(int order, String header) {
            this.order = order;
            this.header = header;
        }
    }

    class SuggestedAction implements Serializable {
        public String section;
        public String[] actions;
        public String[] laws, enforcement, coordination, policies, programs;

        public SuggestedAction(String section, String[] actions) {
            this.section = section;
            this.actions = actions;
        }
    }

    class Conventions implements Serializable {
        // public Boolean minimumWork, minimumHazardWork, compulsoryEducation,
        // freeEducation;
        // public String minimumWorkAge, minimumHazardWorkAge, compulsoryEducationAge;
        public String c138Ratified, c182Ratified, crcRatified, crcArmedConflictRatified, crcSexualExploitationRatified,
                palermoRatified;
    }

    class Statistics implements Serializable {
        public String workAgeRange, workPercent, workTotal, agriculturePercent, servicesPercent, industryPercent;
        public String educationAgeRange, educationPercent, workAndEducationAgeRange, workAndEducationPercent,
                primaryCompletionPercent;
    }

    class Standard implements Serializable {
        String type;
        String value;
        String age;
        String calculatedAge;
        String conformsStandard;

        public Standard(String type, String value, String age, String calculatedAge, String conformsStandard) {
            this.type = type;
            this.value = value;
            this.age = age;
            this.calculatedAge = calculatedAge;
            this.conformsStandard = conformsStandard;
        }
    }

    class TerritoryStandard implements Serializable {
        String type;
        ArrayList<TerritoryValue> territories = new ArrayList<TerritoryValue>();

        public TerritoryStandard(String type, ArrayList<TerritoryValue> territories) {
            this.type = type;
            this.territories = territories;
        }
    }

    class TerritoryValue implements Serializable {
        String territory;
        String displayName;
        String value;
        String age;
        String calculatedAge;
        String conformsStandard;

        public TerritoryValue(String territory, String displayName, String value, String age, String calculatedAge,
                String conformsStandard) {
            this.territory = territory;
            this.displayName = displayName;
            this.value = value;
            this.age = age;
            this.calculatedAge = calculatedAge;
            this.conformsStandard = conformsStandard;
        }

        public TerritoryValue(String territory, String displayName, String value) {
            this.territory = territory;
            this.displayName = displayName;
            this.value = value;
        }
    }

    class Enforcement implements Serializable {
        String type;
        String value;

        public Enforcement(String type, String value) {
            this.type = type;
            this.value = value;
        }
    }

    class TerritoryEnforcement implements Serializable {
        String type;
        ArrayList<TerritoryValue> territories = new ArrayList<TerritoryValue>();

        public TerritoryEnforcement(String type, ArrayList<TerritoryValue> territories) {
            this.type = type;
            this.territories = territories;
        }
    }

    /*
     * class TerritoryStatistics implements Serializable { String type;
     * ArrayList<TerritoryValue> territories = new ArrayList<TerritoryValue>();
     * 
     * public TerritoryStatistics(String type, ArrayList<TerritoryValue>
     * territories) { this.type = type; this.territories = territories; } }
     */

    /*
     * class TerritoryStatisticsValue implements Serializable { String territory;
     * String displayName; String value; String age; String calculatedAge; String
     * conformsStandard;
     * 
     * public TerritoryStatisticsValue(String territory, String displayName, String
     * value, String age, String calculatedAge, String conformsStandard) {
     * this.territory = territory; this.displayName = displayName; this.value =
     * value; this.age = age; this.calculatedAge = calculatedAge;
     * this.conformsStandard = conformsStandard; }
     * 
     * public TerritoryStatisticsValue(String territory, String displayName, String
     * value) { this.territory = territory; this.displayName = displayName;
     * this.value = value; } }
     */
}
