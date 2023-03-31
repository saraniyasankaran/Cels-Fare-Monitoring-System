package com._2600tech.gmac.SparklineTempSerial;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;

public class SerialReader {
 
	SerialPort activePort;
	SerialPort availablePorts[] = SerialPort.getCommPorts();
	
	public String[] getAvailablePortNames() {
		String portNames[] = new String[availablePorts.length];
		for(int i = 0; i < availablePorts.length; i++)
			portNames[i] = availablePorts[i].getDescriptivePortName();
		return portNames;
		}
	
	public void setPort(int portIndex) {
	    activePort = SerialPort.getCommPort("COM4");
	}

	
	public void closePort() {
		if (activePort != null)
			activePort.closePort(); 
		}
	
	public void startListening(PortListener portListener) {
		if (activePort != null) {
			activePort.addDataListener((SerialPortDataListener) portListener);
			activePort.openPort();
			}
		}

	
	}