package model.BO;

import java.util.ArrayList;

import model.BEAN.Subject;
import model.DAO.SubjectDAO;

public class SubjectBO {
	public ArrayList<String> getAllSubjectIds() {
		return (new SubjectDAO()).getAllSubjectIds();
	}

	public ArrayList<Subject> getAllSubjects() {
		return (new SubjectDAO()).getAllSubjects();
	}

	public boolean createSubject(String id, String name) {
		return (new SubjectDAO()).createSubject(id, name);
	}
}
