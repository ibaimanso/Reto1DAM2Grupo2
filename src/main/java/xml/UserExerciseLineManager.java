package xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import entities.UserExerciseLine;
import xml.exceptions.XMLException;

public class UserExerciseLineManager implements ManagerInterfaceXML<UserExerciseLine>{

    private static final String                  NAME        = "user_exercise_lines.xml" ;
    private static final String                  ENTITY_NAME = "userExerciseLine";
    private static       UserExerciseLineManager instance    = null;
	private              String                  filePath    = null;
	
	public UserExerciseLineManager() throws XMLException {
		this.filePath = ManagerFactoryXML.getInstance().getPath() + NAME;
		
		if (!new File(this.filePath).exists()) {
			ManagerFactoryXML.getInstance().createDocument(this.filePath, ENTITY_NAME+"s");
		}
		
	}
	
	public static UserExerciseLineManager getInstance() throws XMLException{
		if (null == instance) {
			instance = new UserExerciseLineManager();
		}
		return instance;
	}
	
	private UserExerciseLine fromElementToEntity(Element entityElement) {
		return new UserExerciseLine(
			Integer.parseInt(entityElement.getAttribute("userId")),
			Integer.parseInt(entityElement.getAttribute("exerciseId"))
		);
	}
	
	private boolean exists(UserExerciseLine uelToCheck) throws XMLException{
		List<UserExerciseLine> uels = selectAll();
		
		for (UserExerciseLine uel : uels) {
			if (uel.equals(uelToCheck)) {
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public List<UserExerciseLine> selectAll() throws XMLException {
		List<UserExerciseLine> ret = new ArrayList<UserExerciseLine>();
		
		try {
			Document document = ManagerFactoryXML.getInstance().loadDocument(this.filePath);
			document.getDocumentElement().normalize();
			
			NodeList userExerciseLineNodeList = document.getElementsByTagName(ENTITY_NAME);
			
			for (int i = 0; i < userExerciseLineNodeList.getLength(); i++) {
				ret.add(fromElementToEntity((Element)userExerciseLineNodeList.item(i)));
			}
		} catch (Exception ex) {
			throw new XMLException();
		}
		
		return ret;
	}

	@Override
	public void insert(UserExerciseLine t) throws XMLException {
		
		if (exists(t)) {
			return;
		}
		
		try {
			Document document = ManagerFactoryXML.getInstance().loadDocument(this.filePath);
			Element root = document.getDocumentElement();
			
			Element userExerciseLineElement = document.createElement(ENTITY_NAME);
			userExerciseLineElement.setAttribute("userId", String.valueOf(t.getUserId()));
			userExerciseLineElement.setAttribute("exerciseId", String.valueOf(t.getExerciseId()));
			
			root.appendChild(userExerciseLineElement);
			
			ManagerFactoryXML.getInstance().saveDocument(this.filePath, document);
		} catch (Exception ex) {
			throw new XMLException(ex.getMessage());
		}		
	}

	@Override
	public void update(UserExerciseLine t) throws XMLException {
		
	}

	@Override
	public void delete(UserExerciseLine t) throws XMLException {
		Document doc = ManagerFactoryXML.getInstance().loadDocument(this.filePath);
		NodeList users = doc.getElementsByTagName(ENTITY_NAME);
		
		for (int i = 0; i < users.getLength(); i++) {
			Element userExerciseLine = (Element) users.item(i);
			
			if (
				Integer.parseInt(userExerciseLine.getAttribute("userId")) != t.getUserId() &&
				Integer.parseInt(userExerciseLine.getAttribute("exerciseId")) != t.getExerciseId()
			) {
				userExerciseLine.getParentNode().removeChild(userExerciseLine);
				break;
			}
		}

		ManagerFactoryXML.getInstance().saveDocument(this.filePath, doc);
	}

}
