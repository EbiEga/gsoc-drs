import com.ega.datarepositorysevice.model.AccessMethods;
import com.ega.datarepositorysevice.model.Bundle;
import com.ega.datarepositorysevice.model.Error;
import com.ega.datarepositorysevice.model.Object;
import com.sun.org.apache.xml.internal.utils.URI;
import org.junit.Assert;
import org.junit.Before;
import org.junit.ClassRule;
import org.junit.Test;
import reactor.core.publisher.Mono;

public class ClientIntegrationTest {
    private static String host = "http://notworking.notworking"; //TODO change host to real host of drs service
    private ClientDRS clientDRS ;

    @ClassRule
    public static AssumingConnection assumingConnection =
            new AssumingConnection(new ConnectionChecker(host));

    @Before
    public void testPreparing(){
        String token = "";  //TODO add token retreiving
        clientDRS = new ClientDRS(host, token);
    }

    @Test
    public void getObjectOkTest(){
        Mono<Object> savedObjectMono = clientDRS.saveObject(Mono.just(TestObjectCreator.getObject()));
        Object savedObject = savedObjectMono.block();

        Mono responseObjectMono = clientDRS.getObject(savedObject.getId());
        Assert.assertTrue( responseObjectMono.block() instanceof Object);

    }

    @Test
    public void getObjectNotFoundTest(){
        Mono responseObjectMono = clientDRS.getObject(0L);
        try {
            responseObjectMono.block();
            throw new Error("", 0);
        }catch (Error error){
            Assert.assertEquals(error.getStatusCode(),404);
        }

    }


    @Test
    public void getBundleTest() throws URI.MalformedURIException {
        Mono<Bundle> savedBundleMono = clientDRS.saveBundle(Mono.just(TestObjectCreator.getBundle()));
        Bundle savedBundle = savedBundleMono.block();

        Mono responseBundleMono = clientDRS.getBundle(savedBundle.getId());
        Assert.assertTrue( responseBundleMono.block() instanceof Bundle);
    }

    @Test
    public void getBundleNotFoundTest(){
        Mono responseBundleMono = clientDRS.getBundle(0L);
        try {
            responseBundleMono.block();
            throw new Error("", 0);
        }catch (Error error){
            Assert.assertEquals(error.getStatusCode(),404);
        }    }



    @Test
    public void getAccessTest(){
        Mono<Object> savedAccessMethodsMono = clientDRS.saveObject(Mono.just(TestObjectCreator.getObject()));
        Object savedAccessMethods = savedAccessMethodsMono.block();
        AccessMethods methods = savedAccessMethods.getAccessMethods().get(0);
        Mono responseAccessMethodsMono = clientDRS.getAccessMethod(savedAccessMethods.getId(), methods.getAccessId());
        Assert.assertTrue( responseAccessMethodsMono.block() instanceof AccessMethods);
    }

    @Test
    public void getAccessNotFoundTest(){
        Mono responseAccessMethodstMono = clientDRS.getAccessMethod(0L,0L);
        try {
            responseAccessMethodstMono.block();
            throw new Error("", 0);
        }catch (Error error){
            Assert.assertEquals(error.getStatusCode(),404);
        }
    }









    @Test
    public void saveObjectOkTest(){
        Mono savedObjectMono = clientDRS.saveObject(Mono.just(TestObjectCreator.getObject()));
        Assert.assertTrue(savedObjectMono.block() instanceof Object);


    }

    @Test
    public void saveObjectBadRequestTest(){
        Mono savedObjectMono = clientDRS.saveObject(Mono.just(TestObjectCreator.getObject()));

        try {
            savedObjectMono.block();
            throw new Error("", 0);
        }catch (Error error){
            Assert.assertEquals(error.getStatusCode(),400);
        }
    }

    @Test
    public void saveBundleOkTest() throws URI.MalformedURIException {
        Mono savedBundleMono = clientDRS.saveBundle(Mono.just(TestObjectCreator.getBundle()));
        Assert.assertTrue(savedBundleMono.block() instanceof Bundle);
    }

    @Test
    public void saveBundleBadRequestTest() throws URI.MalformedURIException {
        Mono savedBundleMono = clientDRS.saveBundle(Mono.just(TestObjectCreator.getBundle()));
        try {
            savedBundleMono.block();
            throw new Error("", 0);
        }catch (Error error){
            Assert.assertEquals(error.getStatusCode(),400);
        }
    }

    @Test
    public void saveAccessOkTest(){
        Mono<Object> savedObjectMono = clientDRS.saveObject(Mono.just(TestObjectCreator.getObject()));
        Mono savedAccessMono = clientDRS.saveAccessMethod(savedObjectMono.block().getId(),Mono.just(TestObjectCreator.getAccessMethods()));
        Assert.assertTrue(savedAccessMono.block() instanceof AccessMethods);
    }

    @Test
    public void saveAccessBadRequestTest(){
        Mono<Object> savedObjectMono = clientDRS.saveObject(Mono.just(TestObjectCreator.getObject()));
        try {
            savedObjectMono.block();
            throw new Error("", 0);
        }catch (Error error){
            Assert.assertEquals(error.getStatusCode(),400);
        }
    }





    @Test
    public void deleteObjectOkTest(){
        Mono<Void> deleted =  clientDRS.deleteObject(0L);
        deleted.block();

    }

    @Test
    public void deleteObjectNotFoundTest(){
        Mono<Void> deleted =  clientDRS.deleteObject(0L);
        try {
            deleted.block();
            throw new Error("", 0);
        }catch (Error error){
            Assert.assertEquals(error.getStatusCode(),400);
        }
    }


    @Test
    public void deleteBundleOkTest(){
        Mono<Void> deleted =  clientDRS.deleteBundle(0L);
        deleted.block();
    }

    @Test
    public void deleteBundleNotFoundTest(){
        Mono<Void> deleted =  clientDRS.deleteBundle(0L);
        try {
            deleted.block();
            throw new Error("", 0);
        }catch (Error error){
            Assert.assertEquals(error.getStatusCode(),400);
        }
    }


    @Test
    public void deleteAccessOkTest(){
        Mono<Void> deleted =  clientDRS.deleteAccessMethod(0L, 0L);
        deleted.block();
    }

    @Test
    public void deleteAccessNotFoundTest(){
        Mono<Void> deleted =  clientDRS.deleteAccessMethod(0L, 0L);
        try {
            deleted.block();
            throw new Error("", 0);
        }catch (Error error){
            Assert.assertEquals(error.getStatusCode(),400);
        }
    }






    @Test
    public void updateObjectOkTest(){
        Object object = TestObjectCreator.getObject();
        object.setId(0L);
        Mono<Object> updatedObject = clientDRS.updateObject(object.getId(), Mono.just(object));
        Assert.assertTrue(updatedObject.block() instanceof  Object);
    }

    @Test
    public void updateObjectNotFoundTest(){
        Object object = TestObjectCreator.getObject();
        object.setId(0L);
        Mono<Object> updatedObject = clientDRS.updateObject(object.getId(), Mono.just(object));
        try {
            updatedObject.block();
            throw new Error("", 0);
        }catch (Error error){
            Assert.assertEquals(error.getStatusCode(),400);
        }
    }

    @Test
    public void updateObjectBadRequestTest(){
        Object object = TestObjectCreator.getObject();
        object.setId(0L);
        Mono<Object> updatedObject = clientDRS.updateObject(object.getId(), Mono.just(object));
        try {
            updatedObject.block();
            throw new Error("", 0);
        }catch (Error error){
            Assert.assertEquals(error.getStatusCode(),400);
        }
    }


    @Test
    public void updateBundleOkTest(){
        Object object = TestObjectCreator.getObject();
        object.setId(0L);
        Mono<Object> updatedObject = clientDRS.updateObject(object.getId(), Mono.just(object));
        Assert.assertTrue(updatedObject.block() instanceof  Object);
    }

    @Test
    public void updateBundleNotFoundTest(){
        Object object = TestObjectCreator.getObject();
        object.setId(0L);
        Mono<Object> updatedObject = clientDRS.updateObject(object.getId(), Mono.just(object));
        try {
            updatedObject.block();
            throw new Error("", 0);
        }catch (Error error){
            Assert.assertEquals(error.getStatusCode(),400);
        }
    }

    @Test
    public void updateBundleBadRequestTest(){
        Object object = TestObjectCreator.getObject();
        object.setId(0L);
        Mono<Object> updatedObject = clientDRS.updateObject(object.getId(), Mono.just(object));
        try {
            updatedObject.block();
            throw new Error("", 0);
        }catch (Error error){
            Assert.assertEquals(error.getStatusCode(),400);
        }
    }


    @Test
    public void updateAccessOkTest(){
        Object object = TestObjectCreator.getObject();
        object.setId(0L);
        Mono<Object> updatedObject = clientDRS.updateObject(object.getId(), Mono.just(object));
        Assert.assertTrue(updatedObject.block() instanceof  Object);
    }

    @Test
    public void updateAccessNotFoundTest(){
        Object object = TestObjectCreator.getObject();
        object.setId(0L);
        Mono<Object> updatedObject = clientDRS.updateObject(object.getId(), Mono.just(object));
        try {
            updatedObject.block();
            throw new Error("", 0);
        }catch (Error error){
            Assert.assertEquals(error.getStatusCode(),400);
        }
    }

    @Test
    public void updateAccessBadRequestTest(){
        Object object = TestObjectCreator.getObject();
        object.setId(0L);
        Mono<Object> updatedObject = clientDRS.updateObject(object.getId(), Mono.just(object));
        try {
            updatedObject.block();
            throw new Error("", 0);
        }catch (Error error){
            Assert.assertEquals(error.getStatusCode(),400);
        }
    }

    

}
