package src.cs3500.model;

/**
 * Created by DylanWight on 11/14/15.
 */
public final class NoteImpl implements Note {

  /**
   * The pitch of the note
   * is between [0, 120]
   * octave = pitch / 12
   * pitch = pitch % 12, where:
   *  0  -> C
   *  1  -> C#
   *  2  -> D
   *  3  -> D#
   *  4  -> E
   *  5  -> F
   *  6  -> F#
   *  7  -> G
   *  8  -> G#
   *  9  -> A
   *  10 -> A#
   *  11 -> B
   */
  private int tone;

  /**
   * the length in beats that the note is held
   */
  private final int duration;

  /**
   * the instrument that the note is played on
   */
  private final int instrument;

  /**
   * the volume of the note, ranges from [0 - 127]
   */
  private final int volume;

  /**
   * represents whether a note is hit or sustained
   */
  private final Boolean isHit;

  /**
   * Constructs a new note with the given parameters
   *
   * @param tone the pitch of the note, between [0, 127]
   * @param duration the number of beats that the note is sustained
   * @param instrument represents which instrument the note is played on
   * @param volume the volume the note is played at
   * @param isHit represents whether a note is hit or sustained
   */
  public NoteImpl( int tone, int duration, int instrument, int volume, Boolean isHit) {
    if (tone < 0 || tone > 127) {
      throw new IllegalArgumentException("Outside of pitch range.");
    }
    if (duration < 0) {
      throw new IllegalArgumentException("Duration must be greater than zero.");
    }
    if (instrument < 0 || instrument > 15) {
      throw new IllegalArgumentException("Invalid instrument.");
    }
    if (volume < 0 || volume > 127) {
      throw new IllegalArgumentException("Invalid volume.");
    }
    this.tone = tone;
    this.duration = duration;
    this.instrument = instrument;
    this.volume = volume;
    this.isHit = isHit;
  }

  @Override
  public int getTone() {
    return this.tone;
  }

  @Override
  public int getInstrument() {
    return this.instrument;
  }

  @Override
  public int getDuration() {
    return this.duration;
  }

  @Override
  public int getVolume() {
    return this.volume;
  }

  @Override
  public Boolean isHit() {
    return this.isHit;
  }

  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof NoteImpl)) {
      return false;
    }
    NoteImpl that = (NoteImpl) obj;
    return this.tone == that.tone && this.duration == that.duration
        && this.instrument == that.instrument;
  }
}
