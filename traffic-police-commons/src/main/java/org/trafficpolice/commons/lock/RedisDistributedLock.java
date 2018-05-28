package org.trafficpolice.commons.lock;

import java.util.Collections;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.trafficpolice.commons.cache.CacheNamespace;

/**
 * 使用lua脚本【保证脚本执行原子性】<br/>
 * Redis 使用单个 Lua 解释器去运行所有脚本，并且， Redis 也保证脚本会以原子性(atomic)的方式执行：当某个脚本正在运行的时候，不会有其他脚本或 Redis 命令被执行。<br/>
 * <strong>注意：如果脚本执行过程中某行脚本出现错误，不会回滚</strong>
 * @see <strong>http://doc.redisfans.com/script/eval.html</strong>
 * @author zhangxiaofei
 * @createdOn 2018年5月8日 下午8:52:50
 */
public class RedisDistributedLock {
	
	private static final Logger logger = LoggerFactory.getLogger(RedisDistributedLock.class);
	
	private static final String LOCK_CACHE_PREFIX = CacheNamespace.TRAFFIC_POLICE + CacheNamespace.SEPARATOR;
	
    private RedisTemplate<String, Object> redisTemplate;

	private static final RedisSerializer<String> stringRedisSerializer = new StringRedisSerializer();
	
	private static final RedisSerializer<Boolean> booleanRedisSerializer = new RedisSerializer<Boolean>() {

		@Override
		public byte[] serialize(Boolean t) throws SerializationException {
			return stringRedisSerializer.serialize(t.toString());
		}

		@Override
		public Boolean deserialize(byte[] bytes) throws SerializationException {
			return Boolean.valueOf(stringRedisSerializer.deserialize(bytes));
		}
		
	};
	
	private static final DefaultRedisScript<Boolean> SETNX_AND_EXPIRE_SCRIPT;
	
	static {
		StringBuffer script = new StringBuffer();
		script.append("if (redis.call('setnx', KEYS[1], ARGV[1]) == 1) ")
			  .append("then ")
			    .append("redis.call('expire', KEYS[1], tonumber(ARGV[2])) ")
			    .append("return true ")
			  .append("else ")
			    .append("return false ")
			  .append("end");
		SETNX_AND_EXPIRE_SCRIPT = new DefaultRedisScript<Boolean>(script.toString(), Boolean.class);
	}
	
	private static final DefaultRedisScript<Boolean> DEL_IF_GET_EQUALS;
	
    static {
        StringBuilder script = new StringBuilder();
        script.append("if (redis.call('get', KEYS[1]) == ARGV[1]) ")
        	  .append("then ")
        		.append("redis.call('del', KEYS[1]) ")
        		.append("return true ")
        	  .append("else ")
        	    .append("return false ")
        	  .append("end");
        DEL_IF_GET_EQUALS = new DefaultRedisScript<Boolean>(script.toString(), Boolean.class);
    }
    
    public RedisDistributedLock() {
    	
    }
    
    public RedisDistributedLock(RedisTemplate<String, Object> redisTemplate) {
    	this.redisTemplate = redisTemplate;
    }
    
    private boolean doTryLock(String lockKey, String lockValue, int lockSeconds) {
        lockKey = LOCK_CACHE_PREFIX + lockKey;
        return redisTemplate.execute(SETNX_AND_EXPIRE_SCRIPT, stringRedisSerializer, booleanRedisSerializer, Collections.singletonList(lockKey), new Object[] {lockValue, String.valueOf(lockSeconds)});
    }
    
    /**
     * 尝试获得锁，成功返回true，如果失败立即返回false
     *
     * @param lockSeconds 加锁的时间(秒)，超过这个时间后锁会自动释放
     */
    public boolean tryLock(String lockKey, String lockValue, int lockSeconds) {
        try {
        	return doTryLock(lockKey, lockValue, lockSeconds);
        } catch (Exception e) {
            logger.error("####【redis分布式锁】【tryLock Error】####", e);
            return false;
        }
    }
    
    /**
     * 轮询的方式去获得锁，成功返回true，超过轮询次数或异常返回false
     *
     * @param lockSeconds       加锁的时间(秒)，超过这个时间后锁会自动释放
     * @param tryIntervalMillis 轮询的时间间隔(毫秒)
     * @param maxTryCount       最大的轮询次数
     */
    public boolean tryLock(String lockKey,String lockValue,final int lockSeconds, final long tryIntervalMillis, final int maxTryCount) {
        int tryCount = 0;
        while (true) {
            if (++tryCount >= maxTryCount) {
                //获取锁超过轮询次数
                return false;
            }
            try {
                if (doTryLock(lockKey,lockValue,lockSeconds)) {
                    return true;
                }
            } catch (Exception e) {
                logger.error("####【redis分布式锁】【tryLock Error】【轮询】####", e);
                return false;
            }
            try {
                Thread.sleep(tryIntervalMillis);
            } catch (InterruptedException e) {
                logger.error("####【redis分布式锁】【tryLock sleep interrupted】####", e);
                return false;
            }
        }
    }
    
    /**
     * 解锁操作
     */
    public void unlock(String lockKey, String lockValue) {
        lockKey = LOCK_CACHE_PREFIX + lockKey;
        redisTemplate.execute(DEL_IF_GET_EQUALS, stringRedisSerializer, booleanRedisSerializer, Collections.singletonList(lockKey), new Object[] {lockValue});
    }
	
}
