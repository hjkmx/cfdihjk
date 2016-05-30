package cfdihjk.cfdv32;

import java.io.*;
import javax.xml.bind.*;

public class Cfdv32Marshaller
{
	public void marshal_command_line(Cfdv32 cfdi)
	{
		try
		{
			JAXBContext context = JAXBContext.newInstance(Comprobante.class);
			Marshaller m = context.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			m.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, "http://www.sat.gob.mx/cfd/3 http://www.sat.gob.mx/sitio_internet/cfd/3/cfdv32.xsd");
			m.marshal(cfdi, System.out);
		}
		catch(JAXBException jbe)
		{
			System.out.println("Error en marshal: " + jbe.toString());
		}
	}

	public String marshal(Cfdv32 cfdi)
	{
		String xml = "";
		try
		{
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			JAXBContext context = JAXBContext.newInstance(Comprobante.class);
			Marshaller m = context.createMarshaller();
			//m.setProperty("com.sun.xml.bind.xmlDeclaration", Boolean.FALSE);

			m.setProperty("com.sun.xml.internal.bind.xmlHeaders", "<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
			m.setProperty(Marshaller.JAXB_FRAGMENT, Boolean.TRUE);
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
			m.setProperty(Marshaller.JAXB_SCHEMA_LOCATION, "http://www.sat.gob.mx/cfd/3 http://www.sat.gob.mx/sitio_internet/cfd/3/cfdv32.xsd");
			m.marshal(cfdi,baos);
			xml = new String(baos.toByteArray(),"utf-8");
			xml = xml.replaceAll("'","&apos;");
		}
		catch(JAXBException jbe)
		{
			System.out.println("Error en marshal: " + jbe.toString());
		}
		catch(UnsupportedEncodingException uee)
		{
			System.out.println("Error en marshal: " + uee.toString());
		}
		return xml;
	}
}
