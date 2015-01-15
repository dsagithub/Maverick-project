package edu.upc.eetac.dsaqt1415g3.maverick;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.ArrayList;

import edu.upc.eetac.dsaqt1415g3.maverick.edu.upc.eetac.dsaqt1415g3.maverick.api.AppException;
import edu.upc.eetac.dsaqt1415g3.maverick.edu.upc.eetac.dsaqt1415g3.maverick.api.MaverickAPI;
import edu.upc.eetac.dsaqt1415g3.maverick.edu.upc.eetac.dsaqt1415g3.maverick.api.Song;
import edu.upc.eetac.dsaqt1415g3.maverick.edu.upc.eetac.dsaqt1415g3.maverick.api.SongsCollection;
import edu.upc.eetac.dsaqt1415g3.maverick.edu.upc.eetac.dsaqt1415g3.maverick.api.User;
import edu.upc.eetac.dsaqt1415g3.maverick.edu.upc.eetac.dsaqt1415g3.maverick.api.UsersCollection;

/**
 * Created by david on 09/01/2015.
 */
public class MaverickSong extends ListActivity {

    private class FetchBooksTask extends
            AsyncTask<Void, Void, SongsCollection> {
        private ProgressDialog pd;

        @Override
        protected SongsCollection doInBackground(Void... params) {
            SongsCollection songs = null;
            try {
                EditText et = (EditText) findViewById(R.id.inputBook);
                songs = MaverickAPI.getInstance(MaverickSong.this)
                        .mySongs(et.getText().toString());
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
            pd = new ProgressDialog(MaverickSong.this);
            pd.setTitle("Searching...");
            pd.setCancelable(false);
            pd.setIndeterminate(true);
            pd.show();
        }

    }

    private final static String TAG = search.class.toString();

    private ArrayList<Song> maverickList;
    private MaverickAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_maverick);
    }

    public void clickMe(View v) {
        maverickList = new ArrayList<Song>();
        adapter = new MaverickAdapter(this, maverickList);
        setListAdapter(adapter);


        (new FetchBooksTask()).execute();
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Song song = maverickList.get(position);
        Log.d(TAG, song.getLinks().get("self").getTarget());

        Intent intent = new Intent(this, detailsong.class);
        intent.putExtra("url", song.getLinks().get("self").getTarget());
        startActivity(intent);
    }

    private void addSongs(SongsCollection songs){
        maverickList.addAll(songs.getSongs());
        adapter.notifyDataSetChanged();
    }
}
