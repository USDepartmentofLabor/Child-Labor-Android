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
public class CountryListAdapter extends ArrayAdapter<Country> implements StickyListHeadersAdapter {
    private LayoutInflater inflater;
    private int sectionNumber;

    public CountryListAdapter(Context context, Country[] values, int sectionNumber) {
        super(context, R.layout.country_list_row, values);
        inflater = LayoutInflater.from(context);
        this.sectionNumber = sectionNumber;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater theInflater = LayoutInflater.from(getContext());
        View theView = theInflater.inflate(R.layout.country_list_row, parent, false);

        Country country = getItem(position);

        TextView countryTextView = (TextView) theView.findViewById(R.id.countryTextView);
        countryTextView.setText(country.getName());
        countryTextView.setContentDescription(country.getName() + ", Button");

        ImageView countryFlagImageView = (ImageView) theView.findViewById(R.id.countryFlagImageView);
        Drawable flag = AppHelpers.getFlagDrawable(getContext(), country.getName());

        if (flag != null) {
            countryFlagImageView.setImageDrawable(flag);
        }
        else {
            countryFlagImageView.setVisibility(View.GONE);
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
                headerId = getHeaderIdByLevel(position);
                break;
            case 3:
                headerId = getHeaderIdByRegion(position);
                break;
            default:
                headerId = getItem(position).getName().subSequence(0, 1).charAt(0);
        }

        return headerId;
    }

    private long getHeaderIdByLevel(int position) {
        return getItem(position).getLevelHeader().order;
    }

    private long getHeaderIdByRegion(int position) {
        long headerId;
        switch(getItem(position).getRegionHeader()) {
            case "Asia & the Pacific":
                headerId = 1;
                break;
            case "Europe & Eurasia":
                headerId = 2;
                break;
            case "Latin America & the Caribbean":
                headerId = 3;
                break;
            case "Middle East & North Africa":
                headerId = 4;
                break;
            case "Sub-Saharan Africa":
                headerId = 5;
                break;
            default:
                headerId = 6;
        }

        return headerId;
    }

    private String getHeaderText(int position) {
        String headerText;

        Country country = getItem(position);
        switch (sectionNumber) {
            case 2:
                headerText = country.getLevelHeader().header;
                break;
            case 3:
                headerText = country.getRegionHeader();
                break;
            default:
                headerText = "" + country.getName().subSequence(0, 1).charAt(0);
        }

        return headerText;
    }

    class HeaderViewHolder {
        TextView text;
    }
}
