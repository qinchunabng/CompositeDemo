package com.qin.rmi;

import com.qin.loadbalance.NodeInfo;

import java.net.MalformedURLException;
import java.rmi.AlreadyBoundException;
import java.rmi.Naming;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;

/**
 * Created by DELL on 2017/11/20.
 */
public class RmiUtil {

    /**
     * 启动rmi服务
     * @param host
     * @param port
     * @param id
     */
    public void startRmiServer(String host,String port,String id){
        try {
            SoaRmi soaRmi=new SoaRmiImpl();
            LocateRegistry.createRegistry(Integer.parseInt(port));
            Naming.bind("rmi://"+host+":"+port+"/"+id,soaRmi);
            System.out.println("RMI server started!");
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (AlreadyBoundException e) {
            e.printStackTrace();
        }

    }

    public SoaRmi startRmiClient(NodeInfo nodeInfo,String id){
        String host=nodeInfo.getHost();
        String port=nodeInfo.getPort();

        try {
            return (SoaRmi) Naming.lookup("rmi://"+host+":"+port+"/"+id);
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
        return null;
    }
}
