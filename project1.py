'''
This program will be used as a basic discrete math solver, which will be able to solve certain discrete math problems related to sets, 
such as set theory. In further detail,
this program will be able to perform three operations: Set difference, set partition, and power sets. For set difference,
it will ask for inputs of two sets A and B and return the set difference. For set partition, similarly to set difference, the program will
ask for sets A and B, including a set U (universal set). The program will check if the union of A and B are equal to U, and if A and B are
mutually disjoint. For power sets, the program will take in a set A, and with the help of the combinations function from the itertools module
it will be able to return the power set. This program also reads a file called 'menu.txt' to output a menu screen, and uses two user functions
made by me.

'''
with open('menu.txt', 'r') as file: # 'r' will use reading mode of the open function, 'with' keyword is used to close the file after.
   
    menu = file.read()   # This code will open a file named 'menu.txt' that will output a menu screen to greet the user.
print(menu)

def func_check(): # First function I defined to verify user inputs for the program (more details below).

# The code below will check which integer is being inputted, and will continue to the next steps accordingly.
# However, if the number is not 1, then it will notify the user that the number is invalid.

    while True: # A while loop is used to repeat the function if an invalid input is received. It will continue to repeat until a return value is given.
        try: # Try and except block is here to check for any ValueErrors that may surface.
        
            # This variable 'list_selection' will save the input of the user which includes an integer value between 1 and 3 inclusive
            list_selection = int(input('Please select one of the following by inputting a number from the above list: '))
            if(list_selection == 1): # Option for sets
                print('1a. Set difference \n1b. Set partition \n1c. Power set ')
                
                list_selection = input('Pick one from this list: ')
                    
                if list_selection not in ['1a','1b','1c']: # Checks if input is 1a,1b, or 1c, and returns an error if otherwise.
                    print('ERROR -> RESTART: Input a value from the original list and try again')
                    continue
                        
                return list_selection # If inputs are valid, it will return list_selection and break the while loop
            else:
                print( "ERROR: Enter a value on the list")
                continue
        except ValueError: # If an invalid type is inputted(such as list or tuple), it will print an error message
            print('ERROR: Please enter a proper value')
            continue
    # * All continue statements are used to skip to the next step in iteration *


def sets_calc(list_selection): # Function that will perform operations on sets (when selected)
    while True: # A while loop is used to repeat the function if an invalid input is received. It will continue to repeat until a return value is given.
    
        if list_selection == '1a': # Option for set difference
            try: # Try and except block is here to check for any ValueErrors that may surface (from spaces or commas)
                print('Set difference selected: Returns A - B for any 2 sets inputted')
                temp_pick1 = input('Input a string of integer digits (set #1): ')
                temp_pick2 = input('Input a string of integer digits (set #2): ') # Inputs for sets A,B
                # temp_pick1 and temp_pick2 will be used to store set elements 
                temp_pick1 = set(map(int,temp_pick1))
                temp_pick2 = set(map(int,temp_pick2)) # Both of these variables will be updated into their own respective lists, using map
                
                return temp_pick1 - temp_pick2
            except ValueError: # The ValueError exception will print an error message if anything that cannot be converted into an int is detected.
                print('ERROR: Input a string of only integers (no spaces, commas)')
                continue
            
        elif list_selection == '1b': # Option for set partition
            try:
                print('Set partition selected: Returns True or False for sets A and B on a set U')
                
                temp_pick1 = input('Input a string of integer digits (set #1): ')
                temp_pick2 = input('Input a string of integer digits (set #2): ') # Inputs for sets A,B,U
                uni_set = input('Input a string of integer digits (Universal set): ')
                # temp_pick1, temp_pick2, and uni_set will be used to store set elements 
                temp_pick1 = set(map(int,temp_pick1))
                temp_pick2 = set(map(int,temp_pick2)) # All of these variables will be updated into their own respective lists, using map
                uni_set = set(map(int,uni_set))
                
                return  temp_pick1.isdisjoint(temp_pick2) and temp_pick1.union(temp_pick2) == uni_set
                # This will return True or False, by using isdisjoint function which will check if A and B are disjoint
                # The union function will be used to join A and B
            except ValueError:
               print('ERROR: Input a string of only integers (no spaces, commas)')
               continue
            
        elif list_selection == '1c': # Option for power set
            from itertools import combinations # From the itertools built-in module, I will import the combinations function for power sets.
            try:
                print('Power set selected: Returns the power set of a set A')
                temp_pick1 = input('Input a string of integers: ')
                
                return [set(combinations(temp_pick1, i)) for i in range(len(temp_pick1) + 1)]
                # This will return a set of all subsets of A, including the empty set, using a for loop
            except ValueError:
                print('ERROR: Input a string of only integers (no spaces, commas)')
                continue

# This block will input the return value of the func_check function, and input it into the sets_calc function
list_selection = func_check()  # Saves the output of func_check into a variable
result = sets_calc(list_selection)
print(result) # Prints the final result
