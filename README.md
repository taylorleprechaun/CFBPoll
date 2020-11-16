# CFBPoll

### About

Poll pulls some data about teams and uses it to rank them.  Data used includes game-by-game results and offensive and defensive team stats.

In the poll, a team's "Score" is in relation to the #1 team.  The formula gives points to teams using the data it has.  Once all the teams are ranked it measures against the highest score given out.  So, if Clemson is #1 and they earned 123 points from the poll, then every team's points will be divided by 123 to get their final score.

Made for fun.  I want to get this added to the Massey Composite for the 2020 season and beyond so I still need to do more tweaks before the season starts.

### Recent changes

* 6/24/2020: Big code update to make running the poll better and easier
* 9/26/2020: Some minor follow-up fixes I forgot to commit a couple months ago along with the rest of the BOAT/WOAT
* 11/8/2020: First poll of 2020 released (Week 10)

### Rankings (Updated 11/16/2020)

**Week 11 Rankings**

Rank| Team | Score | Record
---|---|---|---
1 | Northwestern | 1.0000 | 4-0
2 | Alabama | 0.9835 | 6-0
3 | Cincinnati | 0.9685 | 7-0
4 | Marshall | 0.9517 | 7-0
5 | Brigham Young | 0.9477 | 8-0
6 | Clemson | 0.9367 | 7-1
7 | Coastal Carolina | 0.9363 | 7-0
8 | Colorado | 0.9324 | 2-0
9 | Notre Dame | 0.9256 | 8-0
10 | Miami FL | 0.9110 | 7-1
11 | Oklahoma State | 0.9034 | 5-1
12 | Buffalo | 0.8903 | 2-0
13 | Oregon | 0.8876 | 2-0
14 | San Jose State | 0.8844 | 4-0
15 | Florida | 0.8833 | 5-1
16 | Louisiana-Lafayette | 0.8809 | 7-1
17 | Texas A&M | 0.8585 | 5-1
18 | Wisconsin | 0.8544 | 2-0
19 | Central Michigan | 0.8536 | 2-0
20 | Ohio State | 0.8499 | 3-0
21 | Tulsa | 0.8495 | 4-1
22 | Boise State | 0.8444 | 3-1
23 | Indiana | 0.8361 | 4-0
24 | Liberty | 0.8325 | 8-0
25 | Florida Atlantic | 0.8312 | 4-1

#### Observations and Notes (Updated 11/8/2020)

* The lack of non-con is really messing with my poll but it is what it is.  Hopefully it'll sort out more as more games get played.

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
