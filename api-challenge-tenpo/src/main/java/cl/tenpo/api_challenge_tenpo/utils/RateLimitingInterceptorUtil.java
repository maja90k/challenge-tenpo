package cl.tenpo.api_challenge_tenpo.utils;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class RateLimitingInterceptorUtil implements HandlerInterceptor {

  private final ConcurrentHashMap<String, Long> requestCounts = new ConcurrentHashMap<>();
  private final ConcurrentHashMap<String, Long> lastRequestTimes = new ConcurrentHashMap<>();
  private final int MAX_REQUESTS_PER_MINUTE = 3;
  private final long ONE_MINUTE = 60_000;

  @Override
  public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
    String clientIp = request.getRemoteAddr();
    long currentTime = System.currentTimeMillis();

    if ("POST".equalsIgnoreCase(request.getMethod()) || "PUT".equalsIgnoreCase(request.getMethod())) {
      if (!lastRequestTimes.containsKey(clientIp) || currentTime - lastRequestTimes.get(clientIp) > ONE_MINUTE) {
        requestCounts.put(clientIp, 1L);
        lastRequestTimes.put(clientIp, currentTime);
      } else {
        long requestCount = requestCounts.get(clientIp) + 1;

        if (requestCount > MAX_REQUESTS_PER_MINUTE) {
          response.setStatus(429);
          return false;
        }
        requestCounts.put(clientIp, requestCount);
      }
    }
    return true;
  }
}