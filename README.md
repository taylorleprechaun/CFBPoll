# CFBPoll

### About

Poll pulls some data about teams and uses it to rank them.  Data used includes game-by-game results and offensive and defensive yardage.  Plans to introduce more/better data in the future.

In the poll, a team's "Score" is in relation to the #1 team.  The formula gives points to teams using the data it has.  Once all the teams are ranked it measures against the highest score given out.  So, if Clemson is #1 and they earned 123 points from the poll, then every team's points will be divided by 123 to get their final score.

Made for fun.  Not really sure what I plan to do with this once I get it refined.

### Recent changes

* Fixed the strength of schedule/victory stat being used and how it was used

* Tweaked usage of yards/points for and against.

### Rankings

As of 10/14/2018:

Rank| Team | Score | Record
---|---|---|---
1 | Alabama | 1.000 | 7-0
2 | Clemson | 0.959 | 6-0
3 | Ohio State | 0.864 | 7-0
4 | Georgia | 0.837 | 6-1
5 | LSU | 0.835 | 6-1
6 | Notre Dame | 0.834 | 7-0
7 | Florida | 0.822 | 6-1
8 | Michigan | 0.815 | 6-1
9 | Central Florida | 0.803 | 6-0
10 | North Carolina St | 0.785 | 5-0
11 | Oklahoma | 0.762 | 5-1
12 | Kentucky | 0.729 | 5-1
13 | Appalachian St | 0.717 | 4-1
14 | Cincinnati | 0.706 | 6-0
15 | Utah St | 0.703 | 5-1
16 | Miami FL | 0.680 | 5-2
17 | North Texas | 0.667 | 6-1
18 | West Virginia | 0.664 | 5-1
19 | Duke | 0.661 | 5-1
20 | Iowa | 0.659 | 5-1
21 | Fresno St | 0.640 | 5-1
22 | Mississippi St | 0.633 | 4-2
23 | Penn State | 0.631 | 4-2
24 | Texas | 0.629 | 6-1
25 | Texas A&M | 0.626 | 5-2

#### Observations and Notes

* Bro, didn't LSU like *just* beat Georgia?  Yes, yes they did.  I need to look into that, but I have a suspicion of what caused it.  Regardless, the poll needs a way to account for head-to-head results for teams close to each other, which I will definitely procrastinate doing.

* Appalachian State at #13?  My poll likes them I guess ¯\\\_(ツ)_/¯

* Where are Washington, Washington State, Oregon, etc.?  Those teams are sitting at #34, #30, and #28.  Washington is hurt by their record, the latter two are hurt by their strength of schedules.

* I still need to use better stats.  PF, PA, YF, and YA are not very good stats to use to begin with, and I need to replace them with something better (which is resilient to garbage time).

* Texas at #24?  I'm completely stumped by this one, to be honest.  At first I thought it was due to their low average margin of victory (7.71), but Notre dame, a very comparable team (with the stats I am using), is 25% higher than them in the poll with an average margin of victory nearly twice that (13.86).

### TODO List

1. Introduction of head-to-head results

2. Use better stats to limit effect of garbage time (Observations and Notes)

3. Clean up the code (many blocks which can be refactored into cleaner functions and some ugly code exist (like in the renaming function))

4. Figure out goal of this poll
