package dao;

import entity.Session;

import java.util.Optional;

public class SessionDao {
    public Optional<Session> save(Session session) {
        return Optional.ofNullable(session);
    }
}
