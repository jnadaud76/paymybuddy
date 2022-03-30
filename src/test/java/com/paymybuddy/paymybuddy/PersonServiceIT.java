package com.paymybuddy.paymybuddy;

import com.paymybuddy.paymybuddy.dto.PersonMailDto;
import com.paymybuddy.paymybuddy.service.IPersonService;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Set;

import javax.validation.constraints.AssertTrue;

@SpringBootTest
public class PersonServiceIT {

    @Autowired
    IPersonService personServiceIT;

    @Test
    void getPossibleConnectionTest() {
        Set<PersonMailDto> result = personServiceIT.getPossibleConnection(86);

        assertTrue(result.isEmpty());
    }
}
