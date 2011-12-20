package imp.centralApplication;


import imp.DataTier.DataInterfaceDAO;
import imp.DataTier.SqltweetDAO;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import twitter4j.GeoLocation;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Tweet;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;

public class CentralTweetCollector1 {

	/**
	 * version 2.0
	 * This class is an Application that integration of sub-sytem of
	 * geo-coding,sentiment anlysys, NER (annotaions) and significant 
	 * phrase dectection. 
	 * This is implemented with DAO design pattern. 
	 * @author Harish Muppalla
	 */
	public CentralTweetCollector1(){

	}
	/**
	 * static main method
	 * @param args
	 */
	public static void main(String[] args) {

		CentralTweetCollector1 ctc = new CentralTweetCollector1();
		ctc.geoTweetCollector();
	}
	/**
	 * This geoTweetCollector is an important method, to get connect to 
	 * the twitter server and get access tweeets. responsible for extrating
	 * metadata from each tweet, through the text anlysis components sub-
	 * systems it can extract Annotations, significant phrases, geocoordinates
	 * and seniment.  the extracted information get inserted in to the database
	 * table.
	 * 
	 */
	public void  geoTweetCollector()
	{    
		//instantiation of the scheduler 
		ScheduledExecutorService scheduler = new ScheduledThreadPoolExecutor(1); 
        // TextAnalysisComponents object instanciation for accessing single instance
		final TextAnalysisComponents tac = TextAnalysisComponents.getInstance();
		// oblect for persistance storage to the database
		final DataInterfaceDAO stDAO =new SqltweetDAO();
		//simple bean object 
		final TweetObject tobj=new TweetObject();

		/*shedules this method creates and executes at a fixed time interval delay
		 *  until the task get cancelled   */
		scheduler.scheduleWithFixedDelay(new Runnable() {        @Override        public void run() {

			/* variable declaration and intialisation */


			Date createdAt; // date of tweet creation
			Timestamp timeat; // time of tweet creation

			String annotations = " "; 
			double sentiScore = 0;
			String phrases = "  ";
			double[] geocords;
			GeoLocation loc;


			// setting the specific geo-coordinates of grates london
			GeoLocation locsearch = new GeoLocation(51.3026, 0.739);


			// creating instance  of a twitter4j.TwitterFactory 
			Twitter twitter = new TwitterFactory().getInstance();
			try {



				for(int i=1;i<16;i++)
				{
					/* setting Qery options to search tweets by geo searching */
					Query qy = new Query();
					qy.setRpp(100);
					qy.setPage(i);
					qy.resultType("Mixed");
					/*specifying filter to search tweet with a radius of 100 miles
					 * from london */
					qy.geoCode(locsearch, 100,"mi");
					// checking the results perpage, access only if the results more than 10
					if(qy.getRpp()>10)    
					{     
						// implement the query for search the tweets
						QueryResult result = twitter.search(qy);


						List<Tweet> tweets = result.getTweets();

						for (Tweet tweet : tweets) {
							// iterating each tweet from the rearch results

							loc=tweet.getGeoLocation();
							// assigning the tweet metadata to the variables,by using twitter get methods


							tobj.setTweetID(tweet.getId()); 
							tobj.setUserID(tweet.getFromUserId());
							tobj.setUserNme(tweet.getFromUser());

							createdAt =  tweet.getCreatedAt();
							tobj.setCreatedAt(createdAt);
							timeat = new java.sql.Timestamp(createdAt.getTime());
							tobj.setTimeat(timeat);
							tobj.setProfilImage(tweet.getProfileImageUrl());
							tobj.setPlace(tweet.getLocation());
							tobj.setSearchloc("  ");
							tobj.setTweetText(tweet.getText());
							tobj.setSource(tweet.getSource());
							// calling annotaionGen by passing the tweet text as parameter
							annotations = tac.getAnnotations(tweet.getText());
							tobj.setAnnotations(annotations);
							// check  whether the receiver userid is null or not 
							if(tweet.getToUser()!=null)
							{
								tobj.setToUserid(tweet.getToUserId());
								tobj.setToUser(tweet.getToUser());
							}
							else 
							{
								tobj.setToUserid(null);
								tobj.setToUser("null");
							}
							/*check the geolocation variable of tweet is null or not 
							 * if it is not null assign the latitude(geo coordinate)
							 *  of tweet to variable latitude. else call the findGeoCord
							 *  method, place as argument*/ 
							if(loc!=null)
							{   

								tobj.setLatitude( loc.getLatitude());
								tobj.setLongitude(loc.getLongitude());
							}
							else
							{
								if(tobj.getPlace() != null  ){

									geocords = tac.getGeoCode(tobj.getPlace());


									tobj.setLatitude(geocords[0]);
									tobj.setLongitude(geocords[1]);

								}

							}


							/* check whether the tweet text is null or not, if it is not null 
							 * call the scorecalculation method passing tweet text as parameter */
							if(tobj.getTweetText()!=null ||tobj.getTweetText()!=" "){
								sentiScore= tac.getSentiment(tobj.getTweetText());
							}
							tobj.setSentiScore(sentiScore);

							/*check whether the tweet text is empty, if it not empty call the 
							 * phraseFinding method by passing the teet text, assign phrases as 
							 * "null" */
							if(tobj.getTweetText()!= ""){
								tobj.setPhrases(tac.getSignificantphrases(tobj.getTweetText()));
							}
							else if(phrases == "        ")
							{
								tobj.setPhrases("null");
							}

							stDAO.insertTweetdataFinal(tobj);        








						}
					}
				}


			} catch (TwitterException te) {
				te.printStackTrace();
				System.out.println("Failed to search tweets: " + te.getMessage());
				System.exit(-1);
			}
		}    },1,1,TimeUnit.MINUTES); //sheduler deley time is set 1 minute after each execution
	}























}
