# compression

## donaldmcdougal's Java Compression Utilities

See the code below for an example of how to compress and decompress.

```java
	import java.io.IOException;
	import java.nio.file.FileVisitResult;
	import java.nio.file.Files;
	import java.nio.file.Path;
	import java.nio.file.Paths;
	import java.nio.file.SimpleFileVisitor;
	import java.nio.file.attribute.BasicFileAttributes;
	
	import org.apache.commons.compress.archivers.ArchiveException;
	
	import com.donaldmcdougal.utils.archiving.TarArchiver;
	import com.donaldmcdougal.utils.archiving.ZipArchiver;
	import com.donaldmcdougal.utils.compression.GZipCompressor;
	
	
	public class App {
	
		public static void main(String[] args) throws IOException, ArchiveException {
			Path toArchive = Paths.get("tocompress");
			Path tarArchiveFile = toArchive.resolve("archive.tar");
			Path zipArchiveFile = toArchive.resolve("archive.zip");
			Path compressedFile = toArchive.resolve("archive.tar.gz");
			
			TarArchiver archiver = new TarArchiver();
			archiver.archiveDirectory(toArchive, tarArchiveFile);
			
			GZipCompressor compressor = new GZipCompressor();
			compressor.compressFile(tarArchiveFile, compressedFile);
			tarArchiveFile.toFile().delete();
			
			compressor.decompressFile(compressedFile, tarArchiveFile);
			compressedFile.toFile().delete();
			archiver.extractArchive(tarArchiveFile, toArchive.resolve("tar_extracted"));
			tarArchiveFile.toFile().delete();
			deleteRecursively(toArchive.resolve("tar_extracted"));
			
			ZipArchiver zipArchiver = new ZipArchiver();
			zipArchiver.archiveDirectory(toArchive, zipArchiveFile);
			
			zipArchiver.extractArchive(zipArchiveFile, toArchive.resolve("zip_extracted"));
			zipArchiveFile.toFile().delete();
			deleteRecursively(toArchive.resolve("zip_extracted"));
			
		}
		
		/**
		 * Deletes a path recursively.
		 * @param path The path to delete.
		 * @throws IOException if an I/O error occurs.
		 */
		private static void deleteRecursively(Path path) throws IOException {
			
			Files.walkFileTree(path, new SimpleFileVisitor<Path>()
		    {
				/*
				 * (non-Javadoc)
				 * @see java.nio.file.SimpleFileVisitor#visitFile(java.lang.Object, java.nio.file.attribute.BasicFileAttributes)
				 */
		        @Override
		        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException
		        {
		            Files.delete(file);
		            return FileVisitResult.CONTINUE;
		        }
	
		        /*
		         * (non-Javadoc)
		         * @see java.nio.file.SimpleFileVisitor#visitFileFailed(java.lang.Object, java.io.IOException)
		         */
		        @Override
		        public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException
		        {
		            // try to delete the file anyway, even if its attributes
		            // could not be read, since delete-only access is
		            // theoretically possible
		            Files.delete(file);
		            return FileVisitResult.CONTINUE;
		        }
	
		        /*
		         * (non-Javadoc)
		         * @see java.nio.file.SimpleFileVisitor#postVisitDirectory(java.lang.Object, java.io.IOException)
		         */
		        @Override
		        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException
		        {
		            if (exc == null)
		            {
		                Files.delete(dir);
		                return FileVisitResult.CONTINUE;
		            }
		            else
		            {
		                // directory iteration failed; propagate exception
		                throw exc;
		            }
		        }
		    });
		}
	}
```

