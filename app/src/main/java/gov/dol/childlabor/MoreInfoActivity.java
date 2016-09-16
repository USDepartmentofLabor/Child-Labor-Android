package gov.dol.childlabor;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MoreInfoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        })
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        String[] items = {"About this App", "Methodology", "Child Labor Report", "Child and Forced Labor List of Goods",
                "Forced Child Labor List of Products", "Reports Fact Sheet", "OCFT Fact Sheet", "Programs Fact Sheet",
                "Regional Efforts Fact Sheet", "TDA FAQs", "TVPRA FAQs", "EO FAQs", "Combo FAQs", "Toolkit for Businesses"};
        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items) {
            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                String name = getItem(position);

                TextView theView = (TextView) getLayoutInflater().inflate(android.R.layout.simple_list_item_1, parent, false);
                theView.setText(name);
                theView.setContentDescription(name + ", Button");

                return theView;
            }
        };

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(itemsAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = null;
                String item = String.valueOf(parent.getItemAtPosition(position));
                switch (item) {
                    case "About this App":
                        intent = new Intent(getApplicationContext(), AboutActivity.class);
                        break;
                    case "Methodology":
                        intent = new Intent(getApplicationContext(), MethodologyActivity.class);
                        break;
                    case "Child Labor Report":
                        intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.dol.gov"));
                        break;
                    case "Child and Forced Labor List of Goods":
                    case "Forced Child Labor List of Products":
                        intent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.dol.gov"));
                        break;
                    case "TDA FAQs":
                    case "TVPRA FAQs":
                    case "EO FAQs":
                    case "Reports Fact Sheet":
                    case "OCFT Fact Sheet":
                    case "Programs Fact Sheet":
                    case "Regional Efforts Fact Sheet":
                    case "Combo FAQs":
                    case "Toolkit for Businesses":
                        intent = new Intent(getApplicationContext(), FactsheetActivity.class);
                        intent.putExtra("title", item);
                        break;
                }

                if (intent != null) startActivity(intent);
            }
        });

        AppHelpers.trackScreenView((AnalyticsApplication) getApplication(), "More Information Screen");
    }

}
