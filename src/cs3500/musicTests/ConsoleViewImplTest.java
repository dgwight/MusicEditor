package src.cs3500.musicTests;


import org.junit.Test;
import src.cs3500.model.Song;
import src.cs3500.model.SongImpl;
import src.cs3500.view.ConsoleViewImpl;
import src.cs3500.view.MusicView;

import java.io.ByteArrayOutputStream;

import static org.junit.Assert.assertEquals;

/**
 * Created by DylanWight on 11/14/15.
 */
public class ConsoleViewImplTest {
  final ByteArrayOutputStream baos = new ByteArrayOutputStream();
  Song test = new SongImpl();

  @Test
  public void testDisplay() throws Exception {
    test.addNote(1, 1, 1, 1, 70);
    MusicView testView = new ConsoleViewImpl(test, baos);
    testView.display();
    assertEquals("\tC0\tC#0\tD0\n" + "0\t \t \t \n" + "1\t \tX\t \n\n", baos.toString());
  }

  @Test
  public void testDisplayEmpty() throws Exception {
    MusicView testView = new ConsoleViewImpl(test, baos);
    testView.display();
    assertEquals("\n\n", baos.toString());
  }
}
