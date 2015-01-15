package edu.upc.eetac.dsaqt1415g3.maverick;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import edu.upc.eetac.dsaqt1415g3.maverick.edu.upc.eetac.dsaqt1415g3.maverick.api.AppException;
import edu.upc.eetac.dsaqt1415g3.maverick.edu.upc.eetac.dsaqt1415g3.maverick.api.MaverickAPI;
import edu.upc.eetac.dsaqt1415g3.maverick.edu.upc.eetac.dsaqt1415g3.maverick.api.Song;

/**
 * Created by david on 12/01/2015.
 */
public class detailsong extends Activity {

    private final static String TAG = detailsong.class.getName();
    private class FetchStingTask extends AsyncTask<String, Void, Song> {
        private ProgressDialog pd;

        @Override
        protected Song doInBackground(String... params) {
            Song sting = null;
            try {
                sting = MaverickAPI.getInstance(detailsong.this)
                        .getSong(params[0]);
            } catch (AppException e) {
                Log.d(TAG, e.getMessage(), e);
            }
            return sting;
        }

        @Override
        protected void onPostExecute(Song result) {
            loadSong(result);
            if (pd != null) {
                pd.dismiss();
            }
        }

        @Override
        protected void onPreExecute() {
            pd = new ProgressDialog(detailsong.this);
            pd.setTitle("Loading...");
            pd.setCancelable(false);
            pd.setIndeterminate(true);
            pd.show();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_song);
        String urlSting = (String) getIntent().getExtras().get("url");
        (new FetchStingTask()).execute(urlSting);
    }


    private void loadSong(Song song) {

        TextView tvDetailSongName = (TextView) findViewById(R.id.tvDetailSongName);
       TextView tvDetailUsername = (TextView) findViewById(R.id.tvDetailUsername);
        TextView tvDetailAlbum = (TextView) findViewById(R.id.tvDetailAlbum);
        TextView tvDetailDescription = (TextView) findViewById(R.id.tvDetailDescription);
        TextView tvDetailStyle = (TextView) findViewById(R.id.tvDetailStyle);
       // TextView tvDetailLikes = (TextView) findViewById(R.id.tvDetailLikes);



        tvDetailSongName.setText(song.getSong_name());
       // System.out.println(tvDetailSongName);
        //System.out.println(song.getSong_name());
        tvDetailUsername.setText(song.getUsername());
        tvDetailAlbum.setText(song.getAlbum());
       tvDetailDescription.setText(song.getDescription());
       tvDetailStyle.setText(song.getStyle());
        //tvDetailLikes.setText(song.getLikes());

    }
}
