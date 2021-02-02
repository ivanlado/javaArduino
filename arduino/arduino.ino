const int sensorLuz = A0;

const int numberDigitalPins = 5;
const int digitalPins [numberDigitalPins] = {3, 4, 6, 7, 8};

const int numberAnalogPins = 6;
const int analogPins [numberAnalogPins] = {3, 5, 6, 9, 10, 11};

const int numberAnalogReadPins = 5;
const int analogReadPins [numberAnalogReadPins] = {0, 1, 2, 3, 4};

boolean contains(int pins[], int pin, int len){
  for(int i=0; i<len; i++){
    if(pin==pins[i])
        return true;
  }
  return false;
}

void setup() {
  Serial.begin(9600);
  Serial.write(numberDigitalPins);
  Serial.write(numberAnalogPins);
  Serial.write(numberAnalogReadPins);
  for(int i=0; i<numberDigitalPins; i++){
    pinMode(digitalPins[i], OUTPUT);
    Serial.write(digitalPins[i]);
  }
  for(int i=0; i<numberAnalogPins; i++){
    pinMode(analogPins[i], OUTPUT);
    Serial.write(analogPins[i]);
  }
  for(int i=0; i<numberAnalogReadPins; i++){
    Serial.write(-i);
  }
}


void loop() {
  if(Serial.available()>0){
    int modo = Serial.read();
    delay(50);
    int pin = Serial.read();
    switch(modo){
      case 1:
        if(contains(digitalPins, pin, numberDigitalPins)){
          int valor = Serial.read();
          if(valor>0){
            digitalWrite(7, HIGH);
          } else{
            digitalWrite(7, LOW);
          }
        }
        break;
      case 2:
        break;
      case 3:
        break;
    }
    int value = analogRead(sensorLuz);
  }
}
