# compress-text-to-numbers
Compress Text Data to Numbers using Clojure

## Description


You will be developing an application that will allow users to compress and decompress simple text files. A small menu will provide users with a handful of options for manipulating and displaying input. The menu will be particularly convenient, as it will allow both students and graders to evaluate the accuracy of your application.
The first option in the menu will be “Display list of files”. As the name implies, selection of this option will display a list of one or more file names. In practice, this list is just the files in the current folder (i.e., the same folder from which you are running your Clojure program.) The image below shows the basic menu and the result obtained when choosing Option1.

<img width="253" alt="Screenshot 2023-06-30 at 3 13 42 PM" src="https://github.com/anuragagarwal6895/compress-text-to-numbers/assets/117996954/2c3e486d-af61-4dc8-895a-89e7cfd6a7b5">

In this case, the list consists of a pair of Clojure source files (your program code), a file called frequency.txt (discussed below), and a group of tn.txt files (the sample/test files that you or the grader will create). It’s possible that there may also be some OS- specific files (e.g., ..DS_Store); that’s fine too.
Note that while capturing a directory listing can be somewhat tedious in some languages, it is quite trivial in Clojure and makes use of the file-seq function. You can find documentation and samples on the main Clojure web site. You should just need a line or two of code.
We now move on to Option 2 – Display file contents. Here, we are simply going to display the content of the input file that we are currently processing. In the current case, this is one of the tn.txt files (note that these files could have any name).
To display a file, we simply prompt a user for the file name, then read the text content and print it to the screen. Again, this is a trivial thing to do and utilizes Clojure’s slurp function. Use of this function is documented and illustrated on the Clojure web site and should again require just a line or two of code. The image below illustrates the use of Option 2 for the t1.txt file shown in the previous File Listing.

<img width="423" alt="Screenshot 2023-06-30 at 3 14 12 PM" src="https://github.com/anuragagarwal6895/compress-text-to-numbers/assets/117996954/ece2849a-a22a-44a6-aa61-207fa9e1d1c3">

It should be obvious that basic error checking must also be provided. Specifically, we must ensure that both the file names and menu selections are valid. An example of an “invalid file” message is listed below:

<img width="333" alt="Screenshot 2023-06-30 at 3 16 08 PM" src="https://github.com/anuragagarwal6895/compress-text-to-numbers/assets/117996954/570388bb-b69b-48e0-a861-2b16250900fd">

So now we move on to Option 3 and Option 4, which are of course the main focus of the assignment. We begin with Option 3 – Compress a file. In fact, we will be starting with t1.txt, which contains the following text:

#### The first man is from the last group of people

So we need to compress this text file in some way. What sort of compression algorithm are we going to use? A very simple one! Specifically, we are going to convert the original text file into a new text file in which the words in the original file are converting into numbers (again, the output file is a text file, not a binary file).
So let’s select Option 3 and provide the name t1.txt when prompted for a file name. If all goes well, the compression will be performed, a new compressed file will be created, and the menu will be re-displayed. (Note that with this assignment the screen is not cleared each time the menu is displayed; the menu will simply keep scrolling down the console.)
If we now select Option 1 and display the File List again, we will see that a new file has appeared. In this case, it will be called t1.txt.ct. In fact, this is the compressed text (ct) file that was created by our program when we selected Option 3. Because the compressed files are text-based, we can again use Option 2 to display the contents. So if we use Option 2 and provide the name t1.txt.ct, we should see something like the following displayed:

#### 0 41 374 6 15 0 197 178 1 86

This is the compressed version of our original text. So where did these numbers come from? In short, the numbers refer to the frequency of use in the English language. In this case, the word “the” is the most frequently used word in the English language. As a result, it is assigned the numeric label “0”. The word “of” is the second most used word and is represented in our compressed file as “1”.
Of course, you have to get these frequencies from somewhere. So with the assignment, I have provided a text file called frequency.txt. This is a simple text file that contains the 10,000 most common words in English. You will have to use the contents of this file to determine the frequency for a given word. NOTE: The file actually contains phrases as well as words (e.g., “the other”). So, in practice, it is only the first occurrence of the word that is relevant; all subsequent occurrences are associated with phrases and should thus be ignored.
So now we can select Option 4 to uncompress a file. Once we provide the name of a previously compressed file (e.g., t1.txt.ct), the program will decompress the file and (hopefully) display the original text directly to the screen (note: we do not create a new file, we just display the compressed contents in an uncompressed form). At this point, both you and the graders will be able to verify that the compression has worked.
So that’s the basic idea – compress and decompress using word frequency. Of course, we need to do a little more to make the program interesting. 

Let’s point out a number of issues:
• There is a lot of punctuation (commas, periods, parentheses, etc). You can see in the compressed file that the punctuation symbols are included in their original form. This is important since punctuation is required in order to properly read English sentences.
• To do this, the punctuation has been separated from the text before determining frequencies. So, for example, you cannot search for “man,” in the frequency.txt file. Instead, man is represented as 374 in the compressed file, and the “,” comes next.
• The phrase “the 416 area code” contains an integer that must be represented properly in the compressed file. Clearly it cannot be stored simply as 416 since the decompression function(s) would treat this as a frequency. Instead, we record it as @416@ so that we will know that it must be treated differently.
So this complicates the compression/decompression routines. However, there is one more thing to consider. When we decompress the file, we want to get the original text back . 

In other words, the punctuation and presentation style will be wrong. This is because no formatting information can (or should) be stored in the compressed file. It just contains the raw information. So your decompression functions must actually apply basic rules of English text formatting in order to get the data back to a readable form. These “rules” include:
• The first letter in the first word of every sentence must be capitalized.
• End of phrase/sentence punctuation (e.g., [ , . ? ! ] should have a space after but not before.
So we would have “the experienced man, named...” or “...correct information?”
• Opening parentheses - either ( or [ - should have a space before but not after. For example,
“his (principal) assistant”. Conversely, closing parentheses should have a space after.
• A dash should have space before and after, as in “code – and”.
• The @ sign should have a space before but not after, as in “suggested @list”. The same
would be true of a dollar sign $
• Finally, note that any other punctuation formatting is not relevant and will not be included
in the graders’ sample. So, for example, a period (.) will only be used to end a sentence. It will not be used within a real number like 45.678.

#### Example Input & Output:

Input: The experienced man, named Oz, representing the 416 area code - and his (principal) assistant - are not in the suggested @list [production, development]. Is that actually the correct information?<br>
Output: 0 1686 374 , 402 Oz , 2280 0 @416@ 148 617 - 2 18 ( 2156 ) 4103 - 14 23 4 0 957 @ 734 [ 250 , 230 ] . 6 8 759 0 1524 295 ?

Input: The pink elephant is absolutely groovy<br>
Output: 0 6167 elephant 6 5852 groovy
