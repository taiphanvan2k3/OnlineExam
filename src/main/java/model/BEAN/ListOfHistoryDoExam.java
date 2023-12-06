package model.BEAN;

import java.util.ArrayList;

public class ListOfHistoryDoExam {
	private ArrayList<HistoryDoExam> histories;
	private int currentPage;
	private int totalPage;

	public ListOfHistoryDoExam(int currentPage) {
		this.currentPage = currentPage;
		this.histories = new ArrayList<>();
	}

	public ArrayList<HistoryDoExam> getHistories() {
		return histories;
	}

	public void setHistories(ArrayList<HistoryDoExam> histories) {
		this.histories = histories;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}
}
