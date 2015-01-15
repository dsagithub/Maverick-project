package edu.upc.eetac.dsaqt1415g3.maverick;

import android.app.Activity;
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
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.net.Authenticator;
import java.net.PasswordAuthentication;
import java.util.ArrayList;

import edu.upc.eetac.dsaqt1415g3.maverick.edu.upc.eetac.dsaqt1415g3.maverick.api.AppException;
import edu.upc.eetac.dsaqt1415g3.maverick.edu.upc.eetac.dsaqt1415g3.maverick.api.MaverickAPI;
import edu.upc.eetac.dsaqt1415g3.maverick.edu.upc.eetac.dsaqt1415g3.maverick.api.Song;
import edu.upc.eetac.dsaqt1415g3.maverick.edu.upc.eetac.dsaqt1415g3.maverick.api.SongsCollection;
import edu.upc.eetac.dsaqt1415g3.maverick.edu.upc.eetac.dsaqt1415g3.maverick.api.User;

public class MaverickMainActivity extends Activity {
    private final static String TAG = MaverickMainActivity.class.toString();
    private MaverickAdapter adapter;
    private ArrayList<Song> maverickList;
   // Button play = (Button)findViewById(R.id.btn_play);///////////////////////--------Referenciamos los Botones de la Aplicacion.
   // Button stop = (Button)findViewById(R.id.btn_stop);

    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.menu_maverick);


        //maverickList = new ArrayList<Song>();
        //adapter = new MaverickAdapter(this, maverickList);
        //setListAdapter(adapter);

        SharedPreferences prefs = getSharedPreferences("maverick-profile",
                Context.MODE_PRIVATE);
        final String username = prefs.getString("username", null);
        final String password = prefs.getString("password", null);
        TextView helloText = (TextView)findViewById(R.id.username);

        //in your OnCreate() method
       // user = (User) this.getIntent().getExtras().get("user");
        //helloText.setText("                Hello "+user.getUsername());
//.out.println(username);
  //      System.out.println(password);

        Authenticator.setDefault(new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
               return new PasswordAuthentication("david", "david"
                       .toCharArray());
            }
        });
        ;


        //(new FetchMaverickTask()).execute();
    }

    //private class FetchMaverickTask extends
          //  AsyncTask<Void, Void, SongsCollection> {
        //private ProgressDialog pd;

        //@Override
        //protected SongsCollection doInBackground(Void... params) {
           // SongsCollection songs = null;
            //try {
              //  songs = MaverickAPI.getInstance(MaverickMainActivity.this).getSongs();

            //} catch (AppException e) {
              //  e.printStackTrace();
            //}
            //return songs;
        //}

        //@Override
        //protected void onPostExecute(SongsCollection result) {
          //  addSongs(result);
            //if (pd != null) {
              //  pd.dismiss();
            //}
       // }

        //@Override
       // protected void onPreExecute() {
         //   pd = new ProgressDialog(MaverickMainActivity.this);
           // pd.setTitle("Searching...");
           // pd.setCancelable(false);
            //pd.setIndeterminate(true);
            //pd.show();
       // }

    //}

    //@Override
    //public boolean onCreateOptionsMenu(Menu menu) {
       // MenuInflater inflater = getMenuInflater();
        //inflater.inflate(R.menu.menu_maverick_main, menu);
        //return true;
    //}

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       switch (item.getItemId()) {
            case R.id.action_logout:
               SharedPreferences prefs = getSharedPreferences("maverick-profile",
                        Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = prefs.edit();
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
    public void startMaverickProfile(View v){
        Intent intent = new Intent(this, profileuser.class);
        startActivity(intent);

    }
    public void startMaverickSong(View v){
        Intent intent = new Intent(this, MaverickSong.class);
        startActivity(intent);

    }
    public void starttoplistActivity(View v){
        Intent intent = new Intent(this, TopLisMaverick.class);
        startActivity(intent);

    }
    public void startSearchActivity(View v){
        Intent intent = new Intent(this, search.class);
        startActivity(intent);

    }
   // @Override
    //protected void onListItemClick(ListView l, View v, int position, long id) {
//        Game game = gamesList.get(position);
//        Log.d(TAG, game.getLinks().get("self").getTarget());
//
//        Intent intent = new Intent(this, GameDetailActivity.class);
//        intent.putExtra("url", game.getLinks().get("self").getTarget());
//        startActivity(intent);
    //}

  //  private void addSongs(SongsCollection songs){
    //    maverickList.addAll(songs.getSongs());
      //  adapter.notifyDataSetChanged();
    //}
}