package ags.goldenlionerp.application.datacode;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

@RepositoryRestController
public class DataCodeController {

	@Autowired
	private DataCodeRepository repo;
	
	@GetMapping("/dataCodes/{pc}/{sc}")
	@ResponseBody
	public List<DataCode> getByProductCodeAndSystemCode(
			@PathVariable("pc") String productCode,
			@PathVariable("sc") String systemCode
			){
		return repo.findByPk_ProductCodeAndPk_SystemCode(productCode, systemCode);
	}
}
