package edu.upc.eetac.dsaqt1415g3.maverick;

import android.app.ListActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import edu.upc.eetac.dsaqt1415g3.maverick.edu.upc.eetac.dsaqt1415g3.maverick.api.AppException;
import edu.upc.eetac.dsaqt1415g3.maverick.edu.upc.eetac.dsaqt1415g3.maverick.api.MaverickAPI;
import edu.upc.eetac.dsaqt1415g3.maverick.edu.upc.eetac.dsaqt1415g3.maverick.api.Song;
import edu.upc.eetac.dsaqt1415g3.maverick.edu.upc.eetac.dsaqt1415g3.maverick.api.SongsCollection;
import edu.upc.eetac.dsaqt1415g3.maverick.edu.upc.eetac.dsaqt1415g3.maverick.api.User;
import edu.upc.eetac.dsaqt1415g3.maverick.edu.upc.eetac.dsaqt1415g3.maverick.api.UsersCollection;

/**
 * Created by david on 13/01/2015.
 */
public class profileuser extends ListActivity {
    private class FetchBooksTask extends
            AsyncTask<Void, Void, UsersCollection> {
        private ProgressDialog pd;

        @Override
        protected UsersCollection doInBackground(Void... params) {
            UsersCollection users = null;
            try {
                EditText et = (EditText) findViewById(R.id.inputBook);
                users = MaverickAPI.getInstance(profileuser.this)
                        .SearchUser(et.getText().toString());
            } catch (AppException e) {
                e.printStackTrace();
            }
            return users;
        }

        @Override
        protected void onPostExecute(UsersCollection result) {
            addUsers(result);
            if (pd != null) {
                pd.dismiss();
            }
        }

        @Override
        protected void onPreExecute() {
            pd = new ProgressDialog(profileuser.this);
            pd.setTitle("Searching...");
            pd.setCancelable(false);
            pd.setIndeterminate(true);
            pd.show();
        }

    }

    private final static String TAG = search.class.toString();

    private ArrayList<User> maverickList;
    private UserAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_maverick);
    }

    public void clickMe(View v) {
        maverickList = new ArrayList<User>();
        adapter = new UserAdapter(this, maverickList);
        setListAdapter(adapter);


        (new FetchBooksTask()).execute();
    }



    private void addUsers(UsersCollection users){
        maverickList.addAll(users.getUsers());
        adapter.notifyDataSetChanged();
    }
}
