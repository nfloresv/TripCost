package cl.flores.nicolas.tripcost.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import cl.flores.nicolas.tripcost.R;
import cl.flores.nicolas.tripcost.common.Constants;
import cl.flores.nicolas.tripcost.database.Friend;

public class EditFriendActivity extends AppCompatActivity {
  private long friendId;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_compose_friend);

    Intent intent = getIntent();
    friendId = intent.getLongExtra(Constants.FRIEND_ID_MESSAGE, Constants.DEFAULT_ID);
    if (friendId == Constants.DEFAULT_ID) {
      finish();
    }

    ImageButton saveBtn = (ImageButton) findViewById(R.id.save_friend_btn);
    if (saveBtn != null) {
      saveBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          saveFriend(v);
        }
      });
    }

    ImageButton cancelBtn = (ImageButton) findViewById(R.id.cancel_friend_btn);
    if (cancelBtn != null) {
      cancelBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
          cancelFriend(v);
        }
      });
    }
  }

  @Override
  protected void onStart() {
    super.onStart();
    Friend friend = Friend.findById(Friend.class, friendId);
    EditText nameET = (EditText) findViewById(R.id.compose_friend_name_et);
    if (friend != null && nameET != null && nameET.getText().length() == 0) {
      nameET.setText(friend.getName());
    }
  }

  public void saveFriend(View view) {
    EditText nameET = (EditText) this.findViewById(R.id.compose_friend_name_et);
    if (nameET == null || nameET.getText().length() == 0) {
      Toast.makeText(EditFriendActivity.this, R.string.error_friend_name_toast, Toast.LENGTH_SHORT).show();
      return;
    }
    Friend friend = Friend.findById(Friend.class, friendId);
    friend.setName(nameET.getText().toString());
    if (friend.save() <= 0) {
      Toast.makeText(EditFriendActivity.this, R.string.error_saving_friend_toast, Toast.LENGTH_LONG).show();
      return;
    }
    setResult(Activity.RESULT_OK);
    finish();
  }

  public void cancelFriend(View view) {
    setResult(Activity.RESULT_CANCELED);
    finish();
  }
}
