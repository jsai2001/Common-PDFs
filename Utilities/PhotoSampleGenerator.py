#Select Sample set of files from the pool
import os
import shutil
import random
import time

def move_file(source_dir, dest_dir):
    # Copy files sample from source dir to destination dir
    shutil.copy(source_dir,dest_dir)

# Create a list to hold the strings
string_list = []

# Loop from 0 to 6370
photo_list_length = 6370
for i in range(photo_list_length+1):
    # Convert the number to a string and pad with leading zeros to ensure it has a width of 4
    string_with_zeros = str(i).zfill(4)
    # Append the formatted string to the list
    string_list.append(string_with_zeros)

n_minutes = 1
sample_list = random.sample(string_list,((n_minutes*60)//5))
    
dest_dir = 'D:\\Sample'
# Delete pre-existing destination directory , if exists
if os.path.exists(dest_dir):
    shutil.rmtree(dest_dir)
# Create the destination directory if it doesn't exist
if not os.path.exists(dest_dir):
    os.makedirs(dest_dir)
    
for i in sample_list:
    source_dir = f'D:\\Half-Life\\20240109{i}.png'.format(i)
    move_file(source_dir, dest_dir)
