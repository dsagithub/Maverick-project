package edu.upc.eetac.dsaqt1415g3.maverick;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.ArrayList;

import edu.upc.eetac.dsaqt1415g3.maverick.edu.upc.eetac.dsaqt1415g3.maverick.api.AppException;
import edu.upc.eetac.dsaqt1415g3.maverick.edu.upc.eetac.dsaqt1415g3.maverick.api.MaverickAPI;
import edu.upc.eetac.dsaqt1415g3.maverick.edu.upc.eetac.dsaqt1415g3.maverick.api.Song;
import edu.upc.eetac.dsaqt1415g3.maverick.edu.upc.eetac.dsaqt1415g3.maverick.api.SongsCollection;

public class MaverickMainActivity extends ListActivity {
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

        SharedPreferences prefs = getSharedPreferences("maverick-profile",
                Context.MODE_PRIVATE);
        final String username = prefs.getString("username", null);
        final String password = prefs.getString("password", null);



        Authenticator.setDefault(new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication("david", "david"
                        .toCharArray());
            }
        });
        (new FetchMaverickTask()).execute();
    }

    private class FetchMaverickTask extends
            AsyncTask<Void, Void, SongsCollection> {
        private ProgressDialog pd;

        @Override
        protected SongsCollection doInBackground(Void... params) {
            SongsCollection songs = null;
            try {
                songs = MaverickAPI.getInstance(MaverickMainActivity.this).getSongs();

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
            pd = new ProgressDialog(MaverickMainActivity.this);
            pd.setTitle("Searching...");
            pd.setCancelable(false);
            pd.setIndeterminate(true);
            pd.show();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_maverick_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_logout:
               SharedPreferences prefs = getSharedPreferences("maverick-profile",
                        Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit(); //Esto siempre se hace así -> obtener editor + clear
                editor.clear();
                editor.commit();
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
                return true;


            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
//        Game game = gamesList.get(position);
//        Log.d(TAG, game.getLinks().get("self").getTarget());
//
//        Intent intent = new Intent(this, GameDetailActivity.class);
//        intent.putExtra("url", game.getLinks().get("self").getTarget());
//        startActivity(intent);
    }

    private void addSongs(SongsCollection songs){
        maverickList.addAll(songs.getSongs());
        adapter.notifyDataSetChanged();
    }
}