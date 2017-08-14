package gov.dol.childlabor;

import android.graphics.Color;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.text.NumberFormat;
import java.util.Hashtable;
import java.util.Locale;

public class TabbedEnforcementActivity extends AppCompatActivity {

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
        setContentView(R.layout.activity_tabbed_enforcement);

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
        private int sectionNumber;
        private Boolean laborFooter = false;
        private Boolean criminalFooter = false;
        private Boolean showDedicatedInspectors = false;
        private LayoutInflater inflater;

        public PlaceholderFragment() {
        }

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

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            final int sectionNumber = this.sectionNumber = getArguments().getInt(ARG_SECTION_NUMBER);
            this.inflater = inflater;

            Country country = (Country) getActivity().getIntent().getSerializableExtra("country");
            View rootView;
            switch(sectionNumber) {
                case 1:
                    if (country.hasMultipleTerritories) {
                        rootView = inflater.inflate(R.layout.fragment_tabbed_enforcement_labor_multi, container, false);
                        setMultipleTerritoryValues(rootView, country.territoryEnforcements);
                    }
                    else {
                        rootView = inflater.inflate(R.layout.fragment_tabbed_enforcement_labor, container, false);
                        setSingleTerritoryValues(rootView, country.enforcements);
                    }
                    break;
                case 2:
                default:
                    if (country.hasMultipleTerritories) {
                        rootView = inflater.inflate(R.layout.fragment_tabbed_enforcement_criminal_multi, container, false);
                        setMultipleTerritoryValues(rootView, country.territoryEnforcements);
                    }
                    else {
                        rootView = inflater.inflate(R.layout.fragment_tabbed_enforcement_criminal, container, false);
                        setSingleTerritoryValues(rootView, country.enforcements);
                    }
            }

            if (this.sectionNumber == 1 && !this.showDedicatedInspectors) {
                rootView.findViewById(R.id.laborDedicatedInspectorsContainer).setVisibility(View.GONE);
            }
            
            setFooter(rootView);

            return rootView;
        }

        private void setSingleTerritoryValues(View rootView, Hashtable<String, Country.Enforcement> enforcements) {
            if (this.sectionNumber == 1) {
                displayEnforcement((TextView) rootView.findViewById(R.id.laborFundingTextView), enforcements.get("Labor_Funding"));
                displayEnforcement((TextView) rootView.findViewById(R.id.laborInspectorsTextView), enforcements.get("Labor_Inspectors"));
                displayEnforcement((TextView) rootView.findViewById(R.id.laborDedicatedInspectorsTextView), enforcements.get("Dedicated_Labor_Inspectors"));
                displayEnforcement((TextView) rootView.findViewById(R.id.laborAssessPenaltiesTextView), enforcements.get("Authorized_Access_Penalties"));
                displayEnforcement((TextView) rootView.findViewById(R.id.laborInitialTrainingTextView), enforcements.get("Labor_New_Employee_Training"));
                displayEnforcement((TextView) rootView.findViewById(R.id.laborNewLawsTextView), enforcements.get("Labor_New_Law_Training"));
                displayEnforcement((TextView) rootView.findViewById(R.id.laborRefresherCoursesTextView), enforcements.get("Labor_Refresher_Courses"));
                displayEnforcement((TextView) rootView.findViewById(R.id.laborInspectionsTextView), enforcements.get("Labor_Inspections"));
                displayEnforcement((TextView) rootView.findViewById(R.id.laborWorksiteInspectionsTextView), enforcements.get("Labor_Worksite_Inspections"));
                displayEnforcement((TextView) rootView.findViewById(R.id.laborInspectorsMeetILOTextView), enforcements.get("Labor_Inspectors_Intl_Standards"));
                displayEnforcement((TextView) rootView.findViewById(R.id.laborDeskReviewsTextView), enforcements.get("Labor_Desk_Review_Inspections"));
                displayEnforcement((TextView) rootView.findViewById(R.id.laborViolationsFoundTextView), enforcements.get("Labor_Violations"));
                displayEnforcement((TextView) rootView.findViewById(R.id.laborPenaltiesImposedTextView), enforcements.get("Labor_Penalties_Imposed"));
                displayEnforcement((TextView) rootView.findViewById(R.id.laborCollectedPenaltiesTextView), enforcements.get("Labor_Penalties_Collected"));
                displayEnforcement((TextView) rootView.findViewById(R.id.laborConductedRoutineTextView), enforcements.get("Labor_Routine_Inspections_Conducted"));
                displayEnforcement((TextView) rootView.findViewById(R.id.laborTargetedRoutineTextView), enforcements.get("Labor_Routine_Inspections_Targeted"));
                displayEnforcement((TextView) rootView.findViewById(R.id.laborPermittedUnannouncedTextView), enforcements.get("Labor_Unannounced_Inspections_Premitted"));
                displayEnforcement((TextView) rootView.findViewById(R.id.laborConductedUnannouncedTextView), enforcements.get("Labor_Unannounced_Inspections_Conducted"));
                displayEnforcement((TextView) rootView.findViewById(R.id.laborComplaintMechanismTextView), enforcements.get("Labor_Complaint_Mechanism"));
                displayEnforcement((TextView) rootView.findViewById(R.id.laborReferralMechanismTextView), enforcements.get("Labor_Referral_Mechanism"));
            }
            else {
                displayEnforcement((TextView) rootView.findViewById(R.id.criminalInitialTrainingTextView), enforcements.get("Criminal_New_Employee_Training"));
                displayEnforcement((TextView) rootView.findViewById(R.id.criminalNewLawTrainingTextView), enforcements.get("Criminal_New_Law_Training"));
                displayEnforcement((TextView) rootView.findViewById(R.id.criminalRefresherCoursesTextView), enforcements.get("Criminal_Refresher_Courses"));
                displayEnforcement((TextView) rootView.findViewById(R.id.criminalInvestigationsTextView), enforcements.get("Criminal_Investigations"));
                displayEnforcement((TextView) rootView.findViewById(R.id.criminalViolationsFoundTextView), enforcements.get("Criminal_Violations"));
                displayEnforcement((TextView) rootView.findViewById(R.id.criminalProsecutionsInitiatedTextView), enforcements.get("Criminal_Prosecutions"));
                displayEnforcement((TextView) rootView.findViewById(R.id.criminalConvictionsTextView), enforcements.get("Criminal_Convictions"));
                displayEnforcement((TextView) rootView.findViewById(R.id.criminalReferralMechanismTextView), enforcements.get("Criminal_Referral_Mechanism"));
            }
        }

        private void setMultipleTerritoryValues(View rootView, Hashtable<String, Country.TerritoryEnforcement> enforcements) {
            if (this.sectionNumber == 1) {
                displayTerritories((LinearLayout) rootView.findViewById(R.id.laborFundingLinearLayout), enforcements.get("Labor_Funding"));
                displayTerritories((LinearLayout) rootView.findViewById(R.id.laborInspectorsLinearLayout), enforcements.get("Labor_Inspectors"));
                displayTerritories((LinearLayout) rootView.findViewById(R.id.laborDedicatedInspectorsLinearLayout), enforcements.get("Dedicated_Labor_Inspectors"));
                displayTerritories((LinearLayout) rootView.findViewById(R.id.laborAssessPenaltiesLinearLayout), enforcements.get("Authorized_Access_Penalties"));
                displayTerritories((LinearLayout) rootView.findViewById(R.id.laborInitialTrainingLinearLayout), enforcements.get("Labor_New_Employee_Training"));
                displayTerritories((LinearLayout) rootView.findViewById(R.id.laborNewLawsLinearLayout), enforcements.get("Labor_New_Law_Training"));
                displayTerritories((LinearLayout) rootView.findViewById(R.id.laborRefresherCoursesLinearLayout), enforcements.get("Labor_Refresher_Courses"));
                displayTerritories((LinearLayout) rootView.findViewById(R.id.laborInspectionsLinearLayout), enforcements.get("Labor_Inspections"));
                displayTerritories((LinearLayout) rootView.findViewById(R.id.laborWorksiteInspectionsLinearLayout), enforcements.get("Labor_Worksite_Inspections"));
                displayTerritories((LinearLayout) rootView.findViewById(R.id.laborInspectorsMeetILOLinearLayout), enforcements.get("Labor_Inspectors_Intl_Standards"));
                displayTerritories((LinearLayout) rootView.findViewById(R.id.laborDeskReviewsLinearLayout), enforcements.get("Labor_Desk_Review_Inspections"));
                displayTerritories((LinearLayout) rootView.findViewById(R.id.laborViolationsFoundLinearLayout), enforcements.get("Labor_Violations"));
                displayTerritories((LinearLayout) rootView.findViewById(R.id.laborPenaltiesImposedLinearLayout), enforcements.get("Labor_Penalties_Imposed"));
                displayTerritories((LinearLayout) rootView.findViewById(R.id.laborCollectedPenaltiesLinearLayout), enforcements.get("Labor_Penalties_Collected"));
                displayTerritories((LinearLayout) rootView.findViewById(R.id.laborConductedRoutineLinearLayout), enforcements.get("Labor_Routine_Inspections_Conducted"));
                displayTerritories((LinearLayout) rootView.findViewById(R.id.laborTargetedRoutineLinearLayout), enforcements.get("Labor_Routine_Inspections_Targeted"));
                displayTerritories((LinearLayout) rootView.findViewById(R.id.laborPermittedUnannouncedLinearLayout), enforcements.get("Labor_Unannounced_Inspections_Premitted"));
                displayTerritories((LinearLayout) rootView.findViewById(R.id.laborConductedUnannouncedLinearLayout), enforcements.get("Labor_Unannounced_Inspections_Conducted"));
                displayTerritories((LinearLayout) rootView.findViewById(R.id.laborComplaintMechanismLinearLayout), enforcements.get("Labor_Complaint_Mechanism"));
                displayTerritories((LinearLayout) rootView.findViewById(R.id.laborReferralMechanismLinearLayout), enforcements.get("Labor_Referral_Mechanism"));
            }
            else {
                displayTerritories((LinearLayout) rootView.findViewById(R.id.criminalInitialTrainingLinearLayout), enforcements.get("Criminal_New_Employee_Training"));
                displayTerritories((LinearLayout) rootView.findViewById(R.id.criminalNewLawTrainingLinearLayout), enforcements.get("Criminal_New_Law_Training"));
                displayTerritories((LinearLayout) rootView.findViewById(R.id.criminalRefresherCoursesLinearLayout), enforcements.get("Criminal_Refresher_Courses"));
                displayTerritories((LinearLayout) rootView.findViewById(R.id.criminalInvestigationsLinearLayout), enforcements.get("Criminal_Investigations"));
                displayTerritories((LinearLayout) rootView.findViewById(R.id.criminalViolationsFoundLinearLayout), enforcements.get("Criminal_Violations"));
                displayTerritories((LinearLayout) rootView.findViewById(R.id.criminalProsecutionsInitiatedLinearLayout), enforcements.get("Criminal_Prosecutions"));
                displayTerritories((LinearLayout) rootView.findViewById(R.id.criminalConvictionsLinearLayout), enforcements.get("Criminal_Convictions"));
                displayTerritories((LinearLayout) rootView.findViewById(R.id.criminalReferralMechanismLinearLayout), enforcements.get("Criminal_Referral_Mechanism"));
            }
        }


        private void displayEnforcement(TextView view, Country.Enforcement enforcement) {
            displayValue(view, enforcement.type, enforcement.value);
        }

        private void displayTerritories(LinearLayout layout, Country.TerritoryEnforcement enforcement) {

            for (Country.TerritoryValue value : enforcement.territories) {
                LinearLayout territoryRow = (LinearLayout) this.inflater.inflate(R.layout.territory_row, layout, false);

                TextView territoryNameTextView = (TextView) territoryRow.findViewById(R.id.territoryNameTextView);
                territoryNameTextView.setText(value.displayName);
                territoryNameTextView.setContentDescription(value.territory);

                TextView territoryValueTextView = (TextView) territoryRow.findViewById(R.id.territoryValueTextView);
                displayValue(territoryValueTextView, enforcement.type, value.value);

                layout.addView(territoryRow);
            }

            if (enforcement.territories.size() == 0) {
                LinearLayout territoryRow = (LinearLayout) this.inflater.inflate(R.layout.territory_row, layout, false);

                TextView territoryNameTextView = (TextView) territoryRow.findViewById(R.id.territoryNameTextView);
                territoryNameTextView.setText("All Territories");

                // TextView territoryValueTextView = (TextView) territoryRow.findViewById(R.id.territoryValueTextView);
                // displayValue(territoryValueTextView, standard.type, "Unavailable", null, null, null);

                layout.addView(territoryRow);
            }
        }

        private void displayValue(TextView view, String type, String enforcement) {
            String labelText = null;

            if (!enforcement.isEmpty()) {
                labelText = enforcement;
                if (labelText.contains("*")) {
                    if (this.sectionNumber == 1) {
                        this.laborFooter = true;
                    }
                    else {
                        this.criminalFooter = true;
                    }
                }

                if (type.equals("Dedicated_Labor_Inspectors") && !labelText.equals("N/A") && !labelText.contains("Unknown")) this.showDedicatedInspectors = true;
            }

            if (!labelText.isEmpty()) {
                try {
                    NumberFormat nf = NumberFormat.getInstance(Locale.getDefault());
                    double d = Float.parseFloat(labelText);
                    labelText = nf.format(d);
                    if (type.equals("Labor_Funding")) labelText = "$" + labelText;
                }
                catch (Exception ex) {}

                String accessibleText = (!labelText.contains("*")) ? labelText : (labelText.replace("*", "") + ", the Government does not make this information publicly available");

                view.setText(Html.fromHtml(labelText));
                view.setContentDescription((labelText.startsWith("N/A")) ? "Not Available" : accessibleText);
                if (!labelText.startsWith("N/A") && !labelText.startsWith("Unavailable") && !labelText.startsWith("Unknown")) {
                    view.setTextColor(Color.BLACK);
                }
            }
        }

        private void setFooter(View rootView) {
            if ((this.sectionNumber == 1 && this.laborFooter) || (this.sectionNumber == 2 && this.criminalFooter)) {
                TextView footerTextView = (TextView) rootView.findViewById(R.id.footerTextView);
                footerTextView.setVisibility(View.VISIBLE);
                footerTextView.setText("* The Government does not make this information publicly available");
            }
        }
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
            return 2;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "Labor";
                case 1:
                    return "Criminal";
            }
            return null;
        }
    }
}
