package gov.dol.childlabor.charts;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import gov.dol.childlabor.Country;
import gov.dol.childlabor.CountryGood;
import gov.dol.childlabor.CountryXmlParser;
import gov.dol.childlabor.Good;
import gov.dol.childlabor.GoodXmlParser;
import gov.dol.childlabor.R;

public class AssessmentLevelsChart extends AppCompatActivity
         {

    private PieChart chart;
    String country = "Country";
    boolean isGoodsByRegion = false;
    boolean isAssesmentLevelsByRegion = false;
    boolean isLaborInspectorMeetILOByRegion = false;
    ViewPager viewPager;
//    private Country.Enforcement labor_inspectors_intl_standards;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
          //      WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.assessment_chart_pager);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        isGoodsByRegion = getIntent().getBooleanExtra("IS_GOODS_BY_REGION",false);
        isAssesmentLevelsByRegion = getIntent().getBooleanExtra("IS_ASSESSMENT_LEVELS_BY_REGION",false);
        isLaborInspectorMeetILOByRegion = getIntent().getBooleanExtra("LABOR_INSPECTOR_MEET_ILO_BY_REGION",false);

        if (isLaborInspectorMeetILOByRegion) {
            setTitle("Labor Inspector Meet ILO");
        } else {
            setTitle("Assessment Levels By Region");
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
                Country.Enforcement labor_inspectors_intl_standards = countryList[i].enforcements.get("Labor_Inspectors_Intl_Standards");
                if(map.containsKey(countryList[i].getRegion()) && labor_inspectors_intl_standards != null) {
                    Map<String,Integer> innerData = new HashMap();
                    if (labor_inspectors_intl_standards.value.equals("Yes")) {
                        if (map.get(countryList[i].getRegion()).containsKey("Yes")) {
                            int currentValue = map.get(countryList[i].getRegion()).get("Yes");
                            map.get(countryList[i].getRegion()).put("Yes",currentValue +1);
                        }else{
                            map.get(countryList[i].getRegion()).put("Yes",1);
                        }
                    } else if (labor_inspectors_intl_standards.value.equals("No")){
                        if (map.get(countryList[i].getRegion()).containsKey("No")) {
                            int currentValue = map.get(countryList[i].getRegion()).get("No");
                            map.get(countryList[i].getRegion()).put("No",currentValue +1);
                        }else{
                            map.get(countryList[i].getRegion()).put("No",1);
                        }
                    } else if (labor_inspectors_intl_standards.value.equals("N/A")){
                        if (map.get(countryList[i].getRegion()).containsKey("N/A")) {
                            int currentValue = map.get(countryList[i].getRegion()).get("N/A");
                            map.get(countryList[i].getRegion()).put("N/A",currentValue +1);
                        }else{
                            map.get(countryList[i].getRegion()).put("N/A",1);
                        }
                    } else if (labor_inspectors_intl_standards.value.equals("Unknown")){
                        if (map.get(countryList[i].getRegion()).containsKey("Unknown")) {
                            int currentValue = map.get(countryList[i].getRegion()).get("Unknown");
                            map.get(countryList[i].getRegion()).put("Unknown",currentValue +1);
                        }else{
                            map.get(countryList[i].getRegion()).put("Unknown",1);
                        }
                    }
                } else {
                    if (labor_inspectors_intl_standards != null) {
                        Map<String,Integer> innerData = new HashMap();
                        Log.e("LEVEL" , countryList[i].getLevel() +" --->  "+i);
                        if(labor_inspectors_intl_standards.value.equals("Yes")){
                            innerData.put("Yes",1);
                        }else if(labor_inspectors_intl_standards.value.equals("No")){
                            innerData.put("No",1);
                        }else if(labor_inspectors_intl_standards.value.equals("N/A")){
                            innerData.put("N/A",1);
                        }else if(labor_inspectors_intl_standards.value.equals("Unknown")){
                            innerData.put("Unknown",1);
                        }else{
                            innerData.put(labor_inspectors_intl_standards.value,1);
                        }
                        map.put(countryList[i].getRegion(),innerData);
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
