package lxz.dbutil.sql;

import java.io.Serializable;

public class Pagination implements Serializable{
	
	private static final long serialVersionUID = 1L;

	private int currentPage = 1;
	private int totalPage = 0;
	private int totalSize = 0;
	private int pageSize = 20;
	
	private boolean showPrePageBtn = false;
	private boolean showNextPageBtn = false;
	private boolean showFirstPageBtn = false;
	private boolean showLastPageBtn = false;
	
	private String firstPageBtnText = "首页";
	private String lastPageBtnText = "末页";
	private String prePageBtnText = "上一页";
	private String nextPageBtnText = "下一页";

	/**
	 * 分页类，页码从1开始
	 * @param currentPage
	 * @param pageSize
	 */
	public Pagination(int currentPage, int pageSize, int totalSize) {
		if (pageSize <= 0) {
			throw new IllegalArgumentException("pageSize必须大于0！");
		}
		if (currentPage <= 0) {
			throw new IllegalArgumentException("页码从1开始！");
		}
		
		if (totalSize <= 0) {
			throw new IllegalArgumentException("总数目必须大于0！");
		}
		
		this.pageSize = pageSize;
		this.totalSize = totalSize;
		this.currentPage = currentPage;
	}
	
	
	public String toHtml(){
		return null;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public Pagination setCurrentPage(int currentPage) {
		if (currentPage <= 0) {
			currentPage = 1;
		}
		
		this.currentPage = currentPage;
		
		return this;
	}

	public int getPageSize() {
		return pageSize;
	}

	public int getTotalPage() {
		
		totalPage = totalSize / pageSize;
		if ((totalSize % pageSize) != 0) {
			totalPage = totalPage + 1;
		}		
		
		return totalPage;
	}

	public int getTotalSize() {
		return totalSize;
	}

	public boolean isNextPageAvailable() {
		return this.currentPage < getTotalPage();
	}

	public boolean isPreviousPageAvailable() {
		return this.currentPage > 1;
	}

	public int getFromPos() {
		return (currentPage - 1) * pageSize;
	}

	public boolean isShowPrePageBtn() {
		return showPrePageBtn;
	}

	public void setShowPrePageBtn(boolean showPrePageBtn) {
		this.showPrePageBtn = showPrePageBtn;
	}

	public boolean isShowNextPageBtn() {
		return showNextPageBtn;
	}

	public void setShowNextPageBtn(boolean showNextPageBtn) {
		this.showNextPageBtn = showNextPageBtn;
	}

	public boolean isShowFirstPageBtn() {
		return showFirstPageBtn;
	}

	public void setShowFirstPageBtn(boolean showFirstPageBtn) {
		this.showFirstPageBtn = showFirstPageBtn;
	}

	public boolean isShowLastPageBtn() {
		return showLastPageBtn;
	}

	public void setShowLastPageBtn(boolean showLastPageBtn) {
		this.showLastPageBtn = showLastPageBtn;
	}

	public String getFirstPageBtnText() {
		return firstPageBtnText;
	}

	public void setFirstPageBtnText(String firstPageBtnText) {
		this.firstPageBtnText = firstPageBtnText;
	}

	public String getLastPageBtnText() {
		return lastPageBtnText;
	}

	public void setLastPageBtnText(String lastPageBtnText) {
		this.lastPageBtnText = lastPageBtnText;
	}

	public String getPrePageBtnText() {
		return prePageBtnText;
	}

	public void setPrePageBtnText(String prePageBtnText) {
		this.prePageBtnText = prePageBtnText;
	}

	public String getNextPageBtnText() {
		return nextPageBtnText;
	}

	public void setNextPageBtnText(String nextPageBtnText) {
		this.nextPageBtnText = nextPageBtnText;
	}
	
	
}
