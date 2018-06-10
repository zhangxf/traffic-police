package org.trafficpolice.dao;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.trafficpolice.po.BGUser;

/**
 * @author zhangxiaofei
 * 2018年6月11日上午12:24:15
 */
@Repository
public interface BGUserDao {

	public BGUser queryByUsername(@Param("username") String username);
	
}
