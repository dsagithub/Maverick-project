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
import edu.upc.eetac.dsaqt1415g3.maverick.edu.upc.eetac.dsaqt1415g3.maverick.api.User;

/**
 * Created by david on 14/01/2015.
 */
public class profiledetail extends Activity {
    private final static String TAG = profiledetail.class.getName();
    private class FetchStingTask extends AsyncTask<String, Void, User> {
        private ProgressDialog pd;

        @Override
        protected User doInBackground(String... params) {
            User sting = null;
            try {
                sting = MaverickAPI.getInstance(profiledetail.this)
                        .getUser(params[0]);
            } catch (AppException e) {
                Log.d(TAG, e.getMessage(), e);
            }
            return sting;
        }

        @Override
        protected void onPostExecute(User result) {
            loadUser(result);
            if (pd != null) {
                pd.dismiss();
            }
        }

        @Override
        protected void onPreExecute() {
            pd = new ProgressDialog(profiledetail.this);
            pd.setTitle("Loading...");
            pd.setCancelable(false);
            pd.setIndeterminate(true);
            pd.show();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile_maverick);
        String urlSting = (String) getIntent().getExtras().get("url");
        (new FetchStingTask()).execute(urlSting);
    }


    private void loadUser(User user) {

        TextView tvDetailUserName = (TextView) findViewById(R.id.tvDetailUserName);
        TextView tvDetailName = (TextView) findViewById(R.id.tvDetailName);
        TextView tvDetailDescription = (TextView) findViewById(R.id.tvDetailDescription);



        tvDetailUserName.setText(user.getUsername());
        tvDetailName.setText(user.getName());



        tvDetailDescription.setText(user.getDescription());



    }
}
