package gov.dol.childlabor;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.widget.TextView;

public class MechanismActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mechanism);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Country country = (Country) getIntent().getSerializableExtra("country");
        displayValue((TextView) findViewById(R.id.coordinationTextView), country.coordinationMechanism);
        displayValue((TextView) findViewById(R.id.policyTextView), country.policyMechanism);
        displayValue((TextView) findViewById(R.id.programTextView), country.programMechanism);

        AppHelpers.trackScreenView((AnalyticsApplication) getApplication(), "Mechanisms Screen");
    }

    private void displayValue(TextView view, String value) {
        if (value == null) {
            value = "Unavailable";
        }

        switch (value) {
            case "Yes":
                view.setTextColor(Color.parseColor("#007E17"));
                break;
            case "No":
                view.setTextColor(Color.RED);
                break;
            case "NA":
                view.setTextColor(Color.BLACK);
                break;
        }
        view.setText(value);
    }

}
