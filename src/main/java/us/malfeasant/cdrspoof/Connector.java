package us.malfeasant.cdrspoof;

import com.fazecast.jSerialComm.SerialPort;

public abstract class Connector {
    private Connector() {}

    public static class Serial extends Connector {
        public final SerialPort port;

        /**
         * Represents the Serial connection mode
         * @param port which serial port to listen on
         */
        public Serial(SerialPort port) {
            if (port == null) throw new IllegalArgumentException("Serial port must not be null.");
            this.port = port;
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
    }
}
