package gov.dol.childlabor;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

public class SuggestedActionsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggested_actions);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        LayoutInflater inflater = inflater = LayoutInflater.from(this);
        LinearLayout suggestedActionsLinearLayout = (LinearLayout) findViewById(R.id.suggestedActionsLinearLayout);

        Country country = (Country) getIntent().getSerializableExtra("country");

        if (country.getSuggestedActions().length > 0) {
            suggestedActionsLinearLayout.removeAllViews();
        }

        for(Country.SuggestedAction suggestedAction : country.getSuggestedActions()) {
            TextView header = (TextView) inflater.inflate(R.layout.suggested_actions_header, suggestedActionsLinearLayout, false);
            header.setText(suggestedAction.section);
            suggestedActionsLinearLayout.addView(header);

            for(String action : suggestedAction.actions) {
                TextView row = (TextView) inflater.inflate(R.layout.suggested_actions_row, suggestedActionsLinearLayout, false);
                row.setText(action);
                suggestedActionsLinearLayout.addView(row);
            }
        }

        AppHelpers.trackScreenView((AnalyticsApplication) getApplication(), "Suggested Actions Screen");
    }

}
