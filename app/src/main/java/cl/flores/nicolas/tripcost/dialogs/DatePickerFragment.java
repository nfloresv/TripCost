package cl.flores.nicolas.tripcost.dialogs;


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
 * A simple {@link Fragment} subclass to select a date.
 * Use the {@link DatePickerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener {
  private static final String ARG_EDITTEXT_ID = "edittext_id";

  private Calendar date;
  private int editTextID;

  public DatePickerFragment() {
  }

  /**
   * Use this factory method to create a new instance of
   * this fragment using the provided parameters.
   *
   * @param edittext_id The ID of the target Edit Text.
   * @return A new instance of fragment DatePickerFragment.
   */
  public static DatePickerFragment newInstance(int edittext_id) {
    DatePickerFragment fragment = new DatePickerFragment();
    Bundle args = new Bundle();
    args.putInt(ARG_EDITTEXT_ID, edittext_id);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      editTextID = getArguments().getInt(ARG_EDITTEXT_ID);
    }
    date = null;
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
