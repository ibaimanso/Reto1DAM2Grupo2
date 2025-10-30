package xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


import entities.User;
import xml.exceptions.XMLException;

public class UserManager implements ManagerInterfaceXML<User> {

    private static final String      NAME        = "users.xml" ;
    private static final String      ENTITY_NAME = "user";
    private static       UserManager instance    = null;
	private              String      filePath    = null;
	
	public UserManager() throws XMLException {
		this.filePath = ManagerFactoryXML.getInstance().getPath() + NAME;
		
		if (!new File(this.filePath).exists()) {
			ManagerFactoryXML.getInstance().createDocument(this.filePath, ENTITY_NAME+"s");
		}
		
	}
	
	public static UserManager getInstance() throws XMLException{
		if (null == instance) {
			instance = new UserManager();
		}
		return instance;
	}
	
	private User fromElementToEntity(Element userElement) {
		return new User(
			Integer.parseInt(userElement.getAttribute("id")),
			userElement.getAttribute("fname"),
			userElement.getAttribute("lname"),
			userElement.getAttribute("pw"),
			userElement.getAttribute("email"),
			userElement.getAttribute("birth"),
			userElement.getAttribute("lastMod"),
			Integer.parseInt(userElement.getAttribute("level")),
			userElement.getAttribute("trainer").equals("true") ? true: false
		);
	}
	
	@Override
	public List<User> selectAll() throws XMLException {
		List<User> ret = new ArrayList<User>();
		
		try {
			Document document = ManagerFactoryXML.getInstance().loadDocument(this.filePath);
			document.getDocumentElement().normalize();
			
			NodeList userNodeList = document.getElementsByTagName(ENTITY_NAME);
			
			for (int i = 0; i < userNodeList.getLength(); i++) {
				ret.add(fromElementToEntity((Element)userNodeList.item(i)));
			}
		} catch (Exception ex) {
			throw new XMLException();
		}
		
		return ret;
	}

	@Override
	public void insert(User t) throws XMLException {
		try {
			Document document = ManagerFactoryXML.getInstance().loadDocument(this.filePath);
			Element root = document.getDocumentElement();
			
			Element userElement = document.createElement(ENTITY_NAME);
			userElement.setAttribute("trainer", t.isTrainer() ? "true":"false");
			userElement.setAttribute("id", String.valueOf(t.getId()));
			userElement.setAttribute("fname", t.getFname());
			userElement.setAttribute("lname", t.getLname());
			userElement.setAttribute("pw", t.getPw());
			userElement.setAttribute("email", t.getEmail());
			userElement.setAttribute("birth", t.getBirth());
			userElement.setAttribute("level", String.valueOf(t.getId()));
			userElement.setAttribute("lastMod", t.getLastMod());
			
			root.appendChild(userElement);
			
			ManagerFactoryXML.getInstance().saveDocument(this.filePath, document);
		} catch (Exception ex) {
			throw new XMLException(ex.getMessage());
		}
		
	}

	@Override
	public void update(User t) throws XMLException {
		Document doc = ManagerFactoryXML.getInstance().loadDocument(this.filePath);
		NodeList users = doc.getElementsByTagName(ENTITY_NAME);
		
		for (int i = 0; i < users.getLength(); i++) {
			Element user = (Element) users.item(i);
			
			if (Integer.parseInt(user.getAttribute("id")) != t.getId()) {
				continue;
			}
			user.setAttribute("trainer", t.isTrainer() ? "true":"false");
			user.setAttribute("id", String.valueOf(t.getId()));
			user.setAttribute("fname", t.getFname());
			user.setAttribute("lname", t.getLname());
			user.setAttribute("pw", t.getPw());
			user.setAttribute("email", t.getEmail());
			user.setAttribute("birth", t.getBirth());
			user.setAttribute("level", String.valueOf(t.getId()));
			user.setAttribute("lastMod", t.getLastMod());
			break;
		}
		ManagerFactoryXML.getInstance().saveDocument(this.filePath, doc);
	}

	@Override
	public void delete(User t) throws XMLException {
		Document doc = ManagerFactoryXML.getInstance().loadDocument(this.filePath);
		NodeList users = doc.getElementsByTagName(ENTITY_NAME);
		
		for (int i = 0; i < users.getLength(); i++) {
			Element user = (Element) users.item(i);
			
			if (Integer.parseInt(user.getAttribute("id")) == t.getId()) {
				user.getParentNode().removeChild(user);
				break;
			}
		}

		ManagerFactoryXML.getInstance().saveDocument(this.filePath, doc);		
	}

}
