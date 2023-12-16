from datetime import datetime, timedelta
from fyers_apiv3 import fyersModel
import pywhatkit as pwk
import json
import requests
import time

with open("./indexstocks.json", "r") as json_file:
    idxstocks = json.load(json_file)

def telegram_bot_sendtext(bot_message):
    bot_token = '6822197469:AAEM9x6L-c9yBTATK_6GZ7Rh7szFtXi83YY'
    bot_chat_id = '6538741403'
    bot_message=bot_message.replace('-','\\-').replace('.','\\.').replace('=','\\=')
    send_text = 'https://api.telegram.org/bot'+bot_token+'/sendMessage?chat_id='+bot_chat_id+'&parse_mode=MarkdownV2&text='+bot_message
    response = requests.get(send_text)
    return response.json()

def stockDetails(response):
    global baseline
    global result
    global stockFlag
    global fyers

    message = ""
    summary = []
    green_move = ["Green Stocks"]
    red_move = ["Red Stocks"]

    # 489
    for i in range(489):
        baseline[i].append(response["d"][i]["v"]["chp"])
        if len(result[i]) == 1:
            result[i].append("Green")
        if len(baseline[i]) > 1:
            if baseline[i][-1] < baseline[i][-2] and result[i][-1] != "Red":
                result[i].append("Red")
                result[i][0] = 1
                stockFlag[i] = "Red"
            elif baseline[i][-1] < baseline[i][-2] and result[i][-1] == "Red":
                result[i].append("Red")
                result[i][0] += 1
                stockFlag[i] = "Red"
            elif baseline[i][-1] > baseline[i][-2] and result[i][-1] != "Green":
                stockFlag[i] = "Green"
                result[i].append("Green")
                result[i][0] = 1
            elif baseline[i][-1] > baseline[i][-2] and result[i][-1] == "Green":
                stockFlag[i] = "Green"
                result[i].append("Green")
                result[i][0] += 1
        if result[i][0] > 2 and baseline[i][-1] >= 0.5:
            if result[i][-1] == "Red":
                red_move.append(response["d"][i]["n"])
            elif result[i][-1] == "Green":
                green_move.append(response["d"][i]["n"])
                summary.append(
                    [
                        stockFlag[i],
                        " || ",
                        response["d"][i]["n"],
                        " || ",
                        str(response["d"][i]["v"]["chp"]) + "%",
                        " || ",
                        "{} Streak:{}".format(stockFlag[i], result[i][0]),
                        " || ",
                        "LP: {} || HP: {} || PCP: {} || OP: {} || ".format(
                            response["d"][i]["v"]["low_price"],
                            response["d"][i]["v"]["high_price"],
                            response["d"][i]["v"]["prev_close_price"],
                            response["d"][i]["v"]["open_price"],
                        ),
                        "https://www.google.com/finance/quote/{0}:NSE?hl=en".format(
                            response["d"][i]["n"][4:-3]
                        ),
                    ]
                )
    message = ""
    if len(summary):
        summary.sort(
            key=lambda x: (float(x[6].split(":")[-1]), x[4][:-1]), reverse=True
        )
        for i in summary[:200]:
            message += "".join(i)
            message += "\n"
        # Define the maximum length of a message
        MAX_MESSAGE_LENGTH = 4096
        message = message.splitlines()
        # Split the string into smaller chunks of the maximum length
        while(len(message)):
            send_message = ""
            while(len(message) and len(send_message+"\n"+message[0])<4097):
                send_message+="\n"+message[0]
                message.pop(0)
            telegram_bot_sendtext(send_message)

client_id = "6JO4FX24PZ-100"
access_token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJhcGkuZnllcnMuaW4iLCJpYXQiOjE3MDI3MTU1MDksImV4cCI6MTcwMjc3MzA0OSwibmJmIjoxNzAyNzE1NTA5LCJhdWQiOlsieDowIiwieDoxIiwieDoyIiwiZDoxIiwiZDoyIiwieDoxIiwieDowIl0sInN1YiI6ImFjY2Vzc190b2tlbiIsImF0X2hhc2giOiJnQUFBQUFCbGZXQjFuRDFfQVNpODdTcWNrZkl3djdtOUk0bFJONjUxTU1zOEJKTFh5Ny1FYUIxUHEteXRXdkpFMVZmanlWVjcxVUJTQWpPdmphcExQU2JkZHBJQ1B3TldGR29VR0p0c2htcDdZUWM1N201V0V0UT0iLCJkaXNwbGF5X25hbWUiOiJKRUVWQU4gU0FJIEtBTkFQQVJUSEkiLCJvbXMiOiJLMSIsImhzbV9rZXkiOiJiZGU4MzRmNzMwMmM0OTNkNGFmNTc5OTk5ZjkxYzhiYzg1ZjNkMzZhYmM3NTRiOWE5YTlhNDYxYyIsImZ5X2lkIjoiWUowMDI5MSIsImFwcFR5cGUiOjEwMCwicG9hX2ZsYWciOiJOIn0.p2Wn1aHe0QDTKBnAjhfqYqkQiQwT0IqT-GdcR_aCotA"
fyers = fyersModel.FyersModel(
    client_id=client_id, token=access_token, is_async=False, log_path=""
)

baseline = [[] for i in range(len(idxstocks["NIFTY_500"]))]
result = [[1] for i in range(len(idxstocks["NIFTY_500"]))]
stockFlag = ["Green" for i in range(len(idxstocks["NIFTY_500"]))]

watch_Stocks = """"""
for stockSymbol in idxstocks["NIFTY_500"]:
    watch_Stocks += """NSE:{0}-EQ,""".format(stockSymbol)
watch_Stocks = watch_Stocks[:-1]
data = {"symbols": watch_Stocks}

while True:    
    response = fyers.quotes(data=data)
    stockDetails(response)
    time.sleep(60)
