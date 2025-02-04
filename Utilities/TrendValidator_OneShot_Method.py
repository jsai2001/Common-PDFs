from datetime import datetime, timedelta
from fyers_apiv3 import fyersModel
import pywhatkit as pwk
import json

with open("./indexstocks.json", "r") as json_file:
    idxstocks = json.load(json_file)


def sendWhatsappMessage(receiver_number, message):
    try:
        now = datetime.now()
        time_change = timedelta(minutes=1)
        new_time = now + time_change
        hour = new_time.hour
        minute = new_time.minute

        message = str(now) + "\n\n" + message
        pwk.sendwhatmsg(receiver_number, message, hour, minute, tab_close=True)
    except Exception as e:
        print("Error: " + str(e))


def stockDetails(response):
    global baseline
    global result
    global stockFlag
    global fyers

    message = ""
    summary = []
    receiver_number = "+919182004425"
    green_move = ["Green Stocks"]
    red_move = ["Red Stocks"]

    now = datetime.now()
    print(now)

    # 489
    for i in range(489):
        baseline[i].append(response["d"][i]["v"]["chp"])
        if len(result[i]) == 1:
            result[i].append("Green")
        if len(baseline[i]) > 1:
            if baseline[i][-1] < baseline[i][-2] and baseline[i][-2] != "Red":
                result[i].append("Red")
                result[i][0] = 1
                stockFlag[i] = "Red"
            elif baseline[i][-1] < baseline[i][-2] and baseline[i][-2] == "Red":
                result[i].append("Red")
                result[i][0] += 1
                stockFlag[i] = "Red"
            elif baseline[i][-1] >= baseline[i][-2] and baseline[i][-2] != "Green":
                stockFlag[i] = "Green"
                result[i].append("Green")
                result[i][0] = 1
                print(
                    "{0} : Earlier is Red , currently green started".format(
                        response["d"][i]["n"]
                    )
                )
            elif baseline[i][-1] >= baseline[i][-2] and baseline[i][-2] == "Green":
                stockFlag[i] = "Green"
                result[i].append("Green")
                result[i][0] += 1
                print("{0} : Already Green".format(response["d"][i]["n"]))
        if result[i][0] > 2:
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
        sendWhatsappMessage(receiver_number, message)


client_id = "6JO4FX24PZ-100"
access_token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJhcGkuZnllcnMuaW4iLCJpYXQiOjE3MDI1MjU3MDIsImV4cCI6MTcwMjYwMDIyMiwibmJmIjoxNzAyNTI1NzAyLCJhdWQiOlsieDowIiwieDoxIiwieDoyIiwiZDoxIiwiZDoyIiwieDoxIiwieDowIl0sInN1YiI6ImFjY2Vzc190b2tlbiIsImF0X2hhc2giOiJnQUFBQUFCbGVuc0dlaVdXSEx1WkdZNVNSdjVGcGtyV0ticDFDUVRWcUxjc2NGd2k5N2VVOFRNdWtDTEZ4ZmdienBYRml0R2g5Z3d3QXJZcUtKNU1QQ1BfYThyTkFmcS01cHlRNW5OSkpPTE93bmNkN3BWUjk2MD0iLCJkaXNwbGF5X25hbWUiOiJKRUVWQU4gU0FJIEtBTkFQQVJUSEkiLCJvbXMiOiJLMSIsImhzbV9rZXkiOiJiZGU4MzRmNzMwMmM0OTNkNGFmNTc5OTk5ZjkxYzhiYzg1ZjNkMzZhYmM3NTRiOWE5YTlhNDYxYyIsImZ5X2lkIjoiWUowMDI5MSIsImFwcFR5cGUiOjEwMCwicG9hX2ZsYWciOiJOIn0.tBHzBUrvsjX3y_i77wtjo58jnvVfKc8HWv7Ol86-PD8"
fyers = fyersModel.FyersModel(
    client_id=client_id, token=access_token, is_async=False, log_path=""
)

baseline = [[] for i in range(len(idxstocks["NIFTY_500"]))]
result = [[1] for i in range(len(idxstocks["NIFTY_500"]))]
stockFlag = ["Green" for i in range(len(idxstocks["NIFTY_500"]))]

while True:
    watch_Stocks = """"""
    for stockSymbol in idxstocks["NIFTY_500"]:
        watch_Stocks += """NSE:{0}-EQ,""".format(stockSymbol)
    watch_Stocks = watch_Stocks[:-1]
    data = {"symbols": watch_Stocks}
    response = fyers.quotes(data=data)
    stockDetails(response)
