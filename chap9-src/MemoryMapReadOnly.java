/**
 * This program illustrates memory-mapping using the Java API.
 *
 * Usage
 *	java MemoryMapReadOnly <input file>
 *
 * Figure 10.21
 *
 * @author Gagne, Galvin, Silberschatz
 * Operating System Concepts with Java - Sixth Edition
 * Copyright John Wiley & Sons - 2003.
 */

import java.io.*;
import java.nio.*;
import java.nio.channels.*;

public class MemoryMapReadOnly {
	// let's assume the page size is 4 KB
	public static final int PAGE_SIZE = 4096;

	public static void main(String[] args) throws java.io.IOException {
		if (args.length == 1) {
			RandomAccessFile inFile = null;

			try {
				inFile = new RandomAccessFile(args[0], "r");
			} catch (FileNotFoundException fnfe) {
				System.err.println("File " + args[0] + " not found.");
				System.exit(0);
			}

			FileChannel in = inFile.getChannel();
			MappedByteBuffer mappedBuffer = in.map(
					FileChannel.MapMode.READ_ONLY, 0, in.size());
			System.out.println("The size of the mapped buffer is " + in.size());

			long numPages = in.size() / (long) PAGE_SIZE;
			if (in.size() % PAGE_SIZE > 0)
				++numPages;

			// we will "touch" the first byte of every page
			int position = 0;
			for (long i = 0; i < numPages; i++) {
				byte item = mappedBuffer.get(position);
				position += PAGE_SIZE;
			}

			System.out.println("The number of pages (assuming 4 KB page size) = "
							+ numPages);

			// determine if it is loaded
			if (!mappedBuffer.isLoaded()) {
				// now force the loading
				mappedBuffer.load();
			}

			in.close();
			inFile.close();
		}
	}
}
