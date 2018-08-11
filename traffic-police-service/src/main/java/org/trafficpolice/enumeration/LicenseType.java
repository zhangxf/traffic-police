package org.trafficpolice.enumeration;

import java.util.HashSet;
import java.util.Set;

/**
 * 驾驶证类型
 * @author zhangxiaofei
 * 2018年7月1日上午2:08:05
 */
public enum LicenseType {

	A1, A2, A3, 
	B1, B2, 
	C1, C2, C3, C4, C5,
	D, E, F, M, N, P
	;
	
	private static final Set<String> names = new HashSet<String>();
	
	static {
		for (LicenseType lt : LicenseType.values()) {
			names.add(lt.name());
		}
	}
	
	private LicenseType() {
		
	}
	
	public static Set<String> getNames() {
		return names;
	}
	
}
