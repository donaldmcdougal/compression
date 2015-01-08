/**
 * 
 */
package com.donaldmcdougal.utils.compression;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;

import org.apache.commons.compress.compressors.gzip.GzipCompressorInputStream;
import org.apache.commons.compress.compressors.gzip.GzipCompressorOutputStream;

/**
 * Compresses things using GZip.
 * @author Donald McDougal
 *
 */
public class GZipCompressor implements Compressor {

	/* (non-Javadoc)
	 * @see com.donaldmcdougal.utils.compression.Compressor#compressFile(java.nio.file.Path, java.nio.file.Path)
	 */
	@Override
	public void compressFile(Path toCompress, Path compressed) throws IOException {
		
		FileInputStream fin = new FileInputStream(toCompress.toFile());
		BufferedInputStream in = new BufferedInputStream(fin);
		FileOutputStream fout = new FileOutputStream(compressed.toFile());
		GzipCompressorOutputStream gzOut = new GzipCompressorOutputStream(fout);
		final byte[] buffer = new byte[16];
		int n = 0;
		while (-1 != (n = in.read(buffer))) {
		    gzOut.write(buffer, 0, n);
		}
		gzOut.close();
		fout.close();
		in.close();
		fin.close();
	}

	/*
	 * (non-Javadoc)
	 * @see com.donaldmcdougal.utils.compression.Compressor#decompressFile(java.nio.file.Path, java.nio.file.Path)
	 */
	@Override
	public void decompressFile(Path toDecompress, Path decompressed) throws IOException {
		
		FileInputStream fin = new FileInputStream(toDecompress.toFile());
		BufferedInputStream in = new BufferedInputStream(fin);
		FileOutputStream out = new FileOutputStream(decompressed.toFile());
		GzipCompressorInputStream gzIn = new GzipCompressorInputStream(in);
		final byte[] buffer = new byte[16];
		int n = 0;
		while (-1 != (n = gzIn.read(buffer))) {
		    out.write(buffer, 0, n);
		}
		out.close();
		gzIn.close();

	}
}
