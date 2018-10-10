# CFBPoll

### About

Poll pulls some data about teams and uses it to rank them.  Data used includes game-by-game results and offensive and defensive yardage.  Plans to introduce more/better data in the future.

In the poll, a team's "Score" is in relation to the #1 team.  The formula gives points to teams using the data it has.  Once all the teams are ranked it measures against the highest score given out.  So, if Clemson is #1 and they earned 123 points from the poll, then every team's points will be divided by 123 to get their final score.

Made for fun.  Not really sure what I plan to do with this once I get it refined.

### Rankings

As of 10/9/2018:

Rank| Team | Score | Record
---|---|---|---
1 | Clemson | 1.000 | 6-0
2 | Alabama | 0.906 | 6-0
3 | Georgia | 0.884 | 6-0
4 | LSU | 0.624 | 5-1
5 | Ohio State | 0.583 | 6-0
6 | Miami FL | 0.562 | 5-1
7 | Florida | 0.545 | 5-1
8 | Penn State | 0.517 | 4-1
9 | Texas Tech | 0.507 | 3-2
10 | Notre Dame | 0.486 | 6-0
11 | Kentucky | 0.415 | 5-1
12 | Michigan | 0.408 | 5-1
13 | Mississippi St | 0.374 | 4-2
14 | Central Florida | 0.369 | 5-0
15 | Oklahoma | 0.363 | 5-1
16 | North Carolina St | 0.345 | 5-0
17 | Utah St | 0.342 | 4-1
18 | West Virginia | 0.325 | 5-0
19 | Syracuse | 0.314 | 4-2
20 | Auburn | 0.312 | 4-2
21 | Duke | 0.300 | 4-1
22 | Iowa | 0.269 | 4-1
23 | Fresno St | 0.253 | 4-1
24 | Washington | 0.248 | 5-1
25 | Texas A&M | 0.231 | 4-2

#### Observations and Notes

* Clemson takes the top spot over Bama mainly because of their defense.  Bama giving up nearly 75 yards per game more is hurting them.  A way to measure garbage time stats should help this... not sure where/how to get that information.

* Texas Tech at #9?  Yep, you read that right.  Their offense puts up some big yards, and their strength of schedule (.77) and strength of victory (.88) are high as well.  I believe my TODOs in the code mention switching my SoS formula to one similar to the old BCS system (which includes opponent's opponent's records), a change which would mitigate this issue.

* Notre Dame only at #10?  They have the opposite problem Texas Tech has.  Their SoS and SoV *should* be higher, yet they are .57 and .69, respectively.  Perhaps the SoS fix should be my top priority.

Notable Absences: Wisconsin (#27, hurt by SoS/SoV issue) and Texas (#32, hurt by MoV)

### TODO List

1. SoS/SoV issue (detailed in Observations and Notes section)

2. Use better stats to limit effect of garbage time (Observations and Notes)

3. Clean up the code (many blocks which can be refactored into cleaner functions and some ugly code exist (like in the renaming function))

4. Figure out goal of this poll
