package com.example.lab4;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.jacksonandroidnetworking.JacksonParserFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView lvList;
    private List<PhotoFAN> photoList;
    private Lab4Adapter lab4Adapter;
    String link = "https://www.flickr.com/services/rest/?method=flickr.favorites.getList&api_key=1fc1d51cdab4089c967fb002b32d1679&user_id=187041022%40N07&extras=views%2C+media%2C+path_alias%2C+url_sq%2C+url_t%2C+url_s%2C+url_q%2C+url_m%2C+url_n%2C+url_z%2C+url_c%2C+url_l%2C+url_o&per_page=20&page=1&format=json&nojsoncallback=1";
    String linkImg;
    private SwipeRefreshLayout swiperefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadPhoto();
            }
        });
        lvList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "Ảnh " + position, Toast.LENGTH_SHORT).show();
            }
        });



    }

    private void loadPhoto() {
        AndroidNetworking.initialize(getApplicationContext());
        AndroidNetworking.setParserFactory(new JacksonParserFactory());
        AndroidNetworking.get(link).build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        photoList = new ArrayList<>();
                        try {
                            for (int i = 0; i < response.getJSONObject("photos").getJSONArray("photo").length(); i++) {
                                linkImg = response.getJSONObject("photos").getJSONArray("photo").getJSONObject(i).getString("url_z");
                                photoList.add(new PhotoFAN(linkImg));
                            }
                            lab4Adapter = new Lab4Adapter(photoList, MainActivity.this);
                            lvList.setAdapter(lab4Adapter);
                            Log.e("AAAAAAAAAAAAAAAAAAAAA", String.valueOf(photoList.size()));

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
//
//
//                        for (int i =0; i<response.; i++) {
//                            photoList.add(new Photo(response.get(i)))
//                        }
                    }

                    @Override
                    public void onError(ANError anError) {

                    }
                });
    }

    private void initView() {
        lvList = (ListView) findViewById(R.id.lvList);
        swiperefresh = (SwipeRefreshLayout) findViewById(R.id.swiperefresh);
    }
}