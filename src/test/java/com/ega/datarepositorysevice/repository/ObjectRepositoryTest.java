package com.ega.datarepositorysevice.repository;

import com.ega.datarepositorysevice.model.Object;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Optional;

@RunWith(SpringRunner.class)
@DataJpaTest
@AutoConfigureTestDatabase
@ActiveProfiles("test")
public class ObjectRepositoryTest {

    @Autowired
    ObjectRepository objectRepository;

    @Test
    public void getBundleByIdTest() {
        LocalDateTime testDateTime = LocalDateTime.of(2018, 12, 12, 12, 12, 12, 121200000);
        OffsetDateTime date = OffsetDateTime.of(testDateTime, ZoneOffset.ofHours(2));
        Object object = new Object(1L, "string", 0, null, date, "string", "application/json",
                new ArrayList<>(), new ArrayList<>(), "string", null);
        object = objectRepository.save(object);
        Object responseObject = objectRepository.findById(object.getId()).get();
        Assert.assertEquals(object.getId(), responseObject.getId());
        Assert.assertEquals("string", responseObject.getName());
        Assert.assertEquals(0, responseObject.getSize());
    }

    @Test
    public void getBundleByIdNullTest() {
        LocalDateTime testDateTime = LocalDateTime.of(2018, 12, 12, 12, 12, 12, 121200000);
        OffsetDateTime date = OffsetDateTime.of(testDateTime, ZoneOffset.ofHours(2));
        Object object = new Object(Long.parseLong("5"), "string", 0, null, date, "string", "application/json",
                new ArrayList<>(), new ArrayList<>(), "string", null);
        objectRepository.save(object);
        Optional<Object> responseObject = objectRepository.findById(Long.parseLong("3"));
        Assert.assertTrue(!responseObject.isPresent());
    }
}
