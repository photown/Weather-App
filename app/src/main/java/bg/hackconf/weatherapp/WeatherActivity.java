package bg.hackconf.weatherapp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import bg.hackconf.weatherapp.model.Main;
import bg.hackconf.weatherapp.model.Weather;
import bg.hackconf.weatherapp.model.WeatherItem;

/**
 * By Antoan Angelov on 15-Sep-15.
 */
public class WeatherActivity extends Activity implements IWeatherReceived {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_weather_screen);

        String capitalName = getIntent().getStringExtra("capital");

        new DownloadJsonAsyncTask(this).execute(capitalName);
    }

    @Override
    public void onWeatherReceived(WeatherItem weatherItem) {

        Weather weather = weatherItem.getWeather().get(0);
        Main main = weatherItem.getMain();

        TextView mName = (TextView) findViewById(R.id.capital_name);
        mName.setText(weatherItem.getName());

        TextView mDescription = (TextView) findViewById(R.id.capital_description);
        mDescription.setText(weather.getDescription());

        TextView mHumidity = (TextView) findViewById(R.id.capital_humidity);
        mHumidity.setText(main.getHumidity() + "%");

        TextView mTemperatureLow = (TextView) findViewById(R.id.capital_temperature_low);
        Double minTemp = main.getTempMin();
        mTemperatureLow.setText(minTemp != null ? minTemp.intValue() + "°" : "Na");

        TextView mTemperatureHigh = (TextView) findViewById(R.id.capital_temperature_high);
        Double maxTemp = main.getTempMax();
        mTemperatureHigh.setText("/ " + (maxTemp != null ? maxTemp.intValue() + "°" : "Na"));

        TextView mWind = (TextView) findViewById(R.id.capital_wind);
        mWind.setText(weatherItem.getWind().getSpeed() + "m/s");

        findViewById(R.id.additional_info_container).setVisibility(View.VISIBLE);
    }
}
