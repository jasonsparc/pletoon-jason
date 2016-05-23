# Creative Hothouse Android programming test
​
This document is the required information to perform the test for the Creative HotHouse Android developer role. For this test, you must create a small application according to the requirements describe below.
​
You have to deliver the result in less than a week after you receive this document. If for any reason you don’t consider it’s enough time, comment it when you receive this document.
​
#### What to do
​
You must create a client application for Pletoon, our comic platform. You must use the following endpoints:
​
- `http://pletoon.com/api/comics`: this endpoint will return a json with the current list of comic collections available
- `http://pletoon.com/api/comic/{comic_id}`: this endpoint will return details from a comic collection, inlcuding the episodes.
- `http://pletoon.com/api/episode/{episode_id}`: this endpoint will return the episode itself and all the images that compose that episode.
​
We want you to create an android app able to download and list the comics, show their details as well as the list of episodes, and the episode itself with all the realted data (episode images, likes, etc...)
​
You are free to decide what features and information to show and how to do it, but the app should follow some guidelines:
​
- It must work off-line, so, the user has to be able to see the information for the already downloaded collections if there is no connection. Obviously, for collections never downloaded you should indicate that this is not possible.
- You must show the cover image both for collections and episodes. You are free to choose how.
- You could add any functionality you think is appropriate, but we prefer quality than quantity.
- Libraries are welcome. You can use whatever library, module or component you want. Please, deliver a list of the libraries/components used and why are they used for.
- Application should be delivered with the needed files.
​
#### Deliverables
​
You must deliver the following items:
​
- The source code of the application. Send the same files you will upload to a repository, so we can easily import them.
- A few lines explaining what you developed and the architecture used
- Any decision taken that you might think it could be of interest (like the ones stated in the guidelines)
- The list of libraries/components you used (with a link to the repo/page where we can check them)
​
#### What we will check
​
This is the list of things we will check after you deliver, what’s important and what not:
- Code quality: we look for good clean code, with good practices and properly documented (we do  not look for exhaustive documentation, just good one).
- Architecture: the global architecture of the app is important to us, decisions like threading, caching systems, layer separation, etc will be really important
- Design: this is a developer test, not a designer test, so we are not looking for fancy designs, but there are some minimums requested.