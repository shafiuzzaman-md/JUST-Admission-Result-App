package just.cse.just_admission_result;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class MyDBHandler extends SQLiteOpenHelper {
    //information of database
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "a.sqlite";
    private static String DB_PATH = "";
    public static final String TABLE_NAME = "Sheet1";
    public static final String COLUMN_ID = "id";
    public static final String ROLL_TO = "roll_to";
    public static final String ROLL_FROM = "Roll_Number";
    private final Context mContext;
    private boolean mNeedUpdate = false;
    private SQLiteDatabase mDataBase;

    //initialize the database
    public MyDBHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        if (android.os.Build.VERSION.SDK_INT >= 17)
            DB_PATH = context.getApplicationInfo().dataDir + "/databases/";
        else
            DB_PATH = "/data/data/" + context.getPackageName() + "/databases/";
        this.mContext = context;

        copyDataBase();

        this.getReadableDatabase();
    }

    public void updateDataBase() throws IOException {
        if (mNeedUpdate) {
            File dbFile = new File(DB_PATH + DATABASE_NAME);
            if (dbFile.exists())
                dbFile.delete();

            copyDataBase();

            mNeedUpdate = false;
        }
    }

    private boolean checkDataBase() {
        File dbFile = new File(DB_PATH + DATABASE_NAME);
        return dbFile.exists();
    }

    private void copyDataBase() {
        if (!checkDataBase()) {
            this.getReadableDatabase();
            this.close();
            try {
                copyDBFile();
            } catch (IOException mIOException) {
                throw new Error("ErrorCopyingDataBase");
            }
        }
    }

    private void copyDBFile() throws IOException {
        //InputStream mInput = mContext.getAssets().open(DB_NAME);
        InputStream mInput = mContext.getResources().openRawResource(R.raw.a);
        OutputStream mOutput = new FileOutputStream(DB_PATH + DATABASE_NAME);
        byte[] mBuffer = new byte[1024];
        int mLength;
        while ((mLength = mInput.read(mBuffer)) > 0)
            mOutput.write(mBuffer, 0, mLength);
        mOutput.flush();
        mOutput.close();
        mInput.close();
    }

    public boolean openDataBase() throws SQLException {
        mDataBase = SQLiteDatabase.openDatabase(DB_PATH + DATABASE_NAME, null, SQLiteDatabase.CREATE_IF_NECESSARY);
        return mDataBase != null;
    }
    @Override
    public void onCreate(SQLiteDatabase db) {}
    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {}

    public String loadHandler() {
        String result = "";
        String query = "Select * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        while (cursor.moveToNext()) {
            int result_0 = cursor.getInt(0);
            String result_1 = cursor.getString(1);
            result += String.valueOf(result_0) + " " + result_1 +
                    System.getProperty("line.separator");
        }
        cursor.close();
        db.close();
        return result;
    }
    public void addHandler(SeatPlan plan) {}

    public String findHandler(int roll_number) {
        String seat_plan = null;
        String query = "Select * FROM " + TABLE_NAME + " WHERE " + ROLL_FROM + " = "  + roll_number;
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query, null);

        if (cursor.moveToFirst()) {
            seat_plan = "Congratulation! "+  cursor.getString(1) + "\n\n"+
                    "You are in the merit list of Unit " + cursor.getString(2) +"\n\n"+
                        "Merit Position: "+ cursor.getString(3) + "\n";
        }else{
            return  "Sorry!, You are not in the merit list.";
        }

        db.close();
        return seat_plan;
    }
   // public boolean deleteHandler(int ID) {}
    //public boolean updateHandler(int ID, String name) {}
}
