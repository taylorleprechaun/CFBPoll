# CFBPoll

### About

Poll pulls some data about teams and uses it to rank them.  Data used includes game-by-game results and offensive and defensive yardage.  Plans to introduce more/better data in the future.

In the poll, a team's "Score" is in relation to the #1 team.  The formula gives points to teams using the data it has.  Once all the teams are ranked it measures against the highest score given out.  So, if Clemson is #1 and they earned 123 points from the poll, then every team's points will be divided by 123 to get their final score.

Made for fun.  Not really sure what I plan to do with this once I get it refined.

### Recent changes

* Minor tweaks to formula

* New code block in main method for predicting games!  This doesn't work and is pretty bad!  I made a really basic formula using some of the stats I grab from sports-reference and tried to predict some scores.  It either predicts very close matchups or ridiculous blowouts (I'm talking 80-22 kinds of blowouts).  This is something I am definitely going to work on in the offseason

### Rankings

**Week 16 (Pre NCG/Post Bowl) Rankings**

(As of 01/06/2019)

Rank| Team | Score | Record
---|---|---|---
1 | Clemson | 1.0000 | 14-0
2 | Alabama | 0.9864 | 14-0
3 | Notre Dame | 0.9300 | 12-1
4 | Ohio State | 0.8984 | 13-1
5 | Oklahoma | 0.8955 | 12-2
6 | Georgia | 0.8916 | 11-3
7 | Central Florida | 0.8903 | 12-1
8 | LSU | 0.8726 | 10-3
9 | Florida | 0.8664 | 10-3
10 | Army | 0.8584 | 11-2
11 | Fresno St | 0.8534 | 12-2
12 | Appalachian St | 0.8519 | 11-2
13 | Washington St | 0.8514 | 11-2
14 | Texas A&M | 0.8494 | 9-4
15 | Michigan | 0.8458 | 10-3
16 | Kentucky | 0.8385 | 10-3
17 | Syracuse | 0.8347 | 10-3
18 | Cincinnati | 0.8324 | 11-2
19 | Boise St | 0.8289 | 10-3
20 | Washington | 0.8204 | 10-4
21 | Utah St | 0.8204 | 11-2
22 | Georgia Southern | 0.8093 | 10-3
23 | Troy | 0.8087 | 10-3
24 | Texas | 0.8028 | 10-4
25 | Stanford | 0.7993 | 9-4

#### Observations and Notes

* Texas is BACK!!!  No longer a notable absence!

* My poll still views Notre Dame as being a step above OSU, Oklahoma, and UGA, even after losing to Clemson in the CFP.

* Something I immediately noticed was LSU above UF (given I am a UF alum).  It seems that bowl season pushed their SoS up enough to put them just ahead of UF (they are only 6% apart).  Maybe head-to-head needs to get factored in for adjacent teams... not sure how I'll adjust that.  A project for this long offseason.

### TODO List

1. ~~Introduction of head-to-head results?~~ ~~I think I might scrap this part.~~ Maybe head-to-head could be used if neighboring teams are close enough and have played... not sure about this.

2. Score predictor!  I wrote about it in the recent changes section.  I tried to predict scores between teams (knowing the outcome and using data from before that game was played) and the results were wild.

3. Clean up the code (many blocks which can be refactored into cleaner functions and some ugly code exist (like in the renaming function))
