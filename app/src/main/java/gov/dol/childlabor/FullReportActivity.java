package gov.dol.childlabor;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.joanzapata.pdfview.PDFView;

public class FullReportActivity extends AppCompatActivity {

    String filename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_report);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Country country = (Country) getIntent().getSerializableExtra("country");

        filename = country.getName().replace("ô", "o").replace("ã", "a").replace("é", "e").replace("í", "i").replace("ó", "o").replace("á", "a")
                .replace(".", "") + ".pdf";

        setTitle(country.getName());
        PDFView pdfView = (PDFView) findViewById(R.id.pdfview);
        pdfView.fromAsset(filename).load();
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
