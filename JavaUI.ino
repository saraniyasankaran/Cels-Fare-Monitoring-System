#include "DHT.h"
#include <Adafruit_Sensor.h>
#include <SoftwareSerial.h>
#define DHTPIN 7
#define DHTTYPE DHT11
#define BT_SERIAL_TX 10
#define BT_SERIAL_RX 11
SoftwareSerial BluetoothSerial(BT_SERIAL_TX, BT_SERIAL_RX);

const int buzzerPin = 9; 

DHT dht(DHTPIN, DHTTYPE);

void setup() {
  Serial.begin(9600);
      BluetoothSerial.begin(9600);

  pinMode(buzzerPin, OUTPUT);
  dht.begin();

  }

void loop() {
  delay(2000);
  
  double t = dht.readTemperature();
  double h =dht.readHumidity();
  if (isnan(t) || isnan(h)) {
    Serial.println(F("Failed to read from DHT sensor!"));
    return;
    }
    Serial.println(t);
  Serial.println(h);
      BluetoothSerial.println("Temperature " + String(t) + "C Humidity: " + String(h) + "%");

  if(t>25 || h<70)
  {
    tone(buzzerPin, 80);
  delay(500);
  noTone(buzzerPin);
  delay(500);
  }
  if(t<25 || h<70)
  {
BluetoothSerial.println("Low Temperature and Humdidity warning");
  }
 else if(t>25 || h>70)
  {
BluetoothSerial.println("High Temperature and Humdidity warning");
  }
  
  }

