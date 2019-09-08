# CFBPoll

### About

Poll pulls some data about teams and uses it to rank them.  Data used includes game-by-game results and offensive and defensive yardage.  Plans to introduce more/better data in the future.

In the poll, a team's "Score" is in relation to the #1 team.  The formula gives points to teams using the data it has.  Once all the teams are ranked it measures against the highest score given out.  So, if Clemson is #1 and they earned 123 points from the poll, then every team's points will be divided by 123 to get their final score.

Made for fun.  Not really sure what I plan to do with this once I get it refined.

### Recent changes

* Some changes to the structure of the code and ability to handle teams who have only played FCS games through Week 2 (they get ranked in last place)

### Rankings

**Week 2 Rankings**

(As of 09/08/2019)

Rank| Team | Score | Record
---|---|---|---
1 | North Carolina St | 1.0000 | 2-0
2 | Kansas St | 0.9983 | 2-0
3 | Penn State | 0.9982 | 2-0
4 | Oklahoma | 0.9963 | 2-0
5 | Baylor | 0.9960 | 2-0
6 | Arizona St | 0.9906 | 2-0
7 | Appalachian St | 0.9884 | 2-0
8 | Indiana | 0.9883 | 2-0
9 | Texas Tech | 0.9882 | 2-0
10 | California | 0.9762 | 2-0
11 | LSU | 0.9568 | 2-0
12 | Michigan St | 0.9557 | 2-0
13 | Colorado | 0.9508 | 2-0
14 | SMU | 0.9440 | 2-0
15 | Boise St | 0.9399 | 2-0
16 | Boston College | 0.9379 | 2-0
17 | Virginia | 0.9263 | 2-0
18 | Memphis | 0.9232 | 2-0
19 | Iowa | 0.9177 | 2-0
20 | Mississippi St | 0.9143 | 2-0
21 | Utah | 0.9135 | 2-0
22 | Michigan | 0.9038 | 2-0
23 | Alabama | 0.8887 | 2-0
24 | Maryland | 0.8866 | 2-0
25 | Auburn | 0.8857 | 2-0

#### Observations and Notes

* Okay, so obviously NC State *isn't* the best team in the country, but we'll just roll with it until we get some more data points to clean this up.

* Notable teams missing from the Top 25: #26 Clemson, #55 Ohio State, #68 Florida, #70 Texas, #79 Georgia

### TODO List

1. Score predictor <-- No real progress made here.  Fiddled with it during the offseason but never got anything useful.  Maybe some day...

2. Clean up the code <-- Some progress made here!

Possible use of head-to-head results has been scrapped