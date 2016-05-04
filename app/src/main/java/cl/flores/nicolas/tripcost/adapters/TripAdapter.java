package cl.flores.nicolas.tripcost.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cl.flores.nicolas.tripcost.R;
import cl.flores.nicolas.tripcost.database.Trip;
import cl.flores.nicolas.tripcost.fragments.TripFragment.OnTripListInteractionListener;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Trip} and makes a call to the
 * specified {@link OnTripListInteractionListener}.
 */
public class TripAdapter extends RecyclerView.Adapter<TripAdapter.ViewHolder> {

  private final List<Trip> mValues;
  private final OnTripListInteractionListener mListener;

  public TripAdapter(OnTripListInteractionListener listener) {
    mValues = Trip.listAll(Trip.class);
    mListener = listener;
  }

  public TripAdapter(List<Trip> items, OnTripListInteractionListener listener) {
    mValues = items;
    mListener = listener;
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.fragment_trip, parent, false);
    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(final ViewHolder holder, int position) {
    holder.trip = mValues.get(position);
    holder.tripId.setText(String.valueOf(mValues.get(position).getId()));
    holder.tripName.setText(mValues.get(position).getName());
    holder.tripDescription.setText(mValues.get(position).getDescription());
    holder.tripDate.setText(mValues.get(position).getStart().toString());

    holder.mView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (null != mListener) {
          // Notify the active callbacks interface (the activity, if the
          // fragment is attached to one) that an item has been selected.
          mListener.onTripListInteraction(holder.trip);
        }
      }
    });
  }

  @Override
  public int getItemCount() {
    return mValues.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
    public final View mView;
    public final TextView tripId;
    public final TextView tripName;
    public final TextView tripDescription;
    public final TextView tripDate;
    public Trip trip;

    public ViewHolder(View view) {
      super(view);
      mView = view;
      tripId = (TextView) view.findViewById(R.id.trip_id);
      tripName = (TextView) view.findViewById(R.id.trip_name);
      tripDescription = (TextView) view.findViewById(R.id.trip_description);
      tripDate = (TextView) view.findViewById(R.id.trip_date);
    }

    @Override
    public String toString() {
      return super.toString() + " '" + tripName.getText() + "'";
    }
  }
}
