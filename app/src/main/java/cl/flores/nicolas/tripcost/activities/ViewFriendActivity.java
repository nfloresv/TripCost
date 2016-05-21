package cl.flores.nicolas.tripcost.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.TextView;

import cl.flores.nicolas.tripcost.R;
import cl.flores.nicolas.tripcost.common.Constants;
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
    friendId = intent.getLongExtra(Constants.FRIEND_ID_MESSAGE, Constants.DEFAULT_ID);
    if (friendId == Constants.DEFAULT_ID) {
      finish();
    }

    FloatingActionButton editBtn = (FloatingActionButton) findViewById(R.id.edit_friend);
    if (editBtn != null) {
      editBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view) {
          Intent intent = new Intent(ViewFriendActivity.this, EditFriendActivity.class);
          intent.putExtra(Constants.FRIEND_ID_MESSAGE, friendId);
          startActivityForResult(intent, Constants.EDIT_FRIEND_REQUEST);
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
    Friend friend = Friend.findById(Friend.class, friendId);
    TextView nameTV = (TextView) findViewById(R.id.view_friend_name_tv);
    if (friend != null && nameTV != null) {
      nameTV.setText(friend.getName());
    }
  }
}
