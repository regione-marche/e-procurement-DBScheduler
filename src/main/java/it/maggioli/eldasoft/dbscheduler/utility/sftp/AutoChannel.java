package it.maggioli.eldasoft.dbscheduler.utility.sftp;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AutoChannel implements AutoCloseable {

    private static final Logger log = LoggerFactory.getLogger(AutoChannel.class);

    private Session session;
    private Channel channel;

    AutoChannel(SftpConnectionData dto) {
        createSftpChannel(dto);
    }
    private Channel createSftpChannel(SftpConnectionData dto) {
        try {
            JSch jsch = new JSch();
            session = jsch.getSession(dto.getUsername(), dto.getHost(),
                                      dto.getPort());
            session.setPassword(dto.getPassword());

            session.setConfig("StrictHostKeyChecking", "no");
            session.connect();

            channel = session.openChannel("sftp");
        } catch (Exception e) {
            log.error("Errore durante la creazione della connessione sftp:", e);
            throw new RuntimeException(e);
        }

        return channel;
    }

    public Channel getChannel() {
        return channel;
    }

    @Override
    public void close() throws Exception {
        if (channel != null && channel.isConnected()) {
            session.disconnect();
            channel.disconnect();
        }
    }

}