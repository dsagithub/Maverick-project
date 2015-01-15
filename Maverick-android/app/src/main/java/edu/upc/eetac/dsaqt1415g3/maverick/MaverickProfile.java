package edu.upc.eetac.dsaqt1415g3.maverick;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import java.net.Authenticator;
import java.net.PasswordAuthentication;

/**
 * Created by david on 11/01/2015.
 */
public class MaverickProfile extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.user_profile_maverick);


        //maverickList = new ArrayList<Song>();
        //adapter = new MaverickAdapter(this, maverickList);
        //setListAdapter(adapter);


        //(new FetchMaverickTask()).execute();
    }

}
