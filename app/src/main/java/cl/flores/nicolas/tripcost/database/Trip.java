package cl.flores.nicolas.tripcost.database;

import com.orm.SugarRecord;

import java.util.Date;

public class Trip extends SugarRecord {

  String name;
  String description;
  Date start;
  Date end;

  public Trip() {
  }

  public Trip(String name, String description, Date start, Date end) {
    this.name = name;
    this.description = description;
    this.start = start;
    this.end = end;
  }
}
