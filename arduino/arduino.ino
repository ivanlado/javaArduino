const int sensorLuz = A0;
const int numberPins = 5;
const int pins [numberPins] = {3, 4, 6, 7, 8};

void setup() {
  Serial.begin(9600);
  for(int i=0; i<numberPins; i++){
    pinMode(pins[i], OUTPUT);
    Serial.write(pins[i]);
  }
}


void loop() {
  if(Serial.available()>0){
    int led = Serial.read();
    int value = analogRead(sensorLuz);
    digitalWrite(led, HIGH);
  }
}
