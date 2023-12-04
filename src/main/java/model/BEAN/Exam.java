package model.BEAN;

import java.sql.Timestamp;

public class Exam {
	private String subjectId;
	private String name;
	private int numberOfQuestions;
	private double totalTime;
	private String password;
	private Timestamp openAt;
	private String createBy;

	public Exam() {
		this.subjectId = "";
		this.name = "";
		this.name = "";
		this.createBy = "";
		this.password = "";
		this.numberOfQuestions = 1;
		this.totalTime = 1;
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

	public String getCreateBy() {
		return createBy;
	}

	public void setCreateBy(String createBy) {
		this.createBy = createBy;
	}

}
