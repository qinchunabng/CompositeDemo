package com.qin.configBean;

import com.qin.cluster.Cluster;
import com.qin.cluster.FailfastClusterInvoke;
import com.qin.cluster.FailoverClusterInvoke;
import com.qin.cluster.FailsafeClusterInvoke;
import com.qin.invoke.HttpInvoke;
import com.qin.invoke.Invoke;
import com.qin.invoke.NettyInvoke;
import com.qin.invoke.RmiInvoke;
import com.qin.loadbalance.LoadBalance;
import com.qin.loadbalance.RandomLoadBalance;
import com.qin.loadbalance.RoundRobinLoadBalance;
import com.qin.proxy.advice.InvokeInvocationHandler;
import com.qin.redis.RedisApi;
import com.qin.redis.RedisServer;
import com.qin.registry.BaseRegistryDelegate;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.lang.reflect.Proxy;
import java.util.*;

/**
 * Created by DELL on 2017/11/13.
 */
public class Reference implements Serializable,FactoryBean,ApplicationContextAware,InitializingBean{

    private static final long serialVersionUID = -4354884389533911735L;
    private String id;
    private String intf;
    private String protocol;
    private String loadbalance;
    private Integer retries;
    private String cluster;

    private Invoke invoke;
    private ApplicationContext applicationContext;
    private static Map<String,Invoke> invokeMap=new HashMap<String, Invoke>();

    /**
     * 生产者调用列表
     */
    private static Set<String> registryInfo=Collections.synchronizedSet(new HashSet<String>());

    private static Map<String,LoadBalance> loadBalanceMap=new HashMap<String, LoadBalance>();

    private static Map<String,Cluster> clusterMap=new HashMap<>();

    static {
        invokeMap.put("http",new HttpInvoke());
        invokeMap.put("rmi",new RmiInvoke());
        invokeMap.put("netty",new NettyInvoke());

        loadBalanceMap.put("random",new RandomLoadBalance());
        loadBalanceMap.put("roundrob",new RoundRobinLoadBalance());

        clusterMap.put("failover",new FailoverClusterInvoke());
        clusterMap.put("failfast",new FailfastClusterInvoke());
        clusterMap.put("failsafe",new FailsafeClusterInvoke());
    }

    public Reference(){
        System.out.println("Reference构造函数");
    }

    /**
     * spring初始化时调用的，具体是getBean方法里面调用
     * ApplicationContext.getBean("id")
     * 在这个方法返回的是intf接口的代理
     * @return 这个方法的返回值会交给spring容器进行管理
     */
    public Object getObject() throws Exception {
        System.out.println("返回intf的代理对象");
        if(StringUtils.isEmpty(protocol)){
            Protocol protocol = applicationContext.getBean(Protocol.class);
            if(protocol!=null){
                invoke=invokeMap.get(protocol.getName());
            }else{
                //默认协议
                invoke=invokeMap.get("http");
            }
        }else{
            invoke=invokeMap.get(protocol);
        }
        return Proxy.newProxyInstance(this.getClass().getClassLoader(),new Class<?>[]{Class.forName(intf)},
                new InvokeInvocationHandler(invoke,this));
    }


    public Class<?> getObjectType() {
        if(!StringUtils.isEmpty(intf)){
            try {
                return Class.forName(intf);
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public boolean isSingleton() {
        return true;
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext=applicationContext;
    }

    public void afterPropertiesSet() throws Exception {
        List<String> list = BaseRegistryDelegate.getRegistryInfo(id,applicationContext);
        registryInfo.addAll(list);
        System.out.println(registryInfo);

        RedisApi.subscribe(new RedisServer(),"channel_"+id);
    }

    /**
     * 根据负载均衡名称获取负载均衡算法
     * @param loadbalance
     * @return
     */
    public LoadBalance getLoadBalance(String loadbalance){
        LoadBalance loadBalance = Reference.loadBalanceMap.get(loadbalance);
        if(loadBalance==null){
            loadBalance=new RandomLoadBalance();
        }
        return loadBalance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIntf() {
        return intf;
    }

    public void setIntf(String intf) {
        this.intf = intf;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String port) {
        this.protocol = port;
    }

    public String getLoadbalance() {
        return loadbalance;
    }

    public void setLoadbalance(String loadbalance) {
        this.loadbalance = loadbalance;
    }

    public Integer getRetries() {
        return retries;
    }

    public void setRetries(Integer retries) {
        this.retries = retries;
    }

    public String getCluster() {
        return cluster;
    }

    public void setCluster(String cluster) {
        this.cluster = cluster;
    }

    public static Set<String> getRegistryInfo(){
        return Reference.registryInfo;
    }

    public Cluster getClusterInvoke(){
        return Reference.clusterMap.get(cluster);
    }
}
