package cl.flores.nicolas.tripcost.activities;

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

import cl.flores.nicolas.tripcost.R;
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

    FloatingActionButton add = (FloatingActionButton) findViewById(R.id.add);
    if (add != null) {
      add.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          if (actualFragment instanceof TripsFragment) {
            Snackbar.make(view, "Add trip", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
          } else if (actualFragment instanceof FriendsFragment){
            Snackbar.make(view, "Add friend", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
          }
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

    Fragment trips = new TripsFragment();
    setFragment(trips);
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
    Snackbar.make(getCurrentFocus(), friend.toString(), Snackbar.LENGTH_LONG)
        .setAction("Action", null).show();
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
      toolbar.setTitle(R.string.trips_fragment);
    } else if (fragment instanceof FriendsFragment) {
      toolbar.setTitle(R.string.friends_fragment);
    }
  }
}
