package core.session;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 *
 * Package Name: core.session
 * File Name: SessionManager
 * Description:
 * author: munke
 *
 * @version 1.0
 * @see core.session
 * @since 2026-07-30
 * <p>
 * Modification Information
 * 수정일          수정자                    수정내용
 * --------- ------------------- -------------------------------
 * 2026-07-30        munke                   최초개정
 */
public class SessionManager {
    private final ConcurrentHashMap<String, Session> sessionMap = new ConcurrentHashMap<>();
    private static final long MAX_INACTIVE_INTERVAL = 10 * 1000L; // 밀리초 단위 - 10초
    public static final int MAX_AGE = 60 * 60 * 24 * 14; // 초단위, 14일

    public SessionManager() {
        this.startCleaner();
    }

    public void startCleaner() {
        ScheduledExecutorService cleaner = Executors.newSingleThreadScheduledExecutor(runnable -> {
            Thread thread = new Thread(runnable);
            thread.setDaemon(true);
            return thread;
        });
        cleaner.scheduleAtFixedRate(this::cleanExpiredSessions, 1, 10, TimeUnit.SECONDS);
    }

    public Session createSession() {
        Session session = new Session();
        sessionMap.put(session.getSessionId(), session);
        return session;
    }

    public Session getSession(String sessionId) {
        if (sessionId == null) return null;

        Session session = sessionMap.get(sessionId);

        if (session != null) {
            if (isExpired(session)) {
                invalidate(sessionId);
                return null;
            }
            session.updateLastAccessedAt();
        }
        return session;
    }

    public void invalidate(String sessionId) {
        if (sessionId != null) {
            sessionMap.remove(sessionId);
        }
    }

    private boolean isExpired(Session session) {
        return (System.currentTimeMillis() - session.getLastAccessedAt()) > MAX_INACTIVE_INTERVAL;
    }

    private void cleanExpiredSessions() {
        long now = System.currentTimeMillis();
        System.out.println("지금은 : " + now);

        sessionMap.entrySet().stream()
            .filter(entry -> (now - entry.getValue().getLastAccessedAt()) >= MAX_INACTIVE_INTERVAL) // 접속안한지 10초 넘으면 지움
            .peek(entry -> System.out.println("만료 세션 : " + entry.getValue().getSessionId()))
            .map(Map.Entry::getKey)
            .toList()
            .forEach(sessionMap::remove);
    }
}
