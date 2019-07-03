package com.rccl.lambda.handler;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.rccl.dto.CurrentPriceParaDTO;
import com.rccl.model.GatewayResponse;
import com.rccl.model.ParameterFiltersData;
import com.rccl.model.validator.RequestDataValidator;
import com.rccl.service.CurrentPriceParaService;
import com.rccl.testdata.FiltersData;
import com.rccl.utils.RCCLConstants;
import com.rccl.utils.ResponseUtil;

/**
 * @author chandrabhan.birla
 *
 */
public class GetCurrentPriceDataHandler
		implements RequestHandler<ParameterFiltersData, GatewayResponse<? extends Object>> {

	static {
		System.setProperty("log4j.configurationFile", "log4j2.xml");
	}

	// Initialize the Log4j logger.
		static final Logger logger = LogManager.getLogger(GetCurrentPriceDataHandler.class);

	/*
	 * This method will be invoked by AWS Lambda function to fetch current_price_para
	 * parameter table data based on provided filter criteria
	 *
	 * @see
	 * com.amazonaws.services.lambda.runtime.RequestHandler#handleRequest(java.lang.
	 * Object, com.amazonaws.services.lambda.runtime.Context)
	 * 
	 */
	public GatewayResponse<? extends Object> handleRequest(ParameterFiltersData request, Context context) {
		// LambdaLogger logger = context.getLogger();
		logger.info("Input: " + request.toString());
		
		List<CurrentPriceParaDTO> currentPriceParaList = null;
		GatewayResponse<? extends Object> response = null;
		ResponseUtil respUtil = ResponseUtil.getInstance();

		try {
			// validating request data
			RequestDataValidator currentPriceValidator = new RequestDataValidator();
			response = currentPriceValidator.validateGetRequest(request);
			if (response == null) { // response null denotes request is valid

				CurrentPriceParaService currrentPriceService = new CurrentPriceParaService();
				currentPriceParaList = currrentPriceService.getCurrentPriceParaData(request, logger);
				response = new GatewayResponse<List<CurrentPriceParaDTO>>(currentPriceParaList, respUtil.getHeaders(),
						RCCLConstants.SC_OK);
			}

		} catch (Exception e) {
			logger.error("Error occured while executing GetCurrentPriceDataHandler: " + e.getMessage());
			//throw new RCCLException("Error occured while executing GetPriceRangeDataHandler", e);
			response = new GatewayResponse<String>(e.getLocalizedMessage(), respUtil.getHeaders(),
					RCCLConstants.SC_BAD_REQUEST); 
		}
		// System.out.println(new
		// GsonBuilder().serializeNulls().create().toJson(response));
		return response;

	}

	/**
	 * The main method will be used for testing with sample data.
	 * 
	 * @param args the arguments
	 */
	public static void main(String[] args) {

		/*
		 * ParameterFiltersData data = new ParameterFiltersData();
		 * data.setMetaproduct("OASIS"); System.out.println(new
		 * GsonBuilder().serializeNulls().create().toJson(data)); System.exit(0);
		 */

		new GetCurrentPriceDataHandler().handleRequest(FiltersData.getRequestData(), null);
	}
}