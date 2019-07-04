package bmbsoft.orderfoodonline.model.shared;

import javax.validation.constraints.NotNull;

public class Pagination {
	
	@NotNull
	private int start; // first page
//	private int totalItemCount; // return total item for client
	@NotNull
	private int number; // get max item
//	private int numberOfPages; // return nop for client
	
	
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
//	public int getTotalItemCount() {
//		return totalItemCount;
//	}
//	public void setTotalItemCount(int totalItemCount) {
//		this.totalItemCount = totalItemCount;
//	}
	public int getNumber() {
		return number;
	}
	public void setNumber(int number) {
		this.number = number;
	}
//	public int getNumberOfPages() {
//		return numberOfPages;
//	}
//	public void setNumberOfPages(int numberOfPages) {
//		this.numberOfPages = numberOfPages;
//	}

}
