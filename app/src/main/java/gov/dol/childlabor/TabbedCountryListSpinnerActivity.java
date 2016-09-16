package gov.dol.childlabor;

import android.app.Activity;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class TabbedCountryListSpinnerActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbed_country_list_spinner);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);
        for( int i = 0; i < tabLayout.getTabCount(); i++) {
            tabLayout.getTabAt(i).setContentDescription(tabLayout.getTabAt(i).getText() + ", Button");
        }

        AppHelpers.trackScreenView((AnalyticsApplication) getApplication(), "Countries List Screen");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search, menu);
        return true;
    }


    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return PlaceholderFragment.newInstance(position + 1);
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "A - Z";
                case 1:
                    return "By Level";
                case 2:
                    return "By Region";
            }
            return null;
        }
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        private String searchQuery = "";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setHasOptionsMenu(true);
        }

        @Override
        public void onPrepareOptionsMenu(Menu menu) {
            final int sectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);

            MenuItem searchItem = menu.findItem(R.id.search);

            final SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
            searchView.setQueryHint("Filter Countries");
            searchView.clearFocus();
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextChange(String query) {
                    searchQuery = query;

                    Country[] countries;
                    String selection;
                    switch (sectionNumber) {
                        case 2:
                            selection = ((Spinner) getView().findViewById(R.id.listViewSpinner)).getSelectedItem().toString();
                            countries = getCountriesBySearch(query, getCountriesByLevel(selection));
                            break;
                        case 3:
                            selection = ((Spinner) getView().findViewById(R.id.listViewSpinner)).getSelectedItem().toString();
                            countries = getCountriesBySearch(query, getCountriesByRegion(selection));
                            break;
                        default:
                            countries = getCountriesBySearch(query);
                    }
                    CountryListAdapter itemsAdapter = new CountryListAdapter(getActivity(), countries, 1);
                    ListView listView = (ListView) getView().findViewById(R.id.listView);
                    listView.setAdapter(itemsAdapter);
                    return false;
                }

                @Override
                public boolean onQueryTextSubmit(String query) {
                    searchQuery = query;

                    searchView.clearFocus();
                    return false;
                }

            });

            super.onPrepareOptionsMenu(menu);
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_tabbed_country_list_spinner, container, false);

            final int sectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);
            switch(sectionNumber) {
                case 1:
                    Spinner spinner = (Spinner) rootView.findViewById(R.id.listViewSpinner);
                    spinner.setVisibility(View.GONE);
                    break;
                case 2:
                    getLevelHeaderView(rootView);
                    break;
                case 3:
                    getRegionHeaderView(rootView);
                    break;
            }

            Country[] countries = CountryXmlParser.fromContext(getContext()).getCountryList();
            ListView listView = (ListView) rootView.findViewById(R.id.listView);
            listView.setAdapter(new CountryListAdapter(getActivity(), countries, sectionNumber));
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Country selectedCountry = (Country) parent.getItemAtPosition(position);
                    Intent intent = new Intent(getActivity(), CountryViewActivity.class);
                    intent.putExtra("country", selectedCountry);
                    startActivity(intent);
                }
            });

//            final SearchView searchFilter = (SearchView) rootView.findViewById(R.id.searchFilter);
//            searchFilter.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//                @Override
//                public boolean onQueryTextChange(String query) {
//                    Country[] countries;
//                    String selection;
//                    switch (sectionNumber) {
//                        case 2:
//                            selection = ((Spinner) getView().findViewById(R.id.listViewSpinner)).getSelectedItem().toString();
//                            countries = getCountriesBySearch(query, getCountriesByLevel(selection));
//                            break;
//                        case 3:
//                            selection = ((Spinner) getView().findViewById(R.id.listViewSpinner)).getSelectedItem().toString();
//                            countries = getCountriesBySearch(query, getCountriesByRegion(selection));
//                            break;
//                        default:
//                            countries = getCountriesBySearch(query);
//                    }
//                    CountryListAdapter itemsAdapter = new CountryListAdapter(getActivity(), countries, 1);
//                    ListView listView = (ListView) getView().findViewById(R.id.listView);
//                    listView.setAdapter(itemsAdapter);
//                    return false;
//                }
//
//                @Override
//                public boolean onQueryTextSubmit(String query) {
//                    searchFilter.clearFocus();
//                    return false;
//                }
//
//            });

            return rootView;
        }

        protected void getLevelHeaderView(View rootView) {

            Spinner spinner = (Spinner) rootView.findViewById(R.id.listViewSpinner);
            String[] items = {"All Assessment Levels", "Significant Advancement", "Moderate Advancement", "Minimal Advancement", "No Advancement", "No Assessment", "Not Covered in TDA Report"};
            spinner.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.good_view_exploitation_spinner_row, R.id.exploitationSpinnerTextView, items) {
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    LayoutInflater theInflater = getActivity().getLayoutInflater();
                    View theView = theInflater.inflate(R.layout.good_view_exploitation_spinner_row, parent, false);

                    TextView spinnerTextView = (TextView) theView.findViewById(R.id.exploitationSpinnerTextView);
                    spinnerTextView.setText(getItem(position));

                    ImageView childLaborImageView = (ImageView) theView.findViewById(R.id.exploitationSpinnerChildLaborImageView);
                    ImageView forcedLaborImageView = (ImageView) theView.findViewById(R.id.exploitationSpinnerForcedLaborImageView);
                    ImageView forcedChildLaborImageView = (ImageView) theView.findViewById(R.id.exploitationSpinnerForcedChildLaborImageView);

                    childLaborImageView.setVisibility(View.GONE);
                    forcedLaborImageView.setVisibility(View.GONE);
                    forcedChildLaborImageView.setVisibility(View.GONE);

                    return theView;
                }

                @Override
                public View getDropDownView(int position, View convertView, ViewGroup parent) {
                    return getView(position, convertView, parent);
                }
            });

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String level = parent.getItemAtPosition(position).toString();
                    Country [] countries = getCountriesBySearch(searchQuery, getCountriesByLevel(level));

                    CountryListAdapter itemsAdapter = new CountryListAdapter(getActivity(), countries, 2);
                    ListView listView = (ListView) getView().findViewById(R.id.listView);
                    listView.setAdapter(itemsAdapter);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }

        protected void getRegionHeaderView(View rootView) {

            Spinner spinner = (Spinner) rootView.findViewById(R.id.listViewSpinner);
            String[] items = {"All Regions", "Asia & the Pacific", "Europe & Eurasia", "Latin America & the Caribbean", "Middle East & North Africa", "Sub-Saharan Africa"};
            spinner.setAdapter(new ArrayAdapter<String>(getActivity(), R.layout.good_view_exploitation_spinner_row, R.id.exploitationSpinnerTextView, items) {
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    LayoutInflater theInflater = getActivity().getLayoutInflater();
                    View theView = theInflater.inflate(R.layout.good_view_exploitation_spinner_row, parent, false);

                    TextView spinnerTextView = (TextView) theView.findViewById(R.id.exploitationSpinnerTextView);
                    spinnerTextView.setText(getItem(position));

                    ImageView childLaborImageView = (ImageView) theView.findViewById(R.id.exploitationSpinnerChildLaborImageView);
                    ImageView forcedLaborImageView = (ImageView) theView.findViewById(R.id.exploitationSpinnerForcedLaborImageView);
                    ImageView forcedChildLaborImageView = (ImageView) theView.findViewById(R.id.exploitationSpinnerForcedChildLaborImageView);

                    childLaborImageView.setVisibility(View.GONE);
                    forcedLaborImageView.setVisibility(View.GONE);
                    forcedChildLaborImageView.setVisibility(View.GONE);

                    return theView;
                }

                @Override
                public View getDropDownView(int position, View convertView, ViewGroup parent) {
                    return getView(position, convertView, parent);
                }
            });

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    String region = parent.getItemAtPosition(position).toString();
                    Country [] countries = getCountriesBySearch(searchQuery, getCountriesByRegion(region));

                    CountryListAdapter itemsAdapter = new CountryListAdapter(getActivity(), countries, 3);
                    ListView listView = (ListView) getView().findViewById(R.id.listView);
                    listView.setAdapter(itemsAdapter);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
        }

        public Country[] getCountriesBySearch(String query) {
            Country[] allCountries = CountryXmlParser.fromContext(getContext()).getCountryList();
            return getCountriesBySearch(query, allCountries);
        }

        public Country[] getCountriesBySearch(String query, Country[] countries) {
            if (query.equals("")) return countries;

            ArrayList<Country> countryList = new ArrayList<>();
            for(Country country : countries) {
                String countryName = country.getName().replace("ô", "o").replace("ã", "a").replace("é", "e").replace("í", "i").replace("ó", "o").replace("á", "a");
                if (countryName.toLowerCase().startsWith(query.toLowerCase())) countryList.add(country);
            }

            return countryList.toArray(new Country[countryList.size()]);
        }

        public Country[] getCountriesByLevel(String level) {
            Country[] allCountries = CountryXmlParser.fromContext(getContext()).getCountryList();

            if (level.equals("All Assessment Levels")) return allCountries;

            ArrayList<Country> countryList = new ArrayList<>();
            for(Country country : allCountries) {
                if (level.equals(country.getLevelHeader().header)) countryList.add(country);
            }

            return countryList.toArray(new Country[countryList.size()]);
        }

        public Country[] getCountriesByRegion(String region) {
            Country[] allCountries = CountryXmlParser.fromContext(getContext()).getCountryList();

            if (region.equals("All Regions")) return allCountries;

            ArrayList<Country> countryList = new ArrayList<>();
            for(Country country : allCountries) {
                if (region.equals(country.getRegionHeader())) countryList.add(country);
            }

            return countryList.toArray(new Country[countryList.size()]);
        }
    }
}
