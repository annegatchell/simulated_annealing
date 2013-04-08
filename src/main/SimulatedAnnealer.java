/*
  SimulatedAnnealer.java

  This is a simulated annealer implemented using the Ising model
  of magnetization.

  The annealer runs the simulation from a starting temperature
  provided by the user when instantiating the class.

  The user also provides an alpha value, and may either provide
  a Lattice object or provide a dimension n, for creation of a
  random nxn lattice to then run annealing on.

  The cooling schedule is t_new = t_old*alpha.

  At each iteration, the algorithm proposes that a random
  particle flip the sign of its state. If the resulting
  lattice has a better score (ie. more favorable energy)
  then the flip is kept. If the flip is not more favorable,
  then the flip is kept with a probability

  Pr(keep) = e^-(currentScore - proposedScore)/t
  
  Otherwise, the state is flipped back to the previous state.

  The simulation stops once there have been n^2 rejected
  proposals.
  
  Written By: Anne Gatchell
  Data Created: 7 April 2013
  Date Modified: 8 April 2013
*/


package src.main;

import java.util.Random;
import java.lang.String;

public class SimulatedAnnealer{
  Lattice l;
  double t;
  double a;
  Random rand;
  Lattice snapshot1, snapshot80, snapshotFinal;

  //Constructor for premade lattice
  public SimulatedAnnealer(double t0, Lattice lat, double alpha){
    t = t0;
    l = lat;
    a = alpha;
    rand = new Random();
  }
  //Constructor for make-me-a-lattice
  public SimulatedAnnealer(double t0, int n, double alpha){
    t = t0;
    l = new Lattice(n, "Lattice Sim", true);
    l.setFrameLocation(500,500);
    a = alpha;
    rand = new Random();
  }

  public void anneal(boolean animate){
    System.out.println("Initial Lattice, t = "+ t +", E = "+ l.score()
          +", majority frac ="+l.percentageSame());
    snapshot1 = new Lattice(l, "Initial Lattice", true);
    snapshot1.setFrameLocation(0,0);
    boolean got80snapshot = false;
    int rejects = 0;
    int maxRejects = l.n()*l.n();
    int currentScore = l.score();
    int proposedScore, delta;
    int i, j;
    double exponent, prKeep, flip; 
    while(rejects < maxRejects){
      i = rand.nextInt(l.n());
      j = rand.nextInt(l.n());
      proposedScore = l.flip(i,j);
      delta = currentScore - proposedScore;
      exponent = -(delta)/t;
      prKeep = Math.exp(exponent);
      flip = rand.nextFloat();
      //We want a higher score, so keep negative deltas
      if(delta < 0){
        currentScore = proposedScore; //Accept - higher score
        rejects = 0;
      } else if (prKeep >= flip){
        currentScore = proposedScore; //Accept - lower score
        rejects = 0;
      } else {
        rejects++;
        l.flip(i,j);          //Reject, flip bcack
      }
      if(!got80snapshot && l.percentageSame() >= 0.8){
        System.out.println("80% Same Lattice, t = "+ t +", E = "
          + l.score()+", majority frac ="+l.percentageSame());
        snapshot80 = new Lattice(l, "80% Same", true);
        snapshot80.setFrameLocation(0,500);
        got80snapshot = true;
      }
      t = t*a;
      l.repaint();
      if(animate){
        try{
          Thread.sleep(1);
        } catch (Exception ex){}
      }
    }
    System.out.println("Final Arrangement, t = "+ t +", E = "+ 
          l.score()+", majority frac ="+l.percentageSame());
    snapshotFinal = new Lattice(l, "Final Arrangement",true);
    snapshotFinal.setFrameLocation(500,0);
  }
  
}