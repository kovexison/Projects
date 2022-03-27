#include <LiquidCrystal.h>

LiquidCrystal lcd(12, 11, 5, 4, 3, 2);

void setup() {
  pinMode(13,INPUT);
  pinMode(9, INPUT); //cele 2 pini de la slideswitch pt. a da directia (cutie mare sau mica)
  pinMode(6, OUTPUT);
  pinMode(10, OUTPUT); //cele 2 pini pt. motorul pas cu pas
  //sunt pini de PWM pentru a putea controla si viteza motorului
  //de asemenea, cu un motor cu encoder ar fi fost mai frumos
  //dar nu am avut timp sa implementez cu encoder
  //cu cele 2 pini pot controla si directia motorului
  lcd.begin(16, 2);
  lcd.print("Initializing.");
  delay(500);
  lcd.print(".");
  delay(500);
  lcd.print(".");
  delay(1000);
  lcd.clear();
}

void loop() {
  lcd.print("Sortare...");
  if(digitalRead(13) == HIGH){ //deplasare la stanga, cutie mica
    	lcd.setCursor(0,1);
        lcd.print("Cutie Mica - stg");
    	digitalWrite(6, HIGH);
    	digitalWrite(10, LOW); //motorul se invarte la stanga
  } else
  if(digitalRead(9) == HIGH){
  		lcd.setCursor(0,1);
    	lcd.print("Cutie Mare - dr.");
    	digitalWrite(6, LOW);
    	digitalWrite(10, HIGH);
  }
}
 