package gov.dol.childlabor;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.accessibility.AccessibilityEvent;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;

public class GoodViewActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_good_view);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        final Good good = (Good) getIntent().getSerializableExtra("good");
        setTitle(good.getName());

        TextView nameTextView = (TextView) findViewById(R.id.nameTextView);
        nameTextView.setText(good.getName());
        nameTextView.setContentDescription(good.getName() + ", heading");

        TextView levelTextView = (TextView) findViewById(R.id.sectorTextView);
        levelTextView.setText(good.getSector());
        levelTextView.setContentDescription(good.getSector() + ", heading");

        ImageView goodImageView = (ImageView) findViewById(R.id.goodImageView);
        goodImageView.setImageDrawable(AppHelpers.getGoodDrawable(this, good.getName()));

        Spinner spinner = (Spinner) findViewById(R.id.countryExploitationSpinner);
        String[] items = {"All", "Child Labor", "Forced Labor", "Forced Child Labor"};
        spinner.setAdapter(new ArrayAdapter<String>(this, R.layout.good_view_exploitation_spinner_row, R.id.exploitationSpinnerTextView, items) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                LayoutInflater theInflater = LayoutInflater.from(getContext());
                View theView = theInflater.inflate(R.layout.good_view_exploitation_spinner_row, parent, false);

                TextView spinnerTextView = (TextView) theView.findViewById(R.id.exploitationSpinnerTextView);
                spinnerTextView.setText(getItem(position));

                ImageView childLaborImageView = (ImageView) theView.findViewById(R.id.exploitationSpinnerChildLaborImageView);
                ImageView forcedLaborImageView = (ImageView) theView.findViewById(R.id.exploitationSpinnerForcedLaborImageView);
                ImageView forcedChildLaborImageView = (ImageView) theView.findViewById(R.id.exploitationSpinnerForcedChildLaborImageView);

                RelativeLayout.LayoutParams params;
                switch(getItem(position)) {
                    case "Child Labor":
                        params = (RelativeLayout.LayoutParams) childLaborImageView.getLayoutParams();
                        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                        forcedLaborImageView.setVisibility(View.GONE);
                        forcedChildLaborImageView.setVisibility(View.GONE);
                        break;
                    case "Forced Labor":
                        params = (RelativeLayout.LayoutParams) forcedLaborImageView.getLayoutParams();
                        params.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
                        childLaborImageView.setVisibility(View.GONE);
                        forcedChildLaborImageView.setVisibility(View.GONE);
                        break;
                    case "Forced Child Labor":
                        childLaborImageView.setVisibility(View.GONE);
                        forcedLaborImageView.setVisibility(View.GONE);
                }
                return theView;
            }

            @Override
            public View getDropDownView(int position, View convertView, ViewGroup parent) {
                return getView(position, convertView, parent);
            }
        });

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ArrayList<CountryGood> countryGoods = new ArrayList<CountryGood>();

                for (CountryGood countryGood : good.getCountries()) {
                    if (position == 0) {
                        countryGoods.add(countryGood);
                    } else if (position == 1 && countryGood.hasChildLabor()) {
                        countryGoods.add(countryGood);
                    } else if (position == 2 && countryGood.hasForcedLabor()) {
                        countryGoods.add(countryGood);
                    } else if (position == 3 && countryGood.hasForcedChildLabor()) {
                        countryGoods.add(countryGood);
                    }
                }

                String exploitationType;
                switch (position) {
                    case 1:
                        exploitationType = "CHILD LABOR";
                        break;
                    case 2:
                        exploitationType = "FORCED LABOR";
                        break;
                    case 3:
                        exploitationType = "FORCED CHILD LABOR";
                        break;
                    default:
                        exploitationType = "EXPLOITIVE LABOR";
                }

                TextView countryLabelTextView = (TextView) findViewById(R.id.countryLabelTextView);
                if (countryGoods.size() == 1) {
                    countryLabelTextView.setText("PRODUCED WITH " + exploitationType + " IN 1 COUNTRY");
                } else {
                    countryLabelTextView.setText("PRODUCED WITH " + exploitationType + " IN " + countryGoods.size() + " COUNTRIES");
                }

                addCountries(countryGoods.toArray(new CountryGood[countryGoods.size()]));

                if  (Build.VERSION.SDK_INT >= 16){
                    countryLabelTextView.requestFocus();
                    countryLabelTextView.announceForAccessibility(countryLabelTextView.getText());
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }

        });

        AppHelpers.trackScreenView((AnalyticsApplication) getApplication(), "Good Profile Screen");
        AppHelpers.trackGoodEvent((AnalyticsApplication) getApplication(), "Viewed", good.getName());
    }

    public void addCountries(CountryGood[] goods) {
        LinearLayout goodLinearLayout = (LinearLayout) findViewById(R.id.goodCountryLinearLayout);
        goodLinearLayout.removeAllViews();

        LayoutInflater theInflater = LayoutInflater.from(this);
        for (CountryGood country : goods) {
            View countryListWidget = theInflater.inflate(R.layout.good_country_widget, goodLinearLayout, false);
            String contentDescription = country.getCountryName();
            countryListWidget.setTag(country);

            ImageView countryFlagImageView = (ImageView) countryListWidget.findViewById(R.id.countryFlagImageView);
            Drawable flag = AppHelpers.getFlagDrawable(this, country.getCountryName());
            if (flag != null) {
                countryFlagImageView.setImageDrawable(flag);
            }
            else {
                countryFlagImageView.setVisibility(View.GONE);
            }

            TextView countryGoodTextView = (TextView) countryListWidget.findViewById(R.id.countryTextView);
            countryGoodTextView.setText(country.getCountryName());

            LinearLayout row;
            if(country.hasForcedChildLabor()) {
                row = (LinearLayout) countryListWidget.findViewById(R.id.forceChildLaborLinearLayout);
                row.setVisibility(View.VISIBLE);
                contentDescription += ", Forced Child Labor";
            }
            else if(country.hasChildLabor()) {
                row = (LinearLayout) countryListWidget.findViewById(R.id.childLaborLinearLayout);
                row.setVisibility(View.VISIBLE);
                contentDescription += ", Child Labor";
            }
            else {
                row = (LinearLayout) countryListWidget.findViewById(R.id.childLaborLinearLayout);
                row.setVisibility(View.INVISIBLE);
            }

            if(country.hasForcedLabor()) {
                row = (LinearLayout) countryListWidget.findViewById(R.id.forcedLaborLinearLayout);
                row.setVisibility(View.VISIBLE);
                contentDescription += ", Forced Labor";
            }

            countryListWidget.setContentDescription(contentDescription + ", button");
            goodLinearLayout.addView(countryListWidget);
        }
    }

    public void selectCountryWidget(View v) {
        CountryGood countryGood = (CountryGood) v.getTag();
        Country selectedCountry = new Country("Country");

        CountryXmlParser parser = CountryXmlParser.fromContext(this);
        Country[] countries = parser.getCountryList();
        for(Country country : countries) {
            if (country.getName().equals(countryGood.getCountryName())) {
                selectedCountry = country;
            }
        }

        Intent intent = new Intent(this, CountryViewActivity.class);
        intent.putExtra("country", selectedCountry);
        startActivity(intent);
    }

}
