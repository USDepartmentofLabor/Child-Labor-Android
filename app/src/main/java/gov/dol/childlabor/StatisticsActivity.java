package gov.dol.childlabor;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Locale;

import gov.dol.childlabor.charts.HalfPieChartActivity;
import gov.dol.childlabor.charts.PieChartActivity;

public class StatisticsActivity extends AppCompatActivity {
    String cname;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Country country = (Country) getIntent().getSerializableExtra("country");
        String[] MultiText = {"Somalia", "Pakistan","Tanzania"};
//        String[] MultiText = {"Pakistan","Tanzania"};


        setContentView(R.layout.activity_statistics);



        if (Arrays.asList(MultiText).contains(country.getName())) {
            setContentView(R.layout.activity_statistics_multi);
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


        Country.Statistics statistics = country.statistics;

        findViewById(R.id.open_analysis).setOnClickListener(view -> {
            Intent intent = new Intent(this, PieChartActivity.class);
            intent.putExtra("Country", country.getName());
            intent.putExtra("Agriculture", statistics.agriculturePercent);
            intent.putExtra("Services", statistics.servicesPercent);
            intent.putExtra("Industry", statistics.industryPercent);
            startActivity(intent);
        });


        if (Arrays.asList(MultiText).contains(country.getName())) {
            cname = country.getName();
            displayTerritoriesworking((LinearLayout) findViewById(R.id.workinglinearlayout));
            displayTerritoriesworkingAgriculture((LinearLayout) findViewById(R.id.agriculturelinearlayout));
            displayTerritoriesworkingIndustry((LinearLayout) findViewById(R.id.industrylinearlayout));
            displayTerritoriesworkingServices((LinearLayout) findViewById(R.id.serviceslinearlayout));
            displayTerritoriesattendingschool((LinearLayout) findViewById(R.id.attendingschoollinearlayout));
            displayTerritoriescombiningws((LinearLayout) findViewById(R.id.combiningwslinearlayout));
            displayTerritoriesprimary((LinearLayout) findViewById(R.id.primarylinearlayout));
        } else {
            setStatisticText(R.id.workTextView, statistics.workPercent, statistics.workAgeRange, statistics.workTotal);
            setStatisticText(R.id.agricultureTextView, statistics.agriculturePercent);
            setStatisticText(R.id.servicesTextView, statistics.servicesPercent);
            setStatisticText(R.id.industryTextView, statistics.industryPercent);
            setStatisticText(R.id.educationTextView, statistics.educationPercent, statistics.educationAgeRange);
            setStatisticText(R.id.workAndEducationTextView, statistics.workAndEducationPercent, statistics.workAndEducationAgeRange);
            setStatisticText(R.id.completionRateTextView, statistics.primaryCompletionPercent);
        }
        String[] noHelpText = {"Christmas Island", "Cocos (Keeling) Islands", "Falkland Islands (Islas Malvinas)",
                "Norfolk Island", "Saint Helena, Ascension, and Tristan da Cunha", "Tokelau", "Wallis and Futuna",
                "British Virgin Islands", "Cook Islands", "Montserrat", "Western Sahara"};
        if (Arrays.asList(noHelpText).contains(country.getName())) {
            TextView helpTextView = (TextView) findViewById(R.id.statisticsHelpTextView);
            helpTextView.setVisibility(View.GONE);
        }

        AppHelpers.trackScreenView((AnalyticsApplication) getApplication(), "Statistics Screen");
    }


    //For somalia multi territory
    private void displayTerritoriesworking(LinearLayout layout) {

       if (cname.contains("Somalia"))
       {
        LinearLayout territoryRow = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.territory_row, layout, false);

        TextView territoryNameTextView = (TextView) territoryRow.findViewById(R.id.territoryNameTextView);
        territoryNameTextView.setText("North East Zone");
        territoryNameTextView.setContentDescription("North East Zone");

        TextView territoryValueTextView = (TextView) territoryRow.findViewById(R.id.territoryValueTextView);
        setStatisticTextMultiworking(territoryValueTextView, ".095", "5-14", "Unavailable");

        layout.addView(territoryRow);

        LinearLayout territoryRow1 = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.territory_row, layout, false);
        TextView territoryNameTextView1 = (TextView) territoryRow1.findViewById(R.id.territoryNameTextView);
        territoryNameTextView1.setText("Somaliland");
        territoryNameTextView1.setContentDescription("Somaliland");

        TextView territoryValueTextView1 = (TextView) territoryRow1.findViewById(R.id.territoryValueTextView);
        setStatisticTextMultiworking(territoryValueTextView1, ".132", "10-14", "Unavailable");

        layout.addView(territoryRow1);

//        LinearLayout territoryRow2 = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.territory_row, layout, false);
//        TextView territoryNameTextView2 = (TextView) territoryRow2.findViewById(R.id.territoryNameTextView);
//        territoryNameTextView2.setText("Somaliland");
//        territoryNameTextView2.setContentDescription("Somaliland");
//
//        TextView territoryValueTextView2 = (TextView) territoryRow2.findViewById(R.id.territoryValueTextView);
//        setStatisticTextMultiworking(territoryValueTextView2, ".132", "5-14", "Unknown");
//
//        layout.addView(territoryRow2);
       }
        if (cname.contains("Tanzania"))
        {
            LinearLayout territoryRow = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.territory_row, layout, false);

            TextView territoryNameTextView = (TextView) territoryRow.findViewById(R.id.territoryNameTextView);
            territoryNameTextView.setText("Mainland");
            territoryNameTextView.setContentDescription("Mainland");

            TextView territoryValueTextView = (TextView) territoryRow.findViewById(R.id.territoryValueTextView);
            setStatisticTextMultiworking(territoryValueTextView, ".293", "5-14", "3573467");

            layout.addView(territoryRow);

            LinearLayout territoryRow1 = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.territory_row, layout, false);
            TextView territoryNameTextView1 = (TextView) territoryRow1.findViewById(R.id.territoryNameTextView);
            territoryNameTextView1.setText("Zanzibar");
            territoryNameTextView1.setContentDescription("Zanzibar");

            TextView territoryValueTextView1 = (TextView) territoryRow1.findViewById(R.id.territoryValueTextView);
            territoryValueTextView1.setTextColor(Color.BLACK);
            setStatisticTextMultiworking(territoryValueTextView1, "Unavailable", "Unavailable", "Unavailable");

            layout.addView(territoryRow1);

        }
        if (cname.contains("Pakistan"))
        {
            LinearLayout territoryRow = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.territory_row, layout, false);

            TextView territoryNameTextView = (TextView) territoryRow.findViewById(R.id.territoryNameTextView);
            territoryNameTextView.setText("Federal");
            territoryNameTextView.setContentDescription("Federal");

            TextView territoryValueTextView = (TextView) territoryRow.findViewById(R.id.territoryValueTextView);
            setStatisticTextMultiworking(territoryValueTextView, ".098", "10-14", "2261704");

            layout.addView(territoryRow);

            LinearLayout territoryRow1 = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.territory_row, layout, false);
            TextView territoryNameTextView1 = (TextView) territoryRow1.findViewById(R.id.territoryNameTextView);
            territoryNameTextView1.setText("Punjab");
            territoryNameTextView1.setContentDescription("Punjab");

            TextView territoryValueTextView1 = (TextView) territoryRow1.findViewById(R.id.territoryValueTextView);
            setStatisticTextMultiworking(territoryValueTextView1, ".124", "5-14", "Unavailable");

            layout.addView(territoryRow1);

//            LinearLayout territoryRow2 = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.territory_row, layout, false);
//            TextView territoryNameTextView2 = (TextView) territoryRow2.findViewById(R.id.territoryNameTextView);
//            territoryNameTextView2.setText("Khyber Pakhtunkhwa");
//            territoryNameTextView2.setContentDescription("Khyber Pakhtunkhwa");
//
//            TextView territoryValueTextView2 = (TextView) territoryRow2.findViewById(R.id.territoryValueTextView);
//            setStatisticTextMultiworking(territoryValueTextView2, "Unknown", "Unknown", "Unknown");
//
//            layout.addView(territoryRow2);

            LinearLayout territoryRow3 = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.territory_row, layout, false);
            TextView territoryNameTextView3 = (TextView) territoryRow3.findViewById(R.id.territoryNameTextView);
            territoryNameTextView3.setText("Sindh");
            territoryNameTextView3.setContentDescription("Sindh");

            TextView territoryValueTextView3 = (TextView) territoryRow3.findViewById(R.id.territoryValueTextView);
            setStatisticTextMultiworking(territoryValueTextView3, ".215", "5-14", "Unavailable");

            layout.addView(territoryRow3);

//            LinearLayout territoryRow4 = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.territory_row, layout, false);
//            TextView territoryNameTextView4 = (TextView) territoryRow4.findViewById(R.id.territoryNameTextView);
//            territoryNameTextView4.setText("Balochistan");
//            territoryNameTextView4.setContentDescription("Balochistan");
//
//            TextView territoryValueTextView4 = (TextView) territoryRow4.findViewById(R.id.territoryValueTextView);
//            setStatisticTextMultiworking(territoryValueTextView4, "UnKnown", "UnKnown", "UnKnown");
//
//            layout.addView(territoryRow4);
        }
    }

    private void displayTerritoriesworkingAgriculture(LinearLayout layout) {

        if (cname.contains("Somalia")) {
            LinearLayout territoryRow = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.territory_row, layout, false);

            TextView territoryNameTextView = (TextView) territoryRow.findViewById(R.id.territoryNameTextView);
            territoryNameTextView.setText("All Territories");
            territoryNameTextView.setContentDescription("All Territories");

            TextView territoryValueTextView = (TextView) territoryRow.findViewById(R.id.territoryValueTextView);
            setStatisticTextMulti(territoryValueTextView, "Unavailable");

            layout.addView(territoryRow);

//            LinearLayout territoryRow1 = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.territory_row, layout, false);
//            TextView territoryNameTextView1 = (TextView) territoryRow1.findViewById(R.id.territoryNameTextView);
//            territoryNameTextView1.setText("Puntland");
//            territoryNameTextView1.setContentDescription("Puntland");
//
//            TextView territoryValueTextView1 = (TextView) territoryRow1.findViewById(R.id.territoryValueTextView);
//            setStatisticTextMulti(territoryValueTextView1, "");
//
//            layout.addView(territoryRow1);
//
//            LinearLayout territoryRow2 = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.territory_row, layout, false);
//            TextView territoryNameTextView2 = (TextView) territoryRow2.findViewById(R.id.territoryNameTextView);
//            territoryNameTextView2.setText("Somaliland");
//            territoryNameTextView2.setContentDescription("Somaliland");
//
//            TextView territoryValueTextView2 = (TextView) territoryRow2.findViewById(R.id.territoryValueTextView);
//            setStatisticTextMulti(territoryValueTextView2, "");
//
//            layout.addView(territoryRow2);
        }

        if (cname.contains("Tanzania")) {
            LinearLayout territoryRow = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.territory_row, layout, false);

            TextView territoryNameTextView = (TextView) territoryRow.findViewById(R.id.territoryNameTextView);
            territoryNameTextView.setText("Mainland");
            territoryNameTextView.setContentDescription("Mainland");

            TextView territoryValueTextView = (TextView) territoryRow.findViewById(R.id.territoryValueTextView);
            setStatisticTextMulti(territoryValueTextView, ".941");

            layout.addView(territoryRow);

            LinearLayout territoryRow1 = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.territory_row, layout, false);
            TextView territoryNameTextView1 = (TextView) territoryRow1.findViewById(R.id.territoryNameTextView);
            territoryNameTextView1.setText("Zanzibar");
            territoryNameTextView1.setContentDescription("Zanzibar");

            TextView territoryValueTextView1 = (TextView) territoryRow1.findViewById(R.id.territoryValueTextView);
            territoryValueTextView1.setTextColor(Color.BLACK);
            setStatisticTextMulti(territoryValueTextView1, "Unavailable");

            layout.addView(territoryRow1);
        }

        if (cname.contains("Pakistan")) {
            LinearLayout territoryRow = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.territory_row, layout, false);

            TextView territoryNameTextView = (TextView) territoryRow.findViewById(R.id.territoryNameTextView);
            territoryNameTextView.setText("Federal");
            territoryNameTextView.setContentDescription("Federal");

            TextView territoryValueTextView = (TextView) territoryRow.findViewById(R.id.territoryValueTextView);
			territoryValueTextView.setTextColor(Color.BLACK);
            setStatisticTextMulti(territoryValueTextView, ".694");

            layout.addView(territoryRow);

            LinearLayout territoryRow1 = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.territory_row, layout, false);
            TextView territoryNameTextView1 = (TextView) territoryRow1.findViewById(R.id.territoryNameTextView);
            territoryNameTextView1.setText("Punjab");
            territoryNameTextView1.setContentDescription("Punjab");

            TextView territoryValueTextView1 = (TextView) territoryRow1.findViewById(R.id.territoryValueTextView);
			territoryValueTextView1.setTextColor(Color.BLACK);
            setStatisticTextMulti(territoryValueTextView1, "Unavailable");

            layout.addView(territoryRow1);

//            LinearLayout territoryRow2 = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.territory_row, layout, false);
//            TextView territoryNameTextView2 = (TextView) territoryRow2.findViewById(R.id.territoryNameTextView);
//            territoryNameTextView2.setText("Khyber Pakhtunkhwa");
//            territoryNameTextView2.setContentDescription("Khyber Pakhtunkhwa");
//
//            TextView territoryValueTextView2 = (TextView) territoryRow2.findViewById(R.id.territoryValueTextView);
//            setStatisticTextMulti(territoryValueTextView2, "");
//
//            layout.addView(territoryRow2);


            LinearLayout territoryRow3 = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.territory_row, layout, false);
            TextView territoryNameTextView3 = (TextView) territoryRow3.findViewById(R.id.territoryNameTextView);
            territoryNameTextView3.setText("Sindh");
            territoryNameTextView3.setContentDescription("Sindh");

            TextView territoryValueTextView3 = (TextView) territoryRow3.findViewById(R.id.territoryValueTextView);
			territoryValueTextView3.setTextColor(Color.BLACK);
            setStatisticTextMulti(territoryValueTextView3, "Unavailable");

            layout.addView(territoryRow3);

//            LinearLayout territoryRow4 = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.territory_row, layout, false);
//            TextView territoryNameTextView4 = (TextView) territoryRow4.findViewById(R.id.territoryNameTextView);
//            territoryNameTextView4.setText("Balochistan");
//            territoryNameTextView4.setContentDescription("Balochistan");
//
//            TextView territoryValueTextView4 = (TextView) territoryRow4.findViewById(R.id.territoryValueTextView);
//            setStatisticTextMulti(territoryValueTextView4, "");
//
//            layout.addView(territoryRow4);
        }
    }

    private void displayTerritoriesworkingIndustry(LinearLayout layout) {

        if(cname.contains("Somalia")) {
            LinearLayout territoryRow = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.territory_row, layout, false);

            TextView territoryNameTextView = (TextView) territoryRow.findViewById(R.id.territoryNameTextView);
            territoryNameTextView.setText("All Territories");
            territoryNameTextView.setContentDescription("All Territories");

            TextView territoryValueTextView = (TextView) territoryRow.findViewById(R.id.territoryValueTextView);
            setStatisticTextMulti(territoryValueTextView, "Unavailable");

            layout.addView(territoryRow);

//            LinearLayout territoryRow1 = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.territory_row, layout, false);
//            TextView territoryNameTextView1 = (TextView) territoryRow1.findViewById(R.id.territoryNameTextView);
//            territoryNameTextView1.setText("Puntland");
//            territoryNameTextView1.setContentDescription("Puntland");
//
//            TextView territoryValueTextView1 = (TextView) territoryRow1.findViewById(R.id.territoryValueTextView);
//            setStatisticTextMulti(territoryValueTextView1, "");
//
//            layout.addView(territoryRow1);
//
//            LinearLayout territoryRow2 = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.territory_row, layout, false);
//            TextView territoryNameTextView2 = (TextView) territoryRow2.findViewById(R.id.territoryNameTextView);
//            territoryNameTextView2.setText("Somaliland");
//            territoryNameTextView2.setContentDescription("Somaliland");
//
//            TextView territoryValueTextView2 = (TextView) territoryRow2.findViewById(R.id.territoryValueTextView);
//            setStatisticTextMulti(territoryValueTextView2, "");
//
//            layout.addView(territoryRow2);
        }

        if(cname.contains("Tanzania")) {
            LinearLayout territoryRow = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.territory_row, layout, false);

            TextView territoryNameTextView = (TextView) territoryRow.findViewById(R.id.territoryNameTextView);
            territoryNameTextView.setText("Mainland");
            territoryNameTextView.setContentDescription("Mainland");

            TextView territoryValueTextView = (TextView) territoryRow.findViewById(R.id.territoryValueTextView);
            setStatisticTextMulti(territoryValueTextView, ".01");

            layout.addView(territoryRow);

            LinearLayout territoryRow1 = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.territory_row, layout, false);
            TextView territoryNameTextView1 = (TextView) territoryRow1.findViewById(R.id.territoryNameTextView);
            territoryNameTextView1.setText("Zanzibar");
            territoryNameTextView1.setContentDescription("Zanzibar");

            TextView territoryValueTextView1 = (TextView) territoryRow1.findViewById(R.id.territoryValueTextView);
            territoryValueTextView1.setTextColor(Color.BLACK);
            setStatisticTextMulti(territoryValueTextView1, "Unavailable");

            layout.addView(territoryRow1);

        }

        if(cname.contains("Pakistan")) {
            LinearLayout territoryRow = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.territory_row, layout, false);

            TextView territoryNameTextView = (TextView) territoryRow.findViewById(R.id.territoryNameTextView);
            territoryNameTextView.setText("Federal");
            territoryNameTextView.setContentDescription("Federal");

            TextView territoryValueTextView = (TextView) territoryRow.findViewById(R.id.territoryValueTextView);
			territoryValueTextView.setTextColor(Color.BLACK);
            setStatisticTextMulti(territoryValueTextView, ".109");

            layout.addView(territoryRow);

            LinearLayout territoryRow1 = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.territory_row, layout, false);
            TextView territoryNameTextView1 = (TextView) territoryRow1.findViewById(R.id.territoryNameTextView);
            territoryNameTextView1.setText("Punjab");
            territoryNameTextView1.setContentDescription("Punjab");

            TextView territoryValueTextView1 = (TextView) territoryRow1.findViewById(R.id.territoryValueTextView);
			territoryValueTextView1.setTextColor(Color.BLACK);
            setStatisticTextMulti(territoryValueTextView1, "Unavailable");

            layout.addView(territoryRow1);

//            LinearLayout territoryRow2 = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.territory_row, layout, false);
//            TextView territoryNameTextView2 = (TextView) territoryRow2.findViewById(R.id.territoryNameTextView);
//            territoryNameTextView2.setText("Khyber Pakhtunkhwa");
//            territoryNameTextView2.setContentDescription("Khyber Pakhtunkhwa");
//
//            TextView territoryValueTextView2 = (TextView) territoryRow2.findViewById(R.id.territoryValueTextView);
//            setStatisticTextMulti(territoryValueTextView2, "");

//            layout.addView(territoryRow2);

            LinearLayout territoryRow3 = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.territory_row, layout, false);
            TextView territoryNameTextView3 = (TextView) territoryRow3.findViewById(R.id.territoryNameTextView);
            territoryNameTextView3.setText("Sindh");
            territoryNameTextView3.setContentDescription("Sindh");

            TextView territoryValueTextView3 = (TextView) territoryRow3.findViewById(R.id.territoryValueTextView);
            territoryValueTextView3.setTextColor(Color.BLACK);
            setStatisticTextMulti(territoryValueTextView3, "Unavailable");

            layout.addView(territoryRow3);


//            LinearLayout territoryRow4 = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.territory_row, layout, false);
//            TextView territoryNameTextView4 = (TextView) territoryRow4.findViewById(R.id.territoryNameTextView);
//            territoryNameTextView4.setText("Balochistan");
//            territoryNameTextView4.setContentDescription("Balochistan");
//
//            TextView territoryValueTextView4 = (TextView) territoryRow4.findViewById(R.id.territoryValueTextView);
//            setStatisticTextMulti(territoryValueTextView4, "");
//
//            layout.addView(territoryRow4);
        }
    }

    private void displayTerritoriesworkingServices(LinearLayout layout) {
        if (cname.contains("Somalia"))
        {
            LinearLayout territoryRow = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.territory_row, layout, false);

            TextView territoryNameTextView = (TextView) territoryRow.findViewById(R.id.territoryNameTextView);
            territoryNameTextView.setText("All Territories");
            territoryNameTextView.setContentDescription("All Territories");

            TextView territoryValueTextView = (TextView) territoryRow.findViewById(R.id.territoryValueTextView);
            territoryValueTextView.setTextColor(Color.BLACK);
            setStatisticTextMulti(territoryValueTextView, "Unavailable");

            layout.addView(territoryRow);

//            LinearLayout territoryRow1 = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.territory_row, layout, false);
//            TextView territoryNameTextView1 = (TextView) territoryRow1.findViewById(R.id.territoryNameTextView);
//            territoryNameTextView1.setText("Puntland");
//            territoryNameTextView1.setContentDescription("Puntland");
//
//            TextView territoryValueTextView1 = (TextView) territoryRow1.findViewById(R.id.territoryValueTextView);
//            territoryValueTextView1.setTextColor(Color.BLACK);
//            setStatisticTextMulti(territoryValueTextView1, "");
//
//            layout.addView(territoryRow1);
//
//            LinearLayout territoryRow2 = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.territory_row, layout, false);
//            TextView territoryNameTextView2 = (TextView) territoryRow2.findViewById(R.id.territoryNameTextView);
//            territoryNameTextView2.setText("Somaliland");
//            territoryNameTextView2.setContentDescription("Somaliland");
//
//            TextView territoryValueTextView2 = (TextView) territoryRow2.findViewById(R.id.territoryValueTextView);
//            territoryValueTextView2.setTextColor(Color.BLACK);
//            setStatisticTextMulti(territoryValueTextView2, "");
//
//            layout.addView(territoryRow2);
        }

        if (cname.contains("Tanzania"))
        {
            LinearLayout territoryRow = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.territory_row, layout, false);

            TextView territoryNameTextView = (TextView) territoryRow.findViewById(R.id.territoryNameTextView);
            territoryNameTextView.setText("Mainland");
            territoryNameTextView.setContentDescription("Mainland");

            TextView territoryValueTextView = (TextView) territoryRow.findViewById(R.id.territoryValueTextView);
            territoryValueTextView.setTextColor(Color.BLACK);
            setStatisticTextMulti(territoryValueTextView, ".49");

            layout.addView(territoryRow);

            LinearLayout territoryRow1 = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.territory_row, layout, false);
            TextView territoryNameTextView1 = (TextView) territoryRow1.findViewById(R.id.territoryNameTextView);
            territoryNameTextView1.setText("Zanzibar");
            territoryNameTextView1.setContentDescription("Zanzibar");

            TextView territoryValueTextView1 = (TextView) territoryRow1.findViewById(R.id.territoryValueTextView);
            territoryValueTextView1.setTextColor(Color.BLACK);
            setStatisticTextMulti(territoryValueTextView1, "Unavailable");

            layout.addView(territoryRow1);

        }

        if (cname.contains("Pakistan"))
        {
            LinearLayout territoryRow = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.territory_row, layout, false);

            TextView territoryNameTextView = (TextView) territoryRow.findViewById(R.id.territoryNameTextView);
            territoryNameTextView.setText("Federal");
            territoryNameTextView.setContentDescription("Federal");

            TextView territoryValueTextView = (TextView) territoryRow.findViewById(R.id.territoryValueTextView);
            territoryValueTextView.setTextColor(Color.BLACK);
            setStatisticTextMulti(territoryValueTextView, ".197");

            layout.addView(territoryRow);

            LinearLayout territoryRow1 = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.territory_row, layout, false);
            TextView territoryNameTextView1 = (TextView) territoryRow1.findViewById(R.id.territoryNameTextView);
            territoryNameTextView1.setText("Punjab");
            territoryNameTextView1.setContentDescription("Punjab");

            TextView territoryValueTextView1 = (TextView) territoryRow1.findViewById(R.id.territoryValueTextView);
            territoryValueTextView1.setTextColor(Color.BLACK);
            setStatisticTextMulti(territoryValueTextView1, "Unavailable");

            layout.addView(territoryRow1);

//            LinearLayout territoryRow2 = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.territory_row, layout, false);
//            TextView territoryNameTextView2 = (TextView) territoryRow2.findViewById(R.id.territoryNameTextView);
//            territoryNameTextView2.setText("Khyber Pakhtunkhwa");
//            territoryNameTextView2.setContentDescription("Khyber Pakhtunkhwa");
//
//            TextView territoryValueTextView2 = (TextView) territoryRow2.findViewById(R.id.territoryValueTextView);
//            setStatisticTextMulti(territoryValueTextView2, "");
//
//            layout.addView(territoryRow2);

            LinearLayout territoryRow3 = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.territory_row, layout, false);
            TextView territoryNameTextView3 = (TextView) territoryRow3.findViewById(R.id.territoryNameTextView);
            territoryNameTextView3.setText("Sindh");
            territoryNameTextView3.setContentDescription("Sindh");

            TextView territoryValueTextView3 = (TextView) territoryRow3.findViewById(R.id.territoryValueTextView);
            territoryValueTextView3.setTextColor(Color.BLACK);
            setStatisticTextMulti(territoryValueTextView3, "Unavailable");

            layout.addView(territoryRow3);

//            LinearLayout territoryRow4 = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.territory_row, layout, false);
//            TextView territoryNameTextView4 = (TextView) territoryRow4.findViewById(R.id.territoryNameTextView);
//            territoryNameTextView4.setText("Balochistan");
//            territoryNameTextView4.setContentDescription("Balochistan");
//
//            TextView territoryValueTextView4 = (TextView) territoryRow4.findViewById(R.id.territoryValueTextView);
//            setStatisticTextMulti(territoryValueTextView4, "");
//
//            layout.addView(territoryRow4);
        }
    }

    private void displayTerritoriesattendingschool(LinearLayout layout) {

        if (cname.contains("Somalia")) {
            LinearLayout territoryRow = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.territory_row, layout, false);

            TextView territoryNameTextView = (TextView) territoryRow.findViewById(R.id.territoryNameTextView);
            territoryNameTextView.setText("North East Zone");
            territoryNameTextView.setContentDescription("North East Zone");

            TextView territoryValueTextView = (TextView) territoryRow.findViewById(R.id.territoryValueTextView);
            territoryValueTextView.setTextColor(Color.BLACK);
            setStatisticTextMulti(territoryValueTextView, ".388", "5-14");

            layout.addView(territoryRow);

            LinearLayout territoryRow1 = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.territory_row, layout, false);
            TextView territoryNameTextView1 = (TextView) territoryRow1.findViewById(R.id.territoryNameTextView);
            territoryNameTextView1.setText("Somaliland");
            territoryNameTextView1.setContentDescription("Somaliland");

            TextView territoryValueTextView1 = (TextView) territoryRow1.findViewById(R.id.territoryValueTextView);
            territoryValueTextView1.setTextColor(Color.BLACK);
            setStatisticTextMulti(territoryValueTextView1, ".442", "5-14");

            layout.addView(territoryRow1);

//            LinearLayout territoryRow2 = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.territory_row, layout, false);
//            TextView territoryNameTextView2 = (TextView) territoryRow2.findViewById(R.id.territoryNameTextView);
//            territoryNameTextView2.setText("Somaliland");
//            territoryNameTextView2.setContentDescription("Somaliland");
//
//            TextView territoryValueTextView2 = (TextView) territoryRow2.findViewById(R.id.territoryValueTextView);
//            territoryValueTextView2.setTextColor(Color.BLACK);
//            setStatisticTextMulti(territoryValueTextView2, ".442", "5-14");
//
//            layout.addView(territoryRow2);
        }

        if (cname.contains("Tanzania")) {
            LinearLayout territoryRow = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.territory_row, layout, false);

            TextView territoryNameTextView = (TextView) territoryRow.findViewById(R.id.territoryNameTextView);
            territoryNameTextView.setText("Mainland");
            territoryNameTextView.setContentDescription("Mainland");

            TextView territoryValueTextView = (TextView) territoryRow.findViewById(R.id.territoryValueTextView);
            territoryValueTextView.setTextColor(Color.BLACK);
            setStatisticTextMulti(territoryValueTextView, ".743", "5-14");

            layout.addView(territoryRow);

            LinearLayout territoryRow1 = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.territory_row, layout, false);
            TextView territoryNameTextView1 = (TextView) territoryRow1.findViewById(R.id.territoryNameTextView);
            territoryNameTextView1.setText("Zanzibar");
            territoryNameTextView1.setContentDescription("Zanzibar");

            TextView territoryValueTextView1 = (TextView) territoryRow1.findViewById(R.id.territoryValueTextView);
            territoryValueTextView1.setTextColor(Color.BLACK);
            setStatisticTextMulti(territoryValueTextView1, "Unavailable", "Unavailable");

            layout.addView(territoryRow1);

        }

        if (cname.contains("Pakistan")) {
            LinearLayout territoryRow = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.territory_row, layout, false);

            TextView territoryNameTextView = (TextView) territoryRow.findViewById(R.id.territoryNameTextView);
            territoryNameTextView.setText("Federal");
            territoryNameTextView.setContentDescription("Federal");

            TextView territoryValueTextView = (TextView) territoryRow.findViewById(R.id.territoryValueTextView);
            territoryValueTextView.setTextColor(Color.BLACK);
            setStatisticTextMulti(territoryValueTextView, ".78", "10-14");

            layout.addView(territoryRow);

            LinearLayout territoryRow1 = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.territory_row, layout, false);
            TextView territoryNameTextView1 = (TextView) territoryRow1.findViewById(R.id.territoryNameTextView);
            territoryNameTextView1.setText("Punjab");
            territoryNameTextView1.setContentDescription("Punjab");

            TextView territoryValueTextView1 = (TextView) territoryRow1.findViewById(R.id.territoryValueTextView);
            territoryValueTextView1.setTextColor(Color.BLACK);
            setStatisticTextMulti(territoryValueTextView1, ".606", "5-14");

            layout.addView(territoryRow1);

//            LinearLayout territoryRow2 = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.territory_row, layout, false);
//            TextView territoryNameTextView2 = (TextView) territoryRow2.findViewById(R.id.territoryNameTextView);
//            territoryNameTextView2.setText("Khyber Pakhtunkhwa");
//            territoryNameTextView2.setContentDescription("Khyber Pakhtunkhwa");
//
//            TextView territoryValueTextView2 = (TextView) territoryRow2.findViewById(R.id.territoryValueTextView);
//            setStatisticTextMulti(territoryValueTextView2, "", "");
//
//            layout.addView(territoryRow2);

            LinearLayout territoryRow3 = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.territory_row, layout, false);
            TextView territoryNameTextView3 = (TextView) territoryRow3.findViewById(R.id.territoryNameTextView);
            territoryNameTextView3.setText("Sindh");
            territoryNameTextView3.setContentDescription("Sindh");

            TextView territoryValueTextView3 = (TextView) territoryRow3.findViewById(R.id.territoryValueTextView);
            territoryValueTextView3.setTextColor(Color.BLACK);
            setStatisticTextMulti(territoryValueTextView3, ".771", "5-14");

            layout.addView(territoryRow3);

 /*           LinearLayout territoryRow4 = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.territory_row, layout, false);
            TextView territoryNameTextView4 = (TextView) territoryRow4.findViewById(R.id.territoryNameTextView);
            territoryNameTextView4.setText("Balochistan");
            territoryNameTextView4.setContentDescription("Balochistan");

            TextView territoryValueTextView4 = (TextView) territoryRow4.findViewById(R.id.territoryValueTextView);
            territoryValueTextView4.setTextColor(Color.BLACK);
            setStatisticTextMulti(territoryValueTextView4, "", ""); 

            layout.addView(territoryRow4); */
        } 
    }

    private void displayTerritoriescombiningws(LinearLayout layout) {

        if (cname.contains("Somalia"))
        {
            LinearLayout territoryRow = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.territory_row, layout, false);

            TextView territoryNameTextView = (TextView) territoryRow.findViewById(R.id.territoryNameTextView);
            territoryNameTextView.setText("North East Zone");
            territoryNameTextView.setContentDescription("North East Zone");

            TextView territoryValueTextView = (TextView) territoryRow.findViewById(R.id.territoryValueTextView);
            territoryValueTextView.setTextColor(Color.BLACK);
            setStatisticTextMulti(territoryValueTextView, ".047", "7-14");

            layout.addView(territoryRow);

            LinearLayout territoryRow1 = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.territory_row, layout, false);
            TextView territoryNameTextView1 = (TextView) territoryRow1.findViewById(R.id.territoryNameTextView);
            territoryNameTextView1.setText("Somaliland");
            territoryNameTextView1.setContentDescription("Somaliland");

            TextView territoryValueTextView1 = (TextView) territoryRow1.findViewById(R.id.territoryValueTextView);
            territoryValueTextView1.setTextColor(Color.BLACK);
            setStatisticTextMulti(territoryValueTextView1, ".066", "7-14");

            layout.addView(territoryRow1);

//            LinearLayout territoryRow2 = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.territory_row, layout, false);
//            TextView territoryNameTextView2 = (TextView) territoryRow2.findViewById(R.id.territoryNameTextView);
//            territoryNameTextView2.setText("Somaliland");
//            territoryNameTextView2.setContentDescription("Somaliland");
//
//            TextView territoryValueTextView2 = (TextView) territoryRow2.findViewById(R.id.territoryValueTextView);
//            territoryValueTextView2.setTextColor(Color.BLACK);
//            setStatisticTextMulti(territoryValueTextView2, ".066", "7-14");
//
//
//            layout.addView(territoryRow2);
        }

        if (cname.contains("Tanzania"))
        {
            LinearLayout territoryRow = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.territory_row, layout, false);

            TextView territoryNameTextView = (TextView) territoryRow.findViewById(R.id.territoryNameTextView);
            territoryNameTextView.setText("Mainland");
            territoryNameTextView.setContentDescription("Mainland");

            TextView territoryValueTextView = (TextView) territoryRow.findViewById(R.id.territoryValueTextView);
            territoryValueTextView.setTextColor(Color.BLACK);
            setStatisticTextMulti(territoryValueTextView, ".246", "7-14");

            layout.addView(territoryRow);

            LinearLayout territoryRow1 = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.territory_row, layout, false);
            TextView territoryNameTextView1 = (TextView) territoryRow1.findViewById(R.id.territoryNameTextView);
            territoryNameTextView1.setText("Zanzibar");
            territoryNameTextView1.setContentDescription("Zanzibar");

            TextView territoryValueTextView1 = (TextView) territoryRow1.findViewById(R.id.territoryValueTextView);
            territoryValueTextView1.setTextColor(Color.BLACK);
            setStatisticTextMulti(territoryValueTextView1, "Unavailable", "Unavailable");

            layout.addView(territoryRow1);

        }

        if (cname.contains("Pakistan"))
        {
            LinearLayout territoryRow = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.territory_row, layout, false);

            TextView territoryNameTextView = (TextView) territoryRow.findViewById(R.id.territoryNameTextView);
            territoryNameTextView.setText("Federal");
            territoryNameTextView.setContentDescription("Federal");

            TextView territoryValueTextView = (TextView) territoryRow.findViewById(R.id.territoryValueTextView);
            territoryValueTextView.setTextColor(Color.BLACK);
            setStatisticTextMulti(territoryValueTextView, ".008", "10-14");

            layout.addView(territoryRow);

            LinearLayout territoryRow1 = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.territory_row, layout, false);
            TextView territoryNameTextView1 = (TextView) territoryRow1.findViewById(R.id.territoryNameTextView);
            territoryNameTextView1.setText("Punjab");
            territoryNameTextView1.setContentDescription("Punjab");

            TextView territoryValueTextView1 = (TextView) territoryRow1.findViewById(R.id.territoryValueTextView);
            territoryValueTextView1.setTextColor(Color.BLACK);
            setStatisticTextMulti(territoryValueTextView1, ".082", "7-14");

            layout.addView(territoryRow1);

//            LinearLayout territoryRow2 = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.territory_row, layout, false);
//            TextView territoryNameTextView2 = (TextView) territoryRow2.findViewById(R.id.territoryNameTextView);
//            territoryNameTextView2.setText("Khyber Pakhtunkhwa");
//            territoryNameTextView2.setContentDescription("Khyber Pakhtunkhwa");
//
//            TextView territoryValueTextView2 = (TextView) territoryRow2.findViewById(R.id.territoryValueTextView);
//            setStatisticTextMulti(territoryValueTextView2, "", "");
//
//            layout.addView(territoryRow2);

            LinearLayout territoryRow3 = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.territory_row, layout, false);
            TextView territoryNameTextView3 = (TextView) territoryRow3.findViewById(R.id.territoryNameTextView);
            territoryNameTextView3.setText("Sindh");
            territoryNameTextView3.setContentDescription("Sindh");

            TextView territoryValueTextView3 = (TextView) territoryRow3.findViewById(R.id.territoryValueTextView);
            territoryValueTextView3.setTextColor(Color.BLACK);
            setStatisticTextMulti(territoryValueTextView3, ".116", "7-14");

            layout.addView(territoryRow3);

 /*           LinearLayout territoryRow4 = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.territory_row, layout, false);
            TextView territoryNameTextView4 = (TextView) territoryRow4.findViewById(R.id.territoryNameTextView);
            territoryNameTextView4.setText("Balochistan");
            territoryNameTextView4.setContentDescription("Balochistan");

            TextView territoryValueTextView4 = (TextView) territoryRow4.findViewById(R.id.territoryValueTextView);
            territoryValueTextView4.setTextColor(Color.BLACK);
            setStatisticTextMulti(territoryValueTextView4, "", "");

            layout.addView(territoryRow4); */
        }
    }

    private void displayTerritoriesprimary(LinearLayout layout) {

        if (cname.contains("Somalia")) {
            LinearLayout territoryRow = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.territory_row, layout, false);

            TextView territoryNameTextView = (TextView) territoryRow.findViewById(R.id.territoryNameTextView);
            territoryNameTextView.setText("All Territories");
            territoryNameTextView.setContentDescription("All Territories");

            TextView territoryValueTextView = (TextView) territoryRow.findViewById(R.id.territoryValueTextView);
            territoryValueTextView.setTextColor(Color.BLACK);
            setStatisticTextMulti(territoryValueTextView, "Unavailable");

            layout.addView(territoryRow);

//            LinearLayout territoryRow1 = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.territory_row, layout, false);
//            TextView territoryNameTextView1 = (TextView) territoryRow1.findViewById(R.id.territoryNameTextView);
//            territoryNameTextView1.setText("Puntland");
//            territoryNameTextView1.setContentDescription("Puntland");
//
//            TextView territoryValueTextView1 = (TextView) territoryRow1.findViewById(R.id.territoryValueTextView);
//            territoryValueTextView1.setTextColor(Color.BLACK);
//            setStatisticTextMulti(territoryValueTextView1, "");
//
//            layout.addView(territoryRow1);
//
//            LinearLayout territoryRow2 = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.territory_row, layout, false);
//            TextView territoryNameTextView2 = (TextView) territoryRow2.findViewById(R.id.territoryNameTextView);
//            territoryNameTextView2.setText("Somaliland");
//            territoryNameTextView2.setContentDescription("Somaliland");
//
//            TextView territoryValueTextView2 = (TextView) territoryRow2.findViewById(R.id.territoryValueTextView);
//            territoryValueTextView2.setTextColor(Color.BLACK);
//            setStatisticTextMulti(territoryValueTextView2, "");
//
//            layout.addView(territoryRow2);
        }

        if (cname.contains("Tanzania")) {
            LinearLayout territoryRow = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.territory_row, layout, false);

            TextView territoryNameTextView = (TextView) territoryRow.findViewById(R.id.territoryNameTextView);
            territoryNameTextView.setText("Mainland");
            territoryNameTextView.setContentDescription("Mainland");

            TextView territoryValueTextView = (TextView) territoryRow.findViewById(R.id.territoryValueTextView);
            territoryValueTextView.setTextColor(Color.BLACK);
            setStatisticTextMulti(territoryValueTextView, ".687");

            layout.addView(territoryRow);

            LinearLayout territoryRow1 = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.territory_row, layout, false);
            TextView territoryNameTextView1 = (TextView) territoryRow1.findViewById(R.id.territoryNameTextView);
            territoryNameTextView1.setText("Zanzibar");
            territoryNameTextView1.setContentDescription("Zanzibar");

            TextView territoryValueTextView1 = (TextView) territoryRow1.findViewById(R.id.territoryValueTextView);
            territoryValueTextView1.setTextColor(Color.BLACK);
            setStatisticTextMulti(territoryValueTextView1, "Unavailable");

            layout.addView(territoryRow1);

        }

        if (cname.contains("Pakistan")) {
            LinearLayout territoryRow = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.territory_row, layout, false);

            TextView territoryNameTextView = (TextView) territoryRow.findViewById(R.id.territoryNameTextView);
            territoryNameTextView.setText("Federal");
            territoryNameTextView.setContentDescription("Federal");

            TextView territoryValueTextView = (TextView) territoryRow.findViewById(R.id.territoryValueTextView);
            territoryValueTextView.setTextColor(Color.BLACK);
            setStatisticTextMulti(territoryValueTextView, ".711");

            layout.addView(territoryRow);

            LinearLayout territoryRow1 = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.territory_row, layout, false);
            TextView territoryNameTextView1 = (TextView) territoryRow1.findViewById(R.id.territoryNameTextView);
            territoryNameTextView1.setText("Punjab");
            territoryNameTextView1.setContentDescription("Punjab");

            TextView territoryValueTextView1 = (TextView) territoryRow1.findViewById(R.id.territoryValueTextView);
            territoryValueTextView1.setTextColor(Color.BLACK);
            setStatisticTextMulti(territoryValueTextView1, "Unavailable");

            layout.addView(territoryRow1);

//            LinearLayout territoryRow2 = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.territory_row, layout, false);
//            TextView territoryNameTextView2 = (TextView) territoryRow2.findViewById(R.id.territoryNameTextView);
//            territoryNameTextView2.setText("Khyber Pakhtunkhwa");
//            territoryNameTextView2.setContentDescription("Khyber Pakhtunkhwa");
//
//            TextView territoryValueTextView2 = (TextView) territoryRow2.findViewById(R.id.territoryValueTextView);
//            setStatisticTextMulti(territoryValueTextView2, "");
//
//            layout.addView(territoryRow2);

            LinearLayout territoryRow3 = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.territory_row, layout, false);
            TextView territoryNameTextView3 = (TextView) territoryRow3.findViewById(R.id.territoryNameTextView);
            territoryNameTextView3.setText("Sindh");
            territoryNameTextView3.setContentDescription("Sindh");

            TextView territoryValueTextView3 = (TextView) territoryRow3.findViewById(R.id.territoryValueTextView);
            territoryValueTextView3.setTextColor(Color.BLACK);
            setStatisticTextMulti(territoryValueTextView3, "Unavailable");

            layout.addView(territoryRow3);

/*            LinearLayout territoryRow4 = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.territory_row, layout, false);
            TextView territoryNameTextView4 = (TextView) territoryRow4.findViewById(R.id.territoryNameTextView);
            territoryNameTextView4.setText("Balochistan");
            territoryNameTextView4.setContentDescription("Balochistan");

            TextView territoryValueTextView4 = (TextView) territoryRow4.findViewById(R.id.territoryValueTextView);
            territoryValueTextView4.setTextColor(Color.BLACK);
            setStatisticTextMulti(territoryValueTextView4, "");
            layout.addView(territoryRow4); */
        }
    }

    private void setStatisticTextMultiworking(TextView view, String percent, String ageRange, String total) {
        if (!(percent != null && !percent.isEmpty())
                && !(total != null && !total.isEmpty())) {
            return;
        }

        if (percent != null && !percent.isEmpty()) {
            try {
                percent = new DecimalFormat("#.0").format(Float.parseFloat(percent) * 100) + "%";
            } catch (Exception ex) {
            }

        }

        if (total != null && !total.isEmpty()) {
            try {
                NumberFormat nf = NumberFormat.getInstance(Locale.getDefault());
                double d = Float.parseFloat(total);
                total = nf.format(d);
            } catch (Exception nfe) {
            }
        }

        String text = percent;
        if ((total != null && !total.isEmpty()) || (ageRange != null && !ageRange.isEmpty())) {
            if (!text.equals("Unavailable") && !text.equals("N/A") && !text.equals("Unknown")) {
                text += " (";
                if (total != null && !total.isEmpty() && !total.equals("0")) text += total + "; ";
                if (ageRange != null && !ageRange.isEmpty()) text += "ages " + ageRange;
                text += ")";
            }
        }

        view.setText(text);
        if(view.getText().toString().startsWith("NA")||view.getText().toString().startsWith("N/A")){
            view.setContentDescription("Not Available");
        }
        view.setTextColor(Color.BLACK);
    }

    private void setStatisticTextMulti(TextView view, String percent) {
        if (!(percent != null && !percent.isEmpty())) {
            return;
        }
        try {
            percent = new DecimalFormat("#.0").format(Float.parseFloat(percent) * 100) + "%";
        } catch (Exception nfe) {
        }

        view.setText(percent);
        if(view.getText().toString().startsWith("NA")||view.getText().toString().startsWith("N/A")){
            view.setContentDescription("Not Available");
        }
        view.setTextColor(Color.BLACK);
    }

    private void setStatisticTextMulti(TextView view, String percent, String ageRange) {
        if (!(percent != null && !percent.isEmpty())) {
            return;
        }

        if (percent != null && !percent.isEmpty()) {
            try {
                percent = new DecimalFormat("#.0").format(Float.parseFloat(percent) * 100) + "%";
            } catch (Exception nfe) {
            }
        }
        String text = percent;
        if (ageRange != null && !ageRange.isEmpty() && !text.equals("Unavailable") && !text.equals("N/A") && !text.equals("Unknown")) {
            text += " (ages " + ageRange + ")";
        }

        view.setText(text);
        if(view.getText().toString().startsWith("NA")||view.getText().toString().startsWith("N/A")){
            view.setContentDescription("Not Available");
        }
        view.setTextColor(Color.BLACK);
    }

    //-----------------------------------------------------------------------------
    private void setStatisticText(int textViewId, String percent) {
        if (!(percent != null && !percent.isEmpty())) {
            return;
        }
        try {
            percent = new DecimalFormat("#.0").format(Float.parseFloat(percent) * 100) + "%";
        } catch (Exception nfe) {
        }
        TextView view = (TextView) findViewById(textViewId);
        view.setText(percent);
        if(view.getText().toString().startsWith("NA")||view.getText().toString().startsWith("N/A")){
            view.setContentDescription("Not Available");
        }
        view.setTextColor(Color.BLACK);
    }

    private void setStatisticText(int textViewId, String percent, String ageRange) {
        if (!(percent != null && !percent.isEmpty())) {
            return;
        }

        if (percent != null && !percent.isEmpty()) {
            try {
                percent = new DecimalFormat("#.0").format(Float.parseFloat(percent) * 100) + "%";
            } catch (Exception nfe) {
            }
        }
        String text = percent;
        if (ageRange != null && !ageRange.isEmpty() && !text.equals("Unavailable") && !text.equals("N/A") && !text.equals("Unknown")) {
            text += " (ages " + ageRange + ")";
        }

        TextView view = (TextView) findViewById(textViewId);
        view.setText(text);
        if(view.getText().toString().startsWith("NA")||view.getText().toString().startsWith("N/A")){
            view.setContentDescription("Not Available");
        }
        view.setTextColor(Color.BLACK);
    }

    private void setStatisticText(int textViewId, String percent, String ageRange, String total) {
        if (!(percent != null && !percent.isEmpty())
                && !(total != null && !total.isEmpty())) {
            return;
        }

        if (percent != null && !percent.isEmpty()) {
            try {
                percent = new DecimalFormat("#.0").format(Float.parseFloat(percent) * 100) + "%";
            } catch (Exception ex) {
            }

        }

        if (total != null && !total.isEmpty()) {
            try {
                NumberFormat nf = NumberFormat.getInstance(Locale.getDefault());
                double d = Float.parseFloat(total);
                total = nf.format(d);
            } catch (Exception nfe) {
            }
        }

        String text = percent;
        if ((total != null && !total.isEmpty()) || (ageRange != null && !ageRange.isEmpty())) {
            if (!text.equals("Unavailable") && !text.equals("N/A") && !text.equals("Unknown")) {
                text += " (";
                if (total != null && !total.isEmpty() && !total.equals("0")) text += total + "; ";
                if (ageRange != null && !ageRange.isEmpty()) text += "ages " + ageRange;
                text += ")";
            }


        }

        TextView view = (TextView) findViewById(textViewId);
        view.setText(text);
        if(view.getText().toString().startsWith("NA")||view.getText().toString().startsWith("N/A")){
            view.setContentDescription("Not Available");
        }
        view.setTextColor(Color.BLACK);
    }


}
