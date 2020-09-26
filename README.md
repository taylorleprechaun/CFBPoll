# CFBPoll

### About

Poll pulls some data about teams and uses it to rank them.  Data used includes game-by-game results and offensive and defensive team stats.

In the poll, a team's "Score" is in relation to the #1 team.  The formula gives points to teams using the data it has.  Once all the teams are ranked it measures against the highest score given out.  So, if Clemson is #1 and they earned 123 points from the poll, then every team's points will be divided by 123 to get their final score.

Made for fun.  I want to get this added to the Massey Composite for the 2020 season and beyond so I still need to do more tweaks before the season starts.

### Recent changes

* 6/24/2020: Big code update to make running the poll better and easier
* 9/26/2020: Some minor follow-up fixes I forgot to commit a couple months ago along with the rest of the BOAT/WOAT

### Rankings (Updated 1/14/2020)

**Week 17 Rankings**

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

#### Observations and Notes (Updated 1/14/2020)

* Ohio State above Clemson is expected by my poll given how far ahead of them they were before each team took a loss.  I mentioned it last season that maybe something to compare head-to-head results could be useful but I decided against it then and I'm still against it now.  I'm willing to stick with what my computer says based on the formula and not mess with the standings afterwards.

* I'm also not surprised by how many G5 schools are ranked (9) given a big factor of my poll is winning the games you play.  It is definitely more than the AP (7 - Final, 6 - Week 16), Coaches (6 - Week 16), or CFP (4 - Week 16) rankings have in theirs.

* See you all next fall!  Go Gators!

### TODO List (Updated 9/26/2020)

1. Ideas for the future? - scrapping the first idea because in hindsight it's kinda dumb
    * ~~Boosts for conference champions perhaps? <-- Would do manually if I did it since programming in tiebreakers for conferences seems very not fun and easy to mess up... if I did this I would need to figure out how it affects a team's rating and I haven't thought of a good way to do that yet~~
    * Conference and division rankings maybe? <-- As it stands I could definitely put something easy together to print out each conference's rankings in relation to the others (Highest/Lowest ranked teams, Mean and Median ranking, etc.).  Not sure how much I care about doing this though
	
2. Clean up the code more

3. ????

### Misc.

**Introducing the Best and Worst of all Time!**

My poll, as of this update, has been run across every season from 2000 through 2019 (that's because sports-reference only goes back to the year 2000) (using my new rating formula, meaning the ratings from all previous posts aren't totally accurate to what you'll see here, but the formula is way better now, trust me)

Something I noticed across these seasons is that the best teams had a rating above 40 and the worst teams were below 16, so I made a list of them (here: https://github.com/taylorleprechaun/CFBPoll/blob/master/rsc/teams/BOAT%20and%20WOAT.xlsx )

I decided to split up the rankings between pre-CFP and CFP eras.  This was because the teams in the CFP would get little bumps from playing an extra game against an elite-level team.

**The top 5 Best of all Time (pre-CFP era)**

Rank | Year | Team | Rating | Record
---|---|---|---|---
1 | 2001 | Miami FL | 42.309 | 12-0
2 | 2009 | Alabama | 41.756 | 14-0
3 | 2010 | Auburn | 41.464 | 14-0
4 | 2005 | Texas | 41.291 | 13-0
5 | 2000 | Oklahoma | 41.246 | 13-0

**The top 5 Best of all Time (CFP era)**

Rank | Year | Team | Rating | Record
---|---|---|---|---
1 | 2018 | Clemson | 42.445 | 15-0
2 | 2019 | LSU | 42.374 | 15-0
3 | 2015 | Alabama | 41.615 | 14-1
4 | 2016 | ALabama | 41.061 | 14-1
5 | 2016 | Clemson | 41.044 | 14-1

**The bottom 5 Worst of all Time**

Rank | Year | Team | Rating | Record
---|---|---|---|---
1 | 2019 | Akron | 13.370 | 0-12
2 | 2009 | EMU | 13.809 | 0-12
3 | 2004 | UCF | 13.954 | 0-11
4 | 2013 | Miami OH | 13.973 | 0-12
5 | 2006 | FIU | 14.394 | 0-12
