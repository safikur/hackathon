package com.walgreens.dae.hpp.bo;

import java.util.List;

public interface LoyaltyMemberKeysBO {
	/**
	 * 
	 * @param memberId
	 * @return list of offer Ids
	 */
	public List<String> get7PartKeysByMemberId(String memberId);
}
