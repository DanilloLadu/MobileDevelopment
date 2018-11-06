package be.danillo.mymobileapp.vieuw;


import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

public class ProductActivity extends SingleFragmentActivity {

    private static final String EXTRA_PRODUCT_ID = "be.danillo.mymobileapp.productintent.product_id";

    public static Intent newIntent(Context packageContext ,int id){
        Intent intent = new Intent(packageContext, ProductActivity.class);
        //opslaan van id in de intent
        intent.putExtra(EXTRA_PRODUCT_ID, id);
        return intent;
    }

    @Override
    protected Fragment createFragment() {
        int productId = (int) getIntent().getSerializableExtra(EXTRA_PRODUCT_ID);
        return ProductFragment.newInstance(productId);
    }
}
