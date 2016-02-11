package src.cs3500.controller;

import src.cs3500.model.Song;
import src.cs3500.model.SongImpl;
import src.cs3500.util.MusicReader;
import src.cs3500.util.SaveSong;
import src.cs3500.view.*;

import javax.sound.midi.InvalidMidiDataException;
import javax.sound.midi.MidiUnavailableException;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.UnsupportedEncodingException;

/**
 * Created by DylanWight on 11/22/15.
 */
public final class Controller {
  /**
   * The name of the file to read and write from
   */
  private String fileName;

  /**
   * The model of the song  being edited
   */
  private Song songModel;

  /**
   * The GUI being run by the controller
   */
  private GUIView GUI;

  /**
   * The view being run by the controller, usually is combined
   */
  private MusicView view;


  /**
   * Constructs a new controller for the music editor
   *
   * @param fileName the fileName to be read or to name the new file
   * @param viewType the view type to constuct
   * @throws InvalidMidiDataException
   * @throws InterruptedException
   * @throws MidiUnavailableException
   */
  public Controller(String fileName, String viewType)
      throws InvalidMidiDataException, InterruptedException, MidiUnavailableException {

    this.fileName = fileName;
    try {
      this.songModel = MusicReader.parseFile(new FileReader(fileName), new SongImpl.Builder());
    } catch (FileNotFoundException e) {
      this.songModel = new SongImpl();
    }

    this.view = MusicView.Factory.makeView(songModel, viewType);
    this.GUI = new GUIViewImpl(songModel);
    this.loadInputs(viewType);
    view.display();
  }

  public final MusicView getView() {
    return view;
  }

  private final void saveFile() throws FileNotFoundException, UnsupportedEncodingException {
    GUI.save();
    SaveSong.save(this.songModel, this.fileName);
  }

  /**
   * Loads and set the Mouse and Key Listeners for the viewType
   * @param viewType the type of the view
   */
  private final void loadInputs(String viewType)
      throws InvalidMidiDataException, MidiUnavailableException, InterruptedException {
    KeyListenerImpl keyListener = new KeyListenerImpl();
    keyListener.addRunnable(KeyEvent.VK_UP, () -> GUI.shiftUp(), "keyPressed");
    keyListener.addRunnable(KeyEvent.VK_DOWN, () -> GUI.shiftDown(), "keyPressed");
    keyListener.addRunnable(KeyEvent.VK_RIGHT, () -> GUI.shiftRight(), "keyPressed");
    keyListener.addRunnable(KeyEvent.VK_LEFT, () -> GUI.shiftLeft(), "keyPressed");
    keyListener.addRunnable(KeyEvent.VK_Q, () -> GUI.durationDown(), "keyPressed");
    keyListener.addRunnable(KeyEvent.VK_W, () -> GUI.durationUp(), "keyPressed");
    keyListener.addRunnable(KeyEvent.VK_Z, () -> GUI.instrumentDown(), "keyPressed");
    keyListener.addRunnable(KeyEvent.VK_X, () -> GUI.instrumentUp(), "keyPressed");
    keyListener.addRunnable(KeyEvent.VK_A, () -> GUI.volumeDown(), "keyPressed");
    keyListener.addRunnable(KeyEvent.VK_S, () -> GUI.volumeUp(), "keyPressed");
    keyListener.addRunnable(KeyEvent.VK_O, () -> {
      try {
        this.saveFile();
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      } catch (UnsupportedEncodingException e) {
        System.out.print("Could Not Save Song.");
        e.printStackTrace();
      }
    }, "keyPressed");

    MouseListenerImpl mouseListener = new MouseListenerImpl();
    mouseListener.addConsumer(1, o -> GUI.addNote((MouseEvent) o));
    mouseListener.addConsumer(2, o -> GUI.copyNote((MouseEvent) o));
    mouseListener.addConsumer(3, o -> GUI.cutNote((MouseEvent) o));
    mouseListener.addConsumer(11, o -> GUI.addGoto((MouseEvent) o));
    GUI.addMouseListener(mouseListener);

    switch (viewType) {

      case "visual":
        keyListener.addRunnable(KeyEvent.VK_HOME, () -> GUI.jumpToBeg(), "keyPressed");
        keyListener.addRunnable(KeyEvent.VK_END, () -> GUI.jumpToEnd(), "keyPressed");
        GUI.addKeyListener(keyListener);
        view = GUI;
        break;

      default:
        CombinedView cv = new CombinedView(GUI, new MidiViewImpl(songModel, null), songModel);
        keyListener.addRunnable(KeyEvent.VK_SPACE, () -> cv.pausePlay(), "keyPressed");
        keyListener.addRunnable(KeyEvent.VK_P, () -> cv.start(), "keyPressed");
        keyListener.addRunnable(KeyEvent.VK_HOME, () -> cv.start(), "keyPressed");
        keyListener.addRunnable(KeyEvent.VK_END, () -> cv.goToEnd(), "keyPressed");
        cv.addKeyListener(keyListener);
        view = cv;
    }
  }
}
