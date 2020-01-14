# CFBPoll

### About

Poll pulls some data about teams and uses it to rank them.  Data used includes game-by-game results and offensive and defensive yardage.  Plans to introduce more/better data in the future.

In the poll, a team's "Score" is in relation to the #1 team.  The formula gives points to teams using the data it has.  Once all the teams are ranked it measures against the highest score given out.  So, if Clemson is #1 and they earned 123 points from the poll, then every team's points will be divided by 123 to get their final score.

Made for fun.  Not really sure what I plan to do with this once I get it refined.

### Recent changes

* None

### Rankings

**Week 17 Rankings**

(As of 1/14/2020)

Rank| Team | Score | Record
---|---|---|---
1 | LSU | 1.0000 | 15-0
2 | Ohio State | 0.9509 | 13-1
3 | Clemson | 0.9358 | 14-1
4 | Georgia | 0.9166 | 12-2
5 | Florida | 0.9009 | 11-2
6 | Penn State | 0.8969 | 11-2
7 | Appalachian St | 0.8866 | 13-1
8 | Oregon | 0.8812 | 12-2
9 | Memphis | 0.8772 | 12-2
10 | Notre Dame | 0.8764 | 11-2
11 | Oklahoma | 0.8752 | 12-2
12 | Alabama | 0.8702 | 11-2
13 | Navy | 0.8619 | 11-2
14 | Boise St | 0.8596 | 12-2
15 | Auburn | 0.8476 | 9-4
16 | Air Force | 0.8411 | 11-2
17 | Minnesota | 0.8396 | 11-2
18 | Cincinnati | 0.8339 | 11-3
19 | Baylor | 0.8327 | 11-3
20 | Florida Atlantic | 0.8315 | 11-3
21 | Utah | 0.8300 | 11-3
22 | Wisconsin | 0.8251 | 10-4
23 | Louisiana-Lafayette | 0.8235 | 11-3
24 | Iowa | 0.8158 | 10-3
25 | SMU | 0.8058 | 10-3

#### Observations and Notes

* Ohio State above Clemson is expected by my poll given how far ahead of them they were before each team took a loss.  I mentioned it last season that maybe something to compare head-to-head results could be useful but I decided against it then and I'm still against it now.  I'm willing to stick with what my computer says based on the formula and not mess with the standings afterwards.

* I'm also not surprised by how many G5 schools are ranked (9) given a big factor of my poll is winning the games you play.  It is definitely more than the AP (7 - Final, 6 - Week 16), Coaches (6 - Week 16), or CFP (4 - Week 16) rankings have in theirs.

* See you all next fall!  Go Gators!

### TODO List

1. First thing is first is I plan on tweaking my ranking formula.  I think some of the criteria and weights I use should be altered a bit.  I already started messing with this after the Post CCG rankings in December but it isn't yet where I want it.

2. Ideas for next season
    * Add in info about conferences so we can print out conference standings <-- Conferences/Divisions added.  Not sure if I want to do standings given all the tiebreaker nonsense
        * Would also need to program in tiebreakers which could get complicated
    * Boosts for conference champions perhaps? <-- Following from previous point: would need to be manually done (wouldn't be too hard to do).  Need to figure out a good boost for this if any
    * Conference and division rankings maybe? <-- As it stands I could definitely put something easy together to print out each conference's rankings in relation to the others (Highest/Lowest ranked teams, Mean and Median ranking, etc.)
	
3. Score predictor <-- Still no progress here.  I might mess with in the offseason but probably not

4. Clean up the code

5. ???