package preety_code.nutrient;

public class Nutrient {
	
	private int water;
	private int salt;
	private int a;
	private int b;
	private int c;
	
	public Nutrient(int water, int salt) {
		this(water, salt, 0);
	}
	
	public Nutrient(int water, int salt, int a) {
		this(water, salt, a, 0);
	}

	public Nutrient(int water, int salt, int a, int b) {
		this(water, salt, a, b, 0);
	}
	
	public Nutrient(int water, int salt, int a, int b, int c) {
		this.water = water;
		this.salt = salt;
		this.a = a;
		this.b = b;
		this.c = c;
	}
	
	@Override
	public String toString() {
		return this.water + " : " + this.salt + " : " + this.a + " : " + this.b + " : " + this.c;
	}
	
	
	public static void main(String[] args) {
		Nutrient nu = new Nutrient(10, 10, 20, 30, 40);
		System.out.println(nu);
	}
}
