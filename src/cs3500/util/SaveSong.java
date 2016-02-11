package src.cs3500.util;

import src.cs3500.model.GoTo;
import src.cs3500.model.Note;
import src.cs3500.model.Song;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

/**
 * Created by DylanWight on 11/22/15.
 */
public class SaveSong {
  /**
   * Writes a song to a .txt in the format it can read in
   *
   * @param model the model to save
   * @param fileName the naem of the file to write to
   * @throws FileNotFoundException
   * @throws UnsupportedEncodingException
   */
  public static void save (Song model, String fileName) throws FileNotFoundException,
      UnsupportedEncodingException {

    PrintWriter writer = new PrintWriter(fileName, "UTF-8");
    writer.printf("tempo %d\n", model.getTempo());
    for (int time = 0; time < model.getSongLength(); time++) {
      for (Note note : model.notesAtTime(time)) {
        if (note.isHit()) {
          writer.printf("note %d %d %d %d %d\n", time, time + note.getDuration(),
              note.getInstrument() + 1, note.getTone(), note.getVolume());
        }
      }
    }
    for(GoTo r: model.getGoTos()) {
      writer.printf("goto %d %d\n", r.getFrom(), r.getTo());
    }
    writer.close();
  }
}
