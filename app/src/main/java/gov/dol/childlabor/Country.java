package gov.dol.childlabor;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by trjohnson on 10/21/2015.
 */
public class Country implements Serializable {
    private String name, description, region, level;
    private CountryGood[] goods;
    private ArrayList<SuggestedAction> suggestedActions = new ArrayList<SuggestedAction>();
    public MasterData data;
    public CountryStatistics statistics;

    public Country(String name) {
        this.name = name;
        this.data = new MasterData();
        this.statistics = new CountryStatistics();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRegion() {
        if (region.equalsIgnoreCase("Asia & the Pacific")) {
            return "Asia & Pacific";
        }
        return region;
    }

    public void setRegion(String region) { this.region = region; }

    public String getLevel() {
        if (level.isEmpty()) {
            return "No Assessment Level Data";
        }
        else {
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
        for (CountryGood  good : goods) {
            if (good.hasChildLabor()) countryGoods.add(good);
        }
        return countryGoods.toArray(new CountryGood[countryGoods.size()]);
    }

    public CountryGood[] getForcedLaborGoods() {
        ArrayList<CountryGood> countryGoods = new ArrayList<CountryGood>();
        for (CountryGood  good : goods) {
            if (good.hasForcedLabor()) countryGoods.add(good);
        }
        return countryGoods.toArray(new CountryGood[countryGoods.size()]);
    }

    public CountryGood[] getForcedChildLaborGoods() {
        ArrayList<CountryGood> countryGoods = new ArrayList<CountryGood>();
        for (CountryGood  good : goods) {
            if (good.hasForcedChildLabor()) countryGoods.add(good);
        }
        return countryGoods.toArray(new CountryGood[countryGoods.size()]);
    }

    public SectionHeader getLevelHeader() {
        SectionHeader header;
        switch(getLevel()) {
            case "Significant Advancement":
                header = new SectionHeader(0, "Significant Advancement");
                break;
            case "Moderate Advancement":
                header = new SectionHeader(1, "Moderate Advancement");
                break;
            case "Minimal Advancement":
                header = new SectionHeader(2, "Minimal Advancement");
                break;
            case "No Advancement":
            case "No Advancement - Efforts Made But Complicit":
                header = new SectionHeader(3, "No Advancement");
                break;
            case "No Data":
            case "No Assessment Level Data":
            case "No Assessment":
            default:
                header = new SectionHeader(4, "No Data");
                break;
        }

        return header;
    }

    public String getRegionHeader() {
        return getRegion();
    }

    public void addSuggestedAction(String section, String[] actions) {
        suggestedActions.add(new SuggestedAction(section, actions));
    }

    public SuggestedAction[] getSuggestedActions() {
        return suggestedActions.toArray(new SuggestedAction[suggestedActions.size()]);
    }

    public Boolean hasMultipleTerritories() {
        return this.name.equals("Bosnia and Herzegovina");
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

    class MasterData implements Serializable {
        public Boolean minimumWork, minimumHazardWork, compulsoryEducation, freeEducation;
        public String minimumWorkAge, minimumHazardWorkAge, compulsoryEducationAge;
        public String c138Ratified, c182Ratified, crcRatified, crcArmedConflictRatified, crcSexualExploitationRatified, palermoRatified;
    }

    class CountryStatistics implements Serializable {
        public String workAgeRange, workPercent, workTotal, agriculturePercent, servicesPercent, industryPercent;
        public String educationAgeRange, educationPercent, workAndEducationAgeRange, workAndEducationPercent, primaryCompletionPercent;
    }

}
