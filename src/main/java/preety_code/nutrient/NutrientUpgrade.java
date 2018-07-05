package preety_code.nutrient;
/**
 * 对Nutrient的改进
 * @author liangliangzhou3
 */
public class NutrientUpgrade {

	private int water;
	private int salt;
	private int a;
	private int b;
	private int c;
	
	public NutrientUpgrade(Builder builder) {
		this.water = builder.water;
		this.salt = builder.salt;
		this.a = builder.a;
		this.b = builder.b;
		this.c = builder.c;
	}
	
	public static class Builder {
		private int water;
		private int salt;
		private int a;
		private int b;
		private int c;
		
		public Builder(int water, int salt) {
			this.water = water;
			this.salt = salt;
		}
		
		public Builder a (int a) {
			this.a = a;
			return this;
		}
		
		public Builder b (int b) {
			this.b = b;
			return this;
		}
		
		public Builder c (int c) {
			this.c = c;
			return this;
		}
		
		public NutrientUpgrade build() {
			return new NutrientUpgrade(this);
		}
	}
	
	@Override
	public String toString() {
		return this.water + " : " + this.salt + " : " + this.a + " : " + this.b + " : " + this.c;
	}
	
	public static void main(String[] args) {
		System.out.println(new NutrientUpgrade.Builder(10, 20).a(30).b(40).c(50).build());
		System.out.println(new NutrientUpgrade.Builder(100, 200).a(300).b(400).c(500).build());
	}
}
