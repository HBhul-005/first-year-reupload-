/*
 void setup() {
  // Set PORTD as output
  asm("ldi r17, 0xFF "); // Fill r17 with all 1s
asm("out 0x0A, r17 "); // Use r17 to set all data direction pins on PORTD

}

void loop() {
asm("ldi r16, 0x4F");
asm("out 0x0B, r16");
delay(1000);

// Turn off the display
  asm("ldi r16, 0x00");  // Load 0 to turn off all segments
  asm("out 0x0B, r16");  // Write 0 to PORTD
  delay(1000);  // Wait 1 second

} 


*/

// Lab 4
// No group members, just me

// Question 5

/* This program will be used to flash numbers 0-9 on the 7 segment display, and repeat.
   First in the setup function, the value 0xFF will be loaded into r17, which will set all the 8 bits to HIGH.
   Next, the value 0xFF in r17 will be written to the address Ox0A, which will set all the
   data direction pins on PORTD to HIGH. Continuing to the loop function, it will load the
   corresponding hex representation of the numbers 0-9 into r16 and write it to PORTD, one by one. This will be
   accomplished using the ldi and out instructions, using r16 as the general purpose register and 0x0B as the
   PORTD address to write to. The final step will be adding a 1000 millisecond delay between each number being loaded 
   and written to PORTD.
*/

void setup(){
    asm("ldi r17, 0xFF "); // Fill r17 with all 1s
    asm("out 0x0A, r17 "); // Use r17 to set all data direction pins on PORTD
}

void loop(){

    asm("ldi r16, 0x3F");       //  Load 0x3F into r16 (hex representation of 0 on the LED)
    asm("out 0x0B, r16");       //  Write r16 to PORTD
    delay(1000);                // 1 second delay


    asm("ldi r16, 0x06");       //  Load 0x06 into r16 (hex representation of 1 on the LED)
    asm("out 0x0B, r16");       //  Write r16 to PORTD
    delay(1000);                // 1 second delay


    asm("ldi r16, 0x5B");       //  Load 0x5B into r16 (hex representation of 2 on the LED)
    asm("out 0x0B, r16");       //  Write r16 to PORTD
    delay(1000);                // 1 second delay


    asm("ldi r16, 0x4F");       //  Load 0x4F into r16 (hex representation of 3 on the LED)
    asm("out 0x0B, r16");       //  Write r16 to PORTD
    delay(1000);                // 1 second delay


    asm("ldi r16, 0x66");       //  Load 0x66 into r16 (hex representation of 4 on the LED)
    asm("out 0x0B, r16");       //  Write r16 to PORTD
    delay(1000);                // 1 second delay

    asm("ldi r16, 0x6D");       //  Load 0x6D into r16 (hex representation of 5 on the LED)
    asm("out 0x0B, r16");       //  Write r16 to PORTD
    delay(1000);                // 1 second delay
      

    asm("ldi r16, 0x7D");       //  Load 0x7D into r16 (hex representation of 6 on the LED)
    asm("out 0x0B, r16");       //  Write r16 to PORTD 
    delay(1000);                // 1 second delay


    asm("ldi r16, 0x07");       //  Load 0x07 into r16 (hex representation of 7 on the LED)
    asm("out 0x0B, r16");       //  Write r16 to PORTD
    delay(1000);                // 1 second delay


    asm("ldi r16, 0x7F");       //  Load 0x7F into r16 (hex representation of 8 on the LED)
    asm("out 0x0B, r16");       //  Write r16 to PORTD
    delay(1000);                // 1 second delay


    asm("ldi r16, 0x6F");       //  Load 0x6F into r16 (hex representation of 9 on the LED)
    asm("out 0x0B, r16");       //  Write r16 to PORTD 
    delay(1000);                // 1 second delay

}
 
