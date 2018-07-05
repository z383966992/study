package annotation.test;

import annotation.definition.CheckNull;
import annotation.definition.NotNull;

public class Person {
	
	@CheckNull
	private String personName;
	@CheckNull
	private char cc;
	@CheckNull
	private Integer inte;

	public Integer getInte() {
		return inte;
	}

	public void setInte(Integer inte) {
		this.inte = inte;
	}

	public String getPersonName() {
		return personName;
	}

	public void setPersonName(String personName) {
		this.personName = personName;
	}
	
}
