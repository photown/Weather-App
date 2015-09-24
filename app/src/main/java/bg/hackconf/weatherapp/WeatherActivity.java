package bg.hackconf.weatherapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import bg.hackconf.weatherapp.model.Main;
import bg.hackconf.weatherapp.model.Weather;
import bg.hackconf.weatherapp.model.WeatherItem;

/**
*
* This is the screen which shows us the weather data for the selected capital.
*
* */
public class WeatherActivity extends Activity implements IWeatherReceived {

    // Consider this the entry point of our current screen (MainActivity).
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Here we tell the app to use activity_weather_screen.xml to describe the interface.
        // Here, the XML only contains the entire interface of the weather visualisation.
        setContentView(R.layout.activity_weather_screen);

        // We get our "extra" information we added in MainActivity#onItemClick when opening this activity.
        // It's the name of the capital.
        String capitalName = getIntent().getStringExtra("capital");

        // Here is the logic which downloads some JSON from OpenWeatherMap.
        // We give it an instance of IWeatherReceived (this) and tell it to download data for our capital of interest.
        new DownloadJsonAsyncTask(this).execute(capitalName);
    }

    // This method is called when DownloadJsonAsyncTask is finished.
    // This practically means that we have downloaded the JSON and parsed it into our WeatherItem object.
    @Override
    public void onWeatherReceived(WeatherItem weatherItem) {

        Weather weather = weatherItem.getWeather().get(0);
        Main main = weatherItem.getMain();

        // We take the TextView which describes the label with the capital name and fill it with the actual capital name.
        TextView mName = (TextView) findViewById(R.id.capital_name);
        mName.setText(weatherItem.getName());

        // Basically the same here, only we do it with the description.
        TextView mDescription = (TextView) findViewById(R.id.capital_description);
        mDescription.setText(weather.getDescription());

        // Same...
        TextView mHumidity = (TextView) findViewById(R.id.capital_humidity);
        mHumidity.setText(main.getHumidity() + "%");

        // Same...
        TextView mWind = (TextView) findViewById(R.id.capital_wind);
        mWind.setText(weatherItem.getWind().getSpeed() + "m/s");

        // Since for some capitals the API does not give us data for low and high temperature,
        // we need to see if it is defined. If it is not - simply write "Na" - Not available.
        TextView mTemperatureLow = (TextView) findViewById(R.id.capital_temperature_low);
        Double minTemp = main.getTempMin();
        mTemperatureLow.setText(minTemp != null ? minTemp.intValue() + "°" : "Na");

        // Same logic here
        TextView mTemperatureHigh = (TextView) findViewById(R.id.capital_temperature_high);
        Double maxTemp = main.getTempMax();
        mTemperatureHigh.setText("/ " + (maxTemp != null ? maxTemp.intValue() + "°" : "Na"));

        // Finally, we make the whole container visible, as it was not visible until now.
        // We have intentionally hidden it in the XML, so it displays nothing when it's loading.
        findViewById(R.id.additional_info_container).setVisibility(View.VISIBLE);
    }
}
