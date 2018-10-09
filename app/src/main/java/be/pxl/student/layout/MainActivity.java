package be.pxl.student.layout;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;

import be.pxl.student.JSONArrayCursor;
import be.pxl.student.ProductsListAdapter;
import be.pxl.student.domain.Product;

public class MainActivity extends AppCompatActivity {
    public static final String URL = "http://danillo.be:8080/product/all";

    private ProductsListAdapter mAdapter;
    private RecyclerView mProductslist;
    private Cursor mCursor;

    private String JSONResponse = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                getProducts();
            }
        });

        getProducts(URL);

        RecyclerView listView = (RecyclerView) findViewById(R.id.articlesListView);
    }

    private Cursor getJSONCursor(String response){
        try {
            JSONArray array = new JSONArray(response);
            return new JSONArrayCursor(array);
        } catch (JSONException exception)
        {
            String ex = exception.getMessage();
        }
        return null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void navigateToDetails(View view, Product selectedItem){
        Intent intent = new Intent(view.getContext(), DetailActivity.class);
        intent.putExtra("be.pxl.student.domain.Product", selectedItem);
        startActivity(intent);
    }

    public void getProducts(String url){
        // Instantiate the RequestQueue.
        RequestQueue queue = Volley.newRequestQueue(this);

        // Request a string response from the provided URL.
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONResponse = response;
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(stringRequest);
    }

    public void getProducts(){
        mProductslist = (RecyclerView) this.findViewById(R.id.articlesListView);
        mCursor = getJSONCursor(JSONResponse);
        mAdapter = new ProductsListAdapter(this, mCursor);
        mProductslist.setLayoutManager(new LinearLayoutManager(this));
        mProductslist.setAdapter(mAdapter);
    }
}
