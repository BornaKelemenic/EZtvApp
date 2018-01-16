package hr.diehardzg.eztvapp.adapter;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;

import hr.diehardzg.eztvapp.R;
import hr.diehardzg.eztvapp.serija.Serija;

public class ShowListAdapter extends ArrayAdapter<Serija> implements Filterable
{
    private static final String TAG = "ShowListAdapter";

    private Context context;
    private int resource;
    private ArrayList<Serija> originalData;
    private ArrayList<Serija> filteredData = null;

    private ItemFilter myFilter;

    public ShowListAdapter(Context context, int resource, ArrayList<Serija> objects)
    {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
        this.originalData = objects;
        this.filteredData = objects;

        myFilter = new ItemFilter();
    }

    public ArrayList<Serija> getFilteredData()
    {
        return filteredData;
    }

    @Override
    public int getCount()
    {
        return filteredData.size();
    }

    @Override
    public Serija getItem(int position)
    {
        return filteredData.get(position);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        String name = filteredData.get(position).getName();
        String status = filteredData.get(position).getStatus();
        String rating = filteredData.get(position).getRating();

        ViewHolder holder;
        final View result;

        if(convertView == null)
        {
            convertView = LayoutInflater.from(this.context).inflate(this.resource, parent, false);

            holder = new ViewHolder();
            holder.tvName = (TextView) convertView.findViewById(R.id.showName);
            holder.tvStatus = (TextView) convertView.findViewById(R.id.status);
            holder.tvRating = (TextView) convertView.findViewById(R.id.rating);

            result = convertView;

            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
            result = convertView;
        }

        holder.tvName.setText(name);
        holder.tvStatus.setText("Status: " + status);
        holder.tvRating.setText("Rating: " + rating);

        return convertView;
    }

    private static class ViewHolder
    {
        TextView tvName;
        TextView tvStatus;
        TextView tvRating;
    }

    @Override
    public Filter getFilter()
    {
        //Log.d(TAG, "getFilter: CALLED");
        return myFilter;
    }

    private class ItemFilter extends Filter
    {
        @Override
        protected FilterResults performFiltering(CharSequence constraint)
        {
            String filterString = constraint.toString().toLowerCase();

            FilterResults results = new FilterResults();

            final ArrayList<Serija> list = originalData;

            int count = list.size();
            final ArrayList<Serija> nlist = new ArrayList<>(count);

            String filterableString;

            for(int i = 0; i < count; i++)
            {
                filterableString = list.get(i).getName();
                if(filterableString.toLowerCase().contains(filterString))
                {
                    nlist.add(list.get(i));
                }
            }

            results.values = nlist;
            results.count = nlist.size();

            return results;
        }

        @SuppressWarnings ("unchecked")
        @Override
        protected void publishResults(CharSequence contraint, FilterResults results)
        {
            filteredData = (ArrayList<Serija>) results.values;
            if(results.count > 0)
            {
                notifyDataSetChanged();
            }
            else
            {
                notifyDataSetInvalidated();
            }
        }
    }
}
