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
 * By Antoan Angelov on 15-Sep-15.
 */
public class DownloadJsonAsyncTask extends AsyncTask<String, Void, WeatherItem> {

    private static final String TEMPLATE = "http://api.openweathermap.org/data/2.5/weather?q=%s&units=metric";

    private IWeatherReceived mListener;

    public DownloadJsonAsyncTask(IWeatherReceived listener) {
        mListener = listener;
    }

    @Override
    protected WeatherItem doInBackground(String... params) {
        Gson gson = new Gson();

        Reader reader;
        try {
            String urlString = String.format(TEMPLATE, params[0]);
            URL url = new URL(urlString);

            URLConnection con = url.openConnection();
            reader = new InputStreamReader(con.getInputStream());

            return gson.fromJson(reader, WeatherItem.class);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    protected void onPostExecute(WeatherItem weatherItem) {
        mListener.onWeatherReceived(weatherItem);
    }
}
