package jaxb;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;

public class Xml2ObjectDemo {
	
	public static void main(String[] args) {
		try {
			File file = new File("D:\\file.xml");
			JAXBContext jaxbContent = JAXBContext.newInstance(Customer.class);
			Unmarshaller jaxbUnmarshaller = jaxbContent.createUnmarshaller();
			Customer customer = (Customer) jaxbUnmarshaller.unmarshal(file);
			System.out.println(customer);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
