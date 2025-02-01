Get-ChildItem -Path C:\ -Recurse -File -ErrorAction SilentlyContinue | 
Where-Object { $_.Length -gt 1GB } | 
Sort-Object Length -Descending | 
Select-Object -First 20 | 
ForEach-Object {
    $size = $_.Length
    $sizeInKB = [math]::Round($size / 1KB, 2)
    $sizeInMB = [math]::Round($size / 1MB, 2)
    $sizeInGB = [math]::Round($size / 1GB, 2)
    
    if ($sizeInGB -ge 1) {
        [PSCustomObject]@{
            FullName = $_.FullName
            Size = "$sizeInGB GB"
        }
    }
    elseif ($sizeInMB -ge 1) {
        [PSCustomObject]@{
            FullName = $_.FullName
            Size = "$sizeInMB MB"
        }
    }
    else {
        [PSCustomObject]@{
            FullName = $_.FullName
            Size = "$sizeInKB KB"
        }
    }
}