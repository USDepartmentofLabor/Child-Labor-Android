package gov.dol.childlabor;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.joanzapata.pdfview.PDFView;


public class MenuMain extends AppCompatActivity {

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_page);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        AppHelpers.trackScreenView((AnalyticsApplication) getApplication(), "Main Menu Screen");

        TextView abouttextview = (TextView) findViewById(R.id.action_about);

        abouttextview.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), AboutActivity.class);
                startActivity(intent);
            }
        });

        TextView action_methodology = (TextView) findViewById(R.id.action_methodology);

        action_methodology.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MethodologyActivity.class);
                startActivity(intent);
            }
        });

        TextView ComplyChain = (TextView) findViewById(R.id.ComplyChain);

        ComplyChain.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), ToolKitActivity.class);
                startActivity(intent);
            }
        });


/*        TextView action_fact_sheets = (TextView) findViewById(R.id.action_fact_sheets);

        action_fact_sheets.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.dol.gov/sites/default/files/documents/ilab/reports/child-labor/findings/OCFTBooklet.pdf"));
                startActivity(intent);
                AppHelpers.trackScreenView((AnalyticsApplication) getApplication(), "More Information Screen");
            }
        });*/


        TextView ChildLaborReport = (TextView) findViewById(R.id.ChildLaborReport);

        ChildLaborReport.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.dol.gov/sites/dolgov/files/ILAB/child_labor_reports/tda2018/ChildLaborReport.pdf"));
                startActivity(intent);
                AppHelpers.trackScreenView((AnalyticsApplication) getApplication(), "More Information Screen");
            }
        });


        TextView ChildLaborListGoods = (TextView) findViewById(R.id.ChildLaborListGoods);

        ChildLaborListGoods.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.dol.gov/sites/dolgov/files/ILAB/ListofGoods.pdf"));
                startActivity(intent);
                AppHelpers.trackScreenView((AnalyticsApplication) getApplication(), "More Information Screen");
            }
        });

        TextView SecretaryForeword = (TextView) findViewById(R.id.SecretaryForeword);

        SecretaryForeword.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FactsheetActivity.class);
                intent.putExtra("title", "Foreword");
                startActivity(intent);

            }
        });

        TextView ForcedChildLaborProducts = (TextView) findViewById(R.id.ForcedChildLaborProducts);

        ForcedChildLaborProducts.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.dol.gov/sites/dolgov/files/ILAB/EO_Report_2014.pdf"));
                startActivity(intent);
                AppHelpers.trackScreenView((AnalyticsApplication) getApplication(), "More Information Screen");

            }
        });



//        TextView TDA = (TextView) findViewById(R.id.TDA);

/*        TDA.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FactsheetActivity.class);
                intent.putExtra("title", "TDA FAQs");
                startActivity(intent);

            }
        });*/


//        TextView TVPRA = (TextView) findViewById(R.id.TVPRA);

/*        TVPRA.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FactsheetActivity.class);
                intent.putExtra("title", "TVPRA FAQs");
                startActivity(intent);

            }
        });*/


//        TextView EO = (TextView) findViewById(R.id.EO);

/*        EO.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FactsheetActivity.class);
                intent.putExtra("title", "EO FAQs");
                startActivity(intent);

            }
        });*/


//        TextView Combo = (TextView) findViewById(R.id.Combo);

/*        Combo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), FactsheetActivity.class);
                intent.putExtra("title", "Combo FAQs");
                startActivity(intent);

            }
        });*/
    }
}
