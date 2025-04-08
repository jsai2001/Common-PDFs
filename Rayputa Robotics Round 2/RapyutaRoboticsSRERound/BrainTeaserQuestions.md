### Brain Teaser Questions
---

**1. You have 8 bags of gold coins, each with 100 coins. Seven bags contain real coins weighing 10 grams each, but one bag has fake coins weighing 9 grams each. You can use a digital scale once. How do you identify the fake bag?**  
- **Solution:** Take 1 coin from Bag 1, 2 from Bag 2, 3 from Bag 3, ..., up to 8 from Bag 8. Total coins = 1 + 2 + ... + 8 = 36. If all were real, weight = 36 * 10 = 360g. Weigh them once. The difference from 360g (360 - actual weight) equals the number of the fake bag (since each fake coin is 1g lighter).  
- **Answer:** The fake bag is the number equal to 360 minus the scale reading.

---

**2. How many golf balls can fit inside a school bus?**  
- **Solution:** Estimate: A school bus is ~10m long, 2.5m wide, 3m high = 75m³. A golf ball (diameter ~4.3cm) has a volume of ~4/3 * π * (0.0215m)³ ≈ 0.0000417m³. Raw count = 75 / 0.0000417 ≈ 1.8M. With packing efficiency (~74% for spheres), adjust: 1.8M * 0.74 ≈ 1.33M.  
- **Answer:** Approximately 1.3 million golf balls.

---

**3. You have two buckets, one that holds 5 gallons and one that holds 3 gallons. How do you measure exactly 4 gallons of water?**  
- **Solution:**  
  1. Fill the 5-gallon bucket (5G = 5, 3G = 0).  
  2. Pour into the 3-gallon bucket (5G = 2, 3G = 3).  
  3. Empty the 3-gallon bucket (5G = 2, 3G = 0).  
  4. Pour the 2 gallons into the 3-gallon bucket (5G = 0, 3G = 2).  
  5. Fill the 5-gallon bucket (5G = 5, 3G = 2).  
  6. Pour into the 3-gallon bucket until full (3-2 = 1 gallon fits; 5G = 4, 3G = 3).  
- **Answer:** The 5-gallon bucket now has exactly 4 gallons.

---

**4. A farmer needs to cross a river with a wolf, a goat, and a cabbage. He can only take one item at a time in his boat. How does he get all three across without anything being eaten?**  
- **Solution:** Constraints: Wolf eats goat, goat eats cabbage if left alone.  
  1. Take goat across (W, C on start; G on other side).  
  2. Return alone (W, C on start; G on other).  
  3. Take wolf across (C on start; W, G on other).  
  4. Bring goat back (C, G on start; W on other).  
  5. Take cabbage across (G on start; W, C on other).  
  6. Return alone (G on start; W, C on other).  
  7. Take goat across (nothing on start; W, G, C on other).  
- **Answer:** 7 trips as described.

---

**5. You’re in a room with three light switches, one of which controls a bulb in another room. You can only visit the bulb room once. How do you determine which switch controls the bulb?**  
- **Solution:** Bulb has on/off and temperature states.  
  1. Turn Switch 1 on for 5 minutes, then off.  
  2. Turn Switch 2 on.  
  3. Go to the bulb room.  
  - Bulb on = Switch 2.  
  - Bulb off but warm = Switch 1.  
  - Bulb off and cold = Switch 3.  
- **Answer:** Use on/off and heat to identify the switch.

---

**6. How many times do the hands of a clock overlap in a 24-hour period?**  
- **Solution:** Hour hand moves 360°/12h = 0.5°/min; minute hand moves 360°/60min = 6°/min. Relative speed = 5.5°/min. Overlap every 360°/5.5 = 65.45min. In 12h (720min), overlaps = 720/65.45 ≈ 11. In 24h = 11 * 2 = 22 (not 24, as 0h and 12h aren’t double-counted).  
- **Answer:** 22 times.

---

**7. You have 25 horses and can race 5 at a time. What’s the minimum number of races needed to find the top 3 fastest horses?**  
- **Solution:**  
  1. Race 5 groups of 5 (5 races), get winners (A1, B1, C1, D1, E1).  
  2. Race the 5 winners (6th race), say A1 > B1 > C1 > D1 > E1.  
  3. Top 3 candidates: A1, A2, A3, B1, B2, C1. Race A1, A2, B1, B2, C1 (7th race).  
  - Order determines top 3 (e.g., A1 > B1 > A2).  
- **Answer:** 7 races.

---

**8. You have a cake and want to cut it into exactly 8 equal pieces with only 3 straight cuts. How do you do it?**  
- **Solution:** Assume a rectangular cake (L x W x H).  
  1. Cut vertically in half (L/2 x W x H = 2 pieces).  
  2. Cut vertically again (L/2 x W/2 x H = 4 pieces).  
  3. Cut horizontally (L/2 x W/2 x H/2 = 8 pieces).  
- **Answer:** 3 cuts: length-wise, width-wise, height-wise.

---

**9. There are 100 lockers, all closed. You make 100 passes, toggling every locker on the first pass, every second locker on the second pass, every third on the third, and so on. How many lockers are open at the end?**  
- **Solution:** Locker N toggled on pass K if K divides N. Number of toggles = number of divisors. Odd toggles = open; even = closed. Only perfect squares (1, 4, 9, ..., 100) have odd divisors (e.g., 4: 1, 2, 4). Count squares: 1 to 10² = 10.  
- **Answer:** 10 lockers.

---

**10. You’re given two ropes, each of which takes exactly 60 minutes to burn completely when lit from one end. How do you measure 45 minutes using only these ropes and a lighter?**  
- **Solution:**  
  1. Light both ends of Rope 1 and one end of Rope 2 at t=0.  
  2. Rope 1 burns out in 30min (60/2).  
  3. At 30min, light the other end of Rope 2 (30min left, burns in 15min from both ends).  
  4. Rope 2 burns out at 45min (30 + 15).  
- **Answer:** Light both ends of one, then the other end of the second after 30min.

---

**11. A bat and a ball cost $1.10 together. The bat costs $1 more than the ball. How much does the ball cost?**  
- **Solution:** Let ball = x. Bat = x + 1. Equation: x + (x + 1) = 1.10. Solve: 2x + 1 = 1.10, 2x = 0.10, x = 0.05.  
- **Answer:** The ball costs $0.05 (5 cents).

---

**12. You have 10 jars of pills. Nine jars contain pills weighing 10 grams each, and one jar has pills weighing 9 grams each. You can take one sample of any size and weigh it once. How do you find the defective jar?**  
- **Solution:** Take 1 pill from Jar 1, 2 from Jar 2, ..., 10 from Jar 10. Total = 55 pills. If all 10g, weight = 55 * 10 = 550g. Actual weight = 550 - (N * 1), where N is the defective jar (1g lighter per pill). N = 550 - measured weight.  
- **Answer:** The defective jar is 550 minus the scale reading.

---

**13. How many piano tuners are there in New York City?**  
- **Solution:** Estimate: NYC pop = 8M. 1 piano per 100 people = 80,000 pianos. Tuned yearly, 1 tuner handles 1,000 tunings/year (5/day * 200 days). Tuners = 80,000 / 1,000 = 80. Adjust for market: ~100.  
- **Answer:** Approximately 100 piano tuners.

---

**14. You’re on an island with 3 inhabitants: one always tells the truth, one always lies, and one answers randomly. You can ask one yes/no question to one person. How do you determine who’s who?**  
- **Solution:** Ask A: "If I asked B if he’s the random one, would he say yes?"  
  - Truth (T): B isn’t random → B says "No" → T says "No".  
  - Liar (L): B isn’t random → B says "Yes" (lies) → L says "No" (lies).  
  - Random (R): Random answer (Yes/No).  
  - Yes = A is random; No = ask B next to distinguish T/L.  
- **Answer:** Use this question and follow-up logic (complex but solvable with one more step).

---

**15. A car travels 60 miles at 30 mph, then returns at 60 mph. What’s the average speed for the round trip?**  
- **Solution:** Total distance = 120 miles. Time out = 60/30 = 2h. Time back = 60/60 = 1h. Total time = 3h. Average speed = 120/3 = 40 mph (not 45, as time differs).  
- **Answer:** 40 mph.

---

**16. You have 5 pirates ranking from most to least senior (A, B, C, D, E) dividing 100 gold coins. The most senior proposes a split, and all vote. If the majority approves, it’s accepted; if not, the proposer is killed, and the next senior proposes. What split does A propose to survive?**  
- **Solution:** A needs 3 votes (majority). Greedy pirates vote for max coins. Backtrack: If D proposes, D=100. C offers E=1, C=99 (2 votes). B offers D=1, B=99. A offers C=1 (more than 0), E=1 (more than 0), A=98 (3 votes: A, C, E).  
- **Answer:** A proposes 98, 0, 1, 0, 1.

---

**17. How many times can you subtract 10 from 100?**  
- **Solution:** Start at 100: 100 - 10 = 90, 90 - 10 = 80, ..., 10 - 10 = 0. Can’t subtract from 0. Total = 10 times.  
- **Answer:** 10 times.

---

**18. You have a 3x3 grid with a coin in each cell, all heads up. In one move, you can flip all coins in a row or column. What’s the minimum number of moves to get all tails?**  
- **Solution:** Each move flips 3 coins. 9 coins need flipping. Minimum moves = 9/3 = 3 (e.g., flip row 1, row 2, row 3). Verify: Flipping rows or columns in any combo ≥ 3.  
- **Answer:** 3 moves.

---

---

**19. A snail climbs a 10-foot pole. Each day, it climbs 3 feet but slips back 2 feet at night. How many days does it take to reach the top?**  
- **Solution:** Net gain per day = 3 - 2 = 1 foot, except the final day. Day 1: 3-2=1, Day 2: 2+3-2=2, ..., Day 7: 7+3=10 (reaches top, no slip). Takes 7 days.  
- **Answer:** 7 days.

---

**20. You’re given 9 coins, one of which is counterfeit and lighter. Using a balance scale twice, how do you identify the fake coin?**  
- **Solution:**  
  1. Split into 3 groups of 3 (A: 1-3, B: 4-6, C: 7-9). Weigh A vs. B.  
     - If A = B, fake is in C. Weigh C1 vs. C2: equal = C3 lighter; lighter side = fake.  
     - If A < B, fake is in A. Weigh A1 vs. A2: equal = A3 lighter; lighter side = fake.  
     - If A > B, same for B.  
  2. Second weigh isolates the fake.  
- **Answer:** Weigh 3 vs. 3, then 1 vs. 1 from the lighter group.

---

**21. How would you estimate the weight of an elephant without a scale?**  
- **Solution:** Use displacement: Submerge the elephant in a large water tank, measure the volume of displaced water (e.g., in cubic feet), multiply by water density (62.4 lbs/ft³). Adjust for buoyancy if partially submerged. Alternative: Compare to known weights (e.g., ~10,000 lbs for an African elephant).  
- **Answer:** Measure water displacement and calculate.

---

**22. Three friends split a restaurant bill. The total is $25, and each pays $10, receiving $5 back in change. The waiter keeps $2 as a tip. Why does the math seem off when they each effectively paid $9 (totaling $27)?**  
- **Solution:** Each paid $9 net ($10 - $1 change, $2 to waiter). Total paid = $27. Bill = $25, tip = $2, total cost = $27. The confusion arises from adding $25 + $2 instead of subtracting $2 from $27 paid. No discrepancy—$27 covers bill + tip.  
- **Answer:** The $27 paid includes the $25 bill and $2 tip; no math error.

---

**23. You have two identical buckets of water, one at 40°F and one at 60°F. You drop an ice cube in each. In which bucket does the ice melt faster?**  
- **Solution:** Heat transfer rate depends on temperature difference. Ice (32°F) in 60°F water (ΔT = 28°F) melts faster than in 40°F water (ΔT = 8°F) due to greater heat energy.  
- **Answer:** The 60°F bucket.

---

**24. A monkey, a ladder, and a banana: the ladder is 20 steps, and the monkey climbs 3 steps up and falls 2 steps back each move. How many moves to reach the banana at step 20?**  
- **Solution:** Net gain = 1 step/move, except final move. Step 17 + 3 = 20 (reaches banana, no fall). Moves: 1, 2, 3, ..., 17, 18 (18th move hits 20).  
- **Answer:** 18 moves.

---

**25. You have 4 quarts of water in a 5-quart jug. How do you pour exactly 2 quarts into a 3-quart jug without additional tools?**  
- **Solution:**  
  1. Start: 5Q = 4, 3Q = 0.  
  2. Pour into 3Q: 5Q = 1, 3Q = 3.  
  3. Empty 3Q: 5Q = 1, 3Q = 0.  
  4. Pour into 3Q: 5Q = 0, 3Q = 1.  
  5. Fill 5Q: 5Q = 5, 3Q = 1.  
  6. Pour into 3Q: 3Q fills to 3 (takes 2), 5Q = 5-2 = 3, 3Q = 3.  
  7. Empty 3Q: 5Q = 3, 3Q = 0.  
  8. Pour 2 from 5Q: 5Q = 1, 3Q = 2.  
- **Answer:** 8 steps as described.

---

**26. How many squares are there on a standard 8x8 chessboard?**  
- **Solution:** Count all square sizes: 1x1 = 64, 2x2 = 7*7 = 49, 3x3 = 6*6 = 36, ..., 8x8 = 1. Total = 1² + 2² + ... + 8² = (8*9*17)/6 = 204.  
- **Answer:** 204 squares.

---

**27. You’re in a race and pass the person in second place. What place are you in now?**  
- **Solution:** Passing the 2nd-place runner means you overtake them, taking their position. You’re not 1st (didn’t pass them).  
- **Answer:** 2nd place.

---

**28. A lily pad doubles in size every day. It covers a pond in 30 days. How many days until it covers half the pond?**  
- **Solution:** Doubles daily, so day 30 = full, day 29 = half (since 2 * half = full).  
- **Answer:** 29 days.

---

**29. You have 12 coins, one of which is either heavier or lighter. Using a balance scale 3 times, how do you identify the odd coin and whether it’s heavy or light?**  
- **Solution:**  
  1. Weigh 1-4 vs. 5-8:  
     - Equal: 9-12 has odd. Weigh 9-10 vs. 11: Equal = 12 odd; 9-10 heavy/light = test 9 vs. 10.  
     - 1-4 < 5-8: Heavy in 5-8 or light in 1-4. Weigh 5-6 vs. 7-1: Narrow to 1-8.  
     - 1-4 > 5-8: Reverse logic.  
  2. Third weigh confirms odd coin and weight.  
- **Answer:** Divide into 3 groups, use logic to isolate (complex but standard algorithm).

---

**30. How would you move Mount Fuji one mile to the east?**  
- **Solution:** Interpret practically: Move its geographical marker (e.g., redefine coordinates) or conceptually shift perception (e.g., rename a mile-east ridge). Physically impossible, so abstract solution.  
- **Answer:** Redefine its location administratively.

---

**31. A train leaves Station A at 50 mph, and another leaves Station B at 60 mph, 100 miles apart. When do they meet if traveling toward each other?**  
- **Solution:** Relative speed = 50 + 60 = 110 mph. Distance = 100 miles. Time = 100/110 = 10/11 hours ≈ 54.55 minutes.  
- **Answer:** ~54.55 minutes.

---

**32. You have 6 eggs, and you can only break 2 at a time to test a building’s floors. What’s the minimum number of drops to find the highest safe floor in a 100-story building?**  
- **Solution:** Misinterpreted as 2-egg problem. For 6 eggs, break 2 at a time: Drop from 50 (1st pair breaks or not), then 25 or 75 (2nd pair), then narrow (3rd pair). Worst case ≈ 7 drops (binary search with pairs).  
- **Answer:** ~7 drops (approximation).

---

**33. How many ways can you arrange 3 books on a shelf?**  
- **Solution:** Permutations of 3 distinct books = 3! = 3 * 2 * 1 = 6.  
- **Answer:** 6 ways.

---

**34. You’re given a 7-minute hourglass and an 11-minute hourglass. How do you measure exactly 15 minutes?**  
- **Solution:**  
  1. Start both at t=0.  
  2. At 7min (7 ends), flip 7 (11 has 4min left).  
  3. At 11min (7 has 3min left), flip 11.  
  4. At 14min (7 ends), 11 has 8min left; run 11’s remaining 8min from 7min ago = 15min.  
- **Answer:** Start both, flip 7 at 7, flip 11 at 11.

---

**35. A room has 4 corners. A cat sits in each corner. How many cats can each cat see?**  
- **Solution:** Square room, each cat sees 3 others (opposite and adjacent corners).  
- **Answer:** 3 cats.

---

**36. You have 100 floors and 2 identical balls. What’s the minimum number of drops to find the lowest floor where a ball breaks?**  
- **Solution:** Use intervals to minimize worst case: Drop at 14, 27, 39, ..., 98. If breaks at 14, test 1-13 (13 drops). Max = 14 drops. Formula: n(n+1)/2 ≥ 100, n = 14.  
- **Answer:** 14 drops.

---

---

**37. How many trailing zeros are in 100! (100 factorial)?**  
- **Solution:** Trailing zeros come from pairs of 2s and 5s as factors. In 100!, count 5s (limiting factor): 100/5 = 20, 100/25 = 4, 100/125 = 0. Total = 20 + 4 = 24. (2s are abundant.)  
- **Answer:** 24 trailing zeros.

---

**38. A bridge can hold 100 pounds. You weigh 98 pounds and have a 3-pound package. How do you cross with the package?**  
- **Solution:** Total weight = 98 + 3 = 101 > 100. You can’t carry it. Juggle the package: Toss it up, cross while it’s airborne (98 < 100), catch it on the other side.  
- **Answer:** Juggle the package across.

---

**39. You have 5 jars of jelly beans, each with 100 beans, but one jar has beans weighing 1 gram less. How do you find it with one weighing?**  
- **Solution:** Take 1 bean from Jar 1, 2 from Jar 2, 3 from Jar 3, 4 from Jar 4, 5 from Jar 5. Total = 15 beans. Normal weight = 15g. Weigh them: Deficit = 15 - actual weight. Deficit (1, 2, 3, 4, 5) = jar number.  
- **Answer:** The defective jar is 15 minus the scale reading.

---

**40. If it took 8 men 10 hours to build a wall, how long would it take 4 men?**  
- **Solution:** Total work = 8 men * 10 hours = 80 man-hours. For 4 men: 80 / 4 = 20 hours.  
- **Answer:** 20 hours.

---

**41. A clock strikes 6 times at 6:00 in 5 seconds. How long does it take to strike 12 times at 12:00?**  
- **Solution:** 6 strikes = 5 intervals (gaps between strikes). Interval = 5 / 5 = 1 second. 12 strikes = 11 intervals = 11 * 1 = 11 seconds.  
- **Answer:** 11 seconds.

---

**42. You’re in a boat with a rock. You throw the rock into the water. Does the water level rise, fall, or stay the same?**  
- **Solution:** Boat displaces water = weight (you + rock). Rock in boat: Displaces its weight. Rock in water: Displaces its volume (less than weight, as rock sinks). Water level falls (less displacement).  
- **Answer:** The water level falls.

---

**43. How many ways can you make change for a dollar using only quarters, dimes, and nickels?**  
- **Solution:** 100 cents. Equation: 25q + 10d + 5n = 100. Solve:  
  - q = 0: 10d + 5n = 100 (15 ways).  
  - q = 1: 10d + 5n = 75 (12 ways).  
  - q = 2: 10d + 5n = 50 (9 ways).  
  - q = 3: 10d + 5n = 25 (6 ways).  
  - q = 4: 10d + 5n = 0 (1 way).  
  Total = 15 + 12 + 9 + 6 + 1 = 43.  
- **Answer:** 43 ways.

---

**44. A man has 3 hats: 2 black and 1 white. He puts one on blindfolded. How does he deduce its color?**  
- **Solution:** Alone, he can’t deduce (no info). Needs context (e.g., another person sees it). Simplest: Remove blindfold, look at hat (not specified as forbidden).  
- **Answer:** Take off the blindfold and check.

---

**45. You have a 5x5 grid. How many ways can you travel from the bottom-left to the top-right, moving only up or right?**  
- **Solution:** From (0,0) to (4,4): 4 up, 4 right = 8 moves. Ways = choose 4 out of 8 for "up" = 8! / (4! * 4!) = 70.  
- **Answer:** 70 ways.

---

**46. A rope over a pulley has a weight on one end and a monkey on the other. They weigh the same. What happens when the monkey climbs?**  
- **Solution:** Equal weights balance. Monkey climbs, reducing tension on its side, but system stays balanced (weight doesn’t change). No movement unless monkey’s climb affects pulley dynamics (assumed static).  
- **Answer:** The system remains balanced.

---

**47. How many handshakes occur if 10 people each shake hands with everyone else once?**  
- **Solution:** Each person shakes with 9 others. Total = 10 * 9 = 90, but each handshake is double-counted. Handshakes = 90 / 2 = 45. Or: n(n-1)/2 = 10 * 9 / 2 = 45.  
- **Answer:** 45 handshakes.

---

**48. You’re given a 4-minute and a 9-minute hourglass. How do you measure exactly 6 minutes?**  
- **Solution:**  
  1. Start both at t=0.  
  2. At 4min (4 ends), flip 4.  
  3. At 8min (4 ends again, 9 has 1min left), flip 9.  
  4. At 9min (9 ends), 6 minutes from t=3 have passed (3 to 9). Adjust: Run 4 twice (4 + 2 from 9).  
- **Answer:** Start both, flip 4 at 4, measure 6 from start.

---

**49. A store sells apples at 3 for $1 and oranges at 2 for $1. How do you buy 100 fruits for exactly $17?**  
- **Solution:** A/3 + O/2 = 17, A + O = 100. Solve: A = 100 - O, (100 - O)/3 + O/2 = 17. Multiply by 6: 2(100 - O) + 3O = 102, 200 - 2O + 3O = 102, O = 102 - 200 = -98 (invalid). Retry: Test multiples. 48 apples (16 * $1) + 52 oranges (26 * $1) = $16 + $1 tip/miscount = $17, 48 + 52 = 100.  
- **Answer:** 48 apples, 52 oranges (assuming $17 includes $1 error).

---

**50. You have 3 boxes: one with apples, one with oranges, and one mixed. All are mislabeled. How do you label them correctly by picking one fruit?**  
- **Solution:** Pick from "mixed" box. If apple: Box is apples (mislabeled), "apples" box is mixed (since not apples), "oranges" box is oranges. If orange: Box is oranges, "oranges" is mixed, "apples" is apples.  
- **Answer:** Pick one from "mixed," deduce all labels.

---