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
        Country.MasterData data = country.data;
        displayBooleanValue((TextView) findViewById(R.id.c138TextView), data.c138Ratified);
        displayBooleanValue((TextView) findViewById(R.id.c182TextView), data.c182Ratified);
        displayBooleanValue((TextView) findViewById(R.id.crcTextView), data.crcRatified);
        displayBooleanValue((TextView) findViewById(R.id.crcArmedConflictTextView), data.crcArmedConflictRatified);
        displayBooleanValue((TextView) findViewById(R.id.crcSexualExploitationTextView), data.crcSexualExploitationRatified);
        displayBooleanValue((TextView) findViewById(R.id.palermoTextView), data.palermoRatified);

        AppHelpers.trackScreenView((AnalyticsApplication) getApplication(), "Conventions Screen");
    }

    private void displayBooleanValue(TextView view, Boolean value) {
        if (value == null) {
            view.setText("Unavailable");
        }
        else if (value == true) {
            view.setText("Yes");
            view.setTextColor(Color.parseColor("#54ba5b"));
        }
        else if (value == false) {
            view.setText("No");
            view.setTextColor(Color.RED);
        }
    }
}
