package gov.dol.childlabor;

import android.content.Context;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.Hashtable;

/**
 * Created by tru on 10/22/2015.
 */
public class CountryXmlParser {

    private InputStream stream;

    public CountryXmlParser(InputStream stream) {
        this.stream = stream;
    }

    public static CountryXmlParser fromContext(Context context) {
        InputStream stream = null;
        try {
            stream = context.getAssets().open("countries_2016.xml");
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        return new CountryXmlParser(stream);
    }

    public Country[] getCountryList() {
        Country[] countries = null;
        try {
            XmlPullParserFactory pullParserFactory;
            pullParserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = pullParserFactory.newPullParser();

            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(stream, null);
            countries = parseXML(parser);
        }
        catch(XmlPullParserException e) {
            e.printStackTrace();
        }
        catch(IOException e) {
            e.printStackTrace();
        }

        return countries;
    }

    public Country[] getCountryListByLevel() {
        ArrayList<Country> countries = new ArrayList<Country>(Arrays.asList(getCountryList()));

        Collections.sort(countries, new Comparator<Country>() {
            public int compare(Country c1, Country c2) {
                //return Integer.compare(c1.getLevelHeader().order, c2.getLevelHeader().order);
                return ((Integer) c1.getLevelHeader().order).compareTo(c2.getLevelHeader().order);
            }
        });

        return countries.toArray(new Country[countries.size()]);
    }

    public Country[] getCountryListByRegion() {
        ArrayList<Country> countries = new ArrayList<Country>(Arrays.asList(getCountryList()));

        Collections.sort(countries, new Comparator<Country>() {
            public int compare(Country c1, Country c2) {
                return c1.getRegion().compareToIgnoreCase(c2.getRegion());
            }
        });

        return countries.toArray(new Country[countries.size()]);
    }

    public Country[] getCountyListWithExploitation() {
        ArrayList<Country> countries = new ArrayList<Country>();

        for (Country country : getCountryList()) {
            if (country.getGoods().length > 0) {
                countries.add(country);
            }
        }

        return countries.toArray(new Country[countries.size()]);
    }

    private Country[] parseXML(XmlPullParser parser) throws XmlPullParserException, IOException {
        ArrayList<Country> countries = null;
        int eventType = parser.getEventType();
        Country currentCountry = null;

        while(eventType != XmlPullParser.END_DOCUMENT) {
            String name = null;
            switch(eventType) {
                case XmlPullParser.START_DOCUMENT:
                    countries = new ArrayList();
                    break;
                case XmlPullParser.START_TAG:
                    name = parser.getName();
                    if (name.equals("Country")) {
                        currentCountry = new Country("Country");
                    } else if (currentCountry != null) {
                        switch(name) {
                            case "Name":
                                currentCountry.setName(parser.nextText());
                                break;
                            case "Description":
                                currentCountry.setDescription(parser.nextText());
                                break;
                            case "Region":
                                currentCountry.setRegion(parser.nextText());
                                break;
                            case "Multiple_Territories":
                                currentCountry.hasMultipleTerritories = parser.nextText().equals("Yes");
                                break;

                            case "Advancement_Level":
                                if (currentCountry.automaticdowngrade != "") {
                                    currentCountry.setLevel(currentCountry.automaticdowngrade);
                                }
                                else {
                                    currentCountry.setLevel(parser.nextText());
                                }
                                break;
                            case "Automatic_Downgrade":
                                currentCountry.automaticdowngrade = parser.nextText();
                                if (currentCountry.automaticdowngrade != "") {
                                    currentCountry.setLevel(currentCountry.automaticdowngrade);
                                }
                                break;
                            case "Goods":
                                currentCountry.setGoods(parseGoods(parser, currentCountry.getName()));
                                break;
                            case "Suggested_Actions":
                                parseSuggestedActions(currentCountry, parser);
                                break;
                            case "Country_Statistics":
                                parseCountryStatistics(currentCountry.statistics, parser);
                                break;
                            case "Conventions":
                                parseConventions(currentCountry.conventions, parser);
                                break;
                            case "Legal_Standards":
                                if (!currentCountry.hasMultipleTerritories) {
                                    parseStandards(currentCountry, parser);
                                }
                                else {
                                    parseTerritoryStandards(currentCountry, parser);
                                }
                                break;
                            case "Enforcements":
                                if (!currentCountry.hasMultipleTerritories) {
                                    parseEnforcements(currentCountry, parser);
                                }
                                else {
                                    parseTerritoryEnforcements(currentCountry, parser);
                                }
                                break;
                            case "Mechanisms":
                                parseMechanisms(currentCountry, parser);
                                break;
                            default:
                                skip(parser);
                        }
                    }
                    break;
                case XmlPullParser.END_TAG:
                    name = parser.getName();
                    if (name.equals("Country") && currentCountry != null) {
                        countries.add(currentCountry);
                    }
            }
            eventType = parser.next();
        }

        return countries.toArray(new Country[countries.size()]);

    }

    private CountryGood[] parseGoods(XmlPullParser parser, String countryName) throws XmlPullParserException, IOException {
        ArrayList<CountryGood> countryGoods = new ArrayList<CountryGood>();

        int eventType = parser.getEventType();
        CountryGood currentCountryGood = null;

        while(!(eventType == XmlPullParser.END_TAG && parser.getName().equals("Goods"))) {
            String name = null;
            switch(eventType) {
                case XmlPullParser.START_TAG:
                    name = parser.getName();
                    if (name.equals("Good")) {
                        currentCountryGood = new CountryGood();
                        currentCountryGood.setCountryName(countryName);
                    } else if (currentCountryGood != null) {
                        switch(name) {
                            case "Good_Name":
                                currentCountryGood.setGoodName(parser.nextText());
                                break;
                            case "Child_Labor":
                                currentCountryGood.setChildLabor(parser.nextText().equals("Yes"));
                                break;
                            case "Forced_Labor":
                                currentCountryGood.setForcedLabor(parser.nextText().equals("Yes"));
                                break;
                            case "Forced_Child_Labor":
                                currentCountryGood.setForcedChildLabor(parser.nextText().equals("Yes"));
                                break;
                            default:
                                skip(parser);
                        }
                    }
                    break;
                case XmlPullParser.END_TAG:
                    name = parser.getName();
                    if (name.equals("Good") && currentCountryGood != null) {
                        countryGoods.add(currentCountryGood);
                    }
            }
            eventType = parser.next();
        }

        return countryGoods.toArray(new CountryGood[countryGoods.size()]);
    }

    private void parseSuggestedActions(Country country, XmlPullParser parser) throws XmlPullParserException, IOException {
        String section = null;
        ArrayList<String> actions = new ArrayList<String>();

        int eventType = parser.getEventType();
        while(!(eventType == XmlPullParser.END_TAG && parser.getName().equals("Suggested_Actions"))) {
            String name = null;
            switch(eventType) {
                case XmlPullParser.START_TAG:
                    name = parser.getName();
                    switch(name) {
                        case "Name":
                            actions.add(parser.nextText());
                            break;
                        case "Legal_Framework":
                        case "Laws":
                            section = "Legal Standards";
                            actions = new ArrayList<>();
                            break;
                        case "Enforcement":
                            section = "Enforcement";
                            actions = new ArrayList<>();
                            break;
                        case "Coordination":
                            section = "Coordination";
                            actions = new ArrayList<>();
                            break;
                        case "Government_Policies":
                        case "Policies":
                            section = "Government Policies";
                            actions = new ArrayList<>();
                            break;
                        case "Social_Programs":
                            section = "Social Programs";
                            actions = new ArrayList<>();
                    }
                    break;
                case XmlPullParser.END_TAG:
                    name = parser.getName();
                    switch(name) {
                        case "Legal_Framework":
                        case "Laws":
                        case "Enforcement":
                        case "Coordination":
                        case "Government_Policies":
                        case "Policies":
                        case "Social_Programs":
                            country.addSuggestedAction(section, actions.toArray(new String[actions.size()]));
                    }
                    break;
            }
            eventType = parser.next();
        }
    }

    private void parseConventions(Country.Conventions data, XmlPullParser parser) throws XmlPullParserException, IOException {
        int eventType = parser.getEventType();
        while(!(eventType == XmlPullParser.END_TAG && parser.getName().equals("Conventions"))) {
            String name = null;
            switch(eventType) {
                case XmlPullParser.START_TAG:
                    name = parser.getName();
                    switch(name) {
                        case "C_138_Ratified":
                            data.c138Ratified = parser.nextText();
                            break;
                        case "C_182_Ratified":
                            data.c182Ratified = parser.nextText();
                            break;
                        case "Convention_on_the_Rights_of_the_Child_Ratified":
                            data.crcRatified = parser.nextText();
                            break;
                        case "CRC_Commercial_Sexual_Exploitation_of_Children_Ratified":
                            data.crcSexualExploitationRatified = parser.nextText();
                            break;
                        case "CRC_Armed_Conflict_Ratified":
                            data.crcArmedConflictRatified = parser.nextText();
                            break;
                        case "Palermo_Ratified":
                            data.palermoRatified = parser.nextText();
                            break;
                    }
                    break;
            }
            eventType = parser.next();
        }
    }

    private void parseStandards(Country country, XmlPullParser parser) throws XmlPullParserException, IOException {
        String type = null;
        Hashtable<String, String> standardHash = new Hashtable<String, String>();
        ArrayList<Hashtable<String, String>> territories = new ArrayList<>();

        int eventType = parser.getEventType();
        while(!(eventType == XmlPullParser.END_TAG && parser.getName().equals("Legal_Standards"))) {
            String name = null;
            switch(eventType) {
                case XmlPullParser.START_TAG:
                    name = parser.getName();
                    switch(name) {
                        case "Standard":
                        case "Age":
                        case "Calculated_Age":
                        case "Conforms_To_Intl_Standard":
                            standardHash.put(name, parser.nextText());
                            break;
                        default:
                            type = name;
                            standardHash.put("Type", name);
                            break;
                    }
                    break;
                case XmlPullParser.END_TAG:
                    name = parser.getName();
                    switch(name) {
                        case "Standard":
                        case "Age":
                        case "Calculated_Age":
                        case "Conforms_To_Intl_Standard":
                            break;
                        default:
                            country.addStandard(type, standardHash);
                            break;
                    }
                    break;
            }
            eventType = parser.next();
        }
    }

    private void parseTerritoryStandards(Country country, XmlPullParser parser) throws XmlPullParserException, IOException {
        String type = null;
        Hashtable<String, String> standardHash = new Hashtable<String, String>();
        ArrayList<Hashtable<String, String>> territories = new ArrayList<>();

        int eventType = parser.getEventType();
        while(!(eventType == XmlPullParser.END_TAG && parser.getName().equals("Legal_Standards"))) {
            String name = null;
            switch(eventType) {
                case XmlPullParser.START_TAG:
                    name = parser.getName();
                    switch(name) {
                        case "Standard":
                        case "Age":
                        case "Calculated_Age":
                        case "Conforms_To_Intl_Standard":
                        case "Territory_Name":
                        case "Territory_Display_Name":
                            standardHash.put(name, parser.nextText());
                            break;
                        case "Territory":
                            break;
                        default:
                            type = name;
                            standardHash.put("Type", name);
                            break;
                    }
                    break;
                case XmlPullParser.END_TAG:
                    name = parser.getName();
                    switch(name) {
                        case "Standard":
                        case "Age":
                        case "Calculated_Age":
                        case "Conforms_To_Intl_Standard":
                        case "Territory_Name":
                        case "Territory_Display_Name":
                            break;
                        case "Territory":
                            territories.add((Hashtable<String, String>) standardHash.clone());
                            standardHash = new Hashtable<String, String>();
                            break;
                        default:
                            country.addTerritoryStandard(type, territories);
                            territories.clear();
                            break;
                    }
                    break;
            }
            eventType = parser.next();
        }
    }

    private void parseEnforcements(Country country, XmlPullParser parser) throws XmlPullParserException, IOException {
        int eventType = parser.getEventType();
        while(!(eventType == XmlPullParser.END_TAG && parser.getName().equals("Enforcements"))) {
            String name = null;
            switch(eventType) {
                case XmlPullParser.START_TAG:
                    name = parser.getName();
                    switch(name) {
                        case "Enforcements":
                            break;
                        default:
                            country.addEnforcement(name, parser.nextText());
                            break;
                    }
                    break;
            }
            eventType = parser.next();
        }
    }

    private void parseTerritoryEnforcements(Country country, XmlPullParser parser) throws XmlPullParserException, IOException {
        String type = null;
        Hashtable<String, String> standardHash = new Hashtable<String, String>();
        ArrayList<Hashtable<String, String>> territories = new ArrayList<>();

        int eventType = parser.getEventType();
        while(!(eventType == XmlPullParser.END_TAG && parser.getName().equals("Enforcements"))) {
            String name = null;
            switch(eventType) {
                case XmlPullParser.START_TAG:
                    name = parser.getName();
                    switch(name) {
                        case "Enforcements":
                        case "Territory":
                            break;
                        case "Enforcement":
                        case "Territory_Name":
                        case "Territory_Display_Name":
                            standardHash.put(name, parser.nextText());
                            break;
                        default:
                            type = name;
                            standardHash.put("Type", name);
                            break;
                    }
                    break;
                case XmlPullParser.END_TAG:
                    name = parser.getName();
                    switch(name) {
                        case "Enforcement":
                        case "Territory_Name":
                        case "Territory_Display_Name":
                            break;
                        case "Territory":
                            territories.add((Hashtable<String, String>) standardHash.clone());
                            standardHash = new Hashtable<String, String>();
                            break;
                        default:
                            country.addTerritoryEnforcement(type, territories);
                            territories.clear();
                            break;
                    }
                    break;
            }
            eventType = parser.next();
        }
    }

    private void parseMechanisms(Country country, XmlPullParser parser) throws XmlPullParserException, IOException {
        int eventType = parser.getEventType();
        while(!(eventType == XmlPullParser.END_TAG && parser.getName().equals("Mechanisms"))) {
            String name = null;
            switch(eventType) {
                case XmlPullParser.START_TAG:
                    name = parser.getName();
                    switch(name) {
                        case "Coordination":
                            country.coordinationMechanism = parser.nextText();
                            break;
                        case "Policy":
                            country.policyMechanism = parser.nextText();
                            break;
                        case "Program":
                            country.programMechanism = parser.nextText();
                            break;
                    }
                    break;
            }
            eventType = parser.next();
        }
    }

    private void parseCountryStatistics(Country.Statistics statistics, XmlPullParser parser) throws XmlPullParserException, IOException {
        int eventType = parser.getEventType();
        String section = null;
        while(!(eventType == XmlPullParser.END_TAG && parser.getName().equals("Country_Statistics"))) {
            String name = null;
            switch(eventType) {
                case XmlPullParser.START_TAG:
                    name = parser.getName();
                    switch(name) {
                        case "Children_Work_Statistics":
                        case "Education_Statistics_Attendance_Statistics":
                        case "Children_Working_and_Studying_7-14_yrs_old":
                            section = name;
                            break;
                        case "Total_Percentage_of_Working_Children":
                            statistics.workPercent = parser.nextText();
                            break;
                        case "Total_Working_Population":
                            statistics.workTotal = parser.nextText();
                            break;
                        case "Agriculture":
                            statistics.agriculturePercent = parser.nextText();
                            break;
                        case "Services":
                            statistics.servicesPercent = parser.nextText();
                            break;
                        case "Industry":
                            statistics.industryPercent = parser.nextText();
                            break;
                        case "Percentage":
                            statistics.educationPercent = parser.nextText();
                            break;
                        case "Total":
                            statistics.workAndEducationPercent = parser.nextText();
                            break;
                        case "Rate":
                            statistics.primaryCompletionPercent = parser.nextText();
                            break;
                        case "Age_Range":
                            switch (section) {
                                case "Children_Work_Statistics":
                                    statistics.workAgeRange = parser.nextText();
                                    break;
                                case "Education_Statistics_Attendance_Statistics":
                                    statistics.educationAgeRange = parser.nextText();
                                    break;
                                case "Children_Working_and_Studying_7-14_yrs_old":
                                    statistics.workAndEducationAgeRange = parser.nextText();
                                    break;
                            }
                            break;
                    }
                    break;
            }
            eventType = parser.next();
        }
    }

    private void skip(XmlPullParser parser) throws XmlPullParserException, IOException {
        if (parser.getEventType() != XmlPullParser.START_TAG) {
            throw new IllegalStateException();
        }
        int depth = 1;
        while (depth != 0) {
            switch (parser.next()) {
                case XmlPullParser.END_TAG:
                    depth--;
                    break;
                case XmlPullParser.START_TAG:
                    depth++;
                    break;
            }
        }
    }
}
