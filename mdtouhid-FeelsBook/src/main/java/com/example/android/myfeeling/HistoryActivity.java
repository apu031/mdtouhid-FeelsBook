/**
 * @author: Md Touhidul Islam
 * @date: 2018-10-07
 */

package com.example.android.myfeeling;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class HistoryActivity extends AppCompatActivity {

    private ArrayList<Feeling> feelings = new ArrayList<Feeling>();

    FileForFeel file = new FileForFeel(this);

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        Toast.makeText(getBaseContext(), "To Delete: Just Click", Toast.LENGTH_LONG).show();

        try {
            feelings = file.loadFromFile(this, "feels.sav", feelings);
        }catch(NullPointerException e){
            e.printStackTrace();
            Toast.makeText(getBaseContext(), "Error reading file!", Toast.LENGTH_SHORT).show();
        }

        final FeelingsAdapter adapter = new FeelingsAdapter(this, feelings);

        final ListView listView = (ListView) findViewById(R.id.list);

        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //ListView Clicked item index
                int itemPosition = position;

                // ListView Clicked item value
                //String  itemValue    = (String) listView.getItemAtPosition(position);
                feelings.remove(itemPosition);

                sortArrayList();

                adapter.notifyDataSetChanged();

                file.saveInFile(getBaseContext(), "feels.sav", feelings);

                // Show Alert
                Toast.makeText(getBaseContext(),"Position :"+itemPosition, Toast.LENGTH_LONG).show();
            }
        });
    }

    /**
     * Source Idea: https://stackoverflow.com/questions/5927109/sort-objects-in-arraylist-by-date
     */
    public void sortArrayList(){
        Collections.sort(feelings, new Comparator<Feeling>() {
            @Override
            public int compare(Feeling o1, Feeling o2) {
                return o1.getFeelingTime().compareTo(o2.getFeelingTime());
            }
        });
    }
}
