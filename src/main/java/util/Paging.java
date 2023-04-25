package util;

public class Paging {
	
	private int curPage;
	
	private int totalCount;
	private int listCount;
	private int totalPage;
	
	private int pageCount;
	private int startPage;
	private int endPage;
	
	private int startNo;
	private int endNo;
	
	public Paging() {}
	
	public Paging( int totalCount, int curPage) {
		setTotalCount(totalCount);
		setCurPage(curPage);
		makePaging();
	}

	
	public void makePaging() {
		if(totalCount == 0) return;
		if(curPage<=0) setCurPage(1);
		if(listCount==0) setListCount(10);
		if(pageCount==0) setPageCount(10);
		
		totalPage = totalCount / listCount;
		if( totalCount % listCount > 0) totalPage++;
		
		if(curPage > totalPage) curPage = totalPage;
		
		startPage = ( (curPage-1)/pageCount ) * pageCount + 1;
		endPage = startPage + pageCount -1;
		
		if( endPage > totalPage ) endPage = totalPage;
		
		startNo = (curPage-1) * listCount + 1;
		endNo = curPage * listCount;
		
	}
	
	
	@Override
	public String toString() {
		return "Paging [curPage=" + curPage + ", totalCount=" + totalCount + ", listCount=" + listCount + ", totalPage="
				+ totalPage + ", pageCount=" + pageCount + ", startPage=" + startPage + ", endPage=" + endPage
				+ ", startNo=" + startNo + ", endNo=" + endNo + "]";
	}

	public int getCurPage() {
		return curPage;
	}

	public void setCurPage(int curPage) {
		this.curPage = curPage;
	}

	public int getTotalCount() {
		return totalCount;
	}

	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}

	public int getListCount() {
		return listCount;
	}

	public void setListCount(int listCount) {
		this.listCount = listCount;
	}

	public int getTotalPage() {
		return totalPage;
	}

	public void setTotalPage(int totalPage) {
		this.totalPage = totalPage;
	}

	public int getPageCount() {
		return pageCount;
	}

	public void setPageCount(int pageCount) {
		this.pageCount = pageCount;
	}

	public int getStartPage() {
		return startPage;
	}

	public void setStartPage(int startPage) {
		this.startPage = startPage;
	}

	public int getEndPage() {
		return endPage;
	}

	public void setEndPage(int endPage) {
		this.endPage = endPage;
	}

	public int getStartNo() {
		return startNo;
	}

	public void setStartNo(int startNo) {
		this.startNo = startNo;
	}

	public int getEndNo() {
		return endNo;
	}

	public void setEndNo(int endNo) {
		this.endNo = endNo;
	}
	
	
	
}
