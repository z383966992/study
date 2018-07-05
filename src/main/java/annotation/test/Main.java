package annotation.test;

import annotation.parser.ParserDispatcher;

public class Main {
	
	public void test() {
		ParserDispatcher ap = new ParserDispatcher();
		Person person = new Person();
		person.setPersonName("zhouliangliang");
		person.setInte(100);
		ap.parser(person);
	}
	
	public void sqlConnectorAnnotationTest() {
		ParserDispatcher dispather = new ParserDispatcher();
		DBOperate operator = new DBOperate();
		dispather.parser(operator);
		
		operator.read();
	}
	
	public static void main(String[] args) throws Exception{
		new Main().sqlConnectorAnnotationTest();
	}
}
