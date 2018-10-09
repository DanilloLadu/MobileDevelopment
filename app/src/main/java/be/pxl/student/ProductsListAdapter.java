package be.pxl.student;

import android.content.Context;
import android.content.IntentFilter;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import be.pxl.student.layout.R;

public class ProductsListAdapter extends RecyclerView.Adapter<ProductsListAdapter.ProductListViewHolder> {
    private Cursor mCursor;
    private Context mContext;

    public ProductsListAdapter( Context mContext, Cursor mCursor) {
        this.mCursor = mCursor;
        this.mContext = mContext;
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

        viewHolder.productsTextView.setText(product);
    }

    @Override
    public int getItemCount() {
        if (mCursor == null){
            return 0;
        }
        return mCursor.getCount();
    }

    public void swapCursor(Cursor newCursor) {
        if (mCursor != null) mCursor.close();
        mCursor = newCursor;
        if (newCursor != null){
            this.notifyDataSetChanged();
        }
    }

    class ProductListViewHolder extends RecyclerView.ViewHolder {
        ListView listView;
        TextView productsTextView;

        public ProductListViewHolder(View itemView) {
            super(itemView);
            listView = (ListView) itemView.findViewById(R.id.articlesListView);
            productsTextView = (TextView) itemView.findViewById(R.id.list_item_articles_textview);
        }
    }
}
