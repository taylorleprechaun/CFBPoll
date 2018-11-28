# CFBPoll

### About

Poll pulls some data about teams and uses it to rank them.  Data used includes game-by-game results and offensive and defensive yardage.  Plans to introduce more/better data in the future.

In the poll, a team's "Score" is in relation to the #1 team.  The formula gives points to teams using the data it has.  Once all the teams are ranked it measures against the highest score given out.  So, if Clemson is #1 and they earned 123 points from the poll, then every team's points will be divided by 123 to get their final score.

Made for fun.  Not really sure what I plan to do with this once I get it refined.

### Recent changes

* Minor tweaks to formula

### Rankings

**Week 14 Rankings**

(As of 11/27/2018)

Rank| Team | Score | Record
---|---|---|---
1 | Clemson | 1.0000 | 12-0
2 | Alabama | 0.9847 | 12-0
3 | Notre Dame | 0.9737 | 12-0
4 | Georgia | 0.9645 | 11-1
5 | Central Florida | 0.9495 | 11-0
6 | Oklahoma | 0.9109 | 11-1
7 | Ohio State | 0.8920 | 11-1
8 | Michigan | 0.8884 | 10-2
9 | Florida | 0.8663 | 9-3
10 | Army | 0.8661 | 9-2
11 | LSU | 0.8569 | 9-3
12 | Boise St | 0.8537 | 10-2
13 | Washington St | 0.8534 | 10-2
14 | Appalachian St | 0.8466 | 9-2
15 | Cincinnati | 0.8440 | 10-2
16 | Kentucky | 0.8431 | 9-3
17 | Fresno St | 0.8420 | 10-2
18 | Syracuse | 0.8325 | 9-3
19 | Texas A&M | 0.8316 | 8-4
20 | Washington | 0.8309 | 9-3
21 | Utah | 0.8305 | 9-3
22 | Mississippi St | 0.8211 | 8-4
23 | Penn State | 0.8192 | 9-3
24 | Missouri | 0.8181 | 8-4
25 | Utah St | 0.8174 | 10-2

#### Observations and Notes

* Notable Absenses: West Virginia (#27), Texas (#30)

* My poll really hates Texas.

* The drop-off from #5 to #6 is quite noticeable.  If I had to guess, my top four following the conference championship games (assuming the higher ranked team wins) will be Clemson, Alabama, Notre Dame, and Central Florida.  Oklahoma's defense and Ohio State's strength of schedule are pulling them down and I'm not sure if the extra win will give either team enough of a boost.

### TODO List

1. ~~Introduction of head-to-head results?~~ I think I might scrap this part.

2. Clean up the code (many blocks which can be refactored into cleaner functions and some ugly code exist (like in the renaming function))

3. ~~Figure out goal of this poll~~ I'm thinking the "goal" of the poll is to use it to predict game match-ups.  Use the poll to generate confidence scores for specific games in the coming week (which means finding a place to download full season schedules, including games not yet played, rather than grab from that .html file).  Maybe use it to predict how teams will finish the season... not entirely sure how to go about doing this though.  These are plans for the FUTURE though, nothing to worry about now.
