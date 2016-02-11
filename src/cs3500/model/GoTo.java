package src.cs3500.model;

/**
 * Created by User on 12/15/2015.
 */
public class GoTo {
  int from;
  int to;

  public int getFrom() {
    return from;
  }

  public int getTo() {
    return to;
  }

  public GoTo(int from, int to) {
    this.to = to;
    this.from = from;
  }
}
