package us.malfeasant.cdrspoof;

import org.tinylog.Logger;

import com.fazecast.jSerialComm.SerialPort;

public abstract class Connector {
    private Connector() {}

    public static class Serial extends Connector {
        public final SerialPort port;

        /**
         * Represents the Serial connection mode
         * @param port which serial port to listen on
         * @param baud Baud rate - picked from a ChoiceBox, so shouldn't be arbitrary...
         * @param stopBits - From SerialPort's constants
         * @param dataBits - From SerialPort's constants
         * @param parity - From SerialPort's constants
         */
        public Serial(SerialPort port, int baud, int stopBits, int dataBits, int parity) {
            if (port == null) throw new IllegalArgumentException("Serial port must not be null.");
            this.port = port;
            
            if (!port.openPort()) {
                throw new IllegalStateException("Opening serial port failed.");
            }
            if (!port.setComPortParameters(baud, dataBits, stopBits, parity)) {
                throw new IllegalStateException("Setting port parameters failed.");
            }

            Logger.info("Opened port {} at {} baud.", port, baud);
        }

        @Override
        public void disconnect() {
            port.closePort();
            Logger.info("Closed port {}.", port);
        }
    }

    public static class IP extends Connector {
        public final int port;

        /**
         * Represents the IP connection mode- should we specify a physical interface?
         * @param port which TCP port to listen on
         */
        public IP(int port) {
            if (port <= 0 || port > 0xffff) throw new IllegalArgumentException("Bad port number.  Must be 1-65535.");
            this.port = port;
        }

        @Override
        public void disconnect() {
            // TODO
        }
    }
    

    public abstract void disconnect();
}
