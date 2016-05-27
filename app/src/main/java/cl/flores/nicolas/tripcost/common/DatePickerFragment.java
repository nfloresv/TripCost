package cl.flores.nicolas.tripcost.common;


import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.widget.DatePicker;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import cl.flores.nicolas.tripcost.common.Constants;

/**
 * A simple {@link Fragment} subclass.
 */
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {

  private Calendar date;
  private int editTextID;

  // TODO convert to factory method
  public DatePickerFragment(int editTextID) {
    date = null;
    this.editTextID = editTextID;
  }

  @NonNull
  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    final Calendar calendar = Calendar.getInstance();
    int year = calendar.get(Calendar.YEAR);
    int month = calendar.get(Calendar.MONTH);
    int day = calendar.get(Calendar.DAY_OF_MONTH);

    return new DatePickerDialog(getActivity(), this, year, month, day);
  }

  @Override
  public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
    date = Calendar.getInstance();
    date.set(year, monthOfYear, dayOfMonth);

    EditText dateET = (EditText) getActivity().findViewById(editTextID);
    SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DATE_FORMAT, Locale.getDefault());
    dateET.setText(dateFormat.format(date.getTime()));
  }

  public Calendar getDate() {
    return date;
  }
}
