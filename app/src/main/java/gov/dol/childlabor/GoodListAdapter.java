package gov.dol.childlabor;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import se.emilsjolander.stickylistheaders.StickyListHeadersAdapter;

/**
 * Created by tru on 10/22/2015.
 */
public class GoodListAdapter extends ArrayAdapter<Good> implements StickyListHeadersAdapter {

    private LayoutInflater inflater;
    private int sectionNumber;

    public GoodListAdapter(Context context, Good[] values, int sectionNumber) {
        super(context, R.layout.good_list_row, values);
        inflater = LayoutInflater.from(context);
        this.sectionNumber = sectionNumber;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater theInflater = LayoutInflater.from(getContext());
        View theView = theInflater.inflate(R.layout.good_list_row, parent, false);

        Good good = getItem(position);

        TextView countryTextView = (TextView) theView.findViewById(R.id.goodTextView);
        countryTextView.setText(good.getName());
        countryTextView.setContentDescription(good.getName() + ", Button");

        ImageView goodImageView = (ImageView) theView.findViewById(R.id.goodImageView);
        Drawable flag = AppHelpers.getGoodDrawable(getContext(), good.getName());
        if (flag != null) {
            goodImageView.setImageDrawable(flag);
        }
        else {
            goodImageView.setVisibility(View.GONE);
        }

        return theView;
    }

    @Override
    public View getHeaderView(int position, View convertView, ViewGroup parent) {
        HeaderViewHolder holder;
        if (convertView == null) {
            holder = new HeaderViewHolder();
            convertView = inflater.inflate(R.layout.row_section_header, parent, false);
            holder.text = (TextView) convertView.findViewById(R.id.sectionHeaderTextView);
            convertView.setTag(holder);
        } else {
            holder = (HeaderViewHolder) convertView.getTag();
        }

        //set header text as first char in name
        holder.text.setText(getHeaderText(position));
        return convertView;
    }

    @Override
    public long getHeaderId(int position) {
        long headerId;
        switch (sectionNumber) {
            case 2:
                headerId = getHeaderIdBySector(position);
                break;
            default:
                headerId = 1;
        }

        return headerId;
    }

    private long getHeaderIdBySector(int position) {
        long headerId;
        switch(getItem(position).getSectorHeader()) {
            case "Agriculture":
                headerId = 1;
                break;
            case "Manufacturing":
                headerId = 2;
                break;
            case "Mining":
                headerId = 3;
                break;
            default:
                headerId = 4;
        }

        return headerId;
    }

    private String getHeaderText(int position) {
        String headerText;

        Good good = getItem(position);
        switch (sectionNumber) {
            case 2:
                headerText = good.getSectorHeader();
                break;
            default:
                headerText = "All Goods";
                //headerText = "" + country.getName().subSequence(0, 1).charAt(0);
        }

        return headerText;
    }

    class HeaderViewHolder {
        TextView text;
    }
}