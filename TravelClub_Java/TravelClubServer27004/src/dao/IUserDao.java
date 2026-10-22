package dao;

import model.User;

public interface IUserDao extends IGenericDao<User> {
    User findByEmail(String email);
    User findByEmailAndPassword(String email, String password);
    boolean emailExists(String email);
    boolean phoneExists(String phone);
}
