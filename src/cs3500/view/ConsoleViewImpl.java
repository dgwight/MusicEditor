package src.cs3500.view;

import src.cs3500.model.Song;

import java.io.OutputStream;
import java.io.PrintStream;

/**
 * Created by DylanWight on 11/14/15.
 */
public final class ConsoleViewImpl implements MusicView {
  /**
   * The song model
   */
  private Song model;

  /**
   * Holds the output stream
   */
  private final PrintStream ps;

  /**
   * Constructs a textual view, that outputs to the specified location.
   */
  public ConsoleViewImpl(Song model, OutputStream output) {
    this.model = model;
    ps = new PrintStream(output);
  }

  @Override
  public final void display() {
    for (int tone = model.getLowestTone() - 1; tone <= model.getHighestTone() + 1; tone++) {
      ps.printf("\t%s", Song.getPitchSymbol(tone));
    }
    for (int time = 0; time < model.getSongLength(); time++) {
      ps.printf("\n%d", time);
      for (int tone = model.getLowestTone() - 1; tone <= model.getHighestTone() + 1; tone++) {
        ps.printf("\t%s", model.getSymbol(time, tone));
      }
    }
    ps.print("\n\n");
  }

  @Override
  public void play(int beat) {
    ps.printf("\n%d", beat);
    for (int tone = model.getLowestTone() - 1; tone <= model.getHighestTone() + 1; tone++) {
      ps.printf("\t%s", model.getSymbol(beat, tone));
    }
  }

  public GUIView getGUIView() {
    throw new IllegalStateException("This call is not valid for a console view");
  }

}
