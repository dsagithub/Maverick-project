package edu.upc.eetac.dsaqt1415g3.maverick;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

import edu.upc.eetac.dsaqt1415g3.maverick.edu.upc.eetac.dsaqt1415g3.maverick.api.AppException;
import edu.upc.eetac.dsaqt1415g3.maverick.edu.upc.eetac.dsaqt1415g3.maverick.api.MaverickAPI;
import edu.upc.eetac.dsaqt1415g3.maverick.edu.upc.eetac.dsaqt1415g3.maverick.api.Song;
import edu.upc.eetac.dsaqt1415g3.maverick.edu.upc.eetac.dsaqt1415g3.maverick.api.SongsCollection;

/**
 * Created by david on 12/01/2015.
 */
public class edm extends ListActivity {
    private final static String TAG = MaverickMainActivity.class.toString();
    private MaverickAdapter adapter;
    private ArrayList<Song> maverickList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.maverick_main);


        maverickList = new ArrayList<Song>();
        adapter = new MaverickAdapter(this, maverickList);
        setListAdapter(adapter);
        (new FetchMaverickTask()).execute();
    }

    private class FetchMaverickTask extends
            AsyncTask<Void, Void, SongsCollection> {
        private ProgressDialog pd;

        @Override
        protected SongsCollection doInBackground(Void... params) {

            SongsCollection songs = null;
            try {
                songs = MaverickAPI.getInstance(edm.this).StyleSongsedm();

            } catch (AppException e) {
                e.printStackTrace();
            }
            return songs;
        }

        @Override
        protected void onPostExecute(SongsCollection result) {
            addSongs(result);
            if (pd != null) {
                pd.dismiss();
            }
        }

        @Override
        protected void onPreExecute() {
            pd = new ProgressDialog(edm.this);
            pd.setTitle("Searching...");
            pd.setCancelable(false);
            pd.setIndeterminate(true);
            pd.show();
        }

    }
    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Song song = maverickList.get(position);
        Log.d(TAG, song.getLinks().get("self").getTarget());

        Intent intent = new Intent(this, detailsong.class);
        intent.putExtra("url", song.getLinks().get("self").getTarget());
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_maverick_main, menu);
        return true;
    }
    private void addSongs(SongsCollection songs){
        maverickList.addAll(songs.getSongs());
        adapter.notifyDataSetChanged();
    }
}
