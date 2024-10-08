package ru.luckyskeet.sharehub.request;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import ru.luckyskeet.sharehub.request.model.Request;
import ru.luckyskeet.sharehub.user.UserRepository;
import ru.luckyskeet.sharehub.user.model.User;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class RequestRepositoryTest {

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        User user = new User()
                .setEmail("ya@ya.ru")
                .setName("name");
        userRepository.save(user);
    }

    @Test
    @DirtiesContext
    public void findAllByRequesterIdTest() {
        User user = userRepository.findById(1L).get();
        List<Request> requestList = requestRepository.findAllByRequesterId(1);
        assertTrue(requestList.isEmpty());

        Request request = new Request()
                .setRequester(user)
                .setDescription("I need ball")
                .setCreated(LocalDateTime.now());
        requestRepository.save(request);
        List<Request> oneRequestList = requestRepository.findAllByRequesterId(1);
        assertEquals(1, oneRequestList.size());

        Request secondRequest = new Request()
                .setRequester(user)
                .setDescription("I need box")
                .setCreated(LocalDateTime.now());
        requestRepository.save(secondRequest);
        List<Request> twoRequestList = requestRepository.findAllByRequesterId(1);
        assertEquals(2, twoRequestList.size());
        List<Request> empthyRequestList = requestRepository.findAllByRequesterId(2);
        assertTrue(empthyRequestList.isEmpty());
    }
}