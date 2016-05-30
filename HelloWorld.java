import java.io.*;
import java.util.*;
import java.math.BigDecimal;
import javax.xml.datatype.XMLGregorianCalendar;
import cfdihjk.cfdv32.*;
import org.apache.commons.io.FileUtils.*;

public class HelloWorld
{
	public static void main(String[] args)
	{
		System.out.println("Hello World!");
		Cfdv32 cfdi = new Cfdv32();
		cfdi.setSerie("F");
		cfdi.setFolio("1234");
		cfdi.setFormaDePago("forma de pago");
		cfdi.setNoCertificado("00001000000202525118");
		try
		{
			cfdi.setCertificado(cfdi.utils.encodeBase64ByteArray(org.apache.commons.io.FileUtils.readFileToByteArray(new File("cfdihjk/resources/certificados_prueba/aaa010101aaa_csd_01.cer"))));
		}
		catch(Exception e)
		{
			System.out.println("Error al obtener el certificado: " + e.toString());
		}

		cfdi.setCondicionesDePago("condiciones de pago");

		BigDecimal subTotal = cfdi.utils.formatoBigDecimal(new BigDecimal(1.0d/3.0d));
		cfdi.setSubTotal(subTotal);

		BigDecimal descuento = cfdi.utils.formatoBigDecimal(new BigDecimal(12.0d/145.0d));
		cfdi.setDescuento(descuento);

		cfdi.setMotivoDescuento("motivo descuento");

		BigDecimal tipoCambio = cfdi.utils.formatoBigDecimal(new BigDecimal(11234.0d/13124231.0d));
		cfdi.setTipoCambio(tipoCambio.toString());
		cfdi.setMoneda("moneda");

		BigDecimal total = cfdi.utils.formatoBigDecimal(new BigDecimal(11234.0d/13124231.0d));
		cfdi.setTotal(total);

		cfdi.setTipoDeComprobante("ingreso");
		cfdi.setMetodoDePago("metodo de pago");

		cfdi.setLugarExpedicion("México DF");
		cfdi.setNumCtaPago("num cta pago");

		cfdi.setFolioFiscalOrig("folio fiscal original");
		cfdi.setSerieFolioFiscalOrig("serie folio fiscal original");
		cfdi.setFechaFolioFiscalOrig(cfdi.utils.obtenerFecha());
		BigDecimal montoFolioFiscalOrig = cfdi.utils.formatoBigDecimal(new BigDecimal(3.0d/11.0d));
		cfdi.setMontoFolioFiscalOrig(montoFolioFiscalOrig);

		///////////////////////////////////////////////////////
		//Datos del nodo emisor
		//////////////////////////////////////////////////////
		String [] domicilioFiscal = {"calle","noExterior","noInterior","colonia","municipio","estado","localidad","pais","12345","referencia"};
		String [] expedidoEn = {"calle","noExterior","noInterior","colonia","municipio","estado","localidad","pais","12345","referencia"};
		String [] regimenFiscal = {"Regimen General de Ley Personas Morales"};
		cfdi.setEmisor(cfdi.setDatosEmisor("LA EMPRESA PATO SA DE CV","XAXX010101000",domicilioFiscal,expedidoEn,regimenFiscal));

		///////////////////////////////////////////////////////
		//Datos del nodo receptor
		//////////////////////////////////////////////////////
		String [] domicilio = {"calle","noExterior","","colonia","municipio","estado","","pais","12345","referencia"};
		cfdi.setReceptor(cfdi.setDatosReceptor("ACME SC","XAXX010101000",domicilio));

		//////////////////////////////////////////////////////
		//Datos del nodo conceptos
		//////////////////////////////////////////////////////
		Comprobante.Conceptos conceptos = new Comprobante.Conceptos();

		//Agrego el concepto a conceptos
		String [] nombreAduana = { "nombre aduana a","nombre aduana b" };
		XMLGregorianCalendar [] fechaAduana = { cfdi.utils.generarFecha("2014-12-31"), cfdi.utils.generarFecha("2015-02-01")};
		String [] numeroAduana = { "numero aduana a", "numero aduana b" };
		List<Comprobante.Conceptos.Concepto> listaConceptos = conceptos.getConcepto();

		listaConceptos.add
		(
			cfdi.setConcepto(
				"descripcion 1",
				"unidad 1",
				cfdi.utils.formatoBigDecimal(new BigDecimal(0.5d)),
				cfdi.utils.formatoBigDecimal(new BigDecimal(10.0d)),
				cfdi.utils.formatoBigDecimal(new BigDecimal(5.0d)),
				"no Identificacion 1",
				"0349328432",
				null,
				null,
				null
			)
		);

		listaConceptos.add
		(
			cfdi.setConcepto(
				"descripcion 2",
				"unidad 2",
				cfdi.utils.formatoBigDecimal(new BigDecimal(1.5d)),
				cfdi.utils.formatoBigDecimal(new BigDecimal(5.0d)),
				cfdi.utils.formatoBigDecimal(new BigDecimal(7.5d)),
				null,
				null,
				null,
				null,
				null
			)
		);

		listaConceptos.add
		(
			cfdi.setConcepto(
				"descripcion 3",
				"unidad 3",
				cfdi.utils.formatoBigDecimal(new BigDecimal(1.0d)),
				cfdi.utils.formatoBigDecimal(new BigDecimal(1.0d)),
				cfdi.utils.formatoBigDecimal(new BigDecimal(1.0d)),
				null,
				null,
				null,
				fechaAduana,
				numeroAduana
			)
		);

		Comprobante.Conceptos.Concepto concepto = new Comprobante.Conceptos.Concepto();
		concepto = cfdi.setConcepto(
			"descripcion 4",
			"unidad 4",
			cfdi.utils.formatoBigDecimal(new BigDecimal(2.0d)),
			cfdi.utils.formatoBigDecimal(new BigDecimal(2.0d)),
			cfdi.utils.formatoBigDecimal(new BigDecimal(4.0d)),
			null,
			null,
			null,
			null,
			null
		);
		Comprobante.Conceptos.Concepto.Parte parte = cfdi.setParte(
			"descripcion 4",
			"unidad 4",
			cfdi.utils.formatoBigDecimal(new BigDecimal(3.0/7.0d)),
			cfdi.utils.formatoBigDecimal(new BigDecimal(2.0d)),
			cfdi.utils.formatoBigDecimal(new BigDecimal(4)),
			null,
			null,
			fechaAduana,
			numeroAduana
		);
		List<Comprobante.Conceptos.Concepto.Parte> listaParte = concepto.getParte();
		listaParte.add(parte);
		listaConceptos.add(concepto);

		cfdi.setConceptos(conceptos);

		//////////////////////////////////////////////////////
		//Datos del nodo impuestos
		//////////////////////////////////////////////////////
		Comprobante.Impuestos impuestos = new Comprobante.Impuestos();

		//Impuestos trasladados
		Comprobante.Impuestos.Traslados traslados = new Comprobante.Impuestos.Traslados();
		Comprobante.Impuestos.Traslados.Traslado traslado1 = cfdi.setTraslado("IVA",cfdi.utils.formatoBigDecimal(new BigDecimal(16.0d)),cfdi.utils.formatoBigDecimal(new BigDecimal(0.16d)));
		Comprobante.Impuestos.Traslados.Traslado traslado2 = cfdi.setTraslado("IVA",cfdi.utils.formatoBigDecimal(new BigDecimal(11.0d)),cfdi.utils.formatoBigDecimal(new BigDecimal(0.11d)));

		//List<Comprobante.Impuestos.Traslados.Traslado> listaTraslados = traslados.getTraslado();
		traslados.getTraslado().add(traslado1);
		traslados.getTraslado().add(traslado2);

		impuestos.setTraslados(traslados);
		impuestos.setTotalImpuestosTrasladados(cfdi.utils.formatoBigDecimal(new BigDecimal(0.27d)));

		//Impuestos retenidos
		Comprobante.Impuestos.Retenciones retenciones = new Comprobante.Impuestos.Retenciones();
		Comprobante.Impuestos.Retenciones.Retencion retencion1 = cfdi.setRetencion("IVA",cfdi.utils.formatoBigDecimal(new BigDecimal(0.27d)));
		Comprobante.Impuestos.Retenciones.Retencion retencion2 = cfdi.setRetencion("ISR",cfdi.utils.formatoBigDecimal(new BigDecimal(0.37d)));

		retenciones.getRetencion().add(retencion1);
		retenciones.getRetencion().add(retencion2);

		impuestos.setRetenciones(retenciones);
		impuestos.setTotalImpuestosRetenidos(cfdi.utils.formatoBigDecimal(new BigDecimal(0.64d)));

		//Guardo el dato de los impuestos
		cfdi.setImpuestos(impuestos);

		//Mando a imprimir el xml generado
		Cfdv32Marshaller marshaller = new Cfdv32Marshaller();
		String xml = marshaller.marshal(cfdi);
		System.out.println(xml);

		//Mando a imprimir la cadena original
		CadenaOriginalCfdv32 co = new CadenaOriginalCfdv32();
		String cadenaOriginal = "";
		try
		{
			cadenaOriginal = co.getCadenaOriginal(Comprobante.class,cfdi,"cfdihjk/resources/cadenaoriginal_3_2.xslt");
			System.out.println("CADENA ORIGINAL:");
			System.out.println(cadenaOriginal);
		}
		catch(Exception e)
		{
			System.out.println("Error al generar cadena original: " + e.toString());
		}

		//Mando a sellado el comprobante
		SelladoCfdv32 sellado = new SelladoCfdv32();
		String sello = "";
		try
		{
			sello = sellado.firmaCfd("a0123456789",org.apache.commons.io.FileUtils.readFileToByteArray(new File("cfdihjk/resources/certificados_prueba/aaa010101aaa_csd_01.key")),cadenaOriginal);
			System.out.println("SELLO:");
			System.out.println(sello);
		}
		catch(Exception e)
		{
			System.out.println("Error al generar cadena original: " + e.toString());
		}

		//Guardo el sello en cfdi y genero un archivo xml con el CFDI
		cfdi.setSello(sello);
		xml = marshaller.marshal(cfdi);

		try
		{
			org.apache.commons.io.FileUtils.writeStringToFile(new File("HolaMundo.xml"),xml,"UTF-8");
		}
		catch(Exception e)
		{
			System.out.println("Error al generar xml: " + e.toString());
		}


		//Valido el xml
		VerificaEstructuraCfdv32 ve = new VerificaEstructuraCfdv32();
		String verificacion = ve.verificaEstructuraXml(xml);
		System.out.println("VERIFICACION:");
		System.out.println(verificacion);

	}
}
