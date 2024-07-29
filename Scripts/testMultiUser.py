import threading
import json
import time
import requests

#gatewayUrl = "http://localhost:8080/"
gatewayUrl = "http://34.121.203.246:8080/"
file_id = 1
file_quantity = 100
node_quantity = 1
processTimes = dict()


def get_file(fileId, num):
    processTimes[num] = time.time()
    requests.get(f'{gatewayUrl}get-file/{fileId}')
    processTimes[num] = time.time() - processTimes[num]


def download_file(user_quantity, it):
    threads = []
    times = []
    requests.post(f"{gatewayUrl}cache/clean/{file_id}")
    for (num) in range(user_quantity):
        threads.append(threading.Thread(target=get_file, args=(file_id,num,)))
        threads[num].start()
        print(f"Comienza descarga de usuario {num + 1}")

    for (num) in range(user_quantity):
        threads[num].join()
        times.append(processTimes[num])
        print(f"Termina descarga de usuario {num + 1}")

    #Se obtienen logs
    response = requests.get(f'{gatewayUrl}file/log/{file_id}')
    data = json.loads(response.content)
    data["cantidad_usuarios"] = user_quantity
    data["cantidad_files"] = file_quantity
    data["cantidad_nodos"] = node_quantity
    data["tiempos_descargas"] = times
    if it == 1:
        f = open("results.jsonl", "a")
    else:
        f = open("results_x.jsonl", "a")
    f.write(json.dumps(data) + "\n")
    f.close()
    processTimes.clear()
    requests.delete(f'{gatewayUrl}file/log/{file_id}')


if __name__ == "__main__":
    iterations = 10
    for i in range(iterations):
        print(f"Iteracion {i + 1}")
        userQuantity = 1
        download_file(userQuantity, iterations)
        userQuantity = 8
        download_file(userQuantity, iterations)
        userQuantity = 16
        download_file(userQuantity, iterations)

    print("Se ejecuta una vez más...")
    iterations = 1
    for i in range(iterations):
        userQuantity = 1
        download_file(userQuantity, iterations)
        userQuantity = 8
        download_file(userQuantity, iterations)
        userQuantity = 16
        download_file(userQuantity, iterations)
