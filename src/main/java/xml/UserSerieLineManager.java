package xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import entities.UserSerieLine;
import xml.exceptions.XMLException;

public class UserSerieLineManager implements ManagerInterfaceXML<UserSerieLine>{

    private static final String                  NAME        = "user_serie_lines.xml" ;
    private static final String                  ENTITY_NAME = "userSerieLine";
    private static       UserSerieLineManager instance    = null;
	private              String                  filePath    = null;
	
	public UserSerieLineManager() throws XMLException {
		this.filePath = ManagerFactoryXML.getInstance().getPath() + NAME;
		
		if (!new File(this.filePath).exists()) {
			ManagerFactoryXML.getInstance().createDocument(this.filePath, ENTITY_NAME+"s");
		}
		
	}
	
	public static UserSerieLineManager getInstance() throws XMLException{
		if (null == instance) {
			instance = new UserSerieLineManager();
		}
		return instance;
	}
	
	private UserSerieLine fromElementToEntity(Element entityElement) {
		return new UserSerieLine(
			Integer.parseInt(entityElement.getAttribute("userId")),
			Integer.parseInt(entityElement.getAttribute("serieId"))
		);
	}
	
	private boolean exists(UserSerieLine uslToCheck) throws XMLException{
		List<UserSerieLine> usls = selectAll();
		
		for (UserSerieLine usl : usls) {
			if (usl.equals(uslToCheck)) {
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public List<UserSerieLine> selectAll() throws XMLException {
		List<UserSerieLine> ret = new ArrayList<UserSerieLine>();
		
		try {
			Document document = ManagerFactoryXML.getInstance().loadDocument(this.filePath);
			document.getDocumentElement().normalize();
			
			NodeList userSerieLineNodeList = document.getElementsByTagName(ENTITY_NAME);
			
			for (int i = 0; i < userSerieLineNodeList.getLength(); i++) {
				ret.add(fromElementToEntity((Element)userSerieLineNodeList.item(i)));
			}
		} catch (Exception ex) {
			throw new XMLException();
		}
		
		return ret;
	}

	@Override
	public void insert(UserSerieLine t) throws XMLException {
		
		if (exists(t)) {
			return;
		}
		
		try {
			Document document = ManagerFactoryXML.getInstance().loadDocument(this.filePath);
			Element root = document.getDocumentElement();
			
			Element userSerieLineElement = document.createElement(ENTITY_NAME);
			userSerieLineElement.setAttribute("userId", String.valueOf(t.getUserId()));
			userSerieLineElement.setAttribute("serieId", String.valueOf(t.getSerieId()));
			
			root.appendChild(userSerieLineElement);
			
			ManagerFactoryXML.getInstance().saveDocument(this.filePath, document);
		} catch (Exception ex) {
			throw new XMLException(ex.getMessage());
		}
		
	}

	@Override
	public void update(UserSerieLine t) throws XMLException {
		
	}

	@Override
	public void delete(UserSerieLine t) throws XMLException {
		Document doc = ManagerFactoryXML.getInstance().loadDocument(this.filePath);
		NodeList users = doc.getElementsByTagName(ENTITY_NAME);
		
		for (int i = 0; i < users.getLength(); i++) {
			Element userSerieLine = (Element) users.item(i);
			
			if (
				Integer.parseInt(userSerieLine.getAttribute("userId")) != t.getUserId() &&
				Integer.parseInt(userSerieLine.getAttribute("serieId")) != t.getSerieId()
			) {
				userSerieLine.getParentNode().removeChild(userSerieLine);
				break;
			}
		}

		ManagerFactoryXML.getInstance().saveDocument(this.filePath, doc);		
	}

}
