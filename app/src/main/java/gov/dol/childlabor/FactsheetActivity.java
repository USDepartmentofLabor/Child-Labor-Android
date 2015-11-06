package gov.dol.childlabor;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.joanzapata.pdfview.PDFView;

public class FactsheetActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_factsheet);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String title, filename;
        int id = getIntent().getIntExtra("id", R.id.action_introduction);
        if(id == R.id.action_introduction) {
            title = "Introduction";
            filename = "2014 Findings on the Worst Forms of Child Labor_app.pdf";
        }
        else if(id == R.id.action_reports_fact_sheet) {
            title = "Reports Fact Sheet";
            filename = "Fact Sheets-Reports.pdf";
        }
        else if(id == R.id.action_ocft_fact_sheet) {
            title = "OCFT Fact Sheet";
            filename = "Fact Sheets-OCFT.pdf";
        }
        else {
            title = "Programs Fact Sheet";
            filename = "Fact Sheets-Programming.pdf";
        }

        setTitle(title);
        PDFView pdfView = (PDFView) findViewById(R.id.pdfview);
        pdfView.fromAsset(filename).load();

        AppHelpers.trackScreenView((AnalyticsApplication) getApplication(), title + " Screen");
    }

}
