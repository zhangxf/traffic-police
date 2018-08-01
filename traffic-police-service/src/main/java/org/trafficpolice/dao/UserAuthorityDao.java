package org.trafficpolice.dao;

import java.util.Collection;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import org.trafficpolice.dto.UserAuthorityDTO;
import org.trafficpolice.po.UserAuthority;

/**
 * @author zhangxiaofei
 * @createdOn 2018年7月12日 下午8:57:46
 */
@Repository(UserAuthorityDao.BEAN_ID)
public interface UserAuthorityDao {

	public static final String BEAN_ID = "userAuthorityDao";
	
	public Integer doInsert(UserAuthority po);
	
	public Integer doDelete(@Param("id") Long id);
	
	public Integer deleteByUserId(@Param("userId") Long userId);
	
	public List<Long> findAuthorityIdsByUserId(@Param("userId") Long userId);
	
	public List<UserAuthorityDTO> findAll();
	
	public List<UserAuthority> findByUserId(@Param("userId") Long userId);
	
	public Integer deleteByAuthorityId(@Param("authorityId") Long authorityId);
	
	public List<Long> filterAuthorityIdsByUserIdAndAuthorityIds(@Param("userId") Long userId, @Param("authorityIds") Collection<Long> authorityIds);
	
}
