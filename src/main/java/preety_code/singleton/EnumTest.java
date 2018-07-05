package preety_code.singleton;

public enum EnumTest {
	
	RED("roses", 1) {
		@Override
		public String color() {
			return "red";
		}
	},
	YELLOW("chrysanthemum", 3) {
		@Override
		public String color() {
			return "yellow";
		}
	};
	
	private String name;
	private int num;
	
	public abstract String color();
	
	@Override
	public String toString() {
		return name + "_" + num;
	};
	
	private EnumTest(String name, int num) {
		this.name = name;
		this.num = num;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}
}
