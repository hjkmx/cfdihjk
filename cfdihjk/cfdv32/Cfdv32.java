package cfdihjk.cfdv32;

import java.util.*;
import java.math.BigDecimal;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * <h1>Clase para generar CFDI Versi&oacute;n 3.2</h1>
 * Esta clase contiene m&eacute;todos que simplifican el llenado de un objeto tipo CFDI
 *
 * @author Antonio Cervantes
 * @version 1.0
 * @since Junio 25, 2015
 *
 */

public class Cfdv32 extends Comprobante
{
	public UtilidadesCfdv32 utils;

	/**
	 * Este es el constructor de la clase Cfdv32
	 * por default pone la version como 3.2
	 * por default asigna la fecha y hora del cfdi al momento de creaci&oacute;n del objeto
	 */
	public Cfdv32()
	{
		utils = new UtilidadesCfdv32();
		setVersion("3.2");
		setFecha(utils.obtenerFecha());
	}

	/**
	 * Este m&eacute;todo sirve para generar la informaci&oacute;n del nodo Emisor
	 * @param nombre del emisor
	 * @param rfc del emisor
	 * @param domicilioFiscal del emisor arreglo -> calle,numero exterior,numero interior,colonia,municipio,estado,localidad,pais,codigo postal,referencia
	 * @param expedidoEn del emisor arreglo -> calle,numero exterior,numero interior,colonia,municipio,estado,localidad,pais,codigo postal,referencia
	 * @param regimenFiscal arreglo -> con los regimenes fiscales del contribuyente
	 * @return Regresa un objeto Comprobante.Emisor
	 */
	public Comprobante.Emisor setDatosEmisor(String nombre,String rfc,String[] domicilioFiscal,String [] expedidoEn,String [] regimenFiscal)
	{
		Comprobante.Emisor emisor = new Comprobante.Emisor();
		emisor.setNombre(nombre);
		emisor.setRfc(rfc);

		if(domicilioFiscal!=null)
		{
			boolean agregaUbicacionFiscal = false;
			TUbicacionFiscal ubicacionFiscal = new TUbicacionFiscal();
			if(!domicilioFiscal[0].trim().equals(""))
			{
				ubicacionFiscal.setCalle(domicilioFiscal[0]);
				agregaUbicacionFiscal = true;
			}

			if(!domicilioFiscal[1].trim().equals(""))
			{
				ubicacionFiscal.setNoExterior(domicilioFiscal[1]);
				agregaUbicacionFiscal = true;
			}

			if(!domicilioFiscal[2].trim().equals(""))
			{
				ubicacionFiscal.setNoInterior(domicilioFiscal[2]);
				agregaUbicacionFiscal = true;
			}

			if(!domicilioFiscal[3].trim().equals(""))
			{
				ubicacionFiscal.setColonia(domicilioFiscal[3]);
				agregaUbicacionFiscal = true;
			}

			if(!domicilioFiscal[4].trim().equals(""))
			{
				ubicacionFiscal.setMunicipio(domicilioFiscal[4]);
				agregaUbicacionFiscal = true;
			}

			if(!domicilioFiscal[5].trim().equals(""))
			{
				ubicacionFiscal.setEstado(domicilioFiscal[5]);
				agregaUbicacionFiscal = true;
			}

			if(!domicilioFiscal[6].trim().equals(""))
			{
				ubicacionFiscal.setLocalidad(domicilioFiscal[6]);
				agregaUbicacionFiscal = true;
			}

			if(!domicilioFiscal[7].trim().equals(""))
			{
				ubicacionFiscal.setPais(domicilioFiscal[7]);
				agregaUbicacionFiscal = true;
			}

			if(!domicilioFiscal[8].trim().equals(""))
			{
				ubicacionFiscal.setCodigoPostal(domicilioFiscal[8]);
				agregaUbicacionFiscal = true;
			}

			if(!domicilioFiscal[9].trim().equals(""))
			{
				ubicacionFiscal.setReferencia(domicilioFiscal[9]);
				agregaUbicacionFiscal = true;
			}

			if(agregaUbicacionFiscal)
			{
				emisor.setDomicilioFiscal(ubicacionFiscal);
			}
		}

		if(expedidoEn!=null)
		{
			boolean agregaExpedidoEn = false;
			TUbicacion ubicacion = new TUbicacion();
			if(!expedidoEn[0].trim().equals(""))
			{
				ubicacion.setCalle(expedidoEn[0]);
				agregaExpedidoEn = true;
			}

			if(!expedidoEn[1].trim().equals(""))
			{
				ubicacion.setNoExterior(expedidoEn[1]);
				agregaExpedidoEn = true;
			}

			if(!expedidoEn[2].trim().equals(""))
			{
				ubicacion.setNoInterior(expedidoEn[2]);
				agregaExpedidoEn = true;
			}

			if(!expedidoEn[3].trim().equals(""))
			{
				ubicacion.setColonia(expedidoEn[3]);
				agregaExpedidoEn = true;
			}

			if(!expedidoEn[4].trim().equals(""))
			{
				ubicacion.setMunicipio(expedidoEn[4]);
				agregaExpedidoEn = true;
			}

			if(!expedidoEn[5].trim().equals(""))
			{
				ubicacion.setEstado(expedidoEn[5]);
				agregaExpedidoEn = true;
			}

			if(!expedidoEn[6].trim().equals(""))
			{
				ubicacion.setLocalidad(expedidoEn[6]);
				agregaExpedidoEn = true;
			}

			if(!expedidoEn[7].trim().equals(""))
			{
				ubicacion.setPais(expedidoEn[7]);
				agregaExpedidoEn = true;
			}

			if(!expedidoEn[8].trim().equals(""))
			{
				ubicacion.setCodigoPostal(expedidoEn[8]);
				agregaExpedidoEn = true;
			}

			if(!expedidoEn[9].trim().equals(""))
			{
				ubicacion.setReferencia(expedidoEn[9]);
				agregaExpedidoEn = true;
			}

			if(agregaExpedidoEn)
			{
				emisor.setExpedidoEn(ubicacion);
			}
		}

		if(regimenFiscal!=null)
		{
			Comprobante.Emisor.RegimenFiscal regimen = new Comprobante.Emisor.RegimenFiscal();
			List<Comprobante.Emisor.RegimenFiscal> lista = emisor.getRegimenFiscal();
			for(int x=0; x<regimenFiscal.length; x++)
			{
				regimen.setRegimen(regimenFiscal[x]);
				lista.add(regimen);
			}
		}

		return emisor;
	}

	/**
	 * Este m&eacute;todo sirve para generar la informaci&oacute;n del nodo Receptor
	 * @param nombre del receptor
	 * @param rfc del receptor
	 * @param domicilio del receptor arreglo -> calle,numero exterior,numero interior,colonia,municipio,estado,localidad,pais,codigo postal,referencia
	 * @return Regresa un objeto Comprobante.Receptor
	 */
	public Comprobante.Receptor setDatosReceptor(String nombre,String rfc,String[] domicilio)
	{
		Comprobante.Receptor receptor = new Comprobante.Receptor();
		receptor.setNombre(nombre);
		receptor.setRfc(rfc);

		if(domicilio!=null)
		{
			boolean agregaDomicilio = false;
			TUbicacion ubicacion = new TUbicacion();
			if(!domicilio[0].trim().equals(""))
			{
				ubicacion.setCalle(domicilio[0]);
				agregaDomicilio = true;
			}

			if(!domicilio[1].trim().equals(""))
			{
				ubicacion.setNoExterior(domicilio[1]);
				agregaDomicilio = true;
			}

			if(!domicilio[2].trim().equals(""))
			{
				ubicacion.setNoInterior(domicilio[2]);
				agregaDomicilio = true;
			}

			if(!domicilio[3].trim().equals(""))
			{
				ubicacion.setColonia(domicilio[3]);
				agregaDomicilio = true;
			}

			if(!domicilio[4].trim().equals(""))
			{
				ubicacion.setMunicipio(domicilio[4]);
				agregaDomicilio = true;
			}

			if(!domicilio[5].trim().equals(""))
			{
				ubicacion.setEstado(domicilio[5]);
				agregaDomicilio = true;
			}

			if(!domicilio[6].trim().equals(""))
			{
				ubicacion.setLocalidad(domicilio[6]);
				agregaDomicilio = true;
			}

			if(!domicilio[7].trim().equals(""))
			{
				ubicacion.setPais(domicilio[7]);
				agregaDomicilio = true;
			}

			if(!domicilio[8].trim().equals(""))
			{
				ubicacion.setCodigoPostal(domicilio[8]);
				agregaDomicilio = true;
			}

			if(!domicilio[9].trim().equals(""))
			{
				ubicacion.setReferencia(domicilio[9]);
				agregaDomicilio = true;
			}

			if(agregaDomicilio)
			{
				receptor.setDomicilio(ubicacion);
			}
		}

		return receptor;
	}

	/**
	 * Este m&eacute;todo sirve para generar la informaci&oacute;n de concepto
	 * @param descripcion
	 * @param unidad
	 * @param cantidad
	 * @param valorUnitario 
	 * @param importe
	 * @param noIdentificacion -> en caso de no utilizarlo mandarlo como null
	 * @param cuentaPredial -> en caso de no utilizarlo mandarlo como null
	 * @param nombreAduana -> es un arreglo, en caso de no utilizarlo mandarlo como null
	 * @param fechaAduana -> es un arreglo, en caso de no utilizarlo mandarlo como null
	 * @param numeroAduana -> es un arreglo, en caso de no utilizarlo mandarlo como null
	 * @return Regresa un objeto Comprobante.Conceptos.Concepto
	 */
	public Comprobante.Conceptos.Concepto setConcepto(String descripcion,String unidad,BigDecimal cantidad,BigDecimal valorUnitario,BigDecimal importe,String noIdentificacion,String cuentaPredial,String[] nombreAduana,XMLGregorianCalendar[] fechaAduana,String[] numeroAduana)
	{
		Comprobante.Conceptos.Concepto concepto = new Comprobante.Conceptos.Concepto();
		concepto.setDescripcion(descripcion);
		concepto.setUnidad(unidad);
		concepto.setCantidad(cantidad);
		concepto.setValorUnitario(valorUnitario);
		concepto.setImporte(importe);
		if(noIdentificacion!=null)
		{
			concepto.setNoIdentificacion(noIdentificacion);
		}

		//Datos de la cuenta Predial
		if(cuentaPredial!=null)
		{
			Comprobante.Conceptos.Concepto.CuentaPredial cp = new Comprobante.Conceptos.Concepto.CuentaPredial();
			cp.setNumero(cuentaPredial);
			concepto.setCuentaPredial(cp);
		}

		//Datos de la informacion aduanera
		if(numeroAduana!=null && fechaAduana!=null)
		{
			if(numeroAduana.length==fechaAduana.length)
			{
				for(int x=0; x<numeroAduana.length; x++)
				{
					List<TInformacionAduanera> listaAduana = concepto.getInformacionAduanera();
					TInformacionAduanera aduana = new TInformacionAduanera();
					aduana.setFecha(fechaAduana[x]);
					aduana.setNumero(numeroAduana[x]);
					if(nombreAduana!=null)
					{
						if(!nombreAduana[x].trim().equals(""))
						{
							aduana.setAduana(nombreAduana[x]);
						}
					}
					listaAduana.add(aduana);
				}
			}
		}

		return concepto;
	}

	/**
	 * Este m&eacute;todo sirve para generar la informaci&oacute;n de Parte para concepto
	 * @param descripcion
	 * @param unidad -> en caso de no utilizarlo mandarlo como null
	 * @param cantidad
	 * @param valorUnitario -> en caso de no utilizarlo mandarlo como null
	 * @param importe -> en caso de no utilizarlo mandarlo como null
	 * @param noIdentificacion -> en caso de no utilizarlo mandarlo como null
	 * @param nombreAduana -> es un arreglo, en caso de no utilizarlo mandarlo como null
	 * @param fechaAduana -> es un arreglo, en caso de no utilizarlo mandarlo como null
	 * @param numeroAduana -> es un arreglo, en caso de no utilizarlo mandarlo como null
	 * @return Regresa un objeto Comprobante.Conceptos.Concepto
	 */
	public Comprobante.Conceptos.Concepto.Parte setParte(String descripcion,String unidad,BigDecimal cantidad,BigDecimal valorUnitario,BigDecimal importe,String noIdentificacion,String[] nombreAduana,XMLGregorianCalendar[] fechaAduana,String[] numeroAduana)
	{
		Comprobante.Conceptos.Concepto.Parte parte = new Comprobante.Conceptos.Concepto.Parte();
		parte.setDescripcion(descripcion);
		if(unidad!=null)
		{
			parte.setUnidad(unidad);
		}
		parte.setCantidad(cantidad);
		if(valorUnitario!=null)
		{
			parte.setValorUnitario(valorUnitario);
		}
		if(importe!=null)
		{
			parte.setImporte(importe);
		}
		if(noIdentificacion!=null)
		{
			parte.setNoIdentificacion(noIdentificacion);
		}

		//Datos de la informacion aduanera
		if(numeroAduana!=null && fechaAduana!=null)
		{
			if(numeroAduana.length==fechaAduana.length)
			{
				for(int x=0; x<numeroAduana.length; x++)
				{
					List<TInformacionAduanera> listaAduana = parte.getInformacionAduanera();
					TInformacionAduanera aduana = new TInformacionAduanera();
					aduana.setFecha(fechaAduana[x]);
					aduana.setNumero(numeroAduana[x]);
					if(nombreAduana!=null)
					{
						if(!nombreAduana[x].trim().equals(""))
						{
							aduana.setAduana(nombreAduana[x]);
						}
					}
					listaAduana.add(aduana);
				}
			}
		}

		return parte;
	}

	/**
	 * Este m&eacute;todo sirve para generar la informaci&oacute;n de Impuestos Trasladados
	 * @param impuesto
	 * @param tasa
	 * @param importe
	 * @return Regresa un objeto Comprobante.Impuestos.Traslados.Traslado
	 */
	public Comprobante.Impuestos.Traslados.Traslado setTraslado(String impuesto,BigDecimal tasa,BigDecimal importe)
	{
		Comprobante.Impuestos.Traslados.Traslado traslado = new Comprobante.Impuestos.Traslados.Traslado();

		traslado.setImpuesto(impuesto);
		traslado.setTasa(tasa);
		traslado.setImporte(importe);

		return traslado;
	}

	/**
	 * Este m&eacute;todo sirve para generar la informaci&oacute;n de Impuestos Retenidos
	 * @param impuesto
	 * @param importe
	 * @return Regresa un objeto Comprobante.Impuestos.Retenciones.Retencion
	 */
	public Comprobante.Impuestos.Retenciones.Retencion setRetencion(String impuesto,BigDecimal importe)
	{
		Comprobante.Impuestos.Retenciones.Retencion retencion = new Comprobante.Impuestos.Retenciones.Retencion();

		retencion.setImpuesto(impuesto);
		retencion.setImporte(importe);

		return retencion;
	}
}
