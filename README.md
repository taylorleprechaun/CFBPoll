# CFBPoll

This version of the poll is donezo and has been re-written and published to a [new repo](https://github.com/taylorleprechaun/CFBPollNew).  Go check it out there!

### About

Poll pulls some data about teams and uses it to rank them.  Data used includes game-by-game results and offensive and defensive team stats.

In the poll, a team's "Score" is in relation to the #1 team.  The formula gives points to teams using the data it has.  Once all the teams are ranked it measures against the highest score given out.  So, if Clemson is #1 and they earned 123 points from the poll, then every team's points will be divided by 123 to get their final score.

Made for fun.  I want to get this added to the Massey Composite for the 2020 season and beyond so I still need to do more tweaks before the season starts.

### Recent changes

* 6/24/2020: Big code update to make running the poll better and easier
* 9/26/2020: Some minor follow-up fixes I forgot to commit a couple months ago along with the rest of the BOAT/WOAT
* 11/8/2020: First poll of 2020 released (Week 10)
* 1/12/2021: Final poll of 2020 released (Week 16)

### Rankings (Updated 1/12/2021)

**Week 16 Rankings**

Rank| Team | Score | Record
---|---|---|---
1 | Alabama | 1.0000 | 13-0
2 | Ohio State | 0.9583 | 7-1
3 | Cincinnati | 0.9174 | 9-1
4 | Brigham Young | 0.9030 | 11-1
5 | Coastal Carolina | 0.9010 | 11-1
6 | Clemson | 0.8950 | 10-2
7 | San Jose State | 0.8912 | 7-1
8 | Texas A&M | 0.8904 | 9-1
9 | Louisiana-Lafayette | 0.8860 | 10-1
10 | Oklahoma | 0.8827 | 9-2
11 | Ball State | 0.8687 | 7-1
12 | Notre Dame | 0.8625 | 10-2
13 | Georgia | 0.8614 | 8-2
14 | Iowa State | 0.8563 | 9-3
15 | Liberty | 0.8283 | 10-1
16 | Texas | 0.8281 | 7-3
17 | Northwestern | 0.8277 | 7-2
18 | Oklahoma State | 0.8253 | 8-3
19 | Buffalo | 0.8208 | 6-1
20 | Miami FL | 0.8200 | 8-3
21 | USC | 0.8102 | 5-1
22 | Boise State | 0.8085 | 5-2
23 | Indiana | 0.7979 | 6-2
24 | Miami OH | 0.7977 | 2-1
25 | Army | 0.7924 | 9-3

#### Observations and Notes (Updated 1/12/2021)

* In the end, bowl season did definitely help my poll but a single data point for 50 teams isn't enough to help fix it.  Oh well.  Hopefully next season goes better.  See you all either next season or some time before then with a little update for this poll.  Go Gators!
* Additions to BOAT and WOAT!  (Down below in the section for it)

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

*New additions for the 2020 season!*

* This year Alabama clocks in with a 40.866 in my poll, placing them at #6 in the Playoff Era
* Vanderbilt, Bowling Green, and FIU clock in with 15.040, 15.610, and 15.790, respectively.  This puts them all outside of the all-time bottom 5, but it puts Vanderbilt at #3 worst of the Playoff Era.

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
