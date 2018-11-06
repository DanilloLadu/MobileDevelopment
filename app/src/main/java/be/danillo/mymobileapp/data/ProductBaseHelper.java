package be.danillo.mymobileapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import be.danillo.mymobileapp.domain.ProductDbSchema.dbTables;

public class ProductBaseHelper extends SQLiteOpenHelper {
    public static final int VERSION = 1;
    public static final String DATABASENAME = "ScannedProducts.db";

    public ProductBaseHelper(Context context) {
        super(context, DATABASENAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+ dbTables.NAMEPRODUCT + "(" +
                " _id integer primary key autoincrement, " +
                dbTables.Cols.UUID + ", " +
                dbTables.Cols.NAME +
                ")"

        );
        db.execSQL("create table "+ dbTables.NAMEINGREDIENTS + "(" +
                " _id integer primary key autoincrement, " +
                dbTables.Cols.UUID + ", " +
                dbTables.Cols.NAME + ", " +
                dbTables.Cols.AMMOUNT +
                ")"

        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
