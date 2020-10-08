package gov.dol.childlabor;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class MoreInfoActivity extends AppCompatActivity {
    String filename;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_info);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

//        String[] items = {"About this App", "Methodology", "Child Labor Report", "Child and Forced Labor List of Goods",
//                "Forced Child Labor List of Products", "Reports Fact Sheet", "OCFT Fact Sheet", "Programs Fact Sheet",
//                "Regional Efforts Fact Sheet", "TDA FAQs", "TVPRA FAQs", "EO FAQs", "Combo FAQs", "Toolkit for Businesses"};

        String[] items;
        Integer id = getIntent().getIntExtra("id", R.id.action_reports);
        switch (id) {
            default:
            case R.id.action_reports:
                setTitle("Reports");
//                items = new String[]{"Foreword", "Child Labor Report", "Child and Forced Labor List of Goods", "Forced Child Labor List of Products"};
                items = new String[]{"Foreword", "Child Labor Report"};
                break;
/*            case R.id.action_fact_sheets:
                setTitle("Fact Sheets");
                items = new String[]{"An Intro to OCFT",};
                //items = new String[]{"Reports Fact Sheet", "OCFT Fact Sheet", "Programs Fact Sheet", "Regional Efforts Fact Sheet"};
                break;*/
            case R.id.action_faqs:
                setTitle("FAQs");
                items = new String[]{"TDA FAQs", "TVPRA FAQs", "EO FAQs", "Combo FAQs"};
        }

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
//                    case "Child Labor Report":
//                        intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.dol.gov/sites/dolgov/files/ILAB/child_labor_reports/tda2019/SweatToil.pdf"));
//                        break;
//                    case "Child and Forced Labor List of Goods":
//                        intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.dol.gov/sites/dolgov/files/ILAB/ListofGoods.pdf"));
//                        break;
//                    case "Forced Child Labor List of Products":
//                        intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.dol.gov/sites/dolgov/files/ILAB/EO_Report_2014.pdf"));
//                        break;
                    case "TDA FAQs":
                    case "TVPRA FAQs":
                    case "EO FAQs":
                    case "Foreword":
                    case "Sweat And Toil Magazine":
                    case "An Intro to OCFT":
                    case "Reports Fact Sheet":
                    case "Programs Fact Sheet":
                    case "Regional Efforts Fact Sheet":
                    case "Combo FAQs":
                    case "NEW: Comply Chain app":
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
