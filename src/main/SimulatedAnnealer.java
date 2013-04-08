/*
	SimulatedAnnealer.java

	
	
	Written By: Anne Gatchell
	Data Created: 7 April 2013
	Date Modified: 7 April 2013
*/


package src.main;

import java.util.Random;

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

	public void anneal(){
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
				currentScore = proposedScore;	//Accept - higher score
				rejects = 0;
			} else if (prKeep >= flip){
				currentScore = proposedScore;	//Accept - lower score
				rejects = 0;
			} else {
				rejects++;
				l.flip(i,j); 					//Reject, flip bcack
			}
			if(!got80snapshot && l.percentageSame() >= 0.8){
				snapshot80 = new Lattice(l, "80% Same", true);
				snapshot80.setFrameLocation(0,500);
				got80snapshot = true;
			}
			t = t*a;
			l.repaint();
			try{
				Thread.sleep(1);
			} catch (Exception ex){}
		}
		snapshotFinal = new Lattice(l, "Final Arrangement",true);
		snapshotFinal.setFrameLocation(500,0);
	}
	
}