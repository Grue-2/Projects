/*
JC

10/28/2017 - clean for github update

(This script uses a header I got from
//Source: http://ubld.it/wp-content/uploads/2014/02/rng.h )

This is the script I use to generate binary
keys from a "TrueRNG3" hardware RNG device.

(I don't know if the hardware device is up to real world
cryptographic standards, for all the statistical
tests I know how to code so far it works, but I also
can't really detect most PRNG's with low sample sizes
either given how little I know about crypto currently)

General use is plug in the hardware RNG USB. Check which
COM port its using, change the constant below, then run
the script.
The script then generates binary key files in the folder
where it was run.
*/

#include "TrueRNGInterface.h"

#include<fstream>
#include<iostream>
#include<string>
#include<sstream>
using namespace std;

const int RNG_PORT = 3, ITERATIONS = 40;
const long CHUNK_SIZE = 937500;

/*
Flags and what they do:

No flag:
	Generates keys as expected, by default von Neumann whitens.
	(Slows down key generation significantly)

"-d":
	tested the action of the writing script without accessing the hardware RNG
	(came up becuase its quite possible to write in non binary mode, or have
	some other artifacting which ruins the randomness of keys, so its good to
	test if the files your generating are exactly as you expect)

"-w":
	generates very small keys with the idea of testing if the hardware is working
	as intended. ("wet" mode, for testing with hardware active)

"-n":
	Generates keys as per usual, but instead does so without von Neumann whitening
*/

void startGeneratingKey();

// 375,000,000 bytes per file
int main(int argc, const char*argv[])
{
	if (argc == 2 && !strcmp(argv[1], "-d"))
	{
		cout << "dry mode recognized." << endl;

		unsigned char writeMe = 5;

		for (int file = 0; file < 400; file++)
		{
			ostringstream name;
			name << "KeyFile" << (file + 1);

			ofstream writer(name.str().c_str(), ios::app | ios::binary);

			for (int i = 0; i < 375; i++)
				writer.put(writeMe);

			cout << "KeyFile" << file << " successfully generated." << endl;
		}
	}
	else if (argc == 2 && !strcmp(argv[1], "-w"))
	{
		cout << "wet mode recognized." << endl;

		RandomFromTrueRNG rng(RNG_PORT);

		if (rng.bad)
		{
			cerr << "Couldn't start Rng terminating." << endl;

			return 1;
		}
		for (int file = 0; file < 1; file++)
		{
			ostringstream name;

			name << "KeyFile" << (file + 1);

			ofstream writer(name.str().c_str(), ios::app | ios::binary);

			for (int i = 0; i < 375; i++)
				writer.put(rng.getWhiterByte());

			if (rng.bad)
			{
				cerr << "Rng ran into a problem, terminating" << endl;

				return 1;
			}

			cout << "KeyFile" << (file + 1) << " successfully generated." << endl;
		}
	}
	else
	{
		unsigned char (RandomFromTrueRNG::*getByteFunction)();

		if (argc == 2 && !strcmp(argv[1], "-n"))
		{
			cout << "non neuman whitening mode" << endl;

			getByteFunction = RandomFromTrueRNG::getByte;
		}
		else
			getByteFunction = RandomFromTrueRNG::getWhiterByte;

		RandomFromTrueRNG rng(RNG_PORT);

		if (rng.bad) {
			cerr << "Couldn't start Rng terminating." << endl;

			return 1;
		}

		for (int file = 0; file < 400; ++file)
		{
			ostringstream name;

			name << "KeyFile" << (file + 1);

			ofstream writer(name.str().c_str(), ios::app | ios::binary);

			char buffer[CHUNK_SIZE] = {};

			for (int i = 0; i < ITERATIONS; ++i)
			{
				for (int j = 0; j < CHUNK_SIZE; ++j)
					buffer[j] = (rng.*getByteFunction)();

				writer.write(buffer, CHUNK_SIZE);
			}

			cout << "KeyFile" << (file + 1) << " successfully generated." << endl;
		}
	}

	return 0;
}

void startGeneratingKey()
{

}