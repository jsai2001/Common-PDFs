# -*- coding: utf-8 -*-
"""
Created on Mon Nov 13 14:44:57 2023

@author: Dell
"""

import os
import random
import shutil


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
# os.makedirs(dest_folder)
# for folder in folders:
#     files = os.listdir(folder)
#     for file in files:
#         shutil.move(os.path.join(folder, file), dest_folder)
#----------------------------------------------------------------------------

#----------------------------------------------------------------------------
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
#----------------------------------------------------------------------------

#---------------------------------------------------------------------
# glob = 1
# root_folder = "D:\\Education\\Psychology"
# dest = 'D:\\Education\\TimePass'
# os.makedirs(dest)
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
#--------------------------------------------------------------------

#--------------------------------------------------------------------
# def select_random_files(source_dir, dest_dir, n):
#     files = os.listdir(source_dir)
#     random_files = random.sample(files, n)

#     if os.path.isfile(dest_dir) or os.path.islink(dest_dir):
#         os.unlink(dest_dir)
#     elif os.path.isdir(dest_dir):
#         shutil.rmtree(dest_dir)
#     if not os.path.exists(dest_dir):
#         os.makedirs(dest_dir)

#     for file in random_files:
#         shutil.copy(os.path.join(source_dir, file), dest_dir)

# source_dir = 'D:\\Education\\TimePass'
# dest_dir = 'D:\\Education\\Sample'
# n = 60
# select_random_files(source_dir, dest_dir, n)
#--------------------------------------------------------------------
