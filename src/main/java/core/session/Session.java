package core.session;

import java.util.UUID;

/**
 *
 * Package Name: core.model
 * File Name: Session
 * Description:
 * author: munke
 *
 * @version 1.0
 * @see core.model
 * @since 2026-07-30
 * <p>
 * Modification Information
 * 수정일          수정자                    수정내용
 * --------- ------------------- -------------------------------
 * 2026-07-30        munke                   최초개정
 */
public class Session {
    private final String sessionId;
    private final long createdAt;
    private long lastAccessedAt;

    public Session(){
        this.sessionId = UUID.randomUUID().toString();
        this.createdAt = System.currentTimeMillis();
        this.lastAccessedAt = this.createdAt;
    }

    public String getSessionId() {
        return this.sessionId;
    }

    public long getLastAccessedAt() {
        return this.lastAccessedAt;
    }

    public void updateLastAccessedAt() {
        this.lastAccessedAt = System.currentTimeMillis();
    }
}
