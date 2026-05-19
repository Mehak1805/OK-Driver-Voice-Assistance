#!/usr/bin/env python3
import urllib.request
import os
import shutil

# Create gradle/wrapper directory if it doesn't exist
os.makedirs("gradle/wrapper", exist_ok=True)

# Download gradle-wrapper.jar for gradle 8.2
url = "https://raw.githubusercontent.com/gradle/gradle/v8.2.0/gradle/wrapper/gradle-wrapper.jar"
dest = "gradle/wrapper/gradle-wrapper.jar"

print(f"Downloading {url}...")
try:
    urllib.request.urlretrieve(url, dest)
    print(f"✓ Successfully downloaded to {dest}")
except Exception as e:
    print(f"✗ Failed to download: {e}")
    print("Trying alternative URL...")
    # Try Maven Central mirror
    url = "https://repo1.maven.org/maven2/org/gradle/gradle-wrapper/8.2/gradle-wrapper-8.2.jar"
    try:
        urllib.request.urlretrieve(url, dest)
        print(f"✓ Successfully downloaded from mirror to {dest}")
    except Exception as e2:
        print(f"✗ Mirror download also failed: {e2}")
        print("\nManual download required:")
        print(f"1. Visit: https://gradle.org/releases")
        print(f"2. Download Gradle 8.2")
        print(f"3. Extract gradle-wrapper.jar from gradle/wrapper/ folder")
        print(f"4. Place it in: {os.path.abspath(dest)}")
