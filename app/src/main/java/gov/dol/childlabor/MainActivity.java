package gov.dol.childlabor;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;

public class MainActivity extends AppCompatActivity {
    TextView toolbarTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        toolbarTextView = toolbar.findViewById(R.id.toolbar_title);
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.P){
            toolbarTextView.setAccessibilityHeading(true);
        }else{
            ViewCompat.setAccessibilityDelegate(toolbarTextView, new AccessibilityDelegateCompat() {
                @Override
                public void onInitializeAccessibilityNodeInfo(View host, AccessibilityNodeInfoCompat info) {
                    super.onInitializeAccessibilityNodeInfo(host, info);
                    info.setHeading(true); // false to mark a view as not a heading
                }
            });
        }

        setSupportActionBar(toolbar);

        String[] items = {"Countries/Areas", "Goods", "Exploitation Types"};
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

        Button button = (Button) findViewById(R.id.menubutton);

        button.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MenuMain.class);
                startActivity(intent);
            }
        });

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(itemsAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent;
                switch (String.valueOf(parent.getItemAtPosition(position))) {
                    case "Countries/Areas":
                    default:
                        intent = new Intent(getApplicationContext(), TabbedCountryListSpinnerActivity.class);
                        break;
                    case "Goods":
                        intent = new Intent(getApplicationContext(), TabbedGoodListSpinnerActivity.class);
                        break;
                    case "Exploitation Types":
                        intent = new Intent(getApplicationContext(), ExploitationTypeListSpinnerActivity.class);
                        break;
                }

                startActivity(intent);
            }
        });

        AppHelpers.trackScreenView((AnalyticsApplication) getApplication(), "Main Screen");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
       // getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        if(id == R.id.action_about) {
            Intent intent = new Intent(getApplicationContext(), AboutActivity.class);
            startActivity(intent);
        }
        else if(id == R.id.action_methodology) {
            Intent intent = new Intent(getApplicationContext(), MethodologyActivity.class);
            startActivity(intent);
        }
/*        else if(id == R.id.action_fact_sheets) {
            Intent intent = new Intent(getApplicationContext(), FactsheetActivity.class);
            intent.putExtra("title", "An Intro to OCFT");
            startActivity(intent);
        }*/
        else if(id == R.id.action_toolkit) {
            Intent intent = new Intent(getApplicationContext(), FactsheetActivity.class);
            intent.putExtra("title", "Comply Chain app");
            startActivity(intent);
        }
        else {
            Intent intent = new Intent(getApplicationContext(), MoreInfoActivity.class);
            intent.putExtra("id", id);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }

    public void openBrowserDOL(View v) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.dol.gov"));
        startActivity(browserIntent);
    }

    public void openBrowserILAB(View v) {
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.dol.gov/agencies/ilab"));
        startActivity(browserIntent);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}
