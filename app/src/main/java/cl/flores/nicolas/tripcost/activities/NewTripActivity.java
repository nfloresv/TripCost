package cl.flores.nicolas.tripcost.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import cl.flores.nicolas.tripcost.R;
import cl.flores.nicolas.tripcost.common.Constants;
import cl.flores.nicolas.tripcost.common.DatePickerFragment;
import cl.flores.nicolas.tripcost.database.Trip;

public class NewTripActivity extends AppCompatActivity {
  private DatePickerFragment startDate;
  private DatePickerFragment endDate;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_compose_trip);

    final Calendar calendar = Calendar.getInstance();
    final SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DATE_FORMAT, Locale.getDefault());

    startDate = DatePickerFragment.newInstance(R.id.compose_trip_start_date_et);
    endDate = DatePickerFragment.newInstance(R.id.compose_trip_end_date_et);

    EditText startDateET = (EditText) findViewById(R.id.compose_trip_start_date_et);
    if (startDateET != null) {
      startDateET.setHint(dateFormat.format(calendar.getTime()));
      startDateET.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          startDate.show(getSupportFragmentManager(), Constants.COMPOSE_TRIP_START_DATE_PICKER);
        }
      });
    }

    EditText endDateET = (EditText) findViewById(R.id.compose_trip_end_date_et);
    if (endDateET != null) {
      endDateET.setHint(dateFormat.format(calendar.getTime()));
      endDateET.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          endDate.show(getSupportFragmentManager(), Constants.COMPOSE_TRIP_END_DATE_PICKER);
        }
      });
    }

    ImageButton saveBtn = (ImageButton) findViewById(R.id.save_trip_btn);
    if (saveBtn != null) {
      saveBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          saveTrip(v);
        }
      });
    }

    ImageButton cancelBtn = (ImageButton) findViewById(R.id.cancel_trip_btn);
    if (cancelBtn != null) {
      cancelBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          cancelTrip(v);
        }
      });
    }
  }

  public void saveTrip(View view) {
    EditText nameET = (EditText) this.findViewById(R.id.compose_trip_name_et);
    EditText descriptionET = (EditText) this.findViewById(R.id.compose_trip_description_et);

    if (nameET == null || nameET.getText().length() == 0) {
      Toast.makeText(NewTripActivity.this, R.string.error_trip_name_toast, Toast.LENGTH_SHORT).show();
      return;
    } else if (startDate.getDate() == null || endDate.getDate() == null) {
      Toast.makeText(NewTripActivity.this, R.string.error_trip_date_toast, Toast.LENGTH_SHORT).show();
      return;
    }

    String name = nameET.getText().toString();
    String description = descriptionET != null ? descriptionET.getText().toString() : "";
    Date start = startDate.getDate().getTime();
    Date end = endDate.getDate().getTime();

    Trip trip = new Trip(name, description, start, end);
//    if (trip.save() <= 0) {
//      Toast.makeText(NewTripActivity.this, R.string.error_saving_trip_toast, Toast.LENGTH_LONG).show();
//      return;
//    }

    setResult(Activity.RESULT_OK);
    finish();
  }

  public void cancelTrip(View view) {
    setResult(Activity.RESULT_CANCELED);
    finish();
  }

//  https://developer.android.com/guide/topics/ui/dialogs.html?hl=es
}
