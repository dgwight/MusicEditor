package src.cs3500.musicTests;



import org.junit.Test;
import src.cs3500.model.Song;
import src.cs3500.model.SongImpl;
import src.cs3500.view.MidiViewImpl;
import src.cs3500.view.MusicView;

import java.io.ByteArrayOutputStream;

import static org.junit.Assert.assertEquals;

/**
 * Created by DylanWight on 11/14/15.
 */
public class MidiViewImplTest {
  final ByteArrayOutputStream baos = new ByteArrayOutputStream();
  Song test = new SongImpl();

  @Test
  public void testDisplay() throws Exception {
    test.addNote(1, 1, 1, 1, 70);
    MusicView testView = new MidiViewImpl(test, baos);
    testView.display();
    assertEquals("javax.sound.midi.ShortMessage@76ccd017\t\t-1\n"
        + "javax.sound.midi.ShortMessage@182decdb\t\t587755\n", baos.toString());
  }

  @Test
  public void testDisplayEmpty() throws Exception {
    MusicView testView = new MidiViewImpl(test, baos);
    testView.display();
    assertEquals("", baos.toString());
  }
}
