package com.qin.rmi;

import java.rmi.Remote;
import java.rmi.RemoteException;

/**
 * Created by DELL on 2017/11/20.
 */
public interface SoaRmi extends Remote {

    String invoke(String param) throws RemoteException;
}
