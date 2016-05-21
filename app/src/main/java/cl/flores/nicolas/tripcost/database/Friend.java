package cl.flores.nicolas.tripcost.database;

import com.orm.SugarRecord;

public class Friend extends SugarRecord {

  private String name;

  public Friend() {
  }

  public Friend(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  @Override
  public String toString() {
    return name;
  }
}
