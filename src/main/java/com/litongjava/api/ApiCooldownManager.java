package com.litongjava.api;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ApiCooldownManager {
  private final ConcurrentMap<String, Long> cooldownUntilTimestamps = new ConcurrentHashMap<>();
  private final long COOLDOWN_BUFFER_MS = 500; // Add a small buffer to be safe

  /**
   * Checks if a global cooldown is active for the service. If so, sleeps the current thread.
   * @param serviceKey Identifier for the AI service.
   * @throws InterruptedException if the thread is interrupted while sleeping.
   */
  public void enforceCooldown(String serviceKey) throws InterruptedException {
    Long cooldownUntil = cooldownUntilTimestamps.get(serviceKey);
    if (cooldownUntil != null) {
      long now = System.currentTimeMillis();
      if (now < cooldownUntil) {
        long delay = cooldownUntil - now;
        if (delay > 0) {
          log.info("Global cooldown active for service '{}'. Delaying current request for {} ms.", serviceKey, delay);
          Thread.sleep(delay);
        }
      }
    }
  }

  /**
   * Records or updates a global cooldown period for a service.
   * @param serviceKey Identifier for the AI service.
   * @param actualRetryDelayMillis The delay received from the API.
   */
  public void recordCooldown(String serviceKey, long actualRetryDelayMillis) {
    if (actualRetryDelayMillis <= 0) {
      return;
    }
    long newCooldownUntil = System.currentTimeMillis() + actualRetryDelayMillis + COOLDOWN_BUFFER_MS;
    // Update the cooldown time if the new one is further in the future
    cooldownUntilTimestamps.merge(serviceKey, newCooldownUntil, Math::max);
    log.info("Global cooldown for service '{}' updated. Next requests will be allowed after timestamp: {} (approx. {} ms from now).", serviceKey, newCooldownUntil,
        (newCooldownUntil - System.currentTimeMillis()));
  }
}
