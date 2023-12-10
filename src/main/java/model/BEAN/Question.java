package model.BEAN;

public class Question {
	private int id;
	private String subjectId;
	private String question;
	private String[] answers;
	private String type;
	private String answerSingle;
	private String[] answerMultiple;

	public Question() {}
	
	public Question(int id, String subjectId, String question, String[] answers, String type, String[] answerMultiple) {
		this.id = id;
		this.subjectId = subjectId;
		this.question = question;
		this.answers = answers;
		this.type = type;
		this.answerMultiple = answerMultiple;
	}
	
	public String getSubjectId() {
		return subjectId;
	}

	public void setSubjectId(String subjectId) {
		this.subjectId = subjectId;
	}

	public String getQuestion() {
		return question;
	}

	public void setQuestion(String question) {
		this.question = question;
	}

	public String[] getAnswers() {
		return answers;
	}

	public void setAnswers(String[] answers) {
		this.answers = answers;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getAnswerSingle() {
		return answerSingle;
	}

	public void setAnswerSingle(String answerSingle) {
		this.answerSingle = answerSingle;
	}

	public String[] getAnswerMultiple() {
		return answerMultiple;
	}

	public void setAnswerMultiple(String[] answerMultiple) {
		this.answerMultiple = answerMultiple;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

}
