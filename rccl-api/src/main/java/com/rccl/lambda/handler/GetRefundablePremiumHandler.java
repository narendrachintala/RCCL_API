package com.rccl.lambda.handler;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.ClientContext;
import com.amazonaws.services.lambda.runtime.CognitoIdentity;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.GsonBuilder;
import com.rccl.dto.RefundablePremiumDTO;
import com.rccl.model.GatewayResponse;
import com.rccl.model.ParameterFiltersData;
import com.rccl.model.validator.RequestDataValidator;
import com.rccl.service.RefundablePremiumService;
import com.rccl.utils.RCCLConstants;
import com.rccl.utils.ResponseUtil;

/**
 * The Class GetRefundablePremiumHandler.
 */
public class GetRefundablePremiumHandler
		implements RequestHandler<ParameterFiltersData, GatewayResponse<? extends Object>> {

	static {
		System.setProperty("log4j.configurationFile", "log4j2.xml");
	}

	// Initialize the Log4j logger.
	static final Logger logger = LogManager.getLogger(GetRefundablePremiumHandler.class);
	
	/**
	 * This method will be invoked from AWS Lambda function to fetch refundable premium
	 * parameter data based on provided filter criteria
	 *
	 * @see com.amazonaws.services.lambda.runtime.RequestHandler#handleRequest(java.lang.
	 *      Object, com.amazonaws.services.lambda.runtime.Context)
	 * 
	 */
	public GatewayResponse<? extends Object> handleRequest(ParameterFiltersData request, Context context) {
		//logger.info("Input request: " + request);
		GatewayResponse<? extends Object> response = null;
		List<RefundablePremiumDTO> refundablePremiumList = null;
		RequestDataValidator requestDataValidator = null;
		try {
			// Validate input request
			requestDataValidator = new RequestDataValidator();
			response = requestDataValidator.validateGetRequest(request);
			if (response == null) { // response null denotes request is valid
				RefundablePremiumService refundablePremiumService = new RefundablePremiumService();
				refundablePremiumList = refundablePremiumService.getRefundablePremiumData(request);
				response = new GatewayResponse<List<RefundablePremiumDTO>>(refundablePremiumList, ResponseUtil.getHeaders(),
						RCCLConstants.SC_OK);
			}
		}
		catch (Exception ex) {
			logger.error("Error occured while executing GetRollingWindowHandler: " + ex.getMessage());
			response = new GatewayResponse<String>(ex.getLocalizedMessage(), ResponseUtil.getHeaders(),
					RCCLConstants.SC_BAD_REQUEST);
		}
		System.out.println(new GsonBuilder().serializeNulls().create().toJson(response));
		return response;
	}
	
	public static void main(String args[]) {
		// prepares sample input data for handler class
		ParameterFiltersData parameterFiltersData = new ParameterFiltersData();
		parameterFiltersData.setCat_class("O");
		parameterFiltersData.setMetaproduct("SHORT CARIBBEAN");
		parameterFiltersData.setOccupancy("quad");
		parameterFiltersData.setProduct_code("CARIB5");
		parameterFiltersData.setSail_month("3");
		parameterFiltersData.setShip_code("MJ");
		
		new GetRefundablePremiumHandler().handleRequest(parameterFiltersData, new Context() {

			@Override
			public String getAwsRequestId() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String getLogGroupName() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String getLogStreamName() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String getFunctionName() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String getFunctionVersion() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String getInvokedFunctionArn() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public CognitoIdentity getIdentity() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public ClientContext getClientContext() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public int getRemainingTimeInMillis() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public int getMemoryLimitInMB() {
				// TODO Auto-generated method stub
				return 0;
			}

			@Override
			public LambdaLogger getLogger() {
				// TODO Auto-generated method stub
				return new LambdaLogger() {
					
					@Override
					public void log(String string) {
						// TODO Auto-generated method stub
						
					}
				};
			}
			
		});
	}

}