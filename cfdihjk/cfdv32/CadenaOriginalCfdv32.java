package cfdihjk.cfdv32;

import java.io.*;
import javax.xml.transform.*;
import javax.xml.transform.stream.*;
import javax.xml.bind.util.JAXBSource;
import javax.xml.bind.*;
import java.net.URL;

/**
 * <h1>Clase para generar la cadena original de un CFDI a partir de los archivos XSLT</h1>
 * 
 * @autor Antonio Cervantes
 * @version 1.0
 * @since Junio 26, 2015
 *
 */

public class CadenaOriginalCfdv32
{
	/**
	 * Este m&eacute;todo sirve para obtener un String con la cadena orginal en UTF8
	 * @param cfdi
	 * @param xslt
	 * @return Regresa un String con la cadena original
	 */
	public String getCadenaOriginal(Class miClase,cfdihjk.cfdv32.Cfdv32 cfdi,String xslt) throws Exception
	{
		byte[] bytes = getCadenaOriginalBytes(miClase,cfdi,xslt);
		return new String(bytes, "UTF8");
	}

	/**
	 * Este m&eacute;todo sirve para obtener un arreglo de bytes con la cadena orginal
	 * @param cfdi
	 * @param xslt
	 * @return Regresa un arreglo de bytes con la cadena original
	 */
	byte[] getCadenaOriginalBytes(Class miClase,cfdihjk.cfdv32.Cfdv32 cfdi,String xslt) throws Exception
	{
		JAXBContext context = JAXBContext.newInstance(miClase);
		JAXBSource in = new JAXBSource(context,cfdi);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Result out = new StreamResult(baos);
		TransformerFactory factory = TransformerFactory.newInstance();

		//Transformer transformer = factory.newTransformer(new StreamSource(getClass().getResourceAsStream(xslt)));
		Transformer transformer = factory.newTransformer(new StreamSource(xslt));

		/*
		URL xsltURL = this.getClass().getClassLoader().getResource(xslt);
		Source xsltSource = new StreamSource(xsltURL.openStream(),xsltURL.toExternalForm());

		Transformer transformer = factory.newTransformer(xsltSource);
		*/

		transformer.transform(in, out);
		return baos.toByteArray();
	}
}
