package net.bot.crypto.application.common.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class RedisService {

    private final RedisTemplate<String, Object> redisTemplate;

    /**
     * Redis에서 데이터 가져오기 (다양한 타입 지원)
     * @param key 가져올 데이터의 Key
     * @return 데이터
     */
    public <T> T getData(String key, Class<T> type) {
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        Object result = valueOperations.get(key);
        return castToType(result, type);
    }

    /**
     * Redis에 데이터 저장 (다양한 타입 지원)
     * @param key 저장할 데이터의 Key
     * @param value 저장할 데이터
     */
    public <T> void setData(String key, T value) {
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, value);
    }

    /**
     * Redis에 데이터 저장 및 만료 시간 설정 (다양한 타입 지원)
     * @param key 저장할 데이터의 Key
     * @param value 저장할 데이터
     * @param duration 만료 시간 (초)
     */
    public <T> void setDataExpire(String key, T value, Duration duration) {
        ValueOperations<String, Object> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(key, value, duration);
    }

    /**
     * Redis에 해당 Key를 가진 데이터가 존재하는지 확인
     * @param key 확인할 데이터의 Key
     * @return 데이터 존재 여부
     */
    public boolean hasKey(String key) {
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    /**
     * Redis에 저장된 데이터 삭제
     * @param key 삭제할 데이터의 Key
     */
    public void deleteData(String key) {
        redisTemplate.delete(key);
    }

    // ========== PRIVATE METHODS ========== //
    private <T> T castToType(Object result, Class<T> type) {
        if (type.isInstance(result)) {
            return type.cast(result);
        }
        throw new IllegalArgumentException("Type mismatch! Expected: " + type + ", Actual: " + result.getClass());
    }
}
