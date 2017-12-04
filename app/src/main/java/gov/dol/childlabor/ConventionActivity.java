package gov.dol.childlabor;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

public class ConventionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_convention);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Country country = (Country) getIntent().getSerializableExtra("country");
        Country.Conventions data = country.conventions;
        displayValue((TextView) findViewById(R.id.c138TextView), data.c138Ratified);
        displayValue((TextView) findViewById(R.id.c182TextView), data.c182Ratified);
        displayValue((TextView) findViewById(R.id.crcTextView), data.crcRatified);
        displayValue((TextView) findViewById(R.id.crcArmedConflictTextView), data.crcArmedConflictRatified);
        displayValue((TextView) findViewById(R.id.crcSexualExploitationTextView), data.crcSexualExploitationRatified);
        displayValue((TextView) findViewById(R.id.palermoTextView), data.palermoRatified);

        AppHelpers.trackScreenView((AnalyticsApplication) getApplication(), "Conventions Screen");
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
        }
        view.setText(value);
    }
}
