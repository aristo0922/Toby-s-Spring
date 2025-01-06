package user.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import user.domain.DaoForTest;

@ExtendWith(SpringExtension.class) // Spring 테스트 컨텍스트 통합
@ContextConfiguration(classes = {DaoForTest.class})
class UserServiceTest {

  @Autowired
  UserService userService;

  @Test
  public void bean(){
    Assertions.assertNotNull(this.userService);
  }
}