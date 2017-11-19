<a name="top"></a>
# Projects designating interests of mine

These are projects whose broad themes are 
in sync with what I intend to learn more about.

Note: I'm quite new to these subjects, please don't
take anything I do here as secure in any way shape or form.

# Table of contents

1. [RNG_Scripts](#1)
2. [ticTacToe AI](#2)
3. [Image_Processing - edge detection](#3)
4. [RSA Discord chat](#4)
5. [RSA peer chat](#8)
6. [OTP_With_Integrity_Attempt](#5)
7. [Basic Steganography](#6)
8. [Cryptographic Key Manager](#7)

<a name="1"></a>
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

[to top](#top)

<a name="2"></a>
## Machine learning ticTacToe AI
 -Play ticTacToe with a learning AI

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

(AI only learns from player games if you close it with the x button, if you hard stop it
with eclipse it won't remember the games you played during that programs execution)

[to top](#top)

<a name="3"></a>
## Image_Processing - edge detection 

### Image Filters - Canny edge dectection, sobel edge detection, guassian/mean blur

#### Preface
Computerphile did a cool video https://www.youtube.com/watch?v=uihBwtPIBxM and some others on visual filters.
The description of the algorithm seemed understandble enough that I figured why not try it.
(Simple reason would be theres better work already done out there, but it's a learning process)

#### Purpose
Implements canny edge detection, sobel edge detection, 
and guassian/mean blurs on .png images.

#### Method of action
Mostly uses simple kernel convolution.

guassian/mean blur: kernel convolution averaging values surrounding the pixel, gussian weighs the central pixels higher
which helps peserve edges.

sobel edge detection: kernel convolution testing for x/y direction changes, that is creates a response when theres an edge.

Canny edge detection: input is sobel, thins sobels edges ( finds local edge maximums and dulls the rest), then
uses two threshholds of intensity, one determines which edges are kept, and the other determines which thresholds 
are kept if they are in connection with a stronger edge.(Unfortunately my poor implementation tends to break things apart)  

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

##### base image:

![alt text](/images/before.png "filter example image")

##### filtered image:

![alt text](/images/after.png "filter example image")

[to top](#top)

<a name="4"></a>
## Chat Programs 

### RSA Discord chat - Adding RSA to discord chats

#### Preface
Theres a hassle associated with moving from platform to platform
in order to chat. And theres an issue with major platforms getting
pressured to remove or break end to end encryptions. 
But it occured to me that theres nothing really stopping users from 
just encrypting things on their own other than the hassle.

So I figured why not make a chat program that made it easy for friends
to encrypt their chats through normally unencrypted lines.

#### Purpose
Chat program for my friends and family to communicate that doesn't require
encryption knowledge but still does end to end encryption done on user side 
over a free easy to use online chat program like discord.

#### Method of action
Uses a java discord bot library to interface with discord.
Uses with java.security's RSA implementation.
And a javaFX gui to provide an easy interface to have encrypted chats.

#### User guide

(Sadly not as portable or easy to use as a chrome extension would of been
in retrospect).

Also note this program requires your discord token (which is essentially your login
information) so either read through the code your running or know that your trusting
that I did not write anything malicious in the code.

Anyways.

Firstly, you need a discord account. 
Secondly add the person you want to have encrypted chats with to your friends list on discord.
Then its an eclipse project so import that into eclipse or what have you.

When its running it should look like this

![alt text](/images/LoginScreen.png "filter example image")

Instructions for how to find your discord token can be found here:
https://github.com/TheRacingLion/Discord-SelfBot/wiki/Discord-Token-Tutorial

Fill in the three fields labeled in red, then hit Chat.

If everything is going according to plan it should look like the image below

![alt text](/images/chatScene.png "filter example image")

Enter button sends stuff from input into the chat.

If your getting the error "Can't chat till they send you their key." have your friend
hit establish key.

Clear clears the chatlog, and the Delete Msg's toggle button near the top changes
if you want your encrypted messages to show up in the discord chat log.

(New RSA key generated when you hit the "chat" Button)

[to top](#top)

<a name="8"></a>
### RSA chat - plain RSA encrypted chat direct tcp

#### Preface
If discord doesn't happen to be convenient or down like it was today this
simple program lets you TCP to a friend, requires port forwarding to host if
you are behind a router.

#### Purpose
Allows java.security RSA encryption for plain text chatting through a direct
TCP connection to a friend.

#### Method of action
java.net and java.security along with javaFX to create a simple
encrypted chat program.

#### User guide

Eclipse project, might need to clean or rebuild.

![alt text](/images/rsaChat1.png "RSA chat 1")

Looks like the above. Hit host to host, otherwise fill in fields and hit connect.

![alt text](/images/rsaChat2.png "RSA chat 2")

If all went well it should look like below:

![alt text](/images/rsaChat3.png "RSA chat 2")

[to top](#top)

## Encryption
<a name="5"></a>
### OTP_With_Integrity_Attempt - scramble(one time pad(hash(message) + message)) 

(note: padding out the message to a set length could be done, but the major limiter
for me was entropy bits. So I limited the number of characters in a message to a 6bit set 
and did not padd out message lengths).

#### Preface
Despite having perfect secrecy one time pading does not provide any checks for
message integrity.

#### Purpose
The idea here is to scramble the message using numbers off the key material after hashing
in order to make attempts to change the original message very difficult to hide. That is
it attempts to add message integrity to the encrypted message.

#### Method of action
encryption: scramble(one time pad(hash(message) + message))

The hash is a java sha1.
The one time pad is just xor (or in this case bi-conditional).
The scramble uses rejection sampling to generate numbers to scramble the message.
Then the encryption is wrapped in a javaFX gui for ease of use.

#### User guide

Eclipse project, import into eclipse or what have you.
Main has the main method to run.
Once it runs it should look like this:

![alt text](/images/otpStart.png "filter example image")

The key material the program uses is binary strings.

To use real keys have the key material as binary strings. The slot
next to "Key you" is used for encrypt, and "Key them" is used to decrypt.

If you want encrypt something place it in the slot next to the encrypt button
then press encrypt, vice versa for decrypt.

If the message has been tampered with there is a high probability it did not match
the hash change as well and it will give you the message below:

![alt text](/images/notvalid.png "filter example image")

Keep in mind that once something is invalid the keys will fall out of sync,
and any further attempts tend to stay invalid (unless you readjust the keys).

As a note the program saves the binary keys when you "x" out of it.
And the keys that it comes with are not random.

[to top](#top)

<a name="6"></a>
### Basic Steganography - hide messages in pictures 

#### Preface
When I first started playing with random generators I wanted to find a way
to visuallize the data to look for a pattern. While visualizing was really  useful
for finding when I made huge mistakes in generation, the ability to inspect
by eye wasn't paticularlly useful. 

Well okay that meant that information could be hidden in noise fairly easily.

So I played with it a bit

![alt text](/images/secureRandom.png "filter example image")

And then tried to hide things in pictures that were less obviously noise

![alt text](/images/notquite.png "filter example image")

to a bit less effect. But with a few minor changes to encoding

![alt text](/images/output.png "filter example image")

its quite a bit harder to detect by plain sight. And the information which gets
encoded can be scrambled/encrypted/have its location matched to the picture
or randomized with a key etc...

That is various other techniques can be applied to it. And as it turns out
hiding information in plain sight is actually called steganography.

#### Purpose
This program hides data in a regularlized way without any frills. The purpose
was to see just to demonstrate how little is required to hide information in noise.

From a user standpoint you could text information in a picture for fun.

#### Method of action
The program encodes each bit of a message as a comparison between a pixel and its neighbor.
And pulls it back the same way.

#### User guide
Eclipse project, import or what have you.
Main has the main method, run it.


![alt text](/images/stegEG.png "filter example image")

Press load image to bring up a menu 

![alt text](/images/instruction.png "filter example image")

select an image to either write to or read from.

Then hit read to read whats on the image already or
fill out the text box with what you want to write then hit write.

( If the text doesn't change it either doesn't have a message in the picture
or something went wrong )

[to top](#top)

<a name="7"></a>
### Cryptographic key manager - Single button key managment

#### Preface
I wanted to distribute crytopgraphic keys to friends and family.
But one of the major issues is that managing keys, no reuse, which files,
deletion, folder structure, dependence on multiple programs to not tamper
with keys by mistake etc... made cordinated/safe use of entropy difficult.

#### Purpose
Because of the issues a simple encapsulated and safe manner of getting key material
was in order. As such I packaged this program long with binary keys on a usb.

Essentially this program, along with associated USB, attempts to encapsulate surrounding detail of managing keys
for an audience who can copy and paste, but struggles with moving files into the right place.

#### Method of action
The USB has cryptographic keys generated by a TrueRNGv3 whose output is von Neumann whitened, then
tested using (source: https://www.freebsd.org/doc/en/articles/ipsec-must/code.html ) an implementation
of Maurer's Universal Statistical Test. And is then AES encrypted.
The USB also has an executable .jar of this program in order to manage the keys.

The program itself uses the AESkey in the USB to decrypt the keys (the purpose is so you can seperate the
AES key from the rest of the key material if you wish ). Then tests them for randomness again using
five basic randomness tests from (Source/reference http://cacr.uwaterloo.ca/hac/about/chap5.pdf) Handbook of Applied Cryptography, 
by A. Menezes, P. van Oorschot, and S. Vanstone, CRC Press, 1996.
After checking the keys it expands them into binary strings, and deletes used key material in an attempt to make it
more difficult to double use key material.


#### User guide

Eclipse project again, import etc...
KeyManager.java has main in order to run.

The running program should look like this:

![alt text](/images/KeyManager.png "filter example image")

The Amount of bytes field changes how many bytes of binary string you want to pop up.
The test for randomness checkbox decides to test for randomness or not, keep in mind if your
key fails the program will delete the key.
The "Give" button outputs a binary string to the "Key:" slot.

While operating the program gives vague feedback like:

![alt text](/images/feedback.png "filter example image")

For more detailed feedback check the console output which includes test results:

![alt text](/images/results.png "filter example image")

After all is said and done it should look like this:

![alt text](/images/good.png "filter example image")

With key field having a binary string of your bits that you can use for whatever.

This github upload has two key files KeyFile1c and KeyFile2c for testing.


Things to keep in mind :
- The program deletes keys on use.
- The program deletes keys on errors.
- The program saves the key as a plain text binary string file.
- The program deletes text out of that file as key material is used out of that.
- Key material in the key field is saved between sessions, also as a plain text file.

[to top](#top)



