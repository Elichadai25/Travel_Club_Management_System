/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service.Impl;

import dao.NotificationDao;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import model.Notification;
import service.NotificationService;

/**
 *
 * @notification elichadai
 */
public class NotificationServiceImpl1 extends UnicastRemoteObject implements NotificationService{

    // call dao to persist data
    NotificationDao dao = new NotificationDao();
    
    public NotificationServiceImpl1() throws RemoteException{
        
    }
    
    @Override
    public Notification registerNotificationRecord(Notification theNotification) throws RemoteException {
        return dao.registerNotification(theNotification);
    }

    @Override
    public Notification updateNotificationRecord(Notification theNotification) throws RemoteException {
        return dao.updateNotification(theNotification);
    }

    @Override
    public Notification deleteNotificationRecord(Notification theNotification) throws RemoteException {
        return dao.deleteNotification(theNotification);
    }

    @Override
    public Notification findNotificationRecordById(Notification theNotification) throws RemoteException {
        return dao.findNotificationById(theNotification);
    }

    @Override
    public List<Notification> findAllNotificationRecords() throws RemoteException {
        return dao.findAllNotifications();
    }
    
}
