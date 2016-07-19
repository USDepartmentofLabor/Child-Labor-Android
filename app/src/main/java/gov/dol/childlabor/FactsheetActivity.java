package gov.dol.childlabor;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.joanzapata.pdfview.PDFView;

public class FactsheetActivity extends AppCompatActivity {

    String filename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_factsheet);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String title;
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
        else if(id == R.id.action_toolkit) {
            title = "ILAB Toolkit Handout";
            filename = "ILAB Toolkit Handout.pdf";
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pdf, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(id == R.id.action_open_with) {
            AppHelpers.openPDFIntent(this, filename);
        }

        return super.onOptionsItemSelected(item);
    }

}
