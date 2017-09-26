package gov.dol.childlabor;

import android.content.Intent;
import android.content.res.AssetManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String[] items = {"Countries", "Goods", "Exploitation Types"};
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
                Intent intent;
                switch (String.valueOf(parent.getItemAtPosition(position))) {
                    case "Countries":
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
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
        else if(id == R.id.action_fact_sheets) {
            Intent intent = new Intent(getApplicationContext(), FactsheetActivity.class);
            intent.putExtra("title", "An Intro to OCFT");
            startActivity(intent);
        }
        else if(id == R.id.action_toolkit) {
            Intent intent = new Intent(getApplicationContext(), FactsheetActivity.class);
            intent.putExtra("title", "NEW: Comply Chain app");
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
        Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.dol.gov/ilab"));
        startActivity(browserIntent);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

}
