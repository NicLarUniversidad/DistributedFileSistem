import time

import requests

from FolderReader import FolderReader

gatewayUrl = "http://localhost:8080/"
reader = FolderReader("files")
files = reader.findTxtAndHtmlFiles()
count = 0
skip = 0

maxFileNumber = 10
for file in files:
    if count >= skip:
        if count - skip < maxFileNumber:
            with open(file, 'rb') as f:
                files = {'file': f}
                requests.post(f'{gatewayUrl}upload-file', files=files)
            time.sleep(0.5)
    count += 1
