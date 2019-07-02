package com.rccl.utils.helper;

import com.rccl.model.CurrentPricePara;
import com.rccl.utils.RCCLConstants;

/**
 * The Class CurrentPriceDataHelper.
 */
public class CurrentPriceDataHelper {

	/**
	 * Generate setter condition.
	 *
	 * @param currentPriceReq the current_price_para req
	 * @param queryBuffer the query buffer
	 * @return the string
	 */
	public String generateSetterCondition(CurrentPricePara currentPriceReq, StringBuffer queryBuffer) {

		String EQUALS = RCCLConstants.EQUALS;
		String COMMA = RCCLConstants.COMMA;
		String SINGLE_QUOTE = RCCLConstants.SINGLE_QUOTE;

		if (currentPriceReq.getL1_range_max() != null) {

			queryBuffer.append(RCCLConstants.L1_RANGE_MAX).append(EQUALS);
			queryBuffer.append(SINGLE_QUOTE).append(currentPriceReq.getL1_range_max()).append(SINGLE_QUOTE);
			queryBuffer.append(COMMA);

		}
		if (currentPriceReq.getL1_range_min() != null) {

			queryBuffer.append(RCCLConstants.L1_RANGE_MIN).append(EQUALS);
			queryBuffer.append(SINGLE_QUOTE).append(currentPriceReq.getL1_range_min()).append(SINGLE_QUOTE);
			queryBuffer.append(COMMA);

		}
		if (currentPriceReq.getPrice_window() != null) {

			queryBuffer.append(RCCLConstants.PRICE_WINDOW).append(EQUALS);
			queryBuffer.append(SINGLE_QUOTE).append(currentPriceReq.getPrice_window()).append(SINGLE_QUOTE);
			queryBuffer.append(COMMA);

		}
		// removing last appended extra COMMA
		queryBuffer.replace(queryBuffer.lastIndexOf(COMMA), queryBuffer.length(), "");

		System.out.println("queryBuffer.toString(): " + queryBuffer.toString());

		return queryBuffer.toString();

	}

}