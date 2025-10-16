// Lab 5
// No group members, just me


/*
  This program is used to lighten 5 LEDs on a breadboard using input from
  a joystick component. This program will read the voltage inputs from the
  joystick, which was already given as sample code in C syntax by the professors.
  The assembly portion of the program utilises branch instructions, which use
  the x and y values located in the r16 and r17 registers to light the appropriate
  LEDs. The LEDs will be connected to pins 2-6 on PORTD, where the program will
  set bits accordingly based on which direction the joystick is facing. 2 LEDs can also
  be set at the same time, if the joystick is aimed in a corner position (for example, 
  south west). Pressing the joystick button will light up the LED connected to pin 6,
  while the directional LEDs are connected to pins 2,3,4 and 5.

*/

void analog_init()          // This is a C function used to convert analog values
{
 // Analog functions can be used as-is
 ADCSRA |= (1 << ADEN);
 ADMUX |= (1 << REFS0);
 ADMUX |= (1 << ADLAR); // Left justified output for 8bit mode
 ADCSRA |= (1 << ADSC); // start conversion
}
uint8_t analog8(uint8_t channel)      // Another C function used with the analog conversion pins, which Vrx and Vry connect to
{
 // Analog functions can be used as-is
 ADMUX &= 0xF0;
 ADMUX |= channel;
 ADCSRA |= (1 << ADSC);
 while ( ADCSRA & ( 1 << ADSC ) );
 return ADCH;
}


void setup()
{
 Serial.begin(9600);
 analog_init();
 // Setup your pins and ports here.

 asm("sbi 0x0A, 6"); // Set bit 6 in PORTD for output
asm("cbi 0x0A, 7"); // Clear bit 7 in PORTD for input
asm("sbi 0x0B, 7"); // Set pullup resistor for pin 7 in PORTD

 asm("sbi 0x0A, 2");  // Set bit 2 for output
 asm("sbi 0x0A, 3");  // Set bit 3 for output
 asm("sbi 0x0A, 4");  // Set bit 4 for output
 asm("sbi 0x0A, 5");  // Set bit 5 for output
}
void loop()
{
      asm(" start: ");
      // Get analog values for X and Y
      // Load from ADCH (0x79) into r16/r17
      int x_axis = analog8(0);
      asm(" lds r16, 0x79 ");
      int y_axis = analog8(1);
      asm(" lds r17, 0x79 ");

      // EVERYTHING BELOW THIS LINE SHOULD BE ASSEMBLY
      // Click button logic here. Should the button LED be on or off? pin 6

      asm(" in r25, 0x09 ");    // Load PIND into r25
      asm(" andi r25, 0x80 "); // Bit-mask the 7th bit
      asm(" cpi r25, 0x80 ");  // Compare with 0x80
      asm(" brne click_on ");  // If not zero, branch to click_on

      asm(" cbi 0x0B, 6 ");    // Turn off LED
      asm(" rjmp check_up ");  // jump to check_up label

      asm(" click_on: ");      // click_on label
      asm(" sbi 0x0B, 6 ");    // Turn on LED
      

      // Up direction logic here. Should the Up LED be on or off? pin 3

      asm("check_up:  ");     // check_up label
      asm("cpi r17, 0");      // Compare Y (r17) with 0
      asm("breq up");         // If Y == 0, branch to up
      asm("cbi 0x0B, 3");     // Otherwise, turn OFF Up LED (PD3)
      asm("rjmp check_left"); // Jump to next check

      asm("up:  ");           // up label
      asm("sbi 0x0B, 3");     // Turn ON Up LED (PD3)

      //  Left direction logic here. pin 2

      asm("check_left:  ");       // check_left label
      asm("cpi r16, 0");          // Compare X (r16) with 0
      asm("breq left");           // If X == 0, branch to left
      asm("cbi 0x0B, 2");         // Otherwise, turn OFF Left LED (PD2)
      asm("rjmp check_down");     // Jump to next check

      asm("left:  ");             // left label
      asm("sbi 0x0B, 2");         // Turn ON Left LED (PD2)

      // Down direction logic here, pin 5

      asm("check_down:");         // check_down label
      asm("cpi r17, 255");        // Compare Y (r17) with 255
      asm("breq down");           // If Y == 255, branch to down
      asm("cbi 0x0B, 5");         // Otherwise, turn OFF Down LED (PD5)
      asm("rjmp check_right");    // Jump to next check

      asm("down:  ");             // down label
      asm("sbi 0x0B, 5");         // Turn ON Down LED (PD5)

      // Right direction logic here, pin 4

      asm("check_right:   ");     // check_right label
      asm("cpi r16, 255");        // Compare X (r16) with 255
      asm("breq right");          // If X == 255, branch to right
      asm("cbi 0x0B, 4");         // Otherwise, turn OFF Right LED (PD4)
      asm("rjmp finish");         // Jump to finish

      asm("right:   ");           // right led label
      asm("sbi 0x0B, 4");         // Turn ON Right LED (PD4)

      asm("finish:  ");             // finish label
      delay(2);                   // 2 millisecond delay


 // EVERYTHING ABOVE THIS LINE SHOULD BE ASSEMBLY
 // You can use this serial code for handy debugging.
 // It will let you observe the values coming out of your ADC.
Serial.print("< ");
 Serial.print(x_axis);
 Serial.print(", ");
 Serial.print(y_axis);
 Serial.println(" >");
 delay(2);                // 2 millisecond delay
 asm(" rjmp start ");    // Jump back up to the start label
} 

