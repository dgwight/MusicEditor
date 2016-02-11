package src.cs3500.model;

import src.cs3500.util.CompositionBuilder;

import java.util.*;

/**
 * Created by DylanWight on 11/14/15.
 */
public final class SongImpl implements Song {
  /**
   * A list of the notes in the song. has priority over songSymbols.
   */
  private TreeMap<Integer, Set<Note>> songNotes;

  private int currentGoTo;

  private ArrayList<GoTo> goTos;


  /**
   * The speed, in microseconds per beat, defaults to 200000
   */
  private int tempo = 200000;

  @Override
  public final int getTempo() {
    return this.tempo;
  }

  @Override
  public final void setTempo(int tempo) {
    if (tempo < 0) {
      throw new IllegalArgumentException("Tempo cannot be negative");
    }
    this.tempo = tempo;
  }

  @Override
  public final  int getSongLength() {
    try {
      return this.songNotes.lastKey() + 1;
    } catch (NoSuchElementException n) {
      return 0;
    }
  }

  @Override
  public final int getHighestTone() {
    int highestTone = 0;
    for (int time : songNotes.keySet()) {
      for (Note note : songNotes.get(time)) {
        if (note.getTone() > highestTone) {
          highestTone = note.getTone();
        }
      }
    }
    if (highestTone == 0) {
      highestTone = 80;
    }

    return highestTone;
  }

  @Override
  public final int getLowestTone() {
    int lowestTone = 120;
    for (int time : songNotes.keySet()) {
      for (Note note : songNotes.get(time)) {
        if (note.getTone() < lowestTone) {
          lowestTone = note.getTone();
        }
      }
    }
    return lowestTone;
  }

  /**
   * Constructs a new SongImpl with no notes
   */
  public SongImpl() {
    songNotes = new TreeMap<>();
    goTos = new ArrayList<>();
  }

  /**
   * Builder to construct a song
   */
  public static final class Builder implements CompositionBuilder<Song> {
    Song model = new SongImpl();

    @Override
    public final Song build() {
      return model;
    }

    @Override
    public CompositionBuilder<Song> setTempo(int tempo) {
      model.setTempo(tempo);
      return this;
    }

    @Override
    public final CompositionBuilder<Song> addNote(int start, int end, int instrument, int pitch,
        int volume) {
      model.addNote(start, pitch, end - start, instrument - 1, volume);
      return this;
    }

    /**
     * @param from the location of the repeat
     * @param to   the repeat start
     */
    @Override
    public CompositionBuilder<Song> addRepeat(int from, int to) {
      model.addGoto(from, to);
      return this;
    }
  }

  @Override
  public final void addNote(int time, int tone, int duration, int instrument, int volume) {
    this.extendSong(time + duration);
    songNotes.get(time).add(new NoteImpl(tone, duration, instrument, volume, true));
    for (int sustain = 1; sustain < duration; sustain++) {
      songNotes.get(time + sustain).add(new NoteImpl(tone, duration, instrument, volume, false));
    }
  }


  @Override
  public final Note deleteNote(int time, int tone) {
    Note deletingNote = null;
    if (this.songNotes.get(time) == null) {
      return null;
    }
    int duration = 1;
    for (Iterator<Note> iterator = this.songNotes.get(time).iterator(); iterator.hasNext(); ) {
      Note note = iterator.next();
      if (note.getTone() == tone && note.isHit()) {
        duration = note.getDuration();
        deletingNote = note;
        iterator.remove();
        break;
      }
    }
    for (int sustain = 1; sustain < duration; sustain++) {
      for (Iterator<Note> iteratorSustained = this.songNotes.get(time + sustain).iterator();
           iteratorSustained.hasNext(); ) {
        Note noteSustained = iteratorSustained.next();
        if (noteSustained.getTone() == tone && !noteSustained.isHit()) {
          iteratorSustained.remove();
          break;
        }
      }
    }
    return deletingNote;
  }

  @Override
  public final void extendSong(int newEndTime) {
    for (int time = this.getSongLength(); time < newEndTime; time++) {
      if (this.songNotes.get(time) == null) {
        this.songNotes.put(time, new HashSet<>());

      }
    }
  }

  @Override
  public final Set<Note> notesAtTime(int time) {
    if (time > this.getSongLength())
      throw new IllegalArgumentException("time cannot be greater than the song length");
    return this.songNotes.get(time);
  }

  @Override
  public final int numberOfNotes() {
    int size = 0;
    for (int time : songNotes.keySet()) {
      for (Note note : this.songNotes.get(time)) {
        if (note.isHit()) {
          size++;
        }
      }
    }
    return size;
  }

  @Override
  public final int numberOfSustainedNotes() {
    int size = 0;
    for (int time : songNotes.keySet()) {
      for (Note note : this.songNotes.get(time)) {
        if (!note.isHit()) {
          size++;
        }
      }
    }
    return size;
  }

  @Override
  public final String getSymbol(int time, int tone) {
    if (time >= this.getSongLength() || time < 0) {
      return " ";
    }
    String symbol = " ";
    for (Note note : songNotes.get(time)) {
      if (note.getTone() == tone && note.isHit()) {
        return "X";
      }
      if (note.getTone() == tone && !note.isHit()) {
        symbol = "|";
      }
    }
    return symbol;
  }

  /**
   * @param time the starting time of this note
   * @param tone the tone of the note
   * @return note if note is found, otherwise returns null
   */
  @Override
  public Note getNoteAt(int time, int tone) {
    for (Note n : songNotes.get(time)) {
      if (n.getTone() == tone) {
        return n;
      }
    }
    return null;
  }

  /**
   * adds a new Goto to the song
   *
   * @param from start of segment
   * @param to   end of segment
   */
  @Override
  public void addGoto(int from, int to) {
    this.goTos.add(new GoTo(from, to));
  }

  /**
   * Returns the current goTo
   *
   * @return the current goTo
   */
  @Override
  public GoTo currentGoTo() {
    if (goTos.size() <= currentGoTo) {
      return new GoTo(getSongLength(), 0);
    }
    else {
      return goTos.get(currentGoTo);
    }
  }

  /**
   * Progresses to the next goto
   */
  @Override
  public void incrementGoTo() {
    this.currentGoTo++;
  }

  /**
   * Sets the current goTo to the first one
   */
  @Override
  public void resetGoTo() {
    this.currentGoTo = 0;
  }

  /**
   * retuns the GoTo's in the song
   *
   * @return the GoTo's in the song
   */
  @Override
  public List<GoTo> getGoTos() {
    return new ArrayList<>(goTos);

  }
}


