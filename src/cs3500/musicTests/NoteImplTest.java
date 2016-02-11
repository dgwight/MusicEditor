package src.cs3500.musicTests;


import org.junit.Test;
import src.cs3500.model.Note;
import src.cs3500.model.NoteImpl;

import static org.junit.Assert.assertEquals;

/**
 * Created by DylanWight on 11/14/15.
 */
public class NoteImplTest {
  @Test(expected = IllegalArgumentException.class)
  public void badConstructor2() {
    Note bad = new NoteImpl( -1, 1, 1, 70, true);
  }

  @Test(expected = IllegalArgumentException.class)
  public void badConstructor3() {
    NoteImpl bad = new NoteImpl( 128, 1, 1, 70, true);
  }

  @Test(expected = IllegalArgumentException.class)
  public void badConstructor4() {
    NoteImpl bad = new NoteImpl(1, 0, 1, 70, true);
  }

  @Test(expected = IllegalArgumentException.class)
  public void badConstructor5() {
    Note bad = new NoteImpl( 1, 1, -1, 70, true);
  }

  Note good = new NoteImpl(1, 2, 3, 70, true);

  @Test
  public void testGetTone() throws Exception {
    assertEquals(1, good.getTone());
  }

  @Test
  public void testGetDuration() throws Exception {
    assertEquals(2, good.getDuration());
  }

  @Test
  public void testGetInstrament() throws Exception {
    assertEquals(3, good.getInstrument());
  }
}
