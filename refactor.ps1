$root = "C:\Users\dell\Desktop\java\projet"

$javaDir = "$root\src\main\java"
$resDir = "$root\src\main\resources"

# Create new layout
New-Item -ItemType Directory -Force "$javaDir\com\imagemanager\controller" | Out-Null
New-Item -ItemType Directory -Force "$javaDir\com\imagemanager\model\filters" | Out-Null
New-Item -ItemType Directory -Force "$javaDir\com\imagemanager\service" | Out-Null
New-Item -ItemType Directory -Force "$javaDir\com\imagemanager\util" | Out-Null
New-Item -ItemType Directory -Force "$resDir\com\imagemanager\view" | Out-Null
New-Item -ItemType Directory -Force "$resDir\com\imagemanager\style" | Out-Null

# Mapping of file categories
$mappings = @{
    "Launcher.java" = "com\imagemanager\Launcher.java"
    "Main.java" = "com\imagemanager\MainApp.java"
}

# Move standard files
foreach ($item in Get-ChildItem $javaDir -File) {
    if ($mappings.ContainsKey($item.Name)) {
        Move-Item $item.FullName "$javaDir\$($mappings[$item.Name])" -Force
    }
}

# Move folders
$foldersToMove = @("controller", "model", "service", "util")
foreach ($folder in $foldersToMove) {
    if (Test-Path "$javaDir\$folder") {
        foreach ($file in Get-ChildItem "$javaDir\$folder" -Recurse -File) {
            $relPath = $file.FullName.Substring($javaDir.Length + 1)
            $destPath = "$javaDir\com\imagemanager\$relPath"
            New-Item -ItemType Directory -Force (Split-Path $destPath) | Out-Null
            Move-Item $file.FullName $destPath -Force
        }
        Remove-Item "$javaDir\$folder" -Recurse -Force
    }
}

# Fix missing Package statements & Update Imports in all .java files
foreach ($file in Get-ChildItem "$javaDir\com\imagemanager" -Recurse -File -Filter "*.java") {
    $content = Get-Content $file.FullName -Raw
    
    # Prepend correct package statement
    $relDir = (Split-Path $file.FullName).Substring($javaDir.Length + 1).Replace('\', '.')
    
    # Remove existing package declaration if exists
    $content = $content -replace '(?m)^package\s+[\w\.]+;\s*$', ""
    
    # Add new package declaration
    $content = "package $relDir;`r`n`r`n" + $content.TrimStart()

    # Prepend 'com.imagemanager.' to internal imports
    $content = $content -replace 'import controller\.', 'import com.imagemanager.controller.'
    $content = $content -replace 'import model\.', 'import com.imagemanager.model.'
    $content = $content -replace 'import model\.filters\.', 'import com.imagemanager.model.filters.'
    $content = $content -replace 'import service\.', 'import com.imagemanager.service.'
    $content = $content -replace 'import util\.', 'import com.imagemanager.util.'
    
    # Update Launcher and Main
    $content = $content -replace 'Main\.main\(', 'MainApp.main('
    $content = $content -replace 'public class Main ', 'public class MainApp '

    Set-Content $file.FullName $content
}

# Move Resources
foreach ($file in Get-ChildItem $resDir -File) {
    if ($file.Extension -eq ".fxml") {
        Move-Item $file.FullName "$resDir\com\imagemanager\view\" -Force
    } elseif ($file.Extension -eq ".css") {
        Move-Item $file.FullName "$resDir\com\imagemanager\style\" -Force
    }
}

# Update FXML files
foreach ($file in Get-ChildItem "$resDir\com\imagemanager\view" -File -Filter "*.fxml") {
    $content = Get-Content $file.FullName -Raw
    $content = $content -replace 'fx:controller="controller\.', 'fx:controller="com.imagemanager.controller.'
    # Note: filters_panel.fxml and library_panel.fxml includes don't need changes as they are in same dir
    Set-Content $file.FullName $content
}

# Update Java file resource loading (MainApp.java)
$mainAppPath = "$javaDir\com\imagemanager\MainApp.java"
$mainAppContent = Get-Content $mainAppPath -Raw
$mainAppContent = $mainAppContent -replace '"/main.fxml"', '"/com/imagemanager/view/main.fxml"'
$mainAppContent = $mainAppContent -replace '"/style.css"', '"/com/imagemanager/style/style.css"'
Set-Content $mainAppPath $mainAppContent

# Update pom.xml
$pomPath = "$root\pom.xml"
$pomContent = Get-Content $pomPath -Raw
$pomContent = $pomContent -replace '<mainClass>Main</mainClass>', '<mainClass>com.imagemanager.Launcher</mainClass>'
Set-Content $pomPath $pomContent

Write-Host "Refactoring completed."