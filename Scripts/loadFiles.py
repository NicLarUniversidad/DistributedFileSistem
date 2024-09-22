import time

import requests

from FolderReader import FolderReader

gatewayUrl = "http://34.44.246.72:8080/"
reader = FolderReader("files")
files = reader.findTxtAndHtmlFiles()
count = 0
skip = 10000

maxFileNumber = 90000
for file in files:
    if count >= skip:
        if count - skip < maxFileNumber:
            with open(file, 'rb') as f:
                body = {'x-chunk': 0, 'chunk-append': False}
                file = {'file': f}

                requests.post(url=f'{gatewayUrl}upload-file', data=body, files=file)
            time.sleep(0.5)
    count += 1

