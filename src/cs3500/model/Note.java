package src.cs3500.model;

/**
 * Created by DylanWight on 11/14/15.
 */
public interface Note {

  /**
   * Get the note's tone
   *
   * @return an integer representing the tone, ranges from [0 - 127]
   */
  int getTone();

  /**
   * Gets the note's duration
   *
   * @return the duration of the note in beats
   */
  int getDuration();

  /**
   * Get the note's instrument, ranges from [0 - 15]
   *
   * @return an integer representing the instrument the note is played on
   */
  int getInstrument();

  /**
   * Get the note's volume ranges from [0 - 127]
   */
  int getVolume();

  /**
   * Gets whether a note is hit or sustained
   * True : Hit
   * False : Sustained
   *
   * @return whether a note is hit or sustained
   */
  Boolean isHit();
}
