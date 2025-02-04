#Requires -RunAsAdministrator

# Function to get free space in GB
function Get-FreeSpace {
    param (
        [string]$Drive = $env:SystemDrive
    )
    $disk = Get-WmiObject Win32_LogicalDisk -Filter "DeviceID='$Drive'"
    return [math]::Round($disk.FreeSpace / 1GB, 2)
}

# Function to clear temporary files
function Clear-TempFiles {
    try {
        Remove-Item -Path "$env:TEMP*" -Recurse -Force -ErrorAction SilentlyContinue
        Write-Host "Temporary files cleared."
    } catch {
        Write-Host "Error clearing temporary files: $_"
    }
}

# Function to clear browser cache
function Clear-BrowserCache {
    param (
        [string[]]$Browsers = @("Chrome", "Firefox", "Edge")
    )
    
    foreach ($browser in $Browsers) {
        switch ($browser) {
            "Chrome" {
                $path = "$env:LOCALAPPDATA\Google\Chrome\User Data\Default\Cache"
            }
            "Firefox" {
                $path = "$env:LOCALAPPDATA\Mozilla\Firefox\Profiles\*.default\cache2"
            }
            "Edge" {
                $path = "$env:LOCALAPPDATA\Microsoft\Edge\User Data\Default\Cache"
            }
            default {
                Write-Host "Unsupported browser: $browser"
                continue
            }
        }
        
        if (Test-Path $path) {
            Remove-Item -Path $path -Recurse -Force -ErrorAction SilentlyContinue
            Write-Host "$browser cache cleared."
        }
    }
}

# Function to clear Chrome browser cache for specific profiles
function Clear-ChromeBrowserCache {
    param (
        [string[]]$Browsers = @("Chrome")
    )
    
    foreach ($browser in $Browsers) {
        switch ($browser) {
            "Chrome" {
                $profiles = @("Profile 1", "Profile 2", "Profile 3", "Profile 4", "Profile 5", "Profile 6", "Profile 8")
                foreach ($profile in $profiles) {
                    $path = "C:\Users\Dell\AppData\Local\Google\Chrome\User Data\$profile\Cache\Cache_Data"
                    if (Test-Path $path) {
                        try {
                            Remove-Item -Path $path -Recurse -Force -ErrorAction Stop
                            Write-Host "Cache for $browser in $profile cleared."
                        } catch {
                            # Using string concatenation to avoid issues with variable reference
                            Write-Host "Error clearing cache for $browser in $profile " + $_.ToString()
                        }
                    } else {
                        # Silently neglect if the path does not exist
                        # Write-Host "Cache path for $browser in $profile not found: $path" # Uncomment this if you want to see which paths were not found
                    }
                }
            }
            default {
                Write-Host "Unsupported browser: $browser"
                continue
            }
        }
    }
}

# Function to empty Recycle Bin
function Empty-RecycleBin {
    try {
        Clear-RecycleBin -Force -ErrorAction Stop
        Write-Host "Recycle Bin has been emptied."
    } catch {
        Write-Host "Error emptying Recycle Bin: $_"
    }
}

# Function to clean old Windows updates
function Clean-OldWindowsUpdates {
    try {
        $CleanupCommand = "dism.exe /online /Cleanup-Image /StartComponentCleanup /ResetBase"
        Start-Process -FilePath "cmd.exe" -ArgumentList "/c", $CleanupCommand -Wait -NoNewWindow
        Write-Host "Old Windows updates cleaned."
    } catch {
        Write-Host "Error cleaning old Windows updates: $_"
    }
}

# Function to remove unnecessary log files
function Remove-LogFiles {
    $logPaths = @(
        "$env:WINDIR\Logs",
        "$env:WINDIR\Temp",
        "$env:LOCALAPPDATA\Temp"
    )
    
    foreach ($logPath in $logPaths) {
        if (Test-Path $logPath) {
            Get-ChildItem -Path $logPath -Include *.log -Recurse | Remove-Item -Force -ErrorAction SilentlyContinue
        }
    }
    Write-Host "Log files removed."
}

# Function to clear Windows log files with specific categories
function Clear-WindowsLogFiles {
    $logFile = "$env:LOCALAPPDATA\CleanupLog.txt"
    $logDir = Split-Path -Path $logFile -Parent
    if (-not (Test-Path $logDir)) {
        New-Item -ItemType Directory -Path $logDir -Force | Out-Null
    }

    $categories = @(
        @{Name = ".NET Framework logs"; Path = "$env:WINDIR\Microsoft.NET\Framework\v*.*.*\*.log"},
        @{Name = "Security logs"; Path = "$env:WINDIR\System32\winevt\Logs\Security.evtx"},
        @{Name = "Setup Logs"; Path = "$env:WINDIR\Logs\CBS\*.log"},
        @{Name = "System logs"; Path = "$env:WINDIR\System32\winevt\Logs\System.evtx"},
        @{Name = "Upgrade Log Files"; Path = "$env:WINDIR\Logs\MOSETUP\*.log"}
    )

    foreach ($category in $categories) {
        $categoryPath = $category.Path
        if (Test-Path $categoryPath) {
            try {
                Get-ChildItem -Path $categoryPath -Recurse -ErrorAction Stop | Remove-Item -Force -ErrorAction Stop
                Write-Host "$($category.Name) cleared."
            } catch {
                "Failed to clear $($category.Name): $_" | Out-File -FilePath $logFile -Append
                Write-Host "Error clearing $($category.Name): $_"
            }
        } else {
            Write-Host "$($category.Name) path not found: $categoryPath"
        }
    }
}

# Function to clear program cache files
function Clear-ProgramCacheFiles {
    $paths = @(
        "$env:LOCALAPPDATA\Microsoft\Windows\INetCache",
        "$env:LOCALAPPDATA\Microsoft\Windows\Temporary Internet Files"
    )
    foreach ($path in $paths) {
        if (Test-Path $path) {
            Remove-Item -Path "$path*" -Recurse -Force -ErrorAction SilentlyContinue
        }
    }
    Write-Host "Program cache files cleared."
}

# Function to clear Windows cache files
function Clear-WindowsCacheFiles {
    $paths = @(
        "$env:LOCALAPPDATA\Microsoft\Windows\Explorer",
        "$env:LOCALAPPDATA\Microsoft\Windows\AppCompat\Programs"
    )
    foreach ($path in $paths) {
        if (Test-Path $path) {
            Remove-Item -Path "$path*" -Recurse -Force -ErrorAction SilentlyContinue
        }
    }
    Write-Host "Windows cache files cleared."
}

# Function to clear program temp files
function Clear-ProgramTempFiles {
    $programTemp = "$env:LOCALAPPDATA\Temp"
    if (Test-Path $programTemp) {
        Remove-Item -Path "$programTemp*" -Recurse -Force -ErrorAction SilentlyContinue
    }
    Write-Host "Program temp files cleared."
}

# Function to clear program log files with error logging
function Clear-ProgramLogFiles {
    $programLogPaths = @(
        "$env:LOCALAPPDATA\Temp",
        "$env:PROGRAMDATA"
    )
    # Change the log file path to somewhere you know you have write access, or ensure the directory exists
    $logFile = "$env:LOCALAPPDATA\CleanupLog.txt"
    
    # Ensure the directory exists before writing
    $logDir = Split-Path -Path $logFile -Parent
    if (-not (Test-Path $logDir)) {
        New-Item -ItemType Directory -Path $logDir -Force | Out-Null
    }
    
    foreach ($path in $programLogPaths) {
        if (Test-Path $path) {
            try {
                Get-ChildItem -Path $path -Recurse -Include *.log -ErrorAction Stop | Remove-Item -Force -ErrorAction Stop
            } catch [System.UnauthorizedAccessException] {
                "Access denied to path: $path. Error: $_" | Out-File -FilePath $logFile -Append
            } catch {
                "Error occurred while cleaning $path. Error: $_" | Out-File -FilePath $logFile -Append
            }
        }
    }
    Write-Host "Program log files cleared, where possible. Check $logFile for any errors."
}

# Function to clear program download files
function Clear-ProgramDownloadFiles {
    $downloadPath = "$env:USERPROFILE\Downloads"
    if (Test-Path $downloadPath) {
        Get-ChildItem -Path $downloadPath -Recurse -Include *.exe, *.zip, *.msi, *.iso | Remove-Item -Force -ErrorAction SilentlyContinue
    }
    Write-Host "Program download files cleared."
}

# Function to clear program backup files
function Clear-ProgramBackupFiles {
    $backupPaths = @(
        "$env:USERPROFILE\Documents\Backups",
        "$env:USERPROFILE\Backups"
    )
    foreach ($path in $backupPaths) {
        if (Test-Path $path) {
            Remove-Item -Path "$path*" -Recurse -Force -ErrorAction SilentlyContinue
        }
    }
    Write-Host "Program backup files cleared."
}

# Main execution
Write-Host "Starting disk cleaning process..."

# Measure free space before cleaning
$before = Get-FreeSpace

Clear-TempFiles
Clear-BrowserCache
Clear-ChromeBrowserCache
Empty-RecycleBin
Clean-OldWindowsUpdates
Remove-LogFiles
Clear-WindowsLogFiles
Clear-ProgramCacheFiles
Clear-WindowsCacheFiles
Clear-ProgramTempFiles
Clear-ProgramLogFiles
Clear-ProgramDownloadFiles
Clear-ProgramBackupFiles

# Measure free space after cleaning
$after = Get-FreeSpace

# Calculate and display the space saved
$saved = [math]::Round($after - $before, 2)
Write-Host "Disk cleaning process completed. Total space saved: $saved GB"