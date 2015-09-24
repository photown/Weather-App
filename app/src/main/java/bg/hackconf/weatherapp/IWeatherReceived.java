package bg.hackconf.weatherapp;

import bg.hackconf.weatherapp.model.WeatherItem;

/**
 * By Antoan Angelov on 15-Sep-15.
 */
public interface IWeatherReceived {
    void onWeatherReceived(WeatherItem weatherItem);
}
