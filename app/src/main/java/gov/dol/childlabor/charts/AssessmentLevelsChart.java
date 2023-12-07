package gov.dol.childlabor.charts;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.github.mikephil.charting.charts.PieChart;
import com.google.android.material.tabs.TabLayout;

import java.util.HashMap;
import java.util.Map;

import gov.dol.childlabor.Country;
import gov.dol.childlabor.CountryXmlParser;
import gov.dol.childlabor.R;

public class AssessmentLevelsChart extends AppCompatActivity
         {

    private PieChart chart;
    String country = "Country";
    boolean isGoodsByRegion = false;
    boolean isAssesmentLevelsByRegion = false;
    boolean isLaborInspectorMeetILOByRegion = false;
    ViewPager viewPager;
    TextView toolbarTextView;
//    private Country.Enforcement labor_inspectors_intl_standards;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
          //      WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.assessment_chart_pager);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        findViewById(R.id.toolbar_back_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        toolbarTextView = toolbar.findViewById(R.id.toolbar_title_details);
        isGoodsByRegion = getIntent().getBooleanExtra("IS_GOODS_BY_REGION",false);
        isAssesmentLevelsByRegion = getIntent().getBooleanExtra("IS_ASSESSMENT_LEVELS_BY_REGION",false);
        isLaborInspectorMeetILOByRegion = getIntent().getBooleanExtra("LABOR_INSPECTOR_MEET_ILO_BY_REGION",false);

        if (isLaborInspectorMeetILOByRegion) {
            setTitle("Adequate Number of Labor Inspectors"); // Need this as its checked in AssessmentFragment
            toolbarTextView.setText("Adequate Number of Labor Inspectors");
        } else {
            toolbarTextView.setText("Assessment Levels By Region");
        }
        Map<String,Map<String,Integer>> map = new HashMap<>();
        if(isAssesmentLevelsByRegion){
            CountryXmlParser countryXmlParser = CountryXmlParser.fromContext(this);
            Country[] countryList = countryXmlParser.getCountryList();
            for (int i = 0; i < countryList.length; i++) {
                if(map.containsKey(countryList[i].getRegion())){
                    Log.e("LEVEL" , countryList[i].getLevel() +" --->  "+i);
                    if(countryList[i].getLevel().contains("Not Covered in TDA Report")){

                    }else if(countryList[i].getLevel().contains("Minimal Advancement")){
                        if(map.get(countryList[i].getRegion()).containsKey("Minimal Advancement")){
                            int currentValue = map.get(countryList[i].getRegion()).get("Minimal Advancement");
                            map.get(countryList[i].getRegion()).put("Minimal Advancement",currentValue +1);
                        }else{
                            map.get(countryList[i].getRegion()).put("Minimal Advancement",1);
                        }
                    }else if(countryList[i].getLevel().contains("No Advancement")){
                        if(map.get(countryList[i].getRegion()).containsKey("No Advancement")){
                            int currentValue = map.get(countryList[i].getRegion()).get("No Advancement");
                            map.get(countryList[i].getRegion()).put("No Advancement",currentValue +1);
                        }else{
                            map.get(countryList[i].getRegion()).put("No Advancement",1);
                        }
                    }else{
                        if(map.get(countryList[i].getRegion()).containsKey(countryList[i].getLevel())){
                            int currentValue = map.get(countryList[i].getRegion()).get(countryList[i].getLevel());
                            map.get(countryList[i].getRegion()).put(countryList[i].getLevel(),currentValue +1);
                        }else{
                            map.get(countryList[i].getRegion()).put(countryList[i].getLevel(),1);
                        }
                    }

                }else{
                    Map<String,Integer> innerData = new HashMap();
                    Log.e("LEVEL" , countryList[i].getLevel() +" --->  "+i);
                    if(countryList[i].getLevel().contains("Not Covered in TDA Report")){

                    }else if(countryList[i].getLevel().contains("Minimal Advancement")){
                        innerData.put("Minimal Advancement",1);
                    }else if(countryList[i].getLevel().contains("No Advancement")){
                        innerData.put("No Advancement",1);
                    }else{
                        innerData.put(countryList[i].getLevel(),1);
                    }
                    map.put(countryList[i].getRegion(),innerData);
                }
            }
            map.remove(null);
            Log.e("Data",map.toString());

        } else if (isLaborInspectorMeetILOByRegion) {
            CountryXmlParser countryXmlParser = CountryXmlParser.fromContext(this);
            Country[] countryList = countryXmlParser.getCountryList();

            for (int i = 0; i < countryList.length; i++) {
                String labor_inspectors_intl_standards = "";
                if (countryList[i].hasMultipleTerritories) {
                    labor_inspectors_intl_standards = countryList[i].territoryEnforcements.get("Labor_Inspectors_Intl_Standards").territories.get(0).value;
                } else {
                    if (countryList[i].enforcements.get("Labor_Inspectors_Intl_Standards") != null) {
                        labor_inspectors_intl_standards = countryList[i].enforcements.get("Labor_Inspectors_Intl_Standards").value.toString();
                    }

                }

                Log.e("Data", String.valueOf(labor_inspectors_intl_standards));
                if(map.containsKey(countryList[i].getRegion()) && labor_inspectors_intl_standards != null && labor_inspectors_intl_standards != "") {
                    Map<String,Integer> innerData = new HashMap();
                    String data = "";

                    if (labor_inspectors_intl_standards.equals("Yes")) {
                        if (map.get(countryList[i].getRegion()).containsKey("Yes")) {
                            int currentValue = map.get(countryList[i].getRegion()).get("Yes");
                            map.get(countryList[i].getRegion()).put("Yes",currentValue +1);
                        }else{
                            map.get(countryList[i].getRegion()).put("Yes",1);
                        }
                    } else if (labor_inspectors_intl_standards.equals("No")){
                        if (map.get(countryList[i].getRegion()).containsKey("No")) {
                            int currentValue = map.get(countryList[i].getRegion()).get("No");
                            map.get(countryList[i].getRegion()).put("No",currentValue +1);
                        }else{
                            map.get(countryList[i].getRegion()).put("No",1);
                        }
                    } else if (labor_inspectors_intl_standards.equals("N/A")){
                        if (map.get(countryList[i].getRegion()).containsKey("N/A")) {
                            int currentValue = map.get(countryList[i].getRegion()).get("N/A");
                            map.get(countryList[i].getRegion()).put("N/A",currentValue +1);
                        }else{
                            map.get(countryList[i].getRegion()).put("N/A",1);
                        }
                    } else if (labor_inspectors_intl_standards.equals("Unknown")){
                        if (map.get(countryList[i].getRegion()).containsKey("Unknown")) {
                            int currentValue = map.get(countryList[i].getRegion()).get("Unknown");
                            map.get(countryList[i].getRegion()).put("Unknown",currentValue +1);
                        }else{
                            map.get(countryList[i].getRegion()).put("Unknown",1);
                        }
                    }
                } else {
                    if (labor_inspectors_intl_standards != null && labor_inspectors_intl_standards != "") {
                        Map<String,Integer> innerData = new HashMap();
                        Log.e("LEVEL" , countryList[i].getLevel() +" --->  "+i);
                        if(labor_inspectors_intl_standards.equals("Yes")){
                            innerData.put("Yes",1);
                        }else if(labor_inspectors_intl_standards.equals("No")){
                            innerData.put("No",1);
                        }else if(labor_inspectors_intl_standards.equals("N/A")){
                            innerData.put("N/A",1);
                        }else if(labor_inspectors_intl_standards.equals("Unknown")){
                            innerData.put("Unknown",1);
                        }else{
                            innerData.put(labor_inspectors_intl_standards,1);
                        }
                        map.put(countryList[i].getRegion(),innerData);
                    } else {
                        Log.e("LEVEL" , countryList[i].getLevel() +" --->  "+i);
                    }

                }
            }
            map.remove(null);
            Log.e("Data",map.toString());
        }
        viewPager = findViewById(R.id.view_pager);
        TabLayout tabLayout = (TabLayout)findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(viewPager, true);
        viewPager.setAdapter(new AssessmentLevelsAdapter(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT,map));

    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
