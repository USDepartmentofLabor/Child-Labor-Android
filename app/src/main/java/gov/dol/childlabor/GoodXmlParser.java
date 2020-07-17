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
public class GoodXmlParser {

    private InputStream stream;

    public GoodXmlParser(InputStream stream) {
        this.stream = stream;
    }

    public static GoodXmlParser fromContext(Context context) {
        InputStream stream = null;
        try {
            stream = context.getAssets().open("goods_2019.xml");
        }
        catch(IOException e) {
            e.printStackTrace();
        }
        return new GoodXmlParser(stream);
    }

    public Good[] getGoodList() {
        Good[] goods = null;
        try {
            XmlPullParserFactory pullParserFactory;
            pullParserFactory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = pullParserFactory.newPullParser();

            parser.setFeature(XmlPullParser.FEATURE_PROCESS_NAMESPACES, false);
            parser.setInput(stream, null);
            goods = parseXML(parser);
        }
        catch(XmlPullParserException e) {
            e.printStackTrace();
        }
        catch(IOException e) {
            e.printStackTrace();
        }

        return goods;
    }

    public Good[] getGoodListBySector() {
        ArrayList<Good> goods = new ArrayList<Good>(Arrays.asList(getGoodList()));

        Collections.sort(goods, new Comparator<Good>() {
            public int compare(Good g1, Good g2) {
                return g1.getSector().compareToIgnoreCase(g2.getSector());
            }
        });

        return goods.toArray(new Good[goods.size()]);
    }

    private Good[] parseXML(XmlPullParser parser) throws XmlPullParserException, IOException {
        ArrayList<Good> goods = null;
        int eventType = parser.getEventType();
        Good currentGood = null;

        while(eventType != XmlPullParser.END_DOCUMENT) {
            String name = null;
            switch(eventType) {
                case XmlPullParser.START_DOCUMENT:
                    goods = new ArrayList();
                    break;
                case XmlPullParser.START_TAG:
                    name = parser.getName();
                    if (name.equals("Good")) {
                        currentGood = new Good("Good");
                    } else if (currentGood != null) {
                        switch(name) {
                            case "Good_Name":
                                currentGood.setName(parser.nextText());
                                break;
                            case "Good_Sector":
                                currentGood.setSector(parser.nextText());
                                break;
                            case "Countries":
                                currentGood.setCountries(parseCountries(parser, currentGood.getName()));
                                break;
                            default:
                                skip(parser);
                        }
                    }
                    break;
                case XmlPullParser.END_TAG:
                    name = parser.getName();
                    if (name.equals("Good") && currentGood != null && currentGood.getCountries().length > 0) {
                        goods.add(currentGood);
                    }
            }
            eventType = parser.next();
        }

        return goods.toArray(new Good[goods.size()]);

    }

    private CountryGood[] parseCountries(XmlPullParser parser, String goodName) throws XmlPullParserException, IOException {
        ArrayList<CountryGood> countryGoods = new ArrayList<CountryGood>();

        int eventType = parser.getEventType();
        CountryGood currentCountryGood = null;

        while(!(eventType == XmlPullParser.END_TAG && parser.getName().equals("Countries"))) {
            String name = null;
            switch(eventType) {
                case XmlPullParser.START_TAG:
                    name = parser.getName();
                    if (name.equals("Country")) {
                        currentCountryGood = new CountryGood();
                        currentCountryGood.setGoodName(goodName);
                    } else if (currentCountryGood != null) {
                        switch(name) {
                            case "Country_Name":
                                currentCountryGood.setCountryName(parser.nextText());
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
                    if (name.equals("Country") && currentCountryGood != null) {
                        countryGoods.add(currentCountryGood);
                    }
            }
            eventType = parser.next();
        }

        return countryGoods.toArray(new CountryGood[countryGoods.size()]);
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
