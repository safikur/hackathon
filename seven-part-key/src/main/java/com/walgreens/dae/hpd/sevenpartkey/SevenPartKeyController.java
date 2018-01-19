package com.walgreens.dae.hpd.sevenpartkey;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SevenPartKeyController {
	@RequestMapping(value="/keys/{memberId}", method=RequestMethod.GET)
	public List<String> getSevenPartKeysByMemberId(@PathVariable("memberId") String memberId){
		String[] offerKeys = testData().split("\\|");
		return Arrays.asList(offerKeys);
	}
	
	private String testData() {
		String temp = "offer1|offer2|offer3|offer4|offer5";
		return temp;
	}
}
