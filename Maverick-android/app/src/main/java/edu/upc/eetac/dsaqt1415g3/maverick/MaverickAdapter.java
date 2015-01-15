package edu.upc.eetac.dsaqt1415g3.maverick;

import android.content.Context;
import android.media.MediaPlayer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import edu.upc.eetac.dsaqt1415g3.maverick.R;
import edu.upc.eetac.dsaqt1415g3.maverick.edu.upc.eetac.dsaqt1415g3.maverick.api.Song;

/**
 * Created by david on 02/01/2015.
 */
public class MaverickAdapter extends BaseAdapter {
    private LayoutInflater inflater;



    private static class ViewHolder {
        TextView tvsong_name;
        TextView tvusername;

    }

    private final ArrayList<Song> data;

    public MaverickAdapter(Context context, ArrayList<Song> data) {
        super();
        inflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Object getItem(int position) {
        return data.get(position);
    }
    @Override
    public long getItemId(int position) {
        return ((Song) getItem(position)).getSongid();
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.dashboard_maverick_layout, null);
            viewHolder = new ViewHolder();
            viewHolder.tvusername = (TextView) convertView
                    .findViewById(R.id.tvusername);
            viewHolder.tvsong_name = (TextView) convertView
                    .findViewById(R.id.tvsong_name);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        String username = data.get(position).getUsername();
        String song_name = data.get(position).getSong_name();

        viewHolder.tvusername.setText(username);
        viewHolder.tvsong_name.setText(song_name);



        return convertView;
    }





}
