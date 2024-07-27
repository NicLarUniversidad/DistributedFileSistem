import requests

from FolderReader import FolderReader

gatewayUrl = "http://localhost:8080/"
reader = FolderReader("files")
files = reader.findTxtAndHtmlFiles()
count = 0
skip = 100

maxFileNumber = 10000
for file in files:
    if count >= skip:
        if count - skip < maxFileNumber:
            with open(file, 'rb') as f:
                files = {'file': f}
                requests.post(f'{gatewayUrl}upload-file', files=files)
    count += 1
