package gov.dol.childlabor;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Hashtable;

public class LegalStandardActivity extends AppCompatActivity {

    private Boolean hasStandardsFooter = false;
    private Boolean hasAgeFooter = false;
    private Boolean hasCombatFooter = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_legal_standard);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Country country = (Country) getIntent().getSerializableExtra("country");
        Hashtable<String, Country.Standard> standards = country.standards;
        displayBooleanValue((TextView) findViewById(R.id.minimumWorkTextView), standards.get("Minimum_Work"));
        displayBooleanValue((TextView) findViewById(R.id.minimumHazardWorkTextView), standards.get("Minimum_Hazardous_Work"));
        displayBooleanValue((TextView) findViewById(R.id.compulsoryEducationTextView), standards.get("Compulsory_Education"));
        displayBooleanValue((TextView) findViewById(R.id.freeEducationTextView), standards.get("Free_Public_Education"));

        AppHelpers.trackScreenView((AnalyticsApplication) getApplication(), "Laws Screen");
    }

    private void displayBooleanValue(TextView view, Boolean value) {
        displayBooleanValue(view, value, null);
    }

    private void displayBooleanValue(TextView view, Country.Standard standard) {
        String ageText = null;
        Boolean calculatedAge = standard.calculatedAge.equals("Yes");
        Boolean conformsStandard = standard.conformsStandard.equals("Yes");

        String labelText = null;

        if (!standard.value.isEmpty()) {
            labelText = standard.value;
            if (labelText.startsWith("Yes") && !conformsStandard) {
                this.hasStandardsFooter = true;
                labelText += "";
            }

            if(!standard.age.isEmpty()) {
                labelText += "(" + standard.age.toUpperCase();
                if (calculatedAge) {
                    this.hasAgeFooter = true;
                    labelText += "<sup><small>‡</small></sup>";
                }
                labelText += ")";
                String [] combatTypes = {"Minimum_Compulsory_Military", "Minumum_Voluntary_Military","Minumum_Non_State_Military"};
                if (standard.age.contains("/") && Arrays.asList(combatTypes).contains(standard.type)  && !standard.age.contains("n/a") && !standard.age.contains("N/A")) {
                    this.hasCombatFooter = true;
                    labelText += "";
                }
            }
        }

        if (!labelText.isEmpty()) {
            view.setText(Html.fromHtml(labelText));
            if (labelText.startsWith("Yes") && conformsStandard) {
                view.setTextColor(Color.parseColor("#007E17"));
            }
            else if (labelText.startsWith("Yes") && !conformsStandard) {
                view.setTextColor(Color.RED);
            }
            else if (labelText.startsWith("No") || labelText.startsWith("Unknown")) {
                view.setTextColor(Color.RED);
            }
            else if (!labelText.startsWith("N/A") && !labelText.startsWith("Unavailable")) {
                view.setTextColor(Color.BLACK);
            }
        }
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
            view.setTextColor(Color.parseColor("#007E17"));
        }
        else if (value == false) {
            view.setText("No");
            view.setTextColor(Color.RED);
        }
    }

}
