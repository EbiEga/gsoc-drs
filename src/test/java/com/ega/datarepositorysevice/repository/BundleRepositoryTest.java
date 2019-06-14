//package com.ega.datarepositorysevice.repository;
//
//
//import com.ega.datarepositorysevice.model.Bundle;
//import com.ega.datarepositorysevice.model.BundleObject;
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
//public class BundleRepositoryTest {
//
//    @Autowired
//    BundleRepository bundleRepository;
//
//    @Test
//    public void getBundleByIdTest() throws ParseException {
//        LocalDateTime testDateTime = LocalDateTime.of(2018, 12, 12, 12, 12, 12, 121200000);
//        OffsetDateTime date = OffsetDateTime.of(testDateTime, ZoneOffset.ofHours(2));
//        Bundle bundle = new Bundle(Long.parseLong("1"),"string",23,date,
//                date, "string", null,"string",
//                Arrays.asList("string"), Arrays.asList() );
//
//        bundleRepository.save(bundle);
//        Bundle responseBundle = bundleRepository.findById(Long.valueOf(1)).get();
//        Assert.assertEquals(Long.valueOf(1),responseBundle.getId());
//        Assert.assertEquals("string", responseBundle.getName());
//        Assert.assertEquals(23, responseBundle.getSize());
//    }
//}
