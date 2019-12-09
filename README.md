# CFBPoll

### About

Poll pulls some data about teams and uses it to rank them.  Data used includes game-by-game results and offensive and defensive yardage.  Plans to introduce more/better data in the future.

In the poll, a team's "Score" is in relation to the #1 team.  The formula gives points to teams using the data it has.  Once all the teams are ranked it measures against the highest score given out.  So, if Clemson is #1 and they earned 123 points from the poll, then every team's points will be divided by 123 to get their final score.

Made for fun.  Not really sure what I plan to do with this once I get it refined.

### Recent changes

* Added conferences information for each team.  Not used in the poll in any way, it's just there now.

### Rankings

**Week 16 Rankings**

(As of 12/08/2019)

Rank| Team | Score | Record
---|---|---|---
1 | Ohio State | 1.0000 | 13-0
2 | LSU | 0.9933 | 13-0
3 | Clemson | 0.9584 | 13-0
4 | Memphis | 0.9164 | 12-1
5 | Oklahoma | 0.9159 | 12-1
6 | Boise St | 0.9124 | 12-1
7 | Georgia | 0.9108 | 11-2
8 | Florida | 0.9056 | 10-2
9 | Appalachian St | 0.8958 | 12-1
10 | Penn State | 0.8953 | 10-2
11 | Notre Dame | 0.8889 | 10-2
12 | Oregon | 0.8869 | 11-2
13 | Auburn | 0.8797 | 9-3
14 | Baylor | 0.8770 | 11-2
15 | Utah | 0.8743 | 11-2
16 | Alabama | 0.8685 | 10-2
17 | Wisconsin | 0.8664 | 10-3
18 | Navy | 0.8662 | 9-2
19 | Air Force | 0.8529 | 10-2
20 | Cincinnati | 0.8508 | 10-3
21 | Michigan | 0.8493 | 9-3
22 | SMU | 0.8432 | 10-2
23 | Minnesota | 0.8389 | 10-2
24 | Florida Atlantic | 0.8369 | 10-3
25 | Louisiana-Lafayette | 0.8240 | 10-3

#### Observations and Notes

* And your playoff teams are.......... Ohio State, LSU, Clemson, and *checks notes* Memphis?  Yes that is right, apparently.  Memphis just barely edges out Oklahoma.  Memphis with a slight advantage in strength of schedule, Oklahoma with a small advantage on offensive and defensive stats, and Memphis gets a final bump from turnover margin.  So congrats to Memphis on their playoff appearance!

* Notable teams missing: #26 Iowa, #28 Virginia, #29 USC

* Also special shout out to sports-reference for the team stat tables disappearing until I wrote them, delaying my poll until today


### TODO List

1. Ideas for next season
    * Add in info about conferences so we can print out conference standings <-- Partially done
        * Would also need to program in tiebreakers which could get complicated
    * Boosts for conference champions perhaps? <-- next season probably
    * Conference and division rankings maybe? <-- maybe
	
2. Score predictor <-- No real progress made here.  Fiddled with it during the offseason but never got anything useful.  Maybe some day...

3. Clean up the code

4. ???