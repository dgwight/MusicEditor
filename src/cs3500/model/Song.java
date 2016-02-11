package src.cs3500.model;


import java.util.List;
import java.util.Set;

/**
 * Created by DylanWight on 11/14/15.
 */
public interface Song {

  /**
   * Adds a new note to the song
   *
   * @param time       the number of beats into the song when the note is struck
   * @param tone       the pitch of the note, between (0, 120]
   * @param duration   the number of beats that the note is sustained
   * @param instrument represents which intrament the note is played on
   */
  void addNote(int time, int tone, int duration, int instrument, int volume);



  /**
   * Deletes all the notes at the given tone starting at the given time from the song
   *
   * @param time the number of beats into the song when the note is struck
   * @param tone the pitch of the note, between (0, 120]
   * @return the deleted note
   */
  Note deleteNote(int time, int tone);

  /**
   * Adds extra beats on to the end of the song
   *
   * @param extendSong the new end of the song
   */
  void extendSong(int extendSong);

  /**
   * Finds the set of notes at a time
   *
   * @param time the number of beats into teh song
   * @return a set of notes that start at the given time
   * @throws IllegalArgumentException if time is greater than the length of the song
   */
  Set<Note> notesAtTime(int time);

  /**
   * Finds the number of hit notes in a song
   *
   * @return the number of hit notes in a song
   */
  int numberOfNotes();

  /**
   * Finds the number of sustained notes in a song
   *
   * @return the number of sustained notes in a song
   */
  int numberOfSustainedNotes();

  /**
   * Finds the highest tone in the song
   *
   * @return the highest tone in the song
   */
  int getHighestTone();

  /**
   * Finds the lowest tone in the song
   *
   * @return the lowest tone in the song
   */
  int getLowestTone();

  /**
   * Finds the length of the song
   *
   * @return the length of the song in beats
   */
  int getSongLength();

  /**
   * Gets the song's tempo
   *
   * @return the tempo of the song
   */
  int getTempo();

  /**
   * sets the songs tempo
   *
   * @param tempo the tempo of the song
   * @throws IllegalArgumentException if the tempo is negative
   */
  void setTempo(int tempo);

  static String getPitchSymbol(int tone) {
    switch (tone % 12) {
      case 0:
        return "C" + (tone / 12 - 2);
      case 1:
        return "C#" + (tone / 12 - 2);
      case 2:
        return "D" + (tone / 12 - 2);
      case 3:
        return "D#" + (tone / 12 - 2);
      case 4:
        return "E"+ (tone / 12 - 2);
      case 5:
        return "F"+ (tone / 12 - 2);
      case 6:
        return "F#" + (tone / 12 - 2);
      case 7:
        return "G" + (tone / 12 - 2);
      case 8:
        return "G#" + (tone / 12 - 2);
      case 9:
        return "A" + (tone / 12 - 2);
      case 10:
        return "A#" + (tone / 12 - 2);
      case 11:
        return "B" + (tone / 12 - 2);
    }
    return "";
  }

  static String getInstrumentName(int instrumentNum) {
    switch (instrumentNum) {
      case 0:
        return instrumentNum + " Piano";
      case 1:
        return instrumentNum + " Chromatic Percussion";
      case 2:
        return instrumentNum + " Organ";
      case 3:
        return instrumentNum + " Guitar";
      case 4:
        return instrumentNum + " Bass";
      case 5:
        return instrumentNum + " Strings";
      case 6:
        return instrumentNum + " Ensemble";
      case 7:
        return instrumentNum + " Brass";
      case 8:
        return instrumentNum + " Reed";
      case 9:
        return instrumentNum + " Pipe";
      case 10:
        return instrumentNum + " Synth Lead";
      case 11:
        return instrumentNum + " Synth Pad";
      case 12:
        return instrumentNum + " Synth Effects";
      case 13:
        return instrumentNum + " Ethnic";
      case 14:
        return instrumentNum + " Percussive";
      case 15:
        return instrumentNum + " Sound effects";
      default:
        return instrumentNum + " Unknown";
    }
  }

  /**
   * Checks if a note is being played at the time and tone specified Returns a string representing
   * the state of the note
   *
   * @param time the time when the symbol is being checked
   * @param tone the tone being checked
   * @return "X" if a note is hit, "|" if a note is sustained, " " otherwise
   * @throws IllegalArgumentException if time < 0 || time > song length
   */
  String getSymbol(int time, int tone);


  /**
   * Returns the note at the specified time, that has the specified tone
   *
   * @param time the starting time of this note
   * @param tone the tone of the note
   * @return the note with the specified starting time, and tone
   */
  Note getNoteAt(int time, int tone);


  /** adds a new Goto to the song
   *
   * @param from start of segment
   * @param to end of segment
   */
  void addGoto(int from, int to);

  /** Returns the current goTo
   *
   * @return the current goTo
   */
  GoTo currentGoTo();


  /** Progresses to the next goto
   *
   */
  void incrementGoTo();

  /** Sets the current goTo to the first one
   *
   */
  void resetGoTo();


  /** returns the GoTo's in the song
   *
   * @return the GoTo's in the song
   */
  List<GoTo> getGoTos();

}
