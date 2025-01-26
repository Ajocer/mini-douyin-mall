package org.miniTT.mall.user.session;

public interface SessionStore {
    String getToken(Long userId);
    void addSession(Long userId, String token);
    void removeSession(Long userId);
}
