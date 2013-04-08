/*
	Lattice.java

	A representation of a two-dimensional lattice of 
	Elements.

	Written By: Anne Gatchell
	Data Created: 7 April 2013
	Date Modified: 7 April 2013
*/


package src.main;

import java.util.Random;

public class Lattice{
	private int score;
	private int n;
	private Element[][] lattice;
	private Random rand;
	private int totalElements;
	private int numNegatives;
	private int numPositives;


	//Constructor to create a random lattice of dimension n
	public Lattice(int size){
		if(size <= 0){
			System.err.println("Must create a positive lattice number, creating default 5x5 lattice");
			n = 5;
		}
		score = 0;
		n = size;
		totalElements = n*n;
		numNegatives=0;
		numPositives=0;
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
		
	}

	public Lattice(int[][] values){
		score = 0;
		n = values[0].length;
		totalElements = n*n;
		numNegatives=0;
		numPositives=0;
		lattice = new Element[n][n];
		//Initialize lattice with random elements
		for(int i = 0; i < n; i++){
			for(int j = 0; j < n; j++){
				lattice[i][j] = new Element(values[i][j]);
			}
		}
		//Calculate score for full matrix
		calculateEntireScore();
	}

	public int getScore(){
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
		double pos = numPositives/totalElements;
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
		System.out.println("current "+current);
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
		System.out.println("new "+lattice[i][j].getValue());
		System.out.println("old score "+ oldScore + " new score "+newScore);
		//Update score
		score -= oldScore;
		score += newScore;
		//Change nums negative and positives
		if(lattice[i][j].getValue() < 0){
			numNegatives++;
			numPositives--;
		} else {
			numNegatives--;
			numPositives++;
		}
		
		//Return score
		return score;
	}

	private void calculateEntireScore(){
		numNegatives=0;
		numPositives=0;
		int total = 0;
		for(int i = 0; i < n; i++){
			for(int j = 0; j < n; j++){
				total += lattice[i][j].getValue()*lattice[(i-1+n)%n][j].getValue() < 0 ? 0:1; //up
				total += lattice[i][j].getValue()*lattice[(i+1)%n][j].getValue() < 0 ? 0:1; //down
				total += lattice[i][j].getValue()*lattice[i][(j-1+n)%n].getValue() < 0 ? 0:1; //left
				total += lattice[i][j].getValue()*lattice[i][(j+1)%n].getValue() < 0 ? 0:1; //right
				int trash = lattice[i][j].getValue() < 0 ? numNegatives++ : numPositives++;
			}
		}
		score = total;
	}
}