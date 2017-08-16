package gov.dol.childlabor;

import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
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
import android.widget.RelativeLayout;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

public class TabbedGoodListSpinnerActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_tabbed_good_list_spinner);

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

        AppHelpers.trackScreenView((AnalyticsApplication) getApplication(), "Goods List Screen");
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
            // Show 2 total pages.
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "A - Z";
                case 1:
                    return "By Sector";
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
            searchView.setQueryHint("Filter Goods");
            searchView.clearFocus();
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextChange(String query) {
                    searchQuery = query;

                    Good[] goods;
                    String selection;
                    switch (sectionNumber) {
                        case 2:
                            selection = ((Spinner) getView().findViewById(R.id.listViewSpinner)).getSelectedItem().toString();
                            goods = getGoodsBySearch(query, getGoodsBySector(selection));
                            break;
                        default:
                            goods = getGoodsBySearch(query);
                    }
                    GoodListAdapter itemsAdapter = new GoodListAdapter(getActivity(), goods, 1);
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

            View rootView = inflater.inflate(R.layout.fragment_tabbed_good_list_spinner, container, false);

            getHeaderView(rootView);

            GoodXmlParser parser = GoodXmlParser.fromContext(getContext());
            Good[] goods;

            final int sectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);
            switch(sectionNumber) {
                case 2:
                    goods = parser.getGoodListBySector();
                    Spinner spinner = (Spinner) rootView.findViewById(R.id.listViewSpinner);

                    break;
                default:
                    goods = parser.getGoodList();
            }

            if (sectionNumber == 1) {
                Spinner spinner = (Spinner) rootView.findViewById(R.id.listViewSpinner);
                spinner.setVisibility(View.GONE);
            }

            GoodListAdapter itemsAdapter = new GoodListAdapter(getActivity(), goods, sectionNumber);

            ListView listView = (ListView) rootView.findViewById(R.id.listView);
            listView.setAdapter(itemsAdapter);
            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Good selectedGood = (Good) parent.getItemAtPosition(position);
                    Intent intent = new Intent(getActivity(), GoodViewActivity.class);
                    intent.putExtra("good", selectedGood);
                    startActivity(intent);
                }
            });

//            final SearchView searchFilter = (SearchView) rootView.findViewById(R.id.searchFilter);
//            searchFilter.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//                @Override
//                public boolean onQueryTextChange(String query) {
//                    Good[] goods;
//                    String selection;
//                    switch (sectionNumber) {
//                        case 2:
//                            selection = ((Spinner) getView().findViewById(R.id.listViewSpinner)).getSelectedItem().toString();
//                            goods = getGoodsBySearch(query, getGoodsBySector(selection));
//                            break;
//                        default:
//                            goods = getGoodsBySearch(query);
//                    }
//                    GoodListAdapter itemsAdapter = new GoodListAdapter(getActivity(), goods, 1);
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

        public Good[] getGoodsBySearch(String query) {
            Good[] allGoods = GoodXmlParser.fromContext(getContext()).getGoodList();
            return getGoodsBySearch(query, allGoods);
        }

        public Good[] getGoodsBySearch(String query, Good[] goods) {
            if (query.equals("")) return goods;

            ArrayList<Good> goodList = new ArrayList<>();
            for(Good good : goods) {
                String goodName = good.getName().replace("ô", "o").replace("ã", "a").replace("é", "e").replace("í", "i");
                if (goodName.toLowerCase().contains(query.toLowerCase())) goodList.add(good);
            }

            return goodList.toArray(new Good[goodList.size()]);
        }

        public Good[] getGoodsBySector(String sector) {
            Good[] allGoods = GoodXmlParser.fromContext(getContext()).getGoodList();

            if (sector.equals("All Sectors")) return allGoods;

            ArrayList<Good> goodList = new ArrayList<>();
            for(Good good : allGoods) {
                if (sector.equals(good.getSectorHeader())) goodList.add(good);
            }

            return goodList.toArray(new Good[goodList.size()]);
        }

        protected void getHeaderView(View rootView) {

            Spinner spinner = (Spinner) rootView.findViewById(R.id.listViewSpinner);
            String[] items = {"All Sectors", "Agriculture", "Manufacturing", "Mining", "Other"};
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
                    String sector = parent.getItemAtPosition(position).toString();
                    Good [] goods = getGoodsBySearch(searchQuery, getGoodsBySector(sector));

                    GoodListAdapter itemsAdapter = new GoodListAdapter(getActivity(), goods, 2);
                    ListView listView = (ListView) getView().findViewById(R.id.listView);
                    listView.setAdapter(itemsAdapter);
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });

        }
    }
}
