package cl.flores.nicolas.tripcost.database;

import com.orm.SugarRecord;

public class FriendTrip extends SugarRecord {
  private Trip trip;
  private Friend friend;

  public FriendTrip() {
  }

  public FriendTrip(Trip trip, Friend friend) {
    this.trip = trip;
    this.friend = friend;
  }

  public Trip getTrip() {
    return trip;
  }

  public void setTrip(Trip trip) {
    this.trip = trip;
  }

  public Friend getFriend() {
    return friend;
  }

  public void setFriend(Friend friend) {
    this.friend = friend;
  }

  @Override
  public String toString() {
    String sb = "FriendTrip{" + "trip = " + trip.getName() +
        ", friend = " + friend.getName() +
        '}';
    return sb;
  }
}
