package jaxb;

import java.io.File;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Marshaller;

public class Object2xmlDemo {

	public static void main(String[] args) {
		Customer customer = new Customer();
		
		customer.setId(100);
		customer.setName("suo");
		customer.setAge(29);
		
		try{
			File file = new File("D:\\file.xml");
			JAXBContext jaxbContent = JAXBContext.newInstance(Customer.class);
			Marshaller jaxbMarshaller = jaxbContent.createMarshaller();
			
			jaxbMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			jaxbMarshaller.marshal(customer, file);
			jaxbMarshaller.marshal(customer, System.out);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
