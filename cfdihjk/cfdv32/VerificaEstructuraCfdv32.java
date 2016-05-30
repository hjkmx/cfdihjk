package cfdihjk.cfdv32;

import javax.xml.XMLConstants;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.*;
import java.net.URL;
import java.io.StringReader;
import java.util.*;

/**
 * <h1>Clase de utilidades para generar CFDI Versi&oacute;n 3.2</h1>
 *
 * @author Antonio Cervantes
 * @version 1.0
 * @since Junio 25, 2015
 * 
 */
public class VerificaEstructuraCfdv32
{
	 /**
	 * Este m&eacute;todo sirve para validar la estructura de Cfdiv32
	 * @param miXml -> String con el xml a validar
	 * @return Regresa un String, si su valor es "ok" es correcto, de lo contrario van los mensajes de error
	 */
	public String verificaEstructuraXml(String miXml)
	{
		String r = "ok";
		try
		{
			String [] miSchemaInicial = obtenerSchemas(miXml);
			//Valido solo schema del SAT y de w3.org
			int s = 0;
			for(int x=0; x<miSchemaInicial.length; x++)
			{
				while(miSchemaInicial[x].indexOf("\n")!=-1)
				{
					miSchemaInicial[x] = miSchemaInicial[x].replace("\n"," ");
				}

				while(miSchemaInicial[x].indexOf("\r")!=-1)
				{
					miSchemaInicial[x] = miSchemaInicial[x].replace("\r"," ");
				}

				while(miSchemaInicial[x].indexOf("  ")!=-1)
				{
					miSchemaInicial[x] = miSchemaInicial[x].replace("  "," ");
				}

				if((miSchemaInicial[x].toLowerCase().indexOf("sat.gob.mx")!=-1 || miSchemaInicial[x].toLowerCase().indexOf("www.w3.org")!=-1) && miSchemaInicial[x].toLowerCase().indexOf("addenda")==-1 && miSchemaInicial[x].toLowerCase().indexOf("ftp://")==-1)
				{
					s ++;
				}
			}
			String [] miSchema = new String[s];
			s =0;
			for(int x=0; x<miSchemaInicial.length; x++)
			{
				if((miSchemaInicial[x].toLowerCase().indexOf("sat.gob.mx")!=-1 || miSchemaInicial[x].toLowerCase().indexOf("www.w3.org")!=-1) && miSchemaInicial[x].toLowerCase().indexOf("addenda")==-1 && miSchemaInicial[x].toLowerCase().indexOf("ftp://")==-1)
				{
					miSchema[s] = miSchemaInicial[x];
					s ++;
				}
			}

			Source sources [] = new Source[miSchema.length];
			for(int x=0; x<miSchema.length; x++)
			{	
				sources[x] = new StreamSource(miSchema[x]);
			}

			SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Schema schema = schemaFactory.newSchema(sources);

			Validator validator = schema.newValidator();
			validator.validate(new StreamSource(new StringReader(miXml)));
		}
		catch (Exception e)
		{
			r = e.getLocalizedMessage();
		}
		return r;
	}

	 /**
	 * Este m&eacute;todo sirve para validar la estructura de Cfdiv32
	 * @param miXml -> String con el xml a validar
	 * @param dir -> String con el directorio de los resources
	 * @return Regresa un String, si su valor es "ok" es correcto, de lo contrario van los mensajes de error
	 */
	public String verificaEstructuraXml(String miXml,String dir)
	{
		String r = "ok";
		try
		{
			String [] miSchema = obtenerSchemasEnResources(dir);

			Source sources [] = new Source[miSchema.length];
			for(int x=0; x<miSchema.length; x++)
			{	
				sources[x] = new StreamSource(miSchema[x]);
			}

			SchemaFactory schemaFactory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Schema schema = schemaFactory.newSchema(sources);

			Validator validator = schema.newValidator();
			validator.validate(new StreamSource(new StringReader(miXml)));
		}
		catch (Exception e)
		{
			r = e.getLocalizedMessage() + " | " + e.toString();
		}
		return r;
	}

	
	 /**
	 * Este m&eacute;todo sirve para obtener los schemas incluidos en la carpeta de resources
	 * @param dir -> ruta donde se encuentran los archivos xsd
	 * @return Regresa un arreglo con todos los schemas
	 */
	public String [] obtenerSchemasEnResources(String dir)
	{
		String [] schema = new String[29];
		schema[0] = dir + "aerolineas.xsd";
		schema[1] = dir + "certificadodedestruccion.xsd";
		schema[2] = dir + "cfdiregistrofiscal.xsd";
		schema[3] = dir + "cfdv32.xsd";
		schema[4] = dir + "consumodecombustibles.xsd";
		schema[5] = dir + "detallista.xsd";
		schema[6] = dir + "divisas.xsd";
		schema[7] = dir + "donat11.xsd";
		schema[8] = dir + "ecb.xsd";
		schema[9] = dir + "ecc.xsd";
		schema[10] = dir + "iedu.xsd";
		schema[11] = dir + "implocal.xsd";
		schema[12] = dir + "leyendasFisc.xsd";
		schema[13] = dir + "nomina11.xsd";
		schema[14] = dir + "notariospublicos.xsd";
		schema[15] = dir + "obrasarteantiguedades.xsd";
		schema[16] = dir + "pagoenespecie.xsd";
		schema[17] = dir + "pfic.xsd";
		schema[18] = dir + "psgecfd.xsd";
		schema[19] = dir + "renovacionysustitucionvehiculos.xsd";
		schema[20] = dir + "servicioparcialconstruccion.xsd";
		schema[21] = dir + "spei.xsd";
		schema[22] = dir + "terceros11.xsd";
		schema[23] = dir + "TimbreFiscalDigital.xsd";
		schema[24] = dir + "TuristaPasajeroExtranjero.xsd";
		schema[25] = dir + "valesdedespensa.xsd";
		schema[26] = dir + "vehiculousado.xsd";
		schema[27] = dir + "ventavehiculos11.xsd";
		schema[28] = dir + "AcreditamientoIEPS10.xsd";
		
		return schema;
	}


	 /**
	 * Este m&eacute;todo sirve para obtener los schemas incluidos en el Cfdiv32
	 * @param miXml -> String con el xml a validar
	 * @return Regresa un arreglo con todos los schemas
	 */
	public String [] obtenerSchemas(String xml)
	{
		String [] schema = null;
		try
		{
			String cadena = "xsi:schemaLocation=\"";
			String datos = "";
			String [] tmp = null;
			List<String> miSchema = new ArrayList<String>();
			while(xml.indexOf(cadena)!=-1)
			{
				if(xml.indexOf(cadena)!=-1)
				{
					datos = xml.substring(xml.indexOf(cadena)+cadena.length(),xml.length());
					datos = datos.substring(0,datos.indexOf("\""));
					if(datos.indexOf(" ")!=-1)
					{
						tmp = datos.split(" ");
						if(tmp!=null)
						{
							for(int x=0; x<tmp.length; x++)
							{
								if(tmp[x].toLowerCase().indexOf(".xsd")!=-1)
								{
									miSchema.add(tmp[x]);
								}
							}
						}
					}
					xml = xml.substring(xml.indexOf(cadena)+cadena.length(),xml.length());
				}
			}
			schema = new String[miSchema.size()];
			miSchema.toArray(schema);
		}
		catch(Exception e)
		{
			System.out.println("Error al buscar todos los xsd: " + e.toString());
		}
		return schema;
	}

}
