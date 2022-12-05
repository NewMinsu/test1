package dev.mvc.cate;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

//@RunWith(SpringJUnit4ClassRunner.class)
// @ContextConfiguration(locations = {"file:src/main/resources/mybatis/**/*.xml" })
// C:\kd\ws_java\resort_v1sbm3c\src\main\resources\mybatis\cate.xm
@ContextConfiguration(locations = {"file:C:\\kd\\ws_java\\resort_v1sbm3c\\src\\main\\resources\\mybatis\\cate.xm" })
@SpringBootTest
public class CateProcTest {
 
  @Autowired
  @Qualifier("dev.mvc.cate.CateProc") // 할당되는 Class 객체의 이름
  private CateProc cateProc = null;
 
  @Test
  public void testUpdate_visible_y() throws Exception {
 
    int cnt = this.cateProc.update_visible_y(1);
    System.out.println("-> update_visible_y: " + cnt);
   
  }

  @Test
  public void testUpdate_visible_n() throws Exception {
 
    int cnt = this.cateProc.update_visible_n(1);
    System.out.println("-> update_visible_n: " + cnt);
   
  }

}





