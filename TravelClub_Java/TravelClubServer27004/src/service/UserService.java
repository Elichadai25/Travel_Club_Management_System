/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import model.User;

/**
 *
 * @user elichadai
 */
public interface UserService extends Remote{
    // method signature/ definition
    User registerUserRecord(User theUser) throws RemoteException;
    User updateUserRecord(User theUser) throws RemoteException;
    User deleteUserRecord(User theUser) throws RemoteException;
    User findUserRecordById(User theUser) throws RemoteException;
    List<User> findAllUserRecords() throws RemoteException;    
}
