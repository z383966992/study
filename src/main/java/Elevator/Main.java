package Elevator;

public class Main {
	
	public static void main(String[] args) throws Exception{
		Elevator elev = new Elevator(20);
		elev.run(5);
		elev.run(3);
		elev.run(10);
	}
}
