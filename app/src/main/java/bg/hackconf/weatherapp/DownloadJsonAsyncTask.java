package bg.hackconf.weatherapp;

import android.os.AsyncTask;
import android.util.Log;

import com.google.gson.Gson;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;

import bg.hackconf.weatherapp.model.WeatherItem;

/**
 * This class handles downloading of the JSON from OpenWeatherMap.
 * Take for example this link: http://api.openweathermap.org/data/2.5/weather?q=Sofia&units=metric
 * Since it returns a lot of info, we take only the data we need - "weather", "wind" and "main".
 * Note that the link above gives the weather for Sofia. We indicated this in the "q=Sofia" part of the URL.
 * That's why we use String.format when parsing the final URL string.
 *
 */
public class DownloadJsonAsyncTask extends AsyncTask<String, Void, WeatherItem> {

    // Here we define our template - note the %s in "q=%s". That's where the capital name will go.
    private static final String TEMPLATE = "http://api.openweathermap.org/data/2.5/weather?q=%s&units=metric";

    // We keep a reference for the IWeatherReceived object.
    private IWeatherReceived mListener;

    public DownloadJsonAsyncTask(IWeatherReceived listener) {
        mListener = listener;
    }

    // This method is the most important. When in WeatherActivity we call DownloadJsonAsyncTask.execute(capitalName),
    // this method gets called indirectly. It does all the hard work of parsing the URL, downloading the JSON
    // and converting that JSON to an object we can use (WeatherItem).
    @Override
    protected WeatherItem doInBackground(String... params) {
        Gson gson = new Gson();

        Reader reader;
        try {
            // We parse our URL. Note that "params" is a single-element array.
            // If we said DownloadJsonAsyncTask.execute("London"),
            // "params" would have a single element of "London".
            String urlString = String.format(TEMPLATE, params[0]);
            URL url = new URL(urlString);

            // We open the connection to the API and download the JSON.
            URLConnection con = url.openConnection();
            reader = new InputStreamReader(con.getInputStream());

            // When the JSON is downloaded, we tell a magical Gson library to take the JSON
            // and populate all the classes in the "model" folder with the corresponding values.
            // Also, note that we return the final result.
            return gson.fromJson(reader, WeatherItem.class);

        } catch (IOException e) {
            // It's pretty important to handle the case where we don't have internet
            // or simply have a poor internet connection.
            e.printStackTrace();
        }

        return null;
    }

    // This method gets called when the downloading is finished.
    // From it, we call the listener whose reference we keep, so we can handle it in WeatherActivity.
    @Override
    protected void onPostExecute(WeatherItem weatherItem) {
        mListener.onWeatherReceived(weatherItem);
    }
}
