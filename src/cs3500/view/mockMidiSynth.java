package src.cs3500.view;

import javax.sound.midi.*;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.List;

/**
 * Created by DylanWight on 11/14/15.
 */
public final class mockMidiSynth implements MidiDevice {
  private final PrintStream ps;

  /**
   * Constructs a new mock midi snyth that prints to output
   *
   * @param output the location to print the send calls to
   */
  public mockMidiSynth(OutputStream output) {
    ps = new PrintStream(output);
  }

  @Override
  public Receiver getReceiver() throws MidiUnavailableException {
    return new Receiver() {
      @Override
      public void send(MidiMessage message, long timeStamp) {
        ps.printf("%s\t\t%d\n", message, timeStamp);
      }

      @Override
      public void close() {}
    };
  }

  @Override
  public Info getDeviceInfo() {
    return null;
  }

  @Override
  public void open() throws MidiUnavailableException {}

  @Override
  public void close() {}

  @Override
  public boolean isOpen() {
    return false;
  }

  @Override
  public long getMicrosecondPosition() {
    return 0;
  }

  @Override
  public int getMaxReceivers() {
    return 0;
  }

  @Override
  public int getMaxTransmitters() {
    return 0;
  }

  @Override
  public List<Receiver> getReceivers() {
    return null;
  }

  @Override
  public Transmitter getTransmitter() throws MidiUnavailableException {
    return null;
  }

  @Override
  public List<Transmitter> getTransmitters() {
    return null;
  }
}
