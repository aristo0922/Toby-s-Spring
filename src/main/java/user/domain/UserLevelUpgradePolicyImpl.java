package user.domain;

import static user.service.UserService.MIN_LOGCOUNT_FOR_SILVER;
import static user.service.UserService.MIN_RECCOMEND_FOR_GOLD;

import org.springframework.stereotype.Component;

@Component
public class UserLevelUpgradePolicyImpl implements UserLevelUpgradePolicy{

  @Override
  public boolean canUpgradeLevel(User user){
    Level currentLevel = user.getLevel();
    switch(currentLevel){
      case BASIC: return (user.getLogin() >= MIN_LOGCOUNT_FOR_SILVER);
      case SILVER: return (user.getRecommend() >= MIN_RECCOMEND_FOR_GOLD);
      case GOLD: return false;
      default: throw new IllegalArgumentException("Unknown Level: "+ currentLevel);
    }
  }

  @Override
  public void upgradeLevel(User user){
    user.upgradeLevel();
  }
}
