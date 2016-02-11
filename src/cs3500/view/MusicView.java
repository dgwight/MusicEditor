package src.cs3500.view;


import src.cs3500.model.Song;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;

/**
 * Created by DylanWight on 11/14/15.
 */
public interface MusicView {

  class Factory {
    /**
     * Constructs a new MusicView of the specified concrete class
     *
     * @param type the type of vie to return
     * @return a new view of the specified type
     * @throws MidiUnavailableException if midi is called and unavailable
     */
    public static MusicView makeView(Song model, String type)
        throws MidiUnavailableException, InvalidMidiDataException, InterruptedException {
      switch (type) {
        case "console":
          return new ConsoleViewImpl(model, System.out);
        case "visual":
          return new GUIViewImpl(model);
        case "midi":
          return new MidiViewImpl(model, null);
        case "composite":
          return new CombinedView(new GUIViewImpl(model), new MidiViewImpl(model, null), model);
        default:
          throw new IllegalArgumentException("type must be either 'console'," +
              " 'composite', 'visual', or 'midi'");
      }
    }
  }

  /**
   * Displays the view
   */
  void display() throws InvalidMidiDataException, InterruptedException;

  void play(int beat);

  /**
   *
   * @return this Music view as a GUIView if applicable
   * @throws IllegalStateException if a view cannot represent a GUI view
   */
  GUIView getGUIView();
}
