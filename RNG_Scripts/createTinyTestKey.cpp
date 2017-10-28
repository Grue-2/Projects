/*
JC

10/26/2017 - clean for github update

Goal of this script is to create small alternating 1010 data
binary files (keys) to test other programs with.
*/

#include <fstream>
#include <iostream>
#include <string.h>
using namespace std;

/*
General usage is run it once to create the key.
Run it again with -t flag to check if it did what
you wanted.
( -t flag will make it print the contents of the key )

This script was generally altered to fit whatever
fixed properties I wanted a non random file to have
in testing.
*/
int main(int argc, char**argv) {
	if (argc == 2 && !strcmp(argv[1], "-t"))
	{
		ifstream in("KeyFileTinyTest", ios::binary);

		if (!in)
			cerr << "KeyFile could not be opened." << endl;
		else
		{
			char currentByte;

			while (in.get(currentByte))
			{
				// prints bits out of the byte
				// starting with leftmost
				for (int mask = 128; mask > 0; mask /= 2)
					cout << ((currentByte & mask) / mask);

				cout << endl;
			}
		}
	}
	else
	{
		ofstream out("KeyFileTinyTest", ios::binary | ios::app);
		// 170 is alternating 1010 in binary
		char x = 170;

		// 10000 is just a choosen small number.
		// At around a million bytes statistical tests,
		// encrypting and other tasks time taken
		// start feeling noticable.
		for (int i = 0; i < 10000; i++)
			out.put(x);

		cout << "\nTest file \"KeyFileTinyTest\" successfully created." << endl;
	}

	cout << endl;
	system("pause");
	return 0;
}