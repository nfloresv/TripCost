package cl.flores.nicolas.tripcost.activities;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import cl.flores.nicolas.tripcost.R;
import cl.flores.nicolas.tripcost.database.Friend;

public class NewFriendActivity extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_new_friend);
  }

  public void saveFriend(View view) {
    EditText nameET = (EditText) this.findViewById(R.id.friend_name_et);
    if (nameET == null || nameET.getText().length() == 0) {
      Toast.makeText(NewFriendActivity.this, R.string.error_friend_name_toast, Toast.LENGTH_SHORT).show();
      return;
    }
    Friend friend = new Friend(nameET.getText().toString());
    if (friend.save() <= 0) {
      Toast.makeText(NewFriendActivity.this, R.string.error_saving_friend_toast, Toast.LENGTH_LONG).show();
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
