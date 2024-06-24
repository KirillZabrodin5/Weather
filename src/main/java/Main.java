import dao.SessionDao;
import entity.SessionEntity;

import java.time.LocalDateTime;
import java.util.UUID;

public class Main {

    public static void main(String[] args) {
        UUID uuidSession = UUID.randomUUID();

        SessionEntity sessionEntity = new SessionEntity(uuidSession,
                1L, LocalDateTime.now().plusDays(1));
        SessionDao sessionDao = new SessionDao();
        System.out.println(sessionDao.save(sessionEntity).get());
    }
}
