! Lab 3 

.begin
BASE	.equ 0x3fffc0
COUT	.equ 0x0
CSTAT	.equ 0x4
CIN	.equ 0x8
CICTL	.equ 0xc	
	.org 2048

main:	mov	x, %r5			! save the address of x in %r5
	clr %r1
	clr %r4			! offset in x (memory area)
	sethi BASE, %r1

iwait: 	ldub [%r1 + CICTL], %r2
	andcc %r2, 0x80, %r2
	be iwait
	ldub [%r1 + CIN], %r31
	subcc %r31, 27, %r0
	be end1

	st %r31, [%r5 + %r4]	! store input in shared memory location
	add	%r4, 4, %r4	! increment offset by 4

owait: 	ldub [%r1 + CSTAT], %r6
	andcc %r6, 0x80, %r6
	be owait
	stb %r31, [%r1 + COUT]
	
	
	ba iwait
end1:	call check_int1

	ld[x + 12], %r9		! final result register, loaded from shared memory area
	clr %r15
	call print
	halt

nums: 0x30, 0x31, 0x32, 0x33, 0x34, 0x35, 0x36, 0x37, 0x38, 0x39

operands: 0x2b, 0x2d, 0x2a, 0x2f, 0x5e	



.org 2400		! memory area for all subroutines

check_int1:  	clr %r18		! These subroutines will be used to validate the inputs (both ints, and operator)
		clr %g0
		mov %r15, %r16

		ld [x], %r18
		
		subcc %r18, 0x30, %r0		! These conditions will check if the first int is 0-9
		mov 0, %r21
		be  check_int2

		subcc %r18, 0x31, %r0
		mov 1, %r21
		be  check_int2

		subcc %r18, 0x32, %r0
		mov 2, %r21
		be  check_int2

		subcc %r18, 0x33, %r0
		mov 3, %r21
		be  check_int2

		subcc %r18, 0x34, %r0
		mov 4, %r21
		be  check_int2

		subcc %r18, 0x35, %r0
		mov 5, %r21
		be  check_int2

		subcc %r18, 0x36, %r0
		mov 6, %r21
		be  check_int2

		subcc %r18, 0x37, %r0
		mov 7, %r21
		be  check_int2

		subcc %r18, 0x38, %r0
		mov 8, %r21
		be  check_int2

		subcc %r18, 0x39, %r0
		mov 9, %r21
		be  check_int2

		ba invalid


check_int2:	clr %r19
		clr %g0

		ld [x + 8], %r19
		
		subcc %r19, 0x30, %r0		! These conditions will check if the second int is 0-9
		mov 0, %r22
		be  check_op

		subcc %r19, 0x31, %r0
		mov 1, %r22
		be  check_op

		subcc %r19, 0x32, %r0
		mov 2, %r22
		be  check_op

		subcc %r19, 0x33, %r0
		mov 3, %r22
		be  check_op

		subcc %r19, 0x34, %r0
		mov 4, %r22
		be  check_op

		subcc %r19, 0x35, %r0
		mov 5, %r22
		be  check_op

		subcc %r19, 0x36, %r0
		mov 6, %r22
		be  check_op

		subcc %r19, 0x37, %r0
		mov 7, %r22
		be  check_op

		subcc %r19, 0x38, %r0
		mov 8, %r22
		be  check_op

		subcc %r19, 0x39, %r0
		mov 9, %r22
		be  check_op

		call invalid


check_op:	clr %r20
		clr %g0
		

		ld[x + 4], %r20

		subcc	%r20, 0x2b, %r0		! branch to subroutine based on operator
		be	addition
		
		subcc	%r20, 0x2d, %r0
		be	subtract

		subcc	%r20, 0x2a, %r0
		be	mult
	
		subcc	%r20, 0x2f, %r0
		be 	div

		subcc	%r20, 0x5e, %r0
		be	exp
		
		ba invalid


addition:	clr %r9
		addcc	%r21, %r22, %r11
		st %r11, [%r5 + 12]
		jmpl %r16+4, %r0
		

subtract:	clr %r9
		subcc	%r21, %r22, %r11
		st %r11 , [%r5 + 12]
		jmpl %r16 + 4, %r0
mult:				! multiplication subroutine
	clr %r1
	clr %r2
	clr %r3
	clr %g0
	mov	%r21, %r1
	mov	%r22, %r2

	subcc	%r21, %r0, %r0
	be	fin
	
	subcc	%r22, %r0, %r0
	be	fin
	
	mu:	add 	%r1, %r3, %r3
	subcc	%r2, 1, %r2		! Subtract base by 1
	be	fin			! If 0, reset values and repeat
	bg	mu

	fin: 	st %r3, [%r5 + 12]
		jmpl %r16 + 4, %r0
	halt



div:			! division subroutine
	clr %r1
	clr %r2
	clr %r3
	clr %g0
	mov	%r21, %r1
	mov	%r22, %r2

	subcc	%r0, %r21, %r0
	be	zero
	
	subcc	%r0, %r22, %r0
	be	invalid


	clr %g0
	d:	subcc %r1, %r2, %r1
		add	%r3, 1, %r3
		bg	d		! loop if >0
		bl	final
		be	zero

	zero:	st	%r3, [%r5 + 12]
		jmpl	%r16 + 4, %r0

	final:	sub	%r3, 1, %r3
		st	%r3, [%r5 + 12] 		! Store quotient


	jmpl %r16 + 4, %r0
	halt



exp:				! exponentiation subroutine
	clr %r1
	clr %r2
	clr %r3
	clr %r4
	clr %g0
	mov	%r21, %r1
	mov	%r22, %r2
	
	addcc	%r0, %r22, %r0
	be	fin2
	sub	%r2, 1, %r2	! Dec exponent by 1 
	mov	%r21, %r4

	m: 	add 	%r1, %r3, %r3
		subcc	%r4, 1, %r4	! Subtract base by 1
		be	repeat		! If 0, reset values and repeat
		bg	m

	repeat:	clr	%g0
		clr	%r1
		mov	%r21, %r4
		sub	%r4, 1, %r4	! Dec base by 1 after first iteration
	
	mov	 %r3, %r1	! copy sum to %r1
	subcc	 %r2, 1, %r2	! decrement exp by 1, check if 0
	be	done
	bg	m
	
	done:	clr	%r1
		st	%r3, [%r5 + 12]	! Store %r3

	jmpl %r16 + 4, %r0
	
	fin2:	add %r3, 1, %r3				! Exception for exponent zero
		st	%r3, [%r5 + 12]	! Store %r3
		jmpl %r16 + 4, %r0
	halt



.org 3200

x:	.dwb 4		! shared memory area for all atguments (integer 1, operand, integer 2, and result stored in 4 words)



.org 3400

invalid:	clr %r1		! call when invalid input
 	sethi BASE, %r1
	clr %r2

	loop: 	ld [str_invalid + %r2], %r3
		orcc %r3, %r0, %r0
		be end

	wait: 	ldub [%r1 + CSTAT], %r6
		andcc %r6, 0x80, %r6
		be wait
	
		stb %r3, [%r1 + COUT]
		add %r2, 4, %r2
		ba loop

	end:	halt


str_invalid: 0x20, 0x49, 0x6e, 0x76, 0x61, 0x6c, 0x69, 0x64, 0x20, 0x69, 0x6e, 0x70, 0x75, 0x74, 0x21, 0x0a, 0		! string to output when invalid input is found


print:			! call when ready to print closing message
	clr %r1
 	sethi BASE, %r1
	clr %r2

	l: 	ld [print_statement + %r2], %r3
		orcc %r3, %r0, %r0
		be e

	w: 	ldub [%r1 + CSTAT], %r6
		andcc %r6, 0x80, %r6
		be w
	
		stb %r3, [%r1 + COUT]
		add %r2, 4, %r2
		ba l

	e:	halt

print_statement:	0x20, 0x43, 0x68, 0x65, 0x63, 0x6B, 0x20, 0x25, 0x72, 0x39, 0x00		! string to output closing message

halt
.end




