package org.miniTT.mall.user.session.impl;

import org.miniTT.mall.user.session.SessionStore;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class SessionStoreImpl implements SessionStore {

    private final Map<Long, String> sessions = new HashMap<>();

    @Override
    public String getToken(Long userId) {
        return sessions.get(userId);
    }

    @Override
    public void addSession(Long userId, String token) {
        sessions.put(userId, token);
    }

    @Override
    public void removeSession(Long userId) {
        sessions.remove(userId);
    }
}
