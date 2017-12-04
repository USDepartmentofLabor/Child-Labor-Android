package gov.dol.childlabor;

import android.widget.Button;
import android.content.Intent;
import android.net.Uri;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Locale;

/**
 * Created by 21432 on 8/28/2017.
 */

public class ToolKitActivity extends AppCompatActivity {

    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toolkit);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        AppHelpers.trackScreenView((AnalyticsApplication) getApplication(), "Tool Kit Screen");



        final Button button = (Button) findViewById(R.id.btnInstall);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
               Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("market://details?id=gov.dol.complychain"));
                startActivity(intent);
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.dol.gov/ComplyChain"));
//                startActivity(intent);
//                AppHelpers.trackScreenView((AnalyticsApplication) getApplication(), "Comply Chain Screen");
            }
        });

    }


}
