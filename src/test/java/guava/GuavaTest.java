package guava;

import com.google.common.base.Objects;

public class GuavaTest {
	
	public static void main(String[] args) {
		Person person = new Person();
		person.setName("zhouliangliang");
		person.setScore(100);
		System.out.println(person);
	}
}

class Person {
	private String name;
	private Integer score;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	@Override
	//guava toString
	public String toString() {
		return Objects.toStringHelper(Person.class).add("name", name).add("score", score).toString();
		///
	}
}