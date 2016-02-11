package src.cs3500.musicTests;

import org.junit.Before;
import org.junit.Test;
import src.cs3500.controller.MouseListenerImpl;

import java.awt.*;
import java.awt.event.MouseEvent;

import static org.junit.Assert.assertTrue;

/**
 * Created by DylanWight on 11/24/15.
 */
public class MouseListenerImplTest {
  Integer testInt = 0;
  MouseListenerImpl testMouseListener = new MouseListenerImpl();

  @Before
  public void setUp() throws Exception {
    testMouseListener.addConsumer(MouseEvent.BUTTON1, o -> test1((MouseEvent) o));
    testMouseListener.addConsumer(MouseEvent.BUTTON2, o -> test2((MouseEvent) o));
    testMouseListener.addConsumer(MouseEvent.BUTTON3, o -> test3((MouseEvent) o));
  }

  @Test
  public void testKeyEvents() throws Exception {
    assertTrue(testInt == 0);

    testMouseListener.mouseClicked(
        new MouseEvent(new Button(), 1, -1, 3, 30, 4, 1, true, MouseEvent.BUTTON1));
    assertTrue(testInt == 30);

    testMouseListener.mouseClicked(
        new MouseEvent(new Button(), 1, -1, 3, 30, 4, 1, true, MouseEvent.BUTTON2));
    assertTrue(testInt == 4);

    testMouseListener.mouseClicked(
        new MouseEvent(new Button(), 1, -1, 3, 30, 4, 1, true, MouseEvent.BUTTON3));
    assertTrue(testInt == 3);
  }

  private void test1(MouseEvent e) {
    testInt = e.getX();
  }

  private void test2(MouseEvent e) {
    testInt = e.getY();
  }

  private void test3(MouseEvent e) {
    testInt = 3;
  }
}
