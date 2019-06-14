//package com.ega.datarepositorysevice.repository;
//
//import com.ega.datarepositorysevice.model.Bundle;
//import com.ega.datarepositorysevice.model.Object;
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
//import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.time.LocalDateTime;
//import java.time.OffsetDateTime;
//import java.time.ZoneOffset;
//import java.util.Arrays;
//import java.util.TimeZone;
//
//@RunWith(SpringRunner.class)
//@DataJpaTest
//@AutoConfigureTestDatabase
//public class ObjectRepositoryTest {
//
//    @Autowired
//    ObjectRepository objectRepository;
//
//    @Test
//    public void getBundleByIdTest() throws ParseException {
//        LocalDateTime testDateTime = LocalDateTime.of(2018, 12, 12, 12, 12, 12, 121200000);
//        OffsetDateTime date = OffsetDateTime.of(testDateTime, ZoneOffset.ofHours(2));
//        Object object = new Object(Long.parseLong("1"), "string", 0, null,  date, "string", "application/json",
//                null, null, "string", null);
//        objectRepository.save(object);
//        Object responseObject = objectRepository.findById("id").get();
//        Assert.assertEquals("1", responseObject.getId());
//        Assert.assertEquals("string", responseObject.getName());
//        Assert.assertEquals(0, responseObject.getSize());
//    }
//}
