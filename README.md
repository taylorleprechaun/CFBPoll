# CFBPoll

### About

Poll pulls some data about teams and uses it to rank them.  Data used includes game-by-game results and offensive and defensive team stats.

In the poll, a team's "Score" is in relation to the #1 team.  The formula gives points to teams using the data it has.  Once all the teams are ranked it measures against the highest score given out.  So, if Clemson is #1 and they earned 123 points from the poll, then every team's points will be divided by 123 to get their final score.

Made for fun.  I want to get this added to the Massey Composite for the 2020 season and beyond so I still need to do more tweaks before the season starts.

### Recent changes

* 6/24/2020: Big code update to make running the poll better and easier
* 9/26/2020: Some minor follow-up fixes I forgot to commit a couple months ago along with the rest of the BOAT/WOAT
* 11/8/2020: First poll of 2020 released (Week 10)
* 12/14/2020: Week 14 poll released

### Rankings (Updated 12/14/2020)

**Week 14 Rankings**

Rank| Team | Score | Record
---|---|---|---
1 | Cincinnati | 1.0000 | 8-0
2 | Alabama | 0.9914 | 10-0
3 | Ohio State | 0.9622 | 5-0
4 | Coastal Carolina | 0.9592 | 11-0
5 | Clemson | 0.9526 | 9-1
6 | Brigham Young | 0.9469 | 10-1
7 | San Jose State | 0.9463 | 6-0
8 | Notre Dame | 0.9436 | 10-0
9 | USC | 0.9231 | 5-0
10 | Louisiana-Lafayette | 0.9067 | 9-1
11 | Texas A&M | 0.8879 | 7-1
12 | Miami FL | 0.8874 | 8-2
13 | Iowa State | 0.8868 | 8-2
14 | Marshall | 0.8841 | 7-1
15 | Tulsa | 0.8814 | 6-1
16 | Boise State | 0.8783 | 5-1
17 | Indiana | 0.8739 | 6-1
18 | Northwestern | 0.8568 | 6-1
19 | Oklahoma | 0.8555 | 7-2
20 | Georgia | 0.8517 | 7-2
21 | Colorado | 0.8490 | 4-1
22 | Army | 0.8485 | 8-2
23 | North Carolina | 0.8367 | 8-3
24 | Buffalo | 0.8290 | 5-0
25 | Florida | 0.8284 | 8-2

#### Observations and Notes (Updated 12/14/2020)

* ~~The lack of non-con is really messing with my poll but it is what it is.  Hopefully it'll sort out more as more games get played.~~
* My poll remains messed up going into the conference championships.  Unfortunately the lack of OOC makes it look like trash.  It hopefully will sort itself out (for the most part) after bowl season but until then it is what it is.

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
4 | 2016 | Alabama | 41.061 | 14-1
5 | 2016 | Clemson | 41.044 | 14-1

**The bottom 5 Worst of all Time**

Rank | Year | Team | Rating | Record
---|---|---|---|---
1 | 2019 | Akron | 13.370 | 0-12
2 | 2009 | EMU | 13.809 | 0-12
3 | 2004 | UCF | 13.954 | 0-11
4 | 2013 | Miami OH | 13.973 | 0-12
5 | 2006 | FIU | 14.394 | 0-12
