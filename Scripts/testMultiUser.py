import threading
import json
import requests


gatewayUrl = "http://localhost:8080/"
userQuantity = 16  # 1, 8, 16
file_id = 319
file_quantity = 100
node_quantity = 10
def get_file(fileId):
    requests.get(f'{gatewayUrl}get-file/{fileId}')



if __name__ =="__main__":
    threads = []

    for (num) in range(userQuantity):
        threads.append(threading.Thread(target=get_file, args=(file_id,)))
        threads[num].start()
        print(f"Comienza descarga de usuario {num + 1}")

    for (num) in range(userQuantity):
        threads[num].join()
        print(f"Termina descarga de usuario {num + 1}")

    #Se obtienen logs
    response = requests.get(f'{gatewayUrl}file/log/{file_id}')
    data = json.loads(response.content)
    data["cantidad_usuarios"] = userQuantity
    data["cantidad_files"] = file_quantity
    data["cantidad_nodos"] = node_quantity
    f = open("results.jsonl", "a")
    f.write(json.dumps(data) + "\n")
    f.close()

