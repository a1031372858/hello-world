package org.example.util;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

/**
 * @author xuyachang
 * @date 2025/6/11
 */
@Component
@RequiredArgsConstructor
public class RedisUtils {

    private final RedisTemplate<String,String> redisTemplate;

    /**
     * 根据value值生成锁
     * @param key
     * @param value
     * @param time
     * @param timeUnit
     * @return
     */
    public boolean lock(String key, String value, Long time, TimeUnit timeUnit){
        Boolean result = redisTemplate.opsForValue().setIfAbsent(key, value, time, timeUnit);
        return result;
    }

    /**
     * 根据value值释放锁，值不同则释放失败
     * @param key
     * @param value
     * @return
     */
    public boolean unlock(String key, String value){
        String script = "if redis.call('get',KEYS[1]) == ARGV[1] then return redis.call('del',KEYS[1]) else return 0 end";
        Long result = redisTemplate.execute(new DefaultRedisScript<Long>(script, Long.class), Arrays.asList(key), value);
        return !Objects.isNull(result) && result > 0;
    }
}
