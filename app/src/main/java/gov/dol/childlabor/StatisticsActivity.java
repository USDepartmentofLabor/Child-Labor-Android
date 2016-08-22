package gov.dol.childlabor;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.Arrays;

public class StatisticsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_statistics);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Country country = (Country) getIntent().getSerializableExtra("country");
        Country.Statistics statistics = country.statistics;

        setStatisticText(R.id.workTextView, statistics.workPercent, statistics.workAgeRange, statistics.workTotal);
        setStatisticText(R.id.agricultureTextView, statistics.agriculturePercent);
        setStatisticText(R.id.servicesTextView, statistics.servicesPercent);
        setStatisticText(R.id.industryTextView, statistics.industryPercent);
        setStatisticText(R.id.educationTextView, statistics.educationPercent, statistics.educationAgeRange);
        setStatisticText(R.id.workAndEducationTextView, statistics.workAndEducationPercent, statistics.workAndEducationAgeRange);
        setStatisticText(R.id.completionRateTextView, statistics.primaryCompletionPercent);

        String[] noHelpText = {"Christmas Island", "Cocos (Keeling) Islands", "Falkland Islands (Islas Malvinas)",
                "Norfolk Island", "Saint Helena, Ascension, and Tristan da Cunha", "Tokelau", "Wallis and Futuna",
                "British Virgin Islands", "Cook Islands", "Montserrat", "Western Sahara"};
        if (Arrays.asList(noHelpText).contains(country.getName())) {
            TextView helpTextView = (TextView) findViewById(R.id.statisticsHelpTextView);
            helpTextView.setVisibility(View.GONE);
        }

        AppHelpers.trackScreenView((AnalyticsApplication) getApplication(), "Statistics Screen");
    }

    private void setStatisticText(int textViewId, String percent) {
        if (!(percent != null && !percent.isEmpty())) {
            return;
        }

        percent = new DecimalFormat("#.#").format(Float.parseFloat(percent)) + "%";

        TextView view = (TextView) findViewById(textViewId);
        view.setText(percent);
        view.setTextColor(Color.BLACK);
    }

    private void setStatisticText(int textViewId, String percent, String ageRange) {
        if (!(percent != null && !percent.isEmpty())) {
            return;
        }

        if (percent != null && !percent.isEmpty()) {
            percent = new DecimalFormat("#.#").format(Float.parseFloat(percent)) + "%";
        }
        String text = percent;
        if (ageRange != null && !ageRange.isEmpty()) {
            text += " (ages " + ageRange + ")";
        }

        TextView view = (TextView) findViewById(textViewId);
        view.setText(text);
        view.setTextColor(Color.BLACK);
    }

    private void setStatisticText(int textViewId, String percent, String ageRange, String total) {
        if (!(percent != null && !percent.isEmpty())
            && !(total != null && !total.isEmpty())) {
            return;
        }

        if (percent != null && !percent.isEmpty()) {
            percent = new DecimalFormat("#.#").format(Float.parseFloat(percent)) + "%";
        }

        if (total != null && !total.isEmpty()) {
            DecimalFormat df = new DecimalFormat("#");
            df.setGroupingUsed(true);
            total = df.format(Float.parseFloat(total));
        }

        String text = percent;
        if ((total != null && !total.isEmpty()) || (ageRange != null && !ageRange.isEmpty())) {
            text += " (";
            if (total != null && !total.isEmpty() && !total.equals("0")) text += total + "; ";
            if (ageRange != null && !ageRange.isEmpty()) text += "ages " + ageRange;
            text += ")";
        }

        TextView view = (TextView) findViewById(textViewId);
        view.setText(text);
        view.setTextColor(Color.BLACK);
    }

}
