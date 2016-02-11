package src.cs3500.view;

import javax.sound.midi.InvalidMidiDataException;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by User on 11/24/2015.
 */
public interface GUIView extends MusicView {
  @Override
  void display() throws InvalidMidiDataException, InterruptedException;

  /**
   * Shifts the redLine's position
   *
   * @param beat current beat location of the redLine
   */
  @Override
  void play(int beat);

  /**
   * Adds a keylistener to the GUI
   *
   * @param keyListener the keylistener to add to the GUI
   */
  void addKeyListener(KeyListener keyListener);

  /**
   * Adds a mouseListener to the GUI
   *
   * @param mouseListener the mouseListener to add to the GUI
   */
  void addMouseListener(MouseListener mouseListener);

  /**
   * Shifts the view of the song up to see higher pitched notes
   */
  void shiftUp();

  /**
   * Shifts the view of the song up to see lower pitched notes
   */
  void shiftDown();

  /**
   * Shifts the view of the song left to see earlier notes
   */
  void shiftLeft();

  /**
   * Shifts the view of the song left to see earlier notes
   */
  void shiftRight();

  /**
   * Brings the duration of new notes to be added down
   */
  void durationDown();

  /**
   * Brings the duration of new notes to be added up
   */
  void durationUp();

  /**
   * Brings the volume of new notes to be added up
   */
  void volumeUp();

  /**
   * Brings the volume of new notes to be added down
   */
  void volumeDown();

  /**
   * Brings the instrument of new notes to be added up
   */
  void instrumentUp();

  /**
   * Brings the instrument of new notes to be added down
   */
  void instrumentDown();

  /**
   * Moves the view to the start of the song
   */
  void jumpToBeg();

  /**
   * Moves the view to the end of the song
   */
  void jumpToEnd();

  /**
   * Adds a new note to the model at the location clicked with the new note values
   * @param e the mouse click
   */
  void addNote(MouseEvent e);

  /**
   * Updates the new note values to the values of the note clicked on
   * @param e the mouse click
   */
  void copyNote(MouseEvent e);

  /**
   * Deletes a note from the model at the location clicked and updates the new note values
   * to its values
   */
  void cutNote(MouseEvent e);

  /**
   * Displays the "Saved" indicator
   */
  void save();

  /**
   * Either adds a new goto or sets the what the gotoFrom will be
   *
   * @param e e the mouse click
   */
   void addGoto(MouseEvent e);

}
