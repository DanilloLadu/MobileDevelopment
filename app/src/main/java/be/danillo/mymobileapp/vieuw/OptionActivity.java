package be.danillo.mymobileapp.vieuw;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import be.danillo.mymobileapp.R;
import be.danillo.mymobileapp.data.ApiOption;

public class OptionActivity extends AppCompatActivity {
    private String TAG = OptionActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_option);
        ((EditText) findViewById(R.id.editTextOption)).setText(ApiOption.controlList);
    }


    public void updateOption(View view) {
        EditText edittext = (EditText) findViewById(R.id.editTextOption);
        new ApiOption(this).setControlList(edittext.getText().toString(), this);

        Log.i(TAG, edittext.getText().toString());
    }
}
