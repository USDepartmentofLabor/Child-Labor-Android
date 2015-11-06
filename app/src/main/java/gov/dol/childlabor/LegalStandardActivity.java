package gov.dol.childlabor;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import java.text.DecimalFormat;

public class LegalStandardActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_legal_standard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Country country = (Country) getIntent().getSerializableExtra("country");
        Country.MasterData data = country.data;
        displayBooleanValue((TextView) findViewById(R.id.minimumWorkTextView), data.minimumWork, data.minimumWorkAge);
        displayBooleanValue((TextView) findViewById(R.id.minimumHazardWorkTextView), data.minimumHazardWork, data.minimumHazardWorkAge);
        displayBooleanValue((TextView) findViewById(R.id.compulsoryEducationTextView), data.compulsoryEducation, data.compulsoryEducationAge);
        displayBooleanValue((TextView) findViewById(R.id.freeEducationTextView), data.freeEducation);

        AppHelpers.trackScreenView((AnalyticsApplication) getApplication(), "Laws Screen");
    }

    private void displayBooleanValue(TextView view, Boolean value) {
        displayBooleanValue(view, value, null);
    }

    private void displayBooleanValue(TextView view, Boolean value, String age) {
        String ageText = null;
        if (age != null && !age.isEmpty() && !age.equalsIgnoreCase("None") && !age.equalsIgnoreCase("Unclear") && !age.equalsIgnoreCase("Unknown")) {
            try {
                ageText = new DecimalFormat("#.#").format(Float.parseFloat(age));
            }
            catch (Exception e) {
                ageText = age;
            }
        }

        if (value == null) {
            view.setText("Unavailable");
        }
        else if (value == true) {
            String text = (ageText == null) ? "Yes" : "Yes (" + ageText + ")";
            view.setText(text);
            view.setTextColor(Color.parseColor("#54ba5b"));
        }
        else if (value == false) {
            view.setText("No");
            view.setTextColor(Color.RED);
        }
    }

}
