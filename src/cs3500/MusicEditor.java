package src.cs3500;
/**
 * Created by DylanWight on 11/14/15.
 */


import src.cs3500.controller.Controller;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import java.io.IOException;

public class MusicEditor {
  public static void main(String[] args) throws IOException, InvalidMidiDataException,
      MidiUnavailableException, InterruptedException {

    //String fileName = args[0];      // input the file to read from
    //String viewType = args[1];      // input the view type: 'console', 'visual', or 'midi'

    new Controller("mystery-3.txt", "composite");
  }
}
