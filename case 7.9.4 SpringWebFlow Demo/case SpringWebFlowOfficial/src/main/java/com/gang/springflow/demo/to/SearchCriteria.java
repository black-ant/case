package com.gang.springflow.demo.to;

import java.io.Serializable;

/**
 * A backing bean for the main hotel search form. Encapsulates the criteria needed to perform a hotel search.
 */
public class SearchCriteria implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * The user-provided search criteria for finding Hotels.
	 */
	private String searchString = "";

	/**
	 * The maximum page size of the Hotel result list
	 */
	private int pageSize = 5;

	/**
	 * The page the user is currently on.
	 */
	private int currentPage = 1;


	public String getSearchString() {
		return searchString;
	}

	public void setSearchString(String searchString) {
		this.searchString = searchString;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public int getCurrentPage() {
		return currentPage;
	}

	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}

	public String toString() {
		return "[Search Criteria searchString = '" + searchString + "'";
	}

}
