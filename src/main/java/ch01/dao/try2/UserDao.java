package ch01.dao.try2;

import ch01.dao.User;
import ch01.dao.try1.SimpleConnectionMaker;

import java.sql.Connection;
import java.sql.SQLException;

public class UserDao{
        private ConnectionMaker connectionMaker; // interface 오브젝트에 접근

        public UserDao(){
            // 문제 되는 부분. D사 클래스 생성자를 호출해 오브젝트를 생성하는 코드가 남아있다. (문제상황)
            // 관심사 분리가 필요하다.
            // 관심사: ConnectionMaker 구현 클래스의 오브젝트를 이용하게 할 지 결정
            // 즉, UserDao 와 UserDao가 사용할 ConnectionMaker 의 특정 구현 클래스 사이의 관계를 설정해주는 것에 대한 관심
            connectionMaker = new DConnectionMaker();
            // 두개의 오브젝트(UserDao 인스턴스, DConnectionMaker 인스턴스)가 사용이라는 관계를 맺는다.
        }

        public void add(User user) throws ClassNotFoundException, SQLException {
            Connection c = connectionMaker.makeConnection();
            // 인터페이스에 정의된 메소드를 가져온다.
            // ...
        }

        public User get(String id) throws ClassNotFoundException, SQLException{
            Connection c = connectionMaker.makeConnection();
            // ...
            return new User();
        }
}
