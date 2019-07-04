package bmbsoft.orderfoodonline.model.shared;

public class Sort {
	private String predicate;
	public boolean reverse;

	public String getPredicate() {
		return predicate;
	}

	public void setPredicate(String predicate) {
		this.predicate = predicate;
	}

	public boolean isReverse() {
		return reverse;
	}

	public void setReverse(boolean reverse) {
		this.reverse = reverse;
	}

}
