package cl.flores.nicolas.tripcost.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import cl.flores.nicolas.tripcost.R;
import cl.flores.nicolas.tripcost.common.Constants;
import cl.flores.nicolas.tripcost.dialogs.DatePickerFragment;
import cl.flores.nicolas.tripcost.database.Friend;

public class NewTransactionActivity extends AppCompatActivity {
  private long tripId;
  private DatePickerFragment whenET;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_new_transaction);

    final Calendar calendar = Calendar.getInstance();
    final SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DATE_FORMAT, Locale.getDefault());

    Intent intent = getIntent();
    tripId = intent.getLongExtra(Constants.TRIP_ID_MESSAGE, Constants.DEFAULT_ID);
    if (tripId == Constants.DEFAULT_ID) {
      finish();
    }

    whenET = DatePickerFragment.newInstance(R.id.new_transaction_when_et);
    EditText startDateET = (EditText) findViewById(R.id.new_transaction_when_et);
    if (startDateET != null) {
      startDateET.setHint(dateFormat.format(calendar.getTime()));
      startDateET.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          whenET.show(getSupportFragmentManager(), Constants.COMPOSE_TRIP_START_DATE_PICKER);
        }
      });
    }
  }
}
