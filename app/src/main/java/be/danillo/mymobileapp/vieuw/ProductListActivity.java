package be.danillo.mymobileapp.vieuw;

import android.content.Intent;
import android.support.v4.app.Fragment;

import be.danillo.mymobileapp.R;
import be.danillo.mymobileapp.controller.ProductRestController;
import be.danillo.mymobileapp.domain.Product;


public class ProductListActivity extends SingleFragmentActivity implements ProductListFragment.Callbacks {


    @Override
    protected Fragment createFragment() {
        ProductListFragment temp = new ProductListFragment();
        return temp;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_masterdetail;
    }

    @Override
    public void onProductSelected(Product product) {
        if (findViewById(R.id.detail_fragment_container) == null) {
            Intent intent = ProductActivity.newIntent(this, product.getId());
            startActivity(intent);
        } else {
            Fragment newDetail = ProductFragment.newInstance(product.getId());
            getSupportFragmentManager().beginTransaction().replace(R.id.detail_fragment_container, newDetail).commit();
        }
    }
}
