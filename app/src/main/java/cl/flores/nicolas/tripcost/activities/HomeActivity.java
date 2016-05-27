package cl.flores.nicolas.tripcost.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import cl.flores.nicolas.tripcost.R;
import cl.flores.nicolas.tripcost.common.Constants;
import cl.flores.nicolas.tripcost.database.Friend;
import cl.flores.nicolas.tripcost.database.Trip;
import cl.flores.nicolas.tripcost.fragments.FriendsFragment;
import cl.flores.nicolas.tripcost.fragments.TripsFragment;

public class HomeActivity extends AppCompatActivity
    implements NavigationView.OnNavigationItemSelectedListener,
    FriendsFragment.OnFriendListInteractionListener, TripsFragment.OnTripListInteractionListener {

  private DrawerLayout drawer;
  private Toolbar toolbar;
  private Fragment actualFragment;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_home);
    toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    FloatingActionButton add = (FloatingActionButton) findViewById(R.id.add_btn);
    if (add != null) {
      add.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          addButton(view);
        }
      });
    }

    drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
    ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
        this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
    drawer.setDrawerListener(toggle);
    toggle.syncState();

    NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
    if (navigationView != null) {
      navigationView.setNavigationItemSelectedListener(this);
    }

    if (savedInstanceState == null) {
      Fragment fragment = new TripsFragment();
      setFragment(fragment);
    }
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, Intent data) {
    super.onActivityResult(requestCode, resultCode, data);
    if (requestCode == Constants.ADD_FRIEND_REQUEST) {
      Fragment fragment = new FriendsFragment();
      setFragment(fragment);
      if (resultCode == Activity.RESULT_OK) {
        Toast.makeText(HomeActivity.this, R.string.friend_created_toast, Toast.LENGTH_SHORT).show();
      }
    } else if (requestCode == Constants.ADD_TRIP_REQUEST) {
      Fragment fragment = new TripsFragment();
      setFragment(fragment);
      if (resultCode == Activity.RESULT_OK) {
        Toast.makeText(HomeActivity.this, R.string.trip_created_toast, Toast.LENGTH_SHORT).show();
      }
    }
  }

  @Override
  protected void onSaveInstanceState(Bundle outState) {
    outState.putString(Constants.STATE_DISPLAYED_FRAGMENT, actualFragment.getClass().getSimpleName());
    super.onSaveInstanceState(outState);
  }

  @Override
  protected void onRestoreInstanceState(Bundle savedInstanceState) {
    super.onRestoreInstanceState(savedInstanceState);
    String className = savedInstanceState.getString(Constants.STATE_DISPLAYED_FRAGMENT);
    Fragment fragment = new TripsFragment();
    if (className != null && className.equals(FriendsFragment.class.getSimpleName())) {
      fragment = new FriendsFragment();
    }
    setFragment(fragment);
  }

  @Override
  public void onBackPressed() {
    if (drawer.isDrawerOpen(GravityCompat.START)) {
      drawer.closeDrawer(GravityCompat.START);
    } else {
      super.onBackPressed();
    }
  }

  @Override
  public boolean onCreateOptionsMenu(Menu menu) {
    // Inflate the menu; this adds items to the action bar if it is present.
    getMenuInflater().inflate(R.menu.home, menu);
    return true;
  }

  @Override
  public boolean onOptionsItemSelected(MenuItem item) {
    // Handle action bar item clicks here. The action bar will
    // automatically handle clicks on the Home/Up button, so long
    // as you specify a parent activity in AndroidManifest.xml.
    int id = item.getItemId();

    //noinspection SimplifiableIfStatement
    if (id == R.id.action_settings) {
      return true;
    }

    return super.onOptionsItemSelected(item);
  }

  @Override
  public boolean onNavigationItemSelected(MenuItem item) {
    // Handle navigation view item clicks here.
    int id = item.getItemId();

    if (id == R.id.nav_trips) {
      Fragment trips = new TripsFragment();
      setFragment(trips);
    } else if (id == R.id.nav_friends) {
      Fragment friends = new FriendsFragment();
      setFragment(friends);
    } else if (id == R.id.nav_settings) {

    }

    drawer.closeDrawer(GravityCompat.START);
    return true;
  }

  @Override
  public void onFriendListInteraction(Friend friend) {
    Intent intent = new Intent(HomeActivity.this, ViewFriendActivity.class);
    intent.putExtra(Constants.FRIEND_ID_MESSAGE, friend.getId());
    startActivity(intent);
  }

  @Override
  public void onTripListInteraction(Trip trip) {
    Snackbar.make(getCurrentFocus(), trip.toString(), Snackbar.LENGTH_LONG)
        .setAction("Action", null).show();
  }

  private void setFragment(Fragment fragment) {
    FragmentManager fragmentManager = getSupportFragmentManager();
    fragmentManager.beginTransaction().replace(R.id.content_frame, fragment).commit();
    actualFragment = fragment;
    if (fragment instanceof TripsFragment) {
      toolbar.setTitle(R.string.title_fragment_trips);
    } else if (fragment instanceof FriendsFragment) {
      toolbar.setTitle(R.string.title_fragment_friends);
    }
  }

  private void addButton(View view) {
    if (actualFragment instanceof TripsFragment) {
      Intent intent = new Intent(HomeActivity.this, NewTripActivity.class);
      startActivityForResult(intent, Constants.ADD_TRIP_REQUEST);
    } else if (actualFragment instanceof FriendsFragment) {
      Intent intent = new Intent(HomeActivity.this, NewFriendActivity.class);
      startActivityForResult(intent, Constants.ADD_FRIEND_REQUEST);
    }
  }
}
