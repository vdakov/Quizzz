Agenda for OOP Project meeting 8.1 - Group 30
 
* Location: DW PC-1
* Date: 29 March 2022
* Time: 14:45
* Attendees: Sydney Kho, Catalin Brita, Dmytro Maksymchuk, Delia Ion, Kyongsu Kim, Vasil Dakov
* Chairman: Dmytro Maksymchuk
* Note-takers: Sydney Kho, Vasil Dakov
 
 
## Agenda-items :
* [15:45-15:46] Opening by chair
* [15:46-15:50] Check-in
* [15:50-16:00] [Updates on the project]
  (Discuss: Each one of us discuss what he has done this week and talk about the overall progress of the project)
* [16:00-16:10] [Demonstration of demo] 
  (Discuss and show: Our current state of the project, present the demo of the application to TA)
* [16:05-16:10] [Coding feedback] 
  (Feedback: Get feedback regarding the amount of work and code everyone has contributed this week)
* [16:05-16:10] [Gitlab feedback] 
  (Feedback: Get feedback regarding the contributions to the Gitlab this week (comments, approve, merge))  
* [16:10-16:15] [CoC discussion] 
  (Discuss: Is code of conduct being satisfied, does it need any updates?)  
* [16:15-16:20] [Deadlines] 
  (Discuss: Are there any deadlines for this week?) 
* [16:20-16:30] [Trial run of the presentation] 
  (Discuss: The project demonstration and when things should be done)  
* [16:30-16:40] [Future steps in the project]
  (Discuss: Decide what the next steps in doing the project would be)
* [16:40-16:44] Tips and Tops- first by chairman then everyone:
  (Comment on what everybody in the group thought was well done about the project and give recommendations on what the team could improve) - Some reflection on the tips and tops from last week)
* [16:44-16:45] Closure (schedule next meeting)
 
# Tips and Tops
* Merge dev into your branch (without deleting) before trying to merge, so you don't get merge conflicts
* Be more responsive to new merge requests
* Do more code reviewing (especially those who have less gitlab activity)
* Make sure to include javadoc when writing code 
 
# Feedback

* Code contribution: yellow, Catalin should let others work more. Kyongsu and Delia should work a bit more this week.
* Most extensive issues should be done by Kyongsu, Dima, Delia
* Structure: yellow, some controllers don't have javadoc
* Testing: red, maybe let Kyongsu, Dima, Delia write the tests
* Move logic in controllers to seperate methods and test it
* Code reviews: yellow, Delia has very few comments and Kyongsu and Dima need to do more reviews this week. There should be more code reviews in general (no "all good" or "has been fixed")
* Don't leave the pipeline failing after multiple commits, run checkstyle locally before pushing
* Don't assign too many issues, only the ones you can definitely finish 
* Make sure to do time estimates when assigning issues at the start of the week. Don't forget to add time spent!
* Show you looked over the code by adding comments about javadoc, testing, specific features

# Contributions this week
## Vasil
Admin interface + client-server communication for images + refactoring server browser and waiting room to work wit new multiplayer backend

## Kyongsu
Implemented admin panel

## Dima
Rebuilt scenes, fixed screen size and added a check for unique multiplayer name

## Delia
Websockets for the emojis

## Catalin
Redid multiplayer communication and added long-polling

## Sydney
Implemented multiplayer leaderboard and rebuilt scenes to look more like prototype

# Deadlines
* Choose tonight if we want to make a video or a live demo and deliver it by next week, tuesday
* All the code for the game by next friday
* All deliverables (buddycheck, self-reflection) by next friday

# Presentation
* Probably wednesday morning
* We will be asked questions and to defend decisions like "why long-polling?" "Why websockets?" "Why this design choice?".
* Everyone should have a good overview of the whole project, even if you didn't work on it. e.g. Catalin implemented long polling but everyone should have some understanding of how it works.
* Before the presentation we have to make a video, deadline is next week friday
* Choose by tonight: Record something by tonight as a draft or plan a live demo next week (zoom or on campus)
* Highlight features but also showcase different technologies like long polling and websockets
* Lecturers will probably ask questions about what was in the video
* Questions will be about the concepts, so you don't need to show the code
* They might ask about the Code of Conduct (why we made changes, what changes we made)
* Don't worry about editing too much
