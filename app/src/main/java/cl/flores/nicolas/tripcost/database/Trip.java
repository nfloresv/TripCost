package cl.flores.nicolas.tripcost.database;

import com.orm.SugarRecord;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Trip extends SugarRecord {

  private String name;
  private String description;
  private Date start;
  private Date end;

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

  public void setName(String name) {
    this.name = name;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public void setStart(Date start) {
    this.start = start;
  }

  public void setEnd(Date end) {
    this.end = end;
  }

  @Override
  public String toString() {
    DateFormat df = new SimpleDateFormat("MM/dd/yyyy", Locale.getDefault());
    return String.format("(%s) %s\n%s", df.format(start), name, description);
  }
}
