/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;
import model.Notification;

/**
 *
 * @notification elichadai
 */
public interface NotificationService extends Remote{
    // method signature/ definition
    Notification registerNotificationRecord(Notification theNotification) throws RemoteException;
    Notification updateNotificationRecord(Notification theNotification) throws RemoteException;
    Notification deleteNotificationRecord(Notification theNotification) throws RemoteException;
    Notification findNotificationRecordById(Notification theNotification) throws RemoteException;
    List<Notification> findAllNotificationRecords() throws RemoteException;    
}
