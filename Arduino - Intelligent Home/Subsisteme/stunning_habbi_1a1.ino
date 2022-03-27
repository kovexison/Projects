#include <Servo.h>
#include <LiquidCrystal.h>

Servo servo_5;
Servo servo_6;
double angleS5;
double angleS6;
int i;

LiquidCrystal lcd(13,12,11,10,9,8);

void setup()
{
  pinMode(3, INPUT);
  pinMode(2, INPUT);
  servo_5.attach(5, 500, 2500);
  servo_6.attach(6, 500, 2500);
}

void loop()
{
  lcd.setCursor(0,0);
  lcd.print("S-a incarcat:");
  servo_5.write(0);
  servo_6.write(0);
  lcd.setCursor(0,1);
  if(digitalRead(3) == HIGH){
    lcd.setCursor(0,1);
   	lcd.print("Cutie mica");
    for(i = 0; i < 180; i++){
     	servo_5.write(i);
        delay(15); //wait for the servo to get in the position
    }
  } else
  if(digitalRead(2) == HIGH){
   	lcd.setCursor(0,1);
    lcd.print("Cutie mare");
    for(i = 0; i < 180; i++){
    	servo_6.write(i);
      	delay(15);
    }
  } else {
   	servo_5.write(0);
    servo_6.write(0);
  }
}