//package dk.kvalitetsit.hello.dao;
//
//import dk.kvalitetsit.hello.dao.entity.Result;
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.util.UUID;
//
//import static org.junit.Assert.*;
//
//public class HelloDaoTest extends AbstractDaoTest {
//    @Autowired
//    private TrivyDao helloDao;
//
//    @Test
//    public void testByMessageId() {
//        var input = new Result();
//        input.setName(UUID.randomUUID().toString());
//
//        helloDao.insert(input);
//
//        var result = helloDao.findAll();
//        assertNotNull(result);
//        assertEquals(1, result.size());
//        assertEquals(input.getName(), result.get(0).getName());
//        assertNotNull(result.get(0).getId());
//    }
//}
