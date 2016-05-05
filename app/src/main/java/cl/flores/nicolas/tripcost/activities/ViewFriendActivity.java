package cl.flores.nicolas.tripcost.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import cl.flores.nicolas.tripcost.R;
import cl.flores.nicolas.tripcost.database.Friend;

public class ViewFriendActivity extends AppCompatActivity {

  private long friendId;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_view_friend);
    Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
    setSupportActionBar(toolbar);

    Intent intent = getIntent();
    friendId = intent.getLongExtra(HomeActivity.FRIEND_ID_MESSAGE, 0);

    FloatingActionButton editBtn = (FloatingActionButton) findViewById(R.id.edit_friend);
    if (editBtn != null) {
      editBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
              .setAction("Action", null).show();
        }
      });
    }
    getSupportActionBar().setDisplayHomeAsUpEnabled(true);
  }

  @Override
  protected void onStart() {
    super.onStart();
    Friend friend = Friend.findById(Friend.class, friendId);
    TextView nameTV = (TextView) findViewById(R.id.view_friend_name_tv);
    if (friend != null && nameTV != null) {
      nameTV.setText(friend.getName());
    }
  }
}
