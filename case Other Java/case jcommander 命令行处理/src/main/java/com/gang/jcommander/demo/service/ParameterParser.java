package com.gang.jcommander.demo.service;

import com.beust.jcommander.JCommander;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.ParameterException;

/**
 * The type Parameter parser.
 *
 * @author xingfudeshi @gmail.com
 */
public class ParameterParser {

    private static final String PROGRAM_NAME
            = "sh seata-server.sh(for linux and mac) or cmd seata-server.bat(for windows)";

    @Parameter(names = "--help", help = true)
    private boolean help;
    @Parameter(names = {"--host", "-h"}, description = "The ip to register to registry center.", order = 1)
    private String host;
    @Parameter(names = {"--port", "-p"}, description = "The port to listen.", order = 2)
    private int port;
    @Parameter(names = {"--storeMode", "-m"}, description = "log store mode : file, db", order = 3)
    private String storeMode;
    @Parameter(names = {"--serverNode", "-n"}, description = "server node id, such as 1, 2, 3.it will be generated according to the snowflake by default", order = 4)
    private Long serverNode;
    @Parameter(names = {"--seataEnv", "-e"}, description = "The name used for multi-configuration isolation.",
            order = 5)
    private String seataEnv;

    /**
     * Instantiates a new Parameter parser.
     *
     * @param args the args
     */
    public ParameterParser(String[] args) {
        this.init(args);
    }

    private void init(String[] args) {
        try {

            JCommander jCommander = JCommander.newBuilder().addObject(this).build();
            jCommander.parse(args);
            if (help) {
                jCommander.setProgramName(PROGRAM_NAME);
                jCommander.usage();
                System.exit(0);
            }
        } catch (ParameterException e) {
            printError(e);
        }

    }

    private void printError(ParameterException e) {
        System.err.println("Option error " + e.getMessage());
        e.getJCommander().setProgramName(PROGRAM_NAME);
        e.usage();
        System.exit(0);
    }

    /**
     * Gets host.
     *
     * @return the host
     */
    public String getHost() {
        return host;
    }

    /**
     * Gets port.
     *
     * @return the port
     */
    public int getPort() {
        return port;
    }

    /**
     * Gets store mode.
     *
     * @return the store mode
     */
    public String getStoreMode() {
        return storeMode;
    }

    /**
     * Is help boolean.
     *
     * @return the boolean
     */
    public boolean isHelp() {
        return help;
    }

    /**
     * Gets server node.
     *
     * @return the server node
     */
    public Long getServerNode() {
        return serverNode;
    }

    /**
     * Gets seata env
     *
     * @return the name used for multi-configuration isolation.
     */
    public String getSeataEnv() {
        return seataEnv;
    }


    @Override
    public String toString() {
        return "ParameterParser{" +
                "help=" + help +
                ", host='" + host + '\'' +
                ", port=" + port +
                ", storeMode='" + storeMode + '\'' +
                ", serverNode=" + serverNode +
                ", seataEnv='" + seataEnv + '\'' +
                '}';
    }
}
