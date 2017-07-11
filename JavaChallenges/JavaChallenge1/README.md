## Challenge 1 Questions

General approach: Read in file once, convert to String ArrayList, and pass to each class.
A file writing class is used, where each solution class contains a reference to this.

###a) Read in the file //tmp/shared/num.txt and sum the values of all the lines which contain numeric data.

Approach: regex applied to only retrieve numbers from the ArrayList, then parse these integers.
 
###b) Create a new file in your local home directory called num2.txt so that each row of the new file contains the original file, and a second character on each line representing the ascii character code of the existing line entry.

Approach: Convert first character of each String of each line to an integer, then add this as an additional column.
 
###c) If the file num.txt was orderd in terms of lexicorgraphy (i.e. alphabetically) create a new file num3.txt that has the origional line, and the position of the line in the ordered file.
 
Approach: Take non-empty indices from the original ArrayList, store in a new ArrayList, then sort it. 

###d) Remove any blank lines from the file and save the file as num4.txt in your local directory. 

Approach: Loop through original ArrayList and only write non-empty indices
