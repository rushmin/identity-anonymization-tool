package org.wso2.carbon.privacy.forgetme.rest;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.wso2.carbon.privacy.forgetme.ForgetMeTool;
import org.wso2.msf4j.MicroservicesRunner;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.ByteBuffer;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;
import java.nio.channels.WritableByteChannel;

/**
 * The main class which stars the API server.
 */
public class Application {

    private static final Log log = LogFactory.getLog(Application.class);

    private static final String CMD_OPTION_CONFIG_DIR = "d";
    private static final String CMD_OPTION_SERVER_PORT = "p";
    private static final String CMD_OPTION_HELP = "help";
    public static final int DEFAULT_SERVER_PORT = 8000;

    public static void main(String[] args) {

        try {
            Options options = new Options();

            options.addOption(CMD_OPTION_CONFIG_DIR, true,
                    "Directory where config.json file located (mandatory)");
            options.addOption(CMD_OPTION_SERVER_PORT, true,
                    "The port which the server should listen to (optional). Default - 8000");
            options.addOption(CMD_OPTION_HELP, false, "Help");

            CommandLineParser parser = new DefaultParser();
            CommandLine cmd = null;

            cmd = parser.parse(options, args);

            if (cmd.hasOption(CMD_OPTION_HELP)) {
                emitHelp(System.out);
                return;
            }

            String configDirectoryPath = cmd.getOptionValue(CMD_OPTION_CONFIG_DIR);
            UserService userService = new UserService(configDirectoryPath);

            int serverPort = DEFAULT_SERVER_PORT;

            if (cmd.hasOption(CMD_OPTION_SERVER_PORT)) {
                serverPort = Integer.parseInt(cmd.getOptionValue(CMD_OPTION_SERVER_PORT));
            }

            MicroservicesRunner microservicesRunner = new MicroservicesRunner(serverPort);
            microservicesRunner.deploy(userService).start();

        } catch (Exception e) {
            log.error("An error occurred while starting API server.", e);
            System.exit(1);
        }

    }

    /**
     *
     * Writes the help content to the output stream.
     *
     * @param out
     */
    private static void emitHelp(PrintStream out) {

        InputStream inputStream = ForgetMeTool.class.getClassLoader().getResourceAsStream("help.txt");
        ReadableByteChannel readableByteChannel = Channels.newChannel(inputStream);
        WritableByteChannel writableByteChannel = Channels.newChannel(out);
        ByteBuffer buffer = ByteBuffer.allocateDirect(512);
        try {
            while (readableByteChannel.read(buffer) != -1) {
                buffer.flip();
                while (buffer.hasRemaining()) {
                    writableByteChannel.write(buffer);
                }
                buffer.clear();
            }
        } catch (IOException e) {
            log.error("Could not read the help file.");
        }
    }

}
