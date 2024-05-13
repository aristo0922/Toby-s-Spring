package ch01.dao.try2;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws ClassNotFoundException, SQLException{

        ConnectionMaker connectionMaker = new DConnectionMaker(); // 혹은 NConnectionMaker
    }
}
