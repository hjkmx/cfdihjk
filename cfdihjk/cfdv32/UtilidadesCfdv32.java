package cfdihjk.cfdv32;

import java.io.*;
import java.util.*;
import java.text.SimpleDateFormat;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.DatatypeConstants;
import java.sql.Blob;
import org.apache.commons.codec.binary.Base64;
import java.math.BigDecimal;
import java.math.RoundingMode;
import org.xml.sax.InputSource;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 *  <h1>Clase de utilidades para generar CFDI Versi&oacute;n 3.2</h1>
 *
 * @author Antonio Cervantes
 * @version 1.0
 * @since Junio 25, 2015
 *
 */

public class UtilidadesCfdv32
{
	/**
	 * Este m&eacute;todo sirve para obtener la fecha al momento de crear el comprobante
	 * @return Regresa un objeto XMLGregorianCalendar con la fecha y hora actual
	 */
	public XMLGregorianCalendar obtenerFecha()
	{
		XMLGregorianCalendar fechaEmision = null;
		try
		{
			GregorianCalendar c = new GregorianCalendar();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			Date fecha = new Date();
			fecha = sdf.parse(sdf.format(fecha));
			c.setTime(fecha);
			fechaEmision = DatatypeFactory.newInstance().newXMLGregorianCalendar(c);
			fechaEmision.setTimezone(DatatypeConstants.FIELD_UNDEFINED);
			fechaEmision.setFractionalSecond(null);

		}
		catch(Exception e)
		{
			System.out.println("Error al obtener fecha de cfdi: " + e.toString());
			fechaEmision = null;
		}
		return fechaEmision;
	}

	/**
	 * Este m&eacute;todo sirve para crear un objeto XMLGregorianCalendar solo con fecha
	 * @param fecha con el formato YYYY-MM-DD
	 * @return Regresa un objeto XMLGregorianCalendar con la fecha
	 */
	public XMLGregorianCalendar generarFecha(String paramFecha)
	{
		XMLGregorianCalendar fechaFija = null;
		try
		{
			GregorianCalendar c = new GregorianCalendar();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

			String [] datos = paramFecha.split("-");
			Date fecha = new Date(Integer.parseInt(datos[0])-1900,Integer.parseInt(datos[1])-1,Integer.parseInt(datos[2]));
			fecha = sdf.parse(sdf.format(fecha));
			c.setTime(fecha);
			fechaFija = DatatypeFactory.newInstance().newXMLGregorianCalendarDate(c.get(Calendar.YEAR),c.get(Calendar.MONTH)+1,c.get(Calendar.DAY_OF_MONTH),DatatypeConstants.FIELD_UNDEFINED);
			//fechaFija.setTimezone(DatatypeConstants.FIELD_UNDEFINED);
			//fechaFija.setMillisecond(DatatypeConstants.FIELD_UNDEFINED);
			//fechaFija.setFractionalSecond(null);
		}
		catch(Exception e)
		{
			System.out.println("Error al generar fecha de cfdi: " + e.toString());
			fechaFija = null;
		}
		return fechaFija;
	}

	/**
	 * Este m&eacute;todo sirve para convertir un archivo guardado en base de datos a un arreglo de bytes
	 * @param blob -> el archivo guardado en base de datos
	 * @return Regresa un arreglo de bytes con la informaci&oacute;n del archivo guardado en base de datos
	 */
	public byte[] blobToByteArray(Blob blob)
	{
		try
		{
			return blob.getBytes(1,(int)blob.length());
		}
		catch(Exception e)
		{
			System.out.println("Error al leer blob: " + e.toString());
			return null;
		}
	}

	/**
	 * Este m&eacute;todo sirve para codificar un arreglo de bytes en base64
	 * @param byteArray con la cadena a codificar en base64
	 * @return Regresa un String con la cadena codificada en base64
	 */
	public String encodeBase64ByteArray(byte [] byteArray)
	{
		return new String(Base64.encodeBase64(byteArray));
	}

	/**
	 * Este m&eacute;todo sirve para determinar la cantidad de decimales y ajustarlos autom&aacute;ticamente entre 2 y 6 posiciones
	 * @param bd
	 * @return Regresa un entero con la cantidad de decimales
	 */
	public int obtenerCantidadDecimales(BigDecimal bd)
	{
		String s = bd.stripTrailingZeros().toPlainString();
		int index = s.indexOf(".");
		index = (index < 0) ? 0 : s.length() - index - 1;
		index = (index > 6) ? 6 : index;
		index = (index < 2) ? 2 : index;

		return index;
	}

	/**
	 * Este m&eacute;todo sirve para formatear un BigDecimal autom&aacute;ticamente entre 2 y 6 posiciones
	 * @param bd
	 * @return Regresa un BigDecimal con los decimales ajustados
	 */
	public BigDecimal formatoBigDecimal(BigDecimal bd)
	{
		String s = bd.setScale(obtenerCantidadDecimales(bd),RoundingMode.HALF_EVEN).toPlainString();
		BigDecimal bd2 = new BigDecimal(s);
		return bd2.setScale(obtenerCantidadDecimales(bd2),RoundingMode.HALF_EVEN);
	}

	/**
	 * Este m&eacute;todo sirve para convertir un String de xml de un complemento a Element para poder pegarlo al CFDI
	 * @param c -> un String con el xml del complemento
	 * @return Regresa un Element con el xml del complemento
	 */
	public Element obtenerComplemento(String c)
	{
		//Obtengo el compelemento
		Element element = null;
		try
		{
			DocumentBuilderFactory miDbf = DocumentBuilderFactory.newInstance();
			DocumentBuilder miDb = miDbf.newDocumentBuilder();

			InputSource miInputSource = new InputSource();
			//miInputSource.setCharacterStream(new StringReader(org.apache.commons.io.FileUtils.readFileToString(new File(c+".xml"))));
			miInputSource.setCharacterStream(new StringReader(c));

			Document miDocumento = miDb.parse(miInputSource);
			element = miDocumento.getDocumentElement();

		}
		catch(Exception e)
		{
			System.out.println("Error al obtener el complemento " + c + ": " + e.toString());
		}
		return element;
	}

}
