package gov.dol.childlabor;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        String[] items = {"Countries", "Goods", "Exploitation Types"};
        ArrayAdapter<String> itemsAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items);

        ListView listView = (ListView) findViewById(R.id.listView);
        listView.setAdapter(itemsAdapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // String item = "You selected " + String.valueOf(parent.getItemAtPosition(position));
                // Toast.makeText(MainActivity.this, item, Toast.LENGTH_SHORT).show();

                Intent intent;
                switch (String.valueOf(parent.getItemAtPosition(position))) {
                    case "Countries":
                        intent = new Intent(getApplicationContext(), TabbedCountryListActivity.class);
                        break;
                    case "Goods":
                        intent = new Intent(getApplicationContext(), TabbedGoodListActivity.class);
                        break;
                    case "Exploitation Types":
                        intent = new Intent(getApplicationContext(), TabbedExploitationTypeListActivity.class);
                        break;
                    default:
                        intent = new Intent(getApplicationContext(), TabbedCountryListActivity.class);
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

        //noinspection SimplifiableIfStatement
        //        if(id == R.id.ilab_logo) {
        //            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.dol.gov/ilab"));
        //            startActivity(browserIntent);
        //        }
        //        else if(id == R.id.dol_logo) {
        //            Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.dol.gov"));
        //            startActivity(browserIntent);
        //        }

        if(id == R.id.action_about) {
            Intent intent = new Intent(getApplicationContext(), AboutActivity.class);
            startActivity(intent);
        }
        else if(id == R.id.action_methodology) {
            Intent intent = new Intent(getApplicationContext(), MethodologyActivity.class);
            startActivity(intent);
        }
        else {
            Intent intent = new Intent(getApplicationContext(), FactsheetActivity.class);
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
