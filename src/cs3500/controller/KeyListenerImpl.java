package src.cs3500.controller;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by DylanWight on 11/21/15.
 */
public class KeyListenerImpl implements java.awt.event.KeyListener {

  /**
   *  Holds a map associating each key code with a Runnable for the keyPressed event
   */
  private Map<Integer, Runnable> pressed;

  /**
   *  Holds a map associating each key code with a Runnable for the keyTyped event
   */
  private Map<Integer, Runnable> typed;

  /**
   *  Holds a map associating each key code with a Runnable for the keyReleased event
   */
  private Map<Integer, Runnable> released;

  /**
   * Constructs a new KeyListenerImp
   */
  public KeyListenerImpl() {
    this.pressed = new HashMap<>();
    this.typed = new HashMap<>();
    this.released = new HashMap<>();
  }

  /**
   * Adds a new runnable to the chosen map for a key event
   *
   * @param keyCode   the key code event to add to a map
   * @param runnable  the executable to add to a map
   * @param eventType the map to add the key event and runnable to. Can be "keyPressed",
   *                  "keyTyped", or "keyReleased"
   */
  public void addRunnable(Integer keyCode, Runnable runnable, String eventType) {
    switch (eventType) {
      case "keyPressed":
        this.pressed.put(keyCode, runnable);
        break;
      case "keyTyped":
        this.typed.put(keyCode, runnable);
        break;
      case "keyReleased":
        this.released.put(keyCode, runnable);
        break;
      default:
        throw new
            IllegalArgumentException("eventType must be keyPressed, keyTyped, or keyReleased");
    }
  }

  @Override
  public void keyPressed(KeyEvent e) {
    if (this.pressed.get(e.getKeyCode()) != null) {
      this.pressed.get(e.getKeyCode()).run();
    }
  }

  @Override
  public void keyTyped(KeyEvent e) {
    if (this.typed.get(e.getKeyCode()) != null) {
      this.typed.get(e.getKeyCode()).run();
    }
  }

  @Override
  public void keyReleased(KeyEvent e) {
    if (this.released.get(e.getKeyCode()) != null) {
      this.released.get(e.getKeyCode()).run();
    }
  }
}
