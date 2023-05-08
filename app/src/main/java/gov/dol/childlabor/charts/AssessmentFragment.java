package gov.dol.childlabor.charts;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.DefaultValueFormatter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.listener.OnChartValueSelectedListener;
import com.github.mikephil.charting.utils.ColorTemplate;
import com.github.mikephil.charting.utils.MPPointF;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import gov.dol.childlabor.R;

public class AssessmentFragment extends Fragment implements
        OnChartValueSelectedListener {

    private PieChart chart;
    private TextView hiddenContentDescription;
    public static AssessmentFragment getInstance(Map<String, Integer> stringIntegerMap,String title){
        AssessmentFragment fragment = new AssessmentFragment();
        Bundle bundle = new Bundle();
        bundle.putSerializable("DATA", (Serializable) stringIntegerMap);
        bundle.putString("TITLE", title);
        fragment.setArguments(bundle);
        return fragment;
    }
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView =  inflater.inflate(R.layout.activity_assessment_levels,container,false);
        if (getActivity().getTitle().equals("Labor Inspector Meet ILO")) {
            ((TextView)rootView.findViewById(R.id.text)).setText(getActivity().getTitle() + " "+getArguments().getString("TITLE"));
        } else {
            ((TextView)rootView.findViewById(R.id.text)).setText("Advancement Level for "+getArguments().getString("TITLE"));
        }

        hiddenContentDescription = ((TextView)rootView.findViewById(R.id.content_description));
        chart = rootView.findViewById(R.id.chart1);
        chart.setUsePercentValues(false);
        chart.getDescription().setEnabled(false);
        chart.setExtraOffsets(5, 0, 5, 5);

        chart.setDragDecelerationFrictionCoef(0.95f);

        chart.setCenterText("");

        chart.setDrawHoleEnabled(true);
        chart.setHoleColor(Color.WHITE);

        chart.setTransparentCircleColor(Color.WHITE);
        chart.setTransparentCircleAlpha(110);

        chart.setHoleRadius(50f);
        chart.setTransparentCircleRadius(0f);

        chart.setDrawCenterText(false);

        chart.setRotationAngle(0);
        // enable rotation of the chart by touch
        chart.setRotationEnabled(true);
        chart.setHighlightPerTapEnabled(true);

        // chart.setUnit(" €");
        // chart.setDrawUnitsInChart(true);

        // add a selection listener
        chart.setOnChartValueSelectedListener(this);


        chart.animateY(1400, Easing.EaseInOutQuad);
        // chart.spin(2000, 0, 360);
        chart.setExtraBottomOffset(100);
        Legend l = chart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.BOTTOM);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.LEFT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setXEntrySpace(30f);
        l.setYEntrySpace(0f);
        l.setYOffset(-20f);

        // entry label styling
        chart.setEntryLabelColor(Color.WHITE);
        chart.setEntryLabelTextSize(16f);
        chart.setDrawEntryLabels(false);
        setData();
        return rootView;
    }

    private void setData() {
        //ArrayList<PieEntry> entries = new ArrayList<>();

        // NOTE: The order of the entries when being added to the entries array determines their position around the center of
        // the chart.
        Map<String,Integer> map = sortByValue((Map<String, Integer>) getArguments().getSerializable("DATA"));
        ArrayList<PieEntry> values = new ArrayList<>(6);
        values.add(null);
        values.add(null);
        values.add(null);
        values.add(null);
        values.add(null);
        values.add(null);
        map.remove("");
        ArrayList<Integer> colors = new ArrayList<>();
        colors.add(null);
        colors.add(null);
        colors.add(null);
        colors.add(null);
        colors.add(null);
        colors.add(null);
        for (String key :
                map.keySet()) {
            switch (key){
                case "No Advancement":
                    colors.add(3,Color.rgb(202,
                            31,
                            65));
                    values.add(3,new PieEntry(map.get(key), key));
                    break;
                case "Significant Advancement":
                    colors.add(0,Color.rgb(51,
                            128,
                            116));
                    values.add(0,new PieEntry(map.get(key), key));
                    break;
                case "Moderate Advancement":
                    colors.add(1,Color.rgb(36,
                            132,
                            21));
                    values.add(1,new PieEntry(map.get(key), key));
                    break;
                case "Minimal Advancement":
                    colors.add(2,Color.rgb(63,
                            81,
                            163));
                    values.add(2,new PieEntry(map.get(key), key));
                    break;
                case "No Assessment":
                    colors.add(4,Color.rgb(126,
                            105,
                            165));
                    values.add(4,new PieEntry(map.get(key), key));
                    break;

                case "No":
                    colors.add(3,Color.rgb(202,
                            31,
                            65));
                    values.add(3,new PieEntry(map.get(key), key));
                    break;
                case "Yes":
                    colors.add(0,Color.rgb(51,
                            128,
                            116));
                    values.add(0,new PieEntry(map.get(key), key));
                    break;
                case "Unknown":
                    colors.add(1,Color.rgb(36,
                            132,
                            21));
                    values.add(1,new PieEntry(map.get(key), key));
                    break;
                case "N/A":
                    colors.add(2,Color.rgb(63,
                            81,
                            163));
                    values.add(2,new PieEntry(map.get(key), key));
                    break;
                default:
                    colors.add(5,Color.rgb(255,0,0));
                    values.add(5,new PieEntry(map.get(key), key));
            }

        }
        values.remove(null);
        values.remove(null);
        values.remove(null);
        values.remove(null);
        values.remove(null);
        values.remove(null);

        colors.remove(null);
        colors.remove(null);
        colors.remove(null);
        colors.remove(null);
        colors.remove(null);
        colors.remove(null);
        StringBuffer buffer = new StringBuffer();
        for (int i = 0; i < values.size(); i++) {
            buffer.append((int)values.get(i).getValue() + ", " +values.get(i).getLabel() + ", ");
        }
        hiddenContentDescription.setContentDescription(buffer.toString());

        PieDataSet dataSet = new PieDataSet(values, "");

        dataSet.setDrawIcons(false);

        dataSet.setSliceSpace(3f);
        dataSet.setIconsOffset(new MPPointF(0, 40));
        dataSet.setSelectionShift(5f);

        // add a lot of colors



        /*for (int c : ColorTemplate.VORDIPLOM_COLORS)
            colors.add(c);*/


        /*for (int c : ColorTemplate.COLORFUL_COLORS)
            colors.add(c);
*/
        /*for (int c : ColorTemplate.LIBERTY_COLORS)
            colors.add(c);

        for (int c : ColorTemplate.PASTEL_COLORS)
            colors.add(c);*/

        colors.add(ColorTemplate.getHoloBlue());

        dataSet.setColors(colors);
        //dataSet.setSelectionShift(0f);

        PieData data = new PieData(dataSet);
        data.setValueFormatter(new DefaultValueFormatter(0));
        data.setValueTextSize(16f);
        data.setValueTypeface(Typeface.DEFAULT_BOLD);
        data.setValueTextColor(Color.WHITE);
        chart.setData(data);

        // undo all highlights
        chart.highlightValues(null);
        chart.invalidate();
    }
    @Override
    public void onValueSelected(Entry e, Highlight h) {

        if (e == null)
            return;
        Log.i("VAL SELECTED",
                "Value: " + e.getY() + ", index: " + h.getX()
                        + ", DataSet index: " + h.getDataSetIndex());
    }

    @Override
    public void onNothingSelected() {
        Log.i("PieChart", "nothing selected");
    }

    // function to sort hashmap by values
    private Map<String, Integer> sortByValue(Map<String, Integer> hm)
    {
        // Create a list from elements of HashMap
        List<Map.Entry<String, Integer> > list =
                new LinkedList<Map.Entry<String, Integer> >(hm.entrySet());

        // Sort the list
        Collections.sort(list, new Comparator<Map.Entry<String, Integer> >() {
            public int compare(Map.Entry<String, Integer> o1,
                               Map.Entry<String, Integer> o2)
            {
                return (o1.getValue()).compareTo(o2.getValue());
            }
        });

        // put data from sorted list to hashmap
        HashMap<String, Integer> temp = new LinkedHashMap<String, Integer>();
        for (Map.Entry<String, Integer> aa : list) {
            temp.put(aa.getKey(), aa.getValue());
        }
        return temp;
    }
}
