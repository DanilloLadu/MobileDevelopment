package be.pxl.student.layout;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

import be.pxl.student.domain.Ingredient;
import be.pxl.student.domain.Product;

/**
 * A placeholder fragment containing a simple view.
 */
public class MainActivityFragment extends Fragment {

    public MainActivityFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ArrayAdapter<Product> mArticlesAdapter;


        Product[] data = makeProducts(15);
        List<Product> articles = new ArrayList<Product>(Arrays.asList(data));

        mArticlesAdapter = new ArrayAdapter<Product>(
                getActivity(), R.layout.list_item_articles_textview, R.id.list_item_articles_textview, articles
        );

        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        ListView listView = (ListView) rootView.findViewById(R.id.articlesListView);
        listView.setAdapter(mArticlesAdapter);


        return rootView;
    }

    public Product[] makeProducts(int amount){
        Product[] products = new Product[amount];
        Set<Ingredient> ingredients = new HashSet<>();
        Random rand = new Random();

        for (int i = 0; i < amount; i++){

            byte[] array = new byte[7]; // length is bounded by 7
            new Random().nextBytes(array);
            String generatedString = new String(array, Charset.forName("UTF-8"));


            for (int j = 0; j < amount; j++) {
                byte[] array2 = new byte[7]; // length is bounded by 7
                new Random().nextBytes(array);
                String generatedString2 = new String(array2, Charset.forName("UTF-8"));

                Ingredient ingredient = new Ingredient();
                ingredient.setAmmount(rand.nextDouble());
                ingredient.setName("ingredient " + j);

                ingredients.add(ingredient);
            }




            Product cola = new Product();
            cola.setName("Cola " + i);
            cola.setIngredientList(ingredients);
            cola.setId(rand.nextInt());
            products[i] = cola;
        }

        return products;
    }
}
