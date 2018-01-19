package com.walgreens.dae.hpd.uniquekey;

import java.util.UUID;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UniqueKeyController {

	@RequestMapping(value="/uniqueId/{sevenPartKey}/{version}", method=RequestMethod.GET)
	public String getUniqueKey(@PathVariable("sevenPartKey") String sevenPartKey, @PathVariable("version") String version){
		return UUID.randomUUID().toString();
	}
}
