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
folder_1 = "D:\\Education\\Chemistry_1"
folder_2 = "D:\\Education\\Chemistry_2"
folders = [folder_1,folder_2]

dest_folder = "D:\\Education\\Chemistry"
file_delete_if_exists_and_create(dest_folder)
def get_all_file_paths(directory):
    # Initialize an empty list to store the file paths
    file_paths = []

    # Use os.walk to iterate over all subdirectories and files in the directory
    for root, dirs, files in os.walk(directory):
        for file in files:
            if(file.endswith('.mp4') or file.endswith('.png') or file.endswith('.jpg')):
                # Use os.path.join to construct the full path to the file
                file_path = os.path.join(root, file)
                # Add the file path to the list
                file_paths.append(file_path)

    return file_paths
for folder in folders:
    files = get_all_file_paths(folder)
    for file in files:
        shutil.move(os.path.join(folder, file), dest_folder)

def select_random_files(source_dir, dest_dir, sample_size):
    files = os.listdir(source_dir)
    random_files = random.sample(files, sample_size)

    file_delete_if_exists_and_create(dest_dir)

    for file in random_files:
        shutil.move(os.path.join(source_dir, file), dest_dir)

source_dir = 'D:\\Education\\Chemistry'
n_samples = int(input("Number of modules needed: "))
sample_size = int(input("Sample Size: "))
for sample_no in range(1,n_samples+1):
    dest_dir = f"D:\\Education\\Chemistry_Sample_{sample_no}"
    select_random_files(source_dir, dest_dir, sample_size)
#--------------------------------------------------------------------
# def get_all_file_paths(directory):
#     # Initialize an empty list to store the file paths
#     file_paths = []

#     # Use os.walk to iterate over all subdirectories and files in the directory
#     for root, dirs, files in os.walk(directory):
#         for file in files:
#             if(file.endswith('.mp4') or file.endswith('.png') or file.endswith('.jpg')):
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
# #--------------------------------------------------------------------
# def get_all_file_paths(directory):
#     # Initialize an empty list to store the file paths
#     file_paths = []

#     # Use os.walk to iterate over all subdirectories and files in the directory
#     for root, dirs, files in os.walk(directory):
#         for file in files:
#             if(file.endswith('.mp4') or file.endswith('.png') or file.endswith('.jpg')):
#                 # Use os.path.join to construct the full path to the file
#                 file_path = os.path.join(root, file)
#                 # Add the file path to the list
#                 file_paths.append(file_path)
#     return file_paths
# iter = 202401090000
# def move_folder_to_another(scr_dir,dest_dir):
#     global iter
#     os.mkdir(dest_dir)
#     for file in get_all_file_paths(scr_dir):
#         directory, filename = os.path.split(file)
#         os.rename(file,f"{directory}\\{iter}.png")
#         shutil.copy(f"{directory}\\{iter}.png",dest_dir)
#         iter+=1
# move_folder_to_another("D:\Education\Psychology_Chapter_1","D:\Education\Chemistry_1")
# move_folder_to_another("D:\Education\Psychology_Chapter_2","D:\Education\Chemistry_2")
#--------------------------------------------------------------------