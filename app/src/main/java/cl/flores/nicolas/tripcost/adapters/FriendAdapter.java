package cl.flores.nicolas.tripcost.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import cl.flores.nicolas.tripcost.R;
import cl.flores.nicolas.tripcost.database.Friend;
import cl.flores.nicolas.tripcost.fragments.FriendsFragment;
import cl.flores.nicolas.tripcost.fragments.FriendsFragment.OnFriendListInteractionListener;

/**
 * {@link RecyclerView.Adapter} that can display a {@link Friend} and makes a call to the
 * specified {@link FriendsFragment.OnFriendListInteractionListener}.
 */
public class FriendAdapter extends RecyclerView.Adapter<FriendAdapter.ViewHolder> {

  private final List<Friend> friendList;
  private final OnFriendListInteractionListener mListener;

  public FriendAdapter(OnFriendListInteractionListener listener) {
    friendList = Friend.listAll(Friend.class);
    mListener = listener;
  }

  public FriendAdapter(List<Friend> friends, OnFriendListInteractionListener listener) {
    friendList = friends;
    mListener = listener;
  }

  @Override
  public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    View view = LayoutInflater.from(parent.getContext())
        .inflate(R.layout.fragment_friend, parent, false);
    return new ViewHolder(view);
  }

  @Override
  public void onBindViewHolder(final ViewHolder holder, int position) {
    holder.friend = friendList.get(position);
    holder.friendId.setText(String.valueOf(friendList.get(position).getId()));
    holder.friendName.setText(friendList.get(position).getName());

    holder.mView.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        if (null != mListener) {
          // Notify the active callbacks interface (the activity, if the
          // fragment is attached to one) that an item has been selected.
          mListener.onFriendListInteraction(holder.friend);
        }
      }
    });
  }

  @Override
  public int getItemCount() {
    return friendList.size();
  }

  public class ViewHolder extends RecyclerView.ViewHolder {
    public final View mView;
    public final TextView friendId;
    public final TextView friendName;
    public Friend friend;

    public ViewHolder(View view) {
      super(view);
      mView = view;
      friendId = (TextView) view.findViewById(R.id.friend_id);
      friendName = (TextView) view.findViewById(R.id.friend_name);
    }

    @Override
    public String toString() {
      return super.toString() + " '" + friendName.getText() + "'";
    }
  }
}
