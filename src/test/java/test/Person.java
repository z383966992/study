package test;

import com.google.common.base.Objects;

public class Person {
	
	private String name;
	private Integer age;
	
	public Person(String name, Integer age) {
		this.name = name;
		this.age = age;
	}

	@Override
	public String toString() {
		return Objects.toStringHelper(Person.class).add("name", this.name).add("age", this.age).toString();
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}
	
}
