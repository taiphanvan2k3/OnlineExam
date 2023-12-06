package model.BEAN;

public class Result {
	private String studentId;
	private String studentName;
	private int correctQuestions;
	private int numQuestions;
	private String examName;

	public Result(String studentId, String studentName, int correctQuestions, int numQuestions, String examName) {
		this.studentId = studentId;
		this.studentName = studentName;
		this.correctQuestions = correctQuestions;
		this.numQuestions = numQuestions;
		this.examName = examName;
	}

	public String getStudentId() {
		return studentId;
	}

	public void setStudentId(String studentId) {
		this.studentId = studentId;
	}

	public String getStudentName() {
		return studentName;
	}

	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}

	public int getCorrectQuestions() {
		return correctQuestions;
	}

	public void setCorrectQuestions(int correctQuestions) {
		this.correctQuestions = correctQuestions;
	}

	public int getNumQuestions() {
		return numQuestions;
	}

	public void setNumQuestions(int numQuestions) {
		this.numQuestions = numQuestions;
	}

	public String getExamName() {
		return examName;
	}

	public void setExamName(String examName) {
		this.examName = examName;
	}
}
