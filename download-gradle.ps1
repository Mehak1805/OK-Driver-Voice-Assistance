# Download gradle-wrapper.jar for gradle 8.2
$wrapperPath = "gradle\wrapper\gradle-wrapper.jar"
$dir = "gradle\wrapper"

# Create directory if not exists
if (!(Test-Path $dir)) {
    New-Item -ItemType Directory -Path $dir -Force | Out-Null
}

# Try GitHub source
$url = "https://github.com/gradle/gradle/raw/v8.2.0/gradle/wrapper/gradle-wrapper.jar"

try {
    Write-Host "Downloading gradle-wrapper.jar..."
    [Net.ServicePointManager]::SecurityProtocol = [Net.SecurityProtocolType]::Tls12
    (New-Object System.Net.WebClient).DownloadFile($url, $wrapperPath)
    Write-Host "Successfully downloaded gradle-wrapper.jar"
} catch {
    Write-Host "Failed to download. Recommendation: Use Android Studio"
}
