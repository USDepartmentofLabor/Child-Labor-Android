package gov.dol.childlabor;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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
        /*Country.SuggestedAction[] sortedSuggestedAction = country.getSuggestedActions();
        for(Country.SuggestedAction suggestedAction : country.getSuggestedActions()) {
            if (suggestedAction.section.equals("Legal Framework")) {
                sortedSuggestedAction[0] = suggestedAction;
            }
            if (suggestedAction.section.equals("Enforcement")) {
                sortedSuggestedAction[1] = suggestedAction;
            }
            if (suggestedAction.section.equals("Coordination")) {
                sortedSuggestedAction[2] = suggestedAction;
            }
            if (suggestedAction.section.equals("Government Policies")) {
                sortedSuggestedAction[3] = suggestedAction;
            }
            if (suggestedAction.section.equals("Social Programs")) {
                sortedSuggestedAction[4] = suggestedAction;
            }
        }*/
        for (Country.SuggestedAction suggestedAction : country.getSuggestedActions()) {
            TextView header = (TextView) inflater.inflate(R.layout.suggested_actions_header, suggestedActionsLinearLayout, false);
            header.setText(suggestedAction.section);
            header.setContentDescription(suggestedAction.section + ", heading");
            suggestedActionsLinearLayout.addView(header);

            for (String action : suggestedAction.actions) {
                TextView row = (TextView) inflater.inflate(R.layout.suggested_actions_row, suggestedActionsLinearLayout, false);
                row.setText(action);
                suggestedActionsLinearLayout.addView(row);
            }
        }

        AppHelpers.trackScreenView((AnalyticsApplication) getApplication(), "Suggested Actions Screen");
    }

}
