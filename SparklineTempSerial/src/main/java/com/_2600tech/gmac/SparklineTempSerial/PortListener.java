package com._2600tech.gmac.SparklineTempSerial;

import java.util.regex.Pattern;

import com.fazecast.jSerialComm.SerialPort;
import com.fazecast.jSerialComm.SerialPortDataListener;
import com.fazecast.jSerialComm.SerialPortEvent;

import eu.hansolo.tilesfx.Tile;

public class PortListener implements SerialPortDataListener{

		String serialString = "";
		Tile tile,tile2;
		int valueCount = 0;
		String strValue;
		@Override
		public int getListeningEvents() {

			return SerialPort.LISTENING_EVENT_DATA_AVAILABLE;
			}

		@Override
		 public void serialEvent(SerialPortEvent event) {
	        byte[] buffer = new byte[event.getSerialPort().bytesAvailable()];
	        int bytesRead = event.getSerialPort().readBytes(buffer, buffer.length);
	        String data = new String(buffer, 0, bytesRead);
	        serialString += data;

	        if (serialString.contains("\n")) {
	            int index = serialString.indexOf("\n");
	            
	            String firstLine = serialString.substring(0, index + 1).trim();
	            String secondLine = serialString.substring(index + 1).trim();
	            
	            strValue = (firstLine + secondLine).replace(" ", ""); 
	           
	            if (strValue.matches("\\d{2}\\.\\d{2}")) {
	            	double value =  Double.parseDouble(strValue);
	                if (valueCount % 2 == 0 && tile != null && value<50  ) {
	                    tile.setValue(value);
	                   valueCount++;
	                } else if (valueCount % 2==1  && tile2 != null ){
	                    tile2.setValue(value);
	                    valueCount++;
	                }
	            }

	            serialString = serialString.substring(index + 1);
// System.out.println(serialString);
				}
			}

		
		}
