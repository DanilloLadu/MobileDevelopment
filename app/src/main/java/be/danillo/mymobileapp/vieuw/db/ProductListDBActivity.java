package be.danillo.mymobileapp.vieuw.db;

import android.content.Intent;
import android.support.v4.app.Fragment;

import be.danillo.mymobileapp.R;
import be.danillo.mymobileapp.domain.Product;


import be.danillo.mymobileapp.vieuw.SingleFragmentActivity;


public class ProductListDBActivity extends SingleFragmentActivity implements ProductListDBFragment.Callbacks {


    @Override
    protected Fragment createFragment() {
        ProductListDBFragment temp = new ProductListDBFragment();
        return temp;
    }

    @Override
    protected int getLayoutResId() {
        return R.layout.activity_masterdetail;
    }

    @Override
    public void onProductSelected(Product product) {
        if (findViewById(R.id.detail_fragment_container) == null) {
            Intent intent = ProductDBActivity.newIntent(this, product.getId());
            startActivity(intent);
        } else {
            Fragment newDetail = ProductDBFragment.newInstance(product.getId());
            getSupportFragmentManager().beginTransaction().replace(R.id.detail_fragment_container, newDetail).commit();
        }
    }
}
