//package com.ega.datarepositorysevice.repository;
//
//import com.ega.datarepositorysevice.model.AccessMethods;
//import com.ega.datarepositorysevice.model.AccessURL;
//import com.ega.datarepositorysevice.model.Bundle;
//import com.ega.datarepositorysevice.model.enums.AccessMethodType;
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
//import java.util.HashMap;
//import java.util.Map;
//import java.util.TimeZone;
//
//@RunWith(SpringRunner.class)
//@DataJpaTest
//@AutoConfigureTestDatabase
//public class AccessMethodRepositoryTest {
//    @Autowired
//    AccessMethodsRepository accessMethodsRepository;
//
//    @Test
//    public void getBundleByIdTest() throws ParseException {
//        LocalDateTime testDateTime = LocalDateTime.of(2018, 12, 12, 12, 12, 12, 121200000);
//        OffsetDateTime date = OffsetDateTime.of(testDateTime, ZoneOffset.ofHours(2));
//        Map<String, String> map = new HashMap<>();
//        map.put("Authorization", "Basic Z2E0Z2g6ZHJz");
//        AccessURL accessURL = new AccessURL("http//www.string.com",map);
//        AccessMethods accessMethods = new AccessMethods(Long.parseLong("1"),AccessMethodType.S3,"region",null, null);
//        accessMethodsRepository.save(accessMethods);
//        AccessMethods responseAccessMethods = accessMethodsRepository.findById("access_id").get();
//        Assert.assertEquals("1", responseAccessMethods.getAccessId());
//        Assert.assertEquals("region", responseAccessMethods.getRegion());
//        Assert.assertEquals("s3", responseAccessMethods.getType());
//    }
//}
