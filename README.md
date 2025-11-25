# Team Project

Please keep this up-to-date with information about your project throughout the term.

The readme should include information such as:
- a summary of what your application is all about
- a list of the user stories, along with who is responsible for each one
- information about the API(s) that your project uses 
- screenshots or animations demonstrating current functionality

By keeping this README up-to-date,
your team will find it easier to prepare for the final presentation
at the end of the term.


API Info: 

link to API documentation: https://the-odds-api.com/liveapi/guides/v4/#parameters-2
We are using this API to give us real time odds from live sports across different sportsbooks.

Because our API calls are limited and we don't need to always be updating our possible bets 
to be games that are happening on the day, I periodically run the fetchOdds method but keep it commented
since we don't want to be adding the same data multiple times if we run the program multiple times. 

We are only using basketball games because they happen every day, meaning there will always be data we can draw from the API,
and the API responses are standardized so we can safely pull data without having to worry about formatting. 
it would be very easy to add bets that aren't NBA but for the sake of demonstration we use basketball.
