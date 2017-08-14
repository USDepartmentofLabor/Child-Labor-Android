package gov.dol.childlabor;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Arrays;

public class CountryViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_country_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Country country = (Country) getIntent().getSerializableExtra("country");
        setTitle(country.getName());

        TextView nameTextView = (TextView) findViewById(R.id.nameTextView);
        nameTextView.setText(country.getName());
        nameTextView.setContentDescription(country.getName() + ", heading");

        TextView levelTextView = (TextView) findViewById(R.id.levelTextView);
        levelTextView.setText(country.getLevel());
        levelTextView.setContentDescription(country.getLevel() + ", heading");

        TextView descriptionTextView = (TextView) findViewById(R.id.descriptionTextView);
        descriptionTextView.setText(country.getDescription());
        descriptionTextView.post(new Runnable() {
            @Override
            public void run() {
                TextView descriptionTextView = (TextView) findViewById(R.id.descriptionTextView);
                if (descriptionTextView.getLineCount() > 5) {
                    descriptionTextView.setMaxLines(5);
                }
                else {
                    TextView expandTextView = (TextView) findViewById(R.id.descriptionExpandTextView);
                    expandTextView.setVisibility(View.GONE);
                }
            }
        });

        TextView goodLabelTextView = (TextView) findViewById(R.id.goodLabelTextView);
        if (country.getGoods().length == 0) {
            goodLabelTextView.setVisibility(View.GONE);
        }
        else if (country.getGoods().length == 1) {
            goodLabelTextView.setText(country.getGoods().length + " GOOD PRODUCED WITH EXPLOITIVE LABOR");
        }
        else {
            goodLabelTextView.setText(country.getGoods().length + " GOODS PRODUCED WITH EXPLOITIVE LABOR");
        }

        ImageView mapImageView = (ImageView) findViewById(R.id.mapImageView);
        mapImageView.setImageDrawable(AppHelpers.getMapDrawable(this, country.getName()));

        LinearLayout goodLinearLayout = (LinearLayout) findViewById(R.id.goodsLinearLayout);
        LayoutInflater theInflater = LayoutInflater.from(this);
        for (CountryGood good : country.getGoods()) {
            View countryGoodWidget = theInflater.inflate(R.layout.country_good_widget, goodLinearLayout, false);
            String contentDescription = good.getGoodName();

            countryGoodWidget.setTag(good);

            ImageView countryGoodImageView = (ImageView) countryGoodWidget.findViewById(R.id.countryGoodImageView);
            countryGoodImageView.setImageDrawable(AppHelpers.getGoodDrawable(this, good.getGoodName()));

            TextView countryGoodTextView = (TextView) countryGoodWidget.findViewById(R.id.countryGoodTextView);
            countryGoodTextView.setText(good.getGoodName());

            LinearLayout row;
            if(good.hasForcedChildLabor()) {
                row = (LinearLayout) countryGoodWidget.findViewById(R.id.forceChildLaborLinearLayout);
                row.setVisibility(View.VISIBLE);
                contentDescription += ", Forced Child Labor";
            }
            else if(good.hasChildLabor()) {
                row = (LinearLayout) countryGoodWidget.findViewById(R.id.childLaborLinearLayout);
                row.setVisibility(View.VISIBLE);
                contentDescription += ", Child Labor";
            }
            else {
                row = (LinearLayout) countryGoodWidget.findViewById(R.id.childLaborLinearLayout);
                row.setVisibility(View.INVISIBLE);
            }

            if(good.hasForcedLabor()) {
                row = (LinearLayout) countryGoodWidget.findViewById(R.id.forcedLaborLinearLayout);
                row.setVisibility(View.VISIBLE);
                contentDescription += ", Forced Labor";
            }

            countryGoodWidget.setContentDescription(contentDescription + ", button");
            goodLinearLayout.addView(countryGoodWidget);
        }


        String[] items = {"Suggested Actions", "Statistics", "International Conventions", "Legal Standards", "Enforcement", "Coordinating Mechanisms", "Report PDF"};
        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);

        ListView listView = (ListView) findViewById(R.id.actionListView);
        listView.setAdapter(itemsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;
                switch (String.valueOf(parent.getItemAtPosition(position))) {
                    case "Statistics":
                        intent = new Intent(getApplicationContext(), StatisticsActivity.class);
                        break;
                    case "International Conventions":
                        intent = new Intent(getApplicationContext(), ConventionActivity.class);
                        break;
                    case "Legal Standards":
                        String [] countries = {"Philippines", "Bosnia and Herzegovina"};
                        if (Arrays.asList(countries).contains(country.getName())) {
                            intent = new Intent(getApplicationContext(), BetaLegalStandardActivity.class);

                        }else {
                            intent = new Intent(getApplicationContext(), LegalStandardActivity.class);
                        }
                        intent = new Intent(getApplicationContext(), BetaLegalStandardActivity.class);
                        break;
                    case "Enforcement":
                        intent = new Intent(getApplicationContext(), TabbedEnforcementActivity.class);
                        break;
                    case "Coordinating Mechanisms":
                        intent = new Intent(getApplicationContext(), MechanismActivity.class);
                        break;
                    case "Report PDF":
                        intent = new Intent(getApplicationContext(), FullReportActivity.class);
                        break;
                    case "Suggested Actions":
                    default:
                        intent = new Intent(getApplicationContext(), SuggestedActionsActivity.class);
                }

                intent.putExtra("country", country);
                startActivity(intent);
            }
        });

        String[] excludedCountries = {"Burma", "China", "Iran", "Malaysia", "Mexico", "North Korea", "Tajikistan",
                "Turkmenistan", "Vietnam", "British Indian Ocean Territories", "Heard and McDonald Islands", "Pitcairn Islands"};

        if (Arrays.asList(excludedCountries).contains(country.getName())) {
            listView.setVisibility(View.GONE);
        }

        if (country.getLevel().equals("Not Covered in TDA Report")) {
            listView.setVisibility(View.GONE);
            mapImageView.setVisibility(View.GONE);
        }

        AppHelpers.trackScreenView((AnalyticsApplication) getApplication(), "Country Profile Screen");
        AppHelpers.trackCountryEvent((AnalyticsApplication) getApplication(), "Viewed", country.getName());
    }

    public void selectGoodWidget(View v) {
        CountryGood countryGood = (CountryGood) v.getTag();

        Good selectedGood = new Good("Good");

        GoodXmlParser parser = GoodXmlParser.fromContext(this);
        Good[] goods = parser.getGoodList();
        for(Good good : goods) {
            if (good.getName().equals(countryGood.getGoodName())) {
                selectedGood = good;
            }
        }

        Intent intent = new Intent(this, GoodViewActivity.class);
        intent.putExtra("good", selectedGood);
        startActivity(intent);
    }

    public void expandDescription(View view) {
        TextView descriptionTextView = (TextView) findViewById(R.id.descriptionTextView);
        descriptionTextView.setEllipsize(null);
        descriptionTextView.setMaxLines(Integer.MAX_VALUE);

        TextView expandTextView = (TextView) findViewById(R.id.descriptionExpandTextView);
        expandTextView.setVisibility(View.GONE);
    }
}
