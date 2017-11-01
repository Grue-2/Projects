/*
JC

11/1/2017

Source/reference: http://cacr.uwaterloo.ca/hac/about/chap5.pdf
 */

package jc.Test_Randomness;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;

// Assumptions
//37,500,000 bytes per file
// This is not a constant because many other things
// need to be changed if you were to change input size

//Source/reference http://cacr.uwaterloo.ca/hac/about/chap5.pdf
public class Testing_Randomness {

	public static boolean testSuite(String fileName) throws IOException {
		getData(fileName);

		int result = 0;

		System.out.println("\nStarting tests for randomness a=.01\n" + "Can fail one small test and continue.\n\n");
		System.out.println("\nbyte test\n--------");
		if (monobitTest())
			++result;

		System.out.println("\nserial test(2 bit)\n--------");
		if (twoBitTest())
			++result;

		System.out.println("\nn bit test (3,4,5)\n--------");
		for (int i = 3; i < 6; ++i)
			if (nBitTest(i))
				++result;

		System.out.println("\nruns test\n--------");
		if (runsTest())
			result += 2;

		System.out.println("\nautocorrelation test test\n---------");
		if (autocorrelationTest(8))
			result += 1;
		if (autocorrelationTest(5))
			result += 1;

		System.out.println();

		return (result > 7);
	}

	// TODO: Is locations variable needed ?? this is why coding at 4am isn't a
	// good idea.
	// public static void main(String... args) {
	// data = new byte[] { (byte) 227, 17, 78, (byte) 242, 73, (byte) 227, 17,
	// 78, (byte) 242, 73, (byte) 227, 17, 78,
	// (byte) 242, 73, (byte) 227, 17, 78, (byte) 242, 73, };
	//
	// monobitTest(); // .4 - tested.
	// twoBitTest(); // .6252 - tested.
	// nBitTest(3); // 9.64 - tested.
	// runsTest(); // 31.79 - tested.
	// autocorrelationTest(8); // 3.8933 - tested.
	//
	// // // for n = 300000000, 1000037 is the max n bit runs test
	// // The array size for something like that scale at 2^n however so...
	// // for(int i = 1000037 ; i < 1000050 ; ++ i )
	// // {
	// // if ((300000000/i) >= (5*(1<<i)))
	// // System.out.println(i);
	// // }
	//
	// }

	private static byte[] data;

	private synchronized static void getData(String fileName) throws IOException {
		data = Files.readAllBytes(Paths.get(fileName));
	}

	private static boolean monobitTest() {
		int n = data.length * 8;

		int numberOfOnes = 0;

		for (byte b : data) {
			int unsigned = b & 0xFF;

			while (unsigned > 0) {
				if ((unsigned & 1) == 1)
					++numberOfOnes;
				// even all ones given key file size won't overflow the integer
				// here.

				unsigned = unsigned >> 1;
			}
		}

		Testing_Randomness.numberOfOnes = numberOfOnes;
		Testing_Randomness.numberOfZeros = n - numberOfOnes;

		double temp = n - numberOfOnes - numberOfOnes;

		temp = ((temp / n) * temp);

		temp = Math.abs(temp);

		DecimalFormat twoPlaces = new DecimalFormat("#0.00");
		System.out.println("Monobit result    : " + twoPlaces.format(temp) + " < " + "3.84? " + (temp < 3.841));
		return (temp < 3.841);
	}

	private static int numberOfOnes;
	private static int numberOfZeros;

	// serial test
	// Note that
	// n00 + n01 + n10 + n11 = (n − 1) since the subsequences are allowed to
	// overlap
	private static boolean twoBitTest() {
		// x < 3.841-.95, 6.635-.99, 10.828-.999

		int n = data.length * 8;

		int countOfXY[] = new int[4];

		byte hold = 0;
		boolean notFirst = false;

		for (byte b : data) {
			int unsigned = b & 0xFF;
			if (notFirst) {
				if ((hold & 1) > 0) {
					// 1x
					if ((unsigned & 128) > 0) // 11
						++countOfXY[3];
					else // 10
						++countOfXY[2];
				} else {
					// 0x
					if ((unsigned & 128) > 0) // 01
						++countOfXY[1];
					// 00
				}
			} else
				notFirst = true;

			int iterations = 7 + 1;
			while (--iterations > 0) {
				switch (unsigned & 192) {
				case 64:
					++countOfXY[1];
					break;
				case 128:
					++countOfXY[2];
					break;
				case 192:
					++countOfXY[3];
					break;
				}
				// even all ones given key file size won't overflow the integer
				// here.
				unsigned = unsigned << 1;
			}

			hold = b;
		}

		// WELP I hope things aren't too skew because this will overflow
		// it did time to fix it

		// Pushed in division to stop overflow

		countOfXY[0] = n - 1;
		for (int i = 1; i < 4; ++i)
			countOfXY[0] -= countOfXY[i];

		double temp = 1, n_minus_1 = n - 1;

		// pushed division in hopes of avoiding overflow
		for (int count : countOfXY)
			temp += (count / n_minus_1 * count) * 4;

		temp -= (2.0) * ((double) numberOfOnes / n * numberOfOnes + (double) numberOfZeros / n * numberOfZeros);

		temp = Math.abs(temp);

		DecimalFormat twoPlaces = new DecimalFormat("#0.00");
		System.out.println("Serial  result    : " + twoPlaces.format(temp) + " < " + "5.99? " + (temp < 5.991));
		return (temp < 5.991);

	}

	// poker test
	private static boolean nBitTest(int m) {
		int location = -1;
		int n = data.length * 8;

		int[] counts = new int[1 << m];
		boolean[] buffer = new boolean[m];

		int k = n / m;

		int iterations = n;
		while (iterations % m != 0)
			--iterations;

		for (int i = 0; i < iterations; ++i) {
			buffer[i % m] = (data[(++location) >> 3] & (128 / (1 << location % 8))) > 0;

			if ((i + 1) % m == 0) {
				int temp = 0;
				for (int j = 0; j < m; ++j)
					if (buffer[j])
						temp += 1 << (m - j - 1);

				++counts[temp];
			}
		}

		double chi = 0;

		for (double i : counts)
			chi += i / k * (1 << m) * i;

		// chi *= (double) (1 << m) / k;

		chi -= k;

		chi = Math.abs(chi);

		DecimalFormat twoPlaces = new DecimalFormat("#0.00");

		double alpha[] = new double[] { 14.067, 24.996, 36.415 };

		System.out.println(m + " bit test result : " + twoPlaces.format(chi) + " < " + twoPlaces.format(alpha[m - 3])
				+ "? " + (chi < alpha[m - 3]));
		return (chi < alpha[m - 3]);
	}

	// Autocorrelation test
	private static boolean autocorrelationTest(int d) {
		// 1 <= d <= floor ( n / 2 )

		int notEqualCount = 0; // d = shift
		int n = data.length * 8;

		for (int i = 0, len = n - d; i < len; ++i)
			if ((data[i >> 3] & (128 / (1 << i % 8))) > 0 ^ (data[(i + d) >> 3] & (128 / (1 << (i + d) % 8))) > 0)
				++notEqualCount;

		double chi = 2 * (notEqualCount - (n - d) / 2.0) / (Math.pow(n - d, 0.5));

		// 1.96
		DecimalFormat twoPlaces = new DecimalFormat("#0.00");
		System.out.println(d + " shifted Autocorrelation test : " + twoPlaces.format(chi) + " < 1.96? " + (chi < 1.96));
		return (chi < 1.96);
	}

	private static boolean runsTest() {
		int n = data.length * 8;
		int k = findK(n);

		int counts[] = new int[2 * k], currentCount = 1;
		boolean inBlock = (data[0] & 128) > 0;

		for (int i = 1; i < n; ++i) {
			if (inBlock) {
				inBlock = (data[i >> 3] & (128 / (1 << i % 8))) > 0;

				if (inBlock)
					++currentCount;
				else {
					if (currentCount <= k)
						++counts[currentCount - 1];

					currentCount = 1;
				}
			} else {
				inBlock = (data[i >> 3] & (128 / (1 << i % 8))) > 0;

				if (inBlock) {
					if (currentCount <= k)
						++counts[currentCount + k - 1];

					currentCount = 1;
				} else
					++currentCount;
			}
		}

		if (currentCount <= k) {
			if (inBlock)
				++counts[currentCount - 1];
			else
				++counts[currentCount + k - 1];
		}

		double chi = 0;

		for (int i = 0; i < counts.length; ++i) {
			double ei = (n - (i % k + 1) + 3) / (Math.pow(2, (i % k + 1) + 2));
			chi += Math.pow(counts[i] - ei, 2) / ei;
		}

		// 2K - 2 degrees of freedom

		// 9.4877 for book test

		// k for expected key size is 46 , DF = 44 --> alpha .05 = 60.481

		DecimalFormat twoPlaces = new DecimalFormat("#0.00");
		System.out.println("Runs test result : " + twoPlaces.format(chi) + " < 9.49? " + (chi < 60.481));
		return (chi < 60.481);
	}

	private static int findK(int n) {
		int k = 1;

		while ((n - k + 3) / (Math.pow(2, (k) + 2)) >= 5)
			++k;

		return --k;
	}

	// public void MaurersUniversalStatisticalTest(int l)
	// {}
	// done in c++ on keys before packaging them onto USB.

}
