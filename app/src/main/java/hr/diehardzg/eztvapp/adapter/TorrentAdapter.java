package hr.diehardzg.eztvapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import hr.diehardzg.eztvapp.R;
import hr.diehardzg.eztvapp.torrent.Torrent;

public class TorrentAdapter extends ArrayAdapter<Torrent>
{
    private static final String TAG = "TorrentAdapter";

    private Context context;
    private int resource;

    public TorrentAdapter(Context context, int resource, List<Torrent> objects)
    {
        super(context, resource, objects);
        this.context = context;
        this.resource = resource;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent)
    {
        String name = getItem(position).getName();
        String magnet = getItem(position).getMagnet();
        String torrent = getItem(position).getTorrent();
        String size = getItem(position).getSize();
        String seederi = getItem(position).getSeederi();
        String relesed = getItem(position).getRelesed();

        ViewHolder holder;

        if(convertView == null)
        {
            convertView = LayoutInflater.from(this.context).inflate(this.resource, parent, false);

            holder = new ViewHolder();

            holder.tvName = (TextView) convertView.findViewById(R.id.torrentName);
            holder.tvSeeds = (TextView) convertView.findViewById(R.id.seederi);
            holder.tvSize = (TextView) convertView.findViewById(R.id.size);
            holder.tvRelesed = (TextView) convertView.findViewById(R.id.relesed);

            convertView.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tvName.setText(name);
        holder.tvSeeds.setText("Seeds: " + seederi);
        holder.tvSize.setText(size);
        holder.tvRelesed.setText(relesed);

        return convertView;
    }

    private static class ViewHolder
    {
        TextView tvName;
        TextView tvSeeds;
        TextView tvSize;
        TextView tvRelesed;
    }
}
