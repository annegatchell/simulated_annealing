package src.main;

public class Experiment{


	public static void main(String[] args){
		
		int n = 50; //lattice size 50
		double t = 1000; //initial temp
		double alpha = 0.9999; //alpha = 1 - 1E-4
		Experiment e = new Experiment();
		e.runAnnealerExperiment(t, n, alpha);
	}

	public void runAnnealerExperiment(double t, int n, double alpha){
		SimulatedAnnealer sim = new SimulatedAnnealer(t, n, alpha);
		sim.anneal();
	}
}