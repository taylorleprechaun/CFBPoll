# CFBPoll

### About

Poll pulls some data about teams and uses it to rank them.  Data used includes game-by-game results and offensive and defensive yardage.  Plans to introduce more/better data in the future.

In the poll, a team's "Score" is in relation to the #1 team.  The formula gives points to teams using the data it has.  Once all the teams are ranked it measures against the highest score given out.  So, if Clemson is #1 and they earned 123 points from the poll, then every team's points will be divided by 123 to get their final score.

Made for fun.  Not really sure what I plan to do with this once I get it refined.

### Recent changes

* No changes since last week (10/14).  Just an update to rankings.

### Rankings

As of 10/21/2018:

Rank| Team | Score | Record
---|---|---|---
1 | Alabama | 1.000 | 8-0
2 | Clemson | 0.983 | 7-0
3 | Georgia | 0.800 | 6-1
4 | Michigan | 0.797 | 7-1
5 | LSU | 0.793 | 7-1
6 | Notre Dame | 0.777 | 7-0
7 | Florida | 0.760 | 6-1
8 | Central Florida | 0.740 | 7-0
9 | Oklahoma | 0.731 | 6-1
10 | Appalachian St | 0.700 | 5-1
11 | Ohio State | 0.681 | 7-1
12 | Kentucky | 0.677 | 6-1
13 | Iowa | 0.671 | 6-1
14 | Fresno St | 0.666 | 6-1
15 | Alabama-Birmingham | 0.665 | 6-1
16 | Utah St | 0.657 | 6-1
17 | Miami FL | 0.653 | 5-2
18 | North Carolina St | 0.650 | 5-1
19 | Washington | 0.628 | 6-2
20 | Cincinnati | 0.623 | 6-1
21 | West Virginia | 0.621 | 5-1
22 | Penn State | 0.609 | 5-2
23 | Texas A&M | 0.608 | 5-2
24 | Buffalo | 0.602 | 7-1
25 | North Texas | 0.595 | 6-2

#### Observations and Notes

* Once again, I need to get better stats for the rankings.

* Appalachian State at #10 is kind of funny.

* Notable Absenses: Washington State (#26), Texas (#31), USF (#38)

### TODO List

1. Introduction of head-to-head results

2. Use better stats and find ones that limit the effect of garbage time

3. Clean up the code (many blocks which can be refactored into cleaner functions and some ugly code exist (like in the renaming function))

4. Figure out goal of this poll
