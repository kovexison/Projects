#include <LiquidCrystal.h>

LiquidCrystal lcd(12, 11, 5, 4, 3, 2);
float cm = 0;
int triggerPin = 7;
int echoPin = 13;

void setup() {
  pinMode(triggerPin, OUTPUT);  // Clear the trigger
  pinMode(echoPin, INPUT);
  pinMode(10, OUTPUT); //motor pins
  pinMode(9, OUTPUT);
  // set up the LCD's number of columns and rows:
  lcd.begin(16, 2);
  // Print a message to the LCD.
  lcd.print("Initializing");
  delay(500);
  lcd.print(".");
  delay(500);
  lcd.print(".");
  delay(500);
  lcd.clear();
}

long readUltrasonicDistance()
{  
  digitalWrite(triggerPin, LOW);
  delayMicroseconds(2);
  // Sets the trigger pin to HIGH state for 10 microseconds
  digitalWrite(triggerPin, HIGH);
  delayMicroseconds(10);
  digitalWrite(triggerPin, LOW);
  // Reads the echo pin, and returns the sound wave travel time in microseconds
  return pulseIn(echoPin, HIGH);
}

float ultrasonicToCm(){
 return 0.01723 * readUltrasonicDistance(); 
}


void loop() {
  cm = ultrasonicToCm();
  lcd.clear();
  lcd.setCursor(0,0);
  lcd.print("START");
  delay(2000);
  lcd.clear();
  lcd.print("Incarcare cutie");
  delay(1000);
  lcd.clear();
  lcd.print("Incepe deplasare");
  digitalWrite(10, HIGH);
  digitalWrite(9, LOW);
  delay(2000);
  if(cm <= 40){ //cutie detetcata
  	lcd.setCursor(0,0);
    digitalWrite(10, LOW);
    lcd.print("Cutie detetcata!");
    delay(1500); //asteapa putin ca sa observe pe afisaj ca cutia a fost detectata
    digitalWrite(10, HIGH);
    digitalWrite(9, LOW);
    lcd.clear();
    lcd.print("Deplasare masa");
    delay(3000); //masa se deplaseaza timp de 3 secunde
    digitalWrite(10, LOW); //motorul se opreste
    lcd.clear();
    lcd.print("Dozare bile");
    delay(3000); //se asteapta dozarea cu bile
    //asteapta 3 sec (in 3 sec se dozeaza max 6 bile)
    digitalWrite(10, HIGH);
    lcd.clear();
    lcd.print("Deplasare masa");
    delay(4000);
    digitalWrite(10, LOW);
    lcd.clear();
    lcd.print("Descarcare cutie");
    delay(3000);
    lcd.clear();
    lcd.print("revenire la poz ");
    lcd.setCursor(0,1);
    lcd.print("de start");
    digitalWrite(9, HIGH);
    delay(6000); //in 6 sec revine la pozitia de start
    digitalWrite(9, LOW);
  } else {
   	lcd.clear();
    lcd.print("NO cutie");
    lcd.setCursor(0,1);
    lcd.print("Return la start");
    digitalWrite(10, LOW);
    digitalWrite(9, HIGH);
    delay(2000); //timpul de deplasare pana la pozitia de start
  }
}
 