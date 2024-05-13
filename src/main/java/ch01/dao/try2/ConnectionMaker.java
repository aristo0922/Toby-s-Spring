package ch01.dao.try2;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionMaker { // 인터페이스로 변경
    public Connection makeConnection() throws ClassNotFoundException, SQLException;
}
