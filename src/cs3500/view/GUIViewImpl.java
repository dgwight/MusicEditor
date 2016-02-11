package src.cs3500.view;


import src.cs3500.model.GoTo;
import src.cs3500.model.Note;
import src.cs3500.model.Song;

import javax.sound.midi.InvalidMidiDataException;
import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by DylanWight on 11/21/15.
 */

/**
 * GUIViewImpl class Initializes the ViewGUI object and builds all the components required for the
 * view (note squares, borders, labels, etc)
 */
public final class GUIViewImpl extends JComponent implements GUIView {

  private int beat;
  /**
   * The song the view is representing
   */
  private Song model;

  /**
   * The JFrame of the GUI window
   */
  final private JFrame frame;

  /**
   * The current lowest beat  shown on the screen
   */
  private int colStart;

  /**
   * The current lowest pitch shown on the screen
   */
  private int rowStart;

  /**
   * The number of columns shown on the screen at a time
   */
  private final int numColumns = 60;

  /**
   * The number of rows shown on the screen at a time
   */
  private final int numRows = 30;

  /**
   * The width of each box in pixels
   */
  private final int width = 20;

  /**
   * The height of each box in pixels
   */
  private final int height = 20;

  /**
   * The number of pixels to the left of the song display
   */
  private final int horBorder = 40;

  /**
   * The number of pixels above the song display
   */
  private final int vertBorder = 30;

  /**
   * The beat that the song is currently on
   */
  private int trueBeat;

  /**
   * The duration that new notes will be added with
   */
  private int newNoteDuration = 4;

  /**
   * The instrument that new notes will be added with
   */
  private int newNoteInstrument = 0;

  /**
   * The volume that new notes will be added with
   */
  private int newNoteVolume = 64;

  /**
   * Used to check if the song has been saved
   */
  private Boolean saved = true;

  /**
   * Holds the from to add a GoTo
   */
  private int goToFrom;

  /**
   * Set to true to add a goto
   */
  private Boolean makingGoto = false;

  /**
   * Constructor forGUIViewImpl
   * Makes a new JFrame with the GUI in in
   *
   * @param model The song model to make a GUI for
   */
  public GUIViewImpl(Song model) {
    this.model = model;

    this.colStart = 0;
    this.rowStart = model.getHighestTone();
    this.beat = -5; //this prevents it from being drawn on-screen unless being used by composite

    this.frame = new JFrame();
    setPreferredSize(
        new Dimension(numColumns * width + vertBorder * 3 / 2, numRows * height + vertBorder + 100));

    this.frame.add(this);
    this.frame.pack();
    this.frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
  }

  /**
   * Builds all of the visual components of the ViewGUI, including the colored note squares,
   * measures, note labels, etc.
   *
   * @param g graphical object to build on
   */
  @Override
  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    for (int column = 0; column < numColumns; column++) {
      for (int row = 0; row < numRows; row++) {
        if (column == 0) {
          g.setColor(Color.BLACK);
          // Draw Pitch symbol
          String note = Song.getPitchSymbol(rowStart - row);
          g.drawString(note, horBorder / 4, vertBorder + 15 + (row * height));
        }
        switch (model.getSymbol(column + colStart, rowStart - row)) {
          case "X":
            g.setColor(Color.BLACK);
            break;
          case "|":
            g.setColor(Color.BLUE);
            break;
          default:
            g.setColor(Color.WHITE);
        }
        g.fillRect(horBorder + width * column, vertBorder + height * row, width, height);
        // Draw horizontal lines
        g.setColor(Color.BLACK);
        g.drawLine(horBorder, vertBorder + height * row, horBorder + numColumns * width,
            vertBorder + height * row);
      }
      // Draw beat numbers and vertical lines
      if (column % 4 == 0) {
        g.drawString(String.valueOf(column + colStart), horBorder + width * column,
            horBorder * 2 / 3);
        g.setColor(Color.BLACK);
        g.drawLine(horBorder + width * column, vertBorder, horBorder + width * column,
            vertBorder + height * numRows);
      }
    }
    // Draw song ending vertical line
    g.setColor(Color.BLACK);
    g.drawLine(horBorder + width * numColumns, vertBorder, horBorder + width * numColumns,
        vertBorder + height * numRows);

    // Draw last horizontal line
    g.drawLine(horBorder, vertBorder + height * numRows, horBorder + numColumns * width,
        vertBorder + height * numRows);

    //Draws the redLine
    if (trueBeat > colStart && trueBeat <= colStart + 60 && beat >= 0) {
      g.setColor(Color.RED);
      g.drawLine(horBorder + width * (trueBeat - colStart), vertBorder,
          horBorder + width * (trueBeat - colStart), vertBorder + height * numRows);
      g.drawLine(horBorder + width * (trueBeat - colStart) + 1, vertBorder,
          horBorder + width * (trueBeat - colStart) + 1, vertBorder + height * numRows);
    }
    g.setColor(Color.BLACK);

    g.drawString(String
            .format("Duration: %d\t Volume: %d\t Instrument: %s", newNoteDuration, newNoteVolume,
                Song.getInstrumentName(newNoteInstrument)), horBorder,
        vertBorder + numRows * height + 40);

    g.drawString(String.format("Tempo: %d beats per minute", 60000000 / model.getTempo()),
        horBorder, vertBorder + numRows * height + 60);

    // Draw GoTos
    int offset = 0;
    g.setColor(Color.darkGray);
    for (GoTo goTo : model.getGoTos()) {
      offset = (offset + 2) % 20;
      if (goTo == model.currentGoTo()){
        g.setColor(new Color(0, 200, 80));
      }
      // GoTo head and tail in sight
      if (goTo.getTo() >= colStart && goTo.getTo() < colStart + 60 &&
          goTo.getFrom() >= colStart && goTo.getFrom() < colStart + 60) {
        drawGotoHead((goTo.getTo() - colStart) * width + horBorder + width / 2, offset, g);
        drawGotoTail((goTo.getFrom() - colStart) * width + horBorder + width / 2, offset, g);
        drawGotoLine((goTo.getTo() - colStart) * width + horBorder + width / 2,
            (goTo.getFrom() - colStart) * width + horBorder + width / 2, offset, g);
      } // GoTo head in sight
      else if (goTo.getTo() >= colStart &&goTo.getTo() < colStart + 60) {
        drawGotoHead((goTo.getTo() - colStart) * width + horBorder + width / 2, offset, g);
        if (goTo.getFrom() > goTo.getTo()) {
          drawGotoLine((goTo.getTo() - colStart) * width + horBorder + width / 2,
              numColumns * width + horBorder, offset, g);
        } else {
          drawGotoLine((goTo.getTo() - colStart) * width + horBorder + width / 2, horBorder, offset,
              g);
        }
      } // GoTo tail in sight
      else if (goTo.getFrom() >= colStart && goTo.getFrom() < colStart + 60) {
        drawGotoTail((goTo.getFrom() - colStart) * width + horBorder + width / 2, offset, g);
        if (goTo.getFrom() > goTo.getTo()) {
          drawGotoLine((goTo.getFrom() - colStart) * width + horBorder + width / 2,
              horBorder, offset, g);
        } else {
          drawGotoLine((goTo.getFrom() - colStart) * width + horBorder + width / 2,
              numColumns * width + horBorder, offset, g);
        }
      } // GoTo across sight
      else if ((goTo.getFrom() <= colStart && goTo.getTo() > colStart + 60 )
          ||(goTo.getFrom() <= colStart && goTo.getTo() > colStart + 60)) {
        drawGotoLine(horBorder + width / 2,
            numColumns * width + horBorder + width / 2, offset, g);
      }
    }

    if (saved) {
      g.setColor(Color.darkGray);
      g.drawString("-Saved-",
          horBorder, vertBorder + numRows * height + 80);
    }
  }

  private void drawGotoHead(int head, int offset, Graphics g) {
    g.drawLine(head, vertBorder + numRows * height + 10 + offset, head,
        vertBorder + numRows * height);
    g.drawLine(head + 5, vertBorder + numRows * height + 5, head, vertBorder + numRows * height);
    g.drawLine(head - 5, vertBorder + numRows * height + 5, head, vertBorder + numRows * height);
  }

  private void drawGotoTail(int tail, int offset, Graphics g) {
    g.drawLine(tail, vertBorder + numRows * height + 10 + offset, tail,
        vertBorder + numRows * height);
  }

  private void drawGotoLine(int start, int end, int offset, Graphics g) {
    g.drawLine(start, vertBorder + numRows * height + 10 + offset, end,
        vertBorder + numRows * height + 10 + offset);
  }

  @Override
  public void addGoto(MouseEvent e) {
    if (this.makingGoto) {
      model.addGoto(goToFrom, colStart + (e.getX() - horBorder) / width);
      this.makingGoto = false;
      this.saved = false;
      repaint();
    }
    else {
      goToFrom = colStart + (e.getX() - horBorder) / width;
      this.makingGoto = true;
    }
  }

  @Override
  public void addKeyListener(KeyListener keyListener) {
    frame.addKeyListener(keyListener);
  }

  @Override
  public void addMouseListener(MouseListener mouseListener) {
    frame.addMouseListener(mouseListener);
  }

  @Override
  public void display() throws InvalidMidiDataException, InterruptedException {
    frame.setVisible(true);
    //stops midi from playing after the window is closed
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
  }

  @Override
  public final void shiftDown() {
    if (rowStart - numRows >= 0) {
      rowStart -= 1;
      repaint();
    }
  }

  @Override
  public final void shiftUp() {
    if (rowStart < 127) {
      rowStart += 1;
      repaint();
    }
  }

  @Override
  public final void shiftRight() {
    colStart += 4;
    repaint();
  }

  @Override
  public final void shiftLeft() {
    if (colStart > 0) {
      colStart -= 4;
      repaint();
    }
  }

  @Override
  public final void volumeUp() {
    if (newNoteVolume < 127) {
      newNoteVolume += 1;
      repaint();
    }
  }

  @Override
  public final void volumeDown() {
    if (newNoteVolume > 0) {
      newNoteVolume -= 1;
      repaint();
    }
  }

  @Override
  public final void instrumentUp() {
    newNoteInstrument += 1;
    if (newNoteInstrument == 16) {
      newNoteInstrument = 0;
    }
    repaint();
  }

  @Override
  public final void instrumentDown() {
    newNoteInstrument -= 1;
    if (newNoteInstrument == -1) {
      newNoteInstrument = 15;
    }
    repaint();
  }

  @Override
  public final void durationUp() {
    if (newNoteDuration < 128) {
      newNoteDuration += 1;
      repaint();
    }
  }

  @Override
  public final void durationDown() {
    if (newNoteDuration > 1) {
      newNoteDuration -= 1;
      repaint();
    }
  }

  @Override
  public final void jumpToEnd() {
    if (model.getSongLength() % 60 == 0) {
      colStart = (((model.getSongLength() / 60) - 1) * 60);
    } else {
      colStart = ((model.getSongLength() / 60) * 60);
    }
    repaint();
  }

  @Override
  public final void jumpToBeg() {
    colStart = 0;
    repaint();
  }

  @Override
  public final void copyNote(MouseEvent e) {
    if (e.getX() > horBorder && e.getX() < horBorder + numColumns * width && e.getY() > vertBorder
        && e.getY() < vertBorder + (numRows + 1) * height) {

      Note copied = model.getNoteAt(colStart + (e.getX() - horBorder) / width,
          (rowStart + 1 - (e.getY() - vertBorder) / height));
      if (copied != null) {
        newNoteDuration = copied.getDuration();
        newNoteVolume = copied.getVolume();
        newNoteInstrument = copied.getInstrument();
        repaint();
      }
    }
  }

  @Override
  public final void cutNote(MouseEvent e) {
    if (e.getX() > horBorder && e.getX() < horBorder + numColumns * width && e.getY() > vertBorder
        && e.getY() < vertBorder + (numRows + 1) * height) {

      Note cutNote = model.deleteNote(colStart + (e.getX() - horBorder) / width,
          (rowStart + 1 - (e.getY() - vertBorder) / height));
      if (cutNote != null) {
        newNoteDuration = cutNote.getDuration();
        newNoteVolume = cutNote.getVolume();
        newNoteInstrument = cutNote.getInstrument();
      }
      saved = false;
      repaint();
    }
  }

  @Override
  public final void addNote(MouseEvent e) {
    if (e.getX() > horBorder && e.getX() < horBorder + numColumns * width && e.getY() > vertBorder
        && e.getY() < vertBorder + (numRows + 1) * height) {

      model.addNote(colStart + (e.getX() - horBorder) / width,
          (rowStart + 1 - (e.getY() - vertBorder) / height), newNoteDuration, newNoteInstrument,
          newNoteVolume);
      saved = false;
      this.makingGoto = false;
      repaint();
    }
  }

  @Override
  public final void play(int beat) {
    this.beat = 0;
    trueBeat = beat;
    if (beat == 0) {
      colStart = 0;
    } else if (trueBeat == colStart + 60) {
      colStart += 60;
    }
    repaint();
  }

  @Override
  public final void save() {
    saved = true;
    repaint();
  }

  public GUIView getGUIView() {
    return this;
  }

}
