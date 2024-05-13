package ch01.dao.try3;

import ch01.dao.try2.ConnectionMaker;

public class UserDao {
    ConnectionMaker connectionMaker;
    // client 가 만든 connectionMaker 를 매개변수로 오브젝트 전달
    public UserDao(ConnectionMaker connectionMaker){
        this.connectionMaker = connectionMaker;
    }
}
