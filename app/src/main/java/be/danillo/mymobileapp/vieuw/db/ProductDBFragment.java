package be.danillo.mymobileapp.vieuw.db;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import be.danillo.mymobileapp.R;
import be.danillo.mymobileapp.controller.ProductDBController;
import be.danillo.mymobileapp.controller.ProductRestController;
import be.danillo.mymobileapp.data.ApiOption;
import be.danillo.mymobileapp.domain.Ingredient;
import be.danillo.mymobileapp.domain.Product;


public class ProductDBFragment extends Fragment {
    private String TAG = ProductDBFragment.class.getSimpleName();
    private Product product;
    private RecyclerView recyclerView;
    private IngredientAdapter ingredientAdapter;
    private TextView myId, myName;
    private static final String ARG_PRODUCT_ID = "product_id";

    public static ProductDBFragment newInstance(int id) {
        Bundle args = new Bundle();
        args.putSerializable(ARG_PRODUCT_ID, id);
        ProductDBFragment fragment = new ProductDBFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // product = new Product();
        int productId = (int) getArguments().getSerializable(ARG_PRODUCT_ID);
        // met de nodige activiteit voor de productcontroller

        product = ProductDBController.get(getActivity()).getProductById(productId);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_product, container, false);

        myId = (TextView) v.findViewById(R.id.textViewID);
        myId.setText(product.getId().toString());
        myName = (TextView) v.findViewById(R.id.textViewName);
        myName.setText(product.getName());

        recyclerView = (RecyclerView) v.findViewById(R.id.ingredients_recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return v;
    }

    private void updateUI() {

        List<Ingredient> ingredientList = product.getIngredientList();
        for (Ingredient in : ingredientList) {
            for (String cl : ApiOption.controlList.split(":")) {

                if (cl.equalsIgnoreCase(in.getName())) {
                    Toast.makeText(getActivity(), in.getName() + " Alergie", Toast.LENGTH_LONG).show();
                    Log.i(TAG, in.getName());
                }

            }

        }


        ingredientAdapter = new IngredientAdapter(ingredientList);

        recyclerView.setAdapter(ingredientAdapter);
    }

    //ADAPTER
    private class IngredientAdapter extends RecyclerView.Adapter<IngredientHolder> {

        private List<Ingredient> ingredients;

        public IngredientAdapter(List<Ingredient> ingredients) {

            this.ingredients = ingredients;
        }

        @NonNull
        @Override
        public IngredientHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new IngredientHolder(layoutInflater, viewGroup);
        }

        @Override
        public void onBindViewHolder(@NonNull IngredientHolder ingredientHolder, int position) {
            ingredientHolder.Bind(ingredients.get(position));
        }

        @Override
        public int getItemCount() {
            return ingredients.size();
        }
    }

    //HOLDER
    private class IngredientHolder extends RecyclerView.ViewHolder {
        private TextView idTextView, titleTextView;
        private Ingredient ingredient;

        public IngredientHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_ingredient, parent, false));
            idTextView = (TextView) itemView.findViewById(R.id.textViewID);
            titleTextView = (TextView) itemView.findViewById(R.id.textViewName);
        }

        public void Bind(Ingredient ingredient) {
            this.ingredient = ingredient;
            idTextView.setText(this.ingredient.getId().toString());
            titleTextView.setText(this.ingredient.getName());

        }

    }
}
