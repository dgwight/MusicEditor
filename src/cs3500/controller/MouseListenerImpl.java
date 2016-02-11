package src.cs3500.controller;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Created by DylanWight on 11/21/15.
 */
public class MouseListenerImpl implements MouseListener {

  /**
   *  Holds a map associating each key code with a Runnable for the mouseClicked key
   */
  private Map<Integer, Consumer> clicked;

  public MouseListenerImpl() {
    this.clicked = new HashMap<>();
  }

  public void addConsumer(Integer buttonNum, Consumer consumer) {
    this.clicked.put(buttonNum, consumer);
  }

  @Override
  public void mouseClicked(MouseEvent e) {
    if (this.clicked.get(e.getButton()) != null) {
      int button = e.getButton();
      if (e.isShiftDown()) {
        button += 10;
      }
      this.clicked.get(button).accept(e);
    }
  }

  @Override
  public void mousePressed(MouseEvent e) {
  }

  @Override
  public void mouseReleased(MouseEvent e) {
  }

  @Override
  public void mouseEntered(MouseEvent e) {
  }

  @Override
  public void mouseExited(MouseEvent e) {
  }

}
