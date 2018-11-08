package be.danillo.mymobileapp.controller;

import android.app.ProgressDialog;
import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import com.android.volley.RequestQueue;
import com.android.volley.Request;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.List;


import be.danillo.mymobileapp.Interface.VolleyCallback;
import be.danillo.mymobileapp.domain.Ingredient;
import be.danillo.mymobileapp.domain.Product;


public class ProductRestController {
    private static final String TAG = ProductRestController.class.getSimpleName();
    private static ProductRestController _productRestController;
    private String baseUrl = "http://danillo.be:8080/";
    private static List<Product> productList;
    private RequestQueue requestQueue;

    public ProductRestController(Context context) {
        final ProgressDialog progressDialog = new ProgressDialog(context);
        progressDialog.setMessage("Loading Data...");
        progressDialog.show();
        new Thread(new Runnable() {
            public void run() {
                if (productList == null) {
                    productList = new ArrayList<>();
                    callbackGetResponce(context, "product/all", new VolleyCallback() {
                        @Override
                        public void onSuccessResponse(JSONArray result) {
                            if (result.length() > 0) {
                                for (int i = 0; i < result.length(); i++) {
                                    List<Ingredient> ingredientList = new ArrayList<>();
                                    try {

                                        JSONObject jsonObj = result.getJSONObject(i);

                                        JSONArray jsonIngredientObject = jsonObj.getJSONArray("ingredientList");

                                        for (int j = 0; j < jsonIngredientObject.length(); j++) {

                                            JSONObject in = jsonIngredientObject.getJSONObject(j);
                                            // Log.i(TAG, in.getInt("id")+"");
                                            ingredientList.add(new Ingredient(
                                                    in.getInt("id"),
                                                    in.getString("name"),
                                                    in.getDouble("ammount")
                                            ));
                                        }

                                        productList.add(new Product(
                                                jsonObj.getInt("id"),
                                                jsonObj.getString("name"),
                                                ingredientList));

                                    } catch (JSONException e) {
                                        Log.e(TAG, "Invalid JSON Object.");
                                    } finally {
                                        progressDialog.dismiss();
                                    }
                                }
                            } else {
                                progressDialog.dismiss();
                            }
                        }
                    });
                } else {
                    progressDialog.dismiss();
                }
            }
        }).start();


    }

    public void callbackGetResponce(Context context, String prefix, final VolleyCallback callback) {
        productList = new ArrayList<>();
        String url = baseUrl + prefix;
        JsonArrayRequest arrReq = new JsonArrayRequest(Request.Method.GET, url, null,
                response -> {
                    callback.onSuccessResponse(response); // call call back function here

                }, error -> Log.e("Volley", error.toString())
        );
        requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(arrReq);
    }

    public static ProductRestController get(Context context) {
        if (_productRestController == null) {
            _productRestController = new ProductRestController(context);
        }
        return _productRestController;
    }

    public List<Product> getProductList() {
        return productList;
    }

    public Product getProductById(int id) {
        for (Product p : productList) {
            if (p.getId().equals(id)) {
                return p;
            }
        }
        return null;
    }

}

