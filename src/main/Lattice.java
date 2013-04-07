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
	int score;
	int n;
	Element[][] lattice;
	Random rand;


	//Constructor to create a random lattice of dimension n
	public Lattice(int size){
		if(size <= 0){
			System.err.println("Must create a positive lattice number, creating default 5x5 lattice");
			n = 5;
		}
		score = 0;
		n = size;
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

	private void calculateEntireScore(){
		int total = 0;
		for(int i = 0; i < n; i++){
			for(int j = 0; j < n; j++){
				total += lattice[i][j].getValue()*lattice[(i-1+n)%n][j].getValue() < 0 ? 0:1; //up
				total += lattice[i][j].getValue()*lattice[(i+1)%n][j].getValue() < 0 ? 0:1; //down
				total += lattice[i][j].getValue()*lattice[i][(j-1+n)%n].getValue() < 0 ? 0:1; //left
				total += lattice[i][j].getValue()*lattice[i][(j+1)%n].getValue() < 0 ? 0:1; //right
			}
		}
		score = total;
	}
}