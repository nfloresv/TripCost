package cl.flores.nicolas.tripcost.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import cl.flores.nicolas.tripcost.R;
import cl.flores.nicolas.tripcost.adapters.FriendAdapter;
import cl.flores.nicolas.tripcost.common.Constants;
import cl.flores.nicolas.tripcost.database.Friend;
import cl.flores.nicolas.tripcost.database.Trip;

public class ViewTripActivity extends AppCompatActivity {

  private long tripId;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_view_trip);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    Intent intent = getIntent();
    tripId = intent.getLongExtra(Constants.TRIP_ID_MESSAGE, Constants.DEFAULT_ID);
    if (tripId == Constants.DEFAULT_ID) {
      finish();
    }

    FloatingActionButton addBtn = (FloatingActionButton) findViewById(R.id.add_transaction);
    if (addBtn != null) {
      addBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          Intent intent = new Intent(ViewTripActivity.this, NewTransactionActivity.class);
          intent.putExtra(Constants.TRIP_ID_MESSAGE, tripId);
          startActivity(intent);
        }
      });
    }
    if (getSupportActionBar() != null) {
      getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
  }

  @Override
  protected void onStart() {
    super.onStart();
    final SimpleDateFormat dateFormat = new SimpleDateFormat(Constants.DATE_FORMAT, Locale.getDefault());
    Trip trip = Trip.findById(Trip.class, tripId);

    TextView nameTV = (TextView) findViewById(R.id.view_trip_name_tv);
    if (nameTV != null) {
      nameTV.setText(trip.getName());
    }
    TextView descriptionTV = (TextView) findViewById(R.id.view_trip_description_tv);
    if (descriptionTV != null) {
      descriptionTV.setText(trip.getDescription());
    }
    TextView startDateTV = (TextView) findViewById(R.id.view_trip_start_date_tv);
    if (startDateTV != null) {
      Date start = trip.getStart();
      startDateTV.setText(dateFormat.format(start));
    }
    TextView endDateTv = (TextView) findViewById(R.id.view_trip_end_date_tv);
    if (endDateTv != null) {
      Date end = trip.getEnd();
      endDateTv.setText(dateFormat.format(end));
    }

    View view = findViewById(R.id.view_trip_participants_rv);
    if (view instanceof RecyclerView) {
      List<Friend> friends = Friend.findWithQuery(Friend.class, getString(R.string.query_friends_from_friend_trip), String.valueOf(tripId));
      Context context = getApplicationContext();
      RecyclerView recyclerView = (RecyclerView) view;
      recyclerView.setLayoutManager(new LinearLayoutManager(context));
      recyclerView.setAdapter(new FriendAdapter(friends, null));
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    super.onCreateOptionsMenu(menu);
    getMenuInflater().inflate(R.menu.trip, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    int id = item.getItemId();

    if (id == R.id.action_view_transactions) {
      Snackbar.make(getCurrentFocus(), "Replace with your own action", Snackbar.LENGTH_LONG)
          .setAction("Action", null).show();
      return true;
    } else if (id == R.id.action_edit_trip) {
      Snackbar.make(getCurrentFocus(), "Replace with your own action", Snackbar.LENGTH_LONG)
          .setAction("Action", null).show();
      return true;
    } else if (id == R.id.action_add_transaction) {
      Intent intent = new Intent(ViewTripActivity.this, NewTransactionActivity.class);
      intent.putExtra(Constants.TRIP_ID_MESSAGE, tripId);
      startActivity(intent);
      return true;
    }

    return super.onOptionsItemSelected(item);
  }
}
