package bg.hackconf.weatherapp;

import bg.hackconf.weatherapp.model.WeatherItem;

/**
 * This interface is used to notify our WeatherActivity whenever the JSON has been downloaded.
 */
public interface IWeatherReceived {
    void onWeatherReceived(WeatherItem weatherItem);
}
