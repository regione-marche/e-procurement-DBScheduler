package it.maggioli.eldasoft.dbscheduler.utility.sftp;

public class SftpCallBuilder {

    private SftpConnectionData connectionData = new SftpConnectionData();
    private SftpCall           call           = new SftpCall(connectionData);

    public SftpCallBuilder() { }

    public SftpCallBuilder setUsername(String username) {
        connectionData.setUsername(username);
        return this;
    }
    public SftpCallBuilder setPassword(String password) {
        connectionData.setPassword(password);
        return this;
    }
    public SftpCallBuilder setHost(String host) {
        connectionData.setHost(host);
        return this;
    }
    public SftpCallBuilder setPort(int port) {
        connectionData.setPort(port);
        return this;
    }
    public SftpCallBuilder setCallType(String callType) {
        call.setCallType(callType);
        return this;
    }
    public SftpCallBuilder setDestinationPath(String destinationPath) {
        call.setDestinationPath(destinationPath);
        return this;
    }
    public SftpCallBuilder addFilePathToMove(String filePathTomove) {
        call.addFilePathToMove(filePathTomove);
        return this;
    }

    public SftpCall build() {
        return call;
    }

}
