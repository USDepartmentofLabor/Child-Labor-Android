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
            stream = context.getAssets().open("countries_for_app.xml");
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
                            case "Advancement_Level":
                                currentCountry.setLevel(parser.nextText());
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
                            case "Master_Data":
                                parseMasterData(currentCountry.data, parser);
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
                            section = "Laws";
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
                            section = "Polices";
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

    private void parseMasterData(Country.MasterData data, XmlPullParser parser) throws XmlPullParserException, IOException {
        int eventType = parser.getEventType();
        while(!(eventType == XmlPullParser.END_TAG && parser.getName().equals("Master_Data"))) {
            String name = null;
            switch(eventType) {
                case XmlPullParser.START_TAG:
                    name = parser.getName();
                    switch(name) {
                        case "C_138_Ratified":
                            data.c138Ratified = parser.nextText().equals("Yes");
                            break;
                        case "C_182_Ratified":
                            data.c182Ratified = parser.nextText().equals("Yes");
                            break;
                        case "Convention_on_the_Rights_of_the_Child_Ratified":
                            data.crcRatified = parser.nextText().equals("Yes");
                            break;
                        case "CRC_Commercial_Sexual_Exploitation_of_Children_Ratified":
                            data.crcSexualExploitationRatified = parser.nextText().equals("Yes");
                            break;
                        case "CRC_Armed_Conflict_Ratified":
                            data.crcArmedConflictRatified = parser.nextText().equals("Yes");
                            break;
                        case "Palermo_Ratified":
                            data.palermoRatified = parser.nextText().equals("Yes");
                            break;
                        case "Minimum_Age_for_Work_Estabslished":
                            data.minimumWork = parser.nextText().equals("Yes");
                            break;
                        case "Minimum_Age_for_Work":
                            data.minimumWorkAge = parser.nextText();
                            break;
                        case "Minimum_Age_for_Hazardous_Work_Estabslished":
                            data.minimumHazardWork = parser.nextText().equals("Yes");
                            break;
                        case "Minimum_Age_for_Hazardous_Work":
                            data.minimumHazardWorkAge = parser.nextText();
                            break;
                        case "Compulsory_Education_Age_Established":
                            data.compulsoryEducation = parser.nextText().equals("Yes");
                            break;
                        case "Minimum_Age_for_Compulsory_Education":
                            data.compulsoryEducationAge = parser.nextText();
                            break;
                        case "Free_Public_Education_Estabslished":
                            data.freeEducation = parser.nextText().equals("Yes");
                            break;
                    }
                    break;
            }
            eventType = parser.next();
        }
    }

    private void parseCountryStatistics(Country.CountryStatistics statistics, XmlPullParser parser) throws XmlPullParserException, IOException {
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
