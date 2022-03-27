#include <LiquidCrystal.h>

LiquidCrystal lcd(12, 11, 5, 4, 3, 2);
#include <Keypad.h>
#include <Servo.h>

Servo servo_A0;

const byte ROWS = 4;
const byte COLS = 4;
char keys[ROWS][COLS] = {
  {'1','2','3','A'},
  {'4','5','6','B'},
  {'7','8','9','C'},
  {'*','0','#','D'}
};
byte rowPins[ROWS] = {10,9,8,7};
byte colPins[COLS] = {6,1,13,0};

Keypad keypad = Keypad(makeKeymap(keys), rowPins, colPins, ROWS, COLS);

void setup() {
  lcd.begin(16,2);
  lcd.setCursor(0,0);
  servo_A0.attach(A0, 500, 2500);
  servo_A0.write(0);
  lcd.print("Initializing.");
  delay(500);
  lcd.print(".");
  delay(500);
  lcd.print(".");
  delay(1500);
  lcd.clear();
}

void loop() {
  char key = keypad.getKey(); 
  lcd.setCursor(0,0);
  lcd.print("Selectati nr de");
  lcd.setCursor(0,1);
  lcd.print("bile pt dozare");
  switch(key){
  	 case '0':
    		lcd.clear();
    		lcd.setCursor(0,0);
    		lcd.print("0 bile dozate");
    		break;
     case '1': 
    		lcd.clear();
    		lcd.print("1 bila dozata");
             servo_A0.write(90); //450 ms -> o bila cade
             delay(450); //asteapta servomotorul sa ajunga la 30
             servo_A0.write(0);
             delay(450);
             break;
    case '2': 
    		lcd.clear();
    		lcd.print("2 bile dozate");
    		servo_A0.write(90); //900 ms -> 2 bile cad
    		delay(900); //dozare 2 bile
   			servo_A0.write(0);
            delay(450);
    		break;
    case '3': 
    		lcd.clear();
    		lcd.print("3 bile dozate");
    		servo_A0.write(90);
    		delay(1350); //1350 ms -> 3 bile cad
    		servo_A0.write(0);
    		delay(450);
    		break;
    case '4':
    		lcd.clear();
    		lcd.print("4 bile dozate");
    		servo_A0.write(90);
    		delay(1800); //1800 ms -> 4 bile cad
    		servo_A0.write(0);
    		delay(450);
    		break;
    case '5':
    		lcd.clear();
    		lcd.print("5 bile dozate");
    		servo_A0.write(90);
    		delay(2250);
    		servo_A0.write(0);
    		delay(450);
    		break;
    case '6':
    		lcd.clear();
    		lcd.print("6 bile dozate");
    		servo_A0.write(90);
    		delay(2700);
    		servo_A0.write(0);
    		delay(450);
    		break;
  }
  if(key == '7' || key == '8' || key == '9' || key == 'A' || key == 'B' || key == 'C' || key == 'D' || key == '*' || key == '#'){
   	lcd.clear();
    lcd.print("Maxim 6 bile");
    lcd.setCursor(0,1);
    lcd.print("pot fi dozate.");
    delay(2000);
  }
}
 