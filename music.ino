// Lab 6
// No group members, just me
/*

  
*/
#define BUZZER_CLOCK 16000000/8
#include "pitches.h"
/*
 * tone(freq)
 */
void tone(uint16_t freq) {
  asm("sbi 0x04, 1");
  ICR1 = BUZZER_CLOCK / freq ;
  OCR1A = ICR1 * 3 / 4 ;
}
void mute(){
  asm("cbi 0x04, 1");
  asm("cbi 0x05, 1");
}

/*
 * ASM Constants
 */
asm("PORTB = 0x05") ; // data register address
asm("DDRB = 0x04") ; // data direction address
asm("TCCR1A = 0x80") ; // address
asm("TCCR1B = 0x81") ; // address
/*
 * SETUP()
 */
void setup() {
  //TCCR1A and TCCR1B setup which before was in init_timer
  asm("LDI R16, 0B10000010") ;
  asm("STS TCCR1A, R16") ;
  asm("LDI R16, 0B00011010") ;
  asm("STS TCCR1B, R16") ;
  
  asm("SBI DDRB, 1") ; // pin is for output = pin 9
}
/*
 * LOOP()
 */
void loop() {
  
  tone(NOTE_F5); delay(600);  // F5
  mute();
  
  tone(NOTE_FS5); delay(600);  // F#5
  mute();

  tone(NOTE_G5); delay(600);  // D#5
  mute();

  tone(NOTE_C5); delay(600);  // C5
  mute();

  tone(NOTE_F5); delay(450);  // F5
  mute();

  tone(NOTE_G5); delay(450);  // F#5
  mute();
  
  tone(NOTE_DS5); delay(600);  // D#5
  mute();
  
  tone(NOTE_F5); delay(650);  // F5
  mute();
  
  tone(NOTE_FS5); delay(700);  // F#5
  mute();

  tone(NOTE_G5); delay(800);  // D#5
  
  mute();
  delay(1000);
  

}


