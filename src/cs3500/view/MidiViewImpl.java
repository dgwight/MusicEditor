package src.cs3500.view;


import src.cs3500.model.Note;
import src.cs3500.model.Song;

import javax.sound.midi.*;
import java.io.OutputStream;
import java.util.Set;

/**
 * A view for MIDI playback
 */
public class MidiViewImpl implements MusicView {
  private final Synthesizer synth;
  private final Receiver receiver;
  /**
   * Holds the song model
   */
  private Song model;

  /**
   * Constructs and sets up a new midi view
   *
   * @throws MidiUnavailableException if midi is unavailable
   */
  public MidiViewImpl(Song model, OutputStream output)
      throws MidiUnavailableException, InvalidMidiDataException {
    if (output != null) {
      mockMidiSynth mock = new mockMidiSynth(output);
      try {
        synth = MidiSystem.getSynthesizer();
        receiver = mock.getReceiver();
        synth.open();
      } catch (MidiUnavailableException e) {
        e.printStackTrace();
        throw new MidiUnavailableException("Midi Unavailable.");
      }
    } else {
      try {
        synth = MidiSystem.getSynthesizer();
        receiver = synth.getReceiver();
        synth.open();
      } catch (MidiUnavailableException e) {
        e.printStackTrace();
        throw new MidiUnavailableException("Midi Unavailable.");
      }
    }
    this.model = model;
  }

  /**
   * Plays a single note at its pitch for its duratation
   *
   * @param note the note to be played
   * @throws InvalidMidiDataException if the note's data is incompatible with MIDI
   */
  private void playNote(Note note) throws InvalidMidiDataException {
    MidiMessage start = new ShortMessage(ShortMessage.NOTE_ON,
        note.getInstrument(), note.getTone(), note.getVolume());
    MidiMessage stop = new ShortMessage(ShortMessage.NOTE_OFF,
        note.getInstrument(), note.getTone(), note.getVolume());
    this.receiver.send(start, -1);
    this.receiver.send(stop,
        this.synth.getMicrosecondPosition() + (model.getTempo() * note.getDuration()));
  }

  /**
   * Plays a set of notes starting at the same time
   *
   * @param chord a set of notes to be played
   * @throws InvalidMidiDataException called from playNote() if the note data is invalid
   */
  private void playChord(Set<Note> chord) throws InvalidMidiDataException {
    for (Note note : chord) {
      if (note.isHit()) {
        playNote(note);
      }
    }
  }

  /**
   * Plays the notes at the given beat
   *
   * @param beat the beat at which you wish to play the notes
   */
  @Override
  public void play(int beat) {
    try {
      playChord(model.notesAtTime(beat));
    } catch (InvalidMidiDataException e) {
      e.printStackTrace();
    }
  }

  @Override
  public void display() throws InvalidMidiDataException, InterruptedException {
    for (int beat = 0; beat < this.model.getSongLength(); beat++) {
      Set<Note> chord = this.model.notesAtTime(beat);
      this.playChord(chord);
      Thread.sleep(this.model.getTempo() / 1000);
    }
    this.receiver.close(); // Only call this once you're done playing *all* notes
  }

  public GUIView getGUIView() {
    throw new IllegalStateException("This call is not valid for a midi view");
  }

}
