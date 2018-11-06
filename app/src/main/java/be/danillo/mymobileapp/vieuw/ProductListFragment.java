package be.danillo.mymobileapp.vieuw;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import be.danillo.mymobileapp.R;
import be.danillo.mymobileapp.controller.ProductRestController;
import be.danillo.mymobileapp.domain.Product;

public class ProductListFragment extends Fragment {

    private RecyclerView recyclerView;
    private ProductAdapter productAdapter;
    private Callbacks callbacks;

    public interface Callbacks {
        void onProductSelected(Product product);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callbacks = (Callbacks) context;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_products_list, container, false);
        recyclerView = (RecyclerView) v.findViewById(R.id.products_recycle_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateUI();
        return v;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        callbacks = null;
    }

    private void updateUI() {
        //ProductRestController productController = new ProductRestController(getActivity());
        //productlijst
        //List<Product> products = MainActivity.listProducts;
        //get(getActivity()).getProductList()
        if (productAdapter == null) {
            productAdapter = new ProductAdapter(ProductRestController.get(getActivity()).getProductList());

            recyclerView.setAdapter(productAdapter);
        } else {
            productAdapter.notifyDataSetChanged();
        }
    }

    //ADAPTER
    private class ProductAdapter extends RecyclerView.Adapter<ProductHolder> {

        private List<Product> products;

        public ProductAdapter(List<Product> products) {

            this.products = products;
        }

        @NonNull
        @Override
        public ProductHolder onCreateViewHolder(ViewGroup viewGroup, int position) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new ProductHolder(layoutInflater, viewGroup);
        }

        @Override
        public void onBindViewHolder(ProductHolder productHolder, int position) {
            productHolder.Bind(products.get(position));
        }

        @Override
        public int getItemCount() {
            return products.size();
        }
    }

    //HOLDER
    private class ProductHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView idTextView, titleTextView;
        private Product product;

        public ProductHolder(LayoutInflater inflater, ViewGroup parent) {
            super(inflater.inflate(R.layout.list_item_product, parent, false));
            itemView.setOnClickListener(this);
            idTextView = (TextView) itemView.findViewById(R.id.textViewID);
            titleTextView = (TextView) itemView.findViewById(R.id.textViewName);
        }

        public void Bind(Product product) {
            this.product = product;
            idTextView.setText(product.getId().toString());
            titleTextView.setText(product.getName());

        }

        @Override
        public void onClick(View v) {
            callbacks.onProductSelected(product);
        }
    }
}
