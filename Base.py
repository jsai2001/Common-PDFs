# -*- coding: utf-8 -*-
"""
Created on Mon Nov 13 14:44:57 2023

@author: Dell
"""

import os
import random
import shutil
import subprocess

def file_delete_if_exists_and_create(directory):
    if os.path.isfile(directory) or os.path.islink(directory):
        os.unlink(directory)
    elif os.path.isdir(directory):
        shutil.rmtree(directory)
    if not os.path.exists(directory):
        os.makedirs(directory)


#----------------------------------------------------------------------------
# # Replace the source and destination folder paths with the actual paths
# for i in range(1,50):
#     source_folder = 'D:\\Education\\TimePass'

#     # Get a list of all the files in the source folder
#     files = os.listdir(source_folder)

#     # Choose a random sample of files
#     try:
#         sample = random.sample(files, 60) # Change 5 to the number of files you want to move
#     except:
#         sample = random.sample(files,len(files))
#     destination_folder = 'D:\\Education\\Psychology\\Folder_'+str(i)
#     os.mkdir(destination_folder)

#     # Move the files to the destination folder
#     img_itr=1
#     for file in sample:
#         source_path = os.path.join(source_folder, file)
#         destination_path = os.path.join(destination_folder, file)
#         shutil.move(source_path, destination_path)
#         old_name = os.path.join(destination_folder, file)
#         new_name = os.path.join(destination_folder, f"img_{img_itr}.png")
#         os.rename(old_name, new_name)
#         img_itr+=1
        
#----------------------------------------------------------------------------

#----------------------------------------------------------------------------
# folder_1 = "D:\\Education\\Psychology_Chapter_1"
# folder_2 = "D:\\Education\\Psychology_Chapter_2"
# folders = [folder_1,folder_2]
# dest_folder = "D:\\Education\\Psychology"
# file_delete_if_exists_and_create(dest_folder)
# for folder in folders:
#     files = os.listdir(folder)
#     for file in files:
#         shutil.move(os.path.join(folder, file), dest_folder)

# # Specify the path of the folder containing the images
# root_folder = "D:\\Education\\Psychology"
# nfolders=len(os.listdir(root_folder))

# file_num = 1
# for fold_num in range(1,nfolders+1):
#     folder = f"D:\\Education\\Psychology\\Folder_{fold_num}"
#     #os.mkdir(folder)    
#     # Get the list of all the files in the folder
#     files = os.listdir(folder)
#     # Loop through each file
#     for i, file in enumerate(files):
#         #temp = i + 1
#         # Get the old file name
#         old_name = os.path.join(folder, file)
#         # Create the new file name with serial order
#         new_name = os.path.join(folder, f"image_{file_num}.png")
#         # Rename the file
#         os.rename(old_name, new_name)
#         file_num+=1

# glob = 1
# root_folder = "D:\\Education\\Psychology"
# dest = 'D:\\Education\\TimePass'
# file_delete_if_exists_and_create(dest)
# nfolders=len(os.listdir(root_folder))
# for i in range(1, nfolders+1):
#     source = 'D:\\Education\\Psychology\\Folder_'+str(i)
#     dest = 'D:\\Education\\TimePass'
#     for file in os.listdir(source):
#         src_file = os.path.join(source, file)
#         if os.path.isfile(src_file):
#             dst_file = os.path.join(dest, file)
#             glob += 1
#             shutil.copy(src_file, dst_file)

# def select_random_files(source_dir, dest_dir, sample_size):
#     files = os.listdir(source_dir)
#     random_files = random.sample(files, sample_size)

#     file_delete_if_exists_and_create(dest_dir)

#     for file in random_files:
#         shutil.copy(os.path.join(source_dir, file), dest_dir)

# source_dir = 'D:\\Education\\TimePass'
# n_samples = int(input("Number of modules needed: "))
# sample_size = int(input("Sample Size: "))
# for sample_no in range(1,n_samples+1):
#     dest_dir = f"D:\\Education\\Sample_{sample_no}"
#     select_random_files(source_dir, dest_dir, sample_size)
#--------------------------------------------------------------------
# def get_all_file_paths(directory):
#     # Initialize an empty list to store the file paths
#     file_paths = []

#     # Use os.walk to iterate over all subdirectories and files in the directory
#     for root, dirs, files in os.walk(directory):
#         for file in files:
#             if(file.endswith('.mp4')):
#                 # Use os.path.join to construct the full path to the file
#                 file_path = os.path.join(root, file)
#                 # Add the file path to the list
#                 file_paths.append(file_path)

#     return file_paths

# # Specify the root directory here
# directory = 'D:\\Education'
# file_paths = get_all_file_paths(directory)

# # Print all file paths
# for file_path in file_paths:
#     print(file_path)

# # Open the video file with the default application
# n=int(input("1 => Call one & 2 => Filter N : "))
# if(n==1):
#     # Choose a random video file
#     video_file = random.choice(file_paths)
#     # Construct the full file path
#     video_path = os.path.join(directory, video_file)
#     subprocess.call(['C:\\Program Files\\VideoLAN\\VLC\\vlc.exe', video_path])
# else:
#     dest_dir = "D:\\Education\\Sample"
#     n_samples = int(input("N Samples you need?: "))
#     if os.path.isfile(dest_dir) or os.path.islink(dest_dir):
#         os.unlink(dest_dir)
#     elif os.path.isdir(dest_dir):
#         shutil.rmtree(dest_dir)
#     if not os.path.exists(dest_dir):
#         os.makedirs(dest_dir)
#     random_files = random.sample(file_paths, n_samples)
#     for file in random_files:
#        shutil.copy(file, dest_dir)
#--------------------------------------------------------------------
