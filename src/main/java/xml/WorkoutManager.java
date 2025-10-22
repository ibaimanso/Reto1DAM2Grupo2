package xml;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import entities.Workout;
import xml.exceptions.XMLException;

public class WorkoutManager implements ManagerInterfaceXML<Workout>{

    private static final String         NAME        = "workouts.xml" ;
    private static final String         ENTITY_NAME = "workout";
    private static       WorkoutManager instance    = null;
	private              String         filePath    = null;



	
	public WorkoutManager() throws XMLException{
		this.filePath = ManagerFactoryXML.getInstance().getPath() + NAME;
		
		if (!new File(this.filePath).exists()) {
			ManagerFactoryXML.getInstance().createDocument(this.filePath, ENTITY_NAME+"s");
		}
		
	}
	
	public static WorkoutManager getInstance() throws XMLException{
		if (null == instance) {
			instance = new WorkoutManager();
		}
		return instance;
	}
	
	private Workout fromElementToEntity(Element workoutElement) {
		return new Workout(
			Integer.parseInt(workoutElement.getAttribute("id")),
			workoutElement.getAttribute("name"),
			workoutElement.getAttribute("descript"),
			workoutElement.getAttribute("videoUrl"),
			Integer.parseInt(workoutElement.getAttribute("level"))
		);
	}
	
	@Override
	public List<Workout> selectAll() throws XMLException {
		List<Workout> ret = new ArrayList<Workout>();
		
		try {
			Document document = ManagerFactoryXML.getInstance().loadDocument(this.filePath);
			document.getDocumentElement().normalize();
			
			NodeList workoutNodeList = document.getElementsByTagName(ENTITY_NAME);
			
			for (int i = 0; i < workoutNodeList.getLength(); i++) {
				ret.add(fromElementToEntity((Element)workoutNodeList.item(i)));
			}
		} catch (Exception ex) {
			throw new XMLException();
		}
		
		return null;
	}

	@Override
	public void insert(Workout t) throws XMLException {
		try {
			Document document = ManagerFactoryXML.getInstance().loadDocument(this.filePath);
			Element root = document.getDocumentElement();
			
			Element workoutElement = document.createElement(ENTITY_NAME);
			workoutElement.setAttribute("id", String.valueOf(t.getId()));
			workoutElement.setAttribute("name", t.getName());
			workoutElement.setAttribute("descript", t.getDescript());
			workoutElement.setAttribute("videoUrl", t.getVideoUrl());
			workoutElement.setAttribute("level", String.valueOf(t.getId()));
			
			root.appendChild(workoutElement);
			
			ManagerFactoryXML.getInstance().saveDocument(this.filePath, document);
		} catch (Exception ex) {
			throw new XMLException(ex.getMessage());
		}
		
	}

	@Override
	public void update(Workout t) throws XMLException {
		Document doc = ManagerFactoryXML.getInstance().loadDocument(this.filePath);
		NodeList workouts = doc.getElementsByTagName(ENTITY_NAME);
		
		for (int i = 0; i < workouts.getLength(); i++) {
			Element workout = (Element) workouts.item(i);
			
			if (Integer.parseInt(workout.getAttribute("id")) != t.getId()) {
				continue;
			}
			workout.setAttribute("id", String.valueOf(t.getId()));
			workout.setAttribute("name", t.getName());
			workout.setAttribute("descript", t.getDescript());
			workout.setAttribute("videoUrl", t.getVideoUrl());
			workout.setAttribute("level", String.valueOf(t.getId()));
			break;
		}
		ManagerFactoryXML.getInstance().saveDocument(this.filePath, doc);
	}

	@Override
	public void delete(Workout t) throws XMLException {
		Document doc = ManagerFactoryXML.getInstance().loadDocument(this.filePath);
		NodeList workouts = doc.getElementsByTagName(ENTITY_NAME);
		
		for (int i = 0; i < workouts.getLength(); i++) {
			Element workout = (Element) workouts.item(i);
			
			if (Integer.parseInt(workout.getAttribute("id")) != t.getId()) {
				workout.getParentNode().removeChild(workout);
				break;
			}
		}
		
	}

	
	
}
