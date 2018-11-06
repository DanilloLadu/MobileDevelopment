package be.danillo.mymobileapp.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorWrapper;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.util.Log;


import java.util.ArrayList;
import java.util.List;

import be.danillo.mymobileapp.data.ProductBaseHelper;
import be.danillo.mymobileapp.domain.Ingredient;
import be.danillo.mymobileapp.domain.Product;
import be.danillo.mymobileapp.domain.ProductDbSchema.dbTables;


public class ProductDBController {
    private static final String TAG = ProductDBController.class.getSimpleName();
    private SQLiteDatabase sqLiteDatabase;
    private Context context;
    private static ProductDBController _productDBController;


    public ProductDBController(Context context) {
        this.context = context.getApplicationContext();
        sqLiteDatabase = new ProductBaseHelper(this.context).getWritableDatabase();
    }


    public static ProductDBController get(Context context) {
        if (_productDBController == null) {
            _productDBController = new ProductDBController(context);
        }
        return _productDBController;
    }

    public List<Product> getProductList() {

        List<Product> productList = new ArrayList<>();

        ProductCursorWrapper cursorProduct = querryCursor(dbTables.NAMEPRODUCT, null, null);


        try {
            cursorProduct.moveToFirst();
            while (!cursorProduct.isAfterLast()) {


                Log.i(TAG, dbTables.NAMEINGREDIENTS + " - " + dbTables.Cols.UUID + " = ? " + cursorProduct.getProduct().getId().toString());


                Product product = cursorProduct.getProduct();
                product.setIngredientList(getIngredients(product.getId()));
                productList.add(product);
                cursorProduct.moveToNext();
            }
        } finally {
            cursorProduct.close();
        }
        //Log.i(TAG, productList.get(0).getIngredientList().get(0).getName());
        return productList;
    }

    public Product getProductById(int id) {
        ProductCursorWrapper cursor = querryCursor(dbTables.NAMEPRODUCT, dbTables.Cols.UUID + " = ?",
                new String[]{String.valueOf(id)});
        try {
            if (cursor.getCount() == 0) {
                return null;
            }
            cursor.moveToFirst();

            Product product = cursor.getProduct();
            product.setIngredientList(getIngredients(product.getId()));

            return product;

        } finally {
            cursor.close();
        }

    }

    private List<Ingredient> getIngredients(int id) {
        List<Ingredient> ingredientList = new ArrayList<>();
        ProductCursorWrapper cursorIngredient = querryCursor(dbTables.NAMEINGREDIENTS, dbTables.Cols.UUID + " = ?",
                new String[]{String.valueOf(id)});
        try {
            cursorIngredient.moveToFirst();
            while (!cursorIngredient.isAfterLast()) {

                ingredientList.add(cursorIngredient.getIngredient());
                cursorIngredient.moveToNext();
            }
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        } finally {
            cursorIngredient.close();
        }
        return ingredientList;
    }



    public void addProduct(Product product) {
        if (getProductById(product.getId()) == null) {
            for (Ingredient ingredient : product.getIngredientList()) {
                sqLiteDatabase.insert(dbTables.NAMEINGREDIENTS, null, getContentValue(product, ingredient));
            }

            sqLiteDatabase.insert(dbTables.NAMEPRODUCT, null, getContentValue(product));
        }

    }


    private static ContentValues getContentValue(Product product) {
        ContentValues values = new ContentValues();
        values.put(dbTables.Cols.UUID, product.getId().toString());
        values.put(dbTables.Cols.NAME, product.getName());
        return values;
    }


    private static ContentValues getContentValue(Product product, Ingredient ingredient) {
        ContentValues values = new ContentValues();
        values.put(dbTables.Cols.UUID, product.getId().toString());
        values.put(dbTables.Cols.NAME, ingredient.getName());
        values.put(dbTables.Cols.AMMOUNT, ingredient.getAmmount());
        return values;
    }

    @NonNull
    private ProductCursorWrapper querryCursor(String table, String whereClause, String[] whereArgs) {
        Cursor cursor = sqLiteDatabase.query(table,
                null,
                whereClause,
                whereArgs,
                null,
                null,
                null
        );
        return new ProductCursorWrapper(cursor);
    }


    public class ProductCursorWrapper extends CursorWrapper {

        public ProductCursorWrapper(Cursor cursor) {
            super(cursor);
        }

        public Product getProduct() {
            int id = getInt(getColumnIndex(dbTables.Cols.UUID));
            String name = getString(getColumnIndex(dbTables.Cols.NAME));
            return new Product(id, name, null);
        }

        public Ingredient getIngredient() {
            int id = getInt(getColumnIndex(dbTables.Cols.UUID));
            String name = getString(getColumnIndex(dbTables.Cols.NAME));
            double ammount = Double.parseDouble(getString(getColumnIndex(dbTables.Cols.AMMOUNT)));
            return new Ingredient(id, name, ammount);
        }
    }
}
