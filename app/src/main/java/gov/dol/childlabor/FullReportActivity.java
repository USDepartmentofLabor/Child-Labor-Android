package gov.dol.childlabor;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.joanzapata.pdfview.PDFView;

import java.util.Arrays;

public class FullReportActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_full_report);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Country country = (Country) getIntent().getSerializableExtra("country");

        String filename = country.getName().replace("ô", "o").replace("ã", "a").replace("é", "e").replace("í", "i")
                .replace(".", "") + ".pdf";

        setTitle(country.getName());
        PDFView pdfView = (PDFView) findViewById(R.id.pdfview);
        pdfView.fromAsset(filename).load();
    }

}
