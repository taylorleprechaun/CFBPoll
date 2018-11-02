# CFBPoll

### About

Poll pulls some data about teams and uses it to rank them.  Data used includes game-by-game results and offensive and defensive yardage.  Plans to introduce more/better data in the future.

In the poll, a team's "Score" is in relation to the #1 team.  The formula gives points to teams using the data it has.  Once all the teams are ranked it measures against the highest score given out.  So, if Clemson is #1 and they earned 123 points from the poll, then every team's points will be divided by 123 to get their final score.

Made for fun.  Not really sure what I plan to do with this once I get it refined.

### Recent changes

* Slight adjustment to formula.  Nothing else of note.

### Rankings

As of 10/31/2018:

Rank| Team | Score | Record
---|---|---|---
1 | Clemson | 1.000 | 8-0
2 | Alabama | 0.894 | 8-0
3 | Georgia | 0.793 | 7-1
4 | LSU | 0.778 | 7-1
5 | Michigan | 0.764 | 7-1
6 | Oklahoma | 0.754 | 7-1
7 | Notre Dame | 0.725 | 8-0
8 | Fresno St | 0.712 | 7-1
9 | Central Florida | 0.694 | 7-0
10 | Utah St | 0.686 | 7-1
11 | Kentucky | 0.670 | 7-1
12 | Washington St | 0.655 | 7-1
13 | Ohio State | 0.655 | 7-1
14 | West Virginia | 0.653 | 6-1
15 | Florida | 0.643 | 6-2
16 | Appalachian St | 0.634 | 5-2
17 | Houston | 0.632 | 7-1
18 | Alabama-Birmingham | 0.623 | 7-1
19 | Penn State | 0.622 | 6-2
20 | Cincinnati | 0.608 | 7-1
21 | Iowa | 0.607 | 6-2
22 | Utah | 0.606 | 6-2
23 | Georgia Southern | 0.602 | 7-1
24 | Buffalo | 0.599 | 8-1*
25 | Syracuse | 0.591 | 6-2

#### Observations and Notes

* To start with, I was lazy and didn't run this until 11/1 before any games were played that day.  As a result, any games from 10/30-31 have been included in the polls (i.e. 8-1 Buffalo), so this week's poll is a little messed up.

* Once again, I need to get better stats for the rankings.

* The poll seems to be valuing wins just a bit too much, even with my SoS metric trying to weigh it.  As a result, many of the better G5 teams are getting pushed higher up than I feel they should.  i.e. Georgia Southern at #23 when Sagarin and S&P+ have them at 80 and 61, respectively.

* Notable Absenses: Texas (#40)

### TODO List

1. Introduction of head-to-head results

2. Use better stats and find ones that limit the effect of garbage time

3. Adjust wins/SoS weighting in combination with new stats to rank teams better

4. Clean up the code (many blocks which can be refactored into cleaner functions and some ugly code exist (like in the renaming function))

5. Figure out goal of this poll
