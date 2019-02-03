# CFBPoll

### About

Poll pulls some data about teams and uses it to rank them.  Data used includes game-by-game results and offensive and defensive yardage.  Plans to introduce more/better data in the future.

In the poll, a team's "Score" is in relation to the #1 team.  The formula gives points to teams using the data it has.  Once all the teams are ranked it measures against the highest score given out.  So, if Clemson is #1 and they earned 123 points from the poll, then every team's points will be divided by 123 to get their final score.

Made for fun.  Not really sure what I plan to do with this once I get it refined.

### Recent changes

* Minor tweaks to formula

* New code block in main method for predicting games!  This doesn't work and is pretty bad!  I made a really basic formula using some of the stats I grab from sports-reference and tried to predict some scores.  It either predicts very close matchups or ridiculous blowouts (I'm talking 80-22 kinds of blowouts).  This is something I am definitely going to work on in the offseason

### Rankings

**Week 17 (Final) Rankings**

(As of 01/08/2019)

Rank| Team | Score | Record
---|---|---|---
1 | Clemson | 1.0000 | 15-0
2 | Alabama | 0.9517 | 14-1
3 | Notre Dame | 0.9186 | 12-1
4 | Ohio State | 0.8854 | 13-1
5 | Oklahoma | 0.8817 | 12-2
6 | Georgia | 0.8776 | 11-3
7 | Central Florida | 0.8774 | 12-1
8 | LSU | 0.8585 | 10-3
9 | Florida | 0.8536 | 10-3
10 | Army | 0.8460 | 11-2
11 | Fresno St | 0.8411 | 12-2
12 | Appalachian St | 0.8395 | 11-2
13 | Washington St | 0.8391 | 11-2
14 | Texas A&M | 0.8374 | 9-4
15 | Michigan | 0.8336 | 10-3
16 | Kentucky | 0.8260 | 10-3
17 | Syracuse | 0.8252 | 10-3
18 | Cincinnati | 0.8204 | 11-2
19 | Boise St | 0.8169 | 10-3
20 | Utah St | 0.8085 | 11-2
21 | Washington | 0.8085 | 10-4
22 | Georgia Southern | 0.7999 | 10-3
23 | Troy | 0.7970 | 10-3
24 | Texas | 0.7910 | 10-4
25 | Stanford | 0.7878 | 9-4

#### Observations and Notes

* Clemson is the final #1 of my poll, which had them at #1 for a good portion of the year (First poll was Week 7 and Clemson was #1 all but Week 9)

### TODO List

1. ~~Introduction of head-to-head results?~~ ~~I think I might scrap this part.~~ Maybe head-to-head could be used if neighboring teams are close enough and have played... not sure about this.

2. Score predictor!  I wrote about it in the recent changes section.  I tried to predict scores between teams (knowing the outcome and using data from before that game was played) and the results were wild.

3. Clean up the code (many blocks which can be refactored into cleaner functions and some ugly code exist (like in the renaming function))
