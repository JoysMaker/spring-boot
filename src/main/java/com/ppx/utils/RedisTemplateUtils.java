package com.ppx.utils;

import java.io.Serializable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundListOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import com.alibaba.fastjson.JSON;
@Component
public class RedisTemplateUtils {
	
	@Autowired
	private RedisTemplate redisTemplate;
	
	private Lock lock =  new ReentrantLock();//上锁。基于底层IO阻塞考虑  如果分布式的话，就是用分式式的锁
	
	
	private String getMergeKey(String cacheGroup,String key) {
		
		return cacheGroup+":"+key;
	}
	//***************************对string类型的操作**********************************//

	/**
	 * 插入缓存并且设置设置默认失效时间(2小时)
	 * @param key
	 * @param value
	 * @return
	 */
	public boolean set(final String key,final Object value) {
		boolean result = false;
		try {
			ValueOperations<Serializable,Object> operations = redisTemplate.opsForValue();
			operations.set(key, value);
			redisTemplate.expire(key, 7200L, TimeUnit.SECONDS);
			result = true; 
		} catch (Exception e) {
			
			e.printStackTrace();
			
		}
		
		return result;
	}
	
	/**
	 * 插入指定失效时间的缓存(单位:秒)
	 * @param key
	 * @param value
	 * @param expireTime
	 * @return
	 */
	public boolean set(final String key,Object value,Long expireTime) {
		
		boolean result = false;
		try {
			ValueOperations<Serializable,Object> operations = redisTemplate.opsForValue();
			operations.set(key, value);
			redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
		
	}
	
	/**
	 * 设置缓存并且失效时间(失效时间单位自定义)
	 * @param key
	 * @param value
	 * @param expireTime
	 * @param timeUnit
	 * @return
	 */
	public boolean set(final String key,Object value,Long expireTime,TimeUnit timeUnit) {
		boolean result = false;
		try {
			ValueOperations<Serializable,Object> operations = redisTemplate.opsForValue();
			operations.set(key, value);
			redisTemplate.expire(key, expireTime, timeUnit);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return result;
	}
	
	
	public boolean setCacheKeyVal(final String cacheGroup,final String key,final Object value) {
		
		String cacheKey = getMergeKey(cacheGroup, key);
		
		return  set(cacheKey, value);
	}
	
    public boolean setCacheKeyVal(final String cacheGroup,final String key,final Object value,Long expireTime) {
		
		String cacheKey = getMergeKey(cacheGroup, key);
		
		return  set(cacheKey, value,expireTime);
	}
    
    public boolean setCacheKeyVal(final String cacheGroup,final String key,final Object value,Long expireTime,TimeUnit timeUnit) {
		
		String cacheKey = getMergeKey(cacheGroup, key);
		
		return  set(cacheKey, value,expireTime,timeUnit);
	}

	/**
	 * 读取缓存
	 * @param key
	 * @return
	 */
	public Object get(final String key) {
		Object result;
		ValueOperations<Serializable,Object> operations = redisTemplate.opsForValue();
		result = operations.get(key);
	    return result;
	}
	
	
	public<T> T get(final String key,Class<T> clazz) {
		
		return JSON.parseObject(JSON.toJSONString(get(key)), clazz);
	}
	
	public<T> T get(final String cacheGroup ,final String key,Class<T> clazz) {
		String cacheKey = getMergeKey(cacheGroup, key);
		return JSON.parseObject(JSON.toJSONString(get(cacheKey)), clazz);
	}
	/**
	 * 删除操作
	 * @param key
	 */
	public void removeValue(final String key) {
		if (existsKey(key)) {
			redisTemplate.delete(key);
		}
  		
     }
    public void removeValues(final String ...keys) {
    		redisTemplate.delete(keys);
    }
	  
	public boolean existsKey(final String key) {
		
		return redisTemplate.hasKey(key);
	}

  /**
   * 设置过期时间
   * @param key
   * @param expireTime
   */
  public void expire(String key,Long expireTime){
    redisTemplate.expire(key,expireTime, TimeUnit.SECONDS);
  }

  public void expire(String key, Long expireTime, TimeUnit times) {
    redisTemplate.expire(key,expireTime, times);
  }
  
  /**
   * 获取失效时间
   * @param key
   * @return
   */
  public Long getExpireTime(final String key) {
	  Long expire = 0L;
	  if (existsKey(key)) {
		  expire = redisTemplate.getExpire(key,TimeUnit.SECONDS);
	}
	  return expire;
  }
  
 //***************************对List类型的操作**********************************//

  public<T> void pushFromHead(final String key,T value) {
	  
	  BoundListOperations boundListOps = redisTemplate.boundListOps(key);
	  boundListOps.leftPush(value);
  }
  
  public<T> void pushCacheValFromHead(final String  cacheGroup,final String key,T value) {
	  String cacheKey = getMergeKey(cacheGroup, key);
	  pushFromHead(cacheKey, value);
	  
  }
  
  public<T> void pushListFromHead(final String key,T...values) {
	  
	 BoundListOperations boundListOps = redisTemplate.boundListOps(key);
	 boundListOps.leftPushAll(values);
  }
  
  public<T> void pushFromTail(final String key,T value) {
	  
	  BoundListOperations boundListOps = redisTemplate.boundListOps(key);
	  boundListOps.rightPush(value);
  }
  
  public<T> void pushCacheValFromTail(final String  cacheGroup,final String key,T value) {
	  String cacheKey = getMergeKey(cacheGroup, key);
	  pushFromTail(cacheKey, value);
  }
  
  public<T> void pushListFromTail(final String key,T...values) {
	  
	  BoundListOperations boundListOps = redisTemplate.boundListOps(key);
	  boundListOps.rightPushAll(values);
  }
  
  public void removeFromHead(final String key) {
	  
	  BoundListOperations boundListOps = redisTemplate.boundListOps(key);
	  boundListOps.leftPop();
  }
  
  
  public void removeFromTail(final String key) {
	  
	  BoundListOperations boundListOps = redisTemplate.boundListOps(key);
	  boundListOps.rightPop();
  }
  
  
  public<T> T getFromHead(final String key) {
	  
	  BoundListOperations<Serializable,T> boundListOps = redisTemplate.boundListOps(key);
	  
	  try {
		  
		lock.lockInterruptibly();
		
		 if(queueSize(key)==0){
			 
			return null;   
		  }
			T value = boundListOps.leftPop();
			return value;
	} catch (InterruptedException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	  
	  finally {
		  
		  lock.unlock();
	  }
	  
	  return null;
  }
  
  public<T> T getcacheValFromHead(final String cacheGroup,final String key) {
	  
	  String cacheKey = getMergeKey(cacheGroup, key);
	  return getFromHead(cacheKey);
  }
  
	public<T> T getFromTail(final String key) {
		  
		  BoundListOperations<Serializable,T> boundListOps = redisTemplate.boundListOps(key);
		  
		  try {
			  
			lock.lockInterruptibly();
			
			 if(queueSize(key)==0){
				 
				return null;   
			  }
				T value = boundListOps.rightPop();
				return value;
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		  
		  finally {
			  
			  lock.unlock();
		  }
		  
		  return null;
	  }
  
  public<T> T getcacheValFromTail(final String cacheGroup,final String key) {
	  
	  String cacheKey = getMergeKey(cacheGroup, key);
	  return getFromTail(cacheKey);
  }
  
  public Long queueSize(final String key) {
	  
	  BoundListOperations boundListOps = redisTemplate.boundListOps(key);
	  return boundListOps.size();
  }
  
//***************************对hash类型的操作**********************************//
  
}
