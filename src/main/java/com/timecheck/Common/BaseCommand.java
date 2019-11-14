package com.timecheck.Common;

public class BaseCommand {
    public static String USER_VERIFICATION = "echo '%s'|sudo -S ";
    public static String BASE_DOWNLOAD_COMMAND = "wget -P %s %s";
    public static String BASE_FIND_FILE_COMMAND = "find %s -name %s";
    public static String BASE_DELETE_FILE_COMMAND = "rm -rf %s";
    public static String WHO_AM_I = "whoami";
    public static String RENAME_FILE = "mv %s %s";

    //WebSphere
    public static String BASE_WEBSPHERE_DIR = "/opt/IBM/WebSphere/AppServer/profiles/AppSrv01/bin";
    public static String BASE_WEBSPHERE_COMMAND = "/opt/IBM/WebSphere/AppServer/profiles/AppSrv01/bin/wsadmin.sh";
    public static String BASE_SERVER_START_COMMAND = "/opt/IBM/WebSphere/AppServer/profiles/AppSrv01/bin/startServer.sh %s";
    public static String BASE_SERVER_STOP_COMMAND = "/opt/IBM/WebSphere/AppServer/profiles/AppSrv01/bin/stopServer.sh %s";
    public static String GET_SERVER_STATUS = "/opt/IBM/WebSphere/AppServer/profiles/AppSrv01/bin/serverStatus.sh %s";

    public static String GET_ARCHIVE_NAME = "AdminApp.listModules('%s')";
}
