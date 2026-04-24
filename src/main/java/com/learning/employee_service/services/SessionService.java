package com.learning.employee_service.services;

import com.learning.employee_service.entities.Employee;
import com.learning.employee_service.entities.Session;
import com.learning.employee_service.repositories.SessionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.web.authentication.session.SessionAuthenticationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SessionService {

    private final SessionRepository sessionRepository;

    private final int SESSION_LIMIT = 4;

    public void generateNewSession(Employee employee, String refreshToken){
        List<Session> userSessions = sessionRepository.findByEmployee(employee);
        if(userSessions.size() == SESSION_LIMIT){
            userSessions.sort(Comparator.comparing(Session::getLastUsedAt));
            Session leastRecentlyUsedSession = userSessions.getFirst();
            sessionRepository.delete(leastRecentlyUsedSession);
        }

        Session newSession = Session.builder()
                .employee(employee)
                .refreshToken(refreshToken)
                .build();
        sessionRepository.save(newSession);
    }

    public void validSession(String refreshToken){
        Session session = sessionRepository.findByRefreshToken(refreshToken).orElseThrow(
                () -> new SessionAuthenticationException("Session not found with refresh token: "+refreshToken)
        );
        session.setLastUsedAt(LocalDateTime.now());
        sessionRepository.save(session);
    }
}
