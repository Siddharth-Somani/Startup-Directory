package studios.inninc.startupai;

/**
 * Created by Pradyumna1 on 1/22/2017.
 */
import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LikeDBHandler extends SQLiteOpenHelper {

    // All Static variables
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "likesManager";

    // Likes table name
    private static final String TABLE_LIKE = "likes";

    // Likes Table Columns names
    private static final String KEY_ID = "id";
    private static final String KEY_EVENT = "event_name";
    private static final String KEY_LIKED = "liked";

    public LikeDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    // Creating Tables
    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LIKES_TABLE = "CREATE TABLE " + TABLE_LIKE + "("
                + KEY_ID + " INTEGER PRIMARY KEY," + KEY_EVENT + " TEXT,"
                + KEY_LIKED + " TEXT" + ")";
        db.execSQL(CREATE_LIKES_TABLE);
    }

    // Upgrading database
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LIKE);

        // Create tables again
        onCreate(db);
    }

    /**
     * All CRUD(Create, Read, Update, Delete) Operations
     */

    // Adding new Like
    void addLike(Like like) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_EVENT, like.get_event()); // Like Name
        values.put(KEY_LIKED, like.get_liked()); // Like Phone

        // Inserting Row
        db.insert(TABLE_LIKE, null, values);
        db.close(); // Closing database connection
    }

    // Getting single Like
    Like getLike(String eventName) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_LIKE, new String[] { KEY_ID,
                        KEY_EVENT, KEY_LIKED}, KEY_EVENT + "=?",
                new String[] { String.valueOf(eventName) }, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();

        if(cursor.getCount()==0){

            return null;
        }
        else {

            Like like = new Like(Integer.parseInt(cursor.getString(0)),
                    cursor.getString(1), cursor.getString(2));

            // return Like
            return like;

        }
    }

    // Deleting single Like
    public void deleteLike(Like Like) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_LIKE, KEY_ID + " = ?",
                new String[] { String.valueOf(Like.get_id()) });
        db.close();
    }


    // Getting Likes Count
    public int getLikesCount() {
        String countQuery = "SELECT  * FROM " + TABLE_LIKE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        // return count
        return cursor.getCount();
    }

}
