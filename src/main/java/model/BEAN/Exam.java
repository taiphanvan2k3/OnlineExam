package model.BEAN;

import java.sql.Timestamp;

public class Exam {
	private String examId;
	private String subjectId;
	private String name;
	private int numberOfQuestions;
	private double totalTime;
	private String password;
	private Timestamp openAt;
	private String createBy;
	private String subjectName;
	private String teacherName;

	public Exam() {
		this.examId = "";
		this.subjectId = "";
		this.name = "";
		this.name = "";
		this.setCreateBy("");
		this.password = "";
		this.numberOfQuestions = 1;
		this.totalTime = 1;
	}

	public Exam(String examId, String subjectId, String name, int numberOfQuestions, double totalTime, String password,
			Timestamp openAt, String teacherName) {
		this.examId = examId;
		this.subjectId = subjectId;
		this.name = name;
		this.numberOfQuestions = numberOfQuestions;
		this.totalTime = totalTime;
		this.password = password;
		this.openAt = openAt;
		this.teacherName = teacherName;
	}

	public String getExamId() {
		return examId;
	}

	public void setExamId(String examId) {
		this.examId = examId;
	}

	public String getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getNumberOfQuestions() {
		return numberOfQuestions;
	}

	public void setNumberOfQuestions(int numberOfQuestions) {
		this.numberOfQuestions = numberOfQuestions;
	}

	public double getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(double totalTime) {
		this.totalTime = totalTime;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Timestamp getOpenAt() {
		return openAt;
	}

	public void setOpenAt(Timestamp openAt) {
		this.openAt = openAt;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

	public String getTeacherName() {
		return teacherName;
	}

	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
}
