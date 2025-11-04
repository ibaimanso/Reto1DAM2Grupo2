package xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import entities.UserWorkoutLine;
import xml.exceptions.XMLException;

public class UserWorkoutLineManager implements ManagerInterfaceXML<UserWorkoutLine>{

    private static final String                 NAME        = "user_workout_lines.xml" ;
    private static final String                 ENTITY_NAME = "userWorkoutLine";
    private static       UserWorkoutLineManager instance    = null;
	private              String                 filePath    = null;
	
	public UserWorkoutLineManager() throws XMLException {
		this.filePath = ManagerFactoryXML.getInstance().getPath() + NAME;
		
		if (!new File(this.filePath).exists()) {
			ManagerFactoryXML.getInstance().createDocument(this.filePath, ENTITY_NAME+"s");
		}
		
	}
	
	public static UserWorkoutLineManager getInstance() throws XMLException{
		if (null == instance) {
			instance = new UserWorkoutLineManager();
		}
		return instance;
	}
	
	private UserWorkoutLine fromElementToEntity(Element entityElement) {
		int totalTime = 0;
		if (entityElement.hasAttribute("totalTime")) {
			try {
				totalTime = Integer.parseInt(entityElement.getAttribute("totalTime"));
			} catch (NumberFormatException e) {
				totalTime = 0;
			}
		}
		return new UserWorkoutLine(
			Integer.parseInt(entityElement.getAttribute("userId")),
			Integer.parseInt(entityElement.getAttribute("workoutId")),
			entityElement.getAttribute("doneDate"),
			totalTime
		);
	}
	
	private boolean exists(UserWorkoutLine uwlToCheck) throws XMLException{
		List<UserWorkoutLine> uwls = selectAll();
		
		for (UserWorkoutLine uwl : uwls) {
			if (uwl.equals(uwlToCheck)) {
				return true;
			}
		}
		
		return false;
	}
	
	@Override
	public List<UserWorkoutLine> selectAll() throws XMLException {
		List<UserWorkoutLine> ret = new ArrayList<UserWorkoutLine>();
		
		try {
			Document document = ManagerFactoryXML.getInstance().loadDocument(this.filePath);
			document.getDocumentElement().normalize();
			
			NodeList userWorkoutLineNodeList = document.getElementsByTagName(ENTITY_NAME);
			
			for (int i = 0; i < userWorkoutLineNodeList.getLength(); i++) {
				ret.add(fromElementToEntity((Element)userWorkoutLineNodeList.item(i)));
			}
		} catch (Exception ex) {
			throw new XMLException();
		}
		
		return ret;
	}

	@Override
	public void insert(UserWorkoutLine t) throws XMLException {

		if (exists(t)) {
			return;
		}
		
		try {
			Document document = ManagerFactoryXML.getInstance().loadDocument(this.filePath);
			Element root = document.getDocumentElement();
			
			Element userWorkoutLineElement = document.createElement(ENTITY_NAME);
			userWorkoutLineElement.setAttribute("userId", String.valueOf(t.getUserId()));
			userWorkoutLineElement.setAttribute("workoutId", String.valueOf(t.getWorkoutId()));
			userWorkoutLineElement.setAttribute("doneDate", t.getDoneDate());
			userWorkoutLineElement.setAttribute("totalTime", String.valueOf(t.getTotalTime()));
			
			root.appendChild(userWorkoutLineElement);
			
			ManagerFactoryXML.getInstance().saveDocument(this.filePath, document);
		} catch (Exception ex) {
			throw new XMLException(ex.getMessage());
		}
		
	}

	@Override
	public void update(UserWorkoutLine t) throws XMLException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(UserWorkoutLine t) throws XMLException {
		Document doc = ManagerFactoryXML.getInstance().loadDocument(this.filePath);
		NodeList users = doc.getElementsByTagName(ENTITY_NAME);
		
		for (int i = 0; i < users.getLength(); i++) {
			Element userSerieLine = (Element) users.item(i);
			
			if (
				Integer.parseInt(userSerieLine.getAttribute("userId")) == t.getUserId() &&
				Integer.parseInt(userSerieLine.getAttribute("workoutId")) == t.getWorkoutId()
			) {
				userSerieLine.getParentNode().removeChild(userSerieLine);
				break;
			}
		}

		ManagerFactoryXML.getInstance().saveDocument(this.filePath, doc);		
	}

}