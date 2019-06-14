package com.rccl.lambda.handler;

import java.util.List;
import java.util.Map;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;
import com.rccl.model.FilterDataDTO;
import com.rccl.service.FilterDataService;
import com.rccl.testdata.FiltersData;
import com.rccl.utils.RCCLConstants;

/**
 * 
 * @author narendra.chintala
 *
 */
public class GetOccupancyFilterData implements RequestHandler<Map<String, List<String>>, FilterDataDTO> {

	@Override
	public FilterDataDTO handleRequest(Map<String, List<String>> requestMap, Context context) {
		Gson gson = new Gson();
		System.out.println(gson.toJson(requestMap));

		FilterDataService dataService = new FilterDataService();
		FilterDataDTO response = //new FilterDataDTO();
				dataService.getFilterData(FiltersData.getRequestData(), RCCLConstants.OCCUPANCY_F);
		
		return response;

	}

	public static void main(String[] args) {
		System.out.println(new GetOccupancyFilterData().handleRequest(null, null));
	}

}
