# CFBPoll

### About

Poll pulls some data about teams and uses it to rank them.  Data used includes game-by-game results and offensive and defensive yardage.  Plans to introduce more/better data in the future.

In the poll, a team's "Score" is in relation to the #1 team.  The formula gives points to teams using the data it has.  Once all the teams are ranked it measures against the highest score given out.  So, if Clemson is #1 and they earned 123 points from the poll, then every team's points will be divided by 123 to get their final score.

Made for fun.  Not really sure what I plan to do with this once I get it refined.

### Recent changes

* Poll class - minor changes to code structure, behavior is basically the same

* Team class - changes to a few fields, converted individual stats fields to a Stat class

* **Stats class (new)** - Holds team offense and team defense stats

* Rank class - formula update

### Rankings

**Week 11 Rankings**

(As of 11/4/2018)

Rank| Team | Score | Record
---|---|---|---
1 | Clemson | 1.0000 | 9-0
2 | Alabama | 0.9733 | 9-0
3 | Georgia | 0.9683 | 8-1
4 | Notre Dame | 0.9620 | 9-0
5 | Michigan | 0.9433 | 8-1
6 | LSU | 0.9327 | 7-2
7 | Central Florida | 0.9308 | 8-0
8 | Oklahoma | 0.9065 | 8-1
9 | West Virginia | 0.8883 | 7-1
10 | Washington St | 0.8735 | 8-1
11 | Kentucky | 0.8687 | 7-2
12 | North Carolina St | 0.8675 | 6-2
13 | Fresno St | 0.8557 | 8-1
14 | Buffalo | 0.8533 | 8-1
15 | Utah St | 0.8485 | 8-1
16 | Ohio State | 0.8466 | 8-1
17 | Alabama-Birmingham | 0.8464 | 8-1
18 | Cincinnati | 0.8446 | 8-1
19 | Syracuse | 0.8387 | 7-2
20 | Boston College | 0.8327 | 7-2
21 | Mississippi St | 0.8323 | 6-3
22 | Army | 0.8319 | 7-2
23 | Florida | 0.8305 | 6-3
24 | Georgia Southern | 0.8237 | 7-2
25 | Appalachian St | 0.8226 | 6-2

#### Observations and Notes

* Okay, now we're getting somewhere.  Poll is starting to look real legit now.  Recent changes introduced some stats which are new to the poll.

* So the big thing is Clemson over Bama.  The poll sees them as very similar, the biggest difference coming from my strength of schedule metric.

* Ohio State at #16?  Once again, strength of schedule knocks a team down.  Teams around them like Utah State, Buffalo, and Fresno State have similarly ranked schedules, which is why they are all so close.  They should pull ahead in the remaining weeks of the season.

* Notable absences: Penn State (#27), Washington (#29), Texas (#42).

### TODO List

1. Introduction of head-to-head results?

2. Clean up the code (many blocks which can be refactored into cleaner functions and some ugly code exist (like in the renaming function))

3. Figure out goal of this poll
