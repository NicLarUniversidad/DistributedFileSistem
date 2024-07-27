import time

import requests

from FolderReader import FolderReader

gatewayUrl = "http://localhost:8080/"
reader = FolderReader("files")
files = reader.findTxtAndHtmlFiles()

for file in files:
    with open(file, 'rb') as f:
        files = {'file': f}
        requests.post(f'{gatewayUrl}upload-file', files=files)
