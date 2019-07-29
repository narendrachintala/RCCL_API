package com.rccl.lambda.handler;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.amazonaws.services.lambda.runtime.ClientContext;
import com.amazonaws.services.lambda.runtime.CognitoIdentity;
import com.amazonaws.services.lambda.runtime.Context;
import com.amazonaws.services.lambda.runtime.LambdaLogger;
import com.amazonaws.services.lambda.runtime.RequestHandler;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.rccl.dto.RollingWindowDTO;
import com.rccl.model.ApiGatewayProxyRequest;
import com.rccl.model.GatewayResponse;
import com.rccl.model.ParameterFiltersData;
import com.rccl.model.validator.RequestDataValidator;
import com.rccl.service.RollingWindowService;
import com.rccl.utils.RCCLConstants;
import com.rccl.utils.ResourceBundleUtility;
import com.rccl.utils.ResponseUtil;

/**
 * The Class RollingWindowHandler.
 */
// Start of Lambda Function request
public class GetRollingWindowHandler
		implements RequestHandler<ApiGatewayProxyRequest, GatewayResponse> {

	static {
		System.setProperty("log4j.configurationFile", "log4j2.xml");
	}

	// Initialize the Log4j logger.
	static final Logger logger = LogManager.getLogger(GetRollingWindowHandler.class);

	// Read error messages from property file
	private static ResourceBundleUtility rBundleUtility = ResourceBundleUtility.getInstance();

	/**
	 * executes on requesting for list of values for specific table name
	 * 
	 * @param request contains chosen filters as key-value pair
	 * @param context lambda context object
	 * @return the list of column values based on provided tablename
	 */
	public GatewayResponse handleRequest(ApiGatewayProxyRequest req, Context context) {
		
		ParameterFiltersData request = new Gson().fromJson(req.getBody(), ParameterFiltersData.class);
		logger.info("Input request: " + request);

		/**
		 * Assigning the AWS Lambda Request ID to Static Constant, which can be referred
		 * through out session
		 */
		RCCLConstants.REQUEST_ID = context.getAwsRequestId();

		GatewayResponse response = null;
		List<RollingWindowDTO> rollingWindowList = null;
		RequestDataValidator requestDataValidator = null;
		try {
			// Validate input request
			requestDataValidator = new RequestDataValidator();
			response = requestDataValidator.validateGetRequest(request);
			if (response == null) { // response null denotes request is valid
				RollingWindowService rollingWindowService = new RollingWindowService();
				rollingWindowList = rollingWindowService.getRollingWindowData(request);
				if (rollingWindowList != null && rollingWindowList.size() == 0) {
					response = ResponseUtil.getCustErrorMessage(
							rBundleUtility.getValue(RCCLConstants.ERROR_NO_RECORDS_FOUND), RCCLConstants.SC_OK,
							RCCLConstants.REQUEST_ID);
				} else {
					response = new GatewayResponse(rollingWindowList, ResponseUtil.getHeaders(),
							RCCLConstants.SC_OK, RCCLConstants.REQUEST_ID);
				}
			}
		} catch (Exception ex) {
			logger.error("Error occured while executing GetRollingWindowHandler: " + ex.getMessage());
			response = ResponseUtil.getErrorMessage(ex, RCCLConstants.SC_BAD_REQUEST, RCCLConstants.REQUEST_ID);
		}
		System.out.println(new GsonBuilder().serializeNulls().create().toJson(response));
		return response;
	}

	/**
	 * The main method.
	 * 
	 * @param args the arguments
	 */
	public static void main(String[] args) {
		// prepares sample input data for handler class
		ParameterFiltersData parameterFiltersData = new ParameterFiltersData();
		parameterFiltersData.setCat_class("O");
		parameterFiltersData.setMetaproduct("SHORT CARIBBEAN123");
		parameterFiltersData.setOccupancy("quad");
		parameterFiltersData.setProduct_code("BAHAMA4");
		parameterFiltersData.setSail_month("3");
		parameterFiltersData.setShip_code("MJ");

		new GetRollingWindowHandler().handleRequest(null, new Context() {

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

			@Override
			public String getLogStreamName() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String getLogGroupName() {
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
			public String getFunctionVersion() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String getFunctionName() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public ClientContext getClientContext() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public String getAwsRequestId() {
				// TODO Auto-generated method stub
				return null;
			}
		});
	}
}
