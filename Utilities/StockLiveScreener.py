from fyers_apiv3 import fyersModel
import time
import pywhatkit as pwk
import pyautogui
import json

# import time
from datetime import datetime

with open("./indexstocks.json", "r") as json_file:
    data = json.load(json_file)


def sendWhatsappMessage(receiver_number, message):
    try:
        # Schedule the message to be sent at a specific time (24-hour format)
        now = datetime.now()
        hour = now.hour
        minute = now.minute
        pwk.sendwhatmsg(receiver_number, message, hour, minute + 1)
        time.sleep(10)
        pyautogui.hotkey("enter")
        pyautogui.hotkey("alt", "f4")
        pyautogui.hotkey("enter")

    except Exception as e:
        print("Error: " + str(e))


def stockDetails(response, base):
    global baseline
    global result
    global stockFlag
    global fyers

    message = ""
    summary = ""
    receiver_number = "+919182004425"
    green_move = ["Green Stocks"]
    red_move = ["Red Stocks"]

    neutral_move = ["Neutral Stocks"]

    for i in range(10 * base, 10 * (base + 1)):
        baseline[i].append(response["d"][i % 10]["v"]["chp"])
        if len(result[i]) == 1:
            result[i].append("Green")
        if len(baseline[i]) > 1:
            if baseline[i][-1] < baseline[i][-2]:
                result[i].append("Red")
                result[i][0] = 1
                stockFlag[i] = "Red"
            elif baseline[i][-1] > baseline[i][-2]:
                stockFlag[i] = "Green"
                result[i].append("Green")
                result[i][0] = 1
            else:
                result[i].append(result[i][-1])
                result[i][0] += 1
        if result[i][0] > 2:
            if result[i][-1] == "Red":
                red_move.append(response["d"][i % 10]["n"])
                if response["d"][i % 10]["n"] in green_move:
                    green_move.remove(response["d"][i % 10]["n"])
                # if response["d"][i % 10]["n"] in neutral_move:
                #     neutral_move.remove(response["d"][i % 10]["n"])
            elif result[i][-1] == "Green":
                green_move.append(response["d"][i % 10]["n"])
                if response["d"][i % 10]["n"] in red_move:
                    red_move.remove(response["d"][i % 10]["n"])
                # if response["d"][i % 10]["n"] in neutral_move:
                #     neutral_move.remove(response["d"][i % 10]["n"])
            # else:
            #     neutral_move.append(response["d"][i % 10]["n"])
            #     if response["d"][i % 10]["n"] in green_move:
            #         green_move.remove(response["d"][i % 10]["n"])
            #     if response["d"][i % 10]["n"] in red_move:
            #        red_move.remove(response["d"][i % 10]["n"])
            message += """  
    ====================
    = Stock Name: {0}
    = Stock Link: https://www.google.com/finance/quote/{17}:NSE?hl=en                        
    = Day Change: {1}%                      
    = Value Change: {2}                   
    = Open Price: {3}
    = Close Price: {4}
    = High Price: {5}
    = Low Price:          {6}
    = Time: {7}
    = Curr Volume: {8}
    = Day High: {9}
    = Day low:      {10}
    = Prev Close Price: {11}
    = Spread:             {12}
    = {16} Streak:       {13}
    = Previous 5 baselines: {14}
    = Movement:       {15}
    ====================
                  """.format(
                response["d"][i % 10]["n"],
                response["d"][i % 10]["v"]["chp"],
                response["d"][i % 10]["v"]["ch"],
                response["d"][i % 10]["v"]["cmd"]["o"],
                response["d"][i % 10]["v"]["cmd"]["c"],
                response["d"][i % 10]["v"]["cmd"]["h"],
                response["d"][i % 10]["v"]["cmd"]["l"],
                response["d"][i % 10]["v"]["cmd"]["tf"],
                response["d"][i % 10]["v"]["cmd"]["v"],
                response["d"][i % 10]["v"]["high_price"],
                response["d"][i % 10]["v"]["low_price"],
                response["d"][i % 10]["v"]["prev_close_price"],
                response["d"][i % 10]["v"]["spread"],
                result[i][0],
                baseline[i][:-1] if len(baseline[i]) < 6 else baseline[i][-5:],
                result[i][1:][:-1] if len(result[i][1:]) < 21 else result[i][-20:],
                result[i][-1],
                response["d"][i % 10]["n"][4:-3],
            )
    summary += "\n".join(red_move)
    summary += "\n\n"
    summary += "\n".join(green_move)
    summary += "\n\n"
    summary += "\n".join(neutral_move)
    summary += "\n\n"
    if summary != "" and message != "":
        sendWhatsappMessage(receiver_number, summary + "\n" + message)


client_id = "6JO4FX24PZ-100"
access_token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJhcGkuZnllcnMuaW4iLCJpYXQiOjE3MDI0Mzk0MjIsImV4cCI6MTcwMjUxMzgyMiwibmJmIjoxNzAyNDM5NDIyLCJhdWQiOlsieDowIiwieDoxIiwieDoyIiwiZDoxIiwiZDoyIiwieDoxIiwieDowIl0sInN1YiI6ImFjY2Vzc190b2tlbiIsImF0X2hhc2giOiJnQUFBQUFCbGVTbi0xZlQtZEk2TDh1WU4xbU1XaEZvUDVqTFloMVhQU0x5NEFybmkxNDhzQWF5OFdnR1lLX0tGSVotRHJsRWRBNDgxOFJKRU1vRjVWQTM0SkFBeDQ4X2ViSjV6aHUtMndCNGtqNmZnM2h6NFBmcz0iLCJkaXNwbGF5X25hbWUiOiJKRUVWQU4gU0FJIEtBTkFQQVJUSEkiLCJvbXMiOiJLMSIsImhzbV9rZXkiOiJiZGU4MzRmNzMwMmM0OTNkNGFmNTc5OTk5ZjkxYzhiYzg1ZjNkMzZhYmM3NTRiOWE5YTlhNDYxYyIsImZ5X2lkIjoiWUowMDI5MSIsImFwcFR5cGUiOjEwMCwicG9hX2ZsYWciOiJOIn0.E4LGqfMXSV64jzgCXZrhfWG1xdNzP-DYuzNbTXtfFAQ"
# Initialize the FyersModel instance with your client_id, access_token, and enable async mode
fyers = fyersModel.FyersModel(
    client_id=client_id, token=access_token, is_async=False, log_path=""
)

baseline = [[] for i in range(len(data["NIFTY_50"]))]
result = [[1] for i in range(len(data["NIFTY_50"]))]
stockFlag = [["Green"] for i in range(len(data["NIFTY_50"]))]
while True:
    for i in range(0, 5):
        watch_Stocks = """"""
        for stockSymbol in data["NIFTY_50"][10 * i : 10 * (i + 1)]:
            watch_Stocks += """NSE:{0}-EQ,""".format(stockSymbol)
        watch_Stocks = watch_Stocks[:-1]

        sub_data = {
            # "symbols":"""NSE:SIEMENS-EQ,NSE:BAJAJELEC-EQ,NSE:JIOFIN-EQ,NSE:MAZDOCK-EQ,NSE:SUNPHARMA-EQ,NSE:DIXON-EQ,NSE:RVNL-EQ,NSE:ASTERDM-EQ,NSE:SWSOLAR-EQ,NSE:J&KBANK-EQ,NSE:BLS-EQ"""
            "symbols": watch_Stocks
            # "symbols":"""NSE:SIEMENS-EQ"""
        }
        response = fyers.quotes(data=sub_data)
        stockDetails(response, i)

""" 
------------------------------------------------------------------------------------------------------------------------------------------
 Sample Success Response 
 ------------------------------------------------------------------------------------------------------------------------------------------
  {
    "code":200,
    "d":[
        {
          "n":"NSE:SBIN-EQ",
          "s":"ok",
          "v":{
              "ask":0,
              "bid":590.5,
              "ch":-7.95,
              "chp":-1.33,
              "cmd":{
                "c":590.5,
                "h":590.5,
                "l":590.5,
                "o":590.5,
                "t":1691058540,
                "tf":"15:59",
                "v":200
              },
              "description":"NSE:SBIN-EQ",
              "exchange":"NSE",
              "fyToken":"10100000003045",
              "high_price":600.85,
              "low_price":585,
              "lp":590.5,
              "open_price":598.7,
              "original_name":"NSE:SBIN-EQ",
              "prev_close_price":598.45,
              "short_name":"SBIN-EQ",
              "spread":-590.5,
              "symbol":"NSE:SBIN-EQ",
              "tt":"1691020800",
              "volume":27774877
          }
        },
        {
          "n":"NSE:IDEA-EQ",
          "s":"ok",
          "v":{
              "ask":7.85,
              "bid":0,
              "ch":-0.05,
              "chp":-0.63,
              "cmd":{
                "c":7.85,
                "h":7.85,
                "l":7.85,
                "o":7.85,
                "t":1691058540,
                "tf":"15:59",
                "v":250
              },
              "description":"NSE:IDEA-EQ",
              "exchange":"NSE",
              "fyToken":"101000000014366",
              "high_price":8.05,
              "low_price":7.8,
              "lp":7.85,
              "open_price":7.9,
              "original_name":"NSE:IDEA-EQ",
              "prev_close_price":7.9,
              "short_name":"IDEA-EQ",
              "spread":7.85,
              "symbol":"NSE:IDEA-EQ",
              "tt":"1691020800",
              "volume":78116800
          }
        }
    ],
    "message":"",
    "s":"ok"
  }
"""
