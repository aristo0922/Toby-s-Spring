package user.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

//@ExtendWith(SpringExtension.class)
//@ContextConfiguration(classes = {DaoForTest.class})
//@DirtiesContext
class UserTest {

  User user;

  @BeforeEach
  void setUp(){
    user = new User();
  }

  @Test
  void upgradeLevel(){
    Level[] levels = Level.values();
    for (Level level: levels){
      if (level.nextLevel() == null) continue;
      upgrade(level);
      Assertions.assertEquals(level.nextLevel(), user.getLevel());
    }
  }

  @Test
  void cannotUpgradeLevel(){
    Level[] levels = Level.values();
    for (Level level: levels){
      if (level.nextLevel() != null) continue;
      Assertions.assertThrows(IllegalStateException.class, () -> upgrade(level));
    }
  }

  private void upgrade(Level level){
    user.setLevel(level);
    user.upgradeLevel();
  }
}