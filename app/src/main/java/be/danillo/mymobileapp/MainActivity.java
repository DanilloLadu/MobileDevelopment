package be.danillo.mymobileapp;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.List;

import be.danillo.mymobileapp.controller.ProductDBController;
import be.danillo.mymobileapp.controller.ProductRestController;
import be.danillo.mymobileapp.data.ApiOption;
import be.danillo.mymobileapp.domain.Product;
import be.danillo.mymobileapp.vieuw.*;
import be.danillo.mymobileapp.vieuw.db.ProductListDBActivity;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {
    private String TAG = MainActivity.class.getSimpleName();
    private Button buttonScan;
    public static List<Product> listProducts;
    //qr code scanner object
    private IntentIntegrator qrScan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new ProductRestController(this);
        new ProductDBController(this);
        new ApiOption(this);



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        buttonScan = (Button) findViewById(R.id.buttonScan);
        //intializing scan object
        qrScan = new IntentIntegrator(this);

        //attaching onclick listener
        buttonScan.setOnClickListener(this);

//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

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
            Intent intent = new Intent(this, OptionActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_productList) {
            Intent intent = new Intent(this, ProductListActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.nav_productList_Scanned) {
            Intent intent = new Intent(this, ProductListDBActivity.class);
            startActivity(intent);
            return true;

        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //Getting the scan results
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (result != null) {
            //if qrcode has nothing in it
            if (result.getContents() == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_LONG).show();
            } else {
                //if qr contains data
                try {

                    //setting values to textviews
                    //Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                    Product product = ProductRestController.get(this).getProductById(Integer.parseInt(result.getContents()));
                    if (product != null) {
                        Log.e(TAG, product.getName());

                        //ProductDBController.get(this).addProduct(new Product());
                        Intent intent = ProductActivity.newIntent(this, Integer.parseInt(result.getContents()));
                        startActivity(intent);
                        ProductDBController.get(this).addProduct(product);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast.makeText(this, result.getContents(), Toast.LENGTH_LONG).show();
                }
            }
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    @Override
    public void onClick(View view) {
        //initiating the qr code scan
        qrScan.initiateScan();
    }
}
