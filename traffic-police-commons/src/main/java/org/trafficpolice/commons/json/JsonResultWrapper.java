package org.trafficpolice.commons.json;

import org.trafficpolice.commons.enumeration.GlobalStatusEnum;

/**
 * @author zhangxiaofei
 *
 * @createdOn 2018年1月8日 上午10:46:06
 */
public class JsonResultWrapper {

    public static final String DEFAULT_SUCCESS_MSG = "success";
    
	public static JsonResult wrapSuccess(Object data) {
		return new JsonResult(GlobalStatusEnum.SUCCESS.getStatus(), DEFAULT_SUCCESS_MSG, data);
	}
	
}
