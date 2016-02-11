package src.cs3500.view;

import src.cs3500.model.Song;

import javax.sound.midi.InvalidMidiDataException;
import java.awt.event.KeyListener;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by User on 11/22/2015.
 */
public final class CombinedView implements MusicView {

  private boolean hasT1 = false; //marks whether or not t1 has been schedule before
  private GUIView gui;
  private MidiViewImpl midi;
  private Song model;
  private int beat;
  private Timer t1 = new Timer();
  private TimerTaskThingy task1;

  /**
   * @param g     the GUIViewImpl to become part of the combined view
   * @param m     the MidiViewImpl to become part of the combined view
   * @param model the song the two views represent
   */
  public CombinedView(GUIView g, MidiViewImpl m, Song model) throws InvalidMidiDataException,
      InterruptedException {
    this.gui = g;
    this.midi = m;
    this.model = model;
    this.task1 = new TimerTaskThingy();
  }

  /**
   * Plays the notes at the beat in the song, and also draws the redline at them
   */
  @Override
  public final void play(int beat) {
    if (this.beat < model.getSongLength()) {
      midi.play(this.beat);
      gui.play(this.beat);
      if(model.currentGoTo().getFrom() == this.beat) {
        this.beat = model.currentGoTo().getTo();
        model.incrementGoTo();
      }
      else {
        this.beat++;
      }
    } else {
      gui.play(this.beat);
      //this.beat = 0;
    }
  }

  /**
   * Starts the timer and resets the beat
   */
  public final void start() {
    beat = 0;
    model.resetGoTo();
    if (!hasT1) {
      t1.scheduleAtFixedRate(task1, 0, model.getTempo() / 1000);
      hasT1 = true;
    }
    task1.resume();
  }

  /**
   * pauses the playing of the song
   */
  public final void pausePlay() {
    task1.pausePlay();
  }

  /**
   * adds the keyListener to the gui
   *
   * @param k the keyListener to be added to the gui
   */
  public final void addKeyListener(KeyListener k) {
    gui.addKeyListener(k);
  }


  @Override
  public final void display() throws InvalidMidiDataException, InterruptedException {
    gui.display();
  }

  public final void goToEnd() {
    beat = model.getSongLength();
    gui.jumpToEnd();
    gui.play(beat);
  }

  /**
   * The TimerTask for playing the song
   */
  protected final class TimerTaskThingy extends TimerTask {

    /**
     * whether or not it is paused
     */
    private boolean pause;

    /**
     * Creates a new TimerTaskThingy
     *
     */
    TimerTaskThingy() {}

    @Override
    public final void run() {
      if (!pause) {
        play(beat);
      }
    }

    /**
     * resumes this TimerTask
     */
    public final void resume() {
      this.pause = false;
    }

    /**
     * toggles this.pause for this TimerTask
     */
    public final void pausePlay() {
      this.pause = !this.pause;
    }
  }

  public GUIView getGUIView() {
    return this.gui;
  }

}
