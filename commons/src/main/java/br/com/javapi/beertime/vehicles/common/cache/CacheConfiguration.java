package br.com.javapi.beertime.vehicles.common.cache;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

import com.hazelcast.config.Config;
import com.hazelcast.config.GroupConfig;
import com.hazelcast.config.JoinConfig;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.MulticastConfig;
import com.hazelcast.config.NetworkConfig;
import com.hazelcast.config.TcpIpConfig;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

import br.com.javapi.beertime.vehicles.common.bean.Field;

@Configuration
public class CacheConfiguration {
    private static final Logger LOGGER = LoggerFactory.getLogger(CacheConfiguration.class);

    @Value("${hazelcast.config.group.name:}")
    private String group;

    @Value("${hazelcast.config.group.password:}")
    private String password;

    @Value("${hazelcast.config.network.port:5705}")
    private int port;

    @Value("${hazelcast.config.network.port-auto-increment:true}")
    private boolean portAutoIncrement;

    @Value("${hazelcast.config.network.multicast.enabled:false}")
    private boolean multicastEnabled;

    @Value("${hazelcast.config.network.multicast.interfaces:false}")
    private String[] multicastInterfaces;

    @Value("${hazelcast.config.network.multicast.port:0}")
    private int multicastPort;

    @Value("${hazelcast.config.network.multicast.group:group}")
    private String multicastGroup;

    @Value("${hazelcast.config.network.multicast.loopback-mode:false}")
    private boolean multicastLoopbackMode;

    @Value("${hazelcast.config.network.tcp-ip.enabled:false}")
    private boolean tcpIpEnabled;

    // Comma separated
    @Value("${hazelcast.config.network.tcp-ip.members:127.0.0.1}")
    private String[] tcpMembers;
    
    @Autowired
    private ApplicationContext context;

    @Bean
    public Config configureHazelcast() {
        final Config config = new Config();
        if (!StringUtils.isEmpty(group)) {
            LOGGER.info("Hazelcast: group configuration ENABLED {} {}", group, password);
            final GroupConfig groupConfig = new GroupConfig(group);
            groupConfig.setPassword(password);
            config.setGroupConfig(groupConfig);
        }
        final NetworkConfig networkConfig = new NetworkConfig();
        networkConfig.setPort(port);
        networkConfig.setPortAutoIncrement(portAutoIncrement);
        LOGGER.info("Hazelcast: Port configured {} auto increment {}", port, portAutoIncrement);
        final JoinConfig joinConfig = networkConfig.getJoin();
        joinConfig.getMulticastConfig().setEnabled(false);
        joinConfig.getTcpIpConfig().setEnabled(false);
        if (tcpIpEnabled) {
            joinConfig.getTcpIpConfig().setEnabled(true);
            LOGGER.info("Hazelcast: TCP IP joiner {}", Arrays.asList(tcpMembers).stream().collect(Collectors.joining(",")));
            TcpIpConfig tcpIpConfig = joinConfig.getTcpIpConfig();
            if (tcpMembers != null) {
                Stream.of(tcpMembers).forEach(member -> tcpIpConfig.addMember(member));
            }
        } else if (multicastEnabled) {
            joinConfig.getMulticastConfig().setEnabled(true);
            LOGGER.info("Hazelcast: Multicast joiner {}:{} loopback mode={} interfaces {} ",
                                    multicastGroup,
                                    multicastPort,
                                    multicastLoopbackMode,
                                    multicastInterfaces);
            MulticastConfig multicastConfig = joinConfig.getMulticastConfig();
            multicastConfig.setLoopbackModeEnabled(multicastLoopbackMode);
            if (multicastGroup != null) {
                multicastConfig.setMulticastGroup(multicastGroup);
            }
            if (multicastPort > 0) {
                multicastConfig.setMulticastPort(multicastPort);
            }
            if (multicastInterfaces != null) {
                Stream.of(multicastInterfaces).forEach(ip -> multicastConfig.addTrustedInterface(ip));
            }
        }
        return config;
    }
    
    @Bean(name="fieldsMap")
    public IMap<String, Field> createBatchOperationsMap() {
        HazelcastInstance instance = context.getBean(HazelcastInstance.class);
        instance.getConfig().addMapConfig(new MapConfig("fieldsMap"));
        return instance.getMap("fieldsMap");
    }
}
