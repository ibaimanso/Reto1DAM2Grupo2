package xml;

import java.io.File;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import xml.exceptions.XMLException;

public class ManagerFactoryXML {

	private static ManagerFactoryXML instance = null;
	
	private String PATH = null;
	
	public ManagerFactoryXML() throws XMLException {
		
		this.PATH = "C:/trastero/xml/";
		
		try {
            File path = new File(this.PATH);
            if (!path.exists()) {
            	path.mkdirs(); 
            }
		} catch (Exception ex) {
			throw new XMLException();
		}
	}
	
	public static ManagerFactoryXML getInstance() throws XMLException {
		if (null == instance) {
			instance = new ManagerFactoryXML();
		}
		
		return instance;
	}
	
	public String getPath() {
		return this.PATH;
	}
	
	public void createDocument(String xmlPath, String rootName) throws XMLException{
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			
			Document doc = builder.newDocument();
			
			Element root = doc.createElement(rootName);
			doc.appendChild(root);
			
			this.saveDocument(xmlPath, doc);
		} catch (Exception ex) {
			throw new XMLException(ex.getMessage());
		}
	}
	
	public Document loadDocument(String xmlPath) throws XMLException{
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			return builder.parse(new File(xmlPath));
		} catch (Exception ex) {
			throw new XMLException(ex.getMessage());
		}
	}
	
	public void saveDocument(String xmlPath, Document doc) throws XMLException{
		try {
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.transform(new DOMSource(doc), new StreamResult(new File(xmlPath)));
			
		} catch (Exception ex) {
			throw new XMLException(ex.getMessage());
		}
	}
}
