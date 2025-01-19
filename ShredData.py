import os
import mmap
import random
import string
from concurrent.futures import ThreadPoolExecutor
import logging

# Configure logging
logging.basicConfig(level=logging.INFO, format='%(asctime)s - %(levelname)s - %(message)s')
logger = logging.getLogger(__name__)

def shred_file(file_path, passes=3):
    # Check if the file exists
    if not os.path.exists(file_path):
        logger.warning(f"File {file_path} does not exist.")
        return

    file_size = os.path.getsize(file_path)
    
    # Rename the file for added security
    new_name = ''.join(random.choices(string.ascii_letters + string.digits, k=10))
    new_path = os.path.join(os.path.dirname(file_path), new_name)
    try:
        os.rename(file_path, new_path)
        file_path = new_path
    except OSError as e:
        logger.error(f"Failed to rename {file_path}: {e}")
    
    with open(file_path, 'rb+') as file:
        mmap_file = mmap.mmap(file.fileno(), 0)
        for _ in range(passes):
            data = os.urandom(file_size)
            chunk_size = 1024 * 1024  # 1MB chunks
            with ThreadPoolExecutor() as executor:
                futures = []
                for i in range(0, file_size, chunk_size):
                    futures.append(executor.submit(shred_chunk, i, min(i + chunk_size, file_size), mmap_file, data))
                for future in futures:
                    future.result()
        mmap_file.flush()  # Ensure changes are written to disk
        mmap_file.close()
        
        # Truncate the file to zero length
        file.truncate(0)

    # Attempt to delete the file
    try:
        os.remove(file_path)
        logger.info(f"File {file_path} has been securely shredded and deleted.")
    except PermissionError:
        logger.error(f"Permission denied: Unable to delete {file_path}.")
    except Exception as e:
        logger.error(f"An error occurred while deleting {file_path}: {e}")

def shred_chunk(start, end, mmap_file, data):
    mmap_file.seek(start)
    mmap_file.write(data[start:end])

def shred_directory(directory_path, passes=3):
    # Check if the directory exists
    if not os.path.isdir(directory_path):
        logger.warning(f"Directory {directory_path} does not exist.")
        return

    # Walk through the directory tree in reverse order
    for root, dirs, files in os.walk(directory_path, topdown=False):
        for name in files:
            file_path = os.path.join(root, name)
            shred_file(file_path, passes)
        
        # Remove directories after shredding their contents
        for name in dirs:
            dir_path = os.path.join(root, name)
            try:
                os.rmdir(dir_path)
                logger.info(f"Removed directory: {dir_path}")
            except OSError as e:
                logger.error(f"Failed to remove directory: {dir_path}. Error: {e}")

    # Remove the top-level directory if it's now empty
    try:
        os.rmdir(directory_path)
        logger.info(f"Directory {directory_path} has been shredded and removed.")
    except OSError as e:
        logger.error(f"Failed to completely remove directory {directory_path}. It might not be empty or you might lack permission. Error: {e}")

if __name__ == "__main__":
    import sys
    if len(sys.argv) != 2:
        logger.error("Usage: python script_name.py <path_to_folder>")
        sys.exit(1)
    
    path_to_shred = sys.argv[1]
    path_to_shred = os.path.normpath(path_to_shred)
    
    if os.path.isdir(path_to_shred):
        shred_directory(path_to_shred)
    else:
        logger.error("Invalid path provided. Must be a directory.")