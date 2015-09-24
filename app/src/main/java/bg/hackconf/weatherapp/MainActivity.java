package bg.hackconf.weatherapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

/**
*
* This is our main screen. It is the screen that opens when you press on the app's icon in the app drawer.
* Keep in mind that in Android, a screen is called an activity.
*
* */
public class MainActivity extends AppCompatActivity
        implements AdapterView.OnItemClickListener {

    // This list contains the cities that are displayed in the main app.
    private static final String[] capitals = new String[] {
            "London", "Sofia", "Berlin", "Warsaw", "Helsinki", "Budapest", "Rome", "Paris", "Prague", "Stockholm"};

    // Consider this the entry point of our current screen (MainActivity).
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Here we tell the app to use activity_main.xml to describe the interface.
        // Here, the XML only contains a single ListView - a visual element which displays a list of stuff.
        setContentView(R.layout.activity_main);

        // Now we can get our listview via a magic call to findViewById, passing the ID of the ListView from activity_main.xml
        ListView listView = (ListView) findViewById(R.id.list);

        // The adapter ultimately fills the listview with the data it needs to display.
        // In our case, the data is the array with the capitals we defined above.
        // We also tell it to use a standard Android view (simple_list_item_1) to display each capital.
        listView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, capitals));

        // We also tell the listview to register every click on its items and call onItemClick with the corresponding data.
        // It takes an OnItemClick listener as a parameter. MainActivity class implements it.
        listView.setOnItemClickListener(this);
    }

    // This is called whenever a user has clicked on an item from the listview.
    // We registered that when we called listView.setOnItemClickListener(this)
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        // We want to open the WeatherActivity screen when we tap on a capital.
        // In Android, to open another screen, we need to send it a message - a.k.a. Intent.
        // In this intent, we say we want to open WeatherActivity.
        Intent intent = new Intent(this, WeatherActivity.class);

        // We also say we want to add some "extra" information - the name of the capital.
        intent.putExtra("capital", capitals[position]);

        // Finally, we send this "message" to Android and it handles it - by opening our WeatherActivity.
        startActivity(intent);
    }
}
