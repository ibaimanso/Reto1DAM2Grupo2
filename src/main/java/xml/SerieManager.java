package xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import entities.Serie;
import xml.exceptions.XMLException;

public class SerieManager implements ManagerInterfaceXML<Serie>{

    private static final String       NAME        = "series.xml" ;
    private static final String       ENTITY_NAME = "serie";
    private static       SerieManager instance    = null;
	private              String       filePath    = null;
	
	public SerieManager() throws XMLException {
		this.filePath = ManagerFactoryXML.getInstance().getPath() + NAME;
		
		if (!new File(this.filePath).exists()) {
			ManagerFactoryXML.getInstance().createDocument(this.filePath, ENTITY_NAME+"s");
		}
		
	}
	
	public static SerieManager getInstance() throws XMLException{
		if (null == instance) {
			instance = new SerieManager();
		}
		return instance;
	}
	
	private Serie fromElementToEntity(Element serieElement) {
		return new Serie(
			Integer.parseInt(serieElement.getAttribute("id")),
			Integer.parseInt(serieElement.getAttribute("exerciseId")),
			Integer.parseInt(serieElement.getAttribute("expectedTime")),
			Integer.parseInt(serieElement.getAttribute("restTime")),
			Integer.parseInt(serieElement.getAttribute("repetitions")),
			serieElement.getAttribute("name"),
			serieElement.getAttribute("iconPath")
		);
	}
	
	@Override
	public List<Serie> selectAll() throws XMLException {
		List<Serie> ret = new ArrayList<Serie>();
		
		try {
			Document document = ManagerFactoryXML.getInstance().loadDocument(this.filePath);
			document.getDocumentElement().normalize();
			
			NodeList serieNodeList = document.getElementsByTagName(ENTITY_NAME);
			
			for (int i = 0; i < serieNodeList.getLength(); i++) {
				ret.add(fromElementToEntity((Element)serieNodeList.item(i)));
			}
		} catch (Exception ex) {
			throw new XMLException();
		}
		
		return ret;
	}

	@Override
	public void insert(Serie t) throws XMLException {
		try {
			Document document = ManagerFactoryXML.getInstance().loadDocument(this.filePath);
			Element root = document.getDocumentElement();
			
			Element serieElement = document.createElement(ENTITY_NAME);
			serieElement.setAttribute("id", String.valueOf(t.getId()));
			serieElement.setAttribute("exerciseId", String.valueOf(t.getExerciseId()));
			serieElement.setAttribute("expectedTime", String.valueOf(t.getExpectedTime()));
			serieElement.setAttribute("restTime", String.valueOf(t.getRestTime()));
			serieElement.setAttribute("repetitions", String.valueOf(t.getRepetitions()));
			serieElement.setAttribute("name", t.getName());
			serieElement.setAttribute("iconPath", t.getIconPath());
			
			root.appendChild(serieElement);
			
			ManagerFactoryXML.getInstance().saveDocument(this.filePath, document);
		} catch (Exception ex) {
			throw new XMLException(ex.getMessage());
		}
		
	}

	@Override
	public void update(Serie t) throws XMLException {
		Document doc = ManagerFactoryXML.getInstance().loadDocument(this.filePath);
		NodeList series = doc.getElementsByTagName(ENTITY_NAME);
		
		for (int i = 0; i < series.getLength(); i++) {
			Element serie = (Element) series.item(i);
			
			if (Integer.parseInt(serie.getAttribute("id")) != t.getId()) {
				continue;
			}
			serie.setAttribute("id", String.valueOf(t.getId()));
			serie.setAttribute("exerciseId", String.valueOf(t.getExerciseId()));
			serie.setAttribute("expectedTime", String.valueOf(t.getExpectedTime()));
			serie.setAttribute("restTime", String.valueOf(t.getRestTime()));
			serie.setAttribute("repetitions", String.valueOf(t.getRepetitions()));
			serie.setAttribute("name", t.getName());
			serie.setAttribute("iconPath", t.getIconPath());
			break;
		}
		ManagerFactoryXML.getInstance().saveDocument(this.filePath, doc);		
	}

	@Override
	public void delete(Serie t) throws XMLException {
		Document doc = ManagerFactoryXML.getInstance().loadDocument(this.filePath);
		NodeList series = doc.getElementsByTagName(ENTITY_NAME);
		
		for (int i = 0; i < series.getLength(); i++) {
			Element serie = (Element) series.item(i);
			
			if (Integer.parseInt(serie.getAttribute("id")) == t.getId()) {
				serie.getParentNode().removeChild(serie);
				break;
			}
		}

		ManagerFactoryXML.getInstance().saveDocument(this.filePath, doc);		
	}

	
	
}
