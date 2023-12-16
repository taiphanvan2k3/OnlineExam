package model.BEAN;

import java.sql.Timestamp;

public class HistoryDoExam {
	private String subjectId;
	private String subjectName;
	private String nameOfExam;
	private Timestamp openAt;
	private Timestamp submitAt;
	private int totalQuestions;
	private double correctQuestions;
	private double totalTimeOfExam;

	public String getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getNameOfExam() {
		return nameOfExam;
	}

	public void setNameOfExam(String nameOfExam) {
		this.nameOfExam = nameOfExam;
	}

	public Timestamp getOpenAt() {
		return openAt;
	}

	public void setOpenAt(Timestamp openAt) {
		this.openAt = openAt;
	}

	public Timestamp getSubmitAt() {
		return submitAt;
	}

	public void setSubmitAt(Timestamp submitAt) {
		this.submitAt = submitAt;
	}

	public int getTotalQuestions() {
		return totalQuestions;
	}

	public void setTotalQuestions(int totalQuestions) {
		this.totalQuestions = totalQuestions;
	}

	public double getCorrectQuestions() {
		return correctQuestions;
	}

	public void setCorrectQuestions(double correctQuestions) {
		this.correctQuestions = correctQuestions;
	}

	public double getTotalTimeOfExam() {
		return totalTimeOfExam;
	}

	public void setTotalTimeOfExam(double totalTimeOfExam) {
		this.totalTimeOfExam = totalTimeOfExam;
	}
}
