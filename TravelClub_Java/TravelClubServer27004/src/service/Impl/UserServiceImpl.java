/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.Impl;

import dao.UserDao;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import model.User;
import service.UserService;

/**
 *
 * @user elichadai
 */
public class UserServiceImpl extends UnicastRemoteObject implements UserService{

    // call dao to persist data
    UserDao dao = new UserDao();
    
    public UserServiceImpl() throws RemoteException{
        
    }
    
    @Override
    public User registerUserRecord(User theUser) throws RemoteException {
        return dao.registerUser(theUser);
    }

    @Override
    public User updateUserRecord(User theUser) throws RemoteException {
        return dao.updateUser(theUser);
    }

    @Override
    public User deleteUserRecord(User theUser) throws RemoteException {
        return dao.deleteUser(theUser);
    }

    @Override
    public User findUserRecordById(User theUser) throws RemoteException {
        return dao.findUserById(theUser);
    }

    @Override
    public List<User> findAllUserRecords() throws RemoteException {
        return dao.findAllUsers();
    }
    
}
