package src.main;

public class Element{
	private static final int NEG = -1;
	private static final int POS = 1;

	int value;

	public Element(int val){
		if(val < 0){
			value = NEG;
		} else {
			value = POS;
		}
	}

	public void flip(){
		value *= NEG;
	}

	public int getValue(){
		return value;
	}
}