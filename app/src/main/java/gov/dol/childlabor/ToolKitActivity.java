package gov.dol.childlabor;

import android.widget.Button;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

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
                intent.setData(Uri.parse("https://play.google.com/store/apps/details?id=gov.dol.complychain&hl=en_US&gl=US&pli=1"));//"market://details?id=gov.dol.complychain"));
                startActivity(intent);
//                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.dol.gov/ComplyChain"));
//                startActivity(intent);
//                AppHelpers.trackScreenView((AnalyticsApplication) getApplication(), "Comply Chain Screen");
            }
        });

    }


}
