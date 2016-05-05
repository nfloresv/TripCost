package cl.flores.nicolas.tripcost.database;

import com.orm.SugarRecord;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
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

  public String getName() {
    return name;
  }

  public String getDescription() {
    return description;
  }

  public Date getStart() {
    return start;
  }

  public Date getEnd() {
    return end;
  }

  @Override
  public String toString() {
    DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
    return String.format("(%s) %s\n%s", df.format(start), name, description);
  }
}
