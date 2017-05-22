//*****************************************************************************************************************************
// Created by Angela-Maria Despotopoulou, Athens, Greece.
// Latest Update: 22th May 2017.
//*****************************************************************************************************************************

package com.angie.mypet.authentication;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.angie.mypet.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

import javax.net.ssl.HttpsURLConnection;

public class LoginController {

    AppCompatActivity act;
    String usernameToValidate = "";
    String passwordToValidate = "";
    String validatedFirstName = "";
    String userdataJSONToSend = "";
    int responseCode = 0;


    //*****************************************************************************************************************************
    // Constructor.
    //*****************************************************************************************************************************

    public LoginController(AppCompatActivity act) {
        this.act = act;
    }

    //*****************************************************************************************************************************
    // Returns whether any user has already logged in by checking the existence of a shared preferences file.
    // No need to search for particular user as the mobile device belongs to a single person.
    // Called to decide the menu label (Log In vs. Log out) and access to pet detailed description.
    // Returns the user's name, or null if the user is not logged in.
    //*****************************************************************************************************************************

    public String isUserAlreadyLoggedIn() {
        // Checking the existence of the file.
        File f = new File(act.getApplicationInfo().dataDir + "/shared_prefs/" + act.getString(R.string.preference_file_key) + ".xml");

        // If it does not exist, then the user is not logged in. Return null.
        if (f.exists() == false) return null;

        // If it does exist, but is empty, the user is not logged in. Return null.
        SharedPreferences sharedPref = act.getSharedPreferences(act.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        if (sharedPref.getAll().size() == 0) return null;

        // If it does exist and has data, read the data (a string which can be transformed in JSONObject format.
        String userdataAsString = sharedPref.getString("userdata", "");

        // Transform to JSONObject and read the name.
        JSONObject userdata;
        String username;
        try {
            userdata = new JSONObject(userdataAsString);
            username = userdata.getString("firstName");
        } catch (JSONException e) {
            // If anything goes wrong, return null as if the file does not exist.
            username = null;
            e.printStackTrace();
        }

        return username;
    }


    //*****************************************************************************************************************************
    // Provided the user's username and password (from a form), attempts to contact Hodor for a previous registration.
    //*****************************************************************************************************************************

    public void validateUser(String username, String password) {

        // Making those accessible.
        this.usernameToValidate = username;
        this.passwordToValidate = password;

        // Preparing endpoint /{username}/{password}
        String urlString = "http://hodor.ait.gr:8080/myPets/api/user/";
        urlString = urlString.concat(username);
        urlString = urlString.concat("/");
        urlString = urlString.concat(password);

        // Checks connectivity first, just to be sure.
        ConnectivityManager connMgr = (ConnectivityManager) act.getSystemService(Context.CONNECTIVITY_SERVICE);

        try {
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

            if (networkInfo != null && networkInfo.isConnected()) {
                // The username/password combination is being validated.
                new CheckCombinationTask().execute(urlString);
            } else {
                // The user receives a message for connectivity errors.
                Toast.makeText(act, R.string.err_no_internet, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            return;
        }
    }


    //*****************************************************************************************************************************
    // An inner class provides the asynchronous task for user validation.
    //*****************************************************************************************************************************

    private class CheckCombinationTask extends AsyncTask<String, Void, String> {


        @Override
        // doInBackground: does what needs to be done
        protected String doInBackground(String... urls) {
            try {
                return downloadData(urls[0]);
            } catch (IOException e) {
                return null;
            }
        }

        // onPostExecute: displays the results of the AsyncTask
        @Override
        protected void onPostExecute(String result) {
            // Success
            if (result != "") {
                loginUser(result);
                String hello = new String("Long time no see ").concat(validatedFirstName).concat(new String("!"));
                Toast.makeText(act, hello, Toast.LENGTH_SHORT).show();
                act.finish();
            }
            // Failure
            else {
                Toast.makeText(act, R.string.user_not_found, Toast.LENGTH_SHORT).show();
            }
        }
    }


    //*****************************************************************************************************************************
    // The communication with Hodor, in order to perform a GET, in order to check credentials validity.
    //*****************************************************************************************************************************

    private String downloadData(String locationUrl) throws IOException {
        InputStream is = null;

        try {
            // Standard connectivity procedure.
            URL url = new URL(locationUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds */);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("GET");
            conn.setDoInput(true);

            // Starts the query.
            conn.connect();

            // Reads the response (200 means success, 404 failure).
            int response = conn.getResponseCode();

            String result = "";

            // If the username/password combo is valid, we read the user's full info as a JSON string.
            // e.g. {"id":1,"userName":"paprika","password":"!lovebunnies@","firstName":"Marina","lastName":"Lambrou"}
            if (response == HttpsURLConnection.HTTP_OK) {
                is = conn.getInputStream();
                Scanner s = new Scanner(is).useDelimiter("\\A");
                result = s.hasNext() ? s.next() : "";
            }
            return result;
        } catch (Exception e) {
            return "";
        } finally {
            // Makes sure that the InputStream is closed after the app is
            // finished using it.
            if (is != null) {
                is.close();
            }
        }
    }


    //*****************************************************************************************************************************
    // Provided the validates user's information (from an AsyncTask), logs in the user.
    // Returns true for success, false for failure.
    //*****************************************************************************************************************************

    public boolean loginUser(String validatedUserData) {
        // Transform form information to JSONObject form.
        JSONObject userdataObject = readJSONUserEntryForFile(validatedUserData);

        // Attempt to save user info to a Shared Preferences File.
        boolean writeSuccessful = writeJSONUserToSharedPreferencesFile(userdataObject);

        return writeSuccessful;
    }


    //*****************************************************************************************************************************
    // Provided the user information in JSON form, transforms them into a smaller JSONObject.
    // SOS: it is bad practice to save passwords in local files.
    //*****************************************************************************************************************************

    public JSONObject readJSONUserEntryForFile(String validatedUserData) {
        JSONObject user, validatedUser = new JSONObject();
        String firstName, lastName;

        try {
            user = new JSONObject(validatedUserData);
            firstName = user.getString("firstName");
            lastName = user.getString("lastName");
            validatedUser.put("firstName", firstName);
            validatedUser.put("lastName", lastName);
            validatedFirstName = firstName;

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return validatedUser;
    }


    //*****************************************************************************************************************************
    // Writes a JSONObject containing userdata to a given Shared Preferences File. In fact "logs in" the user.
    // Returns true for success, false for failure.
    //*****************************************************************************************************************************

    public boolean writeJSONUserToSharedPreferencesFile(JSONObject user) {
        // Checking the existence of the file.
        File f = new File(act.getApplicationInfo().dataDir + "/shared_prefs/" + act.getString(R.string.preference_file_key) + ".xml");

        // If it does not exist, then the user is not logged in. Return error.
        //if (f.exists() == false) return false;

        // If it not empty, the user is already logged in. Return error.
        SharedPreferences sharedPref = act.getSharedPreferences(act.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        //if (sharedPref.getAll().size() != 0) return false;

        // If it does exist and is empty, proceed to write user data after encapsulating it into a String.
        String userAsString = user.toString();
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString("userdata", userAsString);
        editor.commit();

        return true;
    }


    //*****************************************************************************************************************************
    // Logs out user by emptying the Shared Preferences File.
    // Returns true for success, false for failure.
    //*****************************************************************************************************************************

    public boolean logoutUser() {
        // Checking the existence of the file.
        File f = new File(act.getApplicationInfo().dataDir + "/shared_prefs/" + act.getString(R.string.preference_file_key) + ".xml");

        // If it does not exist, then the user is not logged in. Return success.
        if (f.exists() == false) return true;

        // If it does exist, but is empty, the user is not logged in. Return success.
        SharedPreferences sharedPref = act.getSharedPreferences(act.getString(R.string.preference_file_key), Context.MODE_PRIVATE);
        if (sharedPref.getAll().size() == 0) return true;

        // If it does exist and is not empty, do empty it.
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.clear();
        editor.commit();

        return true;
    }

    ////////////////////////////////////////

    //*****************************************************************************************************************************
    // Provided the user's username and password (from a form), attempts to contact Hodor to perform registration.
    //*****************************************************************************************************************************

    public void registerUser(String firstname, String lastname, String username, String password) {

        // Producing a JSON String from parameters.
        JSONObject userdata = produceJSONUserEntryForRegistration(firstname, lastname, username, password);

        // Preparing endpoint
        String urlString = "http://hodor.ait.gr:8080/myPets/api/user/";
        userdataJSONToSend = userdata.toString();
        //urlString = urlString.concat("/");

        // Checks connectivity first, just to be sure.
        ConnectivityManager connMgr = (ConnectivityManager) act.getSystemService(Context.CONNECTIVITY_SERVICE);

        try {
            NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

            if (networkInfo != null && networkInfo.isConnected()) {
                // The username/password combination is being validated.
                new RegistrationTask().execute(urlString);
            } else {
                // The user receives a message for connectivity errors.
                Toast.makeText(act, R.string.err_no_internet, Toast.LENGTH_SHORT).show();
            }
        } catch (Exception e) {
            return;
        }
    }


    //*****************************************************************************************************************************
    // Provided the user information from registration screen, transforms them into a JSONObject.
    // This will be the message towards Hodor.
    //*****************************************************************************************************************************

    public JSONObject produceJSONUserEntryForRegistration(String firstname, String lastname, String username, String password) {
        JSONObject userdata = new JSONObject();

        try {
            //userdata.put("id", 258);
            userdata.put("userName", username);
            userdata.put("password", password);
            userdata.put("firstName", firstname);
            userdata.put("lastName", lastname);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return userdata;
    }


    //*****************************************************************************************************************************
    // An inner class provides the asynchronous task for registration.
    //*****************************************************************************************************************************

    private class RegistrationTask extends AsyncTask<String, Void, String> {


        @Override
        // doInBackground: does what needs to be done
        protected String doInBackground(String... urls) {
            try {
                return uploadData(urls[0]);
            } catch (IOException e) {
                return null;
            }
        }

        // onPostExecute: displays the results of the AsyncTask
        @Override
        protected void onPostExecute(String result) {
            // Success - If registration has been successful...
            if (responseCode == HttpsURLConnection.HTTP_OK) {
                // Login the user and say hello.
                loginUser(userdataJSONToSend);
                String hello = new String("Nice to meet you ").concat(validatedFirstName).concat(new String("!"));
                Toast.makeText(act, hello, Toast.LENGTH_SHORT).show();
                // Go back to LoginActivity (which will kill itself too).
                act.finish();
            }

            // Failure. Code already exists.
            else if (responseCode == HttpsURLConnection.HTTP_CONFLICT) {
                Toast.makeText(act, R.string.err_code_conflict, Toast.LENGTH_SHORT).show();
            }

            // Failure
            else {
                Toast.makeText(act, R.string.err_no_internet, Toast.LENGTH_SHORT).show();
            }
        }
    }


    //*****************************************************************************************************************************
    // The communication with Hodor, in order to perform a POST, in order to register a new user.
    //*****************************************************************************************************************************

    private String uploadData(String locationUrl) throws IOException {

        BufferedReader in = null;
        OutputStream os = null;
        HttpURLConnection conn = null;
        StringBuffer response = null;

        try {

            URL url = new URL(locationUrl);
            String message = userdataJSONToSend;

            // Prepares connection.
            conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /*milliseconds*/);
            conn.setConnectTimeout(15000 /* milliseconds */);
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.setFixedLengthStreamingMode(message.getBytes().length);
            conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");

            // Opens Connection.
            conn.connect();

            // Sends the data and performs clean-up.
            os = new BufferedOutputStream(conn.getOutputStream());
            os.write(message.getBytes());
            os.flush();

            responseCode = conn.getResponseCode();

            // In case of success read the message as well (not actually used).
            if (responseCode == HttpsURLConnection.HTTP_OK) {

                in = new BufferedReader(
                        new InputStreamReader(conn.getInputStream()));
                String inputLine;
                response = new StringBuffer();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();
            }

            // In case of conflict, produce a conflict message (not actually used).
            else if (responseCode == HttpsURLConnection.HTTP_CONFLICT) {

                response = new StringBuffer("Code already exists.");
            }

            // Everything wrong.
            else
            {
                response = new StringBuffer("No Internet connection.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            os.close();
            conn.disconnect();
        }
        return response.toString();
    }
}













/*




        InputStream is = null;
        String result = null;
        try {

            // Standard connectivity procedure.
            URL url = new URL(locationUrl);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setReadTimeout(10000 /* milliseconds *///);
        /*    conn.setConnectTimeout(15000 /* milliseconds );
            conn.setRequestProperty("Content-Type", "application/json;charset=utf-8");
            conn.setRequestMethod("POST");
            conn.setDoInput(true);
            conn.setDoOutput(true);
            conn.connect();

            OutputStreamWriter streamWriter = new OutputStreamWriter(conn.getOutputStream());
            streamWriter.write(locationUrl);
            streamWriter.flush();*/

// Sends data
            /*OutputStream os = conn.getOutputStream();
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(os, "UTF-8"));
            writer.write(locationUrl);
            writer.flush();
            writer.close();
            os.close();*/

            /*OutputStream os = conn.getOutputStream();
            os.write(locationUrl.getBytes("UTF-8"));
            os.close();

            // Confirming success.
            int responseCode = conn.getResponseCode();

            is = conn.getInputStream();
            Scanner s = new Scanner(is).useDelimiter("\\A");
            result = s.hasNext() ? s.next() : "";

           // if (responseCode == HttpsURLConnection.HTTP_OK) {

                /*String line;

                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                while ((line=br.readLine()) != null) {
                    response += line;
                }
                int h = 8;*/
// }
// else {
//     response="";
// }
        /*} catch (Exception e) {
            e.printStackTrace();
        }

        finally {
            // Makes sure that the InputStream is closed after the app is
            // finished using it.
            if (is != null) {
                is.close();
            }

            return result;*/
//        }
//   }
//}
