package gov.dol.childlabor.charts;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class AssessmentLevelsAdapter extends FragmentPagerAdapter {
    Map<String, Map<String,Integer>> map;
    List<String> list;
    public AssessmentLevelsAdapter(@NonNull FragmentManager fm, int behavior, Map<String, Map<String,Integer>> map) {
        super(fm, behavior);
        this.map = map;
        this.list = new ArrayList<>(map.keySet());

    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        return AssessmentFragment.getInstance(map.get(list.get(position)),list.get(position));
    }

    @Override
    public int getCount() {
        return list.size();
    }
}
