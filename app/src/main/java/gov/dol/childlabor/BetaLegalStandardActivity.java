package gov.dol.childlabor;

import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Arrays;
import java.util.Hashtable;

public class BetaLegalStandardActivity extends AppCompatActivity {

    private Boolean hasStandardsFooter = false;
    private Boolean hasAgeFooter = false;
    private Boolean hasCombatFooter = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Country country = (Country) getIntent().getSerializableExtra("country");
        if (country.hasMultipleTerritories) {
            setContentView(R.layout.activity_beta_legal_standard_multi);

        }else {
            setContentView(R.layout.activity_beta_legal_standard);
        }

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        AppHelpers.trackScreenView((AnalyticsApplication) getApplication(), "Legal Standards Screen");

        if (country.hasMultipleTerritories) {
            setMultipleTerritoryValues(country.territoryStandards);
        }
        else {
            setSingleTerritoryValues(country.standards);
        }

        setFooter();
    }

    private void setMultipleTerritoryValues(Hashtable<String, Country.TerritoryStandard> territoryStandards) {
        displayTerritories((LinearLayout) findViewById(R.id.minimumWorkLinearLayout), territoryStandards.get("Minimum_Work"));
        displayTerritories((LinearLayout) findViewById(R.id.minimumHazardWorkLinearLayout), territoryStandards.get("Minimum_Hazardous_Work"));
        displayTerritories((LinearLayout) findViewById(R.id.compulsoryMilitaryLinearLayout), territoryStandards.get("Minimum_Compulsory_Military"));
        displayTerritories((LinearLayout) findViewById(R.id.voluntaryMilitaryLinearLayout), territoryStandards.get("Minumum_Voluntary_Military"));
        displayTerritories((LinearLayout) findViewById(R.id.nsCompulsoryMilitaryLinearLayout), territoryStandards.get("Minumum_Non_State_Military"));
        displayTerritories((LinearLayout) findViewById(R.id.typesHazardousWorkLinearLayout), territoryStandards.get("Types_Hazardous_Work"));
        displayTerritories((LinearLayout) findViewById(R.id.prohibitionForcedLaborLinearLayout), territoryStandards.get("Prohibition_Forced_Labor"));
        displayTerritories((LinearLayout) findViewById(R.id.prohibitionChildTraffickingLinearLayout), territoryStandards.get("Prohibition_Child_Trafficking"));
        displayTerritories((LinearLayout) findViewById(R.id.prohibitionCSECLinearLayout), territoryStandards.get("Prohibition_CSEC"));
        displayTerritories((LinearLayout) findViewById(R.id.prohibitionIllicitActivitiesLinearLayout), territoryStandards.get("Prohibition_Illicit_Activities"));
        displayTerritories((LinearLayout) findViewById(R.id.compulsoryEducationLinearLayout), territoryStandards.get("Compulsory_Education"));
        displayTerritories((LinearLayout) findViewById(R.id.freeEducationLinearLayout), territoryStandards.get("Free_Public_Education"));
    }

    private void setSingleTerritoryValues(Hashtable<String, Country.Standard> standards) {
        displayStandard((TextView) findViewById(R.id.minimumWorkTextView), standards.get("Minimum_Work"));
        displayStandard((TextView) findViewById(R.id.minimumHazardWorkTextView), standards.get("Minimum_Hazardous_Work"));
        displayStandard((TextView) findViewById(R.id.compulsoryMilitaryTextView), standards.get("Minimum_Compulsory_Military"));
        displayStandard((TextView) findViewById(R.id.voluntaryMilitaryTextView), standards.get("Minumum_Voluntary_Military"));
        displayStandard((TextView) findViewById(R.id.nsCompulsoryMilitaryTextView), standards.get("Minumum_Non_State_Military"));
        displayStandard((TextView) findViewById(R.id.typesHazardousWorkTextView), standards.get("Types_Hazardous_Work"));
        displayStandard((TextView) findViewById(R.id.prohibitionForcedLaborTextView), standards.get("Prohibition_Forced_Labor"));
        displayStandard((TextView) findViewById(R.id.prohibitionChildTraffickingTextView), standards.get("Prohibition_Child_Trafficking"));
        displayStandard((TextView) findViewById(R.id.prohibitionCSECTextView), standards.get("Prohibition_CSEC"));
        displayStandard((TextView) findViewById(R.id.prohibitionIllicitActivitiesTextView), standards.get("Prohibition_Illicit_Activities"));
        displayStandard((TextView) findViewById(R.id.compulsoryEducationTextView), standards.get("Compulsory_Education"));
        displayStandard((TextView) findViewById(R.id.freeEducationTextView), standards.get("Free_Public_Education"));
    }

    private void setFooter() {
        String footerText = "* Please note that a “Yes” indicates that the legal framework meets the international standard. \n\nPlease see the chapter text for more information regarding gaps in legal framework and suggested actions.";
            if (this.hasAgeFooter) {
                if (this.hasStandardsFooter) footerText += "\n";
                footerText += "\n‡ Age calculated based on available information";
            }
            TextView footerTextView = (TextView) findViewById(R.id.footerTextView);
            footerTextView.setContentDescription(footerText);
            footerTextView.setVisibility(View.VISIBLE);
            footerTextView.setText(footerText);
    }

    private void displayTerritories(LinearLayout layout, Country.TerritoryStandard standard) {
        if(standard!=null && standard.territories!=null && standard.territories.size() != 0) {
            for (Country.TerritoryValue value : standard.territories) {
                LinearLayout territoryRow = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.territory_row, layout, false);

                TextView territoryNameTextView = (TextView) territoryRow.findViewById(R.id.territoryNameTextView);
                territoryNameTextView.setText(value.displayName);
                territoryNameTextView.setContentDescription(value.territory);

                TextView territoryValueTextView = (TextView) territoryRow.findViewById(R.id.territoryValueTextView);
                displayValue(territoryValueTextView, standard.type, value.value, value.age, value.calculatedAge, value.conformsStandard);

                layout.addView(territoryRow);
            }
        }else {
            LinearLayout territoryRow = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.territory_row, layout, false);

            TextView territoryNameTextView = (TextView) territoryRow.findViewById(R.id.territoryNameTextView);
            territoryNameTextView.setText("All Territories");

            // TextView territoryValueTextView = (TextView) territoryRow.findViewById(R.id.territoryValueTextView);
            // displayValue(territoryValueTextView, standard.type, "Unavailable", null, null, null);

            layout.addView(territoryRow);
        }
    }

    private void displayStandard(TextView view, Country.Standard standard) {
        if(standard!=null)
            displayValue(view, standard.type, standard.value, standard.age, standard.calculatedAge, standard.conformsStandard);
    }

    private void displayValue(TextView view, String type, String standard, String age, String calculatedAgeString, String conformsStandardString) {
        Boolean calculatedAge = calculatedAgeString.equals("Yes");
        Boolean conformsStandard = conformsStandardString.equals("Yes");

        String labelText = null;
        String accessibleText = null;

        if (standard != null && !standard.isEmpty()) {
            labelText = standard;
            accessibleText = standard.replace("*", "");
            if (labelText.startsWith("Yes") && !conformsStandard) {
                this.hasStandardsFooter = true;
                labelText += "";
                accessibleText += "";
            }

            if(!age.isEmpty() && !age.contains("N/A" ) && !age.contains("n/a") && !age.contains("No") ) {
                labelText += " (" + age.toUpperCase();
                accessibleText += ", " + age.toUpperCase();
                if (calculatedAge) {
                    this.hasAgeFooter = true;
                    labelText += "<sup><small>‡</small></sup>";
                    accessibleText += ", age calculated based on available information ";
                }
                labelText += ")";
                String [] combatTypes = {"Minimum_Compulsory_Military", "Minumum_Voluntary_Military","Minumum_Non_State_Military" };
                if (age.contains("/") && Arrays.asList(combatTypes).contains(type) && !age.contains("n/a") && !age.contains("N/A")) {
                    this.hasCombatFooter = true;
                    labelText += "<sup><small>Φ</small></sup>";
                    accessibleText += ", ages denoted are combat/non-combat ";
                }
            }
        }

        if (labelText != null && !labelText.isEmpty()) {
            view.setText(Html.fromHtml(labelText));
            view.setContentDescription((accessibleText.startsWith("N/A")) ? "Not Applicable" : accessibleText);
            if (labelText.startsWith("Yes") && conformsStandard) {
                view.setTextColor(Color.parseColor("#007E17"));
            }
            else if (labelText.startsWith("Yes") && type.equals("Free_Public_Education")) {
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

}
