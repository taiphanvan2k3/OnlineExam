package model.BEAN;

public class SimpleExam {
	private String examName;
	private String subjectName;
	private double totalTime;

	public SimpleExam(String examName, String subjectName, double totalTime) {
		this.examName = examName;
		this.subjectName = subjectName;
		this.totalTime = totalTime;
	}

	public String getExamName() {
		return examName;
	}

	public void setExamName(String examName) {
		this.examName = examName;
	}

	public String getSubjectName() {
		return subjectName;
	}

	public void setSubjectName(String subjectName) {
		this.subjectName = subjectName;
	}

	public double getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(double totalTime) {
		this.totalTime = totalTime;
	}
}
