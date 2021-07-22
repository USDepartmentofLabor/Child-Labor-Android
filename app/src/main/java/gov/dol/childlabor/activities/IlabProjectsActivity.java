package gov.dol.childlabor.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.util.Linkify;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.List;

import gov.dol.childlabor.AnalyticsApplication;
import gov.dol.childlabor.AppHelpers;
import gov.dol.childlabor.Country;
import gov.dol.childlabor.R;
import gov.dol.childlabor.models.Project;

public class IlabProjectsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_suggested_actions);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        LayoutInflater inflater = inflater = LayoutInflater.from(this);
        LinearLayout suggestedActionsLinearLayout = (LinearLayout) findViewById(R.id.suggestedActionsLinearLayout);
        TextView noData = suggestedActionsLinearLayout.findViewById(R.id.no_data);
        noData.setText("No Projects Available");
        Country country = (Country) getIntent().getSerializableExtra("country");

        if (country.getiLABProjects().size() > 0) {
            suggestedActionsLinearLayout.removeAllViews();
        }
        List<Project> projects = country.getiLABProjects();
        for (int i = 0; i < projects.size(); i++) {
            LinearLayout row = (LinearLayout) inflater.inflate(R.layout.project_row,suggestedActionsLinearLayout,false);
            TextView title = row.findViewById(R.id.project_title);
            TextView link = row.findViewById(R.id.project_link);
            Linkify.addLinks(link,Linkify.WEB_URLS);

            title.setText(projects.get(i).getTitle());
            link.setContentDescription("i lab project link for "+ projects.get(i).getTitle());
            link.setText(projects.get(i).getLink());
            link.setOnClickListener(view -> {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(((TextView)view).getText().toString()));
                startActivity(intent);
            });
            suggestedActionsLinearLayout.addView(row);
        }


        AppHelpers.trackScreenView((AnalyticsApplication) getApplication(), "ILAB Projects Action");
    }

}
