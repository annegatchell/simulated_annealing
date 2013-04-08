/*
	Element.java

	Written By: Anne Gatchell
	Data Created: 7 April 2013
	Date Modified: 7 April 2013
*/

package src.main;

public class Element{
	private static final int NEG = -1;
	private static final int POS = 1;

	private int value;

	public Element(int val){
		if(val < 0){
			value = NEG;
		} else {
			value = POS;
		}
	}

	public void flip(){
		if(value < 0){
			value = POS;
		} else value = NEG;
	}

	public int getValue(){
		return value;
	}
}