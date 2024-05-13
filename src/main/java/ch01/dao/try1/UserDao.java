package ch01.dao.try1;

import ch01.dao.User;

import java.sql.Connection;
import java.sql.SQLException;

// simpleConnectionMaker 에 종속되었다
// - 상속할 때 해당 클래스 코드 수정 없이 DB 커낵션 생성 기능 변경할 수 없다.
public class UserDao {
    private SimpleConnectionMaker simpleConnectionMaker;

    public UserDao(){
        simpleConnectionMaker = new SimpleConnectionMaker();
    }

    public void add(User user) throws ClassNotFoundException, SQLException {
        Connection c = simpleConnectionMaker.makeNewConnection(); // DB 커넥션을 가져오는 부분
        // 만약 연결하는 서비스의 제공 클래스가 openConnection() 이면 add, get 모두 내부 코드를 일일이 변경해야 한다.(이름이 다르니까)
        // simpleConnectionMaker.openConnection()
        // 처럼
        // ...
    }

    public User get(String id) throws ClassNotFoundException, SQLException{
        Connection c = simpleConnectionMaker.makeNewConnection();
        // ...
        return new User();
    }
}
