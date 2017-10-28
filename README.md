# Projects designating interests of mine

These are projects whose broad themes are 
in sync with what I'd love to learn.

## RNG_Scripts - generate cryptographic keys with a TrueRNGv3

### Preface
Getting access to effectively random numbers is not easy.

Sites like Random.org while effective have caps for free entropy, and also record
and take logs of the entropy they give out.

While I don't know enough currently to verify the efficacy of hardware random number
generators(HRNGs) I figured playing with one would be a good learning experience
regardless.

### Purpose

These scripts are for creating binary files filled with entropy generated from
a "TrueRNG3" HRNG. With the intended goal of producing cryptographically useful
binary keys which can be used for one time padding messages.

Now I don't know enough to validate the output, what few tests I can write can't
distinguish PRNGs from HRNGs. So I would not go about using the keys for cryptography
in an important situation. However for test purposes the keys generated have been
good thus far.


### Method of action

The scripts interface with the HRNG which spits out etnropy bits. While the HRNG claims it
whitens its output, the scripts default setting will von Neumann the output again
just in case.

Those spit out entropy bits are stored in a buffer then written to keyfiles.

### User guide

Plug in your TrueRNG3 USB, check which COM port its connected to. (google how) 
Change the RNG_PORT constant to match your TrueRNG3's COM port.
Compile TrueRNGKeyGen.cpp together with TrueRNGInterface.h. (the later is not my code)

run code where you want the key files to be generated.

The folder contains an example key file.

If its working it should look like this :

![alt text](/images/rngScript_Example.png "Rng example image")


## tic-tac-toe AI
 - Play tic-tac-toe with an AI who learns

##### Playing the game itself may require javaFX

### Preface
I don't know anything about AI. Figured I'd write a naive AI for fun.

### Purpose
This is sort of a proof of concept. The AI knows nothing about how tic-tac-toe works
to start with other than it can't make invalid moves. After being trained for a while
it can play a decent game of tic-tac-toe.

### Method of action

The AI stores current game state, compares it against its memory of outcomes in those
game states, then takes its preffered action. Based on the game result it adjusts its preferences.

### User guide

Eclipse project, import into eclipse.
The AI has its memory in preferences.ser. You can delete them and retrain executing
the main method in AI_Trainer.java (is not optimized).
Or just to test the AI you can run the main method in "TicTacToeGame.java" which
gives a nice tic tac toe game.


![alt text](/images/ticTacToe_Example.png "tic-tac-toe example image")

## Image_Processing - edge detection / visualizing random data

### Image Filters - Canny edge dectection, twoMedianThresholdFiltering, sobel edge detection, guassian/mean blur

#### Preface
Computerphile did a cool video https://www.youtube.com/watch?v=uihBwtPIBxM and some others on visual filters.
The description of the algorithm seemed understandble enough that I figured why not try it.
(Simple reason would be theres better work already done out there, but it's a learning process)

#### Purpose
Implements canny edge detection, two median threshold filtering, 
sobel edge detection, and guassian/mean blurs on .png images.

#### Method of action
Mostly uses simple kernel convolution.

Canny edge detection -
two median threshold filtering -
sobel edge detection -
guassian/mean blur - 

#### User guide

Eclipse project, import into eclipse.
Place images in the same folder as the demo images.
Update main with function calls as you wish.
Execute to generate resulting images.

Unlike the other projects this one takes alot of tweaking function
calls to get it to do what you want.

(Also worth noting there are far far better ways to filter images
than my play algorithms here.)

Below is an example of what these functions can do

##### before:

![alt text](/images/before.png "filter example image")

##### after:

![alt text](/images/after.png "filter example image")


















