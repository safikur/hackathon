package com.walgreens.dae.hpp.bo;

public interface PrintVersionBO {
	/**
	 * 
	 * @param locaionNbr
	 * @return print version number for a particular store
	 * @throws Exception 
	 */
	public String getPrintVersionNbr(Integer locaionNbr) throws Exception;
}
