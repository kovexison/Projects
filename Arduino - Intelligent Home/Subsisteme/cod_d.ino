#include <LiquidCrystal.h>
//EXPLICATIE FUNCTIONARE
//FUNCTIA sistem() -> efectueaza incarcarea si deplasarea cutiei pana la colectare
//FUNCTIE ultrasonic()-> detecteaza daca se afla cutii in apropiere(<100cm)
//Daca da, se opreste masa(motorul) si se incepe dozarea bilelor,iar apoi revenirea mesei in pozitia initiala
//Daca nu, masa revine in pozitia intiala pentru a ridica o cutie
//Ciclul se repeta pana la infinit.
//Functia microsecondsToCentimeters(), transforma distanta in cm, pentru o aproximatie mai usoara
//MOTORUL se deplaseaza cu +rpm catre dozatorul de bile, si cu -rpm pentru revenirea in pozitia initiala

LiquidCrystal lcd(A4, 2, 4, 5, 6, 7); // Creez un obiect de tip lcd
long cm,duration ;
int pingPin = 10;
long distanceCm;


void setup() {
 Serial.begin(9600);
 lcd.begin(16,2); // definesc lcd-ul

 pinMode(13, OUTPUT);
 pinMode(12, OUTPUT);
 lcd.setCursor(1, 0);

}


long microsecondsToCentimeters(long microseconds) {

  return microseconds / 29 / 2;
}

void ultrasonic(){


  pinMode(pingPin, OUTPUT);
  digitalWrite(pingPin, LOW);
  delayMicroseconds(2);
  digitalWrite(pingPin, HIGH);
  delayMicroseconds(5);
  digitalWrite(pingPin, LOW);

  pinMode(pingPin, INPUT);
  duration = pulseIn(pingPin, HIGH);
   cm = microsecondsToCentimeters(duration);
  if(cm <= 100){
  lcd.print("DETECTIE CUTIE!");
    delay(5000);
    lcd.clear();
      lcd.print("OPRIRE MASA");
    delay(5000);
    lcd.clear();
     analogWrite(12, 0);
    analogWrite(13, 0);
    lcd.print("DOZARE BILE");
    delay(5000);
    lcd.clear();
    lcd.print("REVENIRE MASA");
    analogWrite(12,0);
   analogWrite(13, 255);
    delay(5000);
    lcd.clear();
  	
  
  }
  else
  {
    lcd.print("NO CUTIE");
    delay(5000);
    lcd.clear();
    analogWrite(12, 0);
    analogWrite(13, 255);
    delay(5000);
    lcd.print("REVENIRE MASA");
    delay(5000);
    lcd.clear();
  }
}

  void sistem()
  {
  lcd.setCursor(0, 1);
  lcd.print("INCARCARE CUTIE");
  delay(5000);
  lcd.clear();
   analogWrite(12, 255);
   analogWrite(13, 0);
   lcd.print("START");
  delay(5000);
  lcd.clear();
  lcd.print("DEPLASARE CUTIE");
  analogWrite(12,255);
   analogWrite(13, 0);
  delay(5000);
  lcd.clear();
  lcd.print("ULTRASONIC RANGE");
    delay(5000);
    lcd.clear();
  }



void loop() {
  
  
 sistem();
 ultrasonic();
  
  
}

