package recipeHackathon.Model;

public class PaginationDetails {
	
	public String PaginationUri;
	
	public int TotalPages;
	
	public String GetPageForPageNumer(int pageNumber) {
		String textToReplace = String.format("pageindex=%d$1", pageNumber);
		return  PaginationUri.replaceFirst("\\bpageindex=.*?(&|$)", textToReplace);
	}
}
