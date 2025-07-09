package it.maggioli.eldasoft.dbscheduler.utility.sftp;

import com.jcraft.jsch.ChannelSftp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class SftpCall {

    private static final Logger log = LoggerFactory.getLogger(SftpCall.class);

    private SftpConnectionData connectionData;
    private String callType;
    private String destinationPath;
    private List<String> filePathToMove = new ArrayList<>();

    SftpCall(SftpConnectionData connectionData) {
        this.connectionData = connectionData;
    }

    public void call() {
        if ("DOWNLOAD".equalsIgnoreCase(callType))
            downloadFile();
        else if("UPLOAD".equalsIgnoreCase(callType))
            uploadFile();
    }

    private void downloadFile() {
        log.debug("START - Download file");

        try (AutoChannel channel = new AutoChannel(connectionData)) {
            channel.getChannel().connect();
            ChannelSftp channelSftp = (ChannelSftp) channel.getChannel();

            filePathToMove.forEach(toGet -> get(toGet, channelSftp));
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

        log.debug("END - Download file");
    }

    private void uploadFile() {
        log.debug("START - Upload file");

        try (AutoChannel channel = new AutoChannel(connectionData)) {
            channel.getChannel().connect();
            ChannelSftp channelSftp = (ChannelSftp) channel.getChannel();

            filePathToMove.forEach(toPut -> put(toPut, channelSftp));
        } catch (Exception ex) {
            log.error("Error during the upload", ex);
            throw new RuntimeException(ex);
        }

        log.debug("END - Upload file");
    }

    private void get(String toGet, ChannelSftp channelSftp) {
        try {
            OutputStream output = Files.newOutputStream(Paths.get(destinationPath + getFileName(toGet)));

            channelSftp.get(toGet, output);
            output.close();
        } catch (Exception e) {
            log.error("Error while downloading the file: " + toGet, e);
            throw new RuntimeException(e);
        }
    }

    private void put(String toPut, ChannelSftp channelSftp) {
        try {
            channelSftp.put(Files.newInputStream(Paths.get(toPut)), destinationPath + getFileName(toPut));
        } catch (Exception e) {
            log.error("Error while uploading the file: " + toPut, e);
            throw new RuntimeException(e);
        }
    }

    private String getFileName(String filePath) {
        int idx = filePath.replaceAll("\\\\", "/").lastIndexOf("/");
        return idx >= 0 ? filePath.substring(idx + 1) : filePath;
    }

    public String getCallType() {
        return callType;
    }

    public void setCallType(String callType) {
        this.callType = callType;
    }

    public String getDestinationPath() {
        return destinationPath;
    }

    public void setDestinationPath(String destinationPath) {
        this.destinationPath = destinationPath;
    }
    public void addFilePathToMove(String toMove) {
        filePathToMove.add(toMove);
    }
}
