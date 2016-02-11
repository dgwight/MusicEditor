package src.cs3500.musicTests;


import org.junit.Before;
import org.junit.Test;
import src.cs3500.model.Song;
import src.cs3500.model.SongImpl;

import static org.junit.Assert.assertEquals;


/**
 * Created by DylanWight on 11/14/15.
 */
public class SongImplTest {
  Song testSongEmpty = new SongImpl();
  Song testSong1 = new SongImpl();

  @Before
  public void setUp() throws Exception {
    testSong1.addNote(2, 3, 2, 1, 70);
    testSong1.addNote(1, 4, 8, 1, 70);
    testSong1.addNote(4, 4, 2, 1, 70);
    testSong1.addNote(0, 10, 1, 1, 70);
  }

  @Test
  public void testDeleteNote() throws Exception {
    assertEquals(4, testSong1.numberOfNotes());
    assertEquals(9, testSong1.numberOfSustainedNotes());
    testSong1.deleteNote(1, 4);     // delete overlapping note
    testSong1.deleteNote(9, 4);     // note does not exist
    assertEquals(3, testSong1.numberOfNotes());
    assertEquals(2, testSong1.numberOfSustainedNotes());
  }

  @Test
  public void testNumberOfNotes() throws Exception {
    assertEquals(0, testSongEmpty.numberOfNotes());
    assertEquals(4, testSong1.numberOfNotes());
  }

  @Test
  public void testGetHighestTone() throws Exception {
    assertEquals(0, testSongEmpty.getHighestTone());
    assertEquals(10, testSong1.getHighestTone());
  }

  @Test
  public void testGetLowestTone() throws Exception {
    assertEquals(120, testSongEmpty.getLowestTone());
    assertEquals(3, testSong1.getLowestTone());
  }

  @Test
  public void testGetSongLength() throws Exception {
    assertEquals(0, testSongEmpty.getSongLength());
    assertEquals(9, testSong1.getSongLength());
  }

  @Test
  public void testGetPitchSymbol() throws Exception {
    assertEquals("G#1", Song.getPitchSymbol(20));
    assertEquals("C10", Song.getPitchSymbol(120));
    assertEquals("F5", Song.getPitchSymbol(65));
  }

  @Test
  public void testGetSymbol() throws Exception {
    assertEquals("X", testSong1.getSymbol(4, 4));
    assertEquals("|", testSong1.getSymbol(6, 4));
    assertEquals(" ", testSong1.getSymbol(4, 5));
  }

  @Test(expected = IllegalArgumentException.class)
  public void badGetSymbol1() {
    testSong1.getSymbol(20, 5);
  }

  @Test(expected = IllegalArgumentException.class)
  public void badGetSymbol2() {
    testSong1.getSymbol(-3, 5);
  }

}
