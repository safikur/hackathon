package com.walgreens.dae.hpd.printversion;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class PrintVersionController {
	@RequestMapping(value="/store/printversion", method=RequestMethod.GET)
	public String getStorePrintVersionNbr(){
		return "004";
	}
}
