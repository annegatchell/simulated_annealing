/*
  Lattice.java

  A representation of a two-dimensional lattice of 
  Elements.

  If draw is set, then the lattice will draw its
  state as a bitmap, and will update every time 
  flip() is called.

  This implementation is 2 dimensional, and each Element
  can take a value of -1 or +1. The energy of the lattice
  is given by 

    E = - SUM[for i != j]J_{ij}s_{i}s_{j}

  where s_{i} and s_{j} are neighboring lattice sites and J_{ij}
  denotes the coupling strength between them, where if
  the two sites are coupled (have same spin), J_{ij} = 1, and
  if they are not coupled, J_{ij} = 0.

  The SCORE of the lattice is -E. It is convenient to use -E
  since we are trying to maximize the score of a lattice.

  Neighbors are defined by the von Neumann neighborhood
  ("Hello.... Nnneumann")
  with periodic edge boundaries. Therefore, the lattice
  is topologically a taurus. 

  Written By: Anne Gatchell
  Data Created: 7 April 2013
  Date Modified: 7 April 2013
*/


package src.main;

import java.util.Random;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;

public class Lattice{
  private int score;
  private int n;
  private Element[][] lattice;
  private Random rand;
  private double totalElements;
  private double numNegatives;
  private int panelSize;
  private int bitsize = 5;
  private JFrame frame;
  private BitMapDrawPanel drawPanel;
  private String title;
  private boolean draw;

  //Constructor to create a random lattice of dimension n
  public Lattice(int size, String t, boolean willDraw){
    title = t;
    draw = willDraw;
    if(size <= 0){
      System.err.println("Must create a positive lattice number," +
        "creating default 5x5 lattice");
      n = 5;
    }
    score = 0;
    n = size;
    totalElements = n*n;
    numNegatives=0;
    //numPositives=0;
    rand = new Random();
    lattice = new Element[n][n];
    //Initialize lattice with random elements
    for(int i = 0; i < n; i++){
      for(int j = 0; j < n; j++){
        lattice[i][j] = new Element(rand.nextInt(2)-1);
      }
    }
    //Calculate score for full matrix
    calculateEntireScore();

    //Draw the lattice
    if(draw)
      setUpFrame();
    
  }

  //Constructor for a deep copy
  public Lattice(Lattice l, String t, boolean willDraw){
    title = t;
    draw = willDraw;
    if(l == null){
      System.err.println("Null lattice");
      return;
    }
    score = 0;
    n = l.n();
    totalElements = n*n;
    numNegatives=0;
    //numPositives=0;
    rand = new Random();
    lattice = new Element[n][n];
    //Initialize lattice with random elements
    for(int i = 0; i < n; i++){
      for(int j = 0; j < n; j++){
        lattice[i][j] = new Element(l.getLatticeValueAt(i,j));
      }
    }
    //Calculate score for full matrix
    calculateEntireScore();
    //Draw the lattice
    if(draw)
      setUpFrame();
  }

  //Constructor for an array of integers for lattice values
  public Lattice(int[][] values, String t, boolean willDraw){
    title = t;
    draw = willDraw;
    score = 0;
    n = values[0].length;
    totalElements = n*n;
    numNegatives=0;
    //numPositives=0;
    lattice = new Element[n][n];
    rand = new Random();
    //Initialize lattice with random elements
    for(int i = 0; i < n; i++){
      for(int j = 0; j < n; j++){
        lattice[i][j] = new Element(values[i][j]);
      }
    }
    //Calculate score for full matrix
    calculateEntireScore();
    //Draw the lattice
    if(draw)
      setUpFrame();
  }

  public void setUpFrame(){
    frame = new JFrame(title);
    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    drawPanel = new BitMapDrawPanel();

    panelSize = bitsize*n;
    frame.getContentPane().add(drawPanel);
    frame.setSize(panelSize,panelSize);
    frame.setVisible(true);

  }

  public void setFrameLocation(int x, int y){
    frame.setLocation(x, y);
  }

  public void repaint(){
    if(draw)
      drawPanel.repaint();
  }

  public int getLatticeValueAt(int i, int j){
    return lattice[i][j].getValue();
  }

  public int score(){
    return score;
  }

  public String toString(){
    StringBuffer s = new StringBuffer();
    for(int i = 0; i < n; i++){
      for(int j = 0; j < n; j++){
        s.append(lattice[i][j].getValue()+" ");
      }
      s.append("\n");
    }
    return s.toString();
  }

  public double percentageSame(){
    double neg = numNegatives/totalElements;
    double pos = 1-neg;
    if(neg > pos){
      return neg;
    }else{
      return pos;
    }
  }

  //Flips the state of the lattice element at i,j and returns
  //the resulting score for the lattice
  public int flip(int i, int j){
    int oldScore = 0;
    int newScore = 0;
    int current = lattice[i][j].getValue();
    if(current*lattice[(i-1+n)%n][j].getValue() < 0)
      newScore+=2;
    else oldScore+=2; //up
    if(current*lattice[(i+1)%n][j].getValue() < 0)
      newScore+=2;
    else oldScore+=2; //down
    if(current*lattice[i][(j-1+n)%n].getValue() < 0)
      newScore+=2;
    else oldScore+=2; //left
    if(current*lattice[i][(j+1)%n].getValue() < 0)
      newScore+=2;
    else oldScore+=2; //right
    //Perform flip
    lattice[i][j].flip();
    //Update score
    score -= oldScore;
    score += newScore;
    //Change nums negative and positives
    if(lattice[i][j].getValue() < 0){
      numNegatives++;
    }else{
      numNegatives--;
    }
    //Return score
    return score;
  }

  private void calculateEntireScore(){
    numNegatives=0;
    //numPositives=0;
    int total = 0;
    for(int i = 0; i < n; i++){
      for(int j = 0; j < n; j++){
        total += lattice[i][j].getValue()*lattice[(i-1+n)%n][j].getValue() < 0
             ? 0:1; //up
        total += lattice[i][j].getValue()*lattice[(i+1)%n][j].getValue() < 0
             ? 0:1; //down
        total += lattice[i][j].getValue()*lattice[i][(j-1+n)%n].getValue() < 0
             ? 0:1; //left
        total += lattice[i][j].getValue()*lattice[i][(j+1)%n].getValue() < 0
             ? 0:1; //right
        if(lattice[i][j].getValue() < 0){
          numNegatives++;
        }
      }
    }
    score = total;
  }
  public int n(){
    return n;
  }

  public void closeFrame(){
    if(frame != null)
      frame.dispatchEvent(new WindowEvent(frame, WindowEvent.WINDOW_CLOSING));
  }

  class BitMapDrawPanel extends JPanel{
    public void paintComponent(Graphics g){
      g.setColor(Color.white);
      g.fillRect(0,0,this.getWidth(), this.getHeight());
      g.setColor(Color.black);
      int x = 0;
      int y = 0;
      for(int i = 0; i < n; i++){
        for(int j = 0; j < n; j++){
          if(lattice[i][j].getValue() < 0){
            g.fillRect(x,y,bitsize,bitsize);
          }
          x += bitsize;
        }
        y += bitsize;
        x = 0;
      }
    }
  }
}