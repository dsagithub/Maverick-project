package edu.upc.eetac.dsaqt1415g3.maverick;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import edu.upc.eetac.dsaqt1415g3.maverick.edu.upc.eetac.dsaqt1415g3.maverick.api.AppException;
import edu.upc.eetac.dsaqt1415g3.maverick.edu.upc.eetac.dsaqt1415g3.maverick.api.MaverickAPI;
import edu.upc.eetac.dsaqt1415g3.maverick.edu.upc.eetac.dsaqt1415g3.maverick.api.Song;
import edu.upc.eetac.dsaqt1415g3.maverick.edu.upc.eetac.dsaqt1415g3.maverick.api.SongsCollection;

/**
 * Created by david on 13/01/2015.
 */
public class allsongs extends ListActivity {
    private class FetchBooksTask extends
            AsyncTask<Void, Void, SongsCollection> {
        private ProgressDialog pd;

        @Override
        protected SongsCollection doInBackground(Void... params) {
            SongsCollection songs = null;
            try {
                EditText et = (EditText) findViewById(R.id.inputBook);
                songs = MaverickAPI.getInstance(allsongs.this)
                        .getSongs(et.getText().toString());
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
            pd = new ProgressDialog(allsongs.this);
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
        //Song book = booksList.get(position);

        //Intent intent = new Intent(this, BookDetailActivity.class);
        //intent.putExtra("url", book.getLinks().get("self").getTarget());

        //startActivity(intent);
    }

    private void addSongs(SongsCollection songs){
        maverickList.addAll(songs.getSongs());
        adapter.notifyDataSetChanged();
    }
}
