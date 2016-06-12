package cl.flores.nicolas.tripcost.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;

import java.util.ArrayList;
import java.util.List;

import cl.flores.nicolas.tripcost.R;
import cl.flores.nicolas.tripcost.database.Friend;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link NoticeDialogListener} interface
 * to handle interaction events.
 * Use the {@link FriendPickerDialogFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FriendPickerDialogFragment extends DialogFragment {
  private static final String ARG_EDIT_TRIP = "edit_trip";
  private static final String ARG_TRIP_ID = "trip_id";

  private boolean mEditTrip;
  private int mTripID;
  private List<Friend> friendList;
  private ArrayList<Integer> selectedItems;
  private boolean[] selectedItemsState;

  private NoticeDialogListener mListener;

  public FriendPickerDialogFragment() {
    // Required empty public constructor
  }

  /**
   * Use this factory method to create a new instance of
   * this fragment using the provided parameters.
   *
   * @return A new instance of fragment FriendPickerDialogFragment.
   */
  public static FriendPickerDialogFragment newInstance() {
    return FriendPickerDialogFragment.newInstance(false, 0);
  }

  /**
   * Use this factory method to create a new instance of
   * this fragment using the provided parameters.
   *
   * @param editTrip Define if this Dialog is going to be used in a new Trip Activity.
   * @param tripID   The ID of Trip.
   * @return A new instance of fragment FriendPickerDialogFragment.
   */
  public static FriendPickerDialogFragment newInstance(boolean editTrip, int tripID) {
    FriendPickerDialogFragment fragment = new FriendPickerDialogFragment();
    Bundle args = new Bundle();
    args.putBoolean(ARG_EDIT_TRIP, editTrip);
    args.putInt(ARG_TRIP_ID, tripID);
    fragment.setArguments(args);
    return fragment;
  }

  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
      mEditTrip = getArguments().getBoolean(ARG_EDIT_TRIP);
      mTripID = getArguments().getInt(ARG_TRIP_ID);
    }
    friendList = Friend.listAll(Friend.class);
    selectedItems = new ArrayList<>();
    selectedItemsState = new boolean[friendList.size()];
    for (int i = 0; i < selectedItemsState.length; i++) {
      selectedItemsState[i] = false;
    }
    if (mEditTrip) {
      // TODO generate boolean array of selected items
    }
  }

  @NonNull
  @Override
  public Dialog onCreateDialog(Bundle savedInstanceState) {
    // TODO search why the dialog don't persist the selected elements
    String[] friends = new String[friendList.size()];
    for (int i = 0; i < friendList.size(); i++) {
      friends[i] = friendList.get(i).getName();
    }

    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
    builder.setTitle(R.string.pick_friend_dialog_title)
        .setMultiChoiceItems(friends, selectedItemsState, new DialogInterface.OnMultiChoiceClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which, boolean isChecked) {
            if (isChecked) {
              selectedItems.add(which);
            } else if (selectedItems.contains(which)) {
              selectedItems.remove(Integer.valueOf(which));
            }
            selectedItemsState[which] = isChecked;
          }
        })
        .setPositiveButton(R.string.pick_friend_dialog_positive_button, new DialogInterface.OnClickListener() {
          @Override
          public void onClick(DialogInterface dialog, int which) {
            if (mListener != null) {
              mListener.onDialogPositiveClick(FriendPickerDialogFragment.this, friendList, selectedItems);
            }
          }
        })
        .setNegativeButton(R.string.pick_friend_dialog_negative_button, null);
    return builder.create();
  }

  @Override
  public void onAttach(Context context) {
    super.onAttach(context);
    if (context instanceof NoticeDialogListener) {
      mListener = (NoticeDialogListener) context;
    } else {
      throw new RuntimeException(context.toString()
          + " must implement NoticeDialogListener");
    }
  }

  @Override
  public void onDetach() {
    super.onDetach();
    mListener = null;
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
  public interface NoticeDialogListener {
    void onDialogPositiveClick(DialogFragment dialog, List<Friend> friendList, ArrayList<Integer> selectedItems);
  }
}
