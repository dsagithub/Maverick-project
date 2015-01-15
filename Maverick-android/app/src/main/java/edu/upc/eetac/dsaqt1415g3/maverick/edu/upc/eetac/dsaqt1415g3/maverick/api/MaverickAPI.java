package edu.upc.eetac.dsaqt1415g3.maverick.edu.upc.eetac.dsaqt1415g3.maverick.api;

import android.content.Context;
import android.content.res.AssetManager;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * Created by david on 02/01/2015.
 */
public class MaverickAPI {
    private final static String TAG = MaverickAPI.class.getName();
    private static MaverickAPI instance = null;
    private URL url;


    private MaverickRootAPI rootAPI = null;

    private MaverickAPI(Context context) throws IOException, AppException {
        super();

        AssetManager assetManager = context.getAssets();
        Properties config = new Properties();
        config.load(assetManager.open("config.properties"));
        String urlHome = config.getProperty("maverick.home");
        url = new URL(urlHome);
       // System.out.println("Miramos el fichero");

        Log.d("LINKS", url.toString());
        getRootAPI();
    }

    public final static MaverickAPI getInstance(Context context) throws AppException {
        if (instance == null)
            try {
                instance = new MaverickAPI(context);
            } catch (IOException e) {
                throw new AppException(
                        "Can't load configuration file");
            }
        return instance;
    }

    private void getRootAPI() throws AppException {
        Log.d(TAG, "getRootAPI()");
        rootAPI = new MaverickRootAPI();
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoInput(true);
            urlConnection.connect();
        } catch (IOException e) {
            throw new AppException(
                    "Can't connect to Maverick API Web Service");
        }

        BufferedReader reader;
        try {
            reader = new BufferedReader(new InputStreamReader(
                    urlConnection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line); //Guardar la respuesta en un StringBuilder (es un JSON)
            }

            JSONObject jsonObject = new JSONObject(sb.toString()); //Se procesa el JSON de respuesta
            JSONArray jsonLinks = jsonObject.getJSONArray("links");
            parseLinks(jsonLinks, rootAPI.getLinks());
        } catch (IOException e) {
            throw new AppException(
                    "Can't get response from Maverick API Web Service");
        } catch (JSONException e) {
            throw new AppException("Error parsing Maverick Root API");
        }

    }

    public SongsCollection getSongs(String username) throws AppException {
        Log.d(TAG, "getSongs()");
        SongsCollection songs = new SongsCollection();

        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) new URL(rootAPI.getLinks()
                    .get("songs").getTarget() +"/" + username).openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoInput(true);
            urlConnection.connect();
        } catch (IOException e) {
            throw new AppException(
                    "Can't connect to Maverick API Web Service");
        }

        BufferedReader reader;
        try {
            reader = new BufferedReader(new InputStreamReader(
                    urlConnection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            JSONObject jsonObject = new JSONObject(sb.toString());
            JSONArray jsonLinks = jsonObject.getJSONArray("links");
            parseLinks(jsonLinks, songs.getLinks());

            JSONArray jsonComments = jsonObject.getJSONArray("songs");
            for (int i = 0; i < jsonComments.length(); i++) {
                Song song = new Song();
                JSONObject jsonComment = jsonComments.getJSONObject(i);

                song.setSong_name(jsonComment.getString("song_name"));
                song.setUsername(jsonComment.getString("username"));

                jsonLinks = jsonComment.getJSONArray("links");
                parseLinks(jsonLinks, song.getLinks());
                songs.getSongs().add(song);
            }
        } catch (IOException e) {
            throw new AppException(
                    "Can't get response from Maverick API Web Service");
        } catch (JSONException e) {
            throw new AppException("Error parsing Maverick Root API");
        }

        return songs;
    }

    private Map<String, Song> songsCache = new HashMap<String, Song>();



    public SongsCollection mySongs(String username) throws AppException {
        Log.d(TAG, "mySongs()");
        SongsCollection songs = new SongsCollection();
//System.out.println(username);

        //User users = new User();
        //System.out.println( "hola " + users.getUsername());
        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) new URL(rootAPI.getLinks()
                    .get("songs").getTarget() + "/search2?username=" + username ).openConnection();
            Log.d(TAG, String.valueOf(urlConnection));
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoInput(true);
            urlConnection.connect();
        } catch (IOException e) {
            throw new AppException(
                    "Can't connect to Maverick API Web Service");
        }

        BufferedReader reader;
        try {
            reader = new BufferedReader(new InputStreamReader(
                    urlConnection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            JSONObject jsonObject = new JSONObject(sb.toString());
            JSONArray jsonLinks = jsonObject.getJSONArray("links");
            parseLinks(jsonLinks, songs.getLinks());

            JSONArray jsonComments = jsonObject.getJSONArray("songs");
            for (int i = 0; i < jsonComments.length(); i++) {
                Song song = new Song();
                JSONObject jsonComment = jsonComments.getJSONObject(i);

                song.setSong_name(jsonComment.getString("song_name"));
                song.setUsername(jsonComment.getString("username"));

                jsonLinks = jsonComment.getJSONArray("links");
                parseLinks(jsonLinks, song.getLinks());
                songs.getSongs().add(song);
            }
        } catch (IOException e) {
            throw new AppException(
                    "Can't get response from Maverick API Web Service");
        } catch (JSONException e) {
            throw new AppException("Error parsing Maverick Root API");
        }

        return songs;
    }

    public SongsCollection StyleSongsRock() throws AppException {
        Log.d(TAG, "StyleSongsRock()");
        SongsCollection songs = new SongsCollection();

        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) new URL(rootAPI.getLinks()
                    .get("songs").getTarget() + "/style?style=rock"  ).openConnection();
            Log.d(TAG, String.valueOf(urlConnection));
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoInput(true);
            urlConnection.connect();
        } catch (IOException e) {
            throw new AppException(
                    "Can't connect to Maverick API Web Service");
        }

        BufferedReader reader;
        try {
            reader = new BufferedReader(new InputStreamReader(
                    urlConnection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            JSONObject jsonObject = new JSONObject(sb.toString());
            JSONArray jsonLinks = jsonObject.getJSONArray("links");
            parseLinks(jsonLinks, songs.getLinks());

            JSONArray jsonComments = jsonObject.getJSONArray("songs");
            for (int i = 0; i < jsonComments.length(); i++) {
                Song song = new Song();
                JSONObject jsonComment = jsonComments.getJSONObject(i);

                song.setSong_name(jsonComment.getString("song_name"));
                song.setUsername(jsonComment.getString("username"));

                jsonLinks = jsonComment.getJSONArray("links");
                parseLinks(jsonLinks, song.getLinks());
                songs.getSongs().add(song);
            }
        } catch (IOException e) {
            throw new AppException(
                    "Can't get response from Maverick API Web Service");
        } catch (JSONException e) {
            throw new AppException("Error parsing Maverick Root API");
        }

        return songs;
    }
    public SongsCollection StyleSongsIndie() throws AppException {
        Log.d(TAG, "StyleSongsIndie()");
        SongsCollection songs = new SongsCollection();

        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) new URL(rootAPI.getLinks()
                    .get("songs").getTarget() + "/style?style=indie"  ).openConnection();
            Log.d(TAG, String.valueOf(urlConnection));
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoInput(true);
            urlConnection.connect();
        } catch (IOException e) {
            throw new AppException(
                    "Can't connect to Maverick API Web Service");
        }

        BufferedReader reader;
        try {
            reader = new BufferedReader(new InputStreamReader(
                    urlConnection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            JSONObject jsonObject = new JSONObject(sb.toString());
            JSONArray jsonLinks = jsonObject.getJSONArray("links");
            parseLinks(jsonLinks, songs.getLinks());

            JSONArray jsonComments = jsonObject.getJSONArray("songs");
            for (int i = 0; i < jsonComments.length(); i++) {
                Song song = new Song();
                JSONObject jsonComment = jsonComments.getJSONObject(i);

                song.setSong_name(jsonComment.getString("song_name"));
                song.setUsername(jsonComment.getString("username"));

                jsonLinks = jsonComment.getJSONArray("links");
                parseLinks(jsonLinks, song.getLinks());
                songs.getSongs().add(song);
            }
        } catch (IOException e) {
            throw new AppException(
                    "Can't get response from Maverick API Web Service");
        } catch (JSONException e) {
            throw new AppException("Error parsing Maverick Root API");
        }

        return songs;
    }
    public SongsCollection StyleSongsPop() throws AppException {
        Log.d(TAG, "StyleSongsPop()");
        SongsCollection songs = new SongsCollection();

        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) new URL(rootAPI.getLinks()
                    .get("songs").getTarget() + "/style?style=pop"  ).openConnection();
            Log.d(TAG, String.valueOf(urlConnection));
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoInput(true);
            urlConnection.connect();
        } catch (IOException e) {
            throw new AppException(
                    "Can't connect to Maverick API Web Service");
        }

        BufferedReader reader;
        try {
            reader = new BufferedReader(new InputStreamReader(
                    urlConnection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            JSONObject jsonObject = new JSONObject(sb.toString());
            JSONArray jsonLinks = jsonObject.getJSONArray("links");
            parseLinks(jsonLinks, songs.getLinks());

            JSONArray jsonComments = jsonObject.getJSONArray("songs");
            for (int i = 0; i < jsonComments.length(); i++) {
                Song song = new Song();
                JSONObject jsonComment = jsonComments.getJSONObject(i);

                song.setSong_name(jsonComment.getString("song_name"));
                song.setUsername(jsonComment.getString("username"));

                jsonLinks = jsonComment.getJSONArray("links");
                parseLinks(jsonLinks, song.getLinks());
                songs.getSongs().add(song);
            }
        } catch (IOException e) {
            throw new AppException(
                    "Can't get response from Maverick API Web Service");
        } catch (JSONException e) {
            throw new AppException("Error parsing Maverick Root API");
        }

        return songs;
    }
    public SongsCollection StyleSongsPachangeo() throws AppException {
        Log.d(TAG, "StyleSongsPachangeo()");
        SongsCollection songs = new SongsCollection();

        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) new URL(rootAPI.getLinks()
                    .get("songs").getTarget() + "/style?style=pachangeo"  ).openConnection();
            Log.d(TAG, String.valueOf(urlConnection));
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoInput(true);
            urlConnection.connect();
        } catch (IOException e) {
            throw new AppException(
                    "Can't connect to Maverick API Web Service");
        }

        BufferedReader reader;
        try {
            reader = new BufferedReader(new InputStreamReader(
                    urlConnection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            JSONObject jsonObject = new JSONObject(sb.toString());
            JSONArray jsonLinks = jsonObject.getJSONArray("links");
            parseLinks(jsonLinks, songs.getLinks());

            JSONArray jsonComments = jsonObject.getJSONArray("songs");
            for (int i = 0; i < jsonComments.length(); i++) {
                Song song = new Song();
                JSONObject jsonComment = jsonComments.getJSONObject(i);

                song.setSong_name(jsonComment.getString("song_name"));
                song.setUsername(jsonComment.getString("username"));

                jsonLinks = jsonComment.getJSONArray("links");
                parseLinks(jsonLinks, song.getLinks());
                songs.getSongs().add(song);
            }
        } catch (IOException e) {
            throw new AppException(
                    "Can't get response from Maverick API Web Service");
        } catch (JSONException e) {
            throw new AppException("Error parsing Maverick Root API");
        }

        return songs;
    }
    public SongsCollection StyleSongsedm() throws AppException {
        Log.d(TAG, "StyleSongsedm()");
        SongsCollection songs = new SongsCollection();

        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) new URL(rootAPI.getLinks()
                    .get("songs").getTarget() + "/style?style=edm"  ).openConnection();
            Log.d(TAG, String.valueOf(urlConnection));
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoInput(true);
            urlConnection.connect();
        } catch (IOException e) {
            throw new AppException(
                    "Can't connect to Maverick API Web Service");
        }

        BufferedReader reader;
        try {
            reader = new BufferedReader(new InputStreamReader(
                    urlConnection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            JSONObject jsonObject = new JSONObject(sb.toString());
            JSONArray jsonLinks = jsonObject.getJSONArray("links");
            parseLinks(jsonLinks, songs.getLinks());

            JSONArray jsonComments = jsonObject.getJSONArray("songs");
            for (int i = 0; i < jsonComments.length(); i++) {
                Song song = new Song();
                JSONObject jsonComment = jsonComments.getJSONObject(i);

                song.setSong_name(jsonComment.getString("song_name"));
                song.setUsername(jsonComment.getString("username"));

                jsonLinks = jsonComment.getJSONArray("links");
                parseLinks(jsonLinks, song.getLinks());
                songs.getSongs().add(song);
            }
        } catch (IOException e) {
            throw new AppException(
                    "Can't get response from Maverick API Web Service");
        } catch (JSONException e) {
            throw new AppException("Error parsing Maverick Root API");
        }

        return songs;
    }
    public SongsCollection StyleSongsotros() throws AppException {
        Log.d(TAG, "StyleSongsotros()");
        SongsCollection songs = new SongsCollection();

        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) new URL(rootAPI.getLinks()
                    .get("songs").getTarget() + "/style?style=otros"  ).openConnection();
            Log.d(TAG, String.valueOf(urlConnection));
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoInput(true);
            urlConnection.connect();
        } catch (IOException e) {
            throw new AppException(
                    "Can't connect to Maverick API Web Service");
        }

        BufferedReader reader;
        try {
            reader = new BufferedReader(new InputStreamReader(
                    urlConnection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            JSONObject jsonObject = new JSONObject(sb.toString());
            JSONArray jsonLinks = jsonObject.getJSONArray("links");
            parseLinks(jsonLinks, songs.getLinks());

            JSONArray jsonComments = jsonObject.getJSONArray("songs");
            for (int i = 0; i < jsonComments.length(); i++) {
                Song song = new Song();
                JSONObject jsonComment = jsonComments.getJSONObject(i);

                song.setSong_name(jsonComment.getString("song_name"));
                song.setUsername(jsonComment.getString("username"));

                jsonLinks = jsonComment.getJSONArray("links");
                parseLinks(jsonLinks, song.getLinks());
                songs.getSongs().add(song);
            }
        } catch (IOException e) {
            throw new AppException(
                    "Can't get response from Maverick API Web Service");
        } catch (JSONException e) {
            throw new AppException("Error parsing Maverick Root API");
        }

        return songs;
    }

    public SongsCollection SearchSong(String song_name) throws AppException {
        Log.d(TAG, "SearchSong()");
        SongsCollection songs = new SongsCollection();

        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) new URL(rootAPI.getLinks()
                    .get("songs").getTarget() + "/search?song_name=" + song_name  ).openConnection();
            Log.d(TAG, String.valueOf(urlConnection));
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoInput(true);
            urlConnection.connect();
        } catch (IOException e) {
            throw new AppException(
                    "Can't connect to Maverick API Web Service");
        }

        BufferedReader reader;
        try {
            reader = new BufferedReader(new InputStreamReader(
                    urlConnection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            JSONObject jsonObject = new JSONObject(sb.toString());
            JSONArray jsonLinks = jsonObject.getJSONArray("links");
            parseLinks(jsonLinks, songs.getLinks());

            JSONArray jsonComments = jsonObject.getJSONArray("songs");
            for (int i = 0; i < jsonComments.length(); i++) {
                Song song = new Song();
                JSONObject jsonComment = jsonComments.getJSONObject(i);

                song.setSong_name(jsonComment.getString("song_name"));
                song.setUsername(jsonComment.getString("username"));

                jsonLinks = jsonComment.getJSONArray("links");
                parseLinks(jsonLinks, song.getLinks());
                songs.getSongs().add(song);
            }
        } catch (IOException e) {
            throw new AppException(
                    "Can't get response from Maverick API Web Service");
        } catch (JSONException e) {
            throw new AppException("Error parsing Maverick Root API");
        }

        return songs;
    }



    public SongsCollection LikeSongs() throws AppException {
        Log.d(TAG, "LikeSongs()");
        SongsCollection songs = new SongsCollection();

        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) new URL(rootAPI.getLinks()
                    .get("songs").getTarget() + "/likes" ).openConnection();
            Log.d(TAG, String.valueOf(urlConnection));
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoInput(true);
            urlConnection.connect();
        } catch (IOException e) {
            throw new AppException(
                    "Can't connect to Maverick API Web Service");
        }

        BufferedReader reader;
        try {
            reader = new BufferedReader(new InputStreamReader(
                    urlConnection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }

            JSONObject jsonObject = new JSONObject(sb.toString());
            JSONArray jsonLinks = jsonObject.getJSONArray("links");
            parseLinks(jsonLinks, songs.getLinks());

            JSONArray jsonComments = jsonObject.getJSONArray("songs");
            for (int i = 0; i < jsonComments.length(); i++) {
                Song song = new Song();
                JSONObject jsonComment = jsonComments.getJSONObject(i);

                song.setSong_name(jsonComment.getString("song_name"));
                song.setUsername(jsonComment.getString("username"));

                jsonLinks = jsonComment.getJSONArray("links");
                parseLinks(jsonLinks, song.getLinks());
                songs.getSongs().add(song);
            }
        } catch (IOException e) {
            throw new AppException(
                    "Can't get response from Maverick API Web Service");
        } catch (JSONException e) {
            throw new AppException("Error parsing Maverick Root API");
        }

        return songs;
    }

    public Boolean checkLogin(String username, String password) throws AppException {
        Log.d(TAG, "checkLogin()");
        Boolean loginOK = false;
        User user = new User();
        user.setUsername(username);
        user.setUserpass(password);
       // System.out.println("1");

        HttpURLConnection urlConnection = null;
        try {
            JSONObject jsonUser = createJsonUser(user);
            URL urlPostUsers = new URL(rootAPI.getLinks().get("login").getTarget());
            urlConnection = (HttpURLConnection) urlPostUsers.openConnection();
            String mediaType = rootAPI.getLinks().get("login").getParameters().get("type");
            urlConnection.setRequestProperty("Accept",
                    mediaType);
            urlConnection.setRequestProperty("Content-Type",
                    mediaType);
            urlConnection.setRequestMethod("POST");
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.connect();
            PrintWriter writer = new PrintWriter(
                    urlConnection.getOutputStream());
            writer.println(jsonUser.toString());
            writer.close();
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    urlConnection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;
            //System.out.println("2");
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            jsonUser = new JSONObject(sb.toString());


            user.setLoginSuccessful(jsonUser.getBoolean("loginSuccessful"));
            loginOK = user.isLoginSuccessful();
          //  System.out.println("3");
            //JSONArray jsonLinks = jsonUser.getJSONArray("links");
            //parseLinks(jsonLinks, user.getLinks());
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage(), e);
            throw new AppException("Error parsing response");
        } catch (IOException e) {
            Log.e(TAG, e.getMessage(), e);
            throw new AppException("Error getting response");
        } finally {
            if (urlConnection != null)
                urlConnection.disconnect();
        }
      //  System.out.println("4");
        return loginOK;

    }


    private Map<String, Song> songCache = new HashMap<String, Song>();

    public Song getSong(String urlSong) throws AppException {
        Song song = null;
        HttpURLConnection urlConnection = null;
        try {


            URL url = new URL(urlSong);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoInput(true);

            song = songCache.get(urlSong);
            String eTag = (song == null) ? null : song.geteTag();
            if (eTag != null)
                urlConnection.setRequestProperty("If-None-Match", eTag);
            urlConnection.connect();
            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_NOT_MODIFIED) {
                Log.d(TAG, "CACHE");
                return songCache.get(urlSong);
            }
            Log.d(TAG, "NOT IN CACHE");
            song = new Song();
            eTag = urlConnection.getHeaderField("ETag");
            song.seteTag(eTag);
            songCache.put(urlSong, song);

            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    urlConnection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            JSONObject jsonSting = new JSONObject(sb.toString());
            song.setSong_name(jsonSting.getString("song_name"));
            song.setUsername(jsonSting.getString("username"));
            song.setAlbum(jsonSting.getString("album"));
            song.setDescription(jsonSting.getString("description"));
            song.setLikes(jsonSting.getInt("likes"));
            song.setStyle(jsonSting.getString("style"));


            JSONArray jsonLinks = jsonSting.getJSONArray("links");
            parseLinks(jsonLinks, song.getLinks());
        } catch (MalformedURLException e) {
            Log.e(TAG, e.getMessage(), e);
            throw new AppException("Bad sting url");
        } catch (IOException e) {
            Log.e(TAG, e.getMessage(), e);
            throw new AppException("Exception when getting the song");
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage(), e);
            throw new AppException("Exception parsing response");
        }

        return song;
    }

    public UsersCollection SearchUser(String username) throws AppException {
        Log.d(TAG, "SearchUser()");
        UsersCollection users = new UsersCollection();

        HttpURLConnection urlConnection = null;
        try {
            urlConnection = (HttpURLConnection) new URL(rootAPI.getLinks()
                    .get("users").getTarget() + "/search?username=" + username  ).openConnection();
            Log.d(TAG, String.valueOf(urlConnection));
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoInput(true);
            urlConnection.connect();
        } catch (IOException e) {
            throw new AppException(
                    "Can't connect to Maverick API Web Service");
        }

        BufferedReader reader;
        try {
            reader = new BufferedReader(new InputStreamReader(
                    urlConnection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            JSONObject jsonObject = new JSONObject(sb.toString());
            JSONArray jsonLinks = jsonObject.getJSONArray("links");
            parseLinks(jsonLinks, users.getLinks());

            JSONArray jsonUsers = jsonObject.getJSONArray("users");
            for (int i = 0; i < jsonUsers.length(); i++) {
                User user = new User();
                JSONObject jsonUser = jsonUsers.getJSONObject(i);

                user.setUsername(jsonUser.getString("username"));
                user.setName(jsonUser.getString("name"));

                jsonLinks = jsonUser.getJSONArray("links");
                parseLinks(jsonLinks, user.getLinks());
                users.getUsers().add(user);

            }
        } catch (IOException e) {
            throw new AppException(
                    "Can't get response from Maverick API Web Service");
        } catch (JSONException e) {
            throw new AppException("Error parsing Maverick Root API");
        }

        return users;
    }

    private Map<String, User> userCache = new HashMap<String, User>();

    public User getUser(String urlUser) throws AppException {
        User user = null;
        HttpURLConnection urlConnection = null;
        try {


            URL url = new URL(urlUser);
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.setDoInput(true);

            user = userCache.get(urlUser);
            String eTag = (user == null) ? null : user.geteTag();
            if (eTag != null)
                urlConnection.setRequestProperty("If-None-Match", eTag);
            urlConnection.connect();
            if (urlConnection.getResponseCode() == HttpURLConnection.HTTP_NOT_MODIFIED) {
                Log.d(TAG, "CACHE");
                return userCache.get(urlUser);
            }
            Log.d(TAG, "NOT IN CACHE");
            user = new User();
            eTag = urlConnection.getHeaderField("ETag");
            user.seteTag(eTag);
            userCache.put(urlUser, user);

            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    urlConnection.getInputStream()));
            StringBuilder sb = new StringBuilder();
            String line = null;
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            JSONObject jsonSting = new JSONObject(sb.toString());
            user.setUsername(jsonSting.getString("username"));
            user.setName(jsonSting.getString("name"));
            user.setDescription(jsonSting.getString("description"));



            JSONArray jsonLinks = jsonSting.getJSONArray("links");
            parseLinks(jsonLinks, user.getLinks());
        } catch (MalformedURLException e) {
            Log.e(TAG, e.getMessage(), e);
            throw new AppException("Bad sting url");
        } catch (IOException e) {
            Log.e(TAG, e.getMessage(), e);
            throw new AppException("Exception when getting the user");
        } catch (JSONException e) {
            Log.e(TAG, e.getMessage(), e);
            throw new AppException("Exception parsing response");
        }

        return user;
    }

    // Crear JSON de un Usuario
    private JSONObject createJsonUser(User user) throws JSONException {
        JSONObject jsonUser = new JSONObject();
        jsonUser.put("username", user.getUsername());
        jsonUser.put("userpass", user.getUserpass());
        jsonUser.put("name", user.getName());
        jsonUser.put("email", user.getEmail());

//System.out.println(user.getUsername());
  //      System.out.println(jsonUser);
        return jsonUser;

    }
    //Le pasamos un Array y un Mapa donde vamos a guardar los links
    private void parseLinks(JSONArray jsonLinks, Map<String, Link> map)
            throws AppException, JSONException {
        for (int i = 0; i < jsonLinks.length(); i++) {
            Link link = null;
            try {
                link = SimpleLinkHeaderParser
                        .parseLink(jsonLinks.getString(i));
            } catch (Exception e) {
                throw new AppException(e.getMessage());
            }
            String rel = link.getParameters().get("rel");
            String rels[] = rel.split("\\s"); //La rel del HATEOAS puede tener varios parámetros, ej: rel="create collection" (separadas por espacio)
            for (String s : rels) //En el mapa se guardan las rels y los titles
                map.put(s, link);
        }
    }


}
