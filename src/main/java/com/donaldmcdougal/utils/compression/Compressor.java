/**
 * 
 */
package com.donaldmcdougal.utils.compression;

import java.io.IOException;
import java.nio.file.Path;

/**
 * An interface for compressing things.
 * @author Donald McDougal
 *
 */
public interface Compressor {

	/**
	 * Compresses a file.
	 * @param toCompress The file to compress.
	 * @param compressed The compressed file.
	 * @throws IOException
	 */
	public void compressFile(Path toCompress, Path compressed) throws IOException;
	
	/**
	 * Decompresses a file.
	 * @param toDecompress The file to decompress.
	 * @param decompressed The decompressed file.
	 * @throws IOException
	 */
	public void decompressFile(Path toDecompress, Path decompressed) throws IOException;
}
