package gov.dol.childlabor;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

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

        String title = getIntent().getStringExtra("title");
        switch (title) {
            case "Reports Fact Sheet":
                filename = "Intro to OCFT.pdf";
                break;
            case "Foreword":
                filename = "TDA-foreword-2017.pdf";
                break;
            case "Sweat & Toil Magazine":
                filename = "Sweat-And-Toil-Magazine.pdf";
                break;
            case "An Intro to OCFT":
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.dol.gov/sites/default/files/documents/ilab/reports/child-labor/findings/OCFTBooklet.pdf"));
                startActivity(intent);
                break;
            case "OCFT Fact Sheet":
                filename = "Intro to OCFT.pdf";
                break;
            case "Programs Fact Sheet":
                filename = "Intro to OCFT.pdf";
                break;
            case "Regional Efforts Fact Sheet":
                filename = "Intro to OCFT.pdf";
                break;
            case "Combo FAQs":
                filename = "FAQs-COMBO.pdf";
                break;
            case "TDA FAQs":
                filename = "FAQs-TDA.pdf";
                break;
            case "TVPRA FAQs":
                filename = "FAQs-TVPRA.pdf";
                break;
            case "EO FAQs":
                filename = "FAQs-EO.pdf";
                break;
            case "Comply Chain":
                intent = new Intent(getApplicationContext(), ToolKitActivity.class);
                intent.putExtra("title", "Comply Chain app");
                startActivity(intent);
/*                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.dol.gov/ComplyChain"));
                startActivity(intent);*/
                break;
            default:
                filename = "ToolkitForResponsibleBusinesses-lo.pdf";
                break;
        }
        if (title.contains("Comply Chain app") ) {
        }
        else if(title.contains("An Intro to OCFT") ) {

        }
        else {
            setTitle(title);
            PDFView pdfView = (PDFView) findViewById(R.id.pdfview);
            pdfView.fromAsset(filename).load();
        }


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
