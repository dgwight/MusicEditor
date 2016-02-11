package src.cs3500.musicTests;

import org.junit.Before;
import org.junit.Test;
import src.cs3500.controller.KeyListenerImpl;

import java.awt.*;
import java.awt.event.KeyEvent;

import static org.junit.Assert.assertTrue;

/**
 * Created by DylanWight on 11/24/15.
 */
public class KeyListenerImplTest {
  Integer testInt = 0;
  KeyListenerImpl testKeyListener = new KeyListenerImpl();

  @Before
  public void setUp() throws Exception {
    testKeyListener.addRunnable(KeyEvent.VK_A, () -> test1(), "keyPressed");
    testKeyListener.addRunnable(KeyEvent.VK_B, () -> test2(), "keyTyped");
    testKeyListener.addRunnable(KeyEvent.VK_C, () -> test3(), "keyReleased");
  }

  @Test(expected = IllegalArgumentException.class)
  public void badAdd() {
    testKeyListener.addRunnable(13, () -> test3(), "bad");
  }

  @Test
  public void testKeyEvents() throws Exception {
    assertTrue(testInt == 0);

    testKeyListener.keyPressed(
        new KeyEvent(new Button(), 1, -1, 3, KeyEvent.VK_A, KeyEvent.CHAR_UNDEFINED));
    assertTrue(testInt == 1);

    testKeyListener.keyTyped(
        new KeyEvent(new Button(), 1, -1, 3, KeyEvent.VK_B, KeyEvent.CHAR_UNDEFINED));
    assertTrue(testInt == 2);

    testKeyListener.keyReleased(
        new KeyEvent(new Button(), 1, -1, 3, KeyEvent.VK_C, KeyEvent.CHAR_UNDEFINED));
    assertTrue(testInt == 3);

    testKeyListener.keyTyped(
        new KeyEvent(new Button(), 1, -1, 3, KeyEvent.VK_C, KeyEvent.CHAR_UNDEFINED));
    assertTrue(testInt == 3);
  }

  private void test1() {
    testInt = 1;
  }

  private void test2() {
    testInt = 2;
  }

  private void test3() {
    testInt = 3;
  }
}
