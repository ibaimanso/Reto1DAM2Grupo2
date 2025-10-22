package xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import entities.Exercise;
import xml.exceptions.XMLException;

public class ExerciseManager implements ManagerInterfaceXML<Exercise>{

    private static final String      NAME         = "exercises.xml" ;
    private static final String      ENTITY_NAME  = "exercise";
    private static       ExerciseManager instance = null;
	private              String      filePath     = null;

	public ExerciseManager() throws XMLException{
		this.filePath = ManagerFactoryXML.getInstance().getPath() + NAME;
		
		if (!new File(this.filePath).exists()) {
			ManagerFactoryXML.getInstance().createDocument(this.filePath, ENTITY_NAME+"s");
		}	}

	public static ExerciseManager getInstance() throws XMLException{
		if (null == instance) {
			instance = new ExerciseManager();
		}
		return instance;
	}

	private Exercise fromElementToEntity(Element exerciseElement) {
	  	return new Exercise(
			Integer.parseInt(exerciseElement.getAttribute("id")),
			Integer.parseInt(exerciseElement.getAttribute("workoutId")),
			exerciseElement.getAttribute("name"),
			exerciseElement.getAttribute("descript")
			);
	}
	
	@Override
	public List<Exercise> selectAll() throws XMLException {
		List<Exercise> ret = new ArrayList<Exercise>();
		
		try {
			Document document = ManagerFactoryXML.getInstance().loadDocument(this.filePath);
			document.getDocumentElement().normalize();
			
			NodeList exerciseNodeList = document.getElementsByTagName(ENTITY_NAME);
			
			for (int i = 0; i < exerciseNodeList.getLength(); i++) {
				ret.add(fromElementToEntity((Element)exerciseNodeList.item(i)));
			}
		} catch (Exception ex) {
			throw new XMLException();
		}
		return null;
	}

	@Override
	public void insert(Exercise t) throws XMLException {
		try {
			Document document = ManagerFactoryXML.getInstance().loadDocument(this.filePath);
			Element root = document.getDocumentElement();
			
			Element exerciseElement = document.createElement(ENTITY_NAME);
			exerciseElement.setAttribute("id", String.valueOf(t.getId()));
			exerciseElement.setAttribute("workoutId", String.valueOf(t.getWorkoutId()));
			exerciseElement.setAttribute("name", t.getName());
			exerciseElement.setAttribute("descript", t.getDescript());
			
			root.appendChild(exerciseElement);
			
			ManagerFactoryXML.getInstance().saveDocument(this.filePath, document);
		} catch (Exception ex) {
			throw new XMLException(ex.getMessage());
		}		
	}

	@Override
	public void update(Exercise t) throws XMLException {
		Document doc = ManagerFactoryXML.getInstance().loadDocument(this.filePath);
		NodeList exercises = doc.getElementsByTagName(ENTITY_NAME);
		
		for (int i = 0; i < exercises.getLength(); i++) {
			Element exercise = (Element) exercises.item(i);
			
			if (Integer.parseInt(exercise.getAttribute("id")) != t.getId()) {
				continue;
			}
			exercise.setAttribute("id", String.valueOf(t.getId()));
			exercise.setAttribute("workoutId", String.valueOf(t.getWorkoutId()));
			exercise.setAttribute("name", t.getName());
			exercise.setAttribute("descript", t.getDescript());
			break;
		}
		ManagerFactoryXML.getInstance().saveDocument(this.filePath, doc);		
	}

	@Override
	public void delete(Exercise t) throws XMLException {
		Document doc = ManagerFactoryXML.getInstance().loadDocument(this.filePath);
		NodeList exercises = doc.getElementsByTagName(ENTITY_NAME);
		
		for (int i = 0; i < exercises.getLength(); i++) {
			Element exercise = (Element) exercises.item(i);
			
			if (Integer.parseInt(exercise.getAttribute("id")) != t.getId()) {
				exercise.getParentNode().removeChild(exercise);
				break;
			}
		}

		ManagerFactoryXML.getInstance().saveDocument(this.filePath, doc);		}
	
}
