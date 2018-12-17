package ags.goldenlionerp.application.setups.dmaai;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;

import ags.goldenlionerp.basecomponents.ParentChildrenService;

import java.io.IOException;

@Service
@Transactional
public class DMAAIService extends ParentChildrenService<DMAAIHeader, DMAAIDetail, Integer, DMAAIDetailPK> {

	private DMAAIHeaderRepository headRepo;
	private DMAAIDetailRepository detailRepo;

	@Autowired @Qualifier("halObjectMapper")
	private ObjectMapper mapper;
	
	public DMAAIService(DMAAIHeaderRepository headRepo, DMAAIDetailRepository detailRepo) {
		super(headRepo, detailRepo);
		this.headRepo = headRepo;
		this.detailRepo = detailRepo;
	}
	
	public Collection<DMAAIDetail> saveDetailsToHeader(int headerId, List<Map<String, Object>> requests) {
		return super.saveChildrenToParent(headerId, requests);
	}
		
	protected DMAAIDetailPK constructChildId(Map<String, Object> request) {
		return new DMAAIDetailPK(
				(Integer) request.get("dmaaiNo"),
				(String) request.get("companyId"),
				(String) request.get("documentType"),
				(String) request.get("documentOrderType"),
				(String) request.get("glClass"),
				(String) request.get("manufacturingCostType")
		);
	}
	
	protected DMAAIDetail constructChild(Map<String, Object> request) {
		
		try {
			String reqJson = mapper.writeValueAsString(request);
			return mapper.readValue(reqJson, DMAAIDetail.class);
		} catch (IOException e) {
			//should never happen
			e.printStackTrace();
			return null;
		}
	}
	
		
	public void deleteHeaderAndAllDetails(int headerId) {
		DMAAIHeader head = headRepo.findById(headerId).orElseThrow(() -> new ResourceNotFoundException());
		
		detailRepo.deleteAll(head.getDetails());
		headRepo.delete(head);
	}

	@Override
	protected void setIdToMatchParent(Integer parentId, Map<String, Object> childRequest) {
		childRequest.put("dmaaiNo", parentId);
	}
}
