package org.trafficpolice.po;

import java.util.Date;

import org.trafficpolice.enumeration.EduType;
import org.trafficpolice.enumeration.ReservationState;

/**
 * 预约
 * @author zhangxiaofei
 * @createdOn 2018年7月25日 下午6:30:39
 */
public class Reservation {

	private Long id;
	
	/**
	 * 用户id
	 */
	private Long userId;
	
	/**
	 * 预约状态
	 */
	private ReservationState reservationState;
	
	/**
	 * 教育类型
	 */
	private EduType eduType;
	
	/**
	 * 场次
	 */
	private String session;
	
	private Date createTime;
	
	private Date updateTime;
	
}
