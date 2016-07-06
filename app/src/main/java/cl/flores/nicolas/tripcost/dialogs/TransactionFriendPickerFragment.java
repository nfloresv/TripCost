package cl.flores.nicolas.tripcost.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import java.util.List;

import cl.flores.nicolas.tripcost.R;
import cl.flores.nicolas.tripcost.database.Friend;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link OnTransactionFriendPickerListener} interface
 * to handle interaction events.
 * Use the {@link TransactionFriendPickerFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class TransactionFriendPickerFragment extends DialogFragment implements AdapterView.OnItemSelectedListener {
  private static final String ARG_TRIP_ID = "trip_id";

  private int mTripID;
  private List<Friend> friends;
  private EditText amount;

  private OnTransactionFriendPickerListener mListener;

  public TransactionFriendPickerFragment() {
    // Required empty public constructor
  }

  /**
   * Use this factory method to create a new instance of
   * this fragment using the provided parameters.
   *
   * @param trip_id The ID of the Trip.
   * @return A new instance of fragment TransactionFriendPickerFragment.
   */
  public static TransactionFriendPickerFragment newInstance(int trip_id) {
    TransactionFriendPickerFragment fragment = new TransactionFriendPickerFragment();
    Bundle args = new Bundle();
    args.putInt(ARG_TRIP_ID, trip_id);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      mTripID = getArguments().getInt(ARG_TRIP_ID);
    }
    friends = Friend.findWithQuery(Friend.class, getString(R.string.query_friends_from_friend_trip), String.valueOf(mTripID));
  }

  @NonNull
  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    LayoutInflater inflater = getActivity().getLayoutInflater();
    View layout = inflater.inflate(R.layout.fragment_transaction_friend_picker, null);

    amount = (EditText) layout.findViewById(R.id.transaction_friend_dialog_amount_et);
    ArrayAdapter<Friend> adapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_item, friends);
    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    Spinner spinner = (Spinner) layout.findViewById(R.id.transaction_friend_picker_friend_spinner);
    spinner.setAdapter(adapter);
    spinner.setOnItemSelectedListener(this);

    builder.setView(layout)
        .setTitle(R.string.pick_transaction_friend_dialog_title)
        .setPositiveButton(R.string.pick_transaction_friend_dialog_positive_button, new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            mListener.onFriendTransactionInteraction(null, 0);
          }
        })
        .setNegativeButton(R.string.pick_transaction_friend_dialog_negative_button, new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            TransactionFriendPickerFragment.this.getDialog().cancel();
          }
        });
    return super.onCreateDialog(savedInstanceState);
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    if (context instanceof OnTransactionFriendPickerListener) {
      mListener = (OnTransactionFriendPickerListener) context;
    } else {
      throw new RuntimeException(context.toString()
          + " must implement OnTransactionFriendPickerListener");
    }
  }

  @Override
  public void onDetach() {
    super.onDetach();
    mListener = null;
  }

  @Override
  public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    // An item was selected. You can retrieve the selected item using
    // parent.getItemAtPosition(pos)
  }

  @Override
  public void onNothingSelected(AdapterView<?> parent) {

  }

  /**
   * This interface must be implemented by activities that contain this
   * fragment to allow an interaction in this fragment to be communicated
   * to the activity and potentially other fragments contained in that
   * activity.
   * <p/>
   * See the Android Training lesson <a href=
   * "http://developer.android.com/training/basics/fragments/communicating.html"
   * >Communicating with Other Fragments</a> for more information.
   */
  public interface OnTransactionFriendPickerListener {
    void onFriendTransactionInteraction(Friend friend, double amount);
  }
}
