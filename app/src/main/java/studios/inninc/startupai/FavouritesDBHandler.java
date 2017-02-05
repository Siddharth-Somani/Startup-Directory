package studios.inninc.startupai;

/**
 * Created by Pradyumna1 on 1/22/2017.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class FavouritesDBHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "favManager";

    // Favouritess table name
    private static final String TABLE_FAV = "Favourites";

    // Favouritess Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_EVENT = "event_name";
    private static final String KEY_CATEGORY = "category";

    public FavouritesDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_FavouritesS_TABLE = "CREATE TABLE " + TABLE_FAV + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_EVENT + " TEXT,"
                + KEY_CATEGORY + " TEXT" + ")";
        db.execSQL(CREATE_FavouritesS_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_FAV);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new Favourites
    void addFavourites(Favourites favourites) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_EVENT, favourites.get_event()); // Favourites Name
        values.put(KEY_CATEGORY, favourites.get_cat()); // Favourites Phone

        // Inserting Row
        db.insert(TABLE_FAV, null, values);
        db.close(); // Closing database connection
    }

    // Getting single Favourites
    Favourites getFavourites(String eventName) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_FAV, new String[] { KEY_ID,
                        KEY_EVENT, KEY_CATEGORY}, KEY_EVENT + "=?",
                new String[] { String.valueOf(eventName) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        if(cursor.getCount()==0){

            return null;
        }
        else {

            Favourites favourites = new Favourites(Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1), cursor.getString(2));

            // return Favourites
            return favourites;

        }
    }

    // Deleting single Favourites
    public void deleteFavourites(Favourites Favourites) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_FAV, KEY_ID + " = ?",
                new String[] { String.valueOf(Favourites.get_id()) });
        db.close();
    }


    // Getting Favouritess Count
    public int getFavouritessCount() {
        String countQuery = "SELECT  * FROM " + TABLE_FAV;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        // return count
        return cursor.getCount();
    }

}

