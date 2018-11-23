package just.cse.just_admission_result;


import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    private TextView result = null;
    private EditText rollNumber = null;
    private MyDBHandler mDBHelper;
    private SQLiteDatabase mDb;
    private int mCounter = 0;
    private TextView header = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        result = (TextView) findViewById(R.id.result);
        rollNumber = (EditText) findViewById(R.id.Roll);
        header = (TextView) findViewById(R.id.textView);

        mDBHelper = new MyDBHandler(this);

        try {
            mDBHelper.updateDataBase();
        } catch (IOException mIOException) {
            throw new Error("UnableToUpdateDatabase");
        }

        try {
            mDb = mDBHelper.getWritableDatabase();
        } catch (SQLException mSQLException) {
            throw mSQLException;
        }
    }


    public void ViewSeat(View view) {
        result.setText("");
        String sRoll = rollNumber.getText().toString();
        if (TextUtils.isEmpty(sRoll)) {
            return;
        }
        int roll = Integer.parseInt(sRoll);
        String plan = mDBHelper.findHandler(roll);

        result.setText(plan);
        //header.setText("" + mCounter++);
    }
}
