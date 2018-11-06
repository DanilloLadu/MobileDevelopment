package be.danillo.mymobileapp.vieuw.db;


import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import be.danillo.mymobileapp.vieuw.SingleFragmentActivity;

public class ProductDBActivity extends SingleFragmentActivity {

    private static final String EXTRA_PRODUCT_ID = "be.danillo.mymobileapp.productintent.product_id";

    public static Intent newIntent(Context packageContext ,int id){
        Intent intent = new Intent(packageContext, ProductDBActivity.class);
        //opslaan van id in de intent
        intent.putExtra(EXTRA_PRODUCT_ID, id);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        int productId = (int) getIntent().getSerializableExtra(EXTRA_PRODUCT_ID);
        return ProductDBFragment.newInstance(productId);
    }
}
