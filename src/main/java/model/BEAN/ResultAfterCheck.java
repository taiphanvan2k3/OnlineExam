package model.BEAN;

public class ResultAfterCheck {
	private double correctQuestions;
	private double finalScore;

	public ResultAfterCheck(double correctQuestions, double finalScore) {
		this.correctQuestions = correctQuestions;
		this.finalScore = finalScore;
	}

	public double getCorrectQuestions() {
		return correctQuestions;
	}

	public void setCorrectQuestions(double correctQuestions) {
		this.correctQuestions = correctQuestions;
	}

	public double getFinalScore() {
		return finalScore;
	}

	public void setFinalScore(double finalScore) {
		this.finalScore = finalScore;
	}
}
