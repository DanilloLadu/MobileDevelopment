package be.pxl.student;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.List;

import be.pxl.student.domain.Product;
import be.pxl.student.layout.DetailActivity;
import be.pxl.student.layout.R;

public class ProductsListAdapter extends RecyclerView.Adapter<ProductsListAdapter.ProductListViewHolder> {
    private Cursor mCursor;
    private Context mContext;
    private List<Product> productList;

    public ProductsListAdapter(Context mContext, Cursor mCursor, List<Product> productList) {
        this.mCursor = mCursor;
        this.mContext = mContext;
        this.productList = productList;
    }

    @NonNull
    @Override
    public ProductListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.fragment_main_list_item, parent, false);
        return new ProductListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ProductListViewHolder viewHolder, int position) {
        if (!mCursor.moveToPosition(position))
            return;

        String product = null;
        product = mCursor.getString(mCursor.getColumnIndex("name"));


        viewHolder.Bind(productList.get(position));

        viewHolder.productsTextView.setText(product);
    }

    @Override
    public int getItemCount() {
        if (mCursor == null) {
            return 0;
        }
        return mCursor.getCount();
    }

    public void swapCursor(Cursor newCursor) {
        if (mCursor != null) mCursor.close();
        mCursor = newCursor;
        if (newCursor != null) {
            this.notifyDataSetChanged();
        }
    }

    class ProductListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ListView listView;
        TextView productsTextView;
        private Toast mToast;
        Product product = new Product();

        public ProductListViewHolder(View itemView) {
            super(itemView);
            listView = (ListView) itemView.findViewById(R.id.articlesListView);
            productsTextView = (TextView) itemView.findViewById(R.id.list_item_articles_textview);

            itemView.setOnClickListener(this);
        }

        public void Bind(Product product) {
            this.product = product;
        }

        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            listView = (ListView) itemView.findViewById(R.id.articlesListView);

            if (mToast != null) {
                mToast.cancel();
            }

            String toastMessage = "Item #" + clickedPosition + " clicked.";
            mToast = Toast.makeText(mContext, toastMessage, Toast.LENGTH_LONG);
            mToast.show();

            Intent intent = new Intent(v.getContext(), DetailActivity.class);
            intent.putExtra("be.pxl.student.domain.ProductId", product.getId());
            mContext.startActivity(intent);
        }
    }
}
