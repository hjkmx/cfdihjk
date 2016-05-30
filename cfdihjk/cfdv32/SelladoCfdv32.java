package cfdihjk.cfdv32;

import java.io.*;
import java.security.*;
import javax.crypto.Cipher;
import org.apache.commons.codec.binary.Base64;
import java.security.spec.*;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.spec.PKCS8EncodedKeySpec;
import org.apache.commons.ssl.PKCS8Key;


/**
 * <h1>Clase para generar sellar y verificar el sello de un CFDI</h1>
 * 
 * @autor Antonio Cervantes
 * @version 1.0
 * @since Junio 29, 2015
 *
 */

public class SelladoCfdv32
{
	/**
	 * Este m&eacute;todo sirve para sellar la cadena original de un CFD
	 * @param claveLlavePrivada privada el CSD
	 * @param llavePrivada -> String con la ruta en disco de la ubicacion de la llave privada del CFDI
	 * @param cadenaOriginal -> cadena original del CFDI a sellar
	 * @return Regresa un String con el sello del CFDI codificado en base64
	 */
	public String firmaCfd(String claveLlavePrivada,byte[] llavePrivadaArchivo,String cadenaOriginal)
	{
		String selloBase64 = "";
		try
		{
			//Cargo la llave privada
			PKCS8Key llavePrivadaPKCS8 = new PKCS8Key(new ByteArrayInputStream(llavePrivadaArchivo),claveLlavePrivada.toCharArray());
			byte [] llavePrivadaByte = llavePrivadaPKCS8.getDecryptedBytes();
			PKCS8EncodedKeySpec llavePrivadaKeySpec = new PKCS8EncodedKeySpec(llavePrivadaByte);
			KeyFactory fabricaDeLlaves = KeyFactory.getInstance("RSA");
			PrivateKey llavePrivada = fabricaDeLlaves.generatePrivate(llavePrivadaKeySpec);

			//Creo el objeto firma
			Signature firma = Signature.getInstance("SHA1withRSA");
			firma.initSign(llavePrivada);
			firma.update(cadenaOriginal.getBytes("UTF-8"));
			byte [] sello = firma.sign();

			//Muestro el sello en base64
			selloBase64 = new String(Base64.encodeBase64(sello));
		}
		catch(Exception e)
		{
			System.out.println("Error al firmar Cfd ... " + e.toString());
			selloBase64 = null;
		}
		return selloBase64;
	}
}
